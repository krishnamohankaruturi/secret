update studentstests set activeflag = false,modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8874611;
update studentstests set enrollmentid = 2302396, testsessionid=2178417, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8776380;

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9040705;
update studentstests set enrollmentid = 2302396, testsessionid=2182885, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9003702;