--Stdent:3740028394  (Move Math performance test)
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9019432;
update studentstests set enrollmentid = 2305242, testsessionid=2180396, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8930774;
	
--Stdent:5030931201  (Move Math and ELA performance test)
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9191298;
update studentstests set enrollmentid = 2369163, testsessionid=2178911, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8792461;

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id in (15700827, 15700828);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9191670;
update studentstests set enrollmentid = 2369163, testsessionid=2179338, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8829181;