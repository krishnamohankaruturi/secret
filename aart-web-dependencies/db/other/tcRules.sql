INSERT INTO testcollectionssessionrules(
            testcollectionid, sessionruleid, 
            createduser, modifieduser)
    (
    Select tc.id as testcollectionid,cat.id as sessionruleid,
92 as createduser,92 as modifieduser from
 category cat,testcollection tc
  where categorytypeid = (Select id from categorytype where typecode='SESSION_RULES')
   and categorycode in
 ('SYSTEM_DEFINED_ENROLLMENT_TO_TEST',
'TICKETED_AT_TEST') and tc.id in (Select id from testcollection limit 35) 
);

INSERT INTO testcollectionssessionrules(
            testcollectionid, sessionruleid, 
            createduser, modifieduser)
    (
    Select tc.id as testcollectionid,cat.id as sessionruleid,
    92 as createduser,92 as modifieduser
     from
 category cat,testcollection tc
  where categorytypeid = (Select id from categorytype where typecode='SESSION_RULES')
   and categorycode in ('MANUAL_DEFINED_ENROLLMENT_TO_TEST','TICKETED_AT_SECTION')
 and tc.id in (Select id from testcollection offset 35 limit 35)
 and tc.id not in (select testcollectionid from testcollectionssessionrules)
 );

 INSERT INTO testcollectionssessionrules(
            testcollectionid, sessionruleid, gracePeriod,
            createduser, modifieduser)
    (
    Select tc.id as testcollectionid,cat.id as sessionruleid,10000 as gracePeriod,
    92 as createduser,92 as modifieduser
     from
 category cat,testcollection tc
  where categorytypeid = (Select id from categorytype where typecode='SESSION_RULES') and categorycode in ('GRACE_PERIOD')
 and tc.id in (Select id from testcollection)
  and tc.id not in (select testcollectionid from testcollectionssessionrules)
 );