-- 243.sql

delete from testcutscores;
delete from entityscalescores;
delete from entitysubscores;
delete from scaleandsem;
delete from userreportupload;

delete from category where categorycode = 'SCALE_AND_SEM';

delete from category where categorycode = 'ENTITY_SUBSCORES';

delete from category where categorycode = 'ENTITY_TOPIC_SCORES';

delete from category where categorycode = 'SUBSCORES_AND_SUBSCORE_SEM';

delete from category where categorycode = 'TOPIC_SCORES_AND_TOPIC_SCORE_SEM';

delete from category where categorycode = 'ENTITY_SCALE_SCORES';

DROP TABLE entityscalescores;
DROP TABLE entitysubscores;
DROP TABLE scaleandsem;

delete from fieldspecificationsrecordtypes where fieldspecificationid = (select id from fieldspecification where mappedname = 'ScaleScore');

delete from fieldspecification where mappedname = 'ScaleScore';
 
insert into category (categoryname, categorycode, categorydescription, categorytypeid, 
originationcode, createddate, activeflag) values
('Raw To Scale Scores','RAW_TO_SCALE_SCORES_RECORD_TYPE', 'Raw To Scale Scores', (select id from categorytype where typecode = 'CSV_RECORD_TYPE'), 'AART', current_timestamp, true);


insert into category (categoryname, categorycode, categorydescription, categorytypeid, 
 originationcode, createddate, activeflag) values 
 ('Raw To Scale Score', 'RAW_TO_SCALE_SCORE', 'Raw To Scale Score', (select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')
, 'AART_ORIG_CODE', current_timestamp, true);
 

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser)
values 
((select id from  fieldspecification where fieldname = 'testId'), (select id from category where categorycode = 'RAW_TO_SCALE_SCORES_RECORD_TYPE')
, current_timestamp, (select id from aartuser where username='cetesysadmin'), true, current_timestamp, (select id from aartuser where username='cetesysadmin'));

insert into fieldspecification 
(fieldname, minimum, maximum, rejectifempty, rejectifinvalid, mappedname, showerror,
createddate, activeflag, modifieddate, iskeyvaluepairfield, createduser, modifieduser)
values
('raw', 0, 9999, true, true, 'Raw', true, current_timestamp, true, current_timestamp, false, (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'));


insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser)
values 
((select id from  fieldspecification where fieldname = 'raw'), (select id from category where categorycode = 'RAW_TO_SCALE_SCORES_RECORD_TYPE')
, current_timestamp, (select id from aartuser where username='cetesysadmin'), true, current_timestamp, (select id from aartuser where username='cetesysadmin'));

insert into fieldspecification 
(fieldname, minimum, maximum, rejectifempty, rejectifinvalid, mappedname, showerror,
createddate, activeflag, modifieddate, iskeyvaluepairfield, createduser, modifieduser)
values
('scaleScore', 0, 9999, true, true, 'Scale Score', true, current_timestamp, true, current_timestamp, false, (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'));

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser)
values 
((select id from  fieldspecification where fieldname = 'scaleScore')
, (select id from category where categorycode = 'RAW_TO_SCALE_SCORES_RECORD_TYPE')
, current_timestamp, (select id from aartuser where username='cetesysadmin'), true, current_timestamp, (select id from aartuser where username='cetesysadmin'));

insert into fieldspecification 
(fieldname,fieldlength, rejectifempty, rejectifinvalid, mappedname, showerror,
createddate, activeflag, modifieddate, iskeyvaluepairfield, createduser, modifieduser)
values
('level', 100,  true, true, 'Level', true, current_timestamp, true, current_timestamp, false, (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'));

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser)
values 
((select id from  fieldspecification where fieldname = 'level')
, (select id from category where categorycode = 'RAW_TO_SCALE_SCORES_RECORD_TYPE')
, current_timestamp, (select id from aartuser where username='cetesysadmin'), true, current_timestamp, (select id from aartuser where username='cetesysadmin'));
