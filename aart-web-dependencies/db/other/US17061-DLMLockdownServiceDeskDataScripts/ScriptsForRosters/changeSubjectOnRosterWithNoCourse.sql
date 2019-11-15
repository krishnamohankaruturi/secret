-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 5.1 Subject change on roster with no Course.

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