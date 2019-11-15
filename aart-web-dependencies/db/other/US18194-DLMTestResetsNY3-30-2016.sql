-- US18194: DLM Test Resets NY (3/30/2016)

-- Resetting student tracker and inactivating students tests for the student EP ID: 1263940 With testname : 1263940yeela4.1pp

UPDATE studentsresponses SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentstestsid IN (SELECT id FROM studentstests WHERE studentid = 1263940 AND 
          testsessionid IN (SELECT id FROM testsession WHERE name in ('DLM-BicserdyPeter-1263940-YE ELA 4.1 PP', 'DLM-BicserdyPeter-1263940-YE ELA 4.2 DP')));

-- Update count : 0


UPDATE studentstestsections SET activeflag = false,  modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
      WHERE studentstestid IN (SELECT id FROM studentstests WHERE studentid = 1263940  AND 
          testsessionid IN (SELECT id FROM testsession WHERE name in ('DLM-BicserdyPeter-1263940-YE ELA 4.1 PP', 'DLM-BicserdyPeter-1263940-YE ELA 4.2 DP')));

-- Update count: 2 


UPDATE studentstests SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentid = 1263940  AND testsessionid IN (SELECT id FROM testsession WHERE name in ('DLM-BicserdyPeter-1263940-YE ELA 4.1 PP', 'DLM-BicserdyPeter-1263940-YE ELA 4.2 DP'));
                 
-- Update count: 2


UPDATE testsession SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE name in ('DLM-BicserdyPeter-1263940-YE ELA 4.1 PP', 'DLM-BicserdyPeter-1263940-YE ELA 4.2 DP') 
       AND id IN (SELECT testsessionid FROM studenttrackerband WHERE studenttrackerid in (SELECT id FROM studenttracker WHERE studentid = 1263940 AND contentareaid = (SELECT id FROM contentarea WHERE abbreviatedname = 'ELA')));

-- Update count :2


UPDATE studenttrackerband SET testsessionid = null, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
     WHERE studenttrackerid = (SELECT id FROM studenttracker WHERE studentid = 1263940 AND contentareaid = (SELECT id FROM contentarea WHERE abbreviatedname = 'ELA'))
       AND source = 'FC';

-- Update count : 1


UPDATE studenttrackerband SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
     WHERE studenttrackerid = (SELECT id FROM studenttracker WHERE studentid = 1263940 AND contentareaid = (SELECT id FROM contentarea WHERE abbreviatedname = 'ELA'))
       AND source = 'ST';

-- Update count : 1


INSERT INTO domainaudithistory (source, objecttype, objectid, createduserid, createddate, action, objectbeforevalues, objectaftervalues)
      values('SCRIPT', 'STUDENTSTESTS', 1263940, 12, now(), 'REST_STUDENT_TRACKER', ('{"StudentId": 1263940, "activeflag": true}')::JSON,  ('{"Reason": "As per US18194, inactivated the students tests and reseted the studenttracker band for studentid 1263940 and contentareaid 3"}')::JSON);
      
-- Insert count: 1      
     
     
-- Resetting student tracker and inactivating students tests for the student EP ID:  With testname : 1262849yeela7.1t

UPDATE studentsresponses SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentstestsid IN (SELECT id FROM studentstests WHERE studentid = 1262849 AND 
          testsessionid IN (SELECT id FROM testsession WHERE name in ('DLM-AhrensJustin-1262849-YE ELA 7.1 T', 'DLM-AhrensJustin-1262849-YE ELA 7.2 PP')));

-- Update count : 5


UPDATE studentstestsections SET activeflag = false,  modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
      WHERE studentstestid IN (SELECT id FROM studentstests WHERE studentid = 1262849  AND 
          testsessionid IN (SELECT id FROM testsession WHERE name in ('DLM-AhrensJustin-1262849-YE ELA 7.1 T', 'DLM-AhrensJustin-1262849-YE ELA 7.2 PP')));

-- Update count: 2  


UPDATE studentstests SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentid = 1262849 AND testsessionid IN (SELECT id FROM testsession WHERE name in ('DLM-AhrensJustin-1262849-YE ELA 7.1 T', 'DLM-AhrensJustin-1262849-YE ELA 7.2 PP'));
                 
-- Update count: 2


UPDATE testsession SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE name in ('DLM-AhrensJustin-1262849-YE ELA 7.1 T', 'DLM-AhrensJustin-1262849-YE ELA 7.2 PP') 
       AND id IN (SELECT testsessionid FROM studenttrackerband WHERE studenttrackerid in (SELECT id FROM studenttracker WHERE studentid = 1262849 AND contentareaid = (SELECT id FROM contentarea WHERE abbreviatedname = 'ELA')));

-- Update count : 2


UPDATE studenttrackerband SET testsessionid = null, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
     WHERE studenttrackerid = (SELECT id FROM studenttracker WHERE studentid = 1262849 AND contentareaid = (SELECT id FROM contentarea WHERE abbreviatedname = 'ELA'))
       AND source = 'FC';

-- Update count : 1


UPDATE studenttrackerband SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
     WHERE studenttrackerid = (SELECT id FROM studenttracker WHERE studentid = 1262849  AND contentareaid = (SELECT id FROM contentarea WHERE abbreviatedname = 'ELA'))
       AND source = 'ST';

-- Update count : 1

INSERT INTO domainaudithistory (source, objecttype, objectid, createduserid, createddate, action, objectbeforevalues, objectaftervalues)
      values('SCRIPT', 'STUDENTSTESTS', 1262849, 12, now(), 'REST_STUDENT_TRACKER', ('{"StudentId": 1262849, "activeflag": true}')::JSON,  ('{"Reason": "As per US18194, inactivated the students tests and reseted the studenttracker band for studentid 1262849 and contentareaid 3"}')::JSON);
      
-- Insert count: 1        
        