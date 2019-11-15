--Stdent:2045517616  (Move ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8729532);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8729532;
update studentstests set enrollmentid = 2111407, testsessionid=2179309, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8701332;

--Stdent:8742727731  (Move ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8935729);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8935729;
update studentstests set enrollmentid = 2027160, testsessionid=2182274, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8923158;
