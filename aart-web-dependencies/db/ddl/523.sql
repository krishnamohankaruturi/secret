--ddl/523.sql
-- US17777: DLM Lockdown Data Script - Scenario 2 enhancement
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
-- Scenario 4.1: Creating new roster with only subject.

DROP FUNCTION IF EXISTS createNewRosterWithNoCourse(character varying[], character varying, character varying, bigint, character varying, character varying, character varying, character varying);


CREATE OR REPLACE FUNCTION createNewRosterWithNoCourse(state_student_identifiers character varying[], att_sch_displayidentifier character varying, ayp_sch_displayidentifier character varying, schoolyear bigint, 
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
   roster_Id BIGINT;  
   
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));   
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));
   SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(ayp_sch_displayidentifier));
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO teacher_Id (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(teacher_uniqueCommonId) LIMIT 1);   
   
   IF((SELECT count(*) FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id
                          AND currentschoolyear = schoolyear AND teacherid = teacher_Id)) <= 0 THEN

     RAISE NOTICE 'No rosters found with roster name: %, subject: %, attendanceschool: %, teacher: %, and school year: %, So creating new roster.  ', 
                  roster_name, subject_abbrName, att_sch_displayidentifier, teacher_uniqueCommonId, schoolyear;

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

      PERFORM moveCompletedTestsAndResetSTWithNoCourse(student_id := enrollmentRecord.studentid,enrollment_id := enrollmentRecord.id, 
                 subjectArea_id := subject_Id, new_roster_id := roster_Id, school_year := schoolyear, attendance_schId := att_sch_id);
          
     END LOOP; 
     
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

	      PERFORM moveCompletedTestsAndResetSTWithNoCourse(student_id := enrollmentRecord.studentid,enrollment_id := enrollmentRecord.id, subjectArea_id := subject_Id, 
	                 new_roster_id := rosterRecord.id, school_year := schoolyear, attendance_schId := att_sch_id);
	      
	   ELSE		      
	      FOR enrlRecords IN (SELECT * FROM enrollmentsrosters WHERE enrollmentid = enrollmentRecord.id AND rosterid = rosterRecord.id LIMIT 1) 
	        LOOP                    
                  UPDATE enrollmentsrosters SET activeflag = true, modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = enrlRecords.id;

	          INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrlRecords.id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || enrlRecords.rosterid || ', "enrollmentId":' ||  enrlRecords.enrollmentid || ',"enrollmentRosterId":' || enrlRecords.id || '}')::json);

		 PERFORM moveCompletedTestsAndResetSTWithNoCourse(student_id := enrollmentRecord.studentid,enrollment_id := enrollmentRecord.id, subjectArea_id := subject_Id, 
		             new_roster_id := rosterRecord.id, school_year := schoolyear, attendance_schId := att_sch_id);
		
	      END LOOP;
	   END IF;	   
        END LOOP;        
     END LOOP;
   END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
--SELECT createNewRosterWithNoCourse(state_student_identifiers := array['987654', '1234'], att_sch_displayidentifier := '1234', ayp_sch_displayidentifier := '1235', schoolyear := 2016, 
      -- subject_abbrName := 'ELA', teacher_uniqueCommonId := '67576', roster_name := 'ELA Roster', stateDisplayidentifier := 'OK');
      
-- or
--SELECT createNewRosterWithNoCourse(state_student_identifiers := array['987654'], att_sch_displayidentifier := '1234', ayp_sch_displayidentifier := '1235', schoolyear := 2016, 
      -- subject_abbrName := 'ELA', teacher_uniqueCommonId := '67576', roster_name := 'ELA Roster', stateDisplayidentifier := 'OK')  ;
      
  
  
 
-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 4.2: Creating new roster with subject and course combination.

DROP FUNCTION IF EXISTS createNewRosterWithSubAndCourse(character varying[], character varying, character varying, bigint, character varying, character varying, character varying, character varying, character varying);


CREATE OR REPLACE FUNCTION createNewRosterWithSubAndCourse(state_student_identifiers character varying[], att_sch_displayidentifier character varying, ayp_sch_displayidentifier character varying, schoolyear bigint, 
      subject_abbrName character varying, course_abbrName character varying, teacher_uniqueCommonId character varying, roster_name character varying, stateDisplayidentifier character varying) RETURNS VOID AS

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
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));   
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));
   SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(ayp_sch_displayidentifier));
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO teacher_Id (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(teacher_uniqueCommonId) LIMIT 1);
   SELECT INTO course_Id (SELECT id FROM gradecourse WHERE abbreviatedname = course_abbrName LIMIT 1);
   
   IF((SELECT count(*) FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id
                       AND r.statecoursesid = course_Id AND currentschoolyear = schoolyear AND teacherid = teacher_Id)) <= 0 THEN

     RAISE NOTICE 'No rosters found with roster name: %, subject: %, course: %, attendanceschool: %, teacher: %, and school year: %, So creating new roster.  ', 
                  roster_name, subject_abbrName, course_abbrName, att_sch_displayidentifier, teacher_uniqueCommonId, schoolyear;

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

       PERFORM moveCompletedTestsAndResetSTWithCourse(student_id := enrollmentRecord.studentid,enrollment_id := enrollmentRecord.id, subjectArea_id := subject_Id, 
                 course_Id := course_Id, new_roster_id := roster_Id, school_year := schoolyear, attendance_schId := att_sch_id);
          
     END LOOP; 
     
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

	      PERFORM moveCompletedTestsAndResetSTWithCourse(student_id := enrollmentRecord.studentid,enrollment_id := enrollmentRecord.id, subjectArea_id := subject_Id, 
	               course_Id := course_Id, new_roster_id := roster_Id, school_year := schoolyear, attendance_schId := att_sch_id);

	      
	   ELSE		      
	      FOR enrlRecords IN (SELECT * FROM enrollmentsrosters WHERE enrollmentid = enrollmentRecord.id AND rosterid = rosterRecord.id LIMIT 1) 
	        LOOP                    
                  UPDATE enrollmentsrosters SET activeflag = true, modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = enrlRecords.id;

	          INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrlRecords.id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || enrlRecords.rosterid || ', "enrollmentId":' ||  enrlRecords.enrollmentid || ',"enrollmentRosterId":' || enrlRecords.id || '}')::json);

		 PERFORM moveCompletedTestsAndResetSTWithCourse(student_id := enrollmentRecord.studentid,enrollment_id := enrollmentRecord.id, subjectArea_id := subject_Id, 
	               course_Id := course_Id, new_roster_id := roster_Id, school_year := schoolyear, attendance_schId := att_sch_id);
		
	      END LOOP;
	   END IF;	   
        END LOOP;        
     END LOOP;
   END IF;
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
  
  
  
