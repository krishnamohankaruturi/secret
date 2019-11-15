--dml/*.sql

--US17808: Reports: Upload subscore descriptions, usage enhancements for 2016
--Create the fieldspec for the new column
insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('sectionLineBelow', '{'''',yes,yeS,yES,YES,YEs,Yes,no,nO,NO,No}', null, null, 3, true,
 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'string'
);

--Create the fieldspecrecordtypes for the all the columns for Subscore descriptions and usage file upload for the categorytype='REPORT_UPLOAD_FILE_TYPE' instead of 'CSV_RECORD_TYPE'
--Assessment_Program
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'assessmentProgram' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Assessment_Program'
);
--Subject
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subject' and allowablevalues is null and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Subject'
);
--School_Year
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'schoolYear' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'School_Year'
);
--Report
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'report' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Report'
);
--Sub-score_Definition_Name
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subscoreDefinitionName' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Sub-score_Definition_Name'
);
--Sub-score_Report_Display_Name
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subscoreReportDisplayName' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Sub-score_Report_Display_Name'
);
--Sub-score_Display_Sequence
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subscoreReportDescription' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Sub-score_Report_Description'
);
--Sub-score_Display_Sequence
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subscoreDisplaySequence' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Sub-score_Display_Sequence'
);
--Section_Line_Below
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'sectionLineBelow' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Section_Line_Below'
);


--Create the fieldspecrecordtypes for the all the columns for Subscore framework upload for the categorytype='REPORT_UPLOAD_FILE_TYPE' instead of 'CSV_RECORD_TYPE'
--Subscore description upload is dependent on Subscore framework upload
--School_Year :schoolYear
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'schoolYear' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'School_Year'
);
-- Assessment_Program  : assessmentProgram
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'assessmentProgram' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Assessment_Program'
);
--Subject :subject
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subject' and allowablevalues is null and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Subject'
);
--Grade :grade
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'grade' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Grade'
);
--Sub-score_Definition_Name :subscoreDefinitionName
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subscoreDefinitionName' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Sub-score_Definition_Name'
);
--Content_Framework :framework
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'framework' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Content_Framework'
);
-- Framework_Level_1_Code  : frameworkLevel1 
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'frameworkLevel1' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Framework_Level_1_Code'
);
-- Framework_Level_2_Code  : frameworkLevel2
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'frameworkLevel2' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Framework_Level_2_Code'
);
-- Framework_Level_3_Code  : frameworkLevel3
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'frameworkLevel3' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_FRAMEWORK_MAPPING' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Framework_Level_3_Code'
);

