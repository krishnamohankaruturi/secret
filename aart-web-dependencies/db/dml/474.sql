-- 474.sql
--TASC CPass subjects
--Business Management and Administration
insert into organizationcontentarea values(51, (select id from contentarea where abbreviatedname='BM&A'), '12');

--Manufacturing
insert into organizationcontentarea values(51, (select id from contentarea where abbreviatedname='Mfg'), '13');

--Architecture & Construction
insert into organizationcontentarea values(51, (select id from contentarea where abbreviatedname='Arch&Const'), '17');

--Agriculture, Food and Natural Resources
insert into organizationcontentarea values(51, (select id from contentarea where abbreviatedname='AgF&NR'), '18');

delete from fieldspecificationsrecordtypes where mappedname like '%/xStudents%';
delete from fieldspecification where mappedname like '%/xStudents%';

-- "comprehensiveRace"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('comprehensiveRace', null, null, null, 5,false,false,
	'(0|1)(0|1)(0|1)(0|1)(0|1)','demographics/races/race/race',true,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='demographics/races/race/race';

-- "currentGradeLevel"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('currentGradeLevel', '{K,1,2,3,4,5,6,7,8,9,10,11,12}', null, null, 2,false,false,
	null,'enrollment/gradeLevel',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='enrollment/gradeLevel';
     
-- "currentSchoolYear"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('currentSchoolYear', null, 1900, 2099, 4,true,true,
	null,'enrollment/schoolYear',true,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='enrollment/schoolYear';

   
--"firstLanguage"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('firstLanguage', '{'''',0,1,2,3,4,5,6,7,8,10,11,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47}', null, null, 2,false,true,
	null,'languages/language/code',true,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='languages/language/code';

-- "gender"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('gender', '{0,1}', null, null, 1,true,false,
	null,'demographics/sex',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='demographics/sex';

-- "generationCode"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('generationCode', '{Jr.,Jr,Sr.,Sr,II,III,IV,V,'''',jr.,jr,sr.,sr,ii,iii,iv,v,JR.,JR,SR.,SR}', null, null, 10,false,false,
	null,'name/suffix',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification 
where mappedname='name/suffix';

-- "hispanicEthnicity"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('hispanicEthnicity', '{0,1}', null, null, 1,false,true,
	null,'demographics/hispanicLatinoEthnicity',true,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification 
where mappedname='demographics/hispanicLatinoEthnicity';

-- "legalFirstName"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('legalFirstName', null, null, null, 60,true,true,
	null,'name/givenName',true,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='name/givenName';

-- "legalLastName"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('legalLastName', null, null, null, 60,true,true,
	null,'name/familyName',true,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='name/familyName';
     
--"legalMiddleName"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('legalMiddleName', null, null, null, 60,false,false,
	null,'name/middleName',true,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='name/middleName';

-- "localStudentIdentifier"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('localStudentIdentifier', null, null, null, 20,false,false,
	null,'localId',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='localId';
    
-- "schoolEntryDate"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('schoolEntryDate', null, null, null, 10,true,false,
	null,'enrollment/entryDate',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='enrollment/entryDate';

-- "stateStudentIdentifier"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('stateStudentIdentifier', null, null, null, 10,true,true,
	'^[A-z0-9!@#$%^&*()-_''"~`:;\[\]{}<>+=\./\ ]++$','@refId',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='@refId';

-- "residenceDistrictIdentifier"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('residenceDistrictIdentifier', null, null, null, 30,false,false,
	null,'enrollment/leaRefId',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='enrollment/leaRefId';

-- "attendanceSchoolProgramIdentifier"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('attendanceSchoolProgramIdentifier', null, null, null, 30,false,false,
	null,'SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/attendanceSchoolProgramIdentifier',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/attendanceSchoolProgramIdentifier';

-- "assessmentProgram1"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('assessmentProgram1', null, null, null, 100,false,false,
	null,'SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/assessmentProgram1',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/assessmentProgram1';

-- "assessmentProgram2"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('assessmentProgram2', null, null, null, 100,false,false,
	null,'SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/assessmentProgram2',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/assessmentProgram2';

-- "assessmentProgram3"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('assessmentProgram3', null, null, null, 100,false,false,
	null,'SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/assessmentProgram3',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/assessmentProgram3';

-- "districtEntryDate"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('districtEntryDate', null, null, null, 10,true,false,
	null,'SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/districtEntryDate',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/districtEntryDate';

-- "stateEntryDate"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('stateEntryDate', null, null, null, 10,true,false,
	null,'SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/stateEntryDate',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/stateEntryDate';

-- "dateOfBirth"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('stateEntryDate', null, null, null, 10,true,false,
	null,'demographics/birthDate',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='demographics/birthDate';

-- "giftedStudent"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('giftedStudent', '{0,1}', null, null, 1,true,false,
	null,'SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/giftedStudent',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/giftedStudent';

-- "aypSchoolId"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('aypSchoolId', null, null, null, 20,false,false,
	null,'SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/AYPSchoolIdentifier',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/AYPSchoolIdentifier';

-- "esolParticipationCode"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('esolParticipationCode', null, null, null, 20,false,false,
	null,'SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/ESOLParticipationCode',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/ESOLParticipationCode';

-- "primaryDisabilityCode"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('primaryDisabilityCode', null, null, null, 20,false,false,
	null,'SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/primaryDisabilityCode',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='ENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/primaryDisabilityCode';

UPDATE SURVEYPAGESTATUS SET modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin'), activeflag=false where globalpagenum IN (15,16,17,18,19,20);

update  fieldspecification  set allowablevalues = '{'''',M,ELA,GKS,AgF&NR,BM&A,Arch&Const,Mfg,Sci,SCI}'
 where fieldname = 'subject' and id = ( select 
 fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select c.id from category c
join categorytype ct on ct.id = c.categorytypeid  
where categorycode='TEC_RECORD_TYPE' and ct.typecode ='CSV_RECORD_TYPE'  )
 and fieldspecificationid = ( select id from fieldspecification where
  fs.fieldspecificationid = id and fs.recordtypeid = (select c.id from category c
join categorytype ct on ct.id = c.categorytypeid  
where categorycode='TEC_RECORD_TYPE' and ct.typecode ='CSV_RECORD_TYPE'  ) and fieldname = 'subject')
);
