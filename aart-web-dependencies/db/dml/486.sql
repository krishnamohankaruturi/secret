--dml/486.sql

INSERT INTO authorities(
            authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('PERM_TRANSFER_STUDENT', 'Transfer Student', 'Student Management-Transfer',current_timestamp,(Select id from aartuser where username='cetesysadmin'),
            true, current_timestamp, (Select id from aartuser where username='cetesysadmin'));
            
