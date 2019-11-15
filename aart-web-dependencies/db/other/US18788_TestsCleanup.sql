--Student: 5688004339
--Cleanup testlests for DLM-MooreJustin-1199856-SP ELA RI.3.5 PP, DLM-MooreJustin-1199856-SP M 3.NBT.2 PP

update studentsresponses  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestsid in (13891232,13891567);

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (13891232,13891567);

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id in (13891232,13891567);
	
UPDATE testsession SET activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') WHERE ID IN (3325538,3325203);
	