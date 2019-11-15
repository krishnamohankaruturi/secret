
--US14910
insert into category(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser) 
values('TEC Record Type', 'TEC_RECORD_TYPE', 'Test, Exit and Clear Record Type.', (select id from categorytype where typecode = 'CSV_RECORD_TYPE'), 'AART_ORIG_CODE', now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'));


insert into fieldspecification (fieldname, allowablevalues, fieldlength, rejectifempty, rejectifinvalid, showerror, createddate, modifieddate, createduser, modifieduser, activeflag, iskeyvaluepairfield)
values ('recordType', '{Test,Exit,Clear}', 5, true, true, true, now(), now(), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), true, false);

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createddate, modifieddate, createduser, modifieduser, activeflag)
values ((select id from fieldspecification where fieldname = 'recordType' and mappedname is null),(select id from category where categorycode='TEC_RECORD_TYPE'), now(), now(), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), true);

--statestudentidentifier already exists
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createddate, modifieddate, createduser, modifieduser, activeflag)
values ((select id from fieldspecification where fieldname = 'stateStudentIdentifier' and mappedname is null),(select id from category where categorycode='TEC_RECORD_TYPE'), now(), now(), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), true);


--attendanceSchoolProgramIdentifier already exists
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createddate, modifieddate, createduser, modifieduser, activeflag)
values ((select id from fieldspecification where fieldname = 'attendanceSchoolProgramIdentifier' and mappedname is null),(select id from category where categorycode='TEC_RECORD_TYPE'), now(), now(), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), true);

insert into fieldspecification (fieldname, allowablevalues, fieldlength, rejectifempty, rejectifinvalid, showerror, createddate, modifieddate, createduser, modifieduser, activeflag, iskeyvaluepairfield)
values ('exitReason', '{'''',1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,98,99}', 2, false, true, true, now(), now(), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), true, false);

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createddate, modifieddate, createduser, modifieduser, activeflag)
values ((select id from fieldspecification where fieldname = 'exitReason' and mappedname is null),(select id from category where categorycode='TEC_RECORD_TYPE'), now(), now(), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), true);

insert into fieldspecification (fieldname, fieldlength, rejectifempty, rejectifinvalid, formatregex, showerror, createddate, modifieddate, createduser, modifieduser, activeflag, iskeyvaluepairfield)
values ('exitDate', 10, false, true, '^((0?[1-9]|1[012])(/|-)(0?[1-9]|[12][0-9]|3[01])(/|-)(19|20)?[0-9][0-9])?$', true, now(), now(), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), true, false);

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createddate, modifieddate, createduser, modifieduser, activeflag)
values ((select id from fieldspecification where fieldname = 'exitDate' and mappedname is null),(select id from category where categorycode='TEC_RECORD_TYPE'), now(), now(), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), true);

update fieldspecification set rejectifempty=false, fieldlength=5 where fieldname='testType';

update fieldspecification set maximum=9999999999 where fieldname='stateStudentIdentifier' and mappedname is null;

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createddate, modifieddate, createduser, modifieduser, activeflag)
values ((select id from fieldspecification where fieldname = 'testType' and mappedname is null),(select id from category where categorycode='TEC_RECORD_TYPE'), now(), now(), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), true);

insert into fieldspecification (fieldname, allowablevalues, fieldlength, rejectifempty, rejectifinvalid, showerror, createddate, modifieddate, createduser, modifieduser, activeflag, iskeyvaluepairfield)
values ('subject', '{'''',M,ELA}', 5, false, true, true, now(), now(), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), true, false);

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createddate, modifieddate, createduser, modifieduser, activeflag)
values ((select id from fieldspecification where fieldname = 'subject' and mappedname is null),(select id from category where categorycode='TEC_RECORD_TYPE'), now(), now(), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), true);

insert into fieldspecification (fieldname, fieldlength, rejectifempty, rejectifinvalid, formatregex, showerror, createddate, modifieddate, createduser, modifieduser, activeflag, iskeyvaluepairfield)
values ('schoolYear', 4, true, true, '^((19|20)?[0-9][0-9])?$', true, now(), now(), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), true, false);

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createddate, modifieddate, createduser, modifieduser, activeflag)
values ((select id from fieldspecification where fieldname = 'schoolYear' and mappedname is null),(select id from category where categorycode='TEC_RECORD_TYPE'), now(), now(), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), true);
