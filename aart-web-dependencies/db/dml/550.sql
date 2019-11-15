--DE13313: Report: Upload Failed for Excluded Items showing ‘No field specifications found for upload type’.	
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'schoolYear' and mappedname is null
), (select id from category where categorycode = 'EXCLUDED_ITEMS' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'School_Year'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'assessmentProgram' and mappedname is null), 
(select id from category where categorycode = 'EXCLUDED_ITEMS' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Assessment_Program'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subject' and allowablevalues is null and mappedname is null), 
(select id from category where categorycode = 'EXCLUDED_ITEMS' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Subject'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'grade' and mappedname is null), 
(select id from category where categorycode = 'EXCLUDED_ITEMS' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Grade'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'taskVariantId' and mappedname is null), 
(select id from category where categorycode = 'EXCLUDED_ITEMS' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Task_Variant_Id'
);
	