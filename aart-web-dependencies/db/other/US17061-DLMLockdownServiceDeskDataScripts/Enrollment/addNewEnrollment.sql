-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario: 12: Add New Enrollment
DROP FUNCTION IF EXISTS addNewEnrollment(character varying, character varying, character varying, character varying, character varying, bigint, 
         date, date, date, character varying, character varying);

CREATE OR REPLACE FUNCTION addNewEnrollment(statestudent_identifier character varying, localState_StuId character varying, aypSch character varying, attSch character varying, district character varying, schoolyear bigint, 
         schEntryDate date, distEntryDate date, state_EntryDate date, grade character varying, stateDisplayidentifier character varying)
        RETURNS VOID AS
$BODY$ 
 DECLARE   
   state_Id BIGINT;
   ayp_sch_id BIGINT;
   att_sch_id BIGINT;
   dist_id BIGINT;
   ceteSysAdminUserId BIGINT;
   new_EnrlId BIGINT;  
   newSchEnrlRecord RECORD;
   grade_id BIGINT;
   studentRecord RECORD;
   
 BEGIN	        
	SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));
	SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(aypSch));
	SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(attSch));	
	SELECT INTO dist_id (SELECT districtid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(district));		
	SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');
        SELECT INTO grade_id (SELECT id FROM gradecourse gc WHERE contentareaid IS NULL AND assessmentprogramgradesid IS NOT NULL 
					AND abbreviatedname = grade);
	
    IF ((SELECT count(*) FROM student WHERE statestudentidentifier = statestudent_identifier AND stateid = state_Id) <=0)

     THEN
	RAISE NOTICE 'Student % is not present in state %', statestudent_identifier, stateDisplayidentifier;
	
    ELSE 
      IF((SELECT  count(en.*) FROM student stu JOIN enrollment en ON en.studentid = stu.id 
					WHERE lower(stu.statestudentidentifier) = lower(statestudent_identifier)
					AND stu.stateid = state_Id and en.currentschoolyear = schoolyear
					AND en.aypschoolid = ayp_sch_id and en.attendanceschoolid = att_sch_id) <= 0)
        THEN
           FOR studentRecord IN (SELECT * FROM student WHERE statestudentidentifier = statestudent_identifier AND stateid = state_Id LIMIT 1)
           LOOP
	     INSERT INTO enrollment(aypschoolidentifier, residencedistrictidentifier, localstudentidentifier, 
			currentgradelevel, currentschoolyear, attendanceschoolid, 
			schoolentrydate, districtentrydate, stateentrydate, exitwithdrawaldate, 
			exitwithdrawaltype, studentid, restrictionid, createddate, createduser, 
			activeflag, modifieddate, modifieduser, aypSchoolId, sourcetype)
		 VALUES (aypSch, district, localState_StuId, 
		          grade_id, schoolyear, att_sch_id, 
		          schEntryDate, distEntryDate, state_EntryDate, null,
		          0, studentRecord.id, 2, now(), ceteSysAdminUserId,
		          true, now(), ceteSysAdminUserId, ayp_sch_id, 'LOCK_DOWN_SCRIPT') RETURNING id INTO new_EnrlId;

	    INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT', new_EnrlId, ceteSysAdminUserId, now(),
		'NEW_ENROLLMENT', ('{"studentId":'|| studentRecord.id || ',"stateId":' ||  studentRecord.stateid
				|| ',"stateStudentIdentifier":"' || studentRecord.statestudentidentifier 
				|| '","aypSchool":' || ayp_sch_id || ',"attendanceSchoolId":'|| att_sch_id
				|| ',"grade":' || grade_id || ',"schoolEntryDate":"' || schEntryDate ||  '"}')::json);

         END LOOP;
				
        ELSE
	      FOR newSchEnrlRecord IN (SELECT stu.statestudentidentifier, en.* FROM enrollment en WHERE studentid = old_studentEnrollemntRecord.studentid AND aypschoolid = ayp_sch_id AND attendanceschoolid = att_sch_id
                      AND currentschoolyear = schoolyear LIMIT 1)
             LOOP

		UPDATE enrollment SET activeflag = true, schoolentrydate = schEntryDate, districtentrydate = distEntryDate, stateentrydate = state_EntryDate,exitwithdrawaldate = null, 
		          exitwithdrawaltype = 0, modifieduser = ceteSysAdminUserId, modifieddate = now() WHERE id = newSchEnrlRecord.id;

		RAISE NOTICE 'Enrollment % is updated, student % in school year %', newSchEnrlRecord.id, newSchEnrlRecord.statestudentidentifier, schoolyear;
                  
             END LOOP;
        
        END IF;
      END IF;
   END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


--SELECT addNewEnrollment(statestudent_identifier :=, localState_StuId :=, aypSch :=, attSch :=, district :=, schoolyear :=, 
        -- schEntryDate :=, distEntryDate :=, state_EntryDate := , grade := , stateDisplayidentifier :=);
