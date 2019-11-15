--Current Student State ID: 1887331323, State ID it needs to be: 5234917591

UPDATE student SET activeflag = false, statestudentidentifier ='5234917591_old', modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') WHERE ID = 950629;

UPDATE student SET statestudentidentifier ='5234917591', modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') WHERE ID = 1238156;
