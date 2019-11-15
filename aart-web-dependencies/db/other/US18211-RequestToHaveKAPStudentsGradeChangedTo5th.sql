-- US18211: EP: Prod - Request to have KAP students grade changed to 5th
-- Changing the grade 6 to 5 and also inactivating all the students tests.
-- Student EP id: 252367 AND enrollmentid : 2201253, Keeping the completed performance tests.

UPDATE studentsresponses SET activeflag = FALSE, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
        WHERE studentstestsid IN (SELECT id FROM studentstests WHERE enrollmentid = 2201253 AND status != 86);

-- Update count:  0           
     

UPDATE studentstestsections SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
             WHERE studentstestid IN (SELECT id FROM studentstests WHERE enrollmentid = 2201253 AND status != 86);

-- Update count: 10


UPDATE studentstests SET activeflag = FALSE, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
             WHERE enrollmentid = 2201253 AND status != 86;
             
-- Update count: 4                   


UPDATE enrollment SET currentgradelevel = (SELECT id FROM gradecourse WHERE abbreviatedname = '5' AND assessmentprogramgradesid IS not null), 
       modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), notes = 'Changing grade level from 6 to 5 as per user story US18211'
       WHERE id = 2201253;

-- Update count: 1
       