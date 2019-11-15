--Stdent:8859966914  (Move Math and ELA performance test)
update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where  studentstestid in (9030799,8859839);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9030799;
update studentstests set enrollmentid = 2305221, testsessionid=2182593, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8865756;

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8859839;
update studentstests set enrollmentid = 2305221, testsessionid=2178546, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8527295;