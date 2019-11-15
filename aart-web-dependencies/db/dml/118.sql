
--DE5412
DROP VIEW node_report;

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
    ti_summary.total_items_presented,
    ts.name as test_session_name
   FROM student s, test t, testcollectionstests tct, testcollection tc, testsession ts, studentstests st, 
    total_item_summary_by_node ti_summary
   LEFT JOIN correct_response_summary_by_node cr_summary ON ti_summary.students_tests_id = cr_summary.students_tests_id AND ti_summary.node_key::text = cr_summary.node_key::text AND ti_summary.content_framework_detail_code::text = cr_summary.content_framework_detail_code::text
   LEFT JOIN in_correct_response_summary_by_node ir_summary ON ti_summary.students_tests_id = ir_summary.students_tests_id AND ti_summary.node_key::text = ir_summary.node_key::text AND ti_summary.content_framework_detail_code::text = ir_summary.content_framework_detail_code::text
   LEFT JOIN answered_item_summary_by_node ai_summary ON ti_summary.students_tests_id = ai_summary.students_tests_id AND ti_summary.node_key::text = ai_summary.node_key::text AND ti_summary.content_framework_detail_code::text = ai_summary.content_framework_detail_code::text
  WHERE tct.testid = t.id AND tct.testcollectionid = tc.id AND ti_summary.student_id = s.id AND ti_summary.test_id = t.id AND st.testid = t.id AND ts.id = st.testsessionid;

ALTER TABLE node_report
  OWNER TO aart;
GRANT ALL ON TABLE node_report TO aart;
GRANT SELECT ON TABLE node_report TO aart_reader;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE node_report TO aart_user;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE node_report TO etl_user;