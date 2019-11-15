--dml/528.sql - US17665 : Upload LevelDescription Report enhancements for 2016

UPDATE fieldspecificationsrecordtypes
SET recordtypeid=(SELECT id FROM category WHERE categorycode='LEVEL_DESCRIPTIONS' AND categorytypeid=(SELECT id FROM categorytype WHERE typecode = 'REPORT_UPLOAD_FILE_TYPE')),
modifieduser=(select id from aartuser where username='cetesysadmin'),
modifieddate=now()
WHERE recordtypeid=(SELECT id FROM category WHERE categorycode='LEVEL_DESCRIPTIONS' AND categorytypeid=(SELECT id FROM categorytype WHERE typecode = 'CSV_RECORD_TYPE'));

insert into fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, showerror, createduser, activeflag, modifieduser, iskeyvaluepairfield, fieldtype)
values('descriptionType','{MDPT,Main,Combined}',null, null, null, true, true, true, (select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), false, 'String');

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select id from fieldspecification where fieldname  = 'descriptionType'), (select id from category where categorycode='LEVEL_DESCRIPTIONS' and categorytypeid=(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'Description_Type');

insert into fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, showerror, createduser, activeflag, modifieduser, iskeyvaluepairfield, fieldtype)
values('descriptionParagraphPageBottom',null,null, null, null, false, true, true, (select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), false, 'String');

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select id from fieldspecification where fieldname  = 'descriptionParagraphPageBottom'), (select id from category where categorycode='LEVEL_DESCRIPTIONS' and categorytypeid=(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'Level_Description_Paragraph_Page_Bottom');

update fieldspecification set fieldlength=22, 
modifieduser= (select id from aartuser where username='cetesysadmin'),
modifieddate=now()
where fieldname='levelName';

delete from fieldspecificationsrecordtypes 
where recordtypeid=(select id from category where categorycode='LEVEL_DESCRIPTIONS' and categorytypeid=(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE'))
and mappedname='TestId_1';

delete from fieldspecificationsrecordtypes
where recordtypeid=(select id from category where categorycode='LEVEL_DESCRIPTIONS' and categorytypeid=(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE'))
and mappedname='TestId_2';

update fieldspecification set fieldlength=null, 
modifieduser= (select id from aartuser where username='cetesysadmin'),
modifieddate=now()
where fieldname='levelDescription';