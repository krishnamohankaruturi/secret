

--US13080
INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('VIEW_ADMIN','View Admin','Professional Development',(Select id from aartuser where username='cetesysadmin'),(Select id from aartuser where username='cetesysadmin'));

 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser) VALUES (
	(select id from groups where groupname='PD Admin'and organizationid = (select id from organization where displayidentifier = 'CETE')), 
	(select id from authorities where authority = 'VIEW_ADMIN'),    
	(Select id from aartuser where username = 'cetesysadmin'), true, (Select id from aartuser where username = 'cetesysadmin')); 	

 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser) VALUES (
	(select id from groups where groupname='PD State Admin'and organizationid = (select id from organization where displayidentifier = 'CETE')), 
	(select id from authorities where authority = 'VIEW_ADMIN'),    
	(Select id from aartuser where username = 'cetesysadmin'), true, (Select id from aartuser where username = 'cetesysadmin')); 	