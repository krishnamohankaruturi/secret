--Stdent:9714207894  (Move Math and ELA performance test)
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9304540;
update studentstests set enrollmentid = 2377499, testsessionid=2175339, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8735082;

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id in (15821106, 15821105, 15820533);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9304857;
update studentstests set enrollmentid = 2377499, testsessionid=2175341, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8767362;
	
--Stdent:9381334366  (Move Math and ELA performance test)
update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin')  where studentstestid in (9005441,9016728);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9005441;
update studentstests set enrollmentid = 2239138, testsessionid=2182556, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8919517;


update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9016728;
update studentstests set enrollmentid = 2239138, testsessionid=2182708, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8921702;
	
--Stdent:2841107671  (Move ELA performance test)
update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin')  where studentstestid in (9085213);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9085213;
update studentstests set enrollmentid = 2362216, testsessionid=2182708, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8921746;

