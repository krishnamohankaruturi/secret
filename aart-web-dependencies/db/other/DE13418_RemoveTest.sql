--Remove this test DLM-BuettnerTabytha-910208-FT F15 SCI MS TARGET for student:8792406567
update studentsresponses  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestsid in (8036301);

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (8036301);

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id in (8036301);
	