-- F190, F286, F451 
-- Category for Local Education Authority
insert into category (categoryname, categorycode, categorydescription, createduser, activeflag, modifieduser, categorytypeid) 
values ('xLeas', 'LEA_XML_RECORD_TYPE', 'This indicates that the uploaded record is state or district record.', 
(Select id from aartuser where username='cetesysadmin'), TRUE, (Select id from aartuser where username='cetesysadmin'), 
(select id from categorytype where typecode ='XML_RECORD_TYPE'));

-- field Specification
-- organization name
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='LEA_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'leaName' as mappedname
from fieldspecificationsrecordtypes where mappedname='Org_Name' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');

-- organization display identifier
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='LEA_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'stateProvinceId' as mappedname
from fieldspecificationsrecordtypes where mappedname='Organization' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');

-- parent organization display identifier
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='LEA_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'leaRefId' as mappedname
from fieldspecificationsrecordtypes where mappedname='Org_Parent' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');

-- organization type code (ST/DT/SCH)
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='LEA_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'SIF_ExtendedElements/SIF_ExtendedElement[@name=''organization'']/leaType' as mappedname
from fieldspecificationsrecordtypes where mappedname='Org_Level' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');

-- parent organization type code (ST/DT/SCH)
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='LEA_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'SIF_ExtendedElements/SIF_ExtendedElement[@name=''organization'']/parentLeaType' as mappedname
from fieldspecificationsrecordtypes where mappedname='Org_TopLevel' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');


-- Category for School (xSchool)
insert into category (categoryname, categorycode, categorydescription, createduser, activeflag, modifieduser, categorytypeid) 
values ('xSchools', 'SCHOOL_XML_RECORD_TYPE', 'This indicates that the uploaded record is school record.', 
(Select id from aartuser where username='cetesysadmin'), TRUE, (Select id from aartuser where username='cetesysadmin'), 
(select id from categorytype where typecode ='XML_RECORD_TYPE'));

-- organization name
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='SCHOOL_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'schoolName' as mappedname
from fieldspecificationsrecordtypes where mappedname='Org_Name' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');

-- organization display identifier
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='SCHOOL_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'stateProvinceId' as mappedname
from fieldspecificationsrecordtypes where mappedname='Organization' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');

-- parent organization display identifier
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='SCHOOL_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'leaRefId' as mappedname
from fieldspecificationsrecordtypes where mappedname='Org_Parent' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');

-- organization type code (ST/DT/SCH)
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='SCHOOL_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'SIF_ExtendedElements/SIF_ExtendedElement[@name=''organization'']/leaType' as mappedname
from fieldspecificationsrecordtypes where mappedname='Org_Level' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');

-- parent organization type code (ST/DT/SCH)
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='SCHOOL_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'SIF_ExtendedElements/SIF_ExtendedElement[@name=''organization'']/parentLeaType' as mappedname
from fieldspecificationsrecordtypes where mappedname='Org_TopLevel' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');







-- Category for Local Education Authority
insert into category (categoryname, categorycode, categorydescription, createduser, activeflag, modifieduser, categorytypeid) 
values ('xLeas', 'DELETE_LEA_XML_RECORD_TYPE', 'This indicates that the uploaded record is state or district record.', 
(Select id from aartuser where username='cetesysadmin'), TRUE, (Select id from aartuser where username='cetesysadmin'), 
(select id from categorytype where typecode ='XML_RECORD_TYPE'));

-- field Specification
-- organization name
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='DELETE_LEA_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'leaName' as mappedname
from fieldspecificationsrecordtypes where mappedname='Org_Name' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');

-- organization display identifier
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='DELETE_LEA_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'stateProvinceId' as mappedname
from fieldspecificationsrecordtypes where mappedname='Organization' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');

-- parent organization display identifier
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='DELETE_LEA_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'leaRefId' as mappedname
from fieldspecificationsrecordtypes where mappedname='Org_Parent' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');

-- organization type code (ST/DT/SCH)
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='DELETE_LEA_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'SIF_ExtendedElements/SIF_ExtendedElement[@name=''organization'']/leaType' as mappedname
from fieldspecificationsrecordtypes where mappedname='Org_Level' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');

-- parent organization type code (ST/DT/SCH)
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='DELETE_LEA_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'SIF_ExtendedElements/SIF_ExtendedElement[@name=''organization'']/parentLeaType' as mappedname
from fieldspecificationsrecordtypes where mappedname='Org_TopLevel' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');


-- Category for School (xSchool)
insert into category (categoryname, categorycode, categorydescription, createduser, activeflag, modifieduser, categorytypeid) 
values ('xSchools', 'DELETE_SCHOOL_XML_RECORD_TYPE', 'This indicates that the uploaded record is school record.', 
(Select id from aartuser where username='cetesysadmin'), TRUE, (Select id from aartuser where username='cetesysadmin'), 
(select id from categorytype where typecode ='XML_RECORD_TYPE'));

-- organization name
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='DELETE_SCHOOL_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'schoolName' as mappedname
from fieldspecificationsrecordtypes where mappedname='Org_Name' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');

-- organization display identifier
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='DELETE_SCHOOL_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'stateProvinceId' as mappedname
from fieldspecificationsrecordtypes where mappedname='Organization' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');

-- parent organization display identifier
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='DELETE_SCHOOL_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'leaRefId' as mappedname
from fieldspecificationsrecordtypes where mappedname='Org_Parent' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');

-- organization type code (ST/DT/SCH)
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='DELETE_SCHOOL_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'SIF_ExtendedElements/SIF_ExtendedElement[@name=''organization'']/leaType' as mappedname
from fieldspecificationsrecordtypes where mappedname='Org_Level' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');

-- parent organization type code (ST/DT/SCH)
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select fieldspecificationid ,(Select id from category where categorycode='DELETE_SCHOOL_XML_RECORD_TYPE') recordtypeid, 
createduser as createduser,now() as createddate, activeflag as activeflag, 
modifieduser as modifieduser, 'SIF_ExtendedElements/SIF_ExtendedElement[@name=''organization'']/parentLeaType' as mappedname
from fieldspecificationsrecordtypes where mappedname='Org_TopLevel' and recordtypeid = (select id from category where categorycode='ORG_RECORD_TYPE');


INSERT into category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, 
createduser, activeflag, modifieddate, modifieduser)
values ( 'TEC Record','TEC_XML_RECORD_TYPE','This indicates that the uploaded record is TEC record.',
(select id from categorytype where typecode ilike 'XML_RECORD_TYPE'),
'AART',now(),(select id from aartuser where username ilike 'cetesysadmin'),'t',now(),(select id from aartuser where username ilike 'cetesysadmin'));

INSERT into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname)
values
( (select id from fieldspecification fs join fieldspecificationsrecordtypes fst on fst.fieldspecificationid =fs.id and fs.fieldname='testType' 
and fst.recordtypeid=(select id from category where categorycode='TEC_RECORD_TYPE' )), 
(select id from category where categorycode= 'TEC_XML_RECORD_TYPE'), now(), 
(select id from aartuser where username ilike 'cetesysadmin'), 't', now(),
(select id from aartuser where username ilike 'cetesysadmin'),'SIF_ExtendedElements/SIF_ExtendedElement[@name=''testType'']'),
( (select id from fieldspecification fs join fieldspecificationsrecordtypes fst on fst.fieldspecificationid =fs.id and fs.fieldname='attendanceSchoolProgramIdentifier' 
and fst.recordtypeid=(select id from category where categorycode='TEC_RECORD_TYPE' )), 
(select id from category where categorycode= 'TEC_XML_RECORD_TYPE'), now(), 
(select id from aartuser where username ilike 'cetesysadmin'), 't', now(),
(select id from aartuser where username ilike 'cetesysadmin'),'programRefId'),
( (select id from fieldspecification fs join fieldspecificationsrecordtypes fst on fst.fieldspecificationid =fs.id and fs.fieldname='schoolYear' 
and fst.recordtypeid=(select id from category where categorycode='TEC_RECORD_TYPE' )), 
(select id from category where categorycode= 'TEC_XML_RECORD_TYPE'), now(), 
(select id from aartuser where username ilike 'cetesysadmin'), 't', now(),
(select id from aartuser where username ilike 'cetesysadmin'),'schoolYear'),
( (select id from fieldspecification fs join fieldspecificationsrecordtypes fst on fst.fieldspecificationid =fs.id and fs.fieldname='recordType' 
and fst.recordtypeid=(select id from category where categorycode='TEC_RECORD_TYPE' )), 
(select id from category where categorycode= 'TEC_XML_RECORD_TYPE'), now(), 
(select id from aartuser where username ilike 'cetesysadmin'), 't', now(),
(select id from aartuser where username ilike 'cetesysadmin'),'entryType/code'),
( (select id from fieldspecification fs join fieldspecificationsrecordtypes fst on fst.fieldspecificationid =fs.id and fs.fieldname='subject' 
and fst.recordtypeid=(select id from category where categorycode='TEC_RECORD_TYPE' )), 
(select id from category where categorycode= 'TEC_XML_RECORD_TYPE'), now(), 
(select id from aartuser where username ilike 'cetesysadmin'), 't', now(),
(select id from aartuser where username ilike 'cetesysadmin'),'SIF_ExtendedElements/SIF_ExtendedElement[@name=''subject'']'),
( (select id from fieldspecification fs join fieldspecificationsrecordtypes fst on fst.fieldspecificationid =fs.id and fs.fieldname='stateStudentIdentifier' 
and fst.recordtypeid=(select id from category where categorycode='TEC_RECORD_TYPE' )), 
(select id from category where categorycode= 'TEC_XML_RECORD_TYPE'), now(), 
(select id from aartuser where username ilike 'cetesysadmin'), 't', now(),
(select id from aartuser where username ilike 'cetesysadmin'),'studentRefId'),
( (select id from fieldspecification fs join fieldspecificationsrecordtypes fst on fst.fieldspecificationid =fs.id and fs.fieldname='exitDateStr' 
and fst.recordtypeid=(select id from category where categorycode='TEC_RECORD_TYPE' )), 
(select id from category where categorycode= 'TEC_XML_RECORD_TYPE'), now(), 
(select id from aartuser where username ilike 'cetesysadmin'), 't', now(),
(select id from aartuser where username ilike 'cetesysadmin'),'exitDate'),
( (select id from fieldspecification fs join fieldspecificationsrecordtypes fst on fst.fieldspecificationid =fs.id and fs.fieldname='exitReason' 
and fst.recordtypeid=(select id from category where categorycode='TEC_RECORD_TYPE' )), 
(select id from category where categorycode= 'TEC_XML_RECORD_TYPE'), now(), 
(select id from aartuser where username ilike 'cetesysadmin'), 't', now(),
(select id from aartuser where username ilike 'cetesysadmin'),'exitType/code'),
( (select id from fieldspecification fs join fieldspecificationsrecordtypes fst on fst.fieldspecificationid =fs.id and fs.fieldname='grade' 
and fst.recordtypeid=(select id from category where categorycode='TEC_RECORD_TYPE' )), 
(select id from category where categorycode= 'TEC_XML_RECORD_TYPE'), now(), 
(select id from aartuser where username ilike 'cetesysadmin'), 't', now(),
(select id from aartuser where username ilike 'cetesysadmin'),'SIF_ExtendedElements/SIF_ExtendedElement[@name=''grade'']');

-- Permission for claim user option
INSERT INTO authorities(
            authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('PERM_USER_CLAIM','Claim Users','Administrative-User', current_timestamp, (Select id from aartuser where username='cetesysadmin'), 
            true, current_timestamp,(Select id from aartuser where username='cetesysadmin'));

-- Template for claim user notification.
insert into activationemailtemplate(templatename,assessmentprogramid,emailsubject,emailbody,includeeplogo,isdefault,allstates,activeflag,createddate,createduser,modifieddate,modifieduser) 
values('User Activation Email Template - Active User',null,'KITE Educator Portal User Account Action Request',
'<p>We are needing your assistance to update a user account in Kite Educator Portal.<br />
The following educator,[Educator display Name], has a district user who has identified 
them as an educator now working at their district and needing you to inactivate their account.</p>
<p>If this educator is no longer working in your district, we are needing you to remove them by inactivating their account. 
Please click the provided link to inactivate their current account.<br/>
[link]</p>',true,false,true,true,now(),(select id from aartuser where username='cetesysadmin'),now(), (select id from aartuser where username='cetesysadmin'));

insert into activationemailtemplatestate(templateid,stateid,createddate,createduser,activeflag,modifieddate,modifieduser)
values((select id from activationemailtemplate  where emailsubject='KITE Educator Portal User Account Action Request'),
null,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'));

update activationemailtemplate set allstates = true, isdefault = true where templatename = 'User Activation Email Template - Default';

--insert query for unEnrollment

insert into category (categoryname, categorycode, categorydescription, createduser, activeflag, modifieduser, categorytypeid) 
values ('UnEnrollment', 'UNENRL_XML_RECORD_TYPE', 'This indicates that the uploaded record is UnEnrollment record.', 
(Select id from aartuser where username='cetesysadmin'), TRUE, (Select id from aartuser where username='cetesysadmin'), 
(select id from categorytype where typecode ='XML_RECORD_TYPE'));

-- field Specification insert querys

--1.State Student Identifier
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='UNENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='stateProvinceId';

--2.Exit Date
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('exitDate', null, null, null, 10,true,false,
	null,null,false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='UNENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, 
modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='enrollment/exitDate';

--3.Exit Reason
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('exitWithdrawalType', null, null, null, 10,true,true,
	'',null,false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='UNENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/exitReason';

--4.School Year
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='UNENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='enrollment/schoolYear';

--5.Selected School Display Identifier
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='UNENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='enrollment/schoolRefId';

--6.Residence District Identifier
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='UNENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='enrollment/leaRefId';

--7.AYP School Id
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='UNENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='SIF_ExtendedElements/SIF_ExtendedElement[@name=''enrollment'']/AYPSchoolIdentifier';

-- "legalFirstName"
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='UNENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='name/givenName';

-- "legalLastName"
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='UNENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='name/familyName';
     
--"legalMiddleName"
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='UNENRL_XML_RECORD_TYPE') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='name/middleName';

update fieldspecification set fieldname='aypSchoolIdentifier', fieldtype='String' where id in (
select fs.id from fieldspecification fs
inner join fieldspecificationsrecordtypes  fsrt on fsrt.fieldspecificationid = fs.id
and recordtypeid in (select id from category where categorycode = 'ENRL_XML_RECORD_TYPE') and fieldname='aypSchoolId'
);
