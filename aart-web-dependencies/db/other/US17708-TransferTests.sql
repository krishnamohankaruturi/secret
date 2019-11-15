--Stdent:1033596922  (Move Math and ELA performance test)
update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where  studentstestid in (9088958, 9080530);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9088958;
update studentstests set enrollmentid = 2195855, testsessionid=2179134, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8608088;

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9080530;
update studentstests set enrollmentid = 2195855, testsessionid=2178595, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8594206;