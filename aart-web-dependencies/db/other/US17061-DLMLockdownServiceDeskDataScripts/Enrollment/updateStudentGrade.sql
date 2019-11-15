-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 8: Update student grade.

DROP FUNCTION IF EXISTS updateStudentGrade(character varying, character varying, character varying, character varying, bigint, character varying, character varying);


CREATE OR REPLACE FUNCTION updateStudentGrade(state_student_identifier character varying, state_displayidentifier character varying, att_sch_displayidentifier character varying, ayp_sch_displayidentifier character varying, 
	schoolyear bigint, old_grade character varying, new_grade character varying) RETURNS VOID AS

$BODY$
   DECLARE
   state_Id BIGINT;   
   ceteSysAdminUserId BIGINT;  
   att_sch_id BIGINT;
   ayp_sch_id BIGINT;
   old_grade_Id BIGINT;
   new_grade_Id BIGINT;
   enrollmentRecord RECORD;
   studentTestsRecords RECORD;
   exitStuTestSecsStatus BIGINT;   
   exitStuTestsStatus BIGINT;
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(state_displayidentifier));
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));
   SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(ayp_sch_displayidentifier));  
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO old_grade_Id (SELECT id FROM gradecourse gc WHERE contentareaid IS NULL AND assessmentprogramgradesid IS NOT NULL 
					AND abbreviatedname = old_grade);
   SELECT INTO new_grade_Id (SELECT id FROM gradecourse gc WHERE contentareaid IS NULL AND assessmentprogramgradesid IS NOT NULL 
					AND abbreviatedname = new_grade);
   SELECT INTO exitStuTestSecsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'exitclearunenrolled');
   SELECT INTO exitStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'exitclearunenrolled');					
					
   IF((SELECT count(stu.*) FROM student stu JOIN enrollment en ON en.studentid = stu.id
              WHERE stu.statestudentidentifier = state_student_identifier AND stu.stateid = state_Id AND en.attendanceschoolid = att_sch_id 
                AND en.aypschoolid = ayp_sch_id AND en.currentschoolyear = schoolyear AND en.currentgradelevel = old_grade_Id) <= 0) THEN
	RAISE NOTICE 'Student % not found in state %, attendace school %, ayp school %, grade %, and current school %', state_student_identifier, 
	                  stateDisplayidentifier, att_sch_displayidentifier, ayp_sch_displayidentifier, old_grade, schoolyear;

   ELSE 

      FOR enrollmentRecord IN (SELECT stu.id as studentid, stu.stateid, en.* FROM student stu JOIN enrollment en ON en.studentid = stu.id
              WHERE stu.statestudentidentifier = state_student_identifier AND stu.stateid = state_Id AND en.attendanceschoolid = att_sch_id 
                AND en.aypschoolid = ayp_sch_id AND en.currentschoolyear = schoolyear AND en.currentgradelevel = old_grade_Id) LOOP

	UPDATE enrollment SET currentgradelevel = new_grade_Id, modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = enRollmentRecord.id;

	INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT', enrollmentRecord.id, ceteSysAdminUserId, now(),
		'GRADE_CHANGE', ('{"studentId":' || enrollmentRecord.studentid || ',"stateId":' || state_Id 
				|| ',"stateStudentIdentifier":"' || state_student_identifier || '","aypSchool":' || enrollmentRecord.aypschoolid 
				|| ',"attendanceSchoolId":' || enrollmentRecord.attendanceschoolid
				|| ',"newGrade":' || new_grade_Id || ',"oldGrade":' || old_grade_Id || '}')::json);

	RAISE NOTICE 'Student %  grade is changed to %', state_student_identifier, new_grade;

	FOR studentTestsRecords IN (SELECT  st.id, st.studentid, st.testid, st.testcollectionid, st.testsessionid, st.status,st.enrollmentid 
                FROM studentstests st JOIN testsession ts on st.testsessionid = ts.id JOIN operationaltestwindow otw on otw.id = ts.operationaltestwindowid
                 WHERE st.activeflag=true AND st.enrollmentid = enrollmentRecord.id AND (otw.effectivedate <= now() AND now() <= otw.expirydate)) 
	LOOP

	    PERFORM inActivateStuTestsTrackerITITestsessions(studentTestsId := studentTestsRecords.id , inActiveStuTestSecStatusId := exitStuTestSecsStatus, 
		      inActiveStuTestStatusId := exitStuTestsStatus, testsession_Id := studentTestsRecords.testsessionid, student_Id := studentTestsRecords.studentid);
	
        END LOOP;
	    UPDATE ititestsessionhistory SET activeflag=false,modifieddate=now(),modifieduser=ceteSysAdminUserId,status=exitStuTestsStatus
		       WHERE studentenrlrosterid IN (SELECT id FROM enrollmentsrosters WHERE enrollmentid = enrollmentRecord.id)
                       AND status = (SELECT cat.id FROM category cat, categorytype ct WHERE ct.id = cat.categorytypeid AND cat.categorycode='pending' AND ct.typecode = 'STUDENT_TEST_STATUS')
                       AND activeflag IS true;
     END LOOP;
   END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
--SELECT updateStudentGrade(state_student_identifier := , state_displayidentifier := , att_sch_displayidentifier := , ayp_sch_displayidentifier := , 
--	schoolyear := , old_grade := , new_grade := );