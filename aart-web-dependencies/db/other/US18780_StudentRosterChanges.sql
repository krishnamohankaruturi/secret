-- Resetting student tracker and inactivating students tests for the student :000721026 

UPDATE studentsresponses SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentstestsid IN (13815603,13830039);

UPDATE studentstestsections SET activeflag = false,  modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
      WHERE studentstestid IN (13815603,13830039);

UPDATE studentstests SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE id in (13815603,13830039);
                 
UPDATE testsession SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE id IN (3284170, 3283611);

UPDATE studenttrackerband SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
     WHERE studenttrackerid = (327484)
       AND testsessionid in (3284170, 3283611)
       AND source = 'ST';

INSERT INTO domainaudithistory (source, objecttype, objectid, createduserid, createddate, action, objectbeforevalues, objectaftervalues)
      values('SCRIPT', 'STUDENTSTESTS', 1091977, 12, now(), 'REST_STUDENT_TRACKER', ('{"StudentId": 1091977, "activeflag": true}')::JSON,  ('{"Reason": "As per US18780, inactivated the students tests and reseted the studenttracker band "}')::JSON);
      
