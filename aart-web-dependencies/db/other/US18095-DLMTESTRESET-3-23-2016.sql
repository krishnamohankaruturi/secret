-- US18095: DLM TEST RESET 3/23/2016

-- Resetting student tracker and inactivating students tests for the student EP ID:1204400  With testsession names : DLM-BrownJamie-1204400-SP ELA RL.3.9 PP and DLM-BrownJamie-1204400-SP ELA CW.3 T
-- DLM-BrownJamie-1204400-SP ELA CW.3 T testsession is created after completion of DLM-BrownJamie-1204400-SP ELA RL.3.9 PP, So inactivating that as well.

UPDATE studentsresponses SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentstestsid IN (SELECT id FROM studentstests WHERE studentid =1204400  AND 
          testsessionid IN (SELECT id FROM testsession WHERE name in ('DLM-BrownJamie-1204400-SP ELA RL.3.9 PP', 'DLM-BrownJamie-1204400-SP ELA CW.3 T')));

-- Update count : 3


UPDATE studentstestsections SET activeflag = false,  modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
      WHERE studentstestid IN (SELECT id FROM studentstests WHERE studentid =1204400  AND 
          testsessionid IN (SELECT id FROM testsession WHERE name in ('DLM-BrownJamie-1204400-SP ELA RL.3.9 PP', 'DLM-BrownJamie-1204400-SP ELA CW.3 T')));

-- Update count: 2


UPDATE studentstests SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentid = 1204400  AND testsessionid IN (SELECT id FROM testsession WHERE name in ('DLM-BrownJamie-1204400-SP ELA RL.3.9 PP', 'DLM-BrownJamie-1204400-SP ELA CW.3 T'));
                 
-- Update count: 2


UPDATE testsession SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE id IN (SELECT testsessionid FROM studenttrackerband WHERE studenttrackerid in (SELECT id FROM studenttracker WHERE studentid = 1204400 AND contentareaid = (SELECT id FROM contentarea WHERE abbreviatedname = 'ELA')))
     AND name in ('DLM-BrownJamie-1204400-SP ELA RL.3.9 PP', 'DLM-BrownJamie-1204400-SP ELA CW.3 T');

-- Update count : 2


UPDATE studenttrackerband SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
     WHERE studenttrackerid = (SELECT id FROM studenttracker WHERE studentid =1204400  AND contentareaid = (SELECT id FROM contentarea WHERE abbreviatedname = 'ELA'))
       AND testsessionid in (SELECT id FROM testsession WHERE name in ('DLM-BrownJamie-1204400-SP ELA RL.3.9 PP', 'DLM-BrownJamie-1204400-SP ELA CW.3 T'))
       AND source = 'ST';

-- Update count : 2


INSERT INTO domainaudithistory (source, objecttype, objectid, createduserid, createddate, action, objectbeforevalues, objectaftervalues)
      values('SCRIPT', 'STUDENTSTESTS', 1204400, 12, now(), 'REST_STUDENT_TRACKER', ('{"StudentId": 1204400, "activeflag": true}')::JSON,  ('{"Reason": "As per US18095, inactivated the students tests and reseted the studenttracker band for studentid 1204400 and students tests DLM-BrownJamie-1204400-SP ELA RL.3.9 PP and DLM-BrownJamie-1204400-SP ELA CW.3 Tcontentareaid 3"}')::JSON);
      
-- Insert count: 1
      
      
      
      

-- Resetting student tracker and inactivating students tests for the student EP ID:1039231  With testsession names : DLM-CareyLaura-1039231-YE M 5.1 PP, DLM-CareyLaura-1039231-YE M 5.2 DP and DLM-CareyLaura-1039231-YE M 5.3 IP
-- Requested to reset DLM-CareyLaura-1039231-YE M 5.1 PP which is assigned as part of FC, after that all the tests were assigned so deactivating all tests.

UPDATE studentsresponses SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentstestsid IN (SELECT id FROM studentstests WHERE studentid = 1039231 AND 
          testsessionid IN (SELECT id FROM testsession WHERE name in ('DLM-CareyLaura-1039231-YE M 5.1 PP', 'DLM-CareyLaura-1039231-YE M 5.2 DP', 'DLM-CareyLaura-1039231-YE M 5.3 IP')));

-- Update count : 7


UPDATE studentstestsections SET activeflag = false,  modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
      WHERE studentstestid IN (SELECT id FROM studentstests WHERE studentid = 1039231 AND 
          testsessionid IN (SELECT id FROM testsession WHERE name in ('DLM-CareyLaura-1039231-YE M 5.1 PP', 'DLM-CareyLaura-1039231-YE M 5.2 DP', 'DLM-CareyLaura-1039231-YE M 5.3 IP')));

-- Update count: 3


UPDATE studentstests SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentid = 1039231  AND testsessionid IN (SELECT id FROM testsession WHERE name in ('DLM-CareyLaura-1039231-YE M 5.1 PP', 'DLM-CareyLaura-1039231-YE M 5.2 DP', 'DLM-CareyLaura-1039231-YE M 5.3 IP'));
                 
-- Update count: 3


UPDATE testsession SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE id IN (SELECT testsessionid FROM studenttrackerband WHERE studenttrackerid in (SELECT id FROM studenttracker WHERE studentid = 1039231 AND contentareaid = (SELECT id FROM contentarea WHERE abbreviatedname = 'M')))
     AND name in ('DLM-CareyLaura-1039231-YE M 5.1 PP', 'DLM-CareyLaura-1039231-YE M 5.2 DP', 'DLM-CareyLaura-1039231-YE M 5.3 IP');

-- Update count : 3

UPDATE studenttrackerband SET testsessionid = null, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
     WHERE studenttrackerid = (SELECT id FROM studenttracker WHERE studentid = 1039231  AND contentareaid = (SELECT id FROM contentarea WHERE abbreviatedname = 'M'))      
       AND source = 'FC';

-- Update count : 1

UPDATE studenttrackerband SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
     WHERE studenttrackerid = (SELECT id FROM studenttracker WHERE studentid = 1039231  AND contentareaid = (SELECT id FROM contentarea WHERE abbreviatedname = 'M'))
       AND testsessionid in (SELECT id FROM testsession WHERE name in ('DLM-CareyLaura-1039231-YE M 5.2 DP', 'DLM-CareyLaura-1039231-YE M 5.3 IP'))
       AND source = 'ST';

-- Update count : 2


INSERT INTO domainaudithistory (source, objecttype, objectid, createduserid, createddate, action, objectbeforevalues, objectaftervalues)
      values('SCRIPT', 'STUDENTSTESTS',1039231 , 12, now(), 'REST_STUDENT_TRACKER', ('{"StudentId": 1039231, "activeflag": true}')::JSON,  ('{"Reason": "As per US18095, inactivated the students tests and reseted the studenttracker band for studentid 1039231 and studentstests  DLM-CareyLaura-1039231-YE M 5.1 PP, DLM-CareyLaura-1039231-YE M 5.2 DP and DLM-CareyLaura-1039231-YE M 5.3 IP contentareaid 440"}')::JSON);
      
-- Insert count: 1       