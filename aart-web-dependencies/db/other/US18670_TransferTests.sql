-- InActivate existing assigned tests(ELA, Math, SS, Sci) for student : 3224483636

update studentsresponses  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestsid in (10154432, 10121172, 10878588, 10677702, 10878589, 10878593, 12718816, 10121171);

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (10154432, 10121172, 10878588, 10677702, 10878589, 10878593, 12718816, 10121171);

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id in (10154432, 10121172, 10878588, 10677702, 10878589, 10878593, 12718816, 10121171);

	
-- Transfer tests and results from 7174837328 to 3224483636
	
	--Math
	update studentstests set enrollmentid = 2382835, testsessionid=2251144, studentid=1319637, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9692274;
	
	update studentstests set enrollmentid = 2382835, testsessionid=2585810, studentid=1319637, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 13267989;
	
	update studentstests set enrollmentid = 2382835, testsessionid=2628669, studentid=1319637, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 13274892;
	
	update studentstests set enrollmentid = 2382835, testsessionid=2251143, studentid=1319637, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9692266;
	
	--Science
	update studentstests set enrollmentid = 2382835, testsessionid=2268397, studentid=1319637, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 10688420;
	
	update studentstests set enrollmentid = 2382835, testsessionid=2268398, studentid=1319637, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 10688424;
	
	update studentstests set enrollmentid = 2382835, testsessionid=2268399, studentid=1319637, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 10688426;
	
	-- Social
	update studentstests set enrollmentid = 2382835, testsessionid=2267605, studentid=1319637, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 10543241;
	
	-- ELA
	update studentstests set enrollmentid = 2382835, testsessionid=2251370, studentid=1319637, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9708756;
	
	update studentstests set enrollmentid = 2382835, testsessionid=2386339, studentid=1319637, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 11031940;
	
	update studentstests set enrollmentid = 2382835, testsessionid=2400430, studentid=1319637, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 11091567;
	
	update studentstests set enrollmentid = 2382835, testsessionid=2251369, studentid=1319637, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9708753;