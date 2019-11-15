--US15900: Reports - Batch process submission

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('PERM_BATCH_REPORT','Batch Reporting','Reports-Batch Process', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
INSERT INTO groupauthorities (authorityid, groupid, createduser, modifieduser) values
((select id from authorities where authority='PERM_BATCH_REPORT'),
(select id from groups where organizationid = (select id from organization where displayidentifier = 'CETE') and groupname = 'Global System Administrator'),
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO groupauthorities (authorityid, groupid, createduser, modifieduser) values
((select id from authorities where authority='PERM_BATCH_REPORT'),
(select id from groups where organizationid = (select id from organization where displayidentifier = 'CETE') and groupname = 'State System Administrator'),
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));