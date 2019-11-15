-- US18177: EP: Prod - Request to have Math KAP test reset per SEA request
UPDATE studentsresponses SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentstestsid IN (SELECT id FROM studentstests WHERE enrollmentid = 2066503 AND 
          testsessionid IN (SELECT id FROM testsession WHERE name in ('2016_3024_Grade 7_Mathematics_Stage 2','2016_3024_Grade 7_Mathematics_Stage 1','2016_3024_Grade 7_Mathematics_Stage 4')));

-- Update count : 25          


UPDATE studentstestsections SET activeflag = false,  modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
      WHERE studentstestid IN (SELECT id FROM studentstests WHERE enrollmentid = 2066503 AND 
          testsessionid IN (SELECT id FROM testsession WHERE name in ('2016_3024_Grade 7_Mathematics_Stage 2','2016_3024_Grade 7_Mathematics_Stage 1','2016_3024_Grade 7_Mathematics_Stage 4')));

-- Update count: 6          


UPDATE studentstests SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE enrollmentid = 2066503 AND testsessionid IN (SELECT id FROM testsession WHERE name in ('2016_3024_Grade 7_Mathematics_Stage 2','2016_3024_Grade 7_Mathematics_Stage 1','2016_3024_Grade 7_Mathematics_Stage 4'));
     
-- Update count: 3