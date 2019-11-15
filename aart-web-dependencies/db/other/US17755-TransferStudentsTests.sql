--Stdent:3532079933  (Move Math and ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (9035036, 9041011);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9035036;
update studentstests set enrollmentid = 2357148, testsessionid=2180441, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8950334;

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9041011;
update studentstests set enrollmentid = 2357148, testsessionid=2180340, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8956097;
