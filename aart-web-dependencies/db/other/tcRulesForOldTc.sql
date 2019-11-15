INSERT INTO testcollectionssessionrules(
            testcollectionid, sessionruleid, 
            createduser, modifieduser)
    (
Select tc.id as testcollectionid,cat.id as sessionruleid,
(Select id from aartuser where username='cetesysadmin') as createduser,
(Select id from aartuser where username='cetesysadmin') as modifieduser
from
 category cat,testcollection tc
  where categorytypeid = (Select id from categorytype where typecode='SESSION_RULES')
   and categorycode in
 ('SYSTEM_DEFINED_ENROLLMENT_TO_TEST')
 and tc.id in (Select id from testcollection)
 and tc.id not in (Select testcollectionid
 from testcollectionssessionrules tcsr,category cat
 where tcsr.sessionruleid = cat.id and cat.categorycode='SYSTEM_DEFINED_ENROLLMENT_TO_TEST')
 );
 
INSERT INTO testcollectionssessionrules(
            testcollectionid, sessionruleid, 
            graceperiod,
            createduser, modifieduser)
    (
Select tc.id as testcollectionid,cat.id as sessionruleid,
5400000 as graceperiod,
(Select id from aartuser where username='cetesysadmin') as createduser,
(Select id from aartuser where username='cetesysadmin') as modifieduser
from
 category cat,testcollection tc
  where categorytypeid = (Select id from categorytype where typecode='SESSION_RULES')
   and categorycode in
 ('GRACE_PERIOD')
 and tc.id in (Select id from testcollection)
 and tc.id not in (Select testcollectionid
 from testcollectionssessionrules tcsr,category cat
 where tcsr.sessionruleid = cat.id and cat.categorycode='GRACE_PERIOD')
 ); 