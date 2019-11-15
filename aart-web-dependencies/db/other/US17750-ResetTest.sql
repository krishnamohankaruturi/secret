--Stdent:8949629658  (Reset Math performance tests)

	UPDATE studentsresponseparameters SET activeflag=false, modifieddate = now(),
			modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin') WHERE studentstestsid = 8989526;
	UPDATE studentsresponses SET activeflag=false, modifieddate = now(),
			modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin') WHERE studentstestsid = 8989526;
	UPDATE studentstestsections SET activeflag=false, modifieddate = now(),
			modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin') WHERE studentstestid = 8989526;
	UPDATE studentstests SET activeflag=false, modifieddate = now(),
			modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')  WHERE id = 8989526;
	 	