-- US18918 : EP: Prod - Request to correct move tests and correct dual enrollment for KAP students
-- Correcting Adaptive tests and inactivating enrollments

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
 stage1_id BIGINT;
 stage2_id BIGINT;
 stage3_id BIGINT; 


 old_sch_max_stageid BIGINT;
 new_sch_max_stageid BIGINT;

 old_sch_stage_testdetails RECORD;
 new_sch_stage_testdetails RECORD;
 previoustestdetails RECORD;
 studentsteststomove RECORD;
 contentarea_record RECORD;
 rtn_msg TEXT;
 nextstagefound BOOLEAN;
 istestmoved BOOLEAN;
 

BEGIN

   SELECT id FROM organization WHERE displayidentifier = 'KS' INTO ks_state_id;
   SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
   SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress' INTO inprogress_studentstests_status;
   SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused' INTO unused_studentstests_status;
   SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete' INTO complete_studentstests_status;
   SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending' INTO pending_studentstests_status;

   SELECT id FROM stage WHERE code = 'Stg1' INTO stage1_id;
   SELECT id FROM stage WHERE code = 'Stg2' INTO stage2_id;
   SELECT id FROM stage WHERE code = 'Stg3' INTO stage3_id;
   
   
   row_count := 1;
    msg := '';
    rtn_msg := '';
    istestmoved := FALSE; 

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
             
             msg := msg || ' Inactivating all pending status tests from correct school ';
             
             WITH inactivestudenttestsectionscount AS(UPDATE studentstestsections SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid 
                    WHERE studentstestid IN (SELECT st.id FROM studentstests st WHERE st.enrollmentid = currentenrollment_record.id AND st.studentid = student_id AND st.activeflag IS true 
                    AND st.status IN (pending_studentstests_status)) RETURNING 1) SELECT count(*) FROM inactivestudenttestsectionscount INTO update_count;

             msg := msg || ' Inactivated studentstestsections count is : ' || update_count;  
                              
             WITH inactivestudentstests AS (UPDATE studentstests SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid 
                    WHERE enrollmentid = currentenrollment_record.id AND studentid = student_id AND activeflag IS true 
                    AND status IN (pending_studentstests_status)  RETURNING 1) SELECT count(*) FROM inactivestudentstests INTO update_count;

                 msg := msg || ' Inactivated studentstests count is : ' || update_count;


              FOR enrollmentneedto_inactivate IN (SELECT id FROM enrollment WHERE studentid = student_id AND currentschoolyear = 2016 AND activeflag is true
                  EXCEPT (SELECT id FROM enrollment WHERE studentid = student_id AND aypschoolid = current_ayp_school_id AND attendanceschoolid = current_attendance_school_id
                         AND currentschoolyear = 2016 AND activeflag is true))  LOOP                         

                 SELECT displayidentifier FROM organization WHERE id = (SELECT attendanceschoolid FROM enrollment WHERE id = enrollmentneedto_inactivate.id) INTO old_att_sch_displayidentifier;
                 SELECT displayidentifier FROM organization WHERE id = (SELECT aypschoolid FROM enrollment WHERE id = enrollmentneedto_inactivate.id) INTO old_ayp_sch_displayidentifier;

                 msg := msg || ' Correcting KAP Adaptive tests and inactivating the incorrect enrollments: currentschool enrollmentid ' || currentenrollment_record.id || ' aypschool is ' ||  temp_stuenrlrecord.aypschooldisplayidentifier
                            || ' and attendanceschool is ' || temp_stuenrlrecord.attendanceschooldisplayidentifier
                            || ' Enrollment to inactive ' || enrollmentneedto_inactivate.id || ' aypschool is ' || old_ayp_sch_displayidentifier || ' and attendanceschool is ' || old_att_sch_displayidentifier;


                 
                 FOR old_sch_tests IN (SELECT st.id as studentstestsid, st.status, st.testcollectionid, st.testid, st.testsessionid, st.scores, st.previousstudentstestid, ts.stageid FROM studentstests st
                           JOIN testsession ts ON ts.id = st.testsessionid WHERE st.enrollmentid = enrollmentneedto_inactivate.id AND st.studentid = student_id AND st.activeflag IS true 
                           AND ts.stageid IN (stage1_id, stage2_id, stage3_id) order by ts.stageid desc) LOOP                   
                   
                   IF((SELECT count(st.*) FROM studentstests st WHERE st.enrollmentid = currentenrollment_record.id AND st.studentid = student_id AND st.testcollectionid = old_sch_tests.testcollectionid AND st.activeflag is true) < 1) THEN

                         IF((SELECT count(*) FROM testsession WHERE testcollectionid = old_sch_tests.testcollectionid AND attendanceschoolid = current_attendance_school_id) >= 1) THEN
                         
                             SELECT id FROM testsession WHERE testcollectionid = old_sch_tests.testcollectionid AND attendanceschoolid = current_attendance_school_id INTO current_attsch_testsession_id;
                             
                              WITH updateedStudentstestsCount AS(UPDATE studentstests SET enrollmentid = currentenrollment_record.id, modifieddate = now(), modifieduser = cetesysadminid, testsessionid = current_attsch_testsession_id
                                     WHERE id = old_sch_tests.studentstestsid RETURNING 1) 
                                   SELECT count(*) FROM updateedStudentstestsCount INTO update_count;

                              msg := msg || ' Updated the testsession on  studentstestsid' || old_sch_tests.studentstestsid || ' from ' || old_sch_tests.testsessionid || ' to ' || current_attsch_testsession_id  
                                         || ' Total records updated: ' || update_count || '   ';
                                         
                              SELECT * FROM studentstests WHERE id = old_sch_tests.previousstudentstestid INTO previoustestdetails;
                              
                              nextstagefound := true;

                              WHILE nextstagefound IS TRUE LOOP
                                               
                                 IF(previoustestdetails.enrollmentid = enrollmentneedto_inactivate.id) THEN

                                    SELECT * FROM studentstests WHERE enrollmentid = currentenrollment_record.id 
                                         AND testcollectionid = previoustestdetails.testcollectionid INTO studentsteststomove;
                                     
                                    SELECT id FROM testsession WHERE testcollectionid = previoustestdetails.testcollectionid
                                             AND attendanceschoolid = current_attendance_school_id INTO current_attsch_testsession_id;

                                     IF(current_attsch_testsession_id IS NULL) THEN

                                          msg := msg || ' <Error> student testid: ' || previoustestdetails.id || ' is not moving to correct school ' ||
                                                   current_attendance_school_id || ' beacuse not testsessions found in school with testcollectionid: ' 
                                                   || previoustestdetails.testcollectionid || '. From here no back tracked tests will move to correct school';            
                                          nextstagefound := FALSE;
                                      
                                     ELSE 

                                      SELECT moveAndInactivateStudentsTestsFromOldSchoolToNewSchool(correct_enrollmentid := currentenrollment_record.id, 
                                        correct_sch_testsessionid := current_attsch_testsession_id,
                                         correct_sch_stuentstestid := studentsteststomove.id, old_sch_studentstestid := previoustestdetails.id) INTO rtn_msg;

                                    msg := msg || rtn_msg;                              
                                    END IF;                         
                                 END IF;
                          
                                 SELECT * FROM studentstests WHERE id = previoustestdetails.previousstudentstestid INTO previoustestdetails;

                                 IF(previoustestdetails.previousstudentstestid IS NULL) THEN
                                    
                                      IF(previoustestdetails.enrollmentid = enrollmentneedto_inactivate.id) THEN
                                      
                                         SELECT * FROM studentstests WHERE enrollmentid = currentenrollment_record.id 
                                           AND testcollectionid = previoustestdetails.testcollectionid INTO studentsteststomove;
                                           
                                         SELECT id FROM testsession WHERE testcollectionid = previoustestdetails.testcollectionid
                                             AND attendanceschoolid = current_attendance_school_id INTO current_attsch_testsession_id;

                                         IF(current_attsch_testsession_id IS NULL) THEN

                                          msg := msg || ' <Error> student testid: ' || previoustestdetails.id || ' is not moving to correct school ' ||
                                                   current_attendance_school_id || ' beacuse not testsessions found in school with testcollectionid: ' 
                                                   || previoustestdetails.testcollectionid || '. From here no back tracked tests will move to correct school';            
                                          nextstagefound := FALSE;
                                      
                                         ELSE 

                                          SELECT moveAndInactivateStudentsTestsFromOldSchoolToNewSchool(correct_enrollmentid := currentenrollment_record.id, 
                                           correct_sch_testsessionid := current_attsch_testsession_id,
                                           correct_sch_stuentstestid := studentsteststomove.id, old_sch_studentstestid := previoustestdetails.id) INTO rtn_msg;

                                         msg := msg || rtn_msg;                              
                                        END IF;
                                      
                                      END IF;
                                    nextstagefound := FALSE;
                                 END IF;
                          
                              END LOOP;  
                              
                              

                          ELSE
                           
                              msg := msg || ' <ERROR> No testsessions found to move from old school ' || old_att_sch_displayidentifier || ' to new school ' || current_attendance_school_id || ' with testcollectionid %' 
                                  || old_sch_tests .testcollectionid || '  ';

                          END IF;
                       END IF;
                   END LOOP;

                 FOR contentarea_record IN (SELECT id, abbreviatedname FROM contentarea WHERE abbreviatedname IN ('ELA', 'M')) LOOP
                 
                  istestmoved := FALSE;

                  msg := msg || 'For Subject: ' || contentarea_record.abbreviatedname || ' ';

                   SELECT max(stage.id)  FROM studentstests st JOIN testsession ts ON st.testsessionid = ts.id  JOIN testcollection tc ON st.testcollectionid = tc.id
                          JOIN stage stage ON ts.stageid = stage.id WHERE st.enrollmentid = enrollmentneedto_inactivate.id AND st.activeflag IS true                               
                          AND ts.stageid IN (stage1_id, stage2_id, stage3_id) AND tc.contentareaid = contentarea_record.id INTO old_sch_max_stageid;

                        
                   SELECT max(stage.id)  FROM studentstests st JOIN testsession ts ON st.testsessionid = ts.id  JOIN testcollection tc ON st.testcollectionid = tc.id
                          JOIN stage stage ON ts.stageid = stage.id WHERE st.enrollmentid = currentenrollment_record.id AND st.activeflag IS true                               
                          AND ts.stageid IN (stage1_id, stage2_id, stage3_id) AND tc.contentareaid = contentarea_record.id INTO new_sch_max_stageid;


                    SELECT st.id, st.enrollmentid, st.previousstudentstestid, st.status,st.testsessionid FROM studentstests st JOIN testsession ts ON st.testsessionid = ts.id  
                          JOIN testcollection tc ON st.testcollectionid = tc.id JOIN stage stage ON ts.stageid = stage.id 
                          WHERE st.enrollmentid = enrollmentneedto_inactivate.id AND st.activeflag IS true                           
                          AND ts.stageid =  old_sch_max_stageid AND tc.contentareaid = contentarea_record.id LIMIT 1 INTO old_sch_stage_testdetails;

                          
                    SELECT st.id, st.enrollmentid, st.previousstudentstestid, st.status, st.testsessionid FROM studentstests st JOIN testsession ts ON st.testsessionid = ts.id  
                          JOIN testcollection tc ON st.testcollectionid = tc.id JOIN stage stage ON ts.stageid = stage.id 
                          WHERE st.enrollmentid = currentenrollment_record.id AND st.activeflag IS true                           
                          AND ts.stageid =  new_sch_max_stageid AND tc.contentareaid = contentarea_record.id LIMIT 1 INTO new_sch_stage_testdetails;

                    IF(old_sch_max_stageid = new_sch_max_stageid) THEN                                                  

                         IF((new_sch_stage_testdetails.status = inprogress_studentstests_status OR new_sch_stage_testdetails.status = unused_studentstests_status) 
                               AND (old_sch_stage_testdetails.status = complete_studentstests_status)) THEN

                            msg := msg || ' Student had completed the test in old school and in the current school student test is in either inprogress or unused or pending. So inactivating the students tests in new school. ';
                                                         
                            SELECT moveAndInactivateStudentsTestsFromOldSchoolToNewSchool(correct_enrollmentid := currentenrollment_record.id, correct_sch_testsessionid := new_sch_stage_testdetails.testsessionid,
                                      correct_sch_stuentstestid := new_sch_stage_testdetails.id, old_sch_studentstestid := old_sch_stage_testdetails.id) INTO rtn_msg;
                            
                            msg := msg || rtn_msg;

                            istestmoved := TRUE;
                                                                                                             
                         ELSIF(old_sch_stage_testdetails.status = complete_studentstests_status 
                                 AND new_sch_stage_testdetails.status = complete_studentstests_status) THEN

                              msg := msg || ' In both schools student finished the tests, correcting the studentstests based on score ';

                              IF(((SELECT sum(score) FROM studentsresponses WHERE studentstestsid = old_sch_stage_testdetails.id and activeflag is true) >= (SELECT sum(score) FROM studentsresponses WHERE studentstestsid = new_sch_stage_testdetails.id and activeflag is true))
                                    OR ((SELECT count(*) FROM studentsresponses WHERE studentstestsid = old_sch_stage_testdetails.id and activeflag is true) >= (SELECT count(*) FROM studentsresponses WHERE studentstestsid = new_sch_stage_testdetails.id and activeflag is true))) THEN
                                                              
                                   SELECT moveAndInactivateStudentsTestsFromOldSchoolToNewSchool(correct_enrollmentid := currentenrollment_record.id, correct_sch_testsessionid := new_sch_stage_testdetails.testsessionid,
                                      correct_sch_stuentstestid := new_sch_stage_testdetails.id, old_sch_studentstestid := old_sch_stage_testdetails.id) INTO rtn_msg;

                                   msg := msg || rtn_msg;

                                   istestmoved := TRUE;

                                END IF;
                         ELSIF(old_sch_stage_testdetails.status = inprogress_studentstests_status AND new_sch_stage_testdetails.status = unused_studentstests_status) THEN

                            msg := msg || ' Studentstests is INPROGRESS in old school and in the current school student test is in unused status. So inactivating the students tests in new school. ';
                                                         
                            SELECT moveAndInactivateStudentsTestsFromOldSchoolToNewSchool(correct_enrollmentid := currentenrollment_record.id, correct_sch_testsessionid := new_sch_stage_testdetails.testsessionid,
                                      correct_sch_stuentstestid := new_sch_stage_testdetails.id, old_sch_studentstestid := old_sch_stage_testdetails.id) INTO rtn_msg;
                            
                            msg := msg || rtn_msg;

                            istestmoved := TRUE;                                                      
                         END IF;                                            
                    
                   END IF;
				   IF(old_sch_max_stageid != stage1_id AND istestmoved = TRUE)  THEN

                      SELECT * FROM studentstests WHERE id = old_sch_stage_testdetails.previousstudentstestid INTO previoustestdetails;
                   ELSE
                      SELECT * FROM studentstests WHERE id = new_sch_stage_testdetails.previousstudentstestid INTO previoustestdetails;

                   END IF;                      
                      nextstagefound := true;
                       
                      WHILE nextstagefound IS TRUE LOOP
                       
                      IF(previoustestdetails.enrollmentid = enrollmentneedto_inactivate.id) THEN

                        SELECT * FROM studentstests WHERE enrollmentid = currentenrollment_record.id 
                             AND testcollectionid = previoustestdetails.testcollectionid INTO studentsteststomove;

                        SELECT id FROM testsession WHERE testcollectionid = previoustestdetails.testcollectionid
                                             AND attendanceschoolid = current_attendance_school_id INTO current_attsch_testsession_id;

                        IF(current_attsch_testsession_id IS NULL) THEN

                             msg := msg || ' <Error> student testid: ' || previoustestdetails.id || ' is not moving to correct school ' ||
                                  current_attendance_school_id || ' beacuse not testsessions found in school with testcollectionid: ' 
                                 || previoustestdetails.testcollectionid || '. From here no back tracked tests will move to correct school';            
                            nextstagefound := FALSE;
                                      
                        ELSE 

                           SELECT moveAndInactivateStudentsTestsFromOldSchoolToNewSchool(correct_enrollmentid := currentenrollment_record.id, 
                                 correct_sch_testsessionid := current_attsch_testsession_id,
                                correct_sch_stuentstestid := studentsteststomove.id, old_sch_studentstestid := previoustestdetails.id) INTO rtn_msg;

                           msg := msg || rtn_msg;                              
                        END IF;                              
                                                             
                        END IF;
                          
                        SELECT * FROM studentstests WHERE id = previoustestdetails.previousstudentstestid INTO previoustestdetails;

                        IF(previoustestdetails.previousstudentstestid IS NULL) THEN

                            IF(previoustestdetails.enrollmentid = enrollmentneedto_inactivate.id) THEN
                                      
                               SELECT * FROM studentstests WHERE enrollmentid = currentenrollment_record.id 
                                     AND testcollectionid = previoustestdetails.testcollectionid INTO studentsteststomove;

                               SELECT id FROM testsession WHERE testcollectionid = previoustestdetails.testcollectionid
                                             AND attendanceschoolid = current_attendance_school_id INTO current_attsch_testsession_id;

                              IF(current_attsch_testsession_id IS NULL) THEN

                                msg := msg || ' <Error> student testid: ' || previoustestdetails.id || ' is not moving to correct school ' ||
                                        current_attendance_school_id || ' beacuse not testsessions found in school with testcollectionid: ' 
                                       || previoustestdetails.testcollectionid || '. From here no back tracked tests will move to correct school';            
                               nextstagefound := FALSE;
                         
                             ELSE 

                               SELECT moveAndInactivateStudentsTestsFromOldSchoolToNewSchool(correct_enrollmentid := currentenrollment_record.id, 
                                     correct_sch_testsessionid := current_attsch_testsession_id,
                                     correct_sch_stuentstestid := studentsteststomove.id, old_sch_studentstestid := previoustestdetails.id) INTO rtn_msg;
                               msg := msg || rtn_msg;                              
                             END IF;
                                      
                            END IF;

                           nextstagefound := FALSE;

                        END IF;
                          
                   END LOOP;
                 END LOOP;                 

                msg := msg || ' Inactivating all the students tests from incorrect school with enrollmentid: ' || enrollmentneedto_inactivate.id || ' ';

                WITH studentstestInactivateCount AS(UPDATE studentstests SET activeflag = false,modifieddate = now(), modifieduser = cetesysadminid
                       WHERE enrollmentid = enrollmentneedto_inactivate.id AND activeflag IS true RETURNING 1) 
                       SELECT count (*) FROM studentstestInactivateCount INTO update_count;

                msg := msg || ' Total studentstests inactivated: ' || update_count || ' ';
                 
                 WITH enrollmentInactivateCount AS (UPDATE enrollment SET activeflag = false,modifieddate = now(), modifieduser = cetesysadminid, exitwithdrawaltype = -55, exitwithdrawaldate = now(),
                        notes = 'Inactivating enrollments as per US18198'
                        WHERE id = enrollmentneedto_inactivate.id RETURNING 1) SELECT count (*) FROM enrollmentInactivateCount INTO update_count;

                 msg := msg || ' Enrollment is inactivated with id '|| enrollmentneedto_inactivate.id || ' aypschool ' || old_ayp_sch_displayidentifier || '  and attendanceschool ' || old_att_sch_displayidentifier 
                            || ' combination. Total enrollments inactivated: ' || update_count || '  ';

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

\COPY (select * from temp_messages order by csvrowid) to 'ks_students_enrollments_to_inactive_msgs.csv' DELIMITER ',' CSV HEADER ;
DROP TABLE IF EXISTS temp_messages;