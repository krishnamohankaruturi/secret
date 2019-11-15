UPDATE student SET statestudentidentifier ='40041099', modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') WHERE ID = 931334;
UPDATE student SET activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') WHERE ID = 483080;