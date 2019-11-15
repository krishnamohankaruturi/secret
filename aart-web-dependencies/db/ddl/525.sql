--ddl/525.sql
--moved from 524.sql
ALTER TABLE studentstestsections ADD COLUMN manualupdatereason character varying(400);
ALTER TABLE studentstests ADD COLUMN manualupdatereason character varying(400);

-- dropping the old function
DROP FUNCTION IF EXISTS updateStudentDemoGraphics(character varying, character varying, boolean, character varying, character varying, character varying, character varying, bigint, character varying);

-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 7: Update student demographic data. Also changes the date of birth, firstname, last name and also updates the username of the student.

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