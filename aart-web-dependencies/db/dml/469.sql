-- 469.sql
update category set categorytypeid=(select id from categorytype where typecode='XML_RECORD_TYPE') where categorycode='ENRL_RECORD_TYPE' and categorytypeid=(select id from categorytype where typecode='REPORT_TYPES_UI');

update category set categorycode='ENRL_XML_RECORD_TYPE' where categorytypeid = (select id from categorytype where typecode='XML_RECORD_TYPE');

update fieldspecificationsrecordtypes set  recordtypeid=(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') where mappedname like '/xStudents/xStudent%';

-- "assessmentProgram1"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('assessmentProgram1', null, null, null, 100,true,true,
	'','/xStudents/xStudent/enrollment/assessmentProgram1',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/enrollment/assessmentProgram1';

-- "assessmentProgram2"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('assessmentProgram2', null, null, null, 100,true,true,
	'','/xStudents/xStudent/enrollment/assessmentProgram2',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/enrollment/assessmentProgram2';

-- "assessmentProgram3"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('assessmentProgram3', null, null, null, 100,true,true,
	'','/xStudents/xStudent/enrollment/assessmentProgram3',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/enrollment/assessmentProgram3';

-- "attendanceSchoolProgramIdentifier"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('attendanceSchoolProgramIdentifier', null, null, null, 30,true,true,
	'','/xStudents/xStudent/enrollment/attendanceSchoolProgramIdentifier',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/enrollment/attendanceSchoolProgramIdentifier';

--"districtEntryDate"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('districtEntryDate', null, null, null, 10,true,false,
	null,'/xStudents/xStudent/enrollment/districtEntryDate',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/enrollment/districtEntryDate';

--"stateEntryDate"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('stateEntryDate', null, null, null, 10,true,false,
	null,'/xStudents/xStudent/enrollment/stateEntryDate',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/enrollment/stateEntryDate';

--"dateOfBirth"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('dateOfBirth', null, null, null, 10,true,false,
	null,'/xStudents/xStudent/dateOfBirth',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/dateOfBirth';

--"giftedStudent"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('giftedStudent', '{0,1}', null, null, 1,false,true,
	null,'/xStudents/xStudent/enrollment/giftedStudent',true,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification 
where mappedname='/xStudents/xStudent/enrollment/giftedStudent';

--"aypSchoolId"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('aypSchoolId', null, null, null, 20,false,false,
	null,'/xStudents/xStudent/enrollment/AYPSchoolIdentifier',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/enrollment/AYPSchoolIdentifier';

--"esolParticipationCode"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('esolParticipationCode', null, null, null, 20,false,false,
	null,'/xStudents/xStudent/enrollment/ESOLParticipationCode',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/enrollment/ESOLParticipationCode';

--"primaryDisabilityCode"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('primaryDisabilityCode', null, null, null, 20,false,false,
	null,'/xStudents/xStudent/enrollment/primaryDisabilityCode',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/enrollment/primaryDisabilityCode';

--"residenceDistrictIdentifier"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('residenceDistrictIdentifier', null, null, null, 30,false,false,
	null,'/xStudents/xStudent/enrollment/leaRefId',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/enrollment/leaRefId';

--sif changes
update fieldspecification set mappedname='/xStudents/xStudent/demographics/birthDate' where mappedname='/xStudents/xStudent/dateOfBirth';
update fieldspecificationsrecordtypes set mappedname='/xStudents/xStudent/demographics/birthDate' where mappedname='/xStudents/xStudent/dateOfBirth';

update fieldspecification set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/assessmentProgram1' where mappedname='/xStudents/xStudent/enrollment/assessmentProgram1';
update fieldspecificationsrecordtypes set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/assessmentProgram1' where mappedname='/xStudents/xStudent/enrollment/assessmentProgram1';

update fieldspecification set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/assessmentProgram2' where mappedname='/xStudents/xStudent/enrollment/assessmentProgram2';
update fieldspecificationsrecordtypes set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/assessmentProgram2' where mappedname='/xStudents/xStudent/enrollment/assessmentProgram2';

update fieldspecification set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/assessmentProgram3' where mappedname='/xStudents/xStudent/enrollment/assessmentProgram3';
update fieldspecificationsrecordtypes set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/assessmentProgram3' where mappedname='/xStudents/xStudent/enrollment/assessmentProgram3';

update fieldspecification set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/attendanceSchoolProgramIdentifier' where mappedname='/xStudents/xStudent/enrollment/attendanceSchoolProgramIdentifier';
update fieldspecificationsrecordtypes set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/attendanceSchoolProgramIdentifier' where mappedname='/xStudents/xStudent/enrollment/attendanceSchoolProgramIdentifier';

update fieldspecification set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/AYPSchoolIdentifier' where mappedname='/xStudents/xStudent/enrollment/AYPSchoolIdentifier';
update fieldspecificationsrecordtypes set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/AYPSchoolIdentifier' where mappedname='/xStudents/xStudent/enrollment/AYPSchoolIdentifier';

update fieldspecification set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/districtEntryDate' where mappedname='/xStudents/xStudent/enrollment/districtEntryDate';
update fieldspecificationsrecordtypes set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/districtEntryDate' where mappedname='/xStudents/xStudent/enrollment/districtEntryDate';

update fieldspecification set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/ESOLParticipationCode' where mappedname='/xStudents/xStudent/enrollment/ESOLParticipationCode';
update fieldspecificationsrecordtypes set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/ESOLParticipationCode' where mappedname='/xStudents/xStudent/enrollment/ESOLParticipationCode';

update fieldspecification set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/giftedStudent' where mappedname='/xStudents/xStudent/enrollment/giftedStudent';
update fieldspecificationsrecordtypes set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/giftedStudent' where mappedname='/xStudents/xStudent/enrollment/giftedStudent';

update fieldspecification set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/primaryDisabilityCode' where mappedname='/xStudents/xStudent/enrollment/primaryDisabilityCode';
update fieldspecificationsrecordtypes set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/primaryDisabilityCode' where mappedname='/xStudents/xStudent/enrollment/primaryDisabilityCode';

update fieldspecification set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/stateEntryDate' where mappedname='/xStudents/xStudent/enrollment/stateEntryDate';
update fieldspecificationsrecordtypes set mappedname='/xStudents/xStudent/SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/stateEntryDate' where mappedname='/xStudents/xStudent/enrollment/stateEntryDate';

