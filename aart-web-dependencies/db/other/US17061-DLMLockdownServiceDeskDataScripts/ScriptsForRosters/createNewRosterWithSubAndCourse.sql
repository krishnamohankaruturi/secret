-- US17061: DLM Lockdown Service Desk Data Scripts
-- Scenario 6.2: Creating new roster with subject and course combination.

DROP FUNCTION IF EXISTS createNewRosterWithSubAndCourse(character varying[], character varying, character varying, bigint, character varying, character varying, character varying, character varying, character varying);


CREATE OR REPLACE FUNCTION createNewRosterWithSubAndCourse(state_student_identifiers character varying[], att_sch_displayidentifier character varying, ayp_sch_displayidentifier character varying, schoolyear bigint, 
      subject_abbrName character varying, course_abbrName character varying, teacher_uniqueCommonId character varying, roster_name character varying, stateDisplayidentifier character varying) RETURNS VOID AS

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
   roster_Id BIGINT;   
   course_Id BIGINT;
   
 BEGIN
   SELECT INTO state_Id (SELECT id FROM organization WHERE lower(displayidentifier) = lower(stateDisplayidentifier));   
   SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(att_sch_displayidentifier));
   SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(schooldisplayidentifier) = lower(ayp_sch_displayidentifier));
   SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');   
   SELECT INTO subject_Id (SELECT id FROM contentarea WHERE lower(abbreviatedname)  = lower(subject_abbrName) LIMIT 1);
   SELECT INTO teacher_Id (SELECT DISTINCT au.id FROM aartuser au JOIN usersorganizations uso ON uso.aartuserid = au.id WHERE uso.organizationid = att_sch_id AND lower(uniquecommonidentifier) = lower(teacher_uniqueCommonId) LIMIT 1);
   SELECT INTO course_Id (SELECT id FROM gradecourse WHERE abbreviatedname = course_abbrName LIMIT 1);
   
   IF((SELECT count(*) FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id
                       AND r.statecoursesid = course_Id AND currentschoolyear = schoolyear AND teacherid = teacher_Id)) <= 0 THEN

     RAISE NOTICE 'No rosters found with roster name: %, subject: %, course: %, attendanceschool: %, teacher: %, and school year: %, So creating new roster.  ', 
                  roster_name, subject_abbrName, course_abbrName, att_sch_displayidentifier, teacher_uniqueCommonId, schoolyear;

     INSERT INTO roster(coursesectionname, teacherid, attendanceSchoolId, statesubjectareaid, restrictionid, createddate, createduser, activeflag, modifieddate, modifieduser,
		educatorschooldisplayidentifier, sourcetype, currentSchoolYear, aypSchoolId, statecoursecode, statecoursesid) 
	VALUES(roster_name, teacher_Id, att_sch_id, subject_Id, 1, now(), ceteSysAdminUserId, true, now(), ceteSysAdminUserId,att_sch_displayidentifier,
                'LOCK_DOWN_SCRIPT', schoolyear, ayp_sch_id, course_abbrName, course_Id) RETURNING id INTO roster_Id;

     FOR enrollmentRecord IN(SELECT stu.statestudentidentifier,en.* FROM enrollment en JOIN student stu ON stu.id = en.studentid AND en.currentschoolyear = schoolyear AND en.aypschoolid = ayp_sch_id AND en.attendanceschoolid = att_sch_id
         AND stu.statestudentidentifier = ANY(state_student_identifiers) AND stu.stateid = state_Id)
     LOOP

        INSERT INTO enrollmentsrosters(enrollmentid, rosterid, createddate, createduser, activeflag, modifieddate, modifieduser) 
                      VALUES (enrollmentRecord.id, roster_Id, now(), ceteSysAdminUserId, true, now(), ceteSysAdminUserId) RETURNING id INTO enrl_Id;

       INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrl_Id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || roster_Id || ', "enrollmentId":' ||  enrollmentRecord.id || ',"enrollmentRosterId":' || enrl_Id || '}')::json);

       RAISE NOTICE 'Student % is added to the roster %', enrollmentRecord.statestudentidentifier, roster_name;
          
     END LOOP; 
     
   ELSE
    FOR enrollmentRecord IN(SELECT stu.statestudentidentifier, en.* FROM enrollment en JOIN student stu ON stu.id = en.studentid AND en.currentschoolyear = schoolyear AND en.aypschoolid = ayp_sch_id AND en.attendanceschoolid = att_sch_id
         AND stu.statestudentidentifier = ANY(state_student_identifiers) AND stu.stateid = state_Id)
     LOOP
        FOR rosterRecord IN(SELECT r.* FROM roster r WHERE lower(r.coursesectionname) = lower(roster_name) AND attendanceschoolid = att_sch_id AND r.statesubjectareaid = subject_Id 
                   AND r.statecoursesid = course_Id AND currentschoolyear = schoolyear AND teacherid = teacher_Id)
	 LOOP
           IF((SELECT count(*) FROM enrollmentsrosters WHERE enrollmentid = enrollmentRecord.id AND rosterid = rosterRecord.id) <= 0) THEN            

	       INSERT INTO enrollmentsrosters(enrollmentid, rosterid, createddate, createduser, activeflag, modifieddate, modifieduser) 
                      VALUES (enrollmentRecord.id, rosterRecord.id, now(), ceteSysAdminUserId, true, now(), ceteSysAdminUserId) RETURNING id INTO enrl_Id;

               INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrl_Id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || rosterRecord.id || ', "enrollmentId":' ||  enrollmentRecord.id || ',"enrollmentRosterId":' || enrl_Id || '}')::json);

	      RAISE NOTICE 'Student % is added to the roster %', enrollmentRecord.statestudentidentifier, roster_name;
	   ELSE		      
	      FOR enrlRecords IN (SELECT * FROM enrollmentsrosters WHERE enrollmentid = enrollmentRecord.id AND rosterid = rosterRecord.id LIMIT 1) 
	        LOOP                    
                  UPDATE enrollmentsrosters SET activeflag = true, modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = enrlRecords.id;

	          INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT_ROSTER', enrlRecords.id, ceteSysAdminUserId, now(),
		      'ADD_STUDNET_TO_ROSTER', ('{"rosterId":' || enrlRecords.rosterid || ', "enrollmentId":' ||  enrlRecords.enrollmentid || ',"enrollmentRosterId":' || enrlRecords.id || '}')::json);
		 
		
	      END LOOP;
	   END IF;	   
        END LOOP;        
     END LOOP;
   END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


-- Calling the function
--SELECT createNewRosterWithSubAndCourse(state_student_identifiers := , att_sch_displayidentifier := , ayp_sch_displayidentifier := , schoolyear := , 
      -- subject_abbrName := , course_abbrName := , teacher_uniqueCommonId := , roster_name := , stateDisplayidentifier := )