--dml/743.sql F725 ISMART

--Populate DLM assessment program is for existing records
UPDATE complexityband SET assessmentprogramid = (select id from assessmentprogram where abbreviatedname='DLM' and activeflag is true);

--Modify display name for FCS extract permission to reflect on Roles page
update authorities set displayname = 'Create DLM/ISMART First Contact Survey Extract' 
	where authority = 'DATA_EXTRACTS_FCS_REPORT';

--Permission for ISMART/ISMART2 Test administration monitoring extract
insert into authorities 
   (authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,labelname,level,sortorder)
   select 'DATA_EXTRACTS_ISMART_TEST_ADMIN','Create ISMART/2 Test Administration Monitoring Extract','Reports-Data Extracts',now(),
           (select id from aartuser where username='cetesysadmin'),TRUE, now(),
           (select id from aartuser where username='cetesysadmin'),'Reports','Data Extracts','ISMART-Specific Extracts',1,
			(select max(sortorder) from authorities where groupingname ='Data Extracts' and labelname='Internal (KU) Extracts')+50
          
   where not exists(
   select 1 from authorities where authority='DATA_EXTRACTS_ISMART_TEST_ADMIN'
   );
   

-- Changes for F784 Access Profile -> PNP Profile rename
update authorities set objecttype='Student Management-PNP Profile', labelName='PNP Profile' where authority = 'PERSONAL_NEEDS_PROFILE_UPLOAD';
update authorities set objecttype='Student Management-PNP Profile', labelName='PNP Profile' where authority = 'CREATE_STUDENT_PNP';
update authorities set objecttype='Student Management-PNP Profile', labelName='PNP Profile'  where authority = 'EDIT_STUDENT_PNP';
update authorities set objecttype='Student Management-PNP Profile', labelName='PNP Profile'  where authority = 'VIEW_STUDENT_PNP';

update authorities set objecttype='Student Management-PNP Profile' where authority = 'PERM_PNP_OPTIONS';

update modulereport set reporttype='PNP Setting Counts' where reporttype='Accessibility Profile Counts';