 
--DE5412 
CREATE OR REPLACE VIEW total_item_summary_by_node AS 
 SELECT st.id AS students_tests_id, st.studentid AS student_id, 
    st.testid AS test_id, st.testcollectionid AS test_collection_id, 
    tvln.nodecode AS node_key, cfd.contentcode AS content_framework_detail_code, 
    cfd.description AS content_framework_detail_description, 
    count(1) AS total_items_presented,
    tss.name as test_session_name
   FROM studentstests st, category ststat, categorytype metatype, 
    testsection ts, testsectionstaskvariants tstv, 
    taskvariantcontentframeworkdetail tvcfd, contentframeworkdetail cfd, 
    taskvariantlearningmapnode tvln, testsession tss
  WHERE st.testsessionid = tss.id AND tvcfd.contentframeworkdetailid = cfd.id AND tvcfd.isprimary = true AND tvcfd.taskvariantid = tstv.taskvariantid AND st.testid = ts.testid AND tstv.testsectionid = ts.id AND st.status = ststat.id AND ststat.categorycode::text <> 'unused'::text AND metatype.id = ststat.categorytypeid AND metatype.typecode::text = 'STUDENT_TEST_STATUS'::text AND tstv.taskvariantid = tvln.taskvariantid
  GROUP BY st.id, st.studentid, st.testid, tvln.nodecode, cfd.contentcode, cfd.description, tss.name;

ALTER TABLE total_item_summary_by_node
  OWNER TO aart;
GRANT ALL ON TABLE total_item_summary_by_node TO aart;
GRANT SELECT ON TABLE total_item_summary_by_node TO aart_reader;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE total_item_summary_by_node TO aart_user;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE total_item_summary_by_node TO etl_user;

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
    ti_summary.test_session_name
   FROM student s, test t, testcollectionstests tct, testcollection tc, 
    total_item_summary_by_node ti_summary
   LEFT JOIN correct_response_summary_by_node cr_summary ON ti_summary.students_tests_id = cr_summary.students_tests_id AND ti_summary.node_key::text = cr_summary.node_key::text AND ti_summary.content_framework_detail_code::text = cr_summary.content_framework_detail_code::text
   LEFT JOIN in_correct_response_summary_by_node ir_summary ON ti_summary.students_tests_id = ir_summary.students_tests_id AND ti_summary.node_key::text = ir_summary.node_key::text AND ti_summary.content_framework_detail_code::text = ir_summary.content_framework_detail_code::text
   LEFT JOIN answered_item_summary_by_node ai_summary ON ti_summary.students_tests_id = ai_summary.students_tests_id AND ti_summary.node_key::text = ai_summary.node_key::text AND ti_summary.content_framework_detail_code::text = ai_summary.content_framework_detail_code::text
  WHERE tct.testid = t.id AND tct.testcollectionid = tc.id AND ti_summary.student_id = s.id AND ti_summary.test_id = t.id;
  
  ALTER TABLE node_report
  OWNER TO aart;
GRANT ALL ON TABLE node_report TO aart;
GRANT SELECT ON TABLE node_report TO aart_reader;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE node_report TO aart_user;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE node_report TO etl_user;
