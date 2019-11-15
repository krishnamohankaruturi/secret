-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 4.1: Change educator with with course.

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
           teacher_uniqueCommonId, schoolyear;

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