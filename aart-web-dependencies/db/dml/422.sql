--422.sql

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('VIEW_ALTERNATE_STUDENT_REPORT','View alternate assessment student report','Reports-Performance Reports', 
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('VIEW_ALTERNATE_ROSTER_REPORT','View alternate assessment roster report','Reports-Performance Reports', 
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));