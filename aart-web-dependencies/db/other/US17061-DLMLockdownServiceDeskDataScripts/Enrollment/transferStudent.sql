-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario: 11: TransferStudent
DROP FUNCTION IF EXISTS transferStudent(character varying, character varying, character varying, numeric, date, bigint, 
      character varying, character varying, character varying, date, date, character varying);

CREATE OR REPLACE FUNCTION transferStudent(statestudent_identifier character varying, old_aypSch character varying, old_attSch character varying, exitReason numeric, exitDate date, schoolyear bigint, 
      new_AypSch character varying, new_attSch character varying, new_Dist character varying, new_schEntryDate date, new_DistEntryDate date, stateDisplayidentifier character varying)
        RETURNS VOID AS
$BODY$ 
 DECLARE
   old_studentEnrollemntRecord RECORD;
   old_stuTestsRecordsInprgsPenUnusedStatus RECORD;
   state_Id BIGINT;
   old_ayp_sch_id BIGINT;
   old_att_sch_id BIGINT;
   new_aypSch_id BIGINT;
   new_attSch_id BIGINT;
   new_dist_id BIGINT;
   exitStuTestSecsStatus BIGINT;   
   exitStuTestsStatus BIGINT;
   inProgressStuTestsStatus BIGINT;
   pendingStuTestsStatus BIGINT;
   unusedStuTestsStatus BIGINT;
   ceteSysAdminUserId BIGINT;
   new_EnrlId BIGINT;
   newSchEnrlRecord RECORD;
   enrlTestTypeSubjectAreaRecord RECORD;
   
 BEGIN	        
	SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));
	SELECT INTO old_ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(old_aypSch));
	SELECT INTO old_att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(old_attSch));
	SELECT INTO new_aypSch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(new_AypSch));
	SELECT INTO new_attSch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(new_attSch));
	SELECT INTO new_dist_id (SELECT districtid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(new_attSch));
	SELECT INTO exitStuTestSecsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'exitclearunenrolled');
	SELECT INTO exitStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'exitclearunenrolled');
	SELECT INTO inProgressStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress');
	SELECT INTO pendingStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending');
	SELECT INTO unusedStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused');
	SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');
    FOR old_studentEnrollemntRecord IN (SELECT stu.statestudentidentifier,stu.stateid, en.* FROM student stu JOIN enrollment en ON en.studentid = stu.id 
					WHERE lower(stu.statestudentidentifier) = lower(statestudent_identifier)
					AND stu.stateid = state_Id and en.currentschoolyear = schoolyear
					AND en.aypschoolid = old_ayp_sch_id and en.attendanceschoolid = old_att_sch_id)
     LOOP        
         IF (old_studentEnrollemntRecord.schoolentrydate <= exitDate) THEN
           UPDATE enrollment SET exitwithdrawaldate = exitDate, activeflag = false, exitwithdrawaltype = exitReason, modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = old_studentEnrollemntRecord.id;        

           RAISE NOTICE 'Updated the enrollment record with id: %', old_studentEnrollemntRecord.id;            

          INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT', old_studentEnrollemntRecord.id, ceteSysAdminUserId, now(),
		'EXIT_STUDENT', ('{"studentId":'|| old_studentEnrollemntRecord.studentid || ',"stateId":' ||  old_studentEnrollemntRecord.stateid
				|| ',"stateStudentIdentifier":"' || old_studentEnrollemntRecord.statestudentidentifier 
				|| '","aypSchool":' || old_studentEnrollemntRecord.aypschoolid || ',"attendanceSchoolId":'|| old_studentEnrollemntRecord.attendanceschoolid
				|| ',"exitWithdrawalDate":"' || exitDate || '","exitReason":"' || exitReason ||  '"}')::json);

          IF ((SELECT count(en.*) FROM enrollment en WHERE studentid = old_studentEnrollemntRecord.studentid AND aypschoolid = new_aypSch_id AND attendanceschoolid = new_attSch_id
                      AND currentschoolyear = schoolyear) <= 0) 
            THEN 
		INSERT INTO enrollment(aypschoolidentifier, residencedistrictidentifier, localstudentidentifier, 
			currentgradelevel, currentschoolyear, attendanceschoolid, 
			schoolentrydate, districtentrydate, stateentrydate, exitwithdrawaldate, 
			exitwithdrawaltype, specialcircumstancestransferchoice,
			giftedstudent, specialedprogramendingdate, 
			qualifiedfor504, studentid, restrictionid, createddate, createduser, 
			activeflag, modifieddate, modifieduser, aypSchoolId, sourcetype)
		 VALUES (new_AypSch, new_Dist, old_studentEnrollemntRecord.localstudentidentifier, 
		          old_studentEnrollemntRecord.currentgradelevel, schoolyear, new_attSch_id, 
		          new_schEntryDate, new_DistEntryDate, old_studentEnrollemntRecord.stateentrydate, null,
		          0, old_studentEnrollemntRecord.specialcircumstancestransferchoice,
		          old_studentEnrollemntRecord.giftedstudent, old_studentEnrollemntRecord.specialedprogramendingdate,
		          old_studentEnrollemntRecord.qualifiedfor504, old_studentEnrollemntRecord.studentid, old_studentEnrollemntRecord.restrictionid, now(), ceteSysAdminUserId,
		          true, now(), ceteSysAdminUserId, new_aypSch_id, 'STUDENT_TRANSFER_T') RETURNING id INTO new_EnrlId;

	    INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT', new_EnrlId, ceteSysAdminUserId, now(),
		'NEW_ENROLLMENT', ('{"studentId":'|| old_studentEnrollemntRecord.studentid || ',"stateId":' ||  old_studentEnrollemntRecord.stateid
				|| ',"stateStudentIdentifier":"' || old_studentEnrollemntRecord.statestudentidentifier 
				|| '","aypSchool":' || new_aypSch_id || ',"attendanceSchoolId":'|| new_attSch_id
				|| ',"grade":' || old_studentEnrollemntRecord.currentgradelevel || ',"schoolEntryDate":"' || new_schEntryDate ||  '"}')::json);

             FOR enrlTestTypeSubjectAreaRecord IN (SELECT * FROM enrollmenttesttypesubjectarea WHERE enrollmentid = old_studentEnrollemntRecord.id AND activeflag = true)
             LOOP 

                 INSERT INTO enrollmenttesttypesubjectarea(activeflag, enrollmentid, testtypeid, subjectareaid, groupingindicator1, groupingindicator2, createddate, createduser, modifieddate, modifieduser) 
                       VALUES(true, new_EnrlId, enrlTestTypeSubjectAreaRecord.testtypeid, enrlTestTypeSubjectAreaRecord.subjectareaid, enrlTestTypeSubjectAreaRecord.groupingindicator1, enrlTestTypeSubjectAreaRecord.groupingindicator2,
                                now(), ceteSysAdminUserId, now(), ceteSysAdminUserId);
                 
             END LOOP;

	  ELSE

	    FOR newSchEnrlRecord IN (SELECT en.* FROM enrollment en WHERE studentid = old_studentEnrollemntRecord.studentid AND aypschoolid = new_aypSch_id AND attendanceschoolid = new_attSch_id
                      AND currentschoolyear = schoolyear LIMIT 1)
             LOOP

		UPDATE enrollment SET activeflag = true, schoolentrydate = new_schEntryDate, districtentrydate = new_DistEntryDate, exitwithdrawaldate = null, 
		          exitwithdrawaltype = 0, currentgradelevel = old_studentEnrollemntRecord.currentgradelevel, modifieduser = ceteSysAdminUserId,
		          modifieddate = now() WHERE id = newSchEnrlRecord.id;

		RAISE NOTICE 'Enrollment % is updated, already transfered school has enrollment for student % in school year %', newSchEnrlRecord.id, old_studentEnrollemntRecord.statestudentidentifier, schoolyear;

             FOR enrlTestTypeSubjectAreaRecord IN (SELECT * FROM enrollmenttesttypesubjectarea WHERE enrollmentid = old_studentEnrollemntRecord.id AND activeflag = true)
             LOOP 

                 INSERT INTO enrollmenttesttypesubjectarea(activeflag, enrollmentid, testtypeid, subjectareaid, groupingindicator1, groupingindicator2, createddate, createduser, modifieddate, modifieduser) 
                       VALUES(true, newSchEnrlRecord.id, enrlTestTypeSubjectAreaRecord.testtypeid, enrlTestTypeSubjectAreaRecord.subjectareaid, enrlTestTypeSubjectAreaRecord.groupingindicator1, enrlTestTypeSubjectAreaRecord.groupingindicator2,
                                now(), ceteSysAdminUserId, now(), ceteSysAdminUserId);
                 
             END LOOP;
                  
             END LOOP;

          END IF;
          
          FOR old_stuTestsRecordsInprgsPenUnusedStatus IN (SELECT  st.id, st.studentid, st.testid, st.testcollectionid, st.testsessionid, st.status,st.enrollmentid 
                FROM studentstests st JOIN testsession ts on st.testsessionid = ts.id JOIN operationaltestwindow otw on otw.id = ts.operationaltestwindowid
                 WHERE st.activeflag=true AND st.enrollmentid = old_studentEnrollemntRecord.id AND (otw.effectivedate <= now() AND now() <= otw.expirydate)
                 AND st.status in (inProgressStuTestsStatus, pendingStuTestsStatus,unusedStuTestsStatus)) LOOP
	    
		PERFORM inActivateStuTestsTrackerITITestsessions(studentTestsId := old_stuTestsRecordsInprgsPenUnusedStatus.id , inActiveStuTestSecStatusId := exitStuTestSecsStatus, 
		      inActiveStuTestStatusId := exitStuTestsStatus, testsession_Id := old_stuTestsRecordsInprgsPenUnusedStatus.testsessionid, student_Id := old_stuTestsRecordsInprgsPenUnusedStatus.studentid);
		
				
          END LOOP;
          UPDATE ititestsessionhistory SET activeflag=false,modifieddate=now(),modifieduser=ceteSysAdminUserId,status=exitStuTestsStatus
		       WHERE studentenrlrosterid IN (SELECT id FROM enrollmentsrosters WHERE enrollmentid = old_studentEnrollemntRecord.id)
                       AND status = (SELECT cat.id FROM category cat, categorytype ct WHERE ct.id = cat.categorytypeid AND cat.categorycode='pending' AND ct.typecode = 'STUDENT_TEST_STATUS')
                       AND activeflag IS true;          
         ELSE 
           RAISE NOTICE 'Exit withdrawal date(%) is less than the school entry date(%)', exitDate, old_studentEnrollemntRecord.schoolentrydate;          
       END IF;       
   END LOOP;
   END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- SELECT transferStudent(statestudent_identifier :=, old_aypSch :=, old_attSch :=, exitReason :=, exitDate :=, schoolyear :=, 
      -- new_AypSch :=, new_attSch :=, new_Dist :=, new_schEntryDate :=, new_DistEntryDate :=, stateDisplayidentifier := );
