
--new permissions for view operationaltestwindow, orgassessmentprogram and assessmentprogramparticipation
INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('PERM_OTW_VIEW','View Operational Test Window','Administrative-Operational Test Window', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
INSERT INTO groupauthorities (authorityid, groupid, createduser, modifieduser) values
((select id from authorities where authority='PERM_OTW_VIEW'),
(select id from groups where organizationid = (select id from organization where displayidentifier = 'CETE') and groupname = 'System Administrator'),
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('PERM_ASSESSPROGPARTIC_VIEW','View Assessment Program Participation','Administrative-Assessment Program Participation', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
INSERT INTO groupauthorities (authorityid, groupid, createduser, modifieduser) values
((select id from authorities where authority='PERM_ASSESSPROGPARTIC_VIEW'),
(select id from groups where organizationid = (select id from organization where displayidentifier = 'CETE') and groupname = 'System Administrator'),
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('PERM_MANAGEORGASSESSPROG_VIEW','View Manage Assessment Contract','Administrative-Manage Assessment Contract', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
INSERT INTO groupauthorities (authorityid, groupid, createduser, modifieduser) values
((select id from authorities where authority='PERM_MANAGEORGASSESSPROG_VIEW'),
(select id from groups where organizationid = (select id from organization where displayidentifier = 'CETE') and groupname = 'System Administrator'),
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));