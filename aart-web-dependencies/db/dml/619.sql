--dml/619.sql
         
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ( 'EDIT_INACTIVE_ACCOUNTS','Edit Inactive Accounts' ,'Administrative-View Tools', current_timestamp,
(Select id from aartuser where username='cetesysadmin'),true,current_timestamp, (Select id from aartuser where username='cetesysadmin'));

update fieldspecification set rejectifempty = false where id = (select id from fieldspecification where fieldname  = 'stage');

update authorities set displayname = 'View English Language Learner assess student score file' where authority = 'VIEW_KELPA_ELP_STUDENT_SCORE';
