--2474316806 -- reenroll student
UPDATE enrollment SET activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18772'
  where id = 2390745;

--Change test status back to unused

UPDATE studentstestsections  set activeflag = true, statusid = 125, modifieddate = now(),
	modifieduser = (SELECT id from aartuser where username = 'cetesysadmin') 
	where studentstestid in (14077351,14082271);

UPDATE studentstests SET activeflag = true, status = 84, modifieddate = now(),
	modifieduser = (SELECT id from aartuser where username = 'cetesysadmin') 
	where id in (14077351,14082271);
	
UPDATE testsession SET activeflag = true, modifieddate = now(), status = 84,
	modifieduser = (SELECT id from aartuser where username = 'cetesysadmin') where id in(3466840, 3470202);
-- Change status back to Untracked

UPDATE studenttracker SET status = 'UNTRACKED', modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()  
 where id in (334840,334791);
 
	