-- dml/585.sql


insert into authorities(authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser) 
values('PERM_EXIT_ALT_STUDENT' ,'Exit Student from Alt Assessment' ,'Student Management-EXIT' , CURRENT_TIMESTAMP ,(select id from aartuser where email='cete@ku.edu') ,true , CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));

update fieldspecification set allowablevalues='{'''',1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,30,98,99}' where fieldname = 'exitReason';

--originally from dml/580.sql

update batchjobschedule set jobrefname = 'batchKSDEDataProcessJobRunScheduler', initmethod= 'run' where jobname = 'TASC Process Scheduler';

update batchjobschedule set jobname = 'KSDE Data Process Scheduler', initmethod= 'run' where jobrefname = 'batchKSDEDataProcessJobRunScheduler';

update batchjobschedule set jobrefname = 'batchKSDEGetDataJobRunScheduler', initmethod= 'run' where jobname = 'TASC Get Data Scheduler';

update batchjobschedule set jobname = 'KSDE Get Data Scheduler', initmethod= 'run' where jobrefname = 'batchKSDEGetDataJobRunScheduler';


-- Update mapped name into both fieldspecification and fieldspecificationsrecordtypes tables

UPDATE fieldspecificationsrecordtypes fsrt
SET mappedname = COALESCE(fs.mappedname)
FROM fieldspecification fs
WHERE fsrt.fieldspecificationid = fs.id
AND fsrt.recordtypeid = (select id from category where categorycode = 'KID_RECORD_TYPE');


UPDATE fieldspecification fs
SET mappedname = COALESCE(fsrt.mappedname)
FROM fieldspecificationsrecordtypes fsrt
WHERE fsrt.fieldspecificationid = fs.id
AND fsrt.recordtypeid = (select id from category where categorycode = 'TASC_RECORD_TYPE');


UPDATE fieldspecificationsrecordtypes SET recordtypeid=(select id from category where categorycode = 'KID_RECORD_TYPE') WHERE recordtypeid = (SELECT id FROM category WHERE categorycode = 'TASC_RECORD_TYPE');


UPDATE fieldspecification SET allowablevalues = '{TASC,TEST,EXIT}' WHERE id = (SELECT id FROM fieldspecification WHERE (mappedname, fieldname) = ('Record_Type','recordType') AND activeflag is true);

UPDATE fieldspecification SET allowablevalues = '{00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18}' WHERE id = (SELECT id FROM fieldspecification WHERE (mappedname, fieldname) = ('Current_Grade_Level','currentGradeLevel') AND activeflag is true);

UPDATE fieldspecification SET allowablevalues = '{0,2,C}' WHERE id = (SELECT id FROM fieldspecification WHERE (mappedname, fieldname) = ('State_ELA_Assess','stateELAAssessment') AND activeflag is true);

UPDATE fieldspecification SET allowablevalues = '{0,2,C}' WHERE id = (SELECT id FROM fieldspecification WHERE (mappedname, fieldname) = ('State_Math_Assess','stateMathAssess') AND activeflag is true);

UPDATE fieldspecification SET allowablevalues = '{0,2,C}' WHERE id = (SELECT id FROM fieldspecification WHERE (mappedname, fieldname) = ('State_Science_Assess','stateSciAssessment') AND activeflag is true);

UPDATE fieldspecification SET allowablevalues = '{0}' WHERE id = (SELECT id FROM fieldspecification WHERE (mappedname, fieldname) = ('State_Hist_Gov_Assess','stateHistGovAssessment') AND activeflag is true);

UPDATE fieldspecification SET allowablevalues = '{01,02,03,04,51,52,53,54,80,81,82,83,84}' WHERE fieldname = 'tascStateSubjectAreaCode' and activeflag is true;
--TEST/EXIT field changes
--State_ELPA_Proctor_ID
insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('elpaProctorId','ELPA_Proctor_ID',null,20,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),false);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname)
	(Select id as fieldspecificationid, (Select id from category where categorycode='KID_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate, mappedname from fieldspecification where (fieldname,mappedname) = ('elpaProctorId' , 'ELPA_Proctor_ID'));

--State_ELPA_Proctor_First_Name
insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('elpaProctorFirstName','ELPA_Proctor_First_Name',null,120,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),false);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname)
	(Select id as fieldspecificationid, (Select id from category where categorycode='KID_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate, mappedname from fieldspecification where (fieldname,mappedname) = ('elpaProctorFirstName' , 'ELPA_Proctor_First_Name'));

--State_ELPA_Proctor_Last_Name
insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('elpaProctorLastName','ELPA_Proctor_Last_Name',null,120,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),false);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname)
	(Select id as fieldspecificationid, (Select id from category where categorycode='KID_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate, mappedname from fieldspecification where (fieldname,mappedname) = ('elpaProctorLastName' , 'ELPA_Proctor_Last_Name'));	

	
--AV_Communications_Assess
insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('avCommunicationsAssessment','AV_Communications_Assess','{0,1,3,C}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),false);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname)
	(Select id as fieldspecificationid, (Select id from category where categorycode='KID_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate, mappedname from fieldspecification where (fieldname,mappedname) = ('avCommunicationsAssessment' , 'AV_Communications_Assess'));	
	

--Grouping_AV_Communications
insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('groupingAvCommunications','Grouping_AV_Communications',null,60,null,false,false,false,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),false);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname)
	(Select id as fieldspecificationid, (Select id from category where categorycode='KID_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate, mappedname from fieldspecification where (fieldname,mappedname) = ('groupingAvCommunications' , 'Grouping_AV_Communications'));	
	
--Financial_Literacy_Assess
insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('financialLiteracyAssessment','Financial_Literacy_Assess','{0,2,C}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),false);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname)
	(Select id as fieldspecificationid, (Select id from category where categorycode='KID_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate, mappedname from fieldspecification where (fieldname,mappedname) = ('financialLiteracyAssessment' , 'Financial_Literacy_Assess'));	
	
--Grouping_Financial_Literacy_1
insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('groupingInd1FinancialLiteracy','Grouping_Financial_Literacy_1',null,60,null,false,false,false,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),false);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname)
	(Select id as fieldspecificationid, (Select id from category where categorycode='KID_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate, mappedname from fieldspecification where (fieldname,mappedname) = ('groupingInd1FinancialLiteracy' , 'Grouping_Financial_Literacy_1'));	
	
--Grouping_Financial_Literacy_2
insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('groupingInd2FinancialLiteracy','Grouping_Financial_Literacy_2',null,60,null,false,false,false,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),false);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname)
	(Select id as fieldspecificationid, (Select id from category where categorycode='KID_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate, mappedname from fieldspecification where (fieldname,mappedname) = ('groupingInd2FinancialLiteracy' , 'Grouping_Financial_Literacy_2'));	

	-- Removed
	
DELETE FROM fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification  WHERE (fieldname,mappedname) = ('tascRecordType','Record_Type'));
DELETE FROM fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification  WHERE (fieldname,mappedname) = ('tascAypSchoolIdentifier','AYP_QPA_Bldg_No'));
DELETE FROM fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification  WHERE (fieldname,mappedname) = ('studentCurrentGradeLevel','Current_Grade_Level'));
DELETE FROM fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification  WHERE (fieldname,mappedname) = ('tascStateStudentIdentifier','State_Student_Identifier'));
DELETE FROM fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification  WHERE (fieldname,mappedname) = ('tascCurrentSchoolYear','Current_School_Year'));
DELETE FROM fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) = ('cpassProctorId' , 'CTE_Proctor_ID'));
DELETE FROM  fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) = ('cpassProctorName' , 'CTE_Proctor_Name'));
DELETE FROM  fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) = ('dlmMathProctorId' , 'Math_DLM_Proctor_ID'));
DELETE FROM  fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) = ('dlmMathProctorName' , 'Math_DLM_Proctor_Name'));
DELETE FROM  fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) = ('dlmELAProctorId' , 'ELA_Proctor_ID'));
DELETE FROM  fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) = ('dlmELAProctorName' , 'ELA_Proctor_Name'));
DELETE FROM  fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) = ('dlmSciProctorId' , 'Science_DLM_Proctor_ID'));
DELETE FROM  fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) = ('dlmSciProctorName' , 'Science_DLM_Proctor_Name'));
DELETE FROM  fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) = ('groupingInd1CTE' , 'CTE_Grouping_1'));
DELETE FROM  fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) = ('groupingInd2CTE' , 'CTE_Grouping_2'));
DELETE FROM  fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) = ('attendanceSchoolIdentifier' , 'Attendance_Bldg_No'));
DELETE FROM  fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) = ('studentLegalFirstName' , 'Student_Legal_First_Name'));
DELETE FROM  fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) = ('studentLegalLastName' , 'Student_Legal_Last_Name'));
DELETE FROM  fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) = ('studentLegalMiddleName' , 'Student_Legal_Middle_Name'));
DELETE FROM  fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) = ('tascStudentGender' , 'Student_Gender'));
DELETE FROM  fieldspecificationsrecordtypes WHERE fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) = ('tascStudentGenerationCode' , 'Student_Generation_Code'));

	
	
DELETE FROM fieldspecification  WHERE (fieldname,mappedname) = ('tascRecordType','Record_Type');
DELETE FROM fieldspecification  WHERE (fieldname,mappedname) = ('tascAypSchoolIdentifier','AYP_QPA_Bldg_No');
DELETE FROM fieldspecification  WHERE (fieldname,mappedname) = ('studentCurrentGradeLevel','Current_Grade_Level');
DELETE FROM fieldspecification  WHERE (fieldname,mappedname) = ('tascStateStudentIdentifier','State_Student_Identifier');
DELETE FROM fieldspecification  WHERE (fieldname,mappedname) = ('tascCurrentSchoolYear','Current_School_Year');

DELETE FROM fieldspecification  WHERE (fieldname,mappedname) = ('cpassProctorId' , 'CTE_Proctor_ID');

DELETE FROM  fieldspecification WHERE (fieldname,mappedname) = ('cpassProctorName' , 'CTE_Proctor_Name');

DELETE FROM  fieldspecification WHERE (fieldname,mappedname) = ('dlmMathProctorId' , 'Math_DLM_Proctor_ID');

DELETE FROM  fieldspecification WHERE (fieldname,mappedname) = ('dlmMathProctorName' , 'Math_DLM_Proctor_Name');

DELETE FROM  fieldspecification WHERE (fieldname,mappedname) = ('dlmELAProctorId' , 'ELA_Proctor_ID');

DELETE FROM  fieldspecification WHERE (fieldname,mappedname) = ('dlmELAProctorName' , 'ELA_Proctor_Name');

DELETE FROM  fieldspecification WHERE (fieldname,mappedname) = ('dlmSciProctorId' , 'Science_DLM_Proctor_ID');

DELETE FROM  fieldspecification WHERE (fieldname,mappedname) = ('dlmSciProctorName' , 'Science_DLM_Proctor_Name');

DELETE FROM  fieldspecification WHERE (fieldname,mappedname) = ('groupingInd1CTE' , 'CTE_Grouping_1');

DELETE FROM  fieldspecification WHERE (fieldname,mappedname) = ('groupingInd2CTE' , 'CTE_Grouping_2');

DELETE FROM  fieldspecification WHERE (fieldname,mappedname) = ('attendanceSchoolIdentifier' , 'Attendance_Bldg_No');

DELETE FROM  fieldspecification WHERE (fieldname,mappedname) = ('studentLegalFirstName' , 'Student_Legal_First_Name');

DELETE FROM  fieldspecification WHERE (fieldname,mappedname) = ('studentLegalLastName' , 'Student_Legal_Last_Name');

DELETE FROM  fieldspecification WHERE (fieldname,mappedname) = ('studentLegalMiddleName' , 'Student_Legal_Middle_Name');

DELETE FROM  fieldspecification WHERE (fieldname,mappedname) = ('tascStudentGender' , 'Student_Gender');

DELETE FROM  fieldspecification WHERE (fieldname,mappedname) = ('tascStudentGenerationCode' , 'Student_Generation_Code');



-- Deleting permissions to view TEST, TASC, and STCO data extracts. For 2017 school year TEST and TASC records are coming in the same XML, so going to show them in one extract only.
-- Created new permission to view the new extract for TEST and TASC.

-- DML FOR US19070: KIDS AUDIT TRAILS
-- Creating one extract for TASC and TEST

-- Deleting the old permissions for extract
DELETE FROM groupauthorities WHERE authorityid in (SELECT id FROM authorities WHERE authority in ('DATA_EXTRACTS_KSDE_TASC', 'DATA_EXTRACTS_KSDE_STCO', 'DATA_EXTRACTS_KSDE_TEST')
	AND objecttype='Reports-Data Extracts'  AND activeflag = TRUE);
	
DELETE FROM authorities WHERE authority = 'DATA_EXTRACTS_KSDE_TASC' AND objecttype='Reports-Data Extracts'  AND activeflag = TRUE;
DELETE FROM authorities WHERE authority = 'DATA_EXTRACTS_KSDE_STCO' AND objecttype='Reports-Data Extracts'  AND activeflag = TRUE;
DELETE FROM authorities WHERE authority = 'DATA_EXTRACTS_KSDE_TEST' AND objecttype='Reports-Data Extracts'  AND activeflag = TRUE;


-- Inserting one permission for both TEST and TASC records
DO
$BODY$
BEGIN
	
	IF ((SELECT count(id) FROM authorities WHERE authority = 'DATA_EXTRACTS_KSDE_TEST_TASC' AND activeflag = TRUE) = 0) THEN
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_KSDE_TEST_TASC does not exist. Inserting...';
		INSERT INTO authorities(
				authority, displayname, objecttype, createddate, createduser,
				activeflag, modifieddate, modifieduser)
			VALUES ('DATA_EXTRACTS_KSDE_TEST_TASC', 'Create KSDE TEST and TASC Records Data Extract',
				'Reports-Data Extracts', now(), (select id from aartuser where username='cetesysadmin'),
				TRUE, now(), (Select id from aartuser where username='cetesysadmin'));
	ELSE
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_KSDE_TEST_TASC exists. Skipping...';
	END IF;
	
END;
$BODY$;


-- typical default
UPDATE profileitemattribute
SET nonselectedvalue = '';

UPDATE profileitemattribute
SET nonselectedvalue = 'false'
WHERE attributename IN (
	'activateByDefault',
	'assignedSupport',
	'directionsOnly',
	'largePrintBooklet',
	'paperAndPencil',
	'ReadAtStartPreference',
	'supportsAdaptiveEquip',
	'supportsAdminIpad',
	'supportsCalculator',
	'supportsHumanReadAloud',
	'supportsIndividualizedManipulatives',
	'supportsLanguageTranslation',
	'supportsPartnerAssistedScanning',
	'supportsSignInterpretation',
	'supportsStudentProvidedAccommodations',
	'supportsTestAdminEnteredResponses',
	'supportsTwoSwitch',
	'visualImpairment'
);

insert into category (categoryname,categorycode,categorydescription,categorytypeid,originationcode,createduser,modifieduser)
values ('Processing LCS Responses','PROCESS_LCS_RESPONSES','Processing LCS Responses',22,'TDE',12,12);

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
	activeflag, modifieddate, modifieduser)
 VALUES ( 'VIEW_TOOLS_MENU','View Tools Menu' ,'Administrative-View Tools', current_timestamp,
 (Select id from aartuser where username='cetesysadmin'),
 true,current_timestamp, (Select id from aartuser where username='cetesysadmin'));
 
insert into authorities(authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser) 
          values('DATA_EXTRACTS_SECURITY_AGREEMENT','Create Security Agreement Status Extract','Reports-Data Extracts' ,NOW(),
           (select id from aartuser where username='cetesysadmin'),TRUE, NOW(),
           (select id from aartuser where username='cetesysadmin'));
           
INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
    ('PERM_INTERIM_ACCESS','View Interim','Interim-Interim', 
    (Select id from aartuser where username='cetesysadmin'), 
    (Select id from aartuser where username='cetesysadmin'));