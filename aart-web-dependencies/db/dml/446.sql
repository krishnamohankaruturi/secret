--446.sql


update fieldspecification set fieldlength = 32 where fieldname = 'levelName';


-- US16657: First Contact Survey Adding Permissions To Control Viewing and Editing of Survey
INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('VIEW_FIRST_CONTACT_SURVEY','View First Contact Survey','Student Management-First Contact', 
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('EDIT_FIRST_CONTACT_SURVEY','Edit First Contact Survey','Student Management-First Contact', 
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	