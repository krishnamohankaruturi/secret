--Stdent:2299342702  (Move ELA performance tests)

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8533975);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8533975;
update studentstests set enrollmentid = 1836684, testsessionid=2175703, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8528678;
