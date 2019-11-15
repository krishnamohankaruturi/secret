--Stdent:2392397023  (Move Math performance test)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (9133417);

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9133417;
update studentstests set enrollmentid = 2365696, testsessionid=2183628, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9029077;
	
--Stdent:3649664461  (Move Math performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8815761);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8815761;
update studentstests set enrollmentid = 2285315, testsessionid=2177813, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8648060;

--Stdent:5903471757  (Move Math performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8815797);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8815797;
update studentstests set enrollmentid = 2285316, testsessionid=2177813, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8648237;

--Stdent:2536090256  (Move Math performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8813231);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8813231;
update studentstests set enrollmentid = 2285317, testsessionid=2177758, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8800381;
	
--Stdent:3292803489  (Move Math and ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (9015370, 8861112);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9015370;
update studentstests set enrollmentid = 2285371, testsessionid=2182862, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9006228;

	update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8861112;
update studentstests set enrollmentid = 2285371, testsessionid=2179475, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8842891;

--Stdent:2626210854  (Move Math and ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (9015417, 8861191);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9015417;
update studentstests set enrollmentid = 2285373, testsessionid=2182862, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9006653;

	update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8861191;
update studentstests set enrollmentid = 2285373, testsessionid=2179475, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8843257;

--Stdent:5732255052  (Move Math and ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (9015441, 8861240);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9015441;
update studentstests set enrollmentid = 2285374, testsessionid=2182862, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9006752;

	update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8861240;
update studentstests set enrollmentid = 2285374, testsessionid=2179475, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8843440;
	
--Stdent:4688972849  (Move ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8861287);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8861287;
update studentstests set enrollmentid = 2285375, testsessionid=2179475, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8843596;

--Stdent:8184073259  (Move Math and ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (9015489, 8861331);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9015489;
update studentstests set enrollmentid = 2285376, testsessionid=2182862, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9006953;

	update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8861331;
update studentstests set enrollmentid = 2285376, testsessionid=2179475, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8844220;
