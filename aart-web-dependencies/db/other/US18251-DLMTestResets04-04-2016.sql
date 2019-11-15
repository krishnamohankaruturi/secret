-- US18251: DLM Test Resets (04/04/2016)

UPDATE studentsresponses SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentstestsid IN (10881834, 12152653, 10972555, 11095863);

-- Update count : 4


UPDATE studentstestsections SET activeflag = false,  modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
      WHERE studentstestid IN (10881834, 12152653, 10972555, 11095863);

-- Update count: 4


UPDATE studentstests SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE id IN(10881834, 12152653, 10972555, 11095863);
                 
-- Update count: 4


UPDATE testsession SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE id IN (2273416, 2524549, 2355002, 2402526);

-- Update count : 2


UPDATE studenttrackerband SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
     WHERE studenttrackerid IN (139442, 207946)
       AND testsessionid in (2524549, 2402526)
       AND source = 'ST';

-- Update count : 2

       
UPDATE studenttrackerband SET testsessionid = null, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
     WHERE studenttrackerid IN (139442, 207946)
     AND testsessionid in (2273416, 2355002)
     AND source = 'FC';
     
-- Update count : 2     


INSERT INTO domainaudithistory (source, objecttype, objectid, createduserid, createddate, action, objectbeforevalues, objectaftervalues)
      values('SCRIPT', 'STUDENTSTESTS',931349 , 12, now(), 'REST_STUDENT_TRACKER', ('{"StudentIds": "931349,1286838", "testsessionids": "2273416, 2524549, 2355002, 2402526","activeflag": true}')::JSON,  ('{"Reason": "As Per US18251: DLM Test Resets (04/04/2016)"}')::JSON);
      
-- Insert count: 1