-- F396 KAP Reports 2017
-- Upload subscore default stage ids

UPDATE subscoresmissingstages set schoolyear = 2016; 


insert into category(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
	values('Subscore default stage ID','SUBSCORE_DEFAULT_STAGE_IDS', 'Subscore default stage ids', (select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE'),
		'AART_ORIG_CODE', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin')); 



insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values((select id from fieldspecification where fieldname = 'schoolYear'), (select id from category where categorycode='SUBSCORE_DEFAULT_STAGE_IDS' and categorytypeid=(select id from
		categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
		'School_Year');

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values((select id from fieldspecification where fieldname = 'assessmentProgram'), (select id from category where categorycode='SUBSCORE_DEFAULT_STAGE_IDS' and categorytypeid=(select id from
		categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
		'Assessment_Program');

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values((select id from fieldspecification where fieldname = 'subject' and id in (select fieldspecificationid from fieldspecificationsrecordtypes
       where recordtypeid in (select id from category where categorytypeid=(select id from
		categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')))), (select id from category where categorycode='SUBSCORE_DEFAULT_STAGE_IDS' and categorytypeid=(select id from
		categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
		'Subject');



insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values((select id from fieldspecification where fieldname = 'grade' and id in (select fieldspecificationid from fieldspecificationsrecordtypes
       where recordtypeid in (select id from category where categorytypeid=(select id from
		categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')))), (select id from category where categorycode='SUBSCORE_DEFAULT_STAGE_IDS' and categorytypeid=(select id from
		categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
		'Grade');


insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values((select id from fieldspecification where fieldname = 'testId2'), (select id from category where categorycode='SUBSCORE_DEFAULT_STAGE_IDS' and categorytypeid=(select id from
		categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
		'TestID_2');		


insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values((select id from fieldspecification where fieldname = 'testId3'), (select id from category where categorycode='SUBSCORE_DEFAULT_STAGE_IDS' and categorytypeid=(select id from
		categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
		'TestID_3');	

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values((select id from fieldspecification where fieldname = 'performanceTestId'), (select id from category where categorycode='SUBSCORE_DEFAULT_STAGE_IDS' and categorytypeid=(select id from
		categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
		'Performance_TestID');


insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values((select id from fieldspecification where fieldname = 'comment'), (select id from category where categorycode='SUBSCORE_DEFAULT_STAGE_IDS' and categorytypeid=(select id from
		categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
		'Comment');
		