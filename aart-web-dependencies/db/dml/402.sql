-- US15942: Reports - View reports UI - General navigation
INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('VIEW_GENERAL_STUDENT_REPORT','View general assessment student report','Reports-Performance Reports', 
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('VIEW_GENERAL_ROSTER_REPORT','View general assessment roster report','Reports-Performance Reports', 
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('VIEW_GENERAL_SCHOOL_REPORT','View general assessment school report','Reports-Performance Reports', 
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('VIEW_GENERAL_DISTRICT_REPORT','View general assessment district report','Reports-Performance Reports', 
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('VIEW_ALTERNATE_STUDENT_REPORT','View alternate assessment student report','Reports-Performance Reports', 
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('VIEW_ALTERNATE_STUDENT_REPORT','View alternate assessment roster report','Reports-Performance Reports', 
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));