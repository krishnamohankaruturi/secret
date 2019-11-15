
-- 315.sql
ALTER TABLE ititestsessionhistory ADD COLUMN activeflag boolean default true;
ALTER TABLE studentstests ADD COLUMN finalbandid bigint;


-- 315.sql
DROP INDEX IF EXISTS idx_profileitemattributenameattributecontainer_acid;
CREATE INDEX idx_profileitemattributenameattributecontainer_acid ON profileitemattributenameattributecontainer USING btree (attributecontainerid);

DROP INDEX IF EXISTS idx_profileitemattributenameattributecontainer_attributenameid;
CREATE INDEX idx_profileitemattributenameattributecontainer_attributenameid ON profileitemattributenameattributecontainer USING btree (attributenameid);

DROP INDEX IF EXISTS idx_profileitemattrnameattrcontainerviewoptions_apid;
CREATE INDEX idx_profileitemattrnameattrcontainerviewoptions_apid ON profileitemattrnameattrcontainerviewoptions USING btree (assessmentprogramid);

DROP INDEX IF EXISTS idx_profileitemattrnameattrcontainerviewoptions_pianacid;
CREATE INDEX idx_profileitemattrnameattrcontainerviewoptions_pianacid ON profileitemattrnameattrcontainerviewoptions USING btree (pianacid);
  
DROP INDEX IF EXISTS idx_studentprofileitemattributevalue_pianacid;
CREATE INDEX idx_studentprofileitemattributevalue_pianacid ON studentprofileitemattributevalue USING btree (profileitemattributenameattributecontainerid);

DROP INDEX IF EXISTS idx_profileitemattributecontainer_attributecontainer;
CREATE INDEX idx_profileitemattributecontainer_attributecontainer ON profileitemattributecontainer USING btree (attributecontainer);

DROP INDEX IF EXISTS idx_studentprofileitemattributevalue_selectedvalue;
CREATE INDEX idx_studentprofileitemattributevalue_selectedvalue ON studentprofileitemattributevalue USING btree (selectedvalue);

DROP INDEX IF EXISTS idx_studentprofileitemattributevalue_lselectedvalue;
CREATE INDEX idx_studentprofileitemattributevalue_lselectedvalue ON studentprofileitemattributevalue USING btree (lower(selectedvalue));

DROP INDEX IF EXISTS idx_studentprofileitemattributevalue_studentid;
CREATE INDEX idx_studentprofileitemattributevalue_studentid ON studentprofileitemattributevalue USING btree (studentid);

DROP INDEX IF EXISTS idx_enrollmenttesttypesubjectarea_testtypeid;
CREATE INDEX idx_enrollmenttesttypesubjectarea_testtypeid ON enrollmenttesttypesubjectarea USING btree (testtypeid);

DROP INDEX IF EXISTS idx_enrollmenttesttypesubjectarea_subjectareaid;
CREATE INDEX idx_enrollmenttesttypesubjectarea_subjectareaid ON enrollmenttesttypesubjectarea USING btree (subjectareaid);

DROP INDEX IF EXISTS idx_enrollmenttesttypesubjectarea_enrollmentid;
CREATE INDEX idx_enrollmenttesttypesubjectarea_enrollmentid ON enrollmenttesttypesubjectarea USING btree (enrollmentid);

DROP INDEX IF EXISTS idx_student_comprehensiverace;
CREATE INDEX idx_student_comprehensiverace ON student USING btree (comprehensiverace);

DROP VIEW node_report;

ALTER TABLE student ALTER COLUMN comprehensiverace TYPE character varying(5);

CREATE OR REPLACE VIEW node_report AS 
 SELECT s.id AS student_id, 
    s.statestudentidentifier AS state_student_identifier, 
    s.legalfirstname AS legal_first_name, 
    s.legalmiddlename AS legal_middle_name, s.legallastname AS legal_last_name, 
    s.comprehensiverace AS comprehensive_race, s.gender, 
    s.primarydisabilitycode AS primary_disability_code, ti_summary.node_key, 
    ti_summary.students_tests_id, ti_summary.test_id, t.testname AS test_name, 
    t.status AS test_status_id, tc.id AS test_collection_id, 
    tc.name AS test_collection_name, 
    nvl(cr_summary.no_of_correct_responses) AS no_of_correct_responses, 
    nvl(ir_summary.no_of_in_correct_responses) AS no_of_in_correct_responses, 
    nvl(cr_summary.no_of_correct_responses) + nvl(ir_summary.no_of_in_correct_responses) AS no_of_responses, 
    nvl(ai_summary.no_of_answered_items) AS no_of_answered_items, 
    nvl(cr_summary.total_raw_score::bigint) AS total_raw_score, 
    ti_summary.content_framework_detail_code, 
    ti_summary.content_framework_detail_description, 
    ti_summary.total_items_presented, ti_summary.test_session_name
   FROM student s, test t, testcollectionstests tct, testcollection tc, 
    total_item_summary_by_node ti_summary
   LEFT JOIN correct_response_summary_by_node cr_summary ON ti_summary.students_tests_id = cr_summary.students_tests_id AND ti_summary.node_key::text = cr_summary.node_key::text AND ti_summary.content_framework_detail_code::text = cr_summary.content_framework_detail_code::text
   LEFT JOIN in_correct_response_summary_by_node ir_summary ON ti_summary.students_tests_id = ir_summary.students_tests_id AND ti_summary.node_key::text = ir_summary.node_key::text AND ti_summary.content_framework_detail_code::text = ir_summary.content_framework_detail_code::text
   LEFT JOIN answered_item_summary_by_node ai_summary ON ti_summary.students_tests_id = ai_summary.students_tests_id AND ti_summary.node_key::text = ai_summary.node_key::text AND ti_summary.content_framework_detail_code::text = ai_summary.content_framework_detail_code::text
  WHERE tct.testid = t.id AND tct.testcollectionid = tc.id AND ti_summary.student_id = s.id AND ti_summary.test_id = t.id;