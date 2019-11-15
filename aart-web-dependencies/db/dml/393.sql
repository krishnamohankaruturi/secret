-- Upload Reports
-- US15913: Reports - Upload subscore conversion from raw to scale score by test

insert into category
(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate,
createduser, activeflag)
values 
('Subscore Raw to Scale Score', 'SUBSCORE_RAW_TO_SCALE_SCORE', 'Subscore Raw to Scale Score' , (select id from categorytype where typecode = 'CSV_RECORD_TYPE'),
'AART', now(), (Select id from aartuser where username = 'cetesysadmin'), true);

delete from fieldspecification where id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid = 
(select id from category where categorycode = 'SUBSCORE_RAW_TO_SCALE_SCORE' and categorytypeid=(select id from categorytype where typecode = 'CSV_RECORD_TYPE')));

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('subScoreDefinitionName', null, null, null, null, true,
 true, null, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'
);