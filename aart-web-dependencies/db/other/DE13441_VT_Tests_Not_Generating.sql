--DE13441_VT_Tests_Not_Generating
--The contentcodes above are for Grade 7 but we are trying to find the tests for Grade 6. 
--That's why the test session could not be created. There was a GradeChange. 
--The testsessions before the grade change must be invalidated and the student tracker must be reset to fix this issue.

UPDATE studentsresponses SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentstestsid IN (select id from studentstests where studentid in (864789));

UPDATE studentstestsections SET activeflag = false,  modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
      WHERE studentstestid IN (select id from studentstests where studentid in (864789));

UPDATE studentstests SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentid in (864789);
                 
UPDATE testsession SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE id IN (select testsessionid from studentstests where studentid in (864789));

UPDATE studenttrackerband SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
    WHERE studenttrackerid in (select id from studenttracker where studentid in (864789))

UPDATE studenttracker SET status = 'UNTRACKED' WHERE studentid in (864789);
	
INSERT INTO domainaudithistory (source, objecttype, objectid, createduserid, createddate, action, objectbeforevalues, objectaftervalues)
      values('SCRIPT', 'STUDENTSTESTS', 864789, 12, now(), 'REST_STUDENT_TRACKER', ('{"TestSessionIds": "3491696, 3491799", "activeflag": true}')::JSON,  ('{"Reason": "As per DE13441, inactivated the students tests"}')::JSON);

 