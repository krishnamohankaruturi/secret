--Empty dml/656.sql ==> For ddl/656.sql

-- DML 656 for DE15619
INSERT INTO authorities(
	authority, 
	displayname, 
	objecttype, 
	createddate, 
	createduser, 
	activeflag, 
	modifieddate, 
	modifieduser)
VALUES (
	'ITI_OVERRIDE_SYS_REC_LEVEL', 
	'ITI - Override System-recommended Level',
	'Administrative-ITI', 
	current_timestamp,
	(Select id from aartuser where username='cetesysadmin'), 
	true, 
	current_timestamp,
	(Select id from aartuser where username='cetesysadmin'));