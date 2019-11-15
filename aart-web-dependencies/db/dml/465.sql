--dml/465.sql
	INSERT INTO authorities(
             authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('PERM_TESTRECORD_CREATE', 'Create Test Record','Student Management-Test Record ', current_timestamp,(Select id from aartuser where username='cetesysadmin'), 
            true, current_timestamp,(Select id from aartuser where username='cetesysadmin') );
			
	INSERT INTO authorities(
             authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('PERM_TESTRECORD_CLEAR', 'Clear Test Record','Student Management-Test Record ', current_timestamp,(Select id from aartuser where username='cetesysadmin'), 
            true, current_timestamp,(Select id from aartuser where username='cetesysadmin') );
			
	INSERT INTO authorities(
             authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('PERM_TESTRECORD_VIEW', 'View Test Record','Student Management-Test Record ', current_timestamp,(Select id from aartuser where username='cetesysadmin'), 
            true, current_timestamp,(Select id from aartuser where username='cetesysadmin') );
            
