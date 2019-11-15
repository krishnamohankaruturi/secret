-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 4.3: Change educator and roster name with no course.

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