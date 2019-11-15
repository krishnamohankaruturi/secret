--/dml/577.sql
-- For US18829 to move the permission to Report tab.

UPDATE AUTHORITIES SET objecttype = 'Reports-Data Extracts' where displayname = 'PD Training Export File Creator' 
	and authority = 'PD_TRAINING_EXPORT_FILE_CREATOR';
	
-- For DE13888 update the category name
update category set categoryname = 'Decline to Answer' where categorycode = 'DA';

-- For US18828
INSERT INTO authorities(
            authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ( 'PERM_DLM_TRAINING_STATUS_EXTRACT','Create DLM Training Status Extract' ,'Reports-Data Extracts', current_timestamp,
    (Select id from aartuser where username='cetesysadmin') ,
	     true,current_timestamp, (Select id from aartuser where username='cetesysadmin'));