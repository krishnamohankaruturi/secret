
-- 396.sql

insert into category
(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate,
createduser, activeflag)
values 
('Subscore Description and Report Usage', 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE', 'Subscore Description and Report Usage' , (select id from categorytype where typecode = 'CSV_RECORD_TYPE'),
'AART', now(), (Select id from aartuser where username = 'cetesysadmin'), true);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('report', '{Student,School,District,Roster}', null, null, null, true,
 true, null, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'
);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('subscoreDefinitionName', null, null, null, 30, true,
 true, null, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'
);


insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('subscoreReportDisplayName', null, null, null, 75, true,
 true, null, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'
);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('subscoreReportDescription', null, null, null, 200, true,
 true, null, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'
);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('subscoreDisplaySequence', null, null, null, null, true,
 true, null, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'number'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'assessmentProgram' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Assessment_Program'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subject' and allowablevalues is null and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Subject'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'schoolYear' and mappedname is null
), (select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'School_Year'
);


insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'report' and mappedname is null
), (select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Report'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subscoreDefinitionName' and mappedname is null
), (select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Sub-score_Definition_Name'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subscoreReportDisplayName' and mappedname is null
), (select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Sub-score_Report_Display_Name'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subscoreReportDescription' and mappedname is null
), (select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Sub-score_Report_Description'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subscoreDisplaySequence' and mappedname is null
), (select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Sub-score_Display_Sequence'
);