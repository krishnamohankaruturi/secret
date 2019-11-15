-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 2.1: Adding student to roster with NO course. (Not transfering any completed testsessions to new roster).

DROP FUNCTION IF EXISTS addStudentToRosterWithNOCourse(character varying, character varying, character varying, bigint, character varying, character varying, character varying, character varying, character varying);


CREATE OR REPLACE FUNCTION addStudentToRosterWithNOCourse(state_student_identifier character varying, att_sch_displayidentifier character varying, ayp_sch_displayidentifier character varying, schoolyear bigint, 
      subject_abbrName character varying, teacher_uniqueCommonId character varying, roster_name character varying, stateDisplayidentifier character varying) RETURNS VOID AS

$BODY$
   DECLARE
   state_Id BIGINT;
   att_sch_id BIGINT;
   ayp_sch_id BIGINT;
   ceteSysAdminUserId BIGINT;
   contentArea_Id BIGINT;
   subject_Id BIGINT;
   teacher_Id BIGINT;   
   rosterRecord RECORD;
   enrollmentRecord RECORD;
   enrlRecords RECORD;   
   enrl_Id BIGINT;   
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));   
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));
   SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(ayp_sch_displayidentifier));
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO teacher_Id (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(teacher_uniqueCommonId) LIMIT 1);
   
   IF((SELECT count(*) FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id
                          AND currentschoolyear = schoolyear AND teacherid = teacher_Id)) <= 0 THEN

     RAISE NOTICE 'No rosters found with roster name: %, subject: %, attendanceschool: %, teacher: %, and school year: %  ', roster_name, subject_abbrName, att_sch_displayidentifier,
          teacher_uniqueCommonId, schoolyear;

   ELSE
    IF((SELECT count(en.*) FROM enrollment en JOIN student stu ON stu.id = en.studentid AND en.currentschoolyear = schoolyear AND en.aypschoolid = ayp_sch_id AND en.attendanceschoolid = att_sch_id
           AND stu.statestudentidentifier = state_student_identifier AND stu.stateid = state_Id) <= 0) THEN 

        RAISE NOTICE 'Student % is not found with ayp %, attendance %  in school year %', state_student_identifier, ayp_sch_displayidentifier, att_sch_displayidentifier, schoolyear;
    ELSE 
         FOR enrollmentRecord IN(SELECT en.* FROM enrollment en JOIN student stu ON stu.id = en.studentid AND en.currentschoolyear = schoolyear AND en.aypschoolid = ayp_sch_id AND en.attendanceschoolid = att_sch_id
           AND stu.statestudentidentifier = state_student_identifier AND stu.stateid = state_Id)
       LOOP
         FOR rosterRecord IN(SELECT r.* FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id 
                          AND currentschoolyear = schoolyear AND teacherid = teacher_Id)
        LOOP
           IF((SELECT count(*) FROM enrollmentsrosters WHERE enrollmentid = enrollmentRecord.id AND rosterid = rosterRecord.id) <= 0) THEN            

		INSERT INTO enrollmentsrosters(enrollmentid, rosterid, createddate, createduser, activeflag, modifieddate, modifieduser) 
                         VALUES (enrollmentRecord.id, rosterRecord.id, now(), ceteSysAdminUserId, true, now(), ceteSysAdminUserId) RETURNING id INTO enrl_Id;

                INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrl_Id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || rosterRecord.id || ', "enrollmentId":' ||  enrollmentRecord.id || ',"enrollmentRosterId":' || enrl_Id || '}')::json);

		RAISE NOTICE 'Student % is added to the roster %', state_student_identifier, roster_name;
	   ELSE		      
	      FOR enrlRecords IN (SELECT * FROM enrollmentsrosters WHERE enrollmentid = enrollmentRecord.id AND rosterid = rosterRecord.id LIMIT 1) 
	        LOOP                    
                  UPDATE enrollmentsrosters SET activeflag = true, modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = enrlRecords.id;

	          INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrlRecords.id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || enrlRecords.rosterid || ', "enrollmentId":' ||  enrlRecords.enrollmentid || ',"enrollmentRosterId":' || enrlRecords.id || '}')::json);

		  RAISE NOTICE 'Student % is added to the roster %', state_student_identifier, roster_name;
		
	      END LOOP;
	   END IF;	   
        END LOOP;        
     END LOOP;
     END IF;
   END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
--SELECT addStudentToRosterWithNOCourse(state_student_identifier := , att_sch_displayidentifier := , ayp_sch_displayidentifier := , schoolyear := , 
      -- subject_abbrName := , teacher_uniqueCommonId := , roster_name := , stateDisplayidentifier := )