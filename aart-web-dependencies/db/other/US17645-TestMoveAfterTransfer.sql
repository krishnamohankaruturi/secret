update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8875810;
update studentstests set enrollmentid = 2312139, testsessionid=2177387, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8699781;