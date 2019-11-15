--dml/630.sql
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'EDIT_INTERNAL_USERS','Edit User Type' ,'Administrative-View Tools', current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp, (Select id from aartuser where username='cetesysadmin'));