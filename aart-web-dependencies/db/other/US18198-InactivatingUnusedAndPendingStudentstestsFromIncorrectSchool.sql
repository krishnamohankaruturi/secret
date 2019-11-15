-- US18918 : EP: Prod - Request to correct move tests and correct dual enrollment for KAP students 
-- Inactivating all the unused and pending status studentstests from incorrect school.
DROP TABLE IF EXISTS temp_messages;

CREATE TEMP TABLE temp_messages(csvrowid INTEGER, messsage TEXT);

DO
$BODY$
DECLARE 

 ks_state_id BIGINT;
 current_ayp_school_id BIGINT;
 student_id BIGINT;
 current_attendance_school_id BIGINT;
 currentenrollment_record RECORD;
 enrollmentneedto_inactivate RECORD;
 cetesysadminid BIGINT;
 temp_stuenrlrecord RECORD;
 inprogress_studentstests_status BIGINT;
 unused_studentstests_status BIGINT;
 complete_studentstests_status BIGINT;
 pending_studentstests_status BIGINT;
 current_attsch_testsession_id BIGINT;
 current_sch_tests RECORD;
 old_sch_tests RECORD;
 update_count INTEGER;
 old_att_sch_displayidentifier CHARACTER VARYING;
 old_ayp_sch_displayidentifier CHARACTER VARYING;
 msg TEXT;
 row_count INTEGER;
 

BEGIN

   SELECT id FROM organization WHERE displayidentifier = 'KS' INTO ks_state_id;
   SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
   SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress' INTO inprogress_studentstests_status;
   SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused' INTO unused_studentstests_status;
   SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete' INTO complete_studentstests_status;
   SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending' INTO pending_studentstests_status;
   
   row_count := 1;
    msg := '';      

   FOR temp_stuenrlrecord IN (SELECT * FROM temp_students_enrollments) LOOP

       row_count := row_count + 1;

       SELECT schoolid FROM organizationtreedetail WHERE schooldisplayidentifier = temp_stuenrlrecord.aypschooldisplayidentifier AND stateid = ks_state_id INTO current_ayp_school_id;
       SELECT schoolid FROM organizationtreedetail WHERE schooldisplayidentifier = temp_stuenrlrecord.attendanceschooldisplayidentifier AND stateid = ks_state_id INTO current_attendance_school_id;
       SELECT id FROM student WHERE statestudentidentifier = temp_stuenrlrecord.statestudentidentifier AND stateid = ks_state_id AND activeflag IS true INTO student_id;

       IF((SELECT count(*) from enrollment where studentid = student_id AND aypschoolid = current_ayp_school_id AND attendanceschoolid = current_attendance_school_id
             AND currentschoolyear = 2016 AND activeflag is true) > 0) THEN

          msg := '';
             
          FOR currentenrollment_record IN (SELECT id FROM enrollment WHERE studentid = student_id AND aypschoolid = current_ayp_school_id AND attendanceschoolid = current_attendance_school_id
             AND currentschoolyear = 2016 AND activeflag is true) LOOP         


              FOR enrollmentneedto_inactivate IN (SELECT id FROM enrollment WHERE studentid = student_id AND currentschoolyear = 2016 AND activeflag is true
                  EXCEPT (SELECT id FROM enrollment WHERE studentid = student_id AND aypschoolid = current_ayp_school_id AND attendanceschoolid = current_attendance_school_id
                         AND currentschoolyear = 2016 AND activeflag is true))  LOOP

                 SELECT displayidentifier FROM organization WHERE id = (SELECT attendanceschoolid FROM enrollment WHERE id = enrollmentneedto_inactivate.id) INTO old_att_sch_displayidentifier;
                 SELECT displayidentifier FROM organization WHERE id = (SELECT aypschoolid FROM enrollment WHERE id = enrollmentneedto_inactivate.id) INTO old_ayp_sch_displayidentifier;

                 msg := msg || ' For correct school enrollmentid ' || currentenrollment_record.id || ' aypschool is ' || temp_stuenrlrecord.aypschooldisplayidentifier || ' and attendanceschool is ' || temp_stuenrlrecord.attendanceschooldisplayidentifier
                            || ' Enrollment id In old school: ' || enrollmentneedto_inactivate.id || ' aypschool is ' || old_ayp_sch_displayidentifier || ' and attendanceschool is ' || old_att_sch_displayidentifier;
                 

                 msg := msg || ' Inactivating all unused and pending studentstests from old school ';
                                                          

                 WITH inactivestudentsresponsescount AS(UPDATE studentsresponses SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid 
                        WHERE studentstestsid IN (SELECT st.id FROM studentstests st WHERE st.enrollmentid = enrollmentneedto_inactivate.id AND st.studentid = student_id AND st.activeflag IS true 
                        AND st.status IN (unused_studentstests_status, pending_studentstests_status)) RETURNING 1) SELECT count(*) FROM inactivestudentsresponsescount INTO update_count;

                 msg := msg || ' Inactivated studentsreponses count is : ' || update_count;
                               
                 WITH inactivestudenttestsectionscount AS(UPDATE studentstestsections SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid 
                        WHERE studentstestid IN (SELECT st.id FROM studentstests st WHERE st.enrollmentid = enrollmentneedto_inactivate.id AND st.studentid = student_id AND st.activeflag IS true 
                        AND st.status IN (unused_studentstests_status, pending_studentstests_status)) RETURNING 1) SELECT count(*) FROM inactivestudenttestsectionscount INTO update_count;

                 msg := msg || ' Inactivated studentstestsections count is : ' || update_count;  
                              
                 WITH inactivestudentstests AS (UPDATE studentstests SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid 
                       WHERE enrollmentid = enrollmentneedto_inactivate.id AND studentid = student_id AND activeflag IS true 
                       AND status IN (unused_studentstests_status, pending_studentstests_status)  RETURNING 1) SELECT count(*) FROM inactivestudentstests INTO update_count;

                 msg := msg || ' Inactivated studentstests count is : ' || update_count;
              END LOOP;                                      

          END LOOP;

       ELSE

          msg := msg ||' <error> Student ' || temp_stuenrlrecord.statestudentidentifier ||  ' is not found in aypschool ' || temp_stuenrlrecord.aypschooldisplayidentifier 
                   || ' and attendanceschool' || temp_stuenrlrecord.attendanceschooldisplayidentifier || ' CSV row number:' || row_count;

       END IF;

   INSERT INTO temp_messages(csvrowid, messsage) VALUES(row_count, msg);
   
   END LOOP;
  
END;
$BODY$;

\COPY (select * from temp_messages order by csvrowid) to 'ks_students_enrollments_to_inactive_unused_pending_students_tests_msgs.csv' DELIMITER ',' CSV HEADER ;
