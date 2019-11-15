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
