--Student:2695059973 (Move Math performance test)
update studentstests set enrollmentid = 2182294, testsessionid=2177853, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8576623;
	
--Stdent:2392397023 (Move ELA performance test)
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9136214;
update studentstests set enrollmentid = 2365696, testsessionid=2192318, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9089786;