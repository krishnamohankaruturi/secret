
--R9-Iter1
--US12930, US13089, US12965 insert statements 


----PD Admin

 INSERT INTO groups (organizationid, groupname, defaultrole, createduser,modifieduser)
	(select id,'PD Admin' as groupname, false as defaulrole, (Select id from aartuser where username = 'cetesysadmin') as createduser, (Select id from aartuser where username = 'cetesysadmin') as modifieduser
		from organization where displayidentifier = 'CETE');

-- View PD

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('VIEW_PROFESSIONAL_DEVELOPMENT','View Professional Development','Professional Development',(Select id from aartuser where username='cetesysadmin'),(Select id from aartuser where username='cetesysadmin'));

 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser) VALUES (
	(select id from groups where groupname='PD Admin'and organizationid = (select id from organization where displayidentifier = 'CETE')), 
	(select id from authorities where authority = 'VIEW_PROFESSIONAL_DEVELOPMENT'),    
	(Select id from aartuser where username = 'cetesysadmin'), true, (Select id from aartuser where username = 'cetesysadmin')); 

-- View Modules

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('VIEW_MODULES','View Modules','Professional Development',(Select id from aartuser where username='cetesysadmin'),(Select id from aartuser where username='cetesysadmin'));

 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser) VALUES (
	(select id from groups where groupname='PD Admin'and organizationid = (select id from organization where displayidentifier = 'CETE')), 
	(select id from authorities where authority = 'VIEW_MODULES'),    
	(Select id from aartuser where username = 'cetesysadmin'), true, (Select id from aartuser where username = 'cetesysadmin')); 	

-- Edit Modules

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('EDIT_MODULES','Edit Modules','Professional Development',(Select id from aartuser where username='cetesysadmin'),(Select id from aartuser where username='cetesysadmin'));

 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser) VALUES (
	(select id from groups where groupname='PD Admin'and organizationid = (select id from organization where displayidentifier = 'CETE')), 
	(select id from authorities where authority = 'EDIT_MODULES'),    
	(Select id from aartuser where username = 'cetesysadmin'), true, (Select id from aartuser where username = 'cetesysadmin')); 		


-- Delete Modules

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('DELETE_MODULES','Delete Modules','Professional Development',(Select id from aartuser where username='cetesysadmin'),(Select id from aartuser where username='cetesysadmin'));

 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser) VALUES (
	(select id from groups where groupname='PD Admin'and organizationid = (select id from organization where displayidentifier = 'CETE')), 
	(select id from authorities where authority = 'DELETE_MODULES'),    
	(Select id from aartuser where username = 'cetesysadmin'), true, (Select id from aartuser where username = 'cetesysadmin')); 		


----PD User


 INSERT INTO groups (organizationid, groupname, defaultrole, createduser,modifieduser)
	(select id,'PD User' as groupname, false as defaulrole, (Select id from aartuser where username = 'cetesysadmin') as createduser, (Select id from aartuser where username = 'cetesysadmin') as modifieduser
		from organization where displayidentifier = 'CETE');

-- View PD

 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser) VALUES (
	(select id from groups where groupname='PD User'and organizationid = (select id from organization where displayidentifier = 'CETE')), 
	(select id from authorities where authority = 'VIEW_PROFESSIONAL_DEVELOPMENT'),    
	(Select id from aartuser where username = 'cetesysadmin'), true, (Select id from aartuser where username = 'cetesysadmin')); 

-- View Modules

 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser) VALUES (
	(select id from groups where groupname='PD User'and organizationid = (select id from organization where displayidentifier = 'CETE')), 
	(select id from authorities where authority = 'VIEW_MODULES'),    
	(Select id from aartuser where username = 'cetesysadmin'), true, (Select id from aartuser where username = 'cetesysadmin')); 	


----PD State Admin


 INSERT INTO groups (organizationid, groupname, defaultrole, createduser,modifieduser)
	(select id,'PD State Admin' as groupname, false as defaulrole, (Select id from aartuser where username = 'cetesysadmin') as createduser, (Select id from aartuser where username = 'cetesysadmin') as modifieduser
		from organization where displayidentifier = 'CETE');

-- View PD

 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser) VALUES (
	(select id from groups where groupname='PD State Admin'and organizationid = (select id from organization where displayidentifier = 'CETE')), 
	(select id from authorities where authority = 'VIEW_PROFESSIONAL_DEVELOPMENT'),    
	(Select id from aartuser where username = 'cetesysadmin'), true, (Select id from aartuser where username = 'cetesysadmin')); 


-- View Modules

 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser) VALUES (
	(select id from groups where groupname='PD State Admin'and organizationid = (select id from organization where displayidentifier = 'CETE')), 
	(select id from authorities where authority = 'VIEW_MODULES'),    
	(Select id from aartuser where username = 'cetesysadmin'), true, (Select id from aartuser where username = 'cetesysadmin')); 	


