--US18270: ServiceRequest: remove “Not started" KAP ELA Stage 4 sessions.
DO
$BODY$
DECLARE

ks_state_id BIGINT;
update_count INTEGER;
ela_contentarea_id BIGINT;
stage4_id BIGINT;
cetesysadminid BIGINT;
unused_studentstests_status_id BIGINT;
pending_studentstests_status_id BIGINT;

BEGIN

     SELECT id FROM organization WHERE displayidentifier = 'KS' INTO ks_state_id;
     SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
     SELECT id FROM contentarea WHERE abbreviatedname = 'ELA' INTO ela_contentarea_id;
     SELECT id FROM stage WHERE code = 'Stg4' INTO stage4_id;
     SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused' INTO unused_studentstests_status_id;
     SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending' INTO pending_studentstests_status_id;

     WITH inactivated_studentstestsections AS (UPDATE studentstestsections SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid
           WHERE studentstestid IN (SELECT st.id FROM testsession ts JOIN studentstests st ON st.testsessionid = ts.id JOIN testcollection tc ON tc.id = ts.testcollectionid
             WHERE ts.stageid = stage4_id AND ts.attendanceschoolid IN (select schoolid from organizationtreedetail where stateid = ks_state_id) 
             AND tc.contentareaid  = ela_contentarea_id AND st.status IN (unused_studentstests_status_id, pending_studentstests_status_id) AND st.activeflag IS true) RETURNING 1)
        SELECT count(*) FROM inactivated_studentstestsections INTO update_count;

     RAISE NOTICE 'Updatded studentstestcollections: %', update_count;

     WITH inactivated_studentstests AS (UPDATE studentstests SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid
           WHERE id IN (SELECT st.id FROM testsession ts JOIN studentstests st ON st.testsessionid = ts.id JOIN testcollection tc ON tc.id = ts.testcollectionid
             WHERE ts.stageid = stage4_id AND ts.attendanceschoolid IN (select schoolid from organizationtreedetail where stateid = ks_state_id) 
             AND tc.contentareaid  = ela_contentarea_id AND st.status IN (unused_studentstests_status_id, pending_studentstests_status_id) AND st.activeflag IS true) RETURNING 1)
        SELECT count(*) FROM inactivated_studentstests INTO update_count;     

     RAISE NOTICE 'Updatded studentstests: %', update_count;

END;
$BODY$;
