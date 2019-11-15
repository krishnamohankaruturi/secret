-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 7: Update student demographic data.

DROP FUNCTION IF EXISTS updateStudentDemoGraphics(character varying, character varying, boolean, character varying, character varying, character varying, character varying, bigint, character varying);


CREATE OR REPLACE FUNCTION updateStudentDemoGraphics(state_student_identifier character varying, state_displayidentifier character varying, hispanic_Ethnicity boolean, race character varying, esolCode character varying,
   att_sch_displayidentifier character varying, ayp_sch_displayidentifier character varying, schoolyear bigint, grade_abbrName character varying) RETURNS VOID AS

$BODY$
   DECLARE
   state_Id BIGINT;   
   ceteSysAdminUserId BIGINT;  
   att_sch_id BIGINT;
   ayp_sch_id BIGINT;
   grade_Id BIGINT;
   studentRecord RECORD;
   
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
	                  stateDisplayidentifier, att_sch_displayidentifier, ayp_sch_displayidentifier, grade_abbrName, schoolyear;

   ELSE 

      FOR studentRecord IN (SELECT stu.* FROM student stu JOIN enrollment en ON en.studentid = stu.id
              WHERE stu.statestudentidentifier = state_student_identifier AND stu.stateid = state_Id AND en.attendanceschoolid = att_sch_id 
                AND en.aypschoolid = ayp_sch_id AND en.currentschoolyear = schoolyear AND en.currentgradelevel = grade_Id LIMIT 1) LOOP

	UPDATE student SET esolparticipationcode = esolCode, hispanicethnicity = hispanic_Ethnicity, comprehensiverace = race, modifieddate = now(), 
	         modifieduser = ceteSysAdminUserId WHERE id = studentRecord.id;

       RAISE NOTICE 'Student %  demographic info got changed', state_student_identifier;
	
      END LOOP;
	
   END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
--SELECT updateStudentDemoGraphics(state_student_identifier := , state_displayidentifier := , hispanic_Ethnicity := , race := , esolCode :=,
   -- att_sch_displayidentifier :=, ayp_sch_displayidentifier := , schoolyear :=, grade_abbrName :=)