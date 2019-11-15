-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 3.1: Change rosterName with no course.

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