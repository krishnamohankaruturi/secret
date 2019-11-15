-- InActivate existing assigned tests(ELA, Math) for student : 8688642877

update studentsresponses  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestsid in (13648343,13648342,13648349,13648348);

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (13648343,13648342,13648349,13648348);

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id in (13648343,13648342,13648349,13648348);

	
-- Transfer tests and results from 9114727757  to 8688642877
	
	--Math and ELA
	update studentstests set enrollmentid = 2392276, studentid=1322105, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id in (9983901,10373905,12043364,13120949,12192344,13169508,13229583,9907347);
	
	