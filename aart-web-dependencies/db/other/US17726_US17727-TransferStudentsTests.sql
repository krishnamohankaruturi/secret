--Stdent:7128266186  (Deactivate Math performance test)
update studentsresponses  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestsid = 8706207;

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid = 8706207;

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8706207;
	
--Stdent:3572478782  (Move Math and ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8808513, 8845220);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8808513;
update studentstests set enrollmentid = 2278991, testsessionid=2176319, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8779359;

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8845220;
update studentstests set enrollmentid = 2278991, testsessionid=2176584, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8812804;