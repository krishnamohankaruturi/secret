--US17787- Student:8090084869  (Move ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (9158102);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9158102;
update studentstests set enrollmentid = 2368459, testsessionid=2179647, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8875519;

--US17788-Stdent:1355102332  (Move MPT performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8748040);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8748040;
update studentstests set enrollmentid = 2205097, testsessionid=2179533, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8644612;

	--US17791 : Stdent:6831009387  (Move Math and ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (9157400, 9157227);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9157400;
update studentstests set enrollmentid = 2368422, testsessionid=2178909, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8809169;

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9157227;
update studentstests set enrollmentid = 2368422, testsessionid=2178385, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8776140;
	
	
	--US17791 : Stdent:8126518553  (Move Math performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8776876);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8776876;
	
update studentstests set enrollmentid = 2239225, testsessionid=2178828, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8744311;


