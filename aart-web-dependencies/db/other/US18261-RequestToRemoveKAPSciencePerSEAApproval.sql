-- US18261: EP: Prod - Request to remove KAP Science per SEA approval
-- Student EP id: 318568 and Enrollmentid in school 8530 is  2058295
-- Requested to reset the Science Stage 1, as student got Stage 1, 2 and 3 in science inactivating all studentstests

UPDATE studentsresponses SET activeflag = FALSE, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
        WHERE studentstestsid IN (SELECT st.id FROM testsession ts JOIN studentstests st ON st.testsessionid = ts.id 
               WHERE ts.name IN ('2016_8530_Grade 8_Science_Stage 1', '2016_8530_Grade 8_Science_Stage 2', '2016_8530_Grade 8_Science_Stage 3') AND st.enrollmentid = 2058295);

-- Update count: 22             
     

UPDATE studentstestsections SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
             WHERE studentstestid IN (SELECT st.id FROM testsession ts JOIN studentstests st ON st.testsessionid = ts.id 
               WHERE ts.name IN ('2016_8530_Grade 8_Science_Stage 1', '2016_8530_Grade 8_Science_Stage 2', '2016_8530_Grade 8_Science_Stage 3') AND st.enrollmentid = 2058295);

-- Update count: 3


UPDATE studentstests SET activeflag = FALSE, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
             WHERE id IN (SELECT st.id FROM testsession ts JOIN studentstests st ON st.testsessionid = ts.id 
               WHERE ts.name IN ('2016_8530_Grade 8_Science_Stage 1', '2016_8530_Grade 8_Science_Stage 2', '2016_8530_Grade 8_Science_Stage 3') AND st.enrollmentid = 2058295);

-- Update count: 3               