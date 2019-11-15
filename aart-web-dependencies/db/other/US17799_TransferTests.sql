--US17799 : Stdent:1086194845  (Move Math and ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (9032626, 8864891);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9032626;
	
update studentstests set enrollmentid = 2312214, testsessionid=2180778, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8910144;

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8864891;
	
update studentstests set enrollmentid = 2312214, testsessionid=2175636, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8606142;