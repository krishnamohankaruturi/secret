update student set activeflag=false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id in (1209653, 1208011, 908683, 1217206, 909990, 1148469, 1208258, 1046194);

update enrollment set activeflag=false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentid = 1148469;
	

update survey set activeflag=false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentid in (1046194, 890027);

update survey set studentid=890027, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentid in (909990);