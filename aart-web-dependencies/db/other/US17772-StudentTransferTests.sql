--Stdent:2394263415  (Clear MDPT test taken on 03/07 and move MDPT and MPT tests from  Meadowlark Elementary to Washington Elem )

	UPDATE studentsresponseparameters SET activeflag=false, modifieddate = now(),
			modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin') WHERE studentstestsid = 8763401;
	UPDATE studentsresponses SET activeflag=false, modifieddate = now(),
			modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin') WHERE studentstestsid = 8763401;
	UPDATE studentstestsections SET activeflag=false, modifieddate = now(),
			modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin') WHERE studentstestid = 8763401;
	UPDATE studentstests SET activeflag=false, modifieddate = now(),
			modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')  WHERE id = 8763401;
	
	update studentstests set enrollmentid = 2182265, testsessionid=2175873, modifieddate = now(),
		modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8705211;
			
			
	
	update studentstestsections  set activeflag = false, modifieddate = now(),
		modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8996642);
	
	update studentstests set activeflag = false, modifieddate = now(),
		modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8996642;
		
	update studentstests set enrollmentid = 2182265, testsessionid=2181225, modifieddate = now(),
		modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8969064;



	 	