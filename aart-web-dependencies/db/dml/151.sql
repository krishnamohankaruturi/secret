INSERT INTO AUTHORITIES (authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('STATE_ADMIN', 'State Admin','Professional Development',now(),(select id from aartuser where username='cetesysadmin'),TRUE,now(),(select id from aartuser where username='cetesysadmin'));

INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser) VALUES (
	(select id from groups where groupname='PD State Admin'and organizationid = (select id from organization where displayidentifier = 'CETE')), 
	(select id from authorities where authority = 'STATE_ADMIN'),    
	(Select id from aartuser where username = 'cetesysadmin'), true, (Select id from aartuser where username = 'cetesysadmin')); 

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Released', 'RELEASED', 'The module is released', (select id from categorytype where typecode='MODULE_STATUS'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));