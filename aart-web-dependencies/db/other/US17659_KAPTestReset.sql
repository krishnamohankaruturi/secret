UPDATE studentstestsections SET activeflag=false, modifieddate = now(),
		modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin') WHERE studentstestid = 8680703;
UPDATE studentstests SET activeflag=false, modifieddate = now(),
	modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')  WHERE id = 8680703;