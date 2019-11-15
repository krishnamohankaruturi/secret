INSERT INTO AUTHORITIES (authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('PUB_MODULE', 'Publish Module','Professional Development',now(),(select id from aartuser where username='cetesysadmin'),TRUE,now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser) VALUES (
	(select id from groups where groupname='PD Admin'and organizationid = (select id from organization where displayidentifier = 'CETE')), 
	(select id from authorities where authority = 'PUB_MODULE'),    
	(Select id from aartuser where username = 'cetesysadmin'), true, (Select id from aartuser where username = 'cetesysadmin')); 

INSERT INTO AUTHORITIES (authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('REL_MODULE', 'Release Module','Professional Development',now(),(select id from aartuser where username='cetesysadmin'),TRUE,now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser) VALUES (
	(select id from groups where groupname='PD State Admin'and organizationid = (select id from organization where displayidentifier = 'CETE')), 
	(select id from authorities where authority = 'REL_MODULE'),    
	(Select id from aartuser where username = 'cetesysadmin'), true, (Select id from aartuser where username = 'cetesysadmin')); 

INSERT INTO AUTHORITIES (authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('ENROLL_MODULE', 'Enroll Module','Professional Development',now(),(select id from aartuser where username='cetesysadmin'),TRUE,now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser) VALUES (
	(select id from groups where groupname='PD User'and organizationid = (select id from organization where displayidentifier = 'CETE')), 
	(select id from authorities where authority = 'ENROLL_MODULE'),    
	(Select id from aartuser where username = 'cetesysadmin'), true, (Select id from aartuser where username = 'cetesysadmin')); 
