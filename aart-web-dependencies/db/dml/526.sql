--dml/526.sql US17652 CombinedLevelMap

insert into category(categoryname, categorycode, categorydescription, categorytypeid, 
originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
values('Combined Level Map','COMBINED_LEVEL_MAP', 'Combined Level Map', (select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE'), 
'AART_ORIG_CODE', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));


--fieldspecifications
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select id from fieldspecification where fieldname  = 'schoolYear'), (select id from category where categorycode='COMBINED_LEVEL_MAP' and categorytypeid=(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'School_Year');

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select id from fieldspecification where fieldname  = 'assessmentProgram'), (select id from category where categorycode='COMBINED_LEVEL_MAP' and categorytypeid=(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'Assessment_Program');


insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select id from fieldspecification where fieldname  = 'subject' and allowablevalues is null), (select id from category where categorycode='COMBINED_LEVEL_MAP' and categorytypeid=(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'Subject');


insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select id from fieldspecification where fieldname  = 'grade'), (select id from category where categorycode='COMBINED_LEVEL_MAP' and categorytypeid=(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'Grade');


insert into fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, showerror, createduser, activeflag, modifieduser, iskeyvaluepairfield, fieldtype)
values('stagesLowScaleScore',null,null, null, null, true, true, true, (select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), false, 'number');

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select id from fieldspecification where fieldname  = 'stagesLowScaleScore'), (select id from category where categorycode='COMBINED_LEVEL_MAP' and categorytypeid=(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'Stages_Low_Scale_Score');

insert into fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, showerror, createduser, activeflag, modifieduser, iskeyvaluepairfield, fieldtype)
values('stagesHighScaleScore',null,null, null, null, true, true, true, (select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), false, 'number');

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select id from fieldspecification where fieldname  = 'stagesHighScaleScore'), (select id from category where categorycode='COMBINED_LEVEL_MAP' and categorytypeid=(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'Stages_High_Scale_Score');

insert into fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, showerror, createduser, activeflag, modifieduser, iskeyvaluepairfield, fieldtype)
values('performanceScaleScore',null,null, 9, null, true, true, true, (select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), false, 'number');

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select id from fieldspecification where fieldname  = 'performanceScaleScore'), (select id from category where categorycode='COMBINED_LEVEL_MAP' and categorytypeid=(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'Performance_Scale_Score');


insert into fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, showerror, createduser, activeflag, modifieduser, iskeyvaluepairfield, fieldtype)
values('combinedLevel',null,null, 9, null, true, true, true, (select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), false, 'number');

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select id from fieldspecification where fieldname  = 'combinedLevel'), (select id from category where categorycode='COMBINED_LEVEL_MAP' and categorytypeid=(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'Combined_Level');

insert into fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, showerror, createduser, activeflag, modifieduser, iskeyvaluepairfield, fieldtype)
values('comment',null,null, null, null, false, false, false, (select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), false, 'string');

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select id from fieldspecification where fieldname  = 'comment'), (select id from category where categorycode='COMBINED_LEVEL_MAP' and categorytypeid=(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'Comment');

