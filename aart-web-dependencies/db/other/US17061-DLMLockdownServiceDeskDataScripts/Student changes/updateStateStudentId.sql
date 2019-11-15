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