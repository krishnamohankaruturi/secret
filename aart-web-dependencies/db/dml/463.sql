
-- US16576 - ITI - History Overlay cancel assignment

-- Creating new role for cancel ITIassignment
insert into authorities (id, authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser)
values (nextval('authorities_id_seq'), 'CANCEL_ITI_ASSIGNMENT', 'ITI - cancel assigned test', 'Administrative-ITI',
now(), (select id from aartuser where username = 'cetesysadmin'),
true,
now(), (select id from aartuser where username = 'cetesysadmin'));


-- Once the ITI plan is cancelled about to update the studentstests and ititestsessionhistory with new status - iticancel
INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid, originationcode,
   createddate, createduser, activeflag, modifieddate, modifieduser) 
   values('ITI Cancel', 'iticancel', 'ITI cancel plan', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'), 'AART_ORIG'
           ,now(),12, true, now(), 12);
           
-- Changes from script bees for US 16756
INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('supportsStudentProvidedAccommodations',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='supportsStudentProvidedAccommodations'), 
		(select id from profileitemattributecontainer where attributecontainer = 'supportsProvidedOutsideSystem'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));	

--disable supportsProvidedOutsideSystem supportsStudentProvidedAccommodations-KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsStudentProvidedAccommodations'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--disable supportsProvidedOutsideSystem supportsStudentProvidedAccommodations-DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsStudentProvidedAccommodations'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--removing Adaptive equipment for DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsAdminIpad'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--removing Administration via ipad for DLM

insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsAdaptiveEquip'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


--adding calculator only for DLM 
INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('supportsCalculator',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='supportsCalculator'), 
		(select id from profileitemattributecontainer where attributecontainer = 'supportsRequiringAdditionalTools'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));	


-- hide calculator for KAP 
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsCalculator'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

-- hide calculator for AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsCalculator'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

    update fieldspecification  set formatregex = '(0?[1-9]|1[012])(/)(0?[1-9]|[12][0-9]|3[01])(/)(19|20)?[0-9][0-9]'where fieldname = 'dateOfBirth' and id = (select 
             fieldspecificationid from fieldspecificationsrecordtypes fs where
            recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' )
             and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
              fs.recordtypeid = (select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'dateOfBirth'));

        update fieldspecification  set formatregex = '(0?[1-9]|1[012])(/)(0?[1-9]|[12][0-9]|3[01])(/)(19|20)?[0-9][0-9]'where fieldname = 'schoolEntryDate' and id = (select 
             fieldspecificationid from fieldspecificationsrecordtypes fs where
            recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' )
             and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
              fs.recordtypeid = (select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'schoolEntryDate'));

    update fieldspecification  set formatregex = '^$|(0?[1-9]|1[012])(/)(0?[1-9]|[12][0-9]|3[01])(/)(19|20)?[0-9][0-9]'where fieldname = 'districtEntryDate' and id = (select 
             fieldspecificationid from fieldspecificationsrecordtypes fs where
            recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' )
             and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
              fs.recordtypeid = (select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'districtEntryDate'));

    update fieldspecification  set formatregex = '^$|(0?[1-9]|1[012])(/)(0?[1-9]|[12][0-9]|3[01])(/)(19|20)?[0-9][0-9]'where fieldname = 'stateEntryDate' and id = (select 
             fieldspecificationid from fieldspecificationsrecordtypes fs where
            recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' )
             and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
              fs.recordtypeid = (select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'stateEntryDate'));
              
INSERT INTO groups(organizationid, groupname, defaultrole, createddate, activeflag, 
            createduser, modifieduser, modifieddate, organizationtypeid, 
            roleorgtypeid, groupcode)
    VALUES ((select id from organization where organizationname = 'CETE Organization'), 'State Scorer', FALSE, CURRENT_TIMESTAMP, TRUE, 
            (select id from aartuser where email='cete@ku.edu'), (select id from aartuser where email='cete@ku.edu'), CURRENT_TIMESTAMP, (select id from organizationtype where typecode = 'ST'), 
            (select id from organizationtype where typecode = 'ST'), 'SSCO');

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('PERM_SCORE_CCQ_TESTS', 'Score CCQ Tests', 'Test Management-Scoring',CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'), true, 
            CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));            
            
            
-- New category type for XML element file parsing --
insert into categorytype (typename, typecode,typedescription, createduser, activeflag, modifieduser)
values ('XML Record Type', 'XML_RECORD_TYPE', 'xml description',(Select id from aartuser where username='cetesysadmin'), true, (Select id from aartuser where username='cetesysadmin'));

-- New category for Enrollment Record Type --
insert into category (categoryname, categorycode, categorydescription, createduser, activeflag, modifieduser, categorytypeid) 
values ('Enrollment', 'ENRL_RECORD_TYPE', 'This indicates that the uploaded record is Enrollment record.', (Select id from aartuser where username='cetesysadmin'), TRUE, (Select id from aartuser where username='cetesysadmin'), (select id from categorytype where typecode ='REPORT_TYPES_UI'));

-- "comprehensiveRace"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('comprehensiveRace', null, null, null, 5,false,false,
	'(0|1)(0|1)(0|1)(0|1)(0|1)','/xStudents/xStudent/demographics/races/race/race',true,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='GEN_ST') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/demographics/races/race/race';

-- "currentGradeLevel"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('currentGradeLevel', '{K,1,2,3,4,5,6,7,8,9,10,11,12}', null, null, 2,false,false,
	null,'/xStudents/xStudent/enrollment/gradeLevel',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='GEN_ST') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/enrollment/gradeLevel';
     
--"currentSchoolYear"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('currentSchoolYear', null, 1900, 2099, 4,true,true,
	null,'/xStudents/xStudent/enrollment/schoolYear',true,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='GEN_ST') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/enrollment/schoolYear';

   
--"firstLanguage"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('firstLanguage', '{'',0,1,2,3,4,5,6,7,8,10,11,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47}', null, null, 2,false,true,
	null,'/xStudents/xStudent/languages/language/code',true,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='GEN_ST') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/languages/language/code';

--"gender"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('gender', '{0,1}', null, null, 1,true,false,
	null,'/xStudents/xStudent/demographics/sex',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='GEN_ST') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/demographics/sex';

--"generationCode"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('generationCode', '{Jr.,Jr,Sr.,Sr,II,III,IV,V,'',jr.,jr,sr.,sr,ii,iii,iv,v,JR.,JR,SR.,SR}', null, null, 10,false,false,
	null,'/xStudents/xStudent/name/suffix',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='GEN_ST') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification 
where mappedname='/xStudents/xStudent/name/suffix';

--"hispanicEthnicity"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('hispanicEthnicity', '{0,1}', null, null, 1,false,true,
	null,'/xStudents/xStudent/demographics/hispanicLatinoEthnicity',true,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='GEN_ST') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification 
where mappedname='/xStudents/xStudent/demographics/hispanicLatinoEthnicity';

--legalFirstName
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('legalFirstName', null, null, null, 60,true,true,
	null,'/xStudents/xStudent/name/givenName',true,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='GEN_ST') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/name/givenName';

--"legalLastName"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('legalLastName', null, null, null, 60,true,true,
	null,'/xStudents/xStudent/name/familyName',true,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='GEN_ST') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/name/familyName';
     
--"legalMiddleName"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('legalMiddleName', null, null, null, 60,false,false,
	null,'/xStudents/xStudent/name/middleName',true,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='GEN_ST') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/name/middleName';

--"localStudentIdentifier"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('localStudentIdentifier', null, null, null, 20,false,false,
	null,'/xStudents/xStudent/localId',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='GEN_ST') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/localId';
    
--"schoolEntryDate"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('schoolEntryDate', null, null, null, 10,true,false,
	null,'/xStudents/xStudent/enrollment/entryDate',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='GEN_ST') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/enrollment/entryDate';

-- "stateStudentIdentifier"
insert into fieldspecification (fieldname, allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,
	formatregex,mappedname,showerror,createduser,activeflag,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
values ('stateStudentIdentifier', null, null, null, 10,true,true,
	'^[A-z0-9!@#$%^&*()-_''"~`:;\[\]{}<>+=\./\ ]++$','/xStudents/xStudent/@refId',false,(Select id from aartuser where username='cetesysadmin'),true,(Select id from aartuser where username='cetesysadmin'),false,null,null,null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser,createddate, activeflag,modifieduser,mappedname)
select id as fieldspecificationid,(Select id from category where categorycode='GEN_ST') recordtypeid, createduser as createduser,createddate as createddate, activeflag as activeflag, modifieduser as modifieduser, mappedname as mappedname
from fieldspecification where mappedname='/xStudents/xStudent/@refId';
