--For /ddl/433.sql

--ChangePond
delete from groupauthorities where authorityid=(select id from authorities where authority = 'PERM_VIEW_STUDENT_PASSWORD');
delete from authorities where authority = 'PERM_VIEW_STUDENT_PASSWORD';

Delete from groupauthorities where authorityid in (Select id from authorities where authority='PERM_USER_MERGE');
Delete from authorities where authority='PERM_USER_MERGE';

INSERT INTO authorities(
            authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('PERM_USER_MERGE','Merge Users','Administrative-User', current_timestamp, (Select id from aartuser where username='cetesysadmin'), 
            true, current_timestamp,(Select id from aartuser where username='cetesysadmin'));
            
update fieldspecification set allowablevalues = '{CONS,ST,RG,AR,DT,BLDG,SCH}'
		where fieldname = 'organizationTypeCode'  and id = (select 
			  fieldspecificationid from fieldspecificationsrecordtypes fs where
			  recordtypeid = ( select id from category where categorycode='ORG_RECORD_TYPE' )
			  and fieldspecificationid = ( select id from  fieldspecification where fs.fieldspecificationid = id and 
			  fs.recordtypeid = ( select id from category where categorycode='ORG_RECORD_TYPE' ) 
			  and fieldname = 'organizationTypeCode')
		);

update fieldspecification set allowablevalues = '{CONS,ST,RG,AR,DT,BLDG,SCH}'
	   where fieldname = 'contractOrgDisplayId'  and id = (select 
			fieldspecificationid from fieldspecificationsrecordtypes fs where
			recordtypeid = ( select id from category where categorycode='ORG_RECORD_TYPE' )
			and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
			fs.recordtypeid = ( select id from category where categorycode='ORG_RECORD_TYPE' ) and fieldname = 'contractOrgDisplayId'  )
		);
		