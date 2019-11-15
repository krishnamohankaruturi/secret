--Stdent:1153991594  (Move Math and ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8729257, 8761172);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8729257;
update studentstests set enrollmentid = 2182278, testsessionid=2176052, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8623757;

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8761172;
update studentstests set enrollmentid = 2182278, testsessionid=2176142, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8644577;
	
--Stdent:7173553032  (Move Math and ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8881263, 8884517);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8881263;
update studentstests set enrollmentid = 1861185, testsessionid=2180654, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8846084;

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8884517;
update studentstests set enrollmentid = 1861185, testsessionid=2180808, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8853777;

--Stdent:2806954894  (Move MDPT tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8880892);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8880892;
update studentstests set enrollmentid = 2335740, testsessionid=2178542, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8822007;
