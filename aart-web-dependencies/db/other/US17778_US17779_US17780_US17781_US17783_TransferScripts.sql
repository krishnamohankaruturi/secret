--US17778 : Stdent:3569196917  (Move Math and ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (9335236, 9335288);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9335236;
update studentstests set enrollmentid = 2378955, testsessionid=2180749, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8937107;

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9335288;
update studentstests set enrollmentid = 2378955, testsessionid=2175534, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8665592;

--US17779 : Stdent:6275671297  (Move Math and ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8797015, 8832626);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8797015;
update studentstests set enrollmentid = 2266036, testsessionid=2175364, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8780412;

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8832626;
update studentstests set enrollmentid = 2266036, testsessionid=2175381, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8815028;

--US17780 : Stdent:3623617601  (Move Math and ELA performance tests)

update studentstests set enrollmentid = 2380996, testsessionid=2181245, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8945385;

update studentstests set enrollmentid = 2380996, testsessionid=2181305, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8940698;
	
--US17781 : Stdent:5908929573  (Move ELA performance tests)

update studentstests set enrollmentid = 2380514, testsessionid=2183766, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8993031;

-- Stdent:8419018465  (Move Math performance tests)
update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (9341613);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9341613;
update studentstests set enrollmentid = 2380518, testsessionid=2203200, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9029996;
	
--US17783 : Stdent:2252645814  (Move MDPT and MPT tests)
update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8875430, 9040825);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8875430;
update studentstests set enrollmentid = 2309269, testsessionid=2180953, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8715667;

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9040825;
update studentstests set enrollmentid = 2309269, testsessionid=2183810, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8974069;