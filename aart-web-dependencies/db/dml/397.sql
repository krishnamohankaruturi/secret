
-- 397.sql

update fieldspecificationsrecordtypes set mappedname = 'Level_Number' where recordtypeid = (select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid = (select id from categorytype where typecode = 'CSV_RECORD_TYPE'))
and fieldspecificationid = (select id from fieldspecification where fieldname = 'level');

insert into category
(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate,
createduser, activeflag)
values 
('Subscore Framework Mapping', 'SUBSCORE_FRAMEWORK_MAPPING', 'Subscore Framework Mapping' , (select id from categorytype where typecode = 'CSV_RECORD_TYPE'),
'AART', now(), (Select id from aartuser where username = 'cetesysadmin'), true);


insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('framework', null, null, null, null, true,
 true, null, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'
);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('frameworkLevel1', null, null, null, null, true,
 true, null, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'
);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('frameworkLevel2', null, null, null, null, true,
 true, null, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'
);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('frameworkLevel3', null, null, null, null, true,
 true, null, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'assessmentProgram' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Assessment_Program'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'grade' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Grade'
);


insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subject' and allowablevalues is null and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Subject'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'schoolYear' and mappedname is null
), (select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'School_Year'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subscoreDefinitionName' and mappedname is null
), (select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Sub-score_Definition_Name'
);


insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'framework' and mappedname is null
), (select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Content_Framework'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'frameworkLevel1' and mappedname is null
), (select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Framework_Level_1_Code'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'frameworkLevel2' and mappedname is null
), (select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Framework_Level_2_Code'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'frameworkLevel3' and mappedname is null
), (select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Framework_Level_3_Code'
);


