--US17867

UPDATE organization SET pooltype='MULTIEEOFG', modifieduser=(select id from aartuser where username='cetesysadmin'), 
	modifieddate=now() WHERE displayidentifier='NY' and activeflag is true;

UPDATE organization SET pooltype='SINGLEEE', modifieduser=(select id from aartuser where username='cetesysadmin'), 
	modifieddate=now() WHERE displayidentifier='UT' and activeflag is true;

UPDATE organization SET pooltype=null, modifieduser=(select id from aartuser where username='cetesysadmin'), 
	modifieddate=now() WHERE displayidentifier in ('MS-cPass','CO-cPass') and activeflag is true;
