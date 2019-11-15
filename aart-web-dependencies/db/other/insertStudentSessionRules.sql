INSERT INTO test_collections_session_rules(
            test_collection_id, session_rule_id, 
            created_user, modified_user)
    (
    Select tc.id as testcollectionid,cat.id as session_rule_id,
92 as created_user,92 as modified_user from
 category cat,testcollection tc
  where categorytypeid = 35 and categorycode in
 ('SYSTEM_DEFINED_ENROLLMENT_TO_TEST',
'TICKETED_AT_TEST') and tc.id in (Select id from testcollection limit 10) 
);

INSERT INTO test_collections_session_rules(
            test_collection_id, session_rule_id, 
            created_user, modified_user)
    (
    Select tc.id as testcollectionid,cat.id as session_rule_id,
    92 as created_user,92 as modified_user
     from
 category cat,testcollection tc
  where categorytypeid = 35 and categorycode in ('MANUAL_DEFINED_ENROLLMENT_TO_TEST','TICKETED_AT_SECTION')
 and tc.id in (Select id from testcollection offset 10 limit 10)
 )

 INSERT INTO test_collections_session_rules(
            test_collection_id, session_rule_id, gracePeriod,
            created_user, modified_user)
    (
    Select tc.id as testcollectionid,cat.id as session_rule_id,10000 as gracePeriod,
    92 as created_user,92 as modified_user
     from
 category cat,testcollection tc
  where categorytypeid = 35 and categorycode in ('GRACE_PERIOD')
 and tc.id in (Select id from testcollection offset 10 limit 10)
 )