----For ddl/*.sql
--DE13230: Raw to scale score upload
delete from fieldspecificationsrecordtypes where fieldspecificationid = (select id from fieldspecification where fieldname = 'performance_testid' and mappedname is null);
delete from fieldspecificationsrecordtypes where fieldspecificationid = (select id from fieldspecification where fieldname = 'performance_subject' and mappedname is null);
delete from fieldspecificationsrecordtypes where fieldspecificationid = (select id from fieldspecification where fieldname = 'performance_rawscore_include' and mappedname is null);

delete from fieldspecification where fieldname = 'performance_testid' and mappedname is null;
delete from fieldspecification where fieldname = 'performance_subject' and mappedname is null;
delete from fieldspecification where fieldname = 'performance_rawscore_include' and mappedname is null;

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('performanceSubject', null, null, null, null, false,
 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'string'
);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('performanceTestId', null, null, null, null, false,
 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'number'
);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('performanceRawscoreInclude', '{'''',yes,YES,Yes,yES,yeS,YEs,No,NO,no,nO}', null, null, 3, false,
 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'string'
);


insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'performanceTestId' and mappedname is null), 
(select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE'))
, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Performance_TestID');

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'performanceSubject' and mappedname is null), 
(select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Performance_Subject');

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'performanceRawscoreInclude' and mappedname is null), 
(select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Include_Performance_Items_In_Raw_Score'
);