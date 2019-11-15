-- US18446 : DLM TEST RESET

UPDATE studentsresponses SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentstestsid IN (SELECT id FROM studentstests WHERE 
     testsessionid IN (2645416,2788611,2718520,2807424));

UPDATE studentstestsections SET activeflag = false,  modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
      WHERE studentstestid IN (SELECT id FROM studentstests WHERE testsessionid IN (2645416,2788611,2718520,2807424 ));

UPDATE studentstests SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE testsessionid IN (2645416,2788611,2718520,2807424 );
                 
UPDATE testsession SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE id IN (2645416,2788611,2718520,2807424  );

UPDATE studenttrackerband SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
     WHERE studenttrackerid in (203948,214029,197855 )
       AND testsessionid in (2645416,2788611,2718520,2807424 )
	AND source = 'ST';

 UPDATE studenttracker SET status = 'UNTRACKED', modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()  where id in (203948,214029,197855);

 UPDATE studentstests SET status=84, startdatetime=null,enddatetime=null,
	modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now() 
	where id IN(11905045,11905873); 
     
UPDATE studentstestsections SET statusid=125, startdatetime=null,enddatetime=null, 
		modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
		WHERE studentstestid IN (SELECT id FROM studentstests WHERE testsessionid IN (2448411,2449190));

UPDATE studentsresponses SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentstestsid IN (SELECT id FROM studentstests WHERE 
     testsessionid IN (2448411, 2449190));

INSERT INTO domainaudithistory (source, objecttype, objectid, createduserid, createddate, action, objectbeforevalues, objectaftervalues)
      values('SCRIPT', 'STUDENTSTESTS', 1291291, 12, now(), 'RESET_STUDENT_TRACKER', ('{"TestSessionIds": "2645416,2788611,2718520,2807424,2448411, 2449190", "activeflag": true}')::JSON,  
      ('{"Reason": "As per US18446, inactivated the students tests"}')::JSON);
