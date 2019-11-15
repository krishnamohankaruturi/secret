-- US17061: DLM Lockdown Service Desk Data Scripts
-- Updating the studentstests, student testsections, testsessions, student tracker, and ITI testsessions

DROP FUNCTION IF EXISTS inActivateStuTestsTrackerITITestsessions(bigint, bigint, bigint, bigint, bigint);

CREATE OR REPLACE FUNCTION inActivateStuTestsTrackerITITestsessions(studentTestsId bigint, inActiveStuTestSecStatusId bigint, inActiveStuTestStatusId bigint, testsession_Id bigint, student_Id bigint)
       RETURNS VOID AS
$BODY$
   DECLARE
     ceteSysAdminUserId BIGINT;
	
	BEGIN
	  SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');
	  UPDATE studentstestsections SET activeflag=false,modifieddate=CURRENT_TIMESTAMP, modifieduser = ceteSysAdminUserId,statusid=inActiveStuTestSecStatusId WHERE studentstestid=studentTestsId;
				
	  RAISE NOTICE 'Updated the studentstestsections records with studentstetsid: %', studentTestsId;
		
	  UPDATE studentstests SET activeflag=false,modifieddate=CURRENT_TIMESTAMP,modifieduser=ceteSysAdminUserId,status=inActiveStuTestSecStatusId WHERE id=studentTestsId;		

	  RAISE NOTICE 'Updated the studentstests records with id: %', studentTestsId;

	  IF ((SELECT count(*) FROM studentstests WHERE testsessionid = testsession_Id) = 1) THEN
		UPDATE testsession SET activeflag=false,modifieddate=CURRENT_TIMESTAMP,modifieduser=ceteSysAdminUserId,status=inActiveStuTestSecStatusId WHERE id=testsession_Id;		

		RAISE NOTICE 'Updated the testsession records with id: %', testsession_Id;
          END IF;
		
	  UPDATE studenttrackerband stb SET testsessionid=null,modifieddate=CURRENT_TIMESTAMP,modifieduser=ceteSysAdminUserId FROM studenttracker st 
		       WHERE st.id=stb.studenttrackerid AND st.studentid=student_Id and stb.testsessionid=testsession_Id;		
                
		       
	  UPDATE ititestsessionhistory SET activeflag=false,modifieddate=CURRENT_TIMESTAMP,modifieduser=ceteSysAdminUserId,status=inActiveStuTestSecStatusId
			WHERE studentid=student_Id AND testsessionid=testsession_Id;  		
        END;

$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;

  
-- Moving the completed testsessions and inactivating the studnt tracker if the student is added to new roster or existing roster with Course.

DROP FUNCTION IF EXISTS moveCompletedTestsAndResetSTWithCourse(BIGINT, BIGINT, BIGINT, BIGINT, BIGINT, BIGINT, BIGINT);

CREATE OR REPLACE FUNCTION moveCompletedTestsAndResetSTWithCourse(student_id BIGINT,enrollment_id BIGINT, subjectArea_id BIGINT, course_Id BIGINT, new_roster_id BIGINT, school_year BIGINT, attendance_schId BIGINT)
     RETURNS VOID AS

$BODY$
   DECLARE
   completed_statusId BIGINT;     
   existingStudentRosters RECORD;
   existingCompletedTestSessions RECORD;
   existingInCompleteAndInProgressTestsessions RECORD;   
   unused_statusId BIGINT;
   inProgress_statusId BIGINT;
   ceteSysAdminUserId BIGINT;
   latest_studentTracker_TestStatus BIGINT;
   roster_unEnrolled_TestStatus BIGINT;
   roster_unEnrolled_TestSecStatus BIGINT;
   studentTest_id BIGINT;
   studentTracker_Record RECORD;
   
 BEGIN
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');
   SELECT INTO completed_statusId (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete');   
   SELECT INTO unused_statusId(SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused');
   SELECT INTO inProgress_statusId(SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress');
   SELECT INTO roster_unEnrolled_TestStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'rosterunenrolled');
   SELECT INTO roster_unEnrolled_TestSecStatus(SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'rosterunenrolled');
   
   FOR existingStudentRosters IN(SELECT enrl.* FROM enrollmentsrosters enrl JOIN roster r ON r.id = enrl.rosterid WHERE enrl.enrollmentid = enrollment_id and enrl.rosterid != new_roster_id
		     and r.attendanceschoolid = attendance_schId AND r.statesubjectareaid = subjectArea_id AND r.currentschoolyear = school_year AND r.statecoursesid = course_Id) LOOP
      
	 IF ((SELECT count(st.*) FROM testsession ts JOIN studentstests st ON st.testsessionid = ts.id WHERE st.enrollmentid = enrollment_id AND ts.rosterid = existingStudentRosters.rosterid 
                AND st.status =  completed_statusId AND st.activeflag IS TRUE and ts.source in ('BATCHAUTO', 'MABATCH', 'FIXBATCH')) > 0) 
                   THEN

                  FOR existingCompletedTestSessions IN (SELECT ts.* FROM testsession ts JOIN studentstests st ON st.testsessionid = ts.id WHERE st.enrollmentid = enrollment_id AND ts.rosterid = existingStudentRosters.rosterid
                                   AND st.status =  completed_statusId AND st.activeflag IS TRUE and ts.source in ('BATCHAUTO', 'MABATCH', 'FIXBATCH')) LOOP 
                                      
                         UPDATE testsession SET rosterid = new_roster_id, modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = existingCompletedTestSessions.id;                                                 		      

                          RAISE NOTICE 'For studentID: %, Completed testsessionId % is moved from rosterId: % to new rosterId: %', student_id, existingCompletedTestSessions.id, existingStudentRosters.rosterid, new_roster_id;
                                                            
                   END LOOP;                                        
           END IF;                 

        select st.id from studentstests st join (select stb.id, stb.testsessionid from studenttrackerband stb join studenttracker stracker on stracker.id = stb.studenttrackerid join testsession ts on ts.id = stb.testsessionid
                          WHERE stracker.studentid = student_id and ts.attendanceschoolid = attendance_schId and stb.activeflag is true and stracker.schoolyear = school_year 
                          and ((stracker.courseid = course_Id  and stracker.contentareaid = subjectArea_id ) or (stracker.courseid = course_Id  and stracker.contentareaid is null))order by stb.id desc limit 1) as strackerDetilas
                on strackerDetilas.testsessionid = st.testsessionid
                and st.activeflag is true and st.studentid = student_id and st.enrollmentid = enrollment_id INTO studentTest_id;

        IF ( studentTest_id is not null) 
        THEN               
          select status from studentstests where id = studentTest_id INTO latest_studentTracker_TestStatus;
                
         
          IF (latest_studentTracker_TestStatus != completed_statusId) THEN                 

            FOR studentTracker_Record IN (SELECT stb.id, stb.testsessionid,stb.studenttrackerid from studenttrackerband stb join studenttracker stracker on stracker.id = stb.studenttrackerid join testsession ts on ts.id = stb.testsessionid
                          WHERE stracker.studentid = student_id and ts.attendanceschoolid = attendance_schId and stb.activeflag is true and stracker.schoolyear = school_year 
                          and ((stracker.courseid = course_Id  and stracker.contentareaid = subjectArea_id ) or (stracker.courseid = course_Id  and stracker.contentareaid is null))order by stb.id desc limit 1)

            LOOP                          
                
              UPDATE studentstestsections set statusid = roster_unEnrolled_TestSecStatus, activeflag = false, modifieddate = now(), modifieduser = ceteSysAdminUserId
                 WHERE studentstestid = studentTest_id; 

              UPDATE studentstests set status= roster_unEnrolled_TestStatus, activeflag = false, modifieddate = now(), modifieduser = ceteSysAdminUserId
                 WHERE id = studentTest_id;

              UPDATE studenttrackerband set activeflag = false, modifieddate = now(), modifieduser = ceteSysAdminUserId where id = studentTracker_Record.id;


               UPDATE studenttracker set status = 'UNTRACKED', modifieddate = now(), modifieduser = ceteSysAdminUserId where id = studentTracker_Record.studenttrackerid;
               
              RAISE NOTICE 'Student Tracker band is updated for studentId: %', student_id;
              
             END LOOP;   
          END IF;
       END IF;
        
   END LOOP;		     

 END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;
  
  
  
  
-- Moving the completed testsessions and inactivating the studnt tracker if the student is added to new roster or existing roster with no course.

DROP FUNCTION IF EXISTS moveCompletedTestsAndResetSTWithNoCourse(BIGINT, BIGINT, BIGINT, BIGINT, BIGINT, BIGINT);

CREATE OR REPLACE FUNCTION moveCompletedTestsAndResetSTWithNoCourse(student_id BIGINT,enrollment_id BIGINT, subjectArea_id BIGINT, new_roster_id BIGINT, school_year BIGINT, attendance_schId BIGINT)
     RETURNS VOID AS

$BODY$
   DECLARE
   completed_statusId BIGINT;     
   existingStudentRosters RECORD;
   existingCompletedTestSessions RECORD;
   existingInCompleteAndInProgressTestsessions RECORD;   
   unused_statusId BIGINT;
   inProgress_statusId BIGINT;
   ceteSysAdminUserId BIGINT;
   latest_studentTracker_TestStatus BIGINT;
   roster_unEnrolled_TestStatus BIGINT;
   roster_unEnrolled_TestSecStatus BIGINT;
   studentTest_id BIGINT;
   studentTracker_Record RECORD;
   
 BEGIN
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');
   SELECT INTO completed_statusId (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete');   
   SELECT INTO unused_statusId(SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused');
   SELECT INTO inProgress_statusId(SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress');
   SELECT INTO roster_unEnrolled_TestStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'rosterunenrolled');
   SELECT INTO roster_unEnrolled_TestSecStatus(SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'rosterunenrolled');
   
   FOR existingStudentRosters IN(SELECT enrl.* FROM enrollmentsrosters enrl JOIN roster r ON r.id = enrl.rosterid WHERE enrl.enrollmentid = enrollment_id and enrl.rosterid != new_roster_id
		     and r.attendanceschoolid = attendance_schId AND r.statesubjectareaid = subjectArea_id AND r.currentschoolyear = school_year) LOOP
      
	 IF ((SELECT count(st.*) FROM testsession ts JOIN studentstests st ON st.testsessionid = ts.id WHERE st.enrollmentid = enrollment_id AND ts.rosterid = existingStudentRosters.rosterid 
                AND st.status =  completed_statusId AND st.activeflag IS TRUE and ts.source in ('BATCHAUTO', 'MABATCH', 'FIXBATCH')) > 0) 
                   THEN

                  FOR existingCompletedTestSessions IN (SELECT ts.* FROM testsession ts JOIN studentstests st ON st.testsessionid = ts.id WHERE st.enrollmentid = enrollment_id AND ts.rosterid = existingStudentRosters.rosterid
                                   AND st.status =  completed_statusId AND st.activeflag IS TRUE and ts.source in ('BATCHAUTO', 'MABATCH', 'FIXBATCH')) LOOP 
                                      
                         UPDATE testsession SET rosterid = new_roster_id, modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = existingCompletedTestSessions.id;                                                 		      

                          RAISE NOTICE 'For studentID: %, Completed testsessionId % is moved from rosterId: % to new rosterId: %', student_id, existingCompletedTestSessions.id, existingStudentRosters.rosterid, new_roster_id;
                                                            
                   END LOOP;                                        
           END IF;                 

        select st.id from studentstests st join (select stb.id, stb.testsessionid from studenttrackerband stb join studenttracker stracker on stracker.id = stb.studenttrackerid join testsession ts on ts.id = stb.testsessionid
                WHERE stracker.studentid = student_id and ts.attendanceschoolid = attendance_schId and stb.activeflag is true and stracker.contentareaid = subjectArea_id and stracker.schoolyear = school_year and stracker.courseid is null order by stb.id desc limit 1) as strackerDetilas
                on strackerDetilas.testsessionid = st.testsessionid
                and st.activeflag is true and st.studentid = student_id and st.enrollmentid = enrollment_id
                INTO studentTest_id;


        IF (studentTest_id is not null) 
        THEN                

           select st.status from studentstests st join (select stb.id, stb.testsessionid from studenttrackerband stb join studenttracker stracker on stracker.id = stb.studenttrackerid join testsession ts on ts.id = stb.testsessionid
                WHERE stracker.studentid = student_id and ts.attendanceschoolid = attendance_schId and stb.activeflag is true and stracker.contentareaid = subjectArea_id and stracker.schoolyear = school_year and stracker.courseid is null order by stb.id desc limit 1) as strackerDetilas
                on strackerDetilas.testsessionid = st.testsessionid
                and st.activeflag is true and st.studentid = student_id and st.enrollmentid = enrollment_id
                INTO latest_studentTracker_TestStatus; 
         
          IF (latest_studentTracker_TestStatus != completed_statusId) THEN

           FOR studentTracker_Record IN (select stb.id, stb.testsessionid,stb.studenttrackerid from studenttrackerband stb join studenttracker stracker on stracker.id = stb.studenttrackerid join testsession ts on ts.id = stb.testsessionid
                WHERE stracker.studentid = student_id and ts.attendanceschoolid = attendance_schId and stb.activeflag is true and stracker.contentareaid = subjectArea_id and stracker.schoolyear = school_year and stracker.courseid is null order by stb.id desc limit 1)

             LOOP
           
               UPDATE studentstestsections set statusid = roster_unEnrolled_TestSecStatus, activeflag = false, modifieddate = now(), modifieduser = ceteSysAdminUserId
                  WHERE studentstestid = studentTest_id;


               UPDATE studentstests set status= roster_unEnrolled_TestStatus, activeflag = false, modifieddate = now(), modifieduser = ceteSysAdminUserId
                  WHERE id = studentTest_id;       

               UPDATE studenttrackerband set activeflag = false, modifieddate = now(), modifieduser = ceteSysAdminUserId where id = studentTracker_Record.id;


               UPDATE studenttracker set status = 'UNTRACKED', modifieddate = now(), modifieduser = ceteSysAdminUserId where id = studentTracker_Record.studenttrackerid;


                RAISE NOTICE 'Student Tracker band is updated for studentId: %', student_id;     
                
             END LOOP;
          END IF;

       END IF;
        
   END LOOP;		     

 END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;
  
  
  
  
  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario: 9: Add New Enrollment
DROP FUNCTION IF EXISTS addNewEnrollment(character varying, character varying, character varying, character varying, character varying, bigint, 
         date, date, date, character varying, character varying);

CREATE OR REPLACE FUNCTION addNewEnrollment(statestudent_identifier character varying, localState_StuId character varying, aypSch character varying, attSch character varying, district character varying, schoolyear bigint, 
         schEntryDate date, distEntryDate date, state_EntryDate date, grade character varying, stateDisplayidentifier character varying)
        RETURNS VOID AS
$BODY$ 
 DECLARE   
   state_Id BIGINT;
   ayp_sch_id BIGINT;
   att_sch_id BIGINT;
   dist_id BIGINT;
   ceteSysAdminUserId BIGINT;
   new_EnrlId BIGINT;  
   newSchEnrlRecord RECORD;
   grade_id BIGINT;
   student_id BIGINT;
   sch_entry_date date;
   district_entry_date date;
   state_entry_date date;
   
 BEGIN	        
	SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));
	SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(aypSch));
	SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(attSch));	
	SELECT INTO dist_id (SELECT districtid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(district));		
	SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');
        SELECT INTO grade_id (SELECT id FROM gradecourse gc WHERE contentareaid IS NULL AND assessmentprogramgradesid IS NOT NULL 
					AND abbreviatedname = grade);
        SELECT INTO student_id (SELECT id FROM student WHERE lower(statestudentidentifier) = lower(statestudent_identifier) AND stateid = state_Id LIMIT 1);
	
    IF (student_id IS NULL)

     THEN
	RAISE NOTICE 'Student % is not present in state %', statestudent_identifier, stateDisplayidentifier;
	
    ELSE 
      IF((SELECT  count(*) FROM enrollment WHERE studentid = student_id AND currentschoolyear = schoolyear
		AND aypschoolid = ayp_sch_id and attendanceschoolid = att_sch_id) <= 0)
        THEN
          INSERT INTO enrollment(aypschoolidentifier, residencedistrictidentifier, localstudentidentifier, 
		currentgradelevel, currentschoolyear, attendanceschoolid, 
		schoolentrydate, districtentrydate, stateentrydate, exitwithdrawaldate, 
		exitwithdrawaltype, studentid, restrictionid, createddate, createduser, 
		activeflag, modifieddate, modifieduser, aypSchoolId, sourcetype)
	     VALUES (aypSch, district, localState_StuId, 
		     grade_id, schoolyear, att_sch_id, 
		     schEntryDate, distEntryDate, state_EntryDate, null,
		     0, student_id, 2, now(), ceteSysAdminUserId,
		     true, now(), ceteSysAdminUserId, ayp_sch_id, 'LOCK_DOWN_SCRIPT') RETURNING id INTO new_EnrlId;

	    INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT', new_EnrlId, ceteSysAdminUserId, now(),
		'NEW_ENROLLMENT', ('{"studentId":'|| student_id || ',"stateId":' ||  state_Id
				|| ',"stateStudentIdentifier":"' ||  statestudent_identifier
				|| '","aypSchool":' || ayp_sch_id || ',"attendanceSchoolId":'|| att_sch_id
				|| ',"grade":' || grade_id || ',"schoolEntryDate":"' || schEntryDate ||  '"}')::json);

	RAISE NOTICE 'Student % is enrolled into School % for school year % and grade level %', statestudent_identifier, attSch, schoolyear, grade; 
				
        ELSE
	      FOR newSchEnrlRecord IN (SELECT stu.statestudentidentifier, en.* FROM enrollment en JOIN student stu ON stu.id = en.studentid
	                WHERE stu.id = student_id AND aypschoolid = ayp_sch_id AND attendanceschoolid = att_sch_id
                         AND currentschoolyear = schoolyear LIMIT 1)
             LOOP
		IF(schEntryDate is null) THEN
		     sch_entry_date := newSchEnrlRecord.schoolentrydate;
		ELSE
		     sch_entry_date := schEntryDate;
		END IF;

                IF(distEntryDate is null) THEN 
                    district_entry_date := newSchEnrlRecord.districtentrydate;
                ELSE
                   district_entry_date := distEntryDate;
                END IF;

                IF(state_EntryDate is null) THEN
                   state_entry_date := newSchEnrlRecord.stateentrydate;
                ELSE
                   state_entry_date := state_EntryDate;
                END IF;
		
		UPDATE enrollment SET activeflag = true, schoolentrydate = sch_entry_date, districtentrydate = district_entry_date, stateentrydate = state_entry_date,exitwithdrawaldate = null, 
		          exitwithdrawaltype = 0, modifieduser = ceteSysAdminUserId, modifieddate = now() WHERE id = newSchEnrlRecord.id;

		RAISE NOTICE 'Enrollment % is updated, student % in school year %', newSchEnrlRecord.id, newSchEnrlRecord.statestudentidentifier, schoolyear;
                  
             END LOOP;
        
        END IF;
      END IF;
   END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


--SELECT addNewEnrollment(statestudent_identifier :=, localState_StuId :=, aypSch :=, attSch :=, district :=, schoolyear :=, 
        -- schEntryDate :=, distEntryDate :=, state_EntryDate := , grade := , stateDisplayidentifier :=);

  

-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario: 7: Exit Student
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

--  SELECT exitStudent(statestudent_identifier := '1234567814', ayp_sch_displayidentifier :='1090024020', att_sch_displayidentifier := '1090024020', exitReason := 7, exitDate := '2016-03-07', schoolyear := 2016, stateDisplayidentifier := 'MO');

  
  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario: 8: TransferStudent
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

  
  
  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 6: Update student grade.

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
	                  state_student_identifier, att_sch_displayidentifier, ayp_sch_displayidentifier, old_grade, schoolyear;

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



-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 2.2: Adding student to roster with course.

DROP FUNCTION IF EXISTS addStudentToRosterWithCourse(character varying, character varying, character varying, bigint, character varying, character varying, character varying, 
      character varying, character varying);


CREATE OR REPLACE FUNCTION addStudentToRosterWithCourse(state_student_identifier character varying, att_sch_displayidentifier character varying, ayp_sch_displayidentifier character varying, schoolyear bigint, 
      subject_abbrName character varying, course_Abbrname character varying, teacher_uniqueCommonId character varying, 
      roster_name character varying, stateDisplayidentifier character varying) RETURNS VOID AS

$BODY$
   DECLARE
   state_Id BIGINT;
   att_sch_id BIGINT;
   ayp_sch_id BIGINT;
   ceteSysAdminUserId BIGINT;
   contentArea_Id BIGINT;
   subject_Id BIGINT;
   teacher_Id BIGINT;   
   rosterRecord RECORD;
   enrollmentRecord RECORD;
   enrlRecords RECORD;
   course_Id BIGINT;
   enrl_Id BIGINT;   
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));   
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));
   SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(ayp_sch_displayidentifier));
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO teacher_Id (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(teacher_uniqueCommonId) LIMIT 1);    
   SELECT INTO course_Id (SELECT id FROM gradecourse WHERE lower(abbreviatedname) = lower(course_Abbrname) AND course is true LIMIT 1);
   
   IF((SELECT count(*) FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id AND r.statecoursesid = course_Id
                          AND currentschoolyear = schoolyear AND teacherid = teacher_Id)) <= 0 THEN

     RAISE NOTICE 'No rosters found with roster name: %, subject: %, course: %, attendanceschool: %, teacher: %, and school year: %  ', roster_name, subject_abbrName, course_Abbrname, att_sch_displayidentifier,
          teacher_uniqueCommonId, schoolyear;

   ELSE
    IF((SELECT count(en.*) FROM enrollment en JOIN student stu ON stu.id = en.studentid AND en.currentschoolyear = schoolyear AND en.aypschoolid = ayp_sch_id AND en.attendanceschoolid = att_sch_id
           AND stu.statestudentidentifier = state_student_identifier AND stu.stateid = state_Id) <= 0) THEN 

        RAISE NOTICE 'Student % is not found with ayp %, attendance %  in school year %', state_student_identifier, ayp_sch_displayidentifier, att_sch_displayidentifier, schoolyear;
    ELSE 
         FOR enrollmentRecord IN(SELECT en.* FROM enrollment en JOIN student stu ON stu.id = en.studentid AND en.currentschoolyear = schoolyear AND en.aypschoolid = ayp_sch_id AND en.attendanceschoolid = att_sch_id
           AND stu.statestudentidentifier = state_student_identifier AND stu.stateid = state_Id)
       LOOP
         FOR rosterRecord IN(SELECT r.* FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id AND r.statecoursesid = course_Id
                          AND currentschoolyear = schoolyear AND teacherid = teacher_Id)
        LOOP
           IF((SELECT count(*) FROM enrollmentsrosters WHERE enrollmentid = enrollmentRecord.id AND rosterid = rosterRecord.id) <= 0) THEN            

		INSERT INTO enrollmentsrosters(enrollmentid, rosterid, createddate, createduser, activeflag, modifieddate, modifieduser) 
                         VALUES (enrollmentRecord.id, rosterRecord.id, now(), ceteSysAdminUserId, true, now(), ceteSysAdminUserId) RETURNING id INTO enrl_Id;

                INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrl_Id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || rosterRecord.id || ', "enrollmentId":' ||  enrollmentRecord.id || ',"enrollmentRosterId":' || enrl_Id || '}')::json);

		RAISE NOTICE 'Student % is added to the roster %', state_student_identifier, roster_name;

                 PERFORM moveCompletedTestsAndResetSTWithCourse(student_id := enrollmentRecord.studentid,enrollment_id := enrollmentRecord.id, subjectArea_id := subject_Id, 
                         course_Id := course_Id, new_roster_id := rosterRecord.id, school_year := schoolyear, attendance_schId := att_sch_id);
				
	   ELSE		      
	      FOR enrlRecords IN (SELECT * FROM enrollmentsrosters WHERE enrollmentid = enrollmentRecord.id AND rosterid = rosterRecord.id LIMIT 1) 
	        LOOP                    
                  UPDATE enrollmentsrosters SET activeflag = true, modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = enrlRecords.id;

	          INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrlRecords.id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || enrlRecords.rosterid || ', "enrollmentId":' ||  enrlRecords.enrollmentid || ',"enrollmentRosterId":' || enrlRecords.id || '}')::json);

		  RAISE NOTICE 'Student % is added to the roster %', state_student_identifier, roster_name;

		  PERFORM moveCompletedTestsAndResetSTWithCourse(student_id := enrollmentRecord.studentid,enrollment_id := enrollmentRecord.id, subjectArea_id := subject_Id, 
		             course_Id := course_Id, new_roster_id := enrlRecords.rosterid, school_year := schoolyear, attendance_schId := att_sch_id);
		
	      END LOOP;
	   END IF;	   
        END LOOP;        
     END LOOP;
     END IF;
   END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
--SELECT addStudentToRosterWithCourse(state_student_identifier := , att_sch_displayidentifier := , ayp_sch_displayidentifier := , schoolyear := , 
       -- subject_abbrName := , course_Abbrname := , teacher_uniqueCommonId := , 
      -- roster_name := , stateDisplayidentifier := );
      
  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 2.1: Adding student to roster with NO course. (Not transfering any completed testsessions to new roster).

DROP FUNCTION IF EXISTS addStudentToRosterWithNOCourse(character varying, character varying, character varying, bigint, character varying, character varying, character varying, character varying, character varying);


CREATE OR REPLACE FUNCTION addStudentToRosterWithNOCourse(state_student_identifier character varying, att_sch_displayidentifier character varying, ayp_sch_displayidentifier character varying, schoolyear bigint, 
      subject_abbrName character varying, teacher_uniqueCommonId character varying, roster_name character varying, stateDisplayidentifier character varying) RETURNS VOID AS

$BODY$
   DECLARE
   state_Id BIGINT;
   att_sch_id BIGINT;
   ayp_sch_id BIGINT;
   ceteSysAdminUserId BIGINT;
   contentArea_Id BIGINT;
   subject_Id BIGINT;
   teacher_Id BIGINT;   
   rosterRecord RECORD;
   enrollmentRecord RECORD;
   enrlRecords RECORD;   
   enrl_Id BIGINT;   
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));   
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));
   SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(ayp_sch_displayidentifier));
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO teacher_Id (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(teacher_uniqueCommonId) LIMIT 1);
   
   IF((SELECT count(*) FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id
                          AND currentschoolyear = schoolyear AND teacherid = teacher_Id)) <= 0 THEN

     RAISE NOTICE 'No rosters found with roster name: %, subject: %, attendanceschool: %, teacher: %, and school year: %  ', roster_name, subject_abbrName, att_sch_displayidentifier,
          teacher_uniqueCommonId, schoolyear;

   ELSE
    IF((SELECT count(en.*) FROM enrollment en JOIN student stu ON stu.id = en.studentid AND en.currentschoolyear = schoolyear AND en.aypschoolid = ayp_sch_id AND en.attendanceschoolid = att_sch_id
           AND stu.statestudentidentifier = state_student_identifier AND stu.stateid = state_Id) <= 0) THEN 

        RAISE NOTICE 'Student % is not found with ayp %, attendance %  in school year %', state_student_identifier, ayp_sch_displayidentifier, att_sch_displayidentifier, schoolyear;
    ELSE 
         FOR enrollmentRecord IN(SELECT en.* FROM enrollment en JOIN student stu ON stu.id = en.studentid AND en.currentschoolyear = schoolyear AND en.aypschoolid = ayp_sch_id AND en.attendanceschoolid = att_sch_id
           AND stu.statestudentidentifier = state_student_identifier AND stu.stateid = state_Id)
       LOOP
         FOR rosterRecord IN(SELECT r.* FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND r.attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id 
                          AND currentschoolyear = schoolyear AND teacherid = teacher_Id)
        LOOP
           IF((SELECT count(*) FROM enrollmentsrosters WHERE enrollmentid = enrollmentRecord.id AND rosterid = rosterRecord.id) <= 0) THEN            

		INSERT INTO enrollmentsrosters(enrollmentid, rosterid, createddate, createduser, activeflag, modifieddate, modifieduser) 
                         VALUES (enrollmentRecord.id, rosterRecord.id, now(), ceteSysAdminUserId, true, now(), ceteSysAdminUserId) RETURNING id INTO enrl_Id;

                INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrl_Id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || rosterRecord.id || ', "enrollmentId":' ||  enrollmentRecord.id || ',"enrollmentRosterId":' || enrl_Id || '}')::json);

		RAISE NOTICE 'Student % is added to the roster %', state_student_identifier, roster_name;

		 PERFORM moveCompletedTestsAndResetSTWithNoCourse(student_id := enrollmentRecord.studentid, enrollment_id := enrollmentRecord.id, subjectArea_id := subject_Id, new_roster_id := rosterRecord.id, school_year:=schoolyear, attendance_schId := att_sch_id);		 
	   ELSE		      
	      FOR enrlRecords IN (SELECT * FROM enrollmentsrosters WHERE enrollmentid = enrollmentRecord.id AND rosterid = rosterRecord.id LIMIT 1) 
	        LOOP                    
                  UPDATE enrollmentsrosters SET activeflag = true, modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = enrlRecords.id;

	          INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrlRecords.id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || enrlRecords.rosterid || ', "enrollmentId":' ||  enrlRecords.enrollmentid || ',"enrollmentRosterId":' || enrlRecords.id || '}')::json);

		  RAISE NOTICE 'Student % is added to the roster %', state_student_identifier, roster_name;

                  PERFORM moveCompletedTestsAndResetSTWithNoCourse(student_id := enrollmentRecord.studentid, enrollment_id := enrollmentRecord.id, subjectArea_id := subject_Id, new_roster_id := enrlRecords.rosterid, school_year:=schoolyear, attendance_schId := att_sch_id);
		
	      END LOOP;
	   END IF;	   
        END LOOP;        
     END LOOP;
     END IF;
   END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;



-- Calling the function
--SELECT addStudentToRosterWithNOCourse(state_student_identifier := , att_sch_displayidentifier := , ayp_sch_displayidentifier := , schoolyear := , 
      -- subject_abbrName := , teacher_uniqueCommonId := , roster_name := , stateDisplayidentifier := )
      

  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 3.4: Change educator and roster name with course.

DROP FUNCTION IF EXISTS changeEductorAndRosterNameOnRosterWithCourse(character varying, bigint, character varying, character varying, character varying, character varying, character varying, character varying, character varying);


CREATE OR REPLACE FUNCTION changeEductorAndRosterNameOnRosterWithCourse(att_sch_displayidentifier character varying, schoolyear bigint, 
      subject_abbrName character varying, course_Abbrname character varying, old_teacher_uniqueCommonId character varying, new_teacher_uniqueCommonId character varying, 
      old_roster_name character varying, new_roster_name character varying, stateDisplayidentifier character varying) RETURNS VOID AS

$BODY$
   DECLARE
   state_Id BIGINT;
   att_sch_id BIGINT;
   ceteSysAdminUserId BIGINT;
   contentArea_Id BIGINT;
   subject_Id BIGINT;
   old_teacherId BIGINT;
   new_teacherId BIGINT;
   rosterRecord RECORD;
   course_Id BIGINT;
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));   
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));   
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO old_teacherId (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(old_teacher_uniqueCommonId) LIMIT 1); 
   SELECT INTO new_teacherId (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(new_teacher_uniqueCommonId) LIMIT 1); 
   SELECT INTO course_Id (SELECT id FROM gradecourse WHERE lower(abbreviatedname) = lower(course_Abbrname) AND course is true LIMIT 1);
   
   IF((SELECT count(*) FROM roster r WHERE lower(r.coursesectionname) = lower(old_roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id AND r.statecoursesid = course_Id
                          AND currentschoolyear = schoolyear AND teacherid = old_teacherId)) <= 0 THEN

     RAISE NOTICE 'No rosters found with roster name: %, subject: %, course: %, attendanceschool: %, teacher: %, and school year: %  ', old_roster_name, subject_abbrName, course_Abbrname, att_sch_displayidentifier,
           old_teacher_uniqueCommonId, schoolyear;

   ELSE
   FOR rosterRecord IN (SELECT r.* FROM roster r WHERE lower(r.coursesectionname) = lower(old_roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id AND r.statecoursesid = course_Id
                          AND currentschoolyear = schoolyear AND teacherid = old_teacherId)
     LOOP

	UPDATE roster set coursesectionname= new_roster_name, teacherid = new_teacherId, modifieddate=CURRENT_TIMESTAMP, modifieduser = ceteSysAdminUserId 
	            WHERE id = rosterRecord.id;

	INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ROSTER', rosterRecord.id, ceteSysAdminUserId, now(),
		'TEACHER_CHANGE', ('{"rosterId":' ||  rosterRecord.id || ',"oldEducatorId":' ||  old_teacherId || ',"newEducatorId":'  || new_teacherId || '}')::json);

	RAISE NOTICE 'Roster with id %, changed the educator % from to new educator %', rosterRecord.id, old_teacher_uniqueCommonId, new_teacher_uniqueCommonId;

     END LOOP;
   END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
--SELECT changeEductorAndRosterNameOnRosterWithCourse(att_sch_displayidentifier := 'ABAES', schoolyear := 2016, subject_abbrName :='ELA' , course_Abbrname := 'ENG11', old_teacher_uniqueCommonId := 'PriyaRao',
      --  new_teacher_uniqueCommonId:='Priya_DLMTest' ,  old_roster_name:='Priya_DLM_ELA_Test', new_roster_name :='Venky_DLM_ELA_TEST' , stateDisplayidentifier := 'MO');
      
  

  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 3.3: Change educator and roster name with no course.

DROP FUNCTION IF EXISTS changeEductorAndRosterNameOnRosterWithNoCourse(character varying, bigint, character varying, character varying, character varying, character varying, character varying, character varying);


CREATE OR REPLACE FUNCTION changeEductorAndRosterNameOnRosterWithNoCourse(att_sch_displayidentifier character varying, schoolyear bigint, 
      subject_abbrName character varying, old_teacher_uniqueCommonId character varying, new_teacher_uniqueCommonId character varying, old_roster_name character varying, new_roster_name character varying, stateDisplayidentifier character varying) RETURNS VOID AS

$BODY$
   DECLARE
   state_Id BIGINT;
   att_sch_id BIGINT;
   ceteSysAdminUserId BIGINT;
   contentArea_Id BIGINT;
   subject_Id BIGINT;
   old_teacherId BIGINT;
   new_teacherId BIGINT;
   rosterRecord RECORD;
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));   
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));   
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO old_teacherId (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(old_teacher_uniqueCommonId) LIMIT 1); 
   SELECT INTO new_teacherId (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(new_teacher_uniqueCommonId) LIMIT 1); 
   
   IF((SELECT count(*) FROM roster r WHERE lower(r.coursesectionname) = lower(old_roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id
                          AND currentschoolyear = schoolyear AND teacherid = old_teacherId)) <= 0 THEN

     RAISE NOTICE 'No rosters found with roster name: %, subject: %, attendanceschool: %, teacher: %, and school year: %  ', old_roster_name, subject_abbrName, att_sch_displayidentifier,
           old_teacher_uniqueCommonId, schoolyear;

   ELSE
   FOR rosterRecord IN (SELECT r.* FROM roster r WHERE lower(r.coursesectionname) = lower(old_roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id
                          AND currentschoolyear = schoolyear AND teacherid = old_teacherId)
     LOOP

	UPDATE roster set coursesectionname= new_roster_name, teacherid = new_teacherId, modifieddate=CURRENT_TIMESTAMP, modifieduser = ceteSysAdminUserId 
	            WHERE id = rosterRecord.id;

	INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ROSTER', rosterRecord.id, ceteSysAdminUserId, now(),
		'TEACHER_CHANGE', ('{"rosterId":' ||  rosterRecord.id || ',"oldEducatorId":' ||  old_teacherId || ',"newEducatorId":'  || new_teacherId || '}')::json);            

	RAISE NOTICE 'Roster with id %, changed the educator % from to new educator %', rosterRecord.id, old_teacher_uniqueCommonId, new_teacher_uniqueCommonId;

     END LOOP;
   END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
--SELECT changeEductorAndRosterNameOnRosterWithNoCourse(att_sch_displayidentifier := 'ABAES', schoolyear := 2016, subject_abbrName :='ELA' , old_teacher_uniqueCommonId := 'PriyaRao',
      --  new_teacher_uniqueCommonId:='Priya_DLMTest' ,  old_roster_name:='Priya_DLM_ELA_Test', new_roster_name :='Venky_DLM_ELA_TEST' , stateDisplayidentifier := 'MO');
      
  

  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 3.2.2: Change educator with with course.

DROP FUNCTION IF EXISTS changeEductorOnRosterWithCourse(character varying, bigint, character varying, character varying, character varying, character varying, character varying, character varying);


CREATE OR REPLACE FUNCTION changeEductorOnRosterWithCourse(att_sch_displayidentifier character varying, schoolyear bigint, 
      subject_abbrName character varying, course_Abbrname character varying, old_teacher_uniqueCommonId character varying, new_teacher_uniqueCommonId character varying, roster_name character varying, stateDisplayidentifier character varying) RETURNS VOID AS

$BODY$
   DECLARE
   state_Id BIGINT;
   att_sch_id BIGINT;
   ceteSysAdminUserId BIGINT;
   contentArea_Id BIGINT;
   subject_Id BIGINT;
   old_teacherId BIGINT;
   new_teacherId BIGINT;
   rosterRecord RECORD;
   course_Id BIGINT;
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));   
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));   
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO old_teacherId (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(old_teacher_uniqueCommonId) LIMIT 1); 
   SELECT INTO new_teacherId (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(new_teacher_uniqueCommonId) LIMIT 1); 
   SELECT INTO course_Id (SELECT id FROM gradecourse WHERE lower(abbreviatedname) = lower(course_Abbrname) and course is true LIMIT 1);
   
   IF((SELECT count(*) FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id AND r.statecoursesid = course_Id
                          AND currentschoolyear = schoolyear AND teacherid = old_teacherId)) <= 0 THEN

     RAISE NOTICE 'No rosters found with roster name: %, subject: %, course: %, attendanceschool: %, teacher: %, and school year: %  ', roster_name, subject_abbrName, course_Abbrname, att_sch_displayidentifier,
           old_teacher_uniqueCommonId, schoolyear;

   ELSE
   FOR rosterRecord IN (SELECT r.* FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id AND r.statecoursesid = course_Id
                          AND currentschoolyear = schoolyear AND teacherid = old_teacherId)
     LOOP

	UPDATE roster set teacherid = new_teacherId, modifieddate=CURRENT_TIMESTAMP, modifieduser = ceteSysAdminUserId 
	            WHERE id = rosterRecord.id;

	INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ROSTER', rosterRecord.id, ceteSysAdminUserId, now(),
		'TEACHER_CHANGE', ('{"rosterId":' ||  rosterRecord.id || ',"oldEducatorId":' ||  old_teacherId || ',"newEducatorId":'  || new_teacherId || '}')::json);

	RAISE NOTICE 'Roster with id %, changed the educator % from to new educator %', rosterRecord.id, old_teacher_uniqueCommonId, new_teacher_uniqueCommonId;

     END LOOP;
   END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
--SELECT changeEductorOnRosterWithCourse(att_sch_displayidentifier := 'ABAES', schoolyear := 2016, subject_abbrName :='ELA', course_Abbrname := 'ENG11', old_teacher_uniqueCommonId := 'PriyaRao',
      --  new_teacher_uniqueCommonId:='Priya_DLMTest' ,  roster_name:='Priya_DLM_ELA_Test',stateDisplayidentifier := 'MO');
      
  
  
  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 3.2.1: Change educator with no course.

DROP FUNCTION IF EXISTS changeEductorOnRosterWithNoCourse(character varying, bigint, character varying, character varying, character varying, character varying, character varying);


CREATE OR REPLACE FUNCTION changeEductorOnRosterWithNoCourse(att_sch_displayidentifier character varying, schoolyear bigint, 
      subject_abbrName character varying, old_teacher_uniqueCommonId character varying, new_teacher_uniqueCommonId character varying, roster_name character varying, stateDisplayidentifier character varying) RETURNS VOID AS

$BODY$
   DECLARE
   state_Id BIGINT;
   att_sch_id BIGINT;
   ceteSysAdminUserId BIGINT;
   contentArea_Id BIGINT;
   subject_Id BIGINT;
   old_teacherId BIGINT;
   new_teacherId BIGINT;
   rosterRecord RECORD;
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));   
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));   
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO old_teacherId (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(old_teacher_uniqueCommonId) LIMIT 1); 
   SELECT INTO new_teacherId (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(new_teacher_uniqueCommonId) LIMIT 1); 
   
   IF((SELECT count(*) FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id
                          AND currentschoolyear = schoolyear AND teacherid = old_teacherId)) <= 0 THEN

     RAISE NOTICE 'No rosters found with roster name: %, subject: %, attendanceschool: %, teacher: %, and school year: %  ', roster_name, subject_abbrName, att_sch_displayidentifier,
           old_teacher_uniqueCommonId, schoolyear;

   ELSE
   FOR rosterRecord IN (SELECT r.* FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id
                          AND currentschoolyear = schoolyear AND teacherid = old_teacherId)
     LOOP

	UPDATE roster set teacherid = new_teacherId, modifieddate=CURRENT_TIMESTAMP, modifieduser = ceteSysAdminUserId 
	            WHERE id = rosterRecord.id;

        INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ROSTER', rosterRecord.id, ceteSysAdminUserId, now(),
		'TEACHER_CHANGE', ('{"rosterId":' ||  rosterRecord.id || ',"oldEducatorId":' ||  old_teacherId || ',"newEducatorId":'  || new_teacherId || '}')::json);

	RAISE NOTICE 'Roster with id %, changed the educator % from to new educator %', rosterRecord.id, old_teacher_uniqueCommonId, new_teacher_uniqueCommonId;

     END LOOP;
   END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
--SELECT changeEductorOnRosterWithNoCourse(att_sch_displayidentifier := 'ABAES', schoolyear := 2016, subject_abbrName :='ELA' , old_teacher_uniqueCommonId := 'PriyaRao',
      --  new_teacher_uniqueCommonId:='Priya_DLMTest' ,  roster_name:='Priya_DLM_ELA_Test',stateDisplayidentifier := 'MO');
      
  

-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 3.1.2: Change rosterName with course.

DROP FUNCTION IF EXISTS changeRosterNameWithCourse(character varying, bigint, character varying, character varying, character varying, character varying, character varying, character varying);


CREATE OR REPLACE FUNCTION changeRosterNameWithCourse(att_sch_displayidentifier character varying, schoolyear bigint, 
      subject_abbrName character varying, course_Abbrname character varying, teacher_uniqueCommonId character varying, old_roster_name character varying, new_roster_name character varying,stateDisplayidentifier character varying) RETURNS VOID AS

$BODY$
   DECLARE
   state_Id BIGINT;
   att_sch_id BIGINT;
   ceteSysAdminUserId BIGINT;
   contentArea_Id BIGINT;
   subject_Id BIGINT;
   teacher_Id BIGINT;
   rosterRecord RECORD;
   course_Id BIGINT;
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));   
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));   
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO teacher_Id (select DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id where uso.organizationid = att_sch_id and lower(uniquecommonidentifier) = lower(teacher_uniqueCommonId) LIMIT 1); 
   SELECT INTO course_Id (SELECT id FROM gradecourse WHERE lower(abbreviatedname) = lower(course_Abbrname) and course is true LIMIT 1);
   
   IF((SELECT count(*) FROM roster r WHERE lower(r.coursesectionname) = lower(old_roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id AND r.statecoursesid = course_Id
                          AND currentschoolyear = schoolyear AND teacherid = teacher_Id)) <= 0 THEN

     RAISE NOTICE 'No rosters found with roster name: %, subject: %, course: %, attendanceschool: %, teacher: %, and school year: %  ', old_roster_name, subject_abbrName, course_Abbrname, att_sch_displayidentifier,
           teacher_uniqueCommonId, schoolyear;

   ELSE
   FOR rosterRecord IN (SELECT r.* FROM roster r WHERE lower(r.coursesectionname) = lower(old_roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id
                          AND r.statecoursesid = course_Id AND currentschoolyear = schoolyear AND teacherid = teacher_Id)
     LOOP

	UPDATE roster set coursesectionname = new_roster_name, modifieddate=CURRENT_TIMESTAMP, modifieduser = ceteSysAdminUserId 
	            WHERE id = rosterRecord.id;

	RAISE NOTICE 'Roster with id % name changed to %', rosterRecord.id, new_roster_name;

     END LOOP;
   END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
--SELECT changeRosterNameWithCourse(att_sch_displayidentifier := 'ABAES' , schoolyear := 2016, 
      --subject_abbrName := 'ELA', course_Abbrname := 'ENG11', teacher_uniqueCommonId :='89234', old_roster_name :='ELA 11 LockDown', new_roster_name :='ELA 11 LockDown_Name_Change',stateDisplayidentifier :='MO');
      
  


-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 3.1.1: Change rosterName with no course.

DROP FUNCTION IF EXISTS changeRosterNameWithNoCourse(character varying, bigint, character varying, character varying, character varying, character varying, character varying);


CREATE OR REPLACE FUNCTION changeRosterNameWithNoCourse(att_sch_displayidentifier character varying, schoolyear bigint, 
      subject_abbrName character varying, teacher_uniqueCommonId character varying, old_roster_name character varying, new_roster_name character varying,stateDisplayidentifier character varying) RETURNS VOID AS

$BODY$
   DECLARE
   state_Id BIGINT;
   att_sch_id BIGINT;
   ceteSysAdminUserId BIGINT;
   contentArea_Id BIGINT;
   subject_Id BIGINT;
   teacher_Id BIGINT;
   rosterRecord RECORD;
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));   
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));   
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO teacher_Id (select DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id where uso.organizationid = att_sch_id and lower(uniquecommonidentifier) = lower(teacher_uniqueCommonId) LIMIT 1); 

   IF((SELECT count(*) FROM roster r WHERE lower(r.coursesectionname) = lower(old_roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id
                          AND currentschoolyear = schoolyear AND teacherid = teacher_Id)) <= 0 THEN

     RAISE NOTICE 'No rosters found with roster name: %, subject: %, attendanceschool: %, teacher: %, and school year: %  ', old_roster_name, subject_abbrName, att_sch_displayidentifier,
           teacher_uniqueCommonId, schoolyear;

   ELSE
   FOR rosterRecord IN (SELECT r.* FROM roster r WHERE lower(r.coursesectionname) = lower(old_roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id
                          AND currentschoolyear = schoolyear AND teacherid = teacher_Id)
     LOOP

	UPDATE roster set coursesectionname = new_roster_name, modifieddate=CURRENT_TIMESTAMP, modifieduser = ceteSysAdminUserId 
	            WHERE id = rosterRecord.id;

	RAISE NOTICE 'Roster with id % name changed to %', rosterRecord.id, new_roster_name;

     END LOOP;
   END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
--SELECT changeRosterNameWithNoCourse(att_sch_displayidentifier := 'ABAES', schoolyear := 2016, subject_abbrName :='ELA' , teacher_uniqueCommonId := 'PriyaRao',
      -- old_roster_name :='Priya_DLMTest' , new_roster_name :='Priya_DLM_ELA_Test',stateDisplayidentifier := 'MO');
      
  
  
  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario : Subject change on roster with no Course.

DROP FUNCTION IF EXISTS changeSubjectOnRosterWithNoCourse(character varying, character varying, character varying, character varying, character varying, bigint, character varying);


CREATE OR REPLACE FUNCTION changeSubjectOnRosterWithNoCourse(att_sch_displayidentifier character varying, old_subject_abbrName character varying, new_subject_abbrName character varying,
        teacher_uniqueCommonId character varying, roster_name character varying, schoolyear bigint, stateDisplayidentifier character varying) RETURNS VOID AS

$BODY$
   DECLARE
   state_Id BIGINT;
   att_sch_id BIGINT;   
   ceteSysAdminUserId BIGINT;   
   old_subject_Id BIGINT;
   new_subject_Id BIGINT;
   teacher_Id BIGINT;   
   rosterRecord RECORD;
   studentsTestsRecords RECORD;     
   enrl_Id BIGINT;
   rosterUnEnrolledStuTestSecsStatus BIGINT;
   rosterUnEnrolledStuTestsStatus BIGINT; 
   pendingStuTestsStatus BIGINT;
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));   
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));   
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO old_subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(old_subject_abbrName) LIMIT 1);
   SELECT INTO new_subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(new_subject_abbrName) LIMIT 1);
   SELECT INTO teacher_Id (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(teacher_uniqueCommonId) LIMIT 1);
   SELECT INTO pendingStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending');
   SELECT INTO rosterUnEnrolledStuTestSecsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'rosterunenrolled');
   SELECT INTO rosterUnEnrolledStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'rosterunenrolled');
   
   IF((SELECT count(*) FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = old_subject_Id
                          AND currentschoolyear = schoolyear AND teacherid = teacher_Id)) <= 0 THEN

     RAISE NOTICE 'No rosters found with roster name: %, subject: %, attendanceschool: %, teacher: %, and school year: %  ', roster_name, old_subject_abbrName, att_sch_displayidentifier,
          teacher_uniqueCommonId, schoolyear;

   ELSE
      FOR rosterRecord IN(SELECT r.* FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = old_subject_Id 
                          AND currentschoolyear = schoolyear AND teacherid = teacher_Id)
       LOOP
       
           UPDATE roster SET statesubjectareaid = new_subject_Id, modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = rosterRecord.id;

           INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ROSTER', rosterRecord.id, ceteSysAdminUserId, now(),
		'SUBJECT_COURSE_CHANGE', ('{"rosterId":' || rosterRecord.id || ',"oldSubjectId":' || old_subject_Id || ',"newSubjectAreaId":' || new_subject_Id || '}')::json);
           
        FOR studentsTestsRecords IN(SELECT st.id, st.studentid, st.testid, st.testcollectionid, st.testsessionid, st.status,st.enrollmentid 
                FROM studentstests st JOIN testsession ts ON st.testsessionid = ts.id JOIN operationaltestwindow otw on otw.id = ts.operationaltestwindowid 
		 WHERE st.activeflag=true AND ts.rosterid = rosterRecord.id AND (otw.effectivedate <= now() AND now() <= otw.expirydate))
        LOOP

            PERFORM inActivateStuTestsTrackerITITestsessions(studentTestsId := studentsTestsRecords.id, inActiveStuTestSecStatusId := rosterUnEnrolledStuTestSecsStatus, 
	            inActiveStuTestStatusId := rosterUnEnrolledStuTestsStatus, testsession_Id := studentsTestsRecords.testsessionid, student_Id :=studentsTestsRecords.studentid);
		
        END LOOP;
	 UPDATE ititestsessionhistory SET activeflag = false, modifieddate = now(), modifieduser = ceteSysAdminUserId, status = rosterUnEnrolledStuTestsStatus
	     WHERE studentenrlrosterid IN (SELECT id FROM enrollmentsrosters WHERE rosterid = rosterRecord.id)
             AND status = pendingStuTestsStatus AND activeflag IS true;
      END LOOP;
   END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
--SELECT changeSubjectOnRosterWithNoCourse(att_sch_displayidentifier := , old_subject_abbrName := , new_subject_abbrName := ,
      --  teacher_uniqueCommonId := , roster_name := , schoolyear := , stateDisplayidentifier :=)
      
  
  
  
  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario : Subject or course change change on roster with Course input.

DROP FUNCTION IF EXISTS changeSubOrCourseOnRosterWithCourse(character varying, character varying, character varying, character varying, character varying,
        character varying, character varying, bigint,  character varying);


CREATE OR REPLACE FUNCTION changeSubOrCourseOnRosterWithCourse(att_sch_displayidentifier character varying, old_subject_abbrName character varying, new_subject_abbrName character varying, old_course_abbrName character varying, new_course_abbrName character varying,
        teacher_uniqueCommonId character varying, roster_name character varying, schoolyear bigint, stateDisplayidentifier character varying) RETURNS VOID AS

$BODY$
   DECLARE
   state_Id BIGINT;
   att_sch_id BIGINT;   
   ceteSysAdminUserId BIGINT;   
   old_subject_Id BIGINT;
   new_subject_Id BIGINT;
   teacher_Id BIGINT;   
   rosterRecord RECORD;
   old_course_Id BIGINT;
   new_course_id BIGINT;
   studentsTestsRecords RECORD;     
   enrl_Id BIGINT;
   rosterUnEnrolledStuTestSecsStatus BIGINT;
   rosterUnEnrolledStuTestsStatus BIGINT; 
   pendingStuTestsStatus BIGINT;
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));   
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));   
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO old_subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(old_subject_abbrName) LIMIT 1);
   SELECT INTO new_subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(new_subject_abbrName) LIMIT 1);
   SELECT INTO teacher_Id (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(teacher_uniqueCommonId) LIMIT 1);
   SELECT INTO pendingStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending');
   SELECT INTO rosterUnEnrolledStuTestSecsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'rosterunenrolled');
   SELECT INTO rosterUnEnrolledStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'rosterunenrolled');
   SELECT INTO old_course_Id (SELECT id FROM gradecourse WHERE lower(abbreviatedname) = lower(old_course_abbrName) and course is true LIMIT 1);
   SELECT INTO new_course_id (SELECT id FROM gradecourse WHERE lower(abbreviatedname) = lower(new_course_abbrName) and course is true LIMIT 1);
   
   IF((SELECT count(*) FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = old_subject_Id
                          AND currentschoolyear = schoolyear AND teacherid = teacher_Id AND r.statecoursesid = old_course_Id)) <= 0 THEN

     RAISE NOTICE 'No rosters found with roster name: %, subject: %, course %, attendanceschool: %, teacher: %, and school year: %  ', roster_name, old_subject_abbrName, att_sch_displayidentifier,
          teacher_uniqueCommonId, schoolyear;

   ELSE
      FOR rosterRecord IN(SELECT r.* FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = old_subject_Id 
                          AND currentschoolyear = schoolyear AND teacherid = teacher_Id AND r.statecoursesid = old_course_Id)
       LOOP
       
           UPDATE roster SET statesubjectareaid = new_subject_Id, statecoursesid =new_course_id, statecoursecode = new_course_abbrName,  modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = rosterRecord.id;

           INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ROSTER', rosterRecord.id, ceteSysAdminUserId, now(),
		'SUBJECT_COURSE_CHANGE', ('{"rosterId":' || rosterRecord.id || ',"oldSubjectId":' || old_subject_Id || ',"newSubjectAreaId":' || new_subject_Id || ',"oldCourseId":' || old_course_Id
		         || ',"newCourseId":' || new_course_id ||'}')::json);
           
        FOR studentsTestsRecords IN(SELECT st.id, st.studentid, st.testid, st.testcollectionid, st.testsessionid, st.status,st.enrollmentid 
                FROM studentstests st JOIN testsession ts ON st.testsessionid = ts.id JOIN operationaltestwindow otw on otw.id = ts.operationaltestwindowid 
		 WHERE st.activeflag=true AND ts.rosterid = rosterRecord.id AND (otw.effectivedate <= now() AND now() <= otw.expirydate))
        LOOP

            PERFORM inActivateStuTestsTrackerITITestsessions(studentTestsId := studentsTestsRecords.id, inActiveStuTestSecStatusId := rosterUnEnrolledStuTestSecsStatus, 
	            inActiveStuTestStatusId := rosterUnEnrolledStuTestsStatus, testsession_Id := studentsTestsRecords.testsessionid, student_Id :=studentsTestsRecords.studentid);
		
        END LOOP;
	 UPDATE ititestsessionhistory SET activeflag = false, modifieddate = now(), modifieduser = ceteSysAdminUserId, status = rosterUnEnrolledStuTestsStatus
	     WHERE studentenrlrosterid IN (SELECT id FROM enrollmentsrosters WHERE rosterid = rosterRecord.id)
             AND status = pendingStuTestsStatus AND activeflag IS true;
      END LOOP;
   END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
-- SELECT changeSubOrCourseOnRosterWithCourse(att_sch_displayidentifier := , old_subject_abbrName := , new_subject_abbrName := , old_course_abbrName := , new_course_abbrName := ,
       -- teacher_uniqueCommonId := , roster_name := , schoolyear := , stateDisplayidentifier := );
       
  
  
  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 4.1: Creating new roster with only subject.

DROP FUNCTION IF EXISTS createNewRosterWithNoCourse(character varying[], character varying, character varying, bigint, character varying, character varying, character varying, character varying);


CREATE OR REPLACE FUNCTION createNewRosterWithNoCourse(state_student_identifiers character varying[], att_sch_displayidentifier character varying, ayp_sch_displayidentifier character varying, schoolyear bigint, 
      subject_abbrName character varying, teacher_uniqueCommonId character varying, roster_name character varying, stateDisplayidentifier character varying) RETURNS TEXT AS

$BODY$
   DECLARE
   state_Id BIGINT;
   att_sch_id BIGINT;
   ayp_sch_id BIGINT;
   ceteSysAdminUserId BIGINT;
   contentArea_Id BIGINT;
   subject_Id BIGINT;
   teacher_Id BIGINT;   
   rosterRecord RECORD;
   enrollmentRecord RECORD;
   enrlRecords RECORD;   
   enrl_Id BIGINT;
   roster_Id BIGINT;  
   error_msg TEXT;
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));   
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));
   SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(ayp_sch_displayidentifier));
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO teacher_Id (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(teacher_uniqueCommonId) LIMIT 1);
   error_msg := '';
   
   IF((SELECT count(*) FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id
                          AND currentschoolyear = schoolyear AND teacherid = teacher_Id)) <= 0 THEN

     RAISE NOTICE 'No rosters found with roster name: %, subject: %, attendanceschool: %, teacher: %, and school year: %, So creating new roster.  ', 
                  roster_name, subject_abbrName, att_sch_displayidentifier, teacher_uniqueCommonId, schoolyear;

     IF(teacher_Id is null) THEN
     
         RAISE NOTICE 'Teacher % is not found in the organization %', teacher_uniqueCommonId, att_sch_displayidentifier;
         
         error_msg := '<error>' || 'Teacher ' || teacher_uniqueCommonId || ' is not found in school ' || att_sch_displayidentifier;

     ELSE
          INSERT INTO roster(coursesectionname, teacherid, attendanceSchoolId, statesubjectareaid, restrictionid, createddate, createduser, activeflag, modifieddate, modifieduser,
		educatorschooldisplayidentifier, sourcetype, currentSchoolYear, aypSchoolId) 
		VALUES(roster_name, teacher_Id, att_sch_id, subject_Id, 1, now(), ceteSysAdminUserId, true, now(), ceteSysAdminUserId,att_sch_displayidentifier,
                'LOCK_DOWN_SCRIPT', schoolyear, ayp_sch_id) RETURNING id INTO roster_Id;

	FOR enrollmentRecord IN(SELECT stu.statestudentidentifier,en.* FROM enrollment en JOIN student stu ON stu.id = en.studentid AND en.currentschoolyear = schoolyear AND en.aypschoolid = ayp_sch_id AND en.attendanceschoolid = att_sch_id
          AND stu.statestudentidentifier = ANY(state_student_identifiers) AND stu.stateid = state_Id)
        LOOP

          INSERT INTO enrollmentsrosters(enrollmentid, rosterid, createddate, createduser, activeflag, modifieddate, modifieduser) 
                      VALUES (enrollmentRecord.id, roster_Id, now(), ceteSysAdminUserId, true, now(), ceteSysAdminUserId) RETURNING id INTO enrl_Id;

          INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrl_Id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || roster_Id || ', "enrollmentId":' ||  enrollmentRecord.id || ',"enrollmentRosterId":' || enrl_Id || '}')::json);

           RAISE NOTICE 'Student % is added to the roster %', enrollmentRecord.statestudentidentifier, roster_name;

            error_msg :=  '<success>' || 'Student ' || enrollmentRecord.statestudentidentifier || ' is added to the roster ' || roster_name || ' ' || error_msg;  

           PERFORM moveCompletedTestsAndResetSTWithNoCourse(student_id := enrollmentRecord.studentid,enrollment_id := enrollmentRecord.id, 
                 subjectArea_id := subject_Id, new_roster_id := roster_Id, school_year := schoolyear, attendance_schId := att_sch_id);
          
        END LOOP;
     END IF;     
   ELSE
    FOR enrollmentRecord IN(SELECT stu.statestudentidentifier, en.* FROM enrollment en JOIN student stu ON stu.id = en.studentid AND en.currentschoolyear = schoolyear AND en.aypschoolid = ayp_sch_id AND en.attendanceschoolid = att_sch_id
         AND stu.statestudentidentifier = ANY(state_student_identifiers) AND stu.stateid = state_Id)
     LOOP
        FOR rosterRecord IN(SELECT r.* FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id 
                        AND currentschoolyear = schoolyear AND teacherid = teacher_Id)
	 LOOP
           IF((SELECT count(*) FROM enrollmentsrosters WHERE enrollmentid = enrollmentRecord.id AND rosterid = rosterRecord.id) <= 0) THEN            

	       INSERT INTO enrollmentsrosters(enrollmentid, rosterid, createddate, createduser, activeflag, modifieddate, modifieduser) 
                      VALUES (enrollmentRecord.id, rosterRecord.id, now(), ceteSysAdminUserId, true, now(), ceteSysAdminUserId) RETURNING id INTO enrl_Id;

               INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrl_Id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || rosterRecord.id || ', "enrollmentId":' ||  enrollmentRecord.id || ',"enrollmentRosterId":' || enrl_Id || '}')::json);

	      RAISE NOTICE 'Student % is added to the roster %', enrollmentRecord.statestudentidentifier, roster_name;

	      error_msg :=  '<success>' || 'Student ' || enrollmentRecord.statestudentidentifier || ' is added to the roster ' || roster_name || ', ' || error_msg;

	      PERFORM moveCompletedTestsAndResetSTWithNoCourse(student_id := enrollmentRecord.studentid,enrollment_id := enrollmentRecord.id, subjectArea_id := subject_Id, 
	                 new_roster_id := rosterRecord.id, school_year := schoolyear, attendance_schId := att_sch_id);
	      
	   ELSE		      
	      FOR enrlRecords IN (SELECT * FROM enrollmentsrosters WHERE enrollmentid = enrollmentRecord.id AND rosterid = rosterRecord.id LIMIT 1) 
	        LOOP                    
                  UPDATE enrollmentsrosters SET activeflag = true, modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = enrlRecords.id;

	          INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrlRecords.id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || enrlRecords.rosterid || ', "enrollmentId":' ||  enrlRecords.enrollmentid || ',"enrollmentRosterId":' || enrlRecords.id || '}')::json);

                 error_msg :=  '<success>' || 'enrollmentid ' || enrlRecords.enrollmentid  || ' is added to the roster ' || roster_name || ', ' || error_msg;		      

		 PERFORM moveCompletedTestsAndResetSTWithNoCourse(student_id := enrollmentRecord.studentid,enrollment_id := enrollmentRecord.id, subjectArea_id := subject_Id, 
		             new_roster_id := rosterRecord.id, school_year := schoolyear, attendance_schId := att_sch_id);
		
	      END LOOP;
	   END IF;	   
        END LOOP;        
     END LOOP;
   END IF;
RETURN error_msg;   
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
--SELECT createNewRosterWithNoCourse(state_student_identifiers := array['987654', '1234'], att_sch_displayidentifier := '1234', ayp_sch_displayidentifier := '1235', schoolyear := 2016, 
      -- subject_abbrName := 'ELA', teacher_uniqueCommonId := '67576', roster_name := 'ELA Roster', stateDisplayidentifier := 'OK')
      
-- or
--SELECT createNewRosterWithNoCourse(state_student_identifiers := array['987654'], att_sch_displayidentifier := '1234', ayp_sch_displayidentifier := '1235', schoolyear := 2016, 
      -- subject_abbrName := 'ELA', teacher_uniqueCommonId := '67576', roster_name := 'ELA Roster', stateDisplayidentifier := 'OK')  
      
  
  
  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 4.2: Creating new roster with subject and course combination.

DROP FUNCTION IF EXISTS createNewRosterWithSubAndCourse(character varying[], character varying, character varying, bigint, character varying, character varying, character varying, character varying, character varying);


CREATE OR REPLACE FUNCTION createNewRosterWithSubAndCourse(state_student_identifiers character varying[], att_sch_displayidentifier character varying, ayp_sch_displayidentifier character varying, schoolyear bigint, 
      subject_abbrName character varying, course_abbrName character varying, teacher_uniqueCommonId character varying, roster_name character varying, stateDisplayidentifier character varying) RETURNS TEXT AS

$BODY$
   DECLARE
   state_Id BIGINT;
   att_sch_id BIGINT;
   ayp_sch_id BIGINT;
   ceteSysAdminUserId BIGINT;
   contentArea_Id BIGINT;
   subject_Id BIGINT;
   teacher_Id BIGINT;   
   rosterRecord RECORD;
   enrollmentRecord RECORD;
   enrlRecords RECORD;   
   enrl_Id BIGINT;
   roster_Id BIGINT;   
   course_Id BIGINT;
   error_msg TEXT;
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));   
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));
   SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(ayp_sch_displayidentifier));
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO teacher_Id (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(teacher_uniqueCommonId) LIMIT 1);
   SELECT INTO course_Id (SELECT id FROM gradecourse WHERE abbreviatedname = course_abbrName LIMIT 1);
   error_msg := '';
   
   IF((SELECT count(*) FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id
                       AND r.statecoursesid = course_Id AND currentschoolyear = schoolyear AND teacherid = teacher_Id)) <= 0 THEN

     RAISE NOTICE 'No rosters found with roster name: %, subject: %, course: %, attendanceschool: %, teacher: %, and school year: %, So creating new roster.  ', 
                  roster_name, subject_abbrName, course_abbrName, att_sch_displayidentifier, teacher_uniqueCommonId, schoolyear;      

     IF(teacher_Id is null) THEN

         RAISE NOTICE 'Teacher % is not found in the organization %', teacher_uniqueCommonId, att_sch_displayidentifier;
         
         error_msg := '<error>' || 'Teacher ' || teacher_uniqueCommonId || ' is not found in school ' || att_sch_displayidentifier;

     ELSE
       INSERT INTO roster(coursesectionname, teacherid, attendanceSchoolId, statesubjectareaid, restrictionid, createddate, createduser, activeflag, modifieddate, modifieduser,
		educatorschooldisplayidentifier, sourcetype, currentSchoolYear, aypSchoolId, statecoursecode, statecoursesid) 
	  VALUES(roster_name, teacher_Id, att_sch_id, subject_Id, 1, now(), ceteSysAdminUserId, true, now(), ceteSysAdminUserId,att_sch_displayidentifier,
                'LOCK_DOWN_SCRIPT', schoolyear, ayp_sch_id, course_abbrName, course_Id) RETURNING id INTO roster_Id;

       FOR enrollmentRecord IN(SELECT stu.statestudentidentifier,en.* FROM enrollment en JOIN student stu ON stu.id = en.studentid AND en.currentschoolyear = schoolyear AND en.aypschoolid = ayp_sch_id AND en.attendanceschoolid = att_sch_id
          AND stu.statestudentidentifier = ANY(state_student_identifiers) AND stu.stateid = state_Id)
       LOOP

          INSERT INTO enrollmentsrosters(enrollmentid, rosterid, createddate, createduser, activeflag, modifieddate, modifieduser) 
                      VALUES (enrollmentRecord.id, roster_Id, now(), ceteSysAdminUserId, true, now(), ceteSysAdminUserId) RETURNING id INTO enrl_Id;

          INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrl_Id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || roster_Id || ', "enrollmentId":' ||  enrollmentRecord.id || ',"enrollmentRosterId":' || enrl_Id || '}')::json);

          RAISE NOTICE 'Student % is added to the roster %', enrollmentRecord.statestudentidentifier, roster_name;

          error_msg :=  '<success>' || 'Student ' || enrollmentRecord.statestudentidentifier || ' is added to the roster ' || roster_name || ' ' || error_msg;

          PERFORM moveCompletedTestsAndResetSTWithCourse(student_id := enrollmentRecord.studentid,enrollment_id := enrollmentRecord.id, subjectArea_id := subject_Id, 
                 course_Id := course_Id, new_roster_id := roster_Id, school_year := schoolyear, attendance_schId := att_sch_id);
          
       END LOOP; 
    END IF;
     
   ELSE
    FOR enrollmentRecord IN(SELECT stu.statestudentidentifier, en.* FROM enrollment en JOIN student stu ON stu.id = en.studentid AND en.currentschoolyear = schoolyear AND en.aypschoolid = ayp_sch_id AND en.attendanceschoolid = att_sch_id
         AND stu.statestudentidentifier = ANY(state_student_identifiers) AND stu.stateid = state_Id)
     LOOP
        FOR rosterRecord IN(SELECT r.* FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id 
                   AND r.statecoursesid = course_Id AND currentschoolyear = schoolyear AND teacherid = teacher_Id)
	 LOOP
           IF((SELECT count(*) FROM enrollmentsrosters WHERE enrollmentid = enrollmentRecord.id AND rosterid = rosterRecord.id) <= 0) THEN            

	       INSERT INTO enrollmentsrosters(enrollmentid, rosterid, createddate, createduser, activeflag, modifieddate, modifieduser) 
                      VALUES (enrollmentRecord.id, rosterRecord.id, now(), ceteSysAdminUserId, true, now(), ceteSysAdminUserId) RETURNING id INTO enrl_Id;

               INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrl_Id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || rosterRecord.id || ', "enrollmentId":' ||  enrollmentRecord.id || ',"enrollmentRosterId":' || enrl_Id || '}')::json);

	      RAISE NOTICE 'Student % is added to the roster %', enrollmentRecord.statestudentidentifier, roster_name;

	      error_msg :=  '<success>' || 'Student ' || enrollmentRecord.statestudentidentifier || ' is added to the roster ' || roster_name || ', ' || error_msg;

	      PERFORM moveCompletedTestsAndResetSTWithCourse(student_id := enrollmentRecord.studentid,enrollment_id := enrollmentRecord.id, subjectArea_id := subject_Id, 
	               course_Id := course_Id, new_roster_id := roster_Id, school_year := schoolyear, attendance_schId := att_sch_id);

	      
	   ELSE		      
	      FOR enrlRecords IN (SELECT * FROM enrollmentsrosters WHERE enrollmentid = enrollmentRecord.id AND rosterid = rosterRecord.id LIMIT 1) 
	        LOOP                    
                  UPDATE enrollmentsrosters SET activeflag = true, modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = enrlRecords.id;

	          INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrlRecords.id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || enrlRecords.rosterid || ', "enrollmentId":' ||  enrlRecords.enrollmentid || ',"enrollmentRosterId":' || enrlRecords.id || '}')::json);

		  error_msg :=  '<success>' || 'enrollmentid ' || enrlRecords.enrollmentid  || ' is added to the roster ' || roster_name || ', ' || error_msg;

		 PERFORM moveCompletedTestsAndResetSTWithCourse(student_id := enrollmentRecord.studentid,enrollment_id := enrollmentRecord.id, subjectArea_id := subject_Id, 
	               course_Id := course_Id, new_roster_id := roster_Id, school_year := schoolyear, attendance_schId := att_sch_id);
		
	      END LOOP;
	   END IF;	   
        END LOOP;        
     END LOOP;
   END IF;
RETURN error_msg;   
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
--SELECT createNewRosterWithSubAndCourse(state_student_identifiers := array['987654', '1234'], att_sch_displayidentifier := '09I027140', ayp_sch_displayidentifier := '09I027140', schoolyear := 2016, 
  --    subject_abbrName := 'ELA', course_abbrName := 'ENG9', teacher_uniqueCommonId := '1200000010', roster_name := 'Created by Lock down data script Roster', stateDisplayidentifier :='OK' );
      
---or
--SELECT createNewRosterWithSubAndCourse(state_student_identifiers := array['987654'], att_sch_displayidentifier := '09I027140', ayp_sch_displayidentifier := '09I027140', schoolyear := 2016, 
  --    subject_abbrName := 'ELA', course_abbrName := 'ENG9', teacher_uniqueCommonId := '1200000010', roster_name := 'Created by Lock down data script Roster', stateDisplayidentifier :='OK' );

  
  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario: 1.2 Removing all students from roster. With course code.
DROP FUNCTION IF EXISTS removeAllStudentsFromRosterWithCourse(character varying, character varying, character varying, bigint, character varying, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION removeAllStudentsFromRosterWithCourse(ayp_sch_displayidentifier character varying, att_sch_displayidentifier character varying, stateDisplayidentifier character varying, 
              schoolyear bigint, subject_abbrName character varying, course_Abbrname character varying, teacher_uniqueCommonId character varying, roster_name character varying) RETURNS TEXT AS

$BODY$ 
 DECLARE
   state_Id BIGINT;
   ayp_sch_id BIGINT;
   att_sch_id BIGINT;
   ceteSysAdminUserId BIGINT;
   contentArea_Id BIGINT;
   rosterUnEnrolledStuTestSecsStatus BIGINT;   
   rosterUnEnrolledStuTestsStatus BIGINT;
   inProgressStuTestsStatus BIGINT;
   pendingStuTestsStatus BIGINT;
   unusedStuTestsStatus BIGINT;
   course_Id BIGINT;
   subject_Id BIGINT;
   teacher_Id BIGINT;   
   studentsEnrollemntsRostersRecord RECORD;
   stuTestsRecordsInprgsPenUnusedStatus RECORD;
   error_msg TEXT;
       
 BEGIN 
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));
   SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(ayp_sch_displayidentifier));
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));
   SELECT INTO rosterUnEnrolledStuTestSecsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'rosterunenrolled');
   SELECT INTO rosterUnEnrolledStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'rosterunenrolled');
   SELECT INTO inProgressStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress');
   SELECT INTO pendingStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending');
   SELECT INTO unusedStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused');   
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');
   SELECT INTO course_Id (SELECT id FROM gradecourse WHERE lower(abbreviatedname) = lower(course_Abbrname) and course is true LIMIT 1);
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO teacher_Id (select DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id where uso.organizationid = att_sch_id and lower(uniquecommonidentifier) = lower(teacher_uniqueCommonId) LIMIT 1);
   error_msg := '';

   IF((SELECT count(*) FROM roster WHERE teacherid = teacher_Id AND statecoursesid = course_Id AND statesubjectareaid = subject_Id AND attendanceschoolid = att_sch_id
          AND currentschoolyear = schoolyear AND lower(coursesectionname) = lower(roster_name)) = 0) THEN

        RAISE NOTICE 'Roster % not found with subject % , course %, teacher %, attendance school % in school year %', roster_name, subject_abbrName, course_Abbrname, teacher_uniqueCommonId, 
                      att_sch_displayidentifier, schoolyear;

        error_msg := '<error>' || 'No rosters found with name ' || roster_name || ' ,subject ' || subject_abbrName || ' , course ' || course_Abbrname || ' , teacher ' || teacher_uniqueCommonId
                     || ' , attendance school ' || att_sch_displayidentifier || ' and school year ' || schoolyear || ' cobination.';
         
   ELSE
    FOR studentsEnrollemntsRostersRecord IN (SELECT stu.statestudentidentifier, stu.id as studentId, en.id as enrollmentid, enrl.id as enrlRosterId, enrl.rosterId as rosterId 
           FROM student stu JOIN enrollment en on stu.id = en.studentid JOIN enrollmentsrosters enrl on enrl.enrollmentid = en.id 
           WHERE enrl.rosterid IN (SELECT r.id FROM roster r 
               WHERE r.statecoursesid = course_Id AND r.teacherid = teacher_id AND r.statesubjectareaid = subject_Id AND r.attendanceschoolid = att_sch_id AND currentschoolyear = schoolyear AND lower(r.coursesectionname) = lower(roster_name))
               AND en.attendanceschoolid = att_sch_id AND en.aypschoolid = ayp_sch_id AND en.currentschoolyear = schoolyear AND stu.stateid = state_Id)
        LOOP
          UPDATE enrollmentsrosters SET activeflag = false,modifieddate=CURRENT_TIMESTAMP, modifieduser = ceteSysAdminUserId 
                  WHERE enrollmentid = studentsEnrollemntsRostersRecord.enrollmentid AND rosterid = studentsEnrollemntsRostersRecord.rosterId
                      AND id = studentsEnrollemntsRostersRecord.enrlRosterId;

           RAISE NOTICE 'Student(%) with Id : % is removed from the rosterId: %  enrollmentrosterId: % ', studentsEnrollemntsRostersRecord.statestudentidentifier, studentsEnrollemntsRostersRecord.studentId, 
                       studentsEnrollemntsRostersRecord.rosterId, studentsEnrollemntsRostersRecord.enrlRosterId;

           error_msg := '<success> Student '|| studentsEnrollemntsRostersRecord.statestudentidentifier || ' is removed from roster ' || roster_name ||' ' || error_msg;                       
                        
           INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', studentsEnrollemntsRostersRecord.enrlRosterId, ceteSysAdminUserId, now(),
		'RM_STUDENT_FROM_ROSTER', ('{"rosterId":' || studentsEnrollemntsRostersRecord.rosterId  || ',"enrollmentId":' 
				|| studentsEnrollemntsRostersRecord.enrollmentid || ',"enrollmentRosterId":' || studentsEnrollemntsRostersRecord.enrlRosterId || '}')::json);
          
           FOR stuTestsRecordsInprgsPenUnusedStatus IN (SELECT st.id, st.studentid, st.testid, st.testcollectionid, st.testsessionid, st.status,st.enrollmentid 
                FROM studentstests st JOIN testsession ts ON st.testsessionid = ts.id JOIN operationaltestwindow otw on otw.id = ts.operationaltestwindowid 
		 WHERE st.activeflag=true AND ts.rosterid = studentsEnrollemntsRostersRecord.rosterId
                AND st.enrollmentid = studentsEnrollemntsRostersRecord.enrollmentid AND (otw.effectivedate <= now() AND now() <= otw.expirydate)
                AND st.status in (inProgressStuTestsStatus, pendingStuTestsStatus, unusedStuTestsStatus))
	    LOOP
	        PERFORM inActivateStuTestsTrackerITITestsessions(studentTestsId := stuTestsRecordsInprgsPenUnusedStatus.id, inActiveStuTestSecStatusId := rosterUnEnrolledStuTestSecsStatus, 
	            inActiveStuTestStatusId := rosterUnEnrolledStuTestsStatus, testsession_Id := stuTestsRecordsInprgsPenUnusedStatus.testsessionid, student_Id :=stuTestsRecordsInprgsPenUnusedStatus.studentid);
            END LOOP;
      UPDATE ititestsessionhistory SET activeflag = false, modifieddate = now(), modifieduser = ceteSysAdminUserId, status = rosterUnEnrolledStuTestsStatus
	     WHERE studentenrlrosterid IN (SELECT id FROM enrollmentsrosters WHERE enrollmentid = studentsEnrollemntsRostersRecord.enrollmentid  and rosterid = studentsEnrollemntsRostersRecord.rosterId)
             AND status = pendingStuTestsStatus AND activeflag IS true;
   END LOOP;
END IF;
RETURN error_msg;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;

-- Sample function execution
-- SELECT removeAllStudentsFromRosterWithCourse(ayp_sch_displayidentifier := 'abaes' , att_sch_displayidentifier := 'abaes' , stateDisplayidentifier := 'mo' , schoolyear := 2016 , 
         --subject_abbrName := 'ELA' , course_Abbrname := 'ENG11' , teacher_uniqueCommonId :='89234' , roster_name:='ELA 11 LockDown');
         
  
  

-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario: 1.4 Removing all students from roster. No course code.
DROP FUNCTION IF EXISTS removeAllStudentsFromRosterWithNoCourse(character varying, character varying, character varying, bigint, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION removeAllStudentsFromRosterWithNoCourse(ayp_sch_displayidentifier character varying, att_sch_displayidentifier character varying, stateDisplayidentifier character varying, 
              schoolyear bigint, subject_abbrName character varying, teacher_uniqueCommonId character varying, roster_name character varying) RETURNS TEXT AS

$BODY$ 
 DECLARE
   state_Id BIGINT;
   ayp_sch_id BIGINT;
   att_sch_id BIGINT;
   ceteSysAdminUserId BIGINT;
   contentArea_Id BIGINT;
   rosterUnEnrolledStuTestSecsStatus BIGINT;   
   rosterUnEnrolledStuTestsStatus BIGINT;
   inProgressStuTestsStatus BIGINT;
   pendingStuTestsStatus BIGINT;
   unusedStuTestsStatus BIGINT;
   course_Id BIGINT;
   subject_Id BIGINT;
   teacher_Id BIGINT;   
   studentsEnrollemntsRostersRecord RECORD;
   stuTestsRecordsInprgsPenUnusedStatus RECORD;
   error_msg TEXT;
       
 BEGIN 
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));
   SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(ayp_sch_displayidentifier));
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));
   SELECT INTO rosterUnEnrolledStuTestSecsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'rosterunenrolled');
   SELECT INTO rosterUnEnrolledStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'rosterunenrolled');
   SELECT INTO inProgressStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress');
   SELECT INTO pendingStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending');
   SELECT INTO unusedStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused');   
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO teacher_Id (select DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id where uso.organizationid = att_sch_id and lower(uniquecommonidentifier) = lower(teacher_uniqueCommonId) LIMIT 1); 	   
   error_msg := '';

   IF((SELECT count(r.*) FROM roster r WHERE r.teacherid = teacher_id AND r.statesubjectareaid = subject_Id 
         AND r.attendanceschoolid = att_sch_id AND currentschoolyear = schoolyear AND lower(r.coursesectionname) = lower(roster_name)) = 0) THEN

          RAISE NOTICE 'Roster % not found with subject % , teacher %, attendance school % in school year %', roster_name, subject_abbrName, teacher_uniqueCommonId, 
                      att_sch_displayidentifier, schoolyear;
                      
         error_msg := '<error>' || 'No rosters found with name ' || roster_name || ' ,subject ' || subject_abbrName || ' , teacher ' || teacher_uniqueCommonId
                     || ' , attendance school ' || att_sch_displayidentifier || ' and school year ' || schoolyear || ' cobination.';

   ELSE   
     FOR studentsEnrollemntsRostersRecord IN (SELECT stu.statestudentidentifier, stu.id as studentId, en.id as enrollmentid, enrl.id as enrlRosterId, enrl.rosterId as rosterId 
           FROM student stu JOIN enrollment en on stu.id = en.studentid JOIN enrollmentsrosters enrl on enrl.enrollmentid = en.id 
           WHERE enrl.rosterid IN (SELECT r.id FROM roster r 
               WHERE r.teacherid = teacher_id AND r.statesubjectareaid = subject_Id AND r.attendanceschoolid = att_sch_id AND currentschoolyear = schoolyear AND lower(r.coursesectionname) = lower(roster_name))
               AND en.attendanceschoolid = att_sch_id AND en.aypschoolid = ayp_sch_id AND en.currentschoolyear = schoolyear AND stu.stateid = state_Id)
        LOOP
          UPDATE enrollmentsrosters SET activeflag = false,modifieddate=CURRENT_TIMESTAMP, modifieduser = ceteSysAdminUserId 
                  WHERE enrollmentid = studentsEnrollemntsRostersRecord.enrollmentid AND rosterid = studentsEnrollemntsRostersRecord.rosterId
                      AND id = studentsEnrollemntsRostersRecord.enrlRosterId;

           RAISE NOTICE 'Student(%) with Id : % is removed from the rosterId: %  enrollmentrosterId: % ', studentsEnrollemntsRostersRecord.statestudentidentifier, studentsEnrollemntsRostersRecord.studentId, 
                       studentsEnrollemntsRostersRecord.rosterId, studentsEnrollemntsRostersRecord.enrlRosterId;

           error_msg := '<success> Student '|| studentsEnrollemntsRostersRecord.statestudentidentifier || ' is removed from roster ' || roster_name ||' ' || error_msg;                       
                        
           INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', studentsEnrollemntsRostersRecord.enrlRosterId, ceteSysAdminUserId, now(),
		'RM_STUDENT_FROM_ROSTER', ('{"rosterId":' || studentsEnrollemntsRostersRecord.rosterId  || ',"enrollmentId":' 
				|| studentsEnrollemntsRostersRecord.enrollmentid || ',"enrollmentRosterId":' || studentsEnrollemntsRostersRecord.enrlRosterId || '}')::json);
          
           FOR stuTestsRecordsInprgsPenUnusedStatus IN (SELECT st.id, st.studentid, st.testid, st.testcollectionid, st.testsessionid, st.status,st.enrollmentid 
                FROM studentstests st JOIN testsession ts ON st.testsessionid = ts.id JOIN operationaltestwindow otw on otw.id = ts.operationaltestwindowid 
		 WHERE st.activeflag=true AND ts.rosterid = studentsEnrollemntsRostersRecord.rosterId
                AND st.enrollmentid = studentsEnrollemntsRostersRecord.enrollmentid AND (otw.effectivedate <= now() AND now() <= otw.expirydate)
                AND st.status in (inProgressStuTestsStatus, pendingStuTestsStatus, unusedStuTestsStatus))
	    LOOP
	        PERFORM inActivateStuTestsTrackerITITestsessions(studentTestsId := stuTestsRecordsInprgsPenUnusedStatus.id, inActiveStuTestSecStatusId := rosterUnEnrolledStuTestSecsStatus, 
	            inActiveStuTestStatusId := rosterUnEnrolledStuTestsStatus, testsession_Id := stuTestsRecordsInprgsPenUnusedStatus.testsessionid, student_Id :=stuTestsRecordsInprgsPenUnusedStatus.studentid);
            END LOOP;
      UPDATE ititestsessionhistory SET activeflag = false, modifieddate = now(), modifieduser = ceteSysAdminUserId, status = rosterUnEnrolledStuTestsStatus
	     WHERE studentenrlrosterid IN (SELECT id FROM enrollmentsrosters WHERE enrollmentid = studentsEnrollemntsRostersRecord.enrollmentid  and rosterid = studentsEnrollemntsRostersRecord.rosterId)
             AND status = pendingStuTestsStatus AND activeflag IS true;
    END LOOP;
  END IF;
RETURN error_msg;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;

-- Sample function execution
-- SELECT removeAllStudentsFromRosterWithNoCourse(ayp_sch_displayidentifier := 'abaes' , att_sch_displayidentifier := 'abaes' , stateDisplayidentifier := 'mo' , schoolyear := 2016 , 
         --subject_abbrName := 'ELA' , teacher_uniqueCommonId :='89234' , roster_name:='ELA 11 LockDown');
         
  
  
  
  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario: 1.1 Removing student from roster with Course.
DROP FUNCTION IF EXISTS removeStudentFromRosterWithCourse(character varying, character varying, character varying, character varying, bigint, character varying, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION removeStudentFromRosterWithCourse(statestudent_identifier character varying, ayp_sch_displayidentifier character varying, att_sch_displayidentifier character varying, stateDisplayidentifier character varying, 
              schoolyear bigint, subject_abbrName character varying, course_Abbrname character varying, teacher_uniqueCommonId character varying, roster_name character varying) RETURNS VOID AS

$BODY$ 
 DECLARE
   state_Id BIGINT;
   ayp_sch_id BIGINT;
   att_sch_id BIGINT;
   ceteSysAdminUserId BIGINT;
   contentArea_Id BIGINT;
   rosterUnEnrolledStuTestSecsStatus BIGINT;   
   rosterUnEnrolledStuTestsStatus BIGINT;
   inProgressStuTestsStatus BIGINT;
   pendingStuTestsStatus BIGINT;
   unusedStuTestsStatus BIGINT;
   course_Id BIGINT;
   subject_Id BIGINT;
   teacher_Id BIGINT;
   studentsEnrollemntsRostersRecord RECORD;
   stuTestsRecordsInprgsPenUnusedStatus RECORD;     
 BEGIN 
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));
   SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(ayp_sch_displayidentifier));
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));
   SELECT INTO rosterUnEnrolledStuTestSecsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'rosterunenrolled');
   SELECT INTO rosterUnEnrolledStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'rosterunenrolled');
   SELECT INTO inProgressStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress');
   SELECT INTO pendingStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending');
   SELECT INTO unusedStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused');   
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');
   SELECT INTO course_Id (SELECT id FROM gradecourse WHERE lower(abbreviatedname) = lower(course_Abbrname) and course is true LIMIT 1);
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO teacher_Id (select DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id where uso.organizationid = att_sch_id and lower(uniquecommonidentifier) = lower(teacher_uniqueCommonId) LIMIT 1); 
	RAISE NOTICE 'INSIDE THE FUNCTION';
   FOR studentsEnrollemntsRostersRecord IN (SELECT stu.statestudentidentifier, stu.id as studentId, en.id as enrollmentid, enrl.id as enrlRosterId, enrl.rosterId as rosterId 
           FROM student stu JOIN enrollment en on stu.id = en.studentid JOIN enrollmentsrosters enrl on enrl.enrollmentid = en.id 
           WHERE lower(stu.statestudentidentifier) = lower(statestudent_identifier) AND
           enrl.rosterid IN (SELECT r.id FROM roster r
               WHERE r.statecoursesid = course_Id AND r.teacherid = teacher_id AND r.statesubjectareaid = subject_Id AND r.attendanceschoolid = att_sch_id AND currentschoolyear = schoolyear AND lower(r.coursesectionname) = lower(roster_name))
               AND en.attendanceschoolid = att_sch_id AND en.aypschoolid = ayp_sch_id AND en.currentschoolyear = schoolyear AND stu.stateid = state_Id)
        LOOP
          UPDATE enrollmentsrosters SET activeflag = false,modifieddate=CURRENT_TIMESTAMP, modifieduser = ceteSysAdminUserId 
                  WHERE enrollmentid = studentsEnrollemntsRostersRecord.enrollmentid AND rosterid = studentsEnrollemntsRostersRecord.rosterId
                      AND id = studentsEnrollemntsRostersRecord.enrlRosterId;

           RAISE NOTICE 'Student(%) with Id : % is removed from the rosterId: %  enrollmentrosterId: % ', studentsEnrollemntsRostersRecord.statestudentidentifier, studentsEnrollemntsRostersRecord.studentId, 
                       studentsEnrollemntsRostersRecord.rosterId, studentsEnrollemntsRostersRecord.enrlRosterId;
                        
           INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', studentsEnrollemntsRostersRecord.enrlRosterId, ceteSysAdminUserId, now(),
		'RM_STUDENT_FROM_ROSTER', ('{"rosterId":' || studentsEnrollemntsRostersRecord.rosterId  || ',"enrollmentId":' 
				|| studentsEnrollemntsRostersRecord.enrollmentid || ',"enrollmentRosterId":' || studentsEnrollemntsRostersRecord.enrlRosterId || '}')::json);
          
           FOR stuTestsRecordsInprgsPenUnusedStatus IN (SELECT st.id, st.studentid, st.testid, st.testcollectionid, st.testsessionid, st.status,st.enrollmentid 
                FROM studentstests st JOIN testsession ts ON st.testsessionid = ts.id JOIN operationaltestwindow otw on otw.id = ts.operationaltestwindowid 
		 WHERE st.activeflag=true AND ts.rosterid = studentsEnrollemntsRostersRecord.rosterId
                AND st.enrollmentid = studentsEnrollemntsRostersRecord.enrollmentid AND (otw.effectivedate <= now() AND now() <= otw.expirydate)
                AND st.status in (inProgressStuTestsStatus, pendingStuTestsStatus, unusedStuTestsStatus))
	    LOOP
	        PERFORM inActivateStuTestsTrackerITITestsessions(studentTestsId := stuTestsRecordsInprgsPenUnusedStatus.id, inActiveStuTestSecStatusId := rosterUnEnrolledStuTestSecsStatus, 
	            inActiveStuTestStatusId := rosterUnEnrolledStuTestsStatus, testsession_Id := stuTestsRecordsInprgsPenUnusedStatus.testsessionid, student_Id :=stuTestsRecordsInprgsPenUnusedStatus.studentid);
            END LOOP;
      UPDATE ititestsessionhistory SET activeflag = false, modifieddate = now(), modifieduser = ceteSysAdminUserId, status = rosterUnEnrolledStuTestsStatus
	     WHERE studentenrlrosterid IN (SELECT id FROM enrollmentsrosters WHERE enrollmentid = studentsEnrollemntsRostersRecord.enrollmentid  and rosterid = studentsEnrollemntsRostersRecord.rosterId)
             AND status = pendingStuTestsStatus AND activeflag IS true;
  END LOOP;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Sample function calling
-- SELECT removeStudentFromRosterWithCourse(statestudent_identifier := '5478565' , ayp_sch_displayidentifier := 'abaes' , att_sch_displayidentifier := 'abaes' , stateDisplayidentifier := 'mo' , schoolyear := 2016 , 
        -- subject_abbrName := 'ELA' , course_Abbrname := 'ENG11' , teacher_uniqueCommonId :='89234' , roster_name:='ELA 11 LockDown');
        
  
  
  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario: 1.3 Removing student from roster with NO Course.
DROP FUNCTION IF EXISTS removeStudentFromRosterWithNoCourse(character varying, character varying, character varying, character varying, bigint, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION removeStudentFromRosterWithNoCourse(statestudent_identifier character varying, ayp_sch_displayidentifier character varying, att_sch_displayidentifier character varying, stateDisplayidentifier character varying, 
              schoolyear bigint, subject_abbrName character varying, teacher_uniqueCommonId character varying, roster_name character varying) RETURNS VOID AS

$BODY$ 
 DECLARE
   state_Id BIGINT;
   ayp_sch_id BIGINT;
   att_sch_id BIGINT;
   ceteSysAdminUserId BIGINT;
   contentArea_Id BIGINT;
   rosterUnEnrolledStuTestSecsStatus BIGINT;   
   rosterUnEnrolledStuTestsStatus BIGINT;
   inProgressStuTestsStatus BIGINT;
   pendingStuTestsStatus BIGINT;
   unusedStuTestsStatus BIGINT;
   subject_Id BIGINT;
   teacher_Id BIGINT;
   studentsEnrollemntsRostersRecord RECORD;
   stuTestsRecordsInprgsPenUnusedStatus RECORD;     
 BEGIN 
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));
   SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(ayp_sch_displayidentifier));
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));
   SELECT INTO rosterUnEnrolledStuTestSecsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'rosterunenrolled');
   SELECT INTO rosterUnEnrolledStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'rosterunenrolled');
   SELECT INTO inProgressStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress');
   SELECT INTO pendingStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending');
   SELECT INTO unusedStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused');   
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);   
   SELECT INTO teacher_Id (select DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id where uso.organizationid = att_sch_id and lower(uniquecommonidentifier) = lower(teacher_uniqueCommonId) LIMIT 1); 
   
   FOR studentsEnrollemntsRostersRecord IN (SELECT stu.statestudentidentifier, stu.id as studentId, en.id as enrollmentid, enrl.id as enrlRosterId, enrl.rosterId as rosterId 
           FROM student stu JOIN enrollment en on stu.id = en.studentid JOIN enrollmentsrosters enrl on enrl.enrollmentid = en.id 
           WHERE lower(stu.statestudentidentifier) = lower(statestudent_identifier) AND
           enrl.rosterid IN (SELECT r.id FROM roster r
               WHERE r.teacherid = teacher_id AND r.statesubjectareaid = subject_Id AND r.attendanceschoolid = att_sch_id AND currentschoolyear = schoolyear AND lower(r.coursesectionname) = lower(roster_name))
               AND en.attendanceschoolid = att_sch_id AND en.aypschoolid = ayp_sch_id AND en.currentschoolyear = schoolyear AND stu.stateid = state_Id)
        LOOP
          UPDATE enrollmentsrosters SET activeflag = false,modifieddate=CURRENT_TIMESTAMP, modifieduser = ceteSysAdminUserId 
                  WHERE enrollmentid = studentsEnrollemntsRostersRecord.enrollmentid AND rosterid = studentsEnrollemntsRostersRecord.rosterId
                      AND id = studentsEnrollemntsRostersRecord.enrlRosterId;

           RAISE NOTICE 'Student(%) with Id : % is removed from the rosterId: %  enrollmentrosterId: % ', studentsEnrollemntsRostersRecord.statestudentidentifier, studentsEnrollemntsRostersRecord.studentId, 
                       studentsEnrollemntsRostersRecord.rosterId, studentsEnrollemntsRostersRecord.enrlRosterId;
                        
           INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', studentsEnrollemntsRostersRecord.enrlRosterId, ceteSysAdminUserId, now(),
		'RM_STUDENT_FROM_ROSTER', ('{"rosterId":' || studentsEnrollemntsRostersRecord.rosterId  || ',"enrollmentId":' 
				|| studentsEnrollemntsRostersRecord.enrollmentid || ',"enrollmentRosterId":' || studentsEnrollemntsRostersRecord.enrlRosterId || '}')::json);
          
           FOR stuTestsRecordsInprgsPenUnusedStatus IN (SELECT st.id, st.studentid, st.testid, st.testcollectionid, st.testsessionid, st.status,st.enrollmentid 
                FROM studentstests st JOIN testsession ts ON st.testsessionid = ts.id --JOIN operationaltestwindow otw on otw.id = ts.operationaltestwindowid 
		 WHERE st.activeflag=true AND ts.rosterid = studentsEnrollemntsRostersRecord.rosterId
                AND st.enrollmentid = studentsEnrollemntsRostersRecord.enrollmentid --AND (otw.effectivedate <= now() AND now() <= otw.expirydate)
                AND st.status in (inProgressStuTestsStatus, pendingStuTestsStatus, unusedStuTestsStatus))
	    LOOP
	        PERFORM inActivateStuTestsTrackerITITestsessions(studentTestsId := stuTestsRecordsInprgsPenUnusedStatus.id, inActiveStuTestSecStatusId := rosterUnEnrolledStuTestSecsStatus, 
	            inActiveStuTestStatusId := rosterUnEnrolledStuTestsStatus, testsession_Id := stuTestsRecordsInprgsPenUnusedStatus.testsessionid, student_Id :=stuTestsRecordsInprgsPenUnusedStatus.studentid);
            END LOOP;
      UPDATE ititestsessionhistory SET activeflag = false, modifieddate = now(), modifieduser = ceteSysAdminUserId, status = rosterUnEnrolledStuTestsStatus
	     WHERE studentenrlrosterid IN (SELECT id FROM enrollmentsrosters WHERE enrollmentid = studentsEnrollemntsRostersRecord.enrollmentid  and rosterid = studentsEnrollemntsRostersRecord.rosterId)
             AND status = pendingStuTestsStatus AND activeflag IS true;
  END LOOP;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Sample function calling
-- SELECT removeStudentFromRosterWithNoCourse(statestudent_identifier := '5478565' , ayp_sch_displayidentifier := 'abaes' , att_sch_displayidentifier := 'abaes' , stateDisplayidentifier := 'mo' , schoolyear := 2016 , 
        -- subject_abbrName := 'ELA' , teacher_uniqueCommonId :='89234' , roster_name:='ELA 11 LockDown');
        
  
  

  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario: Updating state studentidentifier
DROP FUNCTION IF EXISTS updateStateStudentIdentifier(character varying, character varying, character varying, character varying, character varying, bigint, character varying);
               
CREATE OR REPLACE FUNCTION updateStateStudentIdentifier(old_statestudent_identifier character varying, new_statestudent_identifier character varying, aypSch character varying, 
               attSch character varying, schoolyear bigint, stateDisplayidentifier character varying)
        RETURNS VOID AS
$BODY$ 
 DECLARE   
   state_Id BIGINT;
   ayp_sch_id BIGINT;
   att_sch_id BIGINT;
   ceteSysAdminUserId BIGINT;
   new_EnrlId BIGINT;
   grade_id BIGINT;
   studentRecord RECORD;
   
 BEGIN	        
	SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));
	SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(aypSch));
	SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(attSch));	
	SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');        
	
  IF((SELECT  count(en.*) FROM student stu JOIN enrollment en ON en.studentid = stu.id 
					WHERE lower(stu.statestudentidentifier) = lower(old_statestudent_identifier)
					AND stu.stateid = state_Id and en.currentschoolyear = schoolyear
					AND en.aypschoolid = ayp_sch_id and en.attendanceschoolid = att_sch_id) <= 0)
        THEN
           RAISE NOTICE 'Student % is not found in ayp school %, attendance school % and school year %', old_statestudent_identifier, aypSch, attSch, schoolyear; 
				
        ELSE
	      FOR studentRecord IN (SELECT  stu.* FROM student stu JOIN enrollment en ON en.studentid = stu.id 
					WHERE lower(stu.statestudentidentifier) = lower(old_statestudent_identifier)
					AND stu.stateid = state_Id and en.currentschoolyear = schoolyear
					AND en.aypschoolid = ayp_sch_id and en.attendanceschoolid = att_sch_id LIMIT 1)
             LOOP

		UPDATE student SET  statestudentidentifier= new_statestudent_identifier, modifieduser = ceteSysAdminUserId, modifieddate = now() WHERE id = studentRecord.id;

		RAISE NOTICE 'State Student id % is updated to %', old_statestudent_identifier, new_statestudent_identifier;
                  
             END LOOP;
        
        END IF;     
   END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


--SELECT updateStateStudentIdentifier(old_statestudent_identifier := , new_statestudent_identifier := , aypSch :=, 
              -- attSch := , schoolyear := , stateDisplayidentifier := );
              
  
  

-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 5: Update student demographic data.

DROP FUNCTION IF EXISTS updateStudentDemoGraphics(character varying, character varying, boolean, character varying, character varying, character varying, character varying, bigint, character varying);

-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 5: Update student demographic data. Also changes the date of birth, firstname, last name and also updates the username of the student.

DROP FUNCTION IF EXISTS updateStudentDemoGraphics(character varying, character varying, character varying, character varying, boolean, character varying, character varying, character varying, character varying, bigint, character varying, date);


CREATE OR REPLACE FUNCTION updateStudentDemoGraphics(student_firstName character varying, student_LastName character varying, state_student_identifier character varying, state_displayidentifier character varying, hispanic_Ethnicity boolean, race character varying, esolCode character varying,
   att_sch_displayidentifier character varying, ayp_sch_displayidentifier character varying, schoolyear bigint, grade_abbrName character varying, birthDate date) RETURNS VOID AS

$BODY$
   DECLARE
   state_Id BIGINT;   
   ceteSysAdminUserId BIGINT;  
   att_sch_id BIGINT;
   ayp_sch_id BIGINT;
   grade_Id BIGINT;
   student_userName CHARACTER VARYING;
   updated_userName CHARACTER VARYING;
   studentRecord RECORD;
   dob date;
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(state_displayidentifier));
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));
   SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(ayp_sch_displayidentifier));  
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO grade_Id (SELECT id FROM gradecourse gc WHERE contentareaid IS NULL AND assessmentprogramgradesid IS NOT NULL 
					AND abbreviatedname = grade_abbrName);
   
   IF((SELECT count(stu.*) FROM student stu JOIN enrollment en ON en.studentid = stu.id
              WHERE stu.statestudentidentifier = state_student_identifier AND stu.stateid = state_Id AND en.attendanceschoolid = att_sch_id 
                AND en.aypschoolid = ayp_sch_id AND en.currentschoolyear = schoolyear AND en.currentgradelevel = grade_Id) <= 0) THEN
	RAISE NOTICE 'Student % not found in state %, attendace school %, ayp school %, grade %, and current school %', state_student_identifier, 
	                  state_student_identifier, att_sch_displayidentifier, ayp_sch_displayidentifier, grade_abbrName, schoolyear;

   ELSE 

      FOR studentRecord IN (SELECT stu.* FROM student stu JOIN enrollment en ON en.studentid = stu.id
              WHERE stu.statestudentidentifier = state_student_identifier AND stu.stateid = state_Id AND en.attendanceschoolid = att_sch_id 
                AND en.aypschoolid = ayp_sch_id AND en.currentschoolyear = schoolyear AND en.currentgradelevel = grade_Id LIMIT 1) LOOP

        IF(lower(studentRecord.legalfirstname) != lower(student_firstName)  OR lower(studentRecord.legallastname) != lower(student_LastName)) THEN           
            SELECT substring(student_firstName, 1, LEAST(length(student_firstName), 4)) || '.' || substring(student_LastName, 1, LEAST(length(student_LastName), 4)) INTO student_userName;

            select (case when ucount is null 
                 then student_userName 
		 else student_userName || '.' || (ucount+1)
		 end) as modifiedUsername from (select (select 0 as ucount from student where username = student_userName 
					union select CAST(split_part(username, '.', 3) as int) as ucount from student 
					where username like  student_userName || '.%' order by ucount desc limit 1) )a INTO updated_userName;

          update student set legalfirstname = student_firstName, legallastname = student_LastName, username = updated_userName,modifieddate = now(), 
	         modifieduser = ceteSysAdminUserId WHERE id = studentRecord.id;					
             
        END IF;
	IF (birthDate IS null) 
	  THEN
	     dob = studentRecord.dateofbirth;
	   ELSE 
	    dob = birthDate;
	 END IF;
	
	UPDATE student SET esolparticipationcode = esolCode, hispanicethnicity = hispanic_Ethnicity, comprehensiverace = race, modifieddate = now(), dateofbirth = dob,
	         modifieduser = ceteSysAdminUserId WHERE id = studentRecord.id;

       RAISE NOTICE 'Student %  demographic info got changed', state_student_identifier;
	
      END LOOP;
	
   END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
--SELECT updateStudentDemoGraphics(student_firstName :='Max', student_LastName :='Marry', state_student_identifier := '14', state_displayidentifier := 'OK', hispanic_Ethnicity := false, race := '1', esolCode :='1',
  --  att_sch_displayidentifier :='62I019105', ayp_sch_displayidentifier := '62I019105', schoolyear :='2016', grade_abbrName :='3', birthDate:='2010-05-14');

 -- or
-- SELECT updateStudentDemoGraphics(student_firstName :='Max', student_LastName :='Marry', state_student_identifier := '14', state_displayidentifier := 'OK', hispanic_Ethnicity := false, race := '1', esolCode :='1',
  --  att_sch_displayidentifier :='62I019105', ayp_sch_displayidentifier := '62I019105', schoolyear :='2016', grade_abbrName :='3', birthDate := null);
   
  
  

-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario: Transfer the completed testsessions from old roster to new roster with no course.
DROP FUNCTION IF EXISTS moveCTSFromRosterWithNoCourse(character varying, character varying, character varying, character varying, bigint, character varying, character varying, 
         character varying, character varying, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION moveCTSFromRosterWithNoCourse(statestudent_identifier character varying, old_aypSch character varying, old_attSch character varying, 
         old_roster_teacherIdentifier character varying, schoolyear bigint, new_aypSch character varying, new_attSch character varying, sub_abbrName character varying, old_roster_name character varying, 
         new_roster_teacherIdentifier character varying, new_roster_name character varying, stateDisplayidentifier character varying)
        RETURNS VOID AS
$BODY$ 
 DECLARE   
   state_Id BIGINT;
   old_ayp_sch_id BIGINT;
   old_att_sch_id BIGINT;   
   new_ayp_sch_id BIGINT;
   new_att_sch_id BIGINT;
   old_teacherId BIGINT;   
   new_teacher_id BIGINT;
   ceteSysAdminUserId BIGINT;
   subject_id BIGINT; 
   completed_statusId BIGINT;
   old_studentsEnrollemntsRostersRecord RECORD;
   new_studentsEnrollemntsRostersRecord RECORD;
   completedStuTestsRecords RECORD;
   
 BEGIN	        
	SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));
	SELECT INTO old_ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(old_aypSch));
	SELECT INTO old_att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(old_attSch));
        SELECT INTO new_ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(new_aypSch));
	SELECT INTO new_att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(new_attSch));			
	SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');
       	SELECT INTO subject_id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(sub_abbrName) LIMIT 1);
        SELECT INTO completed_statusId (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete');   
        SELECT INTO old_teacherId (select DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id where uso.organizationid = old_att_sch_id and lower(uniquecommonidentifier) = lower(old_roster_teacherIdentifier) LIMIT 1); 
        SELECT INTO new_teacher_id (select DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id where uso.organizationid = new_att_sch_id and lower(uniquecommonidentifier) = lower(new_roster_teacherIdentifier) LIMIT 1); 
        
        FOR old_studentsEnrollemntsRostersRecord IN (SELECT stu.statestudentidentifier, stu.id as studentId, en.id as enrollmentid, enrl.id as enrlRosterId, enrl.rosterId as rosterId 
           FROM student stu JOIN enrollment en on stu.id = en.studentid JOIN enrollmentsrosters enrl on enrl.enrollmentid = en.id 
           WHERE lower(stu.statestudentidentifier) = lower(statestudent_identifier) AND
           enrl.rosterid IN (SELECT r.id FROM roster r
               WHERE r.teacherid = old_teacherId AND r.statesubjectareaid = subject_Id AND r.attendanceschoolid = old_att_sch_id AND currentschoolyear = schoolyear AND lower(r.coursesectionname) = lower(old_roster_name))
               AND en.attendanceschoolid = old_att_sch_id AND en.aypschoolid = old_ayp_sch_id AND en.currentschoolyear = schoolyear AND stu.stateid = state_Id)
       LOOP
          
          
          FOR new_studentsEnrollemntsRostersRecord IN (SELECT stu.statestudentidentifier, stu.id as studentId, en.id as enrollmentid, enrl.id as enrlRosterId, enrl.rosterId as rosterId 
           FROM student stu JOIN enrollment en on stu.id = en.studentid JOIN enrollmentsrosters enrl on enrl.enrollmentid = en.id 
           WHERE lower(stu.statestudentidentifier) = lower(statestudent_identifier) AND
           enrl.rosterid IN (SELECT r.id FROM roster r
               WHERE r.teacherid = new_teacher_id AND r.statesubjectareaid = subject_Id AND r.attendanceschoolid = new_att_sch_id AND currentschoolyear = schoolyear AND lower(r.coursesectionname) = lower(new_roster_name))
               AND en.attendanceschoolid = new_att_sch_id AND en.aypschoolid = new_ayp_sch_id AND en.currentschoolyear = schoolyear AND stu.stateid = state_Id)
          LOOP             
             
             FOR completedStuTestsRecords IN(SELECT st.id, st.studentid, st.testid, st.testcollectionid, st.testsessionid, st.status,st.enrollmentid 
		 FROM studentstests st JOIN testsession ts ON st.testsessionid = ts.id --JOIN operationaltestwindow otw on otw.id = ts.operationaltestwindowid 
		 WHERE st.activeflag=true AND ts.rosterid = old_studentsEnrollemntsRostersRecord.rosterId
                 AND st.enrollmentid = old_studentsEnrollemntsRostersRecord.enrollmentid -- AND (otw.effectivedate <= now() AND now() <= otw.expirydate)
                 AND st.status = completed_statusId)
             LOOP                
                
                UPDATE studentstests SET enrollmentid = new_studentsEnrollemntsRostersRecord.enrollmentid, modifieddate = now(),
                           modifieduser = ceteSysAdminUserId WHERE id = completedStuTestsRecords.id;

                RAISE NOTICE 'Studentstests % is updated with new enrollmentid %', completedStuTestsRecords.id, new_studentsEnrollemntsRostersRecord.enrollmentid;
                 
                IF((SELECT count(distinct enrollmentid) FROM studentstests WHERE testsessionid = completedStuTestsRecords.testsessionid) = 1)  

                THEN
                
                   UPDATE testsession SET rosterid = new_studentsEnrollemntsRostersRecord.rosterId, attendanceschoolid = new_att_sch_id,
			modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = completedStuTestsRecords.testsessionid;

                   RAISE NOTICE 'Testsession % is updated with new rosterid %', completedStuTestsRecords.testsessionid, new_studentsEnrollemntsRostersRecord.rosterId;
                   
		   UPDATE ititestsessionhistory SET rosterid = new_studentsEnrollemntsRostersRecord.rosterId, studentenrlrosterid = new_studentsEnrollemntsRostersRecord.enrlRosterId,
		          modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE testsessionid = completedStuTestsRecords.testsessionid;  

		   RAISE NOTICE 'ITI plan with testsession % is updated with new rosterid %', completedStuTestsRecords.testsessionid, new_studentsEnrollemntsRostersRecord.rosterId;                               

                END IF;
                                                
             END LOOP;
             
          END LOOP;

       END LOOP;
       	
   END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


--SELECT  moveCTSFromRosterWithNoCourse(statestudent_identifier := , old_aypSch := , old_attSch := , old_roster_teacherIdentifier := , 
    -- schoolyear := , new_aypSch := , new_attSch := , sub_abbrName := , old_roster_name := , 
    -- new_roster_teacherIdentifier := , new_roster_name := , stateDisplayidentifier := );

  
  
  

  
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario: Transfer the completed testsessions from old roster to new roster with course.
DROP FUNCTION IF EXISTS moveCTSFromRosterWithCourse(character varying, character varying, character varying, character varying, bigint, character varying, character varying, 
         character varying, character varying, character varying, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION moveCTSFromRosterWithCourse(statestudent_identifier character varying, old_aypSch character varying, old_attSch character varying, 
         old_roster_teacherIdentifier character varying, schoolyear bigint, new_aypSch character varying, new_attSch character varying, sub_abbrName character varying, course_abbrName character varying, old_roster_name character varying, 
         new_roster_teacherIdentifier character varying, new_roster_name character varying, stateDisplayidentifier character varying)
        RETURNS VOID AS
$BODY$ 
 DECLARE   
   state_Id BIGINT;
   old_ayp_sch_id BIGINT;
   old_att_sch_id BIGINT;   
   new_ayp_sch_id BIGINT;
   new_att_sch_id BIGINT;
   old_teacherId BIGINT;   
   new_teacher_id BIGINT;
   ceteSysAdminUserId BIGINT;
   subject_id BIGINT; 
   completed_statusId BIGINT;
   old_studentsEnrollemntsRostersRecord RECORD;
   new_studentsEnrollemntsRostersRecord RECORD;
   completedStuTestsRecords RECORD;
   course_Id BIGINT;
   
 BEGIN	        
	SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));
	SELECT INTO old_ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(old_aypSch));
	SELECT INTO old_att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(old_attSch));
        SELECT INTO new_ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(new_aypSch));
	SELECT INTO new_att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(new_attSch));			
	SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');
       	SELECT INTO subject_id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(sub_abbrName) LIMIT 1);
        SELECT INTO completed_statusId (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete');   
        SELECT INTO old_teacherId (select DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id where uso.organizationid = old_att_sch_id and lower(uniquecommonidentifier) = lower(old_roster_teacherIdentifier) LIMIT 1); 
        SELECT INTO new_teacher_id (select DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id where uso.organizationid = new_att_sch_id and lower(uniquecommonidentifier) = lower(new_roster_teacherIdentifier) LIMIT 1); 
        SELECT INTO course_Id (SELECT id FROM gradecourse WHERE lower(abbreviatedname) = lower(course_abbrName) and course is true LIMIT 1);
        
        FOR old_studentsEnrollemntsRostersRecord IN (SELECT stu.statestudentidentifier, stu.id as studentId, en.id as enrollmentid, enrl.id as enrlRosterId, enrl.rosterId as rosterId 
           FROM student stu JOIN enrollment en on stu.id = en.studentid JOIN enrollmentsrosters enrl on enrl.enrollmentid = en.id 
           WHERE lower(stu.statestudentidentifier) = lower(statestudent_identifier) AND
           enrl.rosterid IN (SELECT r.id FROM roster r
               WHERE r.statecoursesid = course_Id AND r.teacherid = old_teacherId AND r.statesubjectareaid = subject_Id AND r.attendanceschoolid = old_att_sch_id AND currentschoolyear = schoolyear AND lower(r.coursesectionname) = lower(old_roster_name))
               AND en.attendanceschoolid = old_att_sch_id AND en.aypschoolid = old_ayp_sch_id AND en.currentschoolyear = schoolyear AND stu.stateid = state_Id)
       LOOP
          
          
          FOR new_studentsEnrollemntsRostersRecord IN (SELECT stu.statestudentidentifier, stu.id as studentId, en.id as enrollmentid, enrl.id as enrlRosterId, enrl.rosterId as rosterId 
           FROM student stu JOIN enrollment en on stu.id = en.studentid JOIN enrollmentsrosters enrl on enrl.enrollmentid = en.id 
           WHERE lower(stu.statestudentidentifier) = lower(statestudent_identifier) AND
           enrl.rosterid IN (SELECT r.id FROM roster r
               WHERE r.statecoursesid = course_Id AND r.teacherid = new_teacher_id AND r.statesubjectareaid = subject_Id AND r.attendanceschoolid = new_att_sch_id AND currentschoolyear = schoolyear AND lower(r.coursesectionname) = lower(new_roster_name))
               AND en.attendanceschoolid = new_att_sch_id AND en.aypschoolid = new_ayp_sch_id AND en.currentschoolyear = schoolyear AND stu.stateid = state_Id)
          LOOP             
             
             FOR completedStuTestsRecords IN(SELECT st.id, st.studentid, st.testid, st.testcollectionid, st.testsessionid, st.status,st.enrollmentid 
		 FROM studentstests st JOIN testsession ts ON st.testsessionid = ts.id --JOIN operationaltestwindow otw on otw.id = ts.operationaltestwindowid 
		 WHERE st.activeflag=true AND ts.rosterid = old_studentsEnrollemntsRostersRecord.rosterId
                 AND st.enrollmentid = old_studentsEnrollemntsRostersRecord.enrollmentid -- AND (otw.effectivedate <= now() AND now() <= otw.expirydate)
                 AND st.status = completed_statusId)
             LOOP                
                
                UPDATE studentstests SET enrollmentid = new_studentsEnrollemntsRostersRecord.enrollmentid, modifieddate = now(),
                           modifieduser = ceteSysAdminUserId WHERE id = completedStuTestsRecords.id;

                RAISE NOTICE 'Studentstests % is updated with new enrollmentid %', completedStuTestsRecords.id, new_studentsEnrollemntsRostersRecord.enrollmentid;
                 
                IF((SELECT count(distinct enrollmentid) FROM studentstests WHERE testsessionid = completedStuTestsRecords.testsessionid) = 1)  

                THEN
                
                   UPDATE testsession SET rosterid = new_studentsEnrollemntsRostersRecord.rosterId, attendanceschoolid = new_att_sch_id,
			modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = completedStuTestsRecords.testsessionid;

                   RAISE NOTICE 'Testsession % is updated with new rosterid %', completedStuTestsRecords.testsessionid, new_studentsEnrollemntsRostersRecord.rosterId;
                   
		   UPDATE ititestsessionhistory SET rosterid = new_studentsEnrollemntsRostersRecord.rosterId, studentenrlrosterid = new_studentsEnrollemntsRostersRecord.enrlRosterId,
		          modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE testsessionid = completedStuTestsRecords.testsessionid;  

		   RAISE NOTICE 'ITI plan with testsession % is updated with new rosterid %', completedStuTestsRecords.testsessionid, new_studentsEnrollemntsRostersRecord.rosterId;                               

                END IF;
                                                
             END LOOP;
             
          END LOOP;

       END LOOP;
       	
   END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


--SELECT  moveCTSFromRosterWithCourse(statestudent_identifier := , old_aypSch := , old_attSch := , old_roster_teacherIdentifier := , 
    -- schoolyear := , new_aypSch := , new_attSch := , sub_abbrName := , course_abbrName := , old_roster_name := , 
    -- new_roster_teacherIdentifier := , new_roster_name := , stateDisplayidentifier := );

    
  
  
  