-- US18918 : EP: Prod - Request to correct move tests and correct dual enrollment for KAP students
-- Correcting perfomance tests and stage 4 tests in dual enrollments

DROP FUNCTION IF EXISTS moveAndInactivateStudentsTestsFromOldSchoolToNewSchool(BIGINT, BIGINT, BIGINT, BIGINT);

CREATE OR REPLACE FUNCTION moveAndInactivateStudentsTestsFromOldSchoolToNewSchool(correct_enrollmentid BIGINT, correct_sch_testsessionid BIGINT,
         correct_sch_stuentstestid BIGINT, old_sch_studentstestid BIGINT) RETURNS TEXT AS
$BODY$
DECLARE

cetesysadminid BIGINT;
update_count INTEGER;
msg TEXT;

BEGIN

    SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;

    msg := '';     

    WITH updateedStudentstestsCount AS(UPDATE studentstests SET enrollmentid = correct_enrollmentid, modifieddate = now(), modifieduser = cetesysadminid, testsessionid = correct_sch_testsessionid
               WHERE id = old_sch_studentstestid RETURNING 1) SELECT count(*) FROM updateedStudentstestsCount INTO update_count;
               
    msg := msg || 'Updated the testsession on  studentstestsid ' || old_sch_studentstestid || ' to ' || correct_sch_testsessionid
                                         || ' Total records updated: ' || update_count || '   ';
                                                    
    WITH studentresponsecount AS(UPDATE studentsresponses SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid 
             WHERE studentstestsid = correct_sch_stuentstestid RETURNING 1) SELECT count(*) FROM studentresponsecount INTO update_count;

    msg := msg || ' Inactivated the studentsresponses with studentstestsid ' || correct_sch_stuentstestid || ' Total studentsresponses inactivated: ' || update_count || ' ';


    WITH studentstestsectionscount AS(UPDATE studentstestsections SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid 
           WHERE studentstestid = correct_sch_stuentstestid RETURNING 1) SELECT count(*) FROM studentstestsectionscount INTO update_count;

    msg := msg || ' Inactivated the studentstestsections with studentstestsid ' || correct_sch_stuentstestid || ' Total studentstestsections inactivated: ' || update_count || ' ';
                              
    WITH stuentstestscount AS(UPDATE studentstests SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid 
          WHERE id = correct_sch_stuentstestid RETURNING 1) SELECT count(*) FROM stuentstestscount INTO update_count;

    msg := msg || ' Inactivated the studentstests with studentstestsid ' || correct_sch_stuentstestid || ' Total studentstests inactivated: ' || update_count || ' ';

RETURN msg;

END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;



DROP FUNCTION IF EXISTS inactivatestudenttestFromOldSchool(BIGINT);

CREATE OR REPLACE FUNCTION inactivatestudenttestFromOldSchool(old_sch_studentstestid BIGINT) RETURNS TEXT AS

$BODY$

DECLARE 

DECLARE

cetesysadminid BIGINT;
update_count INTEGER;
msg TEXT;


BEGIN

     SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;

     msg := '';   

     WITH inactivestudentsresponsescount AS(UPDATE studentsresponses SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid 
           WHERE studentstestsid = old_sch_studentstestid RETURNING 1) SELECT count(*) FROM inactivestudentsresponsescount INTO update_count;

     msg := msg || ' Inactivated studentsreponses count is : ' || update_count;
                               
     WITH inactivestudenttestsectionscount AS(UPDATE studentstestsections SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid 
          WHERE studentstestid = old_sch_studentstestid RETURNING 1) SELECT count(*) FROM inactivestudenttestsectionscount INTO update_count;

     msg := msg || ' Inactivated studentstestsections count is : ' || update_count;  
                              
     WITH inactivestudentstests AS (UPDATE studentstests SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid 
          WHERE id = old_sch_studentstestid RETURNING 1) SELECT count(*) FROM inactivestudentstests INTO update_count;

     msg := msg || ' Inactivated studentstests count is : ' || update_count;


RETURN msg;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;


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
 perf_stageid BIGINT;
 rtrn_msg TEXT;
 stage4_id BIGINT;
 

BEGIN

   SELECT id FROM organization WHERE displayidentifier = 'KS' INTO ks_state_id;
   SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
   SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress' INTO inprogress_studentstests_status;
   SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused' INTO unused_studentstests_status;
   SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete' INTO complete_studentstests_status;
   SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending' INTO pending_studentstests_status;

   SELECT id FROM stage WHERE code = 'Prfrm' INTO perf_stageid;
   SELECT id FROM stage WHERE code = 'Stg4' INTO stage4_id;
   
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
          rtrn_msg := '';
             
          FOR currentenrollment_record IN (SELECT id FROM enrollment WHERE studentid = student_id AND aypschoolid = current_ayp_school_id AND attendanceschoolid = current_attendance_school_id
             AND currentschoolyear = 2016 AND activeflag is true) LOOP         


              FOR enrollmentneedto_inactivate IN (SELECT id FROM enrollment WHERE studentid = student_id AND currentschoolyear = 2016 AND activeflag is true
                  EXCEPT (SELECT id FROM enrollment WHERE studentid = student_id AND aypschoolid = current_ayp_school_id AND attendanceschoolid = current_attendance_school_id
                         AND currentschoolyear = 2016 AND activeflag is true))  LOOP

                 SELECT displayidentifier FROM organization WHERE id = (SELECT attendanceschoolid FROM enrollment WHERE id = enrollmentneedto_inactivate.id) INTO old_att_sch_displayidentifier;
                 SELECT displayidentifier FROM organization WHERE id = (SELECT aypschoolid FROM enrollment WHERE id = enrollmentneedto_inactivate.id) INTO old_ayp_sch_displayidentifier;

                 msg := msg || ' Correcting the perfomance tests data  in between enrollmentid: ' || currentenrollment_record.id || ' aypschool is ' || current_ayp_school_id 
                            || ' and attendanceschool is ' || current_attendance_school_id
                            || '  Incorrect Enrollmentid: ' || enrollmentneedto_inactivate.id || ' aypschool is ' || old_ayp_sch_displayidentifier || ' and attendanceschool is ' || old_att_sch_displayidentifier;
                 

                 FOR old_sch_tests IN (SELECT st.id as studentstestsid, st.status, st.testcollectionid, st.testid, st.testsessionid, st.scores FROM studentstests st
                           JOIN testsession ts ON ts.id = st.testsessionid WHERE st.enrollmentid = enrollmentneedto_inactivate.id AND st.studentid = student_id AND st.activeflag IS true AND ts.stageid IN (perf_stageid, stage4_id)) LOOP                   
                   
                   IF((SELECT count(st.*) FROM studentstests st WHERE st.enrollmentid = currentenrollment_record.id AND st.studentid = student_id AND st.testcollectionid = old_sch_tests.testcollectionid AND st.activeflag is true) < 1) THEN

                         IF((SELECT count(*) FROM testsession WHERE testcollectionid = old_sch_tests.testcollectionid AND attendanceschoolid = current_attendance_school_id) >= 1) THEN
                         
                             SELECT id FROM testsession WHERE testcollectionid = old_sch_tests.testcollectionid AND attendanceschoolid = current_attendance_school_id INTO current_attsch_testsession_id;
                             
                              WITH updateedStudentstestsCount AS(UPDATE studentstests SET enrollmentid = currentenrollment_record.id, modifieddate = now(), modifieduser = cetesysadminid, testsessionid = current_attsch_testsession_id
                                     WHERE id = old_sch_tests.studentstestsid RETURNING 1) 
                                   SELECT count(*) FROM updateedStudentstestsCount INTO update_count;

                              msg := msg || ' Updated the testsession on  studentstestsid' || old_sch_tests.studentstestsid || ' from ' || old_sch_tests.testsessionid || ' to ' || current_attsch_testsession_id  
                                         || ' Total records updated: ' || update_count || '   ';

                          ELSE
                           
                              msg := msg || ' <ERROR> No testsessions found to move from old school ' || old_att_sch_displayidentifier || ' to new school ' || current_attendance_school_id || ' with testcollectionid %' 
                                  || old_sch_tests .testcollectionid || '  ';

                          END IF; 

                   ELSE
                   
                     FOR current_sch_tests IN (SELECT st.id as studentstestsid, st.status, st.testcollectionid, st.testid, st.testsessionid, st.scores FROM studentstests st 
                            WHERE st.enrollmentid = currentenrollment_record.id AND st.studentid = student_id AND st.activeflag IS true AND st.testcollectionid = old_sch_tests.testcollectionid) LOOP

                         IF((current_sch_tests.status = inprogress_studentstests_status OR current_sch_tests.status = unused_studentstests_status OR current_sch_tests.status = pending_studentstests_status) 
                                    AND old_sch_tests.status = complete_studentstests_status) THEN

                              msg := msg || ' Student had completed the test in old school and in the current school student test is in either inprogress or unused or pending. So inactivating the students tests in new school. ';

                              SELECT  moveAndInactivateStudentsTestsFromOldSchoolToNewSchool(correct_enrollmentid := currentenrollment_record.id, correct_sch_testsessionid := current_sch_tests.testsessionid,
                                      correct_sch_stuentstestid := current_sch_tests.studentstestsid, old_sch_studentstestid := old_sch_tests.studentstestsid) INTO rtrn_msg;
                                      
                              msg := msg || rtrn_msg;


                         ELSIF ((old_sch_tests.status = inprogress_studentstests_status) AND current_sch_tests.status = complete_studentstests_status) THEN

                              msg := msg || ' Inactivating the old school studentstests and scections and responses as students tests were in inprogress and in current school student completed the test ';

                              SELECT inactivatestudenttestFromOldSchool(old_sch_studentstestid := old_sch_tests.studentstestsid) INTO rtrn_msg;
                              
                              msg := msg || rtrn_msg;

                        ELSIF(current_sch_tests.status = complete_studentstests_status AND old_sch_tests.status = complete_studentstests_status) THEN

                                msg := msg || ' In both schools student finished the tests, correcting the studentstests based on score ';

                                IF(((SELECT sum(score) FROM studentsresponses WHERE studentstestsid = old_sch_tests.studentstestsid and activeflag is true) > (SELECT sum(score) FROM studentsresponses WHERE studentstestsid = current_sch_tests.studentstestsid and activeflag is true))
                                    OR ((SELECT count(*) FROM studentsresponses WHERE studentstestsid = old_sch_tests.studentstestsid and activeflag is true) > (SELECT count(*) FROM studentsresponses WHERE studentstestsid = current_sch_tests.studentstestsid and activeflag is true))) THEN
                                                              
                                     SELECT  moveAndInactivateStudentsTestsFromOldSchoolToNewSchool(correct_enrollmentid := currentenrollment_record.id, correct_sch_testsessionid := current_sch_tests.testsessionid,
                                      correct_sch_stuentstestid := current_sch_tests.studentstestsid, old_sch_studentstestid := old_sch_tests.studentstestsid) INTO rtrn_msg;
                                      
                                     msg := msg || rtrn_msg;			            

                                ELSE 

                                     SELECT inactivatestudenttestFromOldSchool(old_sch_studentstestid := old_sch_tests.studentstestsid) INTO rtrn_msg;
                                     msg := msg || rtrn_msg;

                                END IF;

                         END IF;

                      END LOOP;

                   END IF;

                 END LOOP;
           
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

\COPY (select * from temp_messages order by csvrowid) to 'ks_students_enrollments_to_inactive_correcting_perf_tests.csv' DELIMITER ',' CSV HEADER ;