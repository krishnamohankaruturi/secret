----dml/*.sql ==> For ddl/*.sql
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'TOOLS_REACTIVATE_ORGANIZATION','Tools Reactivate Organization' ,'Administrative-View Tools', current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp, (Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'VIEW_STUDENT_INFORMATION','View Student Information','Administrative-View Tools',current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp,(Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'EDIT_TDE_LOGIN','Edit TDE Login' ,'Administrative-View Tools', current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp, (Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'MERGE_STUDENT_RECORDS','Merge Student Records' ,'Administrative-View Tools', current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp, (Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'MERGE_STUDENT_RECORDS','Merge Student Records' ,'Administrative-View Tools', current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp, (Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'VIEW_MISCELLANEOUS','View Miscellaneous','Administrative-View Miscellaneous',current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp,(Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'CUSTOM_REPORTS','Custom Reports','Administrative-View Miscellaneous',current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp,(Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'AUDIT_HISTORY','Audit History','Administrative-View Miscellaneous',current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp,(Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'VIEW_TEST_RESET','View Test Reset','Administrative-View Tools',current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp,(Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'RESET_DLM_TESTLETS','Reset DLM Testlets','Administrative-View Tools',current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp,(Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'MANAGE_LCS','Manage LCS','Administrative-View Tools',current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp,(Select id from aartuser where username='cetesysadmin'));
