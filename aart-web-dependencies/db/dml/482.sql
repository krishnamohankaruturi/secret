--dml/482.sql

--   deactivate the category TICKET AT TEST
update category set activeflag=false where categorycode='TICKETED_AT_TEST';
-- new category type ticket of the day for OTW changes
insert into category (categoryname,categorycode,categorydescription,categorytypeid,createddate,createduser,activeflag,modifieddate,modifieduser) values('Ticket of the day','TICKET_OF_THE_DAY','Ticket of the day', (select id from categorytype where typecode = 'SESSION_RULES'),CURRENT_TIMESTAMP,(select id from aartuser where username='cetesysadmin'),'TRUE',CURRENT_TIMESTAMP,(select id from aartuser where username='cetesysadmin'));

-- SIF Roster API --
-- New category for Roster Record Type --
insert into category (categoryname, categorycode, categorydescription, createduser, activeflag, modifieduser, categorytypeid) 
values ('Roster', 'ROSTER_XML_RECORD_TYPE', 'This indicates that the uploaded record is Roster record.', 
(Select id from aartuser where username='cetesysadmin'), TRUE, (Select id from aartuser where username='cetesysadmin'), 
(select id from categorytype where typecode ='XML_RECORD_TYPE'));

-- "schoolIdentifier"
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser, activeflag,modifieduser,mappedname) 
select id as fieldspecificationid,(Select id from category where categorycode='ROSTER_XML_RECORD_TYPE') recordtypeid, fs.createduser as createduser,
 fs.activeflag as activeflag, fs.modifieduser as modifieduser, 'schoolRefId' as mappedname
from fieldspecification fs, fieldspecificationsrecordtypes fst where fieldname='schoolIdentifier' and fs.id = fst.fieldspecificationid
and fst.recordtypeid = (Select id from category where categorycode='SCRS_RECORD_TYPE');

-- "removefromroster"
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser, activeflag,modifieduser,mappedname) 
select id as fieldspecificationid,(Select id from category where categorycode='ROSTER_XML_RECORD_TYPE') recordtypeid, fs.createduser as createduser,
 fs.activeflag as activeflag, fs.modifieduser as modifieduser, 'SIF_ExtendedElements/SIF_ExtendedElement/removeFromRoster' as mappedname
from fieldspecification fs, fieldspecificationsrecordtypes fst where fieldname='removefromroster' and fs.id = fst.fieldspecificationid
and fst.recordtypeid = (Select id from category where categorycode='SCRS_RECORD_TYPE');
   
--"legalLastName"
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser, activeflag,modifieduser,mappedname) 
select id as fieldspecificationid,(Select id from category where categorycode='ROSTER_XML_RECORD_TYPE') recordtypeid, fs.createduser as createduser,
 fs.activeflag as activeflag, fs.modifieduser as modifieduser, 'students/studentReference/familyName' as mappedname
from fieldspecification fs, fieldspecificationsrecordtypes fst where fieldname='legalLastName' and fs.id = fst.fieldspecificationid
and fst.recordtypeid = (Select id from category where categorycode='SCRS_RECORD_TYPE');

-- "legalFirstName"
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser, activeflag,modifieduser,mappedname)  
select id as fieldspecificationid,(Select id from category where categorycode='ROSTER_XML_RECORD_TYPE') recordtypeid, fs.createduser as createduser,
 fs.activeflag as activeflag, fs.modifieduser as modifieduser, 'students/studentReference/givenName' as mappedname
from fieldspecification fs, fieldspecificationsrecordtypes fst where fieldname='legalFirstName' and fs.id = fst.fieldspecificationid
and fst.recordtypeid = (Select id from category where categorycode='SCRS_RECORD_TYPE');

-- "educatorFirstName"
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser, activeflag,modifieduser,mappedname) 
select id as fieldspecificationid,(Select id from category where categorycode='ROSTER_XML_RECORD_TYPE') recordtypeid, fs.createduser as createduser,
 fs.activeflag as activeflag, fs.modifieduser as modifieduser, 'primaryStaff/staffPersonReference/givenName' as mappedname
from fieldspecification fs, fieldspecificationsrecordtypes fst where fieldname='educatorFirstName' and fs.id = fst.fieldspecificationid
and fst.recordtypeid = (Select id from category where categorycode='SCRS_RECORD_TYPE');

-- "educatorLastName"
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser, activeflag,modifieduser,mappedname) 
select id as fieldspecificationid,(Select id from category where categorycode='ROSTER_XML_RECORD_TYPE') recordtypeid, fs.createduser as createduser,
 fs.activeflag as activeflag, fs.modifieduser as modifieduser, 'primaryStaff/staffPersonReference/familyName' as mappedname
from fieldspecification fs, fieldspecificationsrecordtypes fst where fieldname='educatorLastName' and fs.id = fst.fieldspecificationid
and fst.recordtypeid = (Select id from category where categorycode='SCRS_RECORD_TYPE');

-- "currentSchoolYear"
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser, activeflag,modifieduser,mappedname)  
select id as fieldspecificationid,(Select id from category where categorycode='ROSTER_XML_RECORD_TYPE') recordtypeid, fs.createduser as createduser,
 fs.activeflag as activeflag, fs.modifieduser as modifieduser, 'schoolYear' as mappedname
from fieldspecification fs, fieldspecificationsrecordtypes fst where fieldname='currentSchoolYear' and fs.id = fst.fieldspecificationid
and fst.recordtypeid = (Select id from category where categorycode='SCRS_RECORD_TYPE');

-- "stateCourseCode"
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser, activeflag,modifieduser,mappedname) 
select id as fieldspecificationid,(Select id from category where categorycode='ROSTER_XML_RECORD_TYPE') recordtypeid, fs.createduser as createduser,
 fs.activeflag as activeflag, fs.modifieduser as modifieduser, 'courseRefId' as mappedname
from fieldspecification fs, fieldspecificationsrecordtypes fst where fieldname='stateCourseCode' and fs.id = fst.fieldspecificationid
and fst.recordtypeid = (Select id from category where categorycode='SCRS_RECORD_TYPE');
     
--"stateStudentIdentifier"
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser, activeflag,modifieduser,mappedname)  
select id as fieldspecificationid,(Select id from category where categorycode='ROSTER_XML_RECORD_TYPE') recordtypeid, fs.createduser as createduser,
 fs.activeflag as activeflag, fs.modifieduser as modifieduser, 'students/studentReference/refId' as mappedname
from fieldspecification fs, fieldspecificationsrecordtypes fst where fieldname='stateStudentIdentifier' and fs.id = fst.fieldspecificationid
and fst.recordtypeid = (Select id from category where categorycode='SCRS_RECORD_TYPE');

-- "educatorIdentifier"
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser, activeflag,modifieduser,mappedname) 
select id as fieldspecificationid,(Select id from category where categorycode='ROSTER_XML_RECORD_TYPE') recordtypeid, fs.createduser as createduser,
 fs.activeflag as activeflag, fs.modifieduser as modifieduser, 'primaryStaff/staffPersonReference/refId' as mappedname
from fieldspecification fs, fieldspecificationsrecordtypes fst where fieldname='educatorIdentifier' and fs.id = fst.fieldspecificationid
and fst.recordtypeid = (Select id from category where categorycode='SCRS_RECORD_TYPE');
    
-- "stateSubjectAreaCode"
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser, activeflag,modifieduser,mappedname) 
select id as fieldspecificationid,(Select id from category where categorycode='ROSTER_XML_RECORD_TYPE') recordtypeid, fs.createduser as createduser,
 fs.activeflag as activeflag, fs.modifieduser as modifieduser, 'subject' as mappedname
from fieldspecification fs, fieldspecificationsrecordtypes fst where fieldname='stateSubjectAreaCode' and fs.id = fst.fieldspecificationid
and fst.recordtypeid = (Select id from category where categorycode='SCRS_RECORD_TYPE');

-- "localStudentIdentifier"
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser, activeflag,modifieduser,mappedname)  
select id as fieldspecificationid,(Select id from category where categorycode='ROSTER_XML_RECORD_TYPE') recordtypeid, fs.createduser as createduser,
 fs.activeflag as activeflag, fs.modifieduser as modifieduser, 'students/studentReference/localId' as mappedname
from fieldspecification fs, fieldspecificationsrecordtypes fst where fieldname='localStudentIdentifier' and fs.id = fst.fieldspecificationid
and fst.recordtypeid = (Select id from category where categorycode='SCRS_RECORD_TYPE');
-- SIF Roster API -- 

-- populate assessmentprogramid on all existing operationaltestwindows
update operationaltestwindow optw set assessmentprogramid = (
select distinct ap.id from
assessmentprogram ap JOIN testingprogram tp ON ap.id = tp.assessmentprogramid 
JOIN assessment ass ON tp.id = ass.testingprogramid 
JOIN assessmentstestcollections atc ON ass.id = atc.assessmentid 
JOIN testcollection tc ON atc.testcollectionid = tc.id 
JOIN operationaltestwindowstestcollections otwtc ON tc.id = otwtc.testcollectionid 
JOIN operationaltestwindow otw ON otwtc.operationaltestwindowid = otw.id 
where otwtc.testcollectionid = (select testcollectionid from operationaltestwindowstestcollections where operationaltestwindowid=optw.id order by modifieddate desc limit 1)
and otw.id = optw.id 
);