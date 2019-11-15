--680.sql
-- Scripts to fix issues on SIF Tec file upload

-- missing mappedname for SIF exit student API
update fieldspecificationsrecordtypes set mappedname = 'enrollment/exitDate' ,
  modifieddate=now(),
  modifieduser=(select id from aartuser where username='cetesysadmin') 
where fieldspecificationid in (select id from fieldspecification  where fieldname ='exitWithdrawalDate')
and recordtypeid = (Select id from category where categorycode='UNENRL_XML_RECORD_TYPE');


-- Should not be using CSV upload fields for this.
update fieldspecification set allowablevalues = '{'''',1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,98,99}' ,
  fieldlength = 2,
  fieldtype=null,
  modifieddate=now(),
  modifieduser=(select id from aartuser where username='cetesysadmin') 
where id in (select fs.id from fieldspecificationsrecordtypes fsr
INNER JOIN fieldspecification fs on fs.id = fsr.fieldspecificationid
 where recordtypeid = (select id from category where categorycode  ='TEC_XML_RECORD_TYPE')
 and fs.fieldname in ('exitReason'));

-- Create a new field specification for exitReason which uses CEDS codes.
insert into fieldspecification (fieldname, allowablevalues, fieldlength, rejectifempty, rejectifinvalid, 
showerror, createddate, modifieddate, createduser, modifieduser, activeflag, iskeyvaluepairfield, fieldtype)
values ('exitWithdrawalType', '{1907,1908,1909,1910,1911,1912,1913,1914,1915,1916,1917,1918,
1919,1921,1922,1923,1924,1925,
1926,1927,1928,1930,1931,3499,3502,3503,3504,3505,3508,3509,73060,73061,9999,CANCEL}', null, false, false, true, now(),
now(), (Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin'), true, false, 'String');

-- Map new field to TEC_XML_RECORD_TYPE exitReason
update fieldspecificationsrecordtypes set 
fieldspecificationid = (
select id from fieldspecification where fieldname  = 'exitWithdrawalType' and rejectifempty is false and rejectifinvalid is false
and fieldtype = 'String') ,
  modifieddate=now(),
  modifieduser=(select id from aartuser where username='cetesysadmin') 
where fieldspecificationid in 
(select id from fieldspecification  where fieldname ='exitReason')
and recordtypeid = (Select id from category where categorycode='TEC_XML_RECORD_TYPE');

-- Create a new field specification for exitDate. Need rejectifempty & rejectifinvalid need to be done in custom validation.
insert into fieldspecification (fieldname, allowablevalues, fieldlength, rejectifempty, rejectifinvalid, 
showerror, createddate, modifieddate, createduser, modifieduser, activeflag, iskeyvaluepairfield, fieldtype)
values ('exitDateStr', null, null, false, false, true, now(),
now(), (Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin'), true, false, 'Date');

-- Map new field to TEC_XML_RECORD_TYPE exitDateStr 
update fieldspecificationsrecordtypes 
set fieldspecificationid =(
 	select id from fieldspecification 
 	where fieldname = 'exitDateStr' and rejectifempty is false and rejectifinvalid is false),
  modifieddate=now(),
  modifieduser=(select id from aartuser where username='cetesysadmin') 
where fieldspecificationid in (select id from fieldspecification  where fieldname ='exitDateStr')
and recordtypeid = (Select id from category where categorycode='TEC_XML_RECORD_TYPE');

