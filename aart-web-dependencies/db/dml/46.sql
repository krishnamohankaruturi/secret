
--R8 - Iter 3 

--US12803 Name: Quality Control Admin - Create new role and permission level.

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
('QUALITY_CONTROL_COMPLETE','Quality Control Complete','Quality Control',
(Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin'));

 INSERT INTO groups (organizationid, groupname, defaultrole, createduser,modifieduser)
 (select id,'QC Admin' as groupname, false as defaulrole,
 (Select id from aartuser where username = 'cetesysadmin') as createduser,
 (Select id from aartuser where username = 'cetesysadmin') as modifieduser
 from organization where displayidentifier = 'CETE' );
 
 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='QC Admin'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'QUALITY_CONTROL_COMPLETE'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin')); 
