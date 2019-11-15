--For /ddl/428.sql
--US16289 change pond
delete from  fieldspecificationsrecordtypes  fs  where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) 
and fieldname =  'dlmStatus' )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

--US 16334 Script Bees
UPDATE surveylabel SET label = 'Arm and hand control: Mark all that apply-Cannot use hands to complete tasks even with assistance'
    WHERE labelnumber = 'Q29_4';
    
--US 16374 Script Bees
update surveyresponse set responselabel=3 where id=360;
update surveyresponse set responselabel=4 where id=361;

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=1),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=2),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=3),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q201'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=1),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q201'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=2),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q201'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=3),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

/*US16352 - Organization Change pond*/

update fieldspecificationsrecordtypes set mappedname = 'Organization' where 
fieldspecificationid = ( select id from fieldspecification where fieldname = 'displayIdentifier'  )
and recordtypeid =( select id from category where categorycode='ORG_RECORD_TYPE' );

update fieldspecificationsrecordtypes set mappedname = 'Org_Name' where 
fieldspecificationid = ( select id from fieldspecification where fieldname = 'organizationName'  )
and recordtypeid =( select id from category where categorycode='ORG_RECORD_TYPE' );

update fieldspecificationsrecordtypes set mappedname = 'Org_Level' where 
fieldspecificationid = ( select id from fieldspecification where fieldname = 'organizationTypeCode'  )
and recordtypeid =( select id from category where categorycode='ORG_RECORD_TYPE' );

update fieldspecificationsrecordtypes set mappedname = 'Org_Parent' where 
fieldspecificationid = ( select id from fieldspecification where fieldname = 'parentDisplayIdentifier'  )
and recordtypeid =( select id from category where categorycode='ORG_RECORD_TYPE' );

update fieldspecificationsrecordtypes set mappedname = 'Org_TopLevel' where 
fieldspecificationid = ( select id from fieldspecification where fieldname = 'contractOrgDisplayId'  )
and recordtypeid =( select id from category where categorycode='ORG_RECORD_TYPE' );

/*Enrollment */

update fieldspecificationsrecordtypes fs set mappedname = 'Attendance_District_Identifier' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'residenceDistrictIdentifier'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Student_Legal_Last_Name' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'legalLastName'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Student_Legal_First_Name' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'legalFirstName'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Current_School_Year' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'currentSchoolYear'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'State_Student_Identifier' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'stateStudentIdentifier'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Local_Student_Identifier' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'localStudentIdentifier'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Student_Legal_Middle_Name' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'legalMiddleName'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Generation_Code' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'generationCode'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Gender' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'gender'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Date_of_Birth' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'dateOfBirth'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Current_Grade_Level' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'currentGradeLevel'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Attendance_School_Program_Identifier' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'attendanceSchoolProgramIdentifier'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'School_Entry_Date' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'schoolEntryDate'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'District_Entry_Date' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'districtEntryDate'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'State_Entry_Date' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'stateEntryDate'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Comprehensive_Race' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'comprehensiveRace'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Primary_Disability_Code' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'primaryDisabilityCode'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Gifted_Student' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'giftedStudent'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'First_Language' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'firstLanguage'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'AYP_School_Identifier' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'aypSchoolIdentifier'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'DLM_Status' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'dlmStatus'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'ESOL_Participation_Code' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'esolParticipationCode'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Hispanic_Ethnicity' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'hispanicEthnicity'  )
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

update fieldspecification  set rejectifempty = true where fieldname = 'hispanicEthnicity' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'hispanicEthnicity'  )
);

delete from  fieldspecificationsrecordtypes  fs  where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE' ) 
and fieldname in( 'esolProgramEndingDate','esolProgramEntryDate','usaEntryDate','fundingSchool'  ))
and recordtypeid =( select id from category where categorycode='ENRL_RECORD_TYPE' );

insert into fieldspecification 
	(fieldname,allowablevalues,minimum,maximum,fieldlength,	rejectifempty,rejectifinvalid,formatregex,mappedname,
	showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
	values('assessmentProgram1',null,null,null,100,false,false,'^[A-z0-9!@#$%^&*()-_''~`:,\[\]{}<>+=\./\ ]++$',null,true,current_timestamp,
	(select id from aartuser where username='cetesysadmin'),true,current_timestamp,(select id from aartuser where username='cetesysadmin'),false,null,null,null);	
 

  insert into fieldspecificationsrecordtypes 
	  select id, (select id as recordtypeid from category where categorycode='ENRL_RECORD_TYPE'),current_timestamp,(select id from aartuser where username='cetesysadmin'),
	  true,current_timestamp,(select id from aartuser where username='cetesysadmin'),'Assessment_Program_1' 
	  from fieldspecification where fieldname = 'assessmentProgram1' ;


insert into fieldspecification 
	(fieldname,allowablevalues,minimum,maximum,fieldlength,	rejectifempty,rejectifinvalid,formatregex,mappedname,
	showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
	values('assessmentProgram2',null,null,null,100,false,false,'^[A-z0-9!@#$%^&*()-_''~`:,\[\]{}<>+=\./\ ]++$',null,true,current_timestamp,
	(select id from aartuser where username='cetesysadmin'),true,current_timestamp,(select id from aartuser where username='cetesysadmin'),false,null,null,null);	
 

  insert into fieldspecificationsrecordtypes 
	  select id, (select id as recordtypeid from category where categorycode='ENRL_RECORD_TYPE'),current_timestamp,(select id from aartuser where username='cetesysadmin'),
	  true,current_timestamp,(select id from aartuser where username='cetesysadmin'),'Assessment_Program_2' 
	  from fieldspecification where fieldname = 'assessmentProgram2' ;


insert into fieldspecification 
	(fieldname,allowablevalues,minimum,maximum,fieldlength,	rejectifempty,rejectifinvalid,formatregex,mappedname,
	showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
	values('assessmentProgram3',null,null,null,100,false,false,'^[A-z0-9!@#$%^&*()-_''~`:,\[\]{}<>+=\./\ ]++$',null,true,current_timestamp,
	(select id from aartuser where username='cetesysadmin'),true,current_timestamp,(select id from aartuser where username='cetesysadmin'),false,null,null,null);	
 

  insert into fieldspecificationsrecordtypes 
	  select id, (select id as recordtypeid from category where categorycode='ENRL_RECORD_TYPE'),current_timestamp,(select id from aartuser where username='cetesysadmin'),
	  true,current_timestamp,(select id from aartuser where username='cetesysadmin'),'Assessment_Program_3' 
	  from fieldspecification where fieldname = 'assessmentProgram3' ;

/*Roster*/

update fieldspecificationsrecordtypes fs set mappedname = 'School Identifier' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id 
and fs.recordtypeid = ( select id from category where categorycode='SCRS_RECORD_TYPE' ) 
and fieldname = 'schoolIdentifier'  )
and recordtypeid =( select id from category where categorycode='SCRS_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Remove from roster' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id 
and fs.recordtypeid = ( select id from category where categorycode='SCRS_RECORD_TYPE' ) 
and fieldname = 'removeFromRoster'  )
and recordtypeid =( select id from category where categorycode='SCRS_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Roster Name' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id 
and fs.recordtypeid = ( select id from category where categorycode='SCRS_RECORD_TYPE' ) 
and fieldname = 'rosterName'  )
and recordtypeid =( select id from category where categorycode='SCRS_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Student Legal Last Name' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id 
and fs.recordtypeid = ( select id from category where categorycode='SCRS_RECORD_TYPE' ) 
and fieldname = 'legalLastName'  )
and recordtypeid =( select id from category where categorycode='SCRS_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Student Legal First Name' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id 
and fs.recordtypeid = ( select id from category where categorycode='SCRS_RECORD_TYPE' ) 
and fieldname = 'legalFirstName'  )
and recordtypeid =( select id from category where categorycode='SCRS_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Educator First Name' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id 
and fs.recordtypeid = ( select id from category where categorycode='SCRS_RECORD_TYPE' ) 
and fieldname = 'educatorFirstName'  )
and recordtypeid =( select id from category where categorycode='SCRS_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Educator Last Name' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id 
and fs.recordtypeid = ( select id from category where categorycode='SCRS_RECORD_TYPE' ) 
and fieldname = 'educatorLastName'  )
and recordtypeid =( select id from category where categorycode='SCRS_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'School Year' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id 
and fs.recordtypeid = ( select id from category where categorycode='SCRS_RECORD_TYPE' ) 
and fieldname = 'currentSchoolYear'  )
and recordtypeid =( select id from category where categorycode='SCRS_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Course' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id 
and fs.recordtypeid = ( select id from category where categorycode='SCRS_RECORD_TYPE' ) 
and fieldname = 'stateCourseCode'  )
and recordtypeid =( select id from category where categorycode='SCRS_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'State Student Identifier' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id 
and fs.recordtypeid = ( select id from category where categorycode='SCRS_RECORD_TYPE' ) 
and fieldname = 'stateStudentIdentifier'  )
and recordtypeid =( select id from category where categorycode='SCRS_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Educator Identifier' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id 
and fs.recordtypeid = ( select id from category where categorycode='SCRS_RECORD_TYPE' ) 
and fieldname = 'educatorIdentifier'  )
and recordtypeid =( select id from category where categorycode='SCRS_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Subject' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id 
and fs.recordtypeid = ( select id from category where categorycode='SCRS_RECORD_TYPE' ) 
and fieldname = 'stateSubjectAreaCode'  )
and recordtypeid =( select id from category where categorycode='SCRS_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Local Student Identifier' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id 
and fs.recordtypeid = ( select id from category where categorycode='SCRS_RECORD_TYPE' ) 
and fieldname = 'localStudentIdentifier'  )
and recordtypeid =( select id from category where categorycode='SCRS_RECORD_TYPE' );

/** Upload TEC */
update fieldspecificationsrecordtypes fs set mappedname = 'Attendance_School_Program_Identifier' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' ) and fieldname = 'attendanceSchoolProgramIdentifier'  )
and recordtypeid =( select id from category where categorycode='TEC_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'State_Student_Identifier' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' ) and fieldname = 'stateStudentIdentifier'  )
and recordtypeid =( select id from category where categorycode='TEC_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Test_Type' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' ) and fieldname = 'testType'  )
and recordtypeid =( select id from category where categorycode='TEC_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Record_Type' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' ) and fieldname = 'recordType'  )
and recordtypeid =( select id from category where categorycode='TEC_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Exit_Reason' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' ) and fieldname = 'exitReason'  )
and recordtypeid =( select id from category where categorycode='TEC_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Subject' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' ) and fieldname = 'subject'  )
and recordtypeid =( select id from category where categorycode='TEC_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'School_Year' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' ) and fieldname = 'schoolYear'  )
and recordtypeid =( select id from category where categorycode='TEC_RECORD_TYPE' );

update fieldspecificationsrecordtypes fs set mappedname = 'Exit_Date' where 
fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' ) and fieldname = 'exitDate'  )
and recordtypeid =( select id from category where categorycode='TEC_RECORD_TYPE' );
