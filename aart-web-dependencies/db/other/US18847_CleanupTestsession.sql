--Student: 1001675867
--Test to be removed DLM-AndersonArwen-761160-YE M 6.5 IP

update studentsresponses  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestsid in (14123202);

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (14123202);

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id in (14123202);
	
UPDATE testsession SET activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') WHERE ID IN (3500723);
	