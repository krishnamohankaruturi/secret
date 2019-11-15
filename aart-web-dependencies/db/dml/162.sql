
--US13980 Batch Registration
INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('PERM_BATCH_REGISTER','Batch Registration','Batch', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
INSERT INTO groupauthorities (authorityid, groupid, createduser, modifieduser) values
((select id from authorities where authority='PERM_BATCH_REGISTER'),
(select id from groups where organizationid = (select id from organization where displayidentifier = 'CETE') and groupname = 'System Administrator'),
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));