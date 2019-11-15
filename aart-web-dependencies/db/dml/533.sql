--For ddl/*.sql

--US17646: Reports: Upload test cut scores enhancements for 2016 

--Remove TestID validations 
delete from fieldspecificationsrecordtypes
where fieldspecificationid = (select id from fieldspecification where fieldname = 'testId1' and mappedname is null) 
	and recordtypeid = (select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE'))
	and mappedname = 'TestId_1' and activeflag is true;

delete from fieldspecificationsrecordtypes
where fieldspecificationid = (select id from fieldspecification where fieldname = 'testId2' and mappedname is null) 
	and recordtypeid = (select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE'))
	and mappedname = 'TestId_2' and activeflag is true;
	
	
--If recordtype used is 'REPORT_UPLOAD_FILE_TYPE' instead of 'CSV_RECORD_TYPE'

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'schoolYear' and mappedname is null
), (select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'School_Year'
);


insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'assessmentProgram' and mappedname is null), 
(select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid = (select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
now(), 
(Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Assessment_Program'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subject' and allowablevalues is null and mappedname is null), 
(select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Subject'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'grade' and mappedname is null), 
(select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Grade'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'level' and allowablevalues is null), 
(select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Level_Number'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'levelLowCutScore' and allowablevalues is null), 
(select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Level_Low_Cut_Score'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'levelHighCutScore' and allowablevalues is null), 
(select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Level_High_Cut_Score'
);
	


--US17638: Reports: Upload raw to scale score enhancements for 2016    
--Field Validations 
insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('testId3', null, null, null, null, false,
 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'number'
);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('testId4', null, null, null, null, false,
 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'number'
);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('performance_subject', null, null, null, null, false,
 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'string'
);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('performance_testid', null, null, null, null, false,
 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'number'
);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('performance_rawscore_include', '{'''',yes,YES,Yes,yES,yeS,YEs,No,NO,no,nO}', null, null, 3, false,
 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'string'
);


insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('performanceItemWeight', null, 0.0001, null, null, false,
 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'decimal'); 

-------------------------

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'schoolYear' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'School_Year'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'assessmentProgram' and mappedname is null), 
(select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid = (select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
now(), 
(Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Assessment_Program'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subject' and allowablevalues is null and mappedname is null), 
(select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Subject'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'grade' and mappedname is null), 
(select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Grade'
);


insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'testId1' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'TestID_1'
);


insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'testId2' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'TestID_2'
);


insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'testId3' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'TestID_3'
);


insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'testId4' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'TestID_4'
);


insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'performance_testid' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Performance_TestID'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'performance_subject' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Performance_Subject'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'performance_rawscore_include' and mappedname is null), 
(select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Include_Performance_Items_In_Raw_Score'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'performanceItemWeight' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Performance_Item_Weight'
);
  

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'rawScore' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Raw_Score'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'scaleScore' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Scale_Score'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'standardError' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Standard_Error'
);

