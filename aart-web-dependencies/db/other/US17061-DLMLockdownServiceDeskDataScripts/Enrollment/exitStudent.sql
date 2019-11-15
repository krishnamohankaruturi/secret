-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario: 9: Exit Student
DROP FUNCTION IF EXISTS exitStudent(character varying, character varying, character varying, numeric, date, bigint, character varying);

CREATE OR REPLACE FUNCTION exitStudent(statestudent_identifier character varying, ayp_sch_displayidentifier character varying, att_sch_displayidentifier character varying, exitReason numeric, exitDate date, schoolyear bigint, stateDisplayidentifier character varying)
     RETURNS VOID AS
$BODY$ 
 DECLARE
   studentEnrollemntRecord RECORD;
   stuTestsRecordsInprgsPenUnusedStatus RECORD;
   state_Id BIGINT;
   ayp_sch_id BIGINT;
   att_sch_id BIGINT;
   exitStuTestSecsStatus BIGINT;   
   exitStuTestsStatus BIGINT;
   inProgressStuTestsStatus BIGINT;
   pendingStuTestsStatus BIGINT;
   unusedStuTestsStatus BIGINT;
   ceteSysAdminUserId BIGINT;
   
 BEGIN	        
	SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));
	SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(ayp_sch_displayidentifier));
	SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));
	SELECT INTO exitStuTestSecsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'exitclearunenrolled');
	SELECT INTO exitStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'exitclearunenrolled');
	SELECT INTO inProgressStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress');
	SELECT INTO pendingStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending');
	SELECT INTO unusedStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused');
	SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');
    FOR studentEnrollemntRecord IN (SELECT stu.statestudentidentifier,stu.stateid, en.* FROM student stu JOIN enrollment en ON en.studentid = stu.id 
					WHERE lower(stu.statestudentidentifier) = lower(statestudent_identifier)
					AND stu.stateid = state_Id and en.currentschoolyear = schoolyear
					AND en.aypschoolid = ayp_sch_id and en.attendanceschoolid = att_sch_id)
     LOOP
         IF (studentEnrollemntRecord.schoolentrydate <= exitDate) THEN
           UPDATE enrollment SET exitwithdrawaldate = exitDate, activeflag = false, exitwithdrawaltype = exitReason, modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = studentEnrollemntRecord.id;        

           RAISE NOTICE 'Updated the enrollment record with id: %', studentEnrollemntRecord.id;            

          INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT', studentEnrollemntRecord.id, ceteSysAdminUserId, now(),
		'EXIT_STUDENT', ('{"studentId":'|| studentEnrollemntRecord.studentid || ',"stateId":' ||  studentEnrollemntRecord.stateid
				|| ',"stateStudentIdentifier":"' || studentEnrollemntRecord.statestudentidentifier 
				|| '","aypSchool":' || studentEnrollemntRecord.aypschoolid || ',"attendanceSchoolId":'|| studentEnrollemntRecord.attendanceschoolid
				|| ',"exitWithdrawalDate":"' || exitDate || '","exitReason":"' || exitReason ||  '"}')::json);

          
				                     
          FOR stuTestsRecordsInprgsPenUnusedStatus IN (SELECT  st.id, st.studentid, st.testid, st.testcollectionid, st.testsessionid, st.status,st.enrollmentid 
                FROM studentstests st JOIN testsession ts on st.testsessionid = ts.id JOIN operationaltestwindow otw on otw.id = ts.operationaltestwindowid
                 WHERE st.activeflag=true AND st.enrollmentid = studentEnrollemntRecord.id AND (otw.effectivedate <= now() AND now() <= otw.expirydate)
                 AND st.status in (inProgressStuTestsStatus, pendingStuTestsStatus,unusedStuTestsStatus)) LOOP
	    
		PERFORM inActivateStuTestsTrackerITITestsessions(studentTestsId := stuTestsRecordsInprgsPenUnusedStatus.id , inActiveStuTestSecStatusId := exitStuTestSecsStatus, 
		      inActiveStuTestStatusId := exitStuTestsStatus, testsession_Id := stuTestsRecordsInprgsPenUnusedStatus.testsessionid, student_Id := stuTestsRecordsInprgsPenUnusedStatus.studentid);
		
				
          END LOOP;
          UPDATE ititestsessionhistory SET activeflag=false,modifieddate=now(),modifieduser=ceteSysAdminUserId,status=exitStuTestsStatus
		       WHERE studentenrlrosterid IN (SELECT id FROM enrollmentsrosters WHERE enrollmentid = studentEnrollemntRecord.id)
                       AND status = (SELECT cat.id FROM category cat, categorytype ct WHERE ct.id = cat.categorytypeid AND cat.categorycode='pending' AND ct.typecode = 'STUDENT_TEST_STATUS')
                       AND activeflag IS true;          
         ELSE 
           RAISE NOTICE 'Exit withdrawal date(%) is less than the school entry date(%)', exitDate, studentEnrollemntRecord.schoolentrydate;          
       END IF;       
   END LOOP;
   END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;
