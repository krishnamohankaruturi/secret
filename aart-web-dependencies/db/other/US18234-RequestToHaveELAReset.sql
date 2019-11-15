-- US18234: EP: Prod - Request to have ELA reset
DO
$BODY$
DECLARE

  student_ids BIGINT[] := ARRAY[215394,215391,215392,213438,215388,215398,215389,499279,499291,620116,215393,215401,250304,1210711,215395,589573,733421,499283,566181,1031638,499290,1210710,499273];
  
  ela_subject_id BIGINT;
  update_count INTEGER;
  cete_sys_admin_id BIGINT;
  students_tests_record RECORD;
  msg TEXT;

BEGIN

    SELECT id FROM contentarea WHERE abbreviatedname = 'ELA' INTO ela_subject_id;

    SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cete_sys_admin_id;

    FOR i IN array_lower(student_ids, 1) .. array_upper(student_ids, 1) LOOP       

       msg := '';
           
       RAISE NOTICE 'For Student_ep_id: %', student_ids[i];
     
       FOR students_tests_record IN (SELECT st.id, ts.name, ts.attendanceschoolid FROM studentstests st 
            JOIN testsession ts ON ts.id = st.testsessionid JOIN testcollection tc ON tc.id = st.testcollectionid
            JOIN contentarea ca on ca.id = tc.contentareaid WHERE st.studentid = student_ids[i] AND ts.schoolyear = 2016 AND ca.id = ela_subject_id) LOOP

       msg := msg || ' testsession name: ' || students_tests_record.name || ' and attendanceschool: ' || students_tests_record.attendanceschoolid || '\n';
 
       WITH studentsresponses_inactivating_count AS (UPDATE studentsresponses SET activeflag = FALSE, modifieddate = now(), modifieduser = cete_sys_admin_id
               WHERE studentstestsid = students_tests_record.id RETURNING 1) SELECT count(*) FROM studentsresponses_inactivating_count INTO update_count;

       msg := msg || update_count  ||' studentsresponses were inactivated with studentstestsid: ' || students_tests_record.id || ' ';

       WITH  studentstestsections_inactivating_count AS (UPDATE studentstestsections SET activeflag = FALSE, modifieddate = now(), modifieduser = cete_sys_admin_id
               WHERE studentstestid = students_tests_record.id RETURNING 1) SELECT count(*) FROM studentstestsections_inactivating_count INTO update_count;
         

       msg := msg || update_count  ||' studenttestsections were inactivated with studentstestsid: ' || students_tests_record.id || ' ';

       WITH  studentstests_inactivating_count AS (UPDATE studentstests SET activeflag = FALSE, modifieddate = now(), modifieduser = cete_sys_admin_id
               WHERE id = students_tests_record.id RETURNING 1) SELECT count(*) FROM studentstests_inactivating_count INTO update_count;
         

       msg := msg || update_count  ||' studenttests were inactivated with studentstestsid: ' || students_tests_record.id || ' ';

       RAISE NOTICE '%', msg;
 
       END LOOP;            
    END LOOP;

END;
$BODY$;



-- Inactivating ELA stage 3 and Stage 4 tests for student EP id: 158869 and enrollmentid: 1822886

UPDATE studentsresponses SET activeflag = FALSE, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
               WHERE studentstestsid IN(10158328, 12089246);


UPDATE studentstestsections SET activeflag = FALSE, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
               WHERE studentstestid IN (10158328, 12089246);

UPDATE studentstests SET activeflag = FALSE, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
               WHERE id IN  (10158328, 12089246);  