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