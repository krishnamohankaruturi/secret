-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario: 9: Add New Enrollment
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
   student_id BIGINT;
   sch_entry_date date;
   district_entry_date date;
   state_entry_date date;
   
 BEGIN	        
	SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));
	SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(aypSch));
	SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(attSch));	
	SELECT INTO dist_id (SELECT districtid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(district));		
	SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');
        SELECT INTO grade_id (SELECT id FROM gradecourse gc WHERE contentareaid IS NULL AND assessmentprogramgradesid IS NOT NULL 
					AND abbreviatedname = grade);
        SELECT INTO student_id (SELECT id FROM student WHERE lower(statestudentidentifier) = lower(statestudent_identifier) AND stateid = state_Id LIMIT 1);
	
    IF (student_id IS NULL)

     THEN
	RAISE NOTICE 'Student % is not present in state %', statestudent_identifier, stateDisplayidentifier;
	
    ELSE 
      IF((SELECT  count(*) FROM enrollment WHERE studentid = student_id AND currentschoolyear = schoolyear
		AND aypschoolid = ayp_sch_id and attendanceschoolid = att_sch_id) <= 0)
        THEN
          INSERT INTO enrollment(aypschoolidentifier, residencedistrictidentifier, localstudentidentifier, 
		currentgradelevel, currentschoolyear, attendanceschoolid, 
		schoolentrydate, districtentrydate, stateentrydate, exitwithdrawaldate, 
		exitwithdrawaltype, studentid, restrictionid, createddate, createduser, 
		activeflag, modifieddate, modifieduser, aypSchoolId, sourcetype)
	     VALUES (aypSch, district, localState_StuId, 
		     grade_id, schoolyear, att_sch_id, 
		     schEntryDate, distEntryDate, state_EntryDate, null,
		     0, student_id, 2, now(), ceteSysAdminUserId,
		     true, now(), ceteSysAdminUserId, ayp_sch_id, 'LOCK_DOWN_SCRIPT') RETURNING id INTO new_EnrlId;

	    INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT', new_EnrlId, ceteSysAdminUserId, now(),
		'NEW_ENROLLMENT', ('{"studentId":'|| student_id || ',"stateId":' ||  state_Id
				|| ',"stateStudentIdentifier":"' ||  statestudent_identifier
				|| '","aypSchool":' || ayp_sch_id || ',"attendanceSchoolId":'|| att_sch_id
				|| ',"grade":' || grade_id || ',"schoolEntryDate":"' || schEntryDate ||  '"}')::json);

	RAISE NOTICE 'Student % is enrolled into School % for school year % and grade level %', statestudent_identifier, attSch, schoolyear, grade; 
				
        ELSE
	      FOR newSchEnrlRecord IN (SELECT stu.statestudentidentifier, en.* FROM enrollment en JOIN student stu ON stu.id = en.studentid
	                WHERE stu.id = student_id AND aypschoolid = ayp_sch_id AND attendanceschoolid = att_sch_id
                         AND currentschoolyear = schoolyear LIMIT 1)
             LOOP
		IF(schEntryDate is null) THEN
		     sch_entry_date := newSchEnrlRecord.schoolentrydate;
		ELSE
		     sch_entry_date := schEntryDate;
		END IF;

                IF(distEntryDate is null) THEN 
                    district_entry_date := newSchEnrlRecord.districtentrydate;
                ELSE
                   district_entry_date := distEntryDate;
                END IF;

                IF(state_EntryDate is null) THEN
                   state_entry_date := newSchEnrlRecord.stateentrydate;
                ELSE
                   state_entry_date := state_EntryDate;
                END IF;
		
		UPDATE enrollment SET activeflag = true, schoolentrydate = sch_entry_date, districtentrydate = district_entry_date, stateentrydate = state_entry_date,exitwithdrawaldate = null, 
		          exitwithdrawaltype = 0, modifieduser = ceteSysAdminUserId, modifieddate = now() WHERE id = newSchEnrlRecord.id;

		RAISE NOTICE 'Enrollment % is updated, student % in school year %', newSchEnrlRecord.id, newSchEnrlRecord.statestudentidentifier, schoolyear;
                  
             END LOOP;
        
        END IF;
      END IF;
   END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;
  