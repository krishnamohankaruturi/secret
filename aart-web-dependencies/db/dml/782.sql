-----------------------------------------FRANKLIN RELEASE DML SCRIPTS-----------------------------------------------------------------------

-----------------------------------------F557 DML SCRIPTS-----------------------------------------------------------------------------------

---------------------------------------INSERT INTO APPCONFIGURATION TABLE-------------------------------------------------------------------
---------------------------------------GENERAL REPORTS WITHOUT STUDENT ARCHIEVE------------------------------------------------------------
	
INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'ALL_ST_RPT_FOR_ST', 'GEN', 'All Student Reports for Student', 'Non DLM General Reports', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);
	
INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'GEN_SD', 'GEN', 'General School Detail', 'Remove School Detail from KAP Report access', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'GEN_DD', 'GEN', 'General District Detail', 'Remove District Detail from KAP Report access', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);			
			
---------------------------------------DLM GENERAL REPORTS WITHOUT STUDENT ARCHIEVE------------------------------------------------------	
			
INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'ALT_MONITORING_SUMMARY', 'GEN_DLM', 'Monitoring Summary', 'DLM General Reports', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);			
			
---------------------------------------INSTRUCTIONALLY EMBEDDED REPORTS--------------------------------------------------------------------	
			
INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'ALT_ST', 'IE', 'Student Progress', 'DLM Instructionally Embedded Reports', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);
	
INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'ALT_CR', 'IE', 'Class Roster', 'DLM Instructionally Embedded Reports', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);	
	
INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'ALT_BLUEPRINT_COVERAGE', 'IE', 'Blueprint Coverage', 'DLM Instructionally Embedded Reports', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);	

---------------------------------------YEAR END REPORTS-------------------------------------------------------------------------------------
INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag,createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'ALT_ST_IND', 'YE', 'Student (Individual)', 'Year End Reports', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'ALT_ST_ALL', 'YE', 'Students (Bundled)', 'Year End Reports', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'ALT_SS', 'YE', 'State Aggregate', 'Year End Reports', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);	

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'ALT_DS', 'YE', 'District Aggregate', 'Year End Reports', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);		

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'ALT_STD_SUMMARY', 'YE', 'Student Summary', 'Year End Reports', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);				

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'ALT_SCHOOL', 'YE', 'School Aggregate', 'Year End Reports', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'ALT_CLASS_ROOM', 'YE', 'Class Aggregate', 'Year End Reports', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);	

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'ALT_SCH_SUM_ALL', 'YE', 'School Aggregate (Bundled)', 'Year End Reports', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);	

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'ALT_ST_DCPS', 'YE', 'Student (DCPS)', 'Year End Reports', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);	

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'ALT_ST_SUM_ALL', 'YE', 'Student Summary (Bundled)', 'Year End Reports', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);	



----------------------------------------------STUDENT ARCHIEVE REPORTS---------------------------------------------------------------------
INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'ALL_ST_RPT_FOR_ST', 'SA', 'All Student Reports for Student', 'Student Archieve Reports', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);
			
			

----------------------------------------------DATA EXTRACTS REPORTS------------------------------------------------------------------------			
			
INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'DATA_EXTRACTS_DLM_Gen_Research', 'EX', 'DLM General Research File (GRF)', 'Data Extracts', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);
			
INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'DATA_EXTRACTS_DLM_SPEC_CIRCUM', 'EX', 'DLM Special Circumstance File', 'Data Extracts', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);
			
INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'DATA_EXTRACTS_DLM_INCIDENT', 'EX', 'DLM Incident File', 'Data Extracts', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);
			
INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'DATA_EXTRACTS_DLM_EXIT_STUDENT', 'EX', 'DLM Exited Students', 'Data Extracts', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);	


----------------------------------------------AUTHORITY TABLE UPDATE---------------------------------------------------------------------

UPDATE authorities SET level = 2, sortorder = 14975 WHERE authority = 'MANAGE_STATE_SPECIFIC_FILES';
UPDATE authorities SET sortorder = 14950 WHERE authority = 'VIEW_STATE_SPECIFIC_FILES';

-----------------------EXECUTE FUNCTION TO INSERT EXTRACTASSESSMENTPROGRAM TABLE -----------------------------------------------------------


--- THIS SCRIPT ONLY FOR "DLM" ASSESSMENT PROGRAM. IF NEED ANY OTHER ASSESSMENT PROGAM HAVE TO RUN ALL BELOW SCRIPT WITH RESPECTIVE ASS PGM 

select * from extractassessmentprogram_fn('DLM General Research File (GRF)', 41, 'DLM', 'DATA_EXTRACTS_DLM_Gen_Research');

select * from extractassessmentprogram_fn('DLM Special Circumstance File', 42, 'DLM', 'DATA_EXTRACTS_DLM_SPEC_CIRCUM');

select * from extractassessmentprogram_fn('DLM Incident File', 43, 'DLM', 'DATA_EXTRACTS_DLM_INCIDENT');

select * from extractassessmentprogram_fn('DLM Exited Students', 47, 'DLM', 'DATA_EXTRACTS_DLM_EXIT_STUDENT');

select * from extractassessmentprogram_fn('State Specific Files', 55, 'DLM', 'VIEW_STATE_SPECIFIC_FILES');

--------------------------------------------------------------------------------------------------------------------------------------------------


--------------------------------------------------------F787 DML Script---------------------------------------------------------------------
----------------------------INSERT NEW FIELD IN FIELDSPECIFICATION TABLE--------------------------------------------------------------------


INSERT INTO public.fieldspecification(fieldname, fieldlength, rejectifempty, rejectifinvalid,  mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, 
	iskeyvaluepairfield)	
 VALUES ('accountabilityDistrictIdentifier', 100, false, true,  'Accountability_District_Identifier_RESULTS_UPLOAD', true, current_timestamp, 
 (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'), false );
 
 
 

--------------------------------INSERT FIELDRECORDTYPES IN FIELDSPECIFICATIONRECORDTYPES TABLE-------------------------------------------

			
INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required)	
 VALUES ((select id from fieldspecification where mappedname = 'Accountability_District_Identifier_RESULTS_UPLOAD'), (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE'), 
			current_timestamp, (select id from aartuser where username ='cetesysadmin'), 
			true, current_timestamp, (select id from aartuser where username ='cetesysadmin'), 
			'Accountability_District_Identifier', TRUE); 

	
------------------------------------------------UPDATE FIELDRECORDTYPES IN FIELDSPECIFICATIONRECORDTYPES TABLE------------------------------		

update fieldspecificationsrecordtypes set mappedname = 'Accountability_School_Identifier' 
where fieldspecificationid = 
(select id from fieldspecification where mappedname = 'Ayp_School_Identifier_RESULTS_UPLOAD'
and fieldname = 'aypSchoolIdentifier') 
and recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE');

update fieldspecificationsrecordtypes set mappedname = 'State_Educator_Identifier' 
where fieldspecificationid = 
(select id from fieldspecification where mappedname = 'Educator_Identifier_RESULTS_UPLOAD'
and fieldname = 'educatorIdentifier') 
and recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE');

update fieldspecificationsrecordtypes set mappedname = 'Kite_Educator_Identifier' 
where fieldspecificationid = 
(select id from fieldspecification where mappedname = 'Kite_Educator_Identifier'
and fieldname = 'kiteEducatorIdentifier') 
and recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE');

update fieldspecificationsrecordtypes set mappedname = 'TBD_Growth' 
where fieldspecificationid = 
(select id from fieldspecification where mappedname = 'SGP'
and fieldname = 'sgp') 
and recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE');

update fieldspecificationsrecordtypes set mappedname = 'Kite_Student_Identifier' 
where fieldspecificationid = 
(select id from fieldspecification where mappedname = 'Student_Id'
and fieldname = 'studentId') 
and recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE');


update fieldspecificationsrecordtypes set mappedname = 'Accountability_School_Identifier' 
where fieldspecificationid = 
(select id from fieldspecification where mappedname = 'Ayp_School_Identifier_RESULTS_UPLOAD'
and fieldname = 'aypSchoolIdentifier') 
and recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE');

update fieldspecificationsrecordtypes set mappedname = 'State_Educator_Identifier' 
where fieldspecificationid = 
(select id from fieldspecification where mappedname = 'Educator_Identifier_RESULTS_UPLOAD'
and fieldname = 'educatorIdentifier') 
and recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE');

update fieldspecificationsrecordtypes set mappedname = 'Kite_Educator_Identifier' 
where fieldspecificationid = 
(select id from fieldspecification where mappedname = 'Kite_Educator_Identifier'
and fieldname = 'kiteEducatorIdentifier') 
and recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE');

update fieldspecificationsrecordtypes set mappedname = 'TBD_Growth' 
where fieldspecificationid = 
(select id from fieldspecification where mappedname = 'SGP'
and fieldname = 'sgp') 
and recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE');

update fieldspecificationsrecordtypes set mappedname = 'Kite_Student_Identifier' 
where fieldspecificationid = 
(select id from fieldspecification where mappedname = 'Student_Id'
and fieldname = 'studentId') 
and recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE');

update fieldspecificationsrecordtypes set mappedname = 'Accountability_School_Identifier' 
where fieldspecificationid = 
(select id from fieldspecification where mappedname = 'Ayp_School_Identifier_RESULTS_UPLOAD'
and fieldname = 'aypSchoolIdentifier') 
and recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE');

update fieldspecificationsrecordtypes set mappedname = 'State_Educator_Identifier' 
where fieldspecificationid = 
(select id from fieldspecification where mappedname = 'Educator_Identifier_RESULTS_UPLOAD'
and fieldname = 'educatorIdentifier') 
and recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE');

update fieldspecificationsrecordtypes set mappedname = 'Kite_Educator_Identifier' 
where fieldspecificationid = 
(select id from fieldspecification where mappedname = 'Kite_Educator_Identifier'
and fieldname = 'kiteEducatorIdentifier') 
and recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE');

update fieldspecificationsrecordtypes set mappedname = 'TBD_Growth' 
where fieldspecificationid = 
(select id from fieldspecification where mappedname = 'SGP'
and fieldname = 'sgp') 
and recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE');

update fieldspecificationsrecordtypes set mappedname = 'Kite_Student_Identifier' 
where fieldspecificationid = 
(select id from fieldspecification where mappedname = 'Student_Id'
and fieldname = 'studentId') 
and recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE');

--------------------------------INSERT ACCOUNTABILITY_DISTRICT_IDENTIFIER TO STATE NEW YORK AND IOA----------------------------------------
	
INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required)	
 VALUES ((select id from fieldspecification where mappedname = 'Accountability_District_Identifier_RESULTS_UPLOAD'), 
		(select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE'), 
			current_timestamp, (select id from aartuser where username ='cetesysadmin'), 
			true, current_timestamp, (select id from aartuser where username ='cetesysadmin'), 
			'Accountability_District_Identifier', TRUE); 
			
	
INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required)	
 VALUES ((select id from fieldspecification where mappedname = 'Accountability_District_Identifier_RESULTS_UPLOAD'), 
		(select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE'), 
			current_timestamp, (select id from aartuser where username ='cetesysadmin'), 
			true, current_timestamp, (select id from aartuser where username ='cetesysadmin'), 
			'Accountability_District_Identifier', TRUE); 
			

			
---------------------------------------------------------------INSERT NEW CATEGORY FOR NEW GRF STATE IN CATEGORY------------------------

INSERT INTO public.category(categoryname, categorycode, categorydescription, categorytypeid,  createddate, createduser, activeflag, modifieddate, modifieduser)	
 VALUES ( 'UploadDelawareGrfFile', 'UPLOAD_GRF_DL_FILE_TYPE', 'To insert Delaware general reaSearch file data into uploadgrffile table', 
			(select id from categorytype where typecode = 'CSV_RECORD_TYPE'), 
			current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, 
			current_timestamp, (select id from aartuser where username ='cetesysadmin'));
			
INSERT INTO public.category(categoryname, categorycode, categorydescription, categorytypeid, createddate, createduser, activeflag, modifieddate, modifieduser)	
 VALUES ( 'UploadArkansasGrfFile', 'UPLOAD_GRF_ARK_FILE_TYPE', 'To insert Arkansas general reaSearch file data into uploadgrffile table', 
			(select id from categorytype where typecode = 'CSV_RECORD_TYPE'), 
			current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, 
			current_timestamp, (select id from aartuser where username ='cetesysadmin'));	


---------------------------------------------------------------DELAWARE STATE---------------------------------------------------------------
-------------------------------------------------------INSERT FIELDRECORDTYPES IN FIELDSPECIFICATIONRECORDTYPES TABLE------------------



 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'course'), (select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Course',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'legalLastName'  and mappedname is null), (select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Student_Legal_Last_Name',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'legalFirstName'  and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Student_Legal_First_Name',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'stateStudentIdentifier'   and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'State_Student_Identifier',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'dateOfBirth'  and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Date_of_Birth',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'schoolEntryDate' and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'School_Entry_Date',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'districtEntryDate' and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'District_Entry_Date',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'stateEntryDate' and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'State_Entry_Date',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'comprehensiveRace'  and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Comprehensive_Race',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'primaryDisabilityCode' and mappedname = 'Primary_Disability_Code'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Primary_Disability_Code',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'firstLanguage' and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'First_Language',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'esolParticipationCode' and mappedname = 'ESOL_Participation_Code'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'ESOL_Participation_Code',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'subject'  and allowablevalues is null),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Subject',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where rejectifempty is true and fieldname = 'state'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'State',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee26' and mappedname = 'EE_26'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_26',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee25' and mappedname = 'EE_25'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_25',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee24' and mappedname = 'EE_24'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_24',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee23' and mappedname = 'EE_23'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_23',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee22' and mappedname = 'EE_22'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_22',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee21' and mappedname = 'EE_21'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_21',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee20' and mappedname = 'EE_20'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_20',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee19' and mappedname = 'EE_19'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_19',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee18' and mappedname = 'EE_18'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_18',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee17' and mappedname = 'EE_17'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_17',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee16' and mappedname = 'EE_16'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_16',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee15' and mappedname = 'EE_15'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_15',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee14' and mappedname = 'EE_14'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_14',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee13' and mappedname = 'EE_13'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_13',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee12' and mappedname = 'EE_12'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_12',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee11' and mappedname = 'EE_11'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_11',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee10' and mappedname = 'EE_10'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_10',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee9' and mappedname = 'EE_9'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_9',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee8' and mappedname = 'EE_8'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_8',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee7' and mappedname = 'EE_7'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_7',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee6' and mappedname = 'EE_6'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_6',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee5' and mappedname = 'EE_5'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_5',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee4' and mappedname = 'EE_4'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_4',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee3' and mappedname = 'EE_3'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_3',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee2' and mappedname = 'EE_2'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_2',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee1' and mappedname = 'EE_1'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_1',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'performanceLevel' and mappedname = 'Performance_Level'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Performance_Level',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'sgp' and mappedname = 'SGP'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'TBD_Growth',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'finalBand' and mappedname = 'Final_Band'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Final_Band',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'kiteEducatorIdentifier' and mappedname = 'Kite_Educator_Identifier'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Kite_Educator_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'educatorUserName' and mappedname = 'Educator_User_Name'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Educator_Username',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'schoolName' and mappedname = 'School_Name'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'School',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'districtName' and mappedname = 'District_Name'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'District',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'gender' and mappedname = 'Gender_With_Blank'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Gender',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'userName' and mappedname = 'User_Name'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Username',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'studentId' and mappedname = 'Student_Id'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Kite_Student_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'invalidationCode' and mappedname = 'Invalidation_Code'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Invalidation_Code',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'externalUniqueRowIdentifier' and mappedname = 'Unique_Row_Identifier'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Unique_Row_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'hispanicEthnicity' and mappedname = 'Hispanic_Ethnicity_upload_results'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Hispanic_Ethnicity',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'legalMiddleName' and mappedname = 'Student_Legal_Middle_Name_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Student_Legal_Middle_Name',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'generationCode' and mappedname = 'Generation_Code_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Generation_Code',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'educatorFirstName' and mappedname = 'Educator_First_Name_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Educator_First_Name',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'educatorLastName' and mappedname = 'Educator_Last_Name_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Educator_Last_Name',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'educatorIdentifier' and mappedname = 'Educator_Identifier_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'State_Educator_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'currentGradelevel' and mappedname = 'Current_Grade_Level_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Current_Grade_Level',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'schoolIdentifier' and mappedname = 'School_Code_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'School_Code',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'residenceDistrictIdentifier' and mappedname = 'District_Code_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'District_Code',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'attendanceSchoolProgramIdentifier' and mappedname = 'Attendance_School_Program_Identifier_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Attendance_School_Program_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'aypSchoolIdentifier' and mappedname = 'Ayp_School_Identifier_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Accountability_School_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'accountabilityDistrictIdentifier' and mappedname = 'Accountability_District_Identifier_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DL_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Accountability_District_Identifier',TRUE);
 
 
			
---------------------------------------------------ARKANSAS STATE----------------------------------------------------------------
------------------INSERT FIELDSPECIFICATON IN FIELDSPECIFICATION TABLE-------------------------------------------------------------------
 
INSERT INTO public.fieldspecification(fieldname, fieldlength, rejectifempty, rejectifinvalid,  mappedname, showerror, createddate, createduser,	activeflag, modifieddate, modifieduser, 
	iskeyvaluepairfield)	
 VALUES ('stateUse',  100, false, true, 'StateUse', true, current_timestamp, (select id from aartuser where username ='cetesysadmin'), 
			true, current_timestamp, (select id from aartuser where username ='cetesysadmin'), false );	
  
 -----------------------------------INSERT FIELDRECORDTYPES IN FIELDSPECIFICATIONRECORDTYPES TABLE---------------------------------

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'stateUse' and mappedname = 'StateUse'), (select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'StateUse',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'localStudentIdentifier' and mappedname = 'Local_Student_Identifier'), (select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Local_Student_Identifier',TRUE);
 
 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'legalLastName'  and mappedname is null), (select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'LastName',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'legalFirstName'  and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'FirstName',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'stateStudentIdentifier'   and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'State_Student_Identifier',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'dateOfBirth'  and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'DOB',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'schoolEntryDate' and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'School_Entry_Date',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'districtEntryDate' and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'District_Entry_Date',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'stateEntryDate' and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'State_Entry_Date',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'comprehensiveRace'  and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Comprehensive_Race',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'primaryDisabilityCode' and mappedname = 'Primary_Disability_Code'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Primary_Disability_Code',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'firstLanguage' and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'First_Language',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'esolParticipationCode' and mappedname = 'ESOL_Participation_Code'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'ESOL_Participation_Code',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'subject'  and allowablevalues is null),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Subject',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where rejectifempty is true and fieldname = 'state'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'State',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee26' and mappedname = 'EE_26'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_26',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee25' and mappedname = 'EE_25'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_25',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee24' and mappedname = 'EE_24'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_24',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee23' and mappedname = 'EE_23'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_23',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee22' and mappedname = 'EE_22'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_22',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee21' and mappedname = 'EE_21'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_21',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee20' and mappedname = 'EE_20'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_20',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee19' and mappedname = 'EE_19'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_19',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee18' and mappedname = 'EE_18'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_18',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee17' and mappedname = 'EE_17'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_17',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee16' and mappedname = 'EE_16'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_16',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee15' and mappedname = 'EE_15'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_15',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee14' and mappedname = 'EE_14'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_14',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee13' and mappedname = 'EE_13'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_13',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee12' and mappedname = 'EE_12'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_12',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee11' and mappedname = 'EE_11'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_11',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee10' and mappedname = 'EE_10'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_10',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee9' and mappedname = 'EE_9'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_9',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee8' and mappedname = 'EE_8'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_8',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee7' and mappedname = 'EE_7'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_7',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee6' and mappedname = 'EE_6'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_6',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee5' and mappedname = 'EE_5'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_5',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee4' and mappedname = 'EE_4'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_4',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee3' and mappedname = 'EE_3'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_3',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee2' and mappedname = 'EE_2'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_2',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee1' and mappedname = 'EE_1'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_1',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'performanceLevel' and mappedname = 'Performance_Level'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Performance_Level',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'sgp' and mappedname = 'SGP'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'TBD_Growth',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'finalBand' and mappedname = 'Final_Band'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Final_Band',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'kiteEducatorIdentifier' and mappedname = 'Kite_Educator_Identifier'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Kite_Educator_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'educatorUserName' and mappedname = 'Educator_User_Name'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Educator_Username',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'schoolName' and mappedname = 'School_Name'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'School',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'districtName' and mappedname = 'District_Name'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'District',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'gender' and mappedname = 'Gender_With_Blank'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Gender',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'userName' and mappedname = 'User_Name'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Username',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'studentId' and mappedname = 'Student_Id'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Kite_Student_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'invalidationCode' and mappedname = 'Invalidation_Code'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Invalidation_Code',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'externalUniqueRowIdentifier' and mappedname = 'Unique_Row_Identifier'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Unique_Row_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'hispanicEthnicity' and mappedname = 'Hispanic_Ethnicity_upload_results'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Hispanic_Ethnicity',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'legalMiddleName' and mappedname = 'Student_Legal_Middle_Name_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'MiddleName',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'generationCode' and mappedname = 'Generation_Code_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Generation_Code',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'educatorFirstName' and mappedname = 'Educator_First_Name_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Educator_First_Name',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'educatorLastName' and mappedname = 'Educator_Last_Name_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Educator_Last_Name',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'educatorIdentifier' and mappedname = 'Educator_Identifier_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'State_Educator_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'currentGradelevel' and mappedname = 'Current_Grade_Level_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Current_Grade_Level',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'schoolIdentifier' and mappedname = 'School_Code_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'School_Code',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'residenceDistrictIdentifier' and mappedname = 'District_Code_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'District_Code',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'attendanceSchoolProgramIdentifier' and mappedname = 'Attendance_School_Program_Identifier_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Attendance_School_Program_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'aypSchoolIdentifier' and mappedname = 'Ayp_School_Identifier_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Accountability_School_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'accountabilityDistrictIdentifier' and mappedname = 'Accountability_District_Identifier_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_ARK_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Accountability_District_Identifier',TRUE);
 
------DC State Changes for GRF upload-------

INSERT INTO public.category(categoryname, categorycode, categorydescription, categorytypeid,  createddate, createduser, activeflag, modifieddate, modifieduser)	
 VALUES ( 'UploadDCGrfFile', 'UPLOAD_GRF_DC_FILE_TYPE', 'To insert District of Columbia general research file data into uploadgrffile table', 
			(select id from categorytype where typecode = 'CSV_RECORD_TYPE'), 
			current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, 
			current_timestamp, (select id from aartuser where username ='cetesysadmin'));
			
 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'course'), (select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Course',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'legalLastName'  and mappedname is null), (select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Student_Legal_Last_Name',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'legalFirstName'  and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Student_Legal_First_Name',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'stateStudentIdentifier'   and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'State_Student_Identifier',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'dateOfBirth'  and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Date_of_Birth',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'schoolEntryDate' and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'School_Entry_Date',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'districtEntryDate' and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'District_Entry_Date',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'stateEntryDate' and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'State_Entry_Date',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'comprehensiveRace'  and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Comprehensive_Race',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'primaryDisabilityCode' and mappedname = 'Primary_Disability_Code'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Primary_Disability_Code',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'firstLanguage' and mappedname is null),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'First_Language',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'esolParticipationCode' and mappedname = 'ESOL_Participation_Code'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'ESOL_Participation_Code',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'subject'  and allowablevalues is null),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Subject',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where rejectifempty is true and fieldname = 'state'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'State',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee26' and mappedname = 'EE_26'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_26',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee25' and mappedname = 'EE_25'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_25',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee24' and mappedname = 'EE_24'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_24',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee23' and mappedname = 'EE_23'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_23',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee22' and mappedname = 'EE_22'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_22',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee21' and mappedname = 'EE_21'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_21',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee20' and mappedname = 'EE_20'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_20',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee19' and mappedname = 'EE_19'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_19',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee18' and mappedname = 'EE_18'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_18',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee17' and mappedname = 'EE_17'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_17',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee16' and mappedname = 'EE_16'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_16',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee15' and mappedname = 'EE_15'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_15',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee14' and mappedname = 'EE_14'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_14',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee13' and mappedname = 'EE_13'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_13',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee12' and mappedname = 'EE_12'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_12',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee11' and mappedname = 'EE_11'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_11',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee10' and mappedname = 'EE_10'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_10',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee9' and mappedname = 'EE_9'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_9',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee8' and mappedname = 'EE_8'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_8',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee7' and mappedname = 'EE_7'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_7',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee6' and mappedname = 'EE_6'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_6',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee5' and mappedname = 'EE_5'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_5',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee4' and mappedname = 'EE_4'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_4',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee3' and mappedname = 'EE_3'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_3',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee2' and mappedname = 'EE_2'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_2',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'ee1' and mappedname = 'EE_1'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'EE_1',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'performanceLevel' and mappedname = 'Performance_Level'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Performance_Level',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'sgp' and mappedname = 'SGP'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'TBD_Growth',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'finalBand' and mappedname = 'Final_Band'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Final_Band',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'kiteEducatorIdentifier' and mappedname = 'Kite_Educator_Identifier'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Kite_Educator_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'educatorUserName' and mappedname = 'Educator_User_Name'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Educator_Username',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'schoolName' and mappedname = 'School_Name'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'School',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'districtName' and mappedname = 'District_Name'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'District',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'gender' and mappedname = 'Gender_With_Blank'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Gender',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'userName' and mappedname = 'User_Name'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Username',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'studentId' and mappedname = 'Student_Id'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Kite_Student_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'invalidationCode' and mappedname = 'Invalidation_Code'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Invalidation_Code',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'externalUniqueRowIdentifier' and mappedname = 'Unique_Row_Identifier'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Unique_Row_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'hispanicEthnicity' and mappedname = 'Hispanic_Ethnicity_upload_results'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Hispanic_Ethnicity',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'legalMiddleName' and mappedname = 'Student_Legal_Middle_Name_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Student_Legal_Middle_Name',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'generationCode' and mappedname = 'Generation_Code_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Generation_Code',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'educatorFirstName' and mappedname = 'Educator_First_Name_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Educator_First_Name',TRUE);

 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'educatorLastName' and mappedname = 'Educator_Last_Name_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Educator_Last_Name',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'educatorIdentifier' and mappedname = 'Educator_Identifier_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'State_Educator_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'currentGradelevel' and mappedname = 'Current_Grade_Level_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Current_Grade_Level',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'schoolIdentifier' and mappedname = 'School_Code_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'School_Code',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'residenceDistrictIdentifier' and mappedname = 'District_Code_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'District_Code',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'attendanceSchoolProgramIdentifier' and mappedname = 'Attendance_School_Program_Identifier_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Attendance_School_Program_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'aypSchoolIdentifier' and mappedname = 'Ayp_School_Identifier_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Accountability_School_Identifier',TRUE);


 INSERT INTO public.fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname, required) 
 VALUES ((select id from fieldspecification where fieldname = 'accountabilityDistrictIdentifier' and mappedname = 'Accountability_District_Identifier_RESULTS_UPLOAD'),(select id from category where categorycode = 'UPLOAD_GRF_DC_FILE_TYPE'),current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'),  'Accountability_District_Identifier',TRUE);
 

 /*Commenting the below statements as DC state is already created in prod. so don't need a separate execution, it will get created as part of the funciton call.
  INSERT INTO extractassessmentprogram( extractname,
  extracttypeid, assessmentprogramid, createdate, modifieddate, 
  readytoview, activeflag, stateid, authorityid)
  VALUES ( 'DLM General Research File (GRF)', 41, 3, current_timestamp, current_timestamp, 
  false,true, (select id from organization where displayidentifier = 'DC' and organizationtypeid =2), (select id from authorities where authority = 'DATA_EXTRACTS_DLM_Gen_Research' and activeflag is true limit 1));
  
 INSERT INTO extractassessmentprogram( extractname,
  extracttypeid, assessmentprogramid, createdate, modifieddate, 
  readytoview, activeflag, stateid, authorityid)
  VALUES ( 'DLM Special Circumstance File', 42, 3, current_timestamp, current_timestamp, 
  false,true, (select id from organization where displayidentifier = 'DC' and organizationtypeid =2), (select id from authorities where authority = 'DATA_EXTRACTS_DLM_SPEC_CIRCUM' and activeflag is true limit 1));
  
 INSERT INTO extractassessmentprogram( extractname,
  extracttypeid, assessmentprogramid, createdate, modifieddate, 
  readytoview, activeflag, stateid, authorityid)
  VALUES ( 'DLM Incident File', 43, 3, current_timestamp, current_timestamp, 
  false,true, (select id from organization where displayidentifier = 'DC' and organizationtypeid =2), (select id from authorities where authority = 'DATA_EXTRACTS_DLM_INCIDENT' and activeflag is true limit 1));
  
 INSERT INTO extractassessmentprogram( extractname,
  extracttypeid, assessmentprogramid, createdate, modifieddate, 
  readytoview, activeflag, stateid, authorityid)
  VALUES ( 'DLM Exited Students', 47, 3, current_timestamp, current_timestamp, 
  false,true, (select id from organization where displayidentifier = 'DC' and organizationtypeid =2), (select id from authorities where authority = 'DATA_EXTRACTS_DLM_EXIT_STUDENT' and activeflag is true limit 1));
  
  INSERT INTO extractassessmentprogram( extractname,
  extracttypeid, assessmentprogramid, createdate, modifieddate, 
  readytoview, activeflag, stateid, authorityid)
  VALUES ( 'State Specific Files', 55, 3, current_timestamp, current_timestamp, 
  false,true, (select id from organization where displayidentifier = 'DC' and organizationtypeid =2), (select id from authorities where authority = 'VIEW_STATE_SPECIFIC_FILES' and activeflag is true limit 1));
----------------------------------------------------------------------------------------------------------------------------------------*/


------------------------------------------------------F788 DML SCRIPT--------------------------------------------------------------
INSERT INTO public.studentmetametricsmeasuresscore(
            assessmentprogramid, schoolyear, gradeid, gradecode, subjectid, 
            scalescore, researchmeasure, reportedmeasure, lowerrange, upperrange, 
            createddate, createduser, activeflag, modifieddate, modifieduser)
 select assessmentprogramid, 2019 schoolyear, gradeid, gradecode, subjectid, 
            scalescore, researchmeasure, reportedmeasure, lowerrange, upperrange, 
            now() createddate, createduser, activeflag,now() modifieddate, modifieduser from studentmetametricsmeasuresscore src where schoolyear =2018
            and not exists (select 1 from studentmetametricsmeasuresscore tgt 
                             where tgt.schoolyear =2019 and 
                               tgt.assessmentprogramid=src.assessmentprogramid and 
                               tgt.gradeid=src.gradeid and 
                               tgt.subjectid=src.subjectid and 
                               tgt.subjectid=src.subjectid);

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser,modifieddate)
VALUES ('kap_score_extract_metametric_starting_year','kap_score_extract_metametric_starting_year','kap_score_extract_metametric_starting_year','2018',12,now(),12,now());

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser,modifieddate)
VALUES ('skip_student_calculation_for_uncompleted_stages','skip_student_calculation_for_uncompleted_stages','skip_student_calculation_for_uncompleted_stages','true',12,now(),12,now());

-------------------------------------------------------------------------------------------------------------------------------------------


------------------------------------------------------F885 DML SCRIPT---------------------------------------------------------------
INSERT INTO public.category(
 categoryname, categorycode, categorydescription,
 categorytypeid, externalid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
 VALUES ( 'PERMISSION', 'PERMISSION_RECORD_TYPE', 'permission upload record type',
   (SELECT id FROM categorytype WHERE typecode = 'CSV_RECORD_TYPE'), null, null,
   now(), (select id from aartuser where username = 'cetesysadmin'), true, now(),
   (select id from aartuser where username = 'cetesysadmin'));

----------------------------------------------- FILEDSPECIFICATION SCRIPT-----------------------------------------------------------
insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
	('tab', null, null, null, null, true,
	 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'); 

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
	('grouping', null, null, null, null, false,
	 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'); 

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
	('label', null, null, null, null, false,
	 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'); 

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
	('permission', null, null, null, null, true,
	 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String');
	 
------------------------------------------------------	 FILEDSPECIFICATIONSRECORDTYPE SCRIPT------------------------------------
INSERT INTO fieldspecificationsrecordtypes
(   fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES (
        (select id from fieldspecification where fieldname ='assessmentProgram' and rejectifempty is true 
and rejectifinvalid is true and activeflag is true), 
        (SELECT id FROM category WHERE categorycode = 'PERMISSION_RECORD_TYPE' LIMIT 1),
		now(),(select id from aartuser where username = 'cetesysadmin'),true,
        now(),(select id from aartuser where username = 'cetesysadmin'),
        'Assessment Program'
    );


INSERT INTO fieldspecificationsrecordtypes
(   fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES (
        (select id from fieldspecification where fieldname ='state' and rejectifempty is true 
and rejectifinvalid is true and activeflag is true), 
        (SELECT id FROM category WHERE categorycode = 'PERMISSION_RECORD_TYPE' LIMIT 1),
		now(),(select id from aartuser where username = 'cetesysadmin'),true,
        now(),(select id from aartuser where username = 'cetesysadmin'),
        'State'
    );

INSERT INTO fieldspecificationsrecordtypes
(   fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES (
        (select id from fieldspecification where fieldname ='tab' and rejectifempty is true 
and rejectifinvalid is true and activeflag is true), 
        (SELECT id FROM category WHERE categorycode = 'PERMISSION_RECORD_TYPE' LIMIT 1),
		now(),(select id from aartuser where username = 'cetesysadmin'),true,
        now(),(select id from aartuser where username = 'cetesysadmin'),
        'Tab'
    );

INSERT INTO fieldspecificationsrecordtypes
(   fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES (
        (select id from fieldspecification where fieldname ='grouping' and rejectifempty is false 
and rejectifinvalid is true and activeflag is true), 
        (SELECT id FROM category WHERE categorycode = 'PERMISSION_RECORD_TYPE' LIMIT 1),
		now(),(select id from aartuser where username = 'cetesysadmin'),true,
        now(),(select id from aartuser where username = 'cetesysadmin'),
        'Grouping'
    );

INSERT INTO fieldspecificationsrecordtypes
(   fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES (
        (select id from fieldspecification where fieldname ='label' and rejectifempty is false 
and rejectifinvalid is true and activeflag is true), 
        (SELECT id FROM category WHERE categorycode = 'PERMISSION_RECORD_TYPE' LIMIT 1),
		now(),(select id from aartuser where username = 'cetesysadmin'),true,
        now(),(select id from aartuser where username = 'cetesysadmin'),
        'Label'
    );

INSERT INTO fieldspecificationsrecordtypes
(   fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES (
        (select id from fieldspecification where fieldname ='permission' and rejectifempty is true 
and rejectifinvalid is true and activeflag is true), 
        (SELECT id FROM category WHERE categorycode = 'PERMISSION_RECORD_TYPE' LIMIT 1),
		now(),(select id from aartuser where username = 'cetesysadmin'),true,
        now(),(select id from aartuser where username = 'cetesysadmin'),
        'Permission'
    );


update groupauthorities set authorityid = (
select min(id) from authorities where authority = 'MERGE_STUDENT_RECORDS') where authorityid = (
select max(id) from authorities where authority = 'MERGE_STUDENT_RECORDS');

update reportassessmentprogram set authorityid = (
select min(id) from authorities where authority = 'MERGE_STUDENT_RECORDS') where authorityid = (
select max(id) from authorities where authority = 'MERGE_STUDENT_RECORDS');

update restrictionsauthorities set authorityid = (
select min(id) from authorities where authority = 'MERGE_STUDENT_RECORDS') where authorityid = (
select max(id) from authorities where authority = 'MERGE_STUDENT_RECORDS');

update groupauthoritiesexclusion set authorityid = (
select min(id) from authorities where authority = 'MERGE_STUDENT_RECORDS') where authorityid = (
select max(id) from authorities where authority = 'MERGE_STUDENT_RECORDS');

delete from authorities where id =(select max(id) from authorities where authority = 'MERGE_STUDENT_RECORDS');




------------------------------------------------------------------------------------------------------------------------------------
--F912 Regression

INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'assessment_program_esol_code_4or7and8', 'assessment_program_esol_code_4or7and8', 'assessment_program_esol_code_4or7and8', 'KAP', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);
            
            
INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'st_esol_Optional_Transitional_year', 'st_esolparticipationcode', 'Optional Transitional year [7]', '7', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);
            
            
INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, activeflag, createduser, createddate, modifieduser, modifieddate)
	VALUES ( 'st_esol_Monitored_ESOL_Student', 'st_esolparticipationcode', 'Monitored ESOL Student [8]', '8', true,
			(select id from aartuser where username ='cetesysadmin'), current_timestamp, 
			(select id from aartuser where username ='cetesysadmin'), current_timestamp);

-------------------------------------------------------------------------------------------------------------------------------------------------------------------
--ACT Level
DO
$do$
BEGIN
IF EXISTS (select id from contentarea where abbreviatedname= 'ELA' and name='English Language Arts') THEN --the actlevel, description and levelscore information is not needed for pltw

--level 1
INSERT INTO public.actlevel(
	 levelid, createduser, createddate, activeflag, modifieduser, modifieddate)
	VALUES ( 1, (select id from aartuser where username ='cetesysadmin'), current_timestamp, true, (select id from aartuser where username ='cetesysadmin'), current_timestamp);
--level 2
INSERT INTO public.actlevel(
	 levelid, createduser, createddate, activeflag, modifieduser, modifieddate)
	VALUES ( 2, (select id from aartuser where username ='cetesysadmin'), current_timestamp, true, (select id from aartuser where username ='cetesysadmin'), current_timestamp);    
--level 3
INSERT INTO public.actlevel(
	 levelid, createduser, createddate, activeflag, modifieduser, modifieddate)
	VALUES ( 3, (select id from aartuser where username ='cetesysadmin'), current_timestamp, true, (select id from aartuser where username ='cetesysadmin'), current_timestamp);
--level 4
INSERT INTO public.actlevel(
	 levelid, createduser, createddate, activeflag, modifieduser, modifieddate)
	VALUES ( 4, (select id from aartuser where username ='cetesysadmin'), current_timestamp, true, (select id from aartuser where username ='cetesysadmin'), current_timestamp);

--ACT Description
--KAP subject ELA grade 10 school 2019
INSERT INTO public.actdescription(
	subjectid, gradeid, assessmentprogramid, schoolyear, description, descriptionorder, createduser, createddate, activeflag, modifieddate, modifieduser)
	VALUES ((select id from contentarea where abbreviatedname= 'ELA'), 
            (select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA')),
            (select id from assessmentprogram where abbreviatedname = 'KAP'), 2019, 'Student''s actual KAP grade 10 ELA score', 1,
            (select id from aartuser where username ='cetesysadmin'), current_timestamp, true, current_timestamp, 
            (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescription(
	subjectid, gradeid, assessmentprogramid, schoolyear, description, descriptionorder, createduser, createddate, activeflag, modifieddate, modifieduser)
	VALUES ((select id from contentarea where abbreviatedname= 'ELA'), 
            (select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA')),
            (select id from assessmentprogram where abbreviatedname = 'KAP'), 2019, 
            'Student''s projected ACT Reading score', 2, (select id from aartuser where username ='cetesysadmin'), current_timestamp, true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescription(
	subjectid, gradeid, assessmentprogramid, schoolyear, description, descriptionorder, createduser, createddate, activeflag, modifieddate, modifieduser)
	VALUES ((select id from contentarea where abbreviatedname= 'ELA'), 
            (select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA')),
            (select id from assessmentprogram where abbreviatedname = 'KAP'), 2019, 'Student''s projected ACT English score', 3, (select id from aartuser where username ='cetesysadmin'), current_timestamp, true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));

--ACT Decription level score
--KAP subject ELA grade 10 school 2019 description 1
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from actdescription where schoolyear=2019 
             and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
             and description = 'Student''s actual KAP grade 10 ELA score' and descriptionorder = 1),     
         	(select levelid from actlevel where levelid=1), 268, 220, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from actdescription where schoolyear=2019 
             and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
             and description = 'Student''s actual KAP grade 10 ELA score' and descriptionorder = 1),
          	(select levelid from actlevel where levelid=2), 299, 269, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from actdescription where schoolyear=2019 
             and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
             and description = 'Student''s actual KAP grade 10 ELA score' and descriptionorder = 1),
          	(select levelid from actlevel where levelid=3), 333, 300, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((select id from actdescription where schoolyear=2019 
             and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
             and description = 'Student''s actual KAP grade 10 ELA score' and descriptionorder = 1),
            (select levelid from actlevel where levelid=4), 380, 334, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));

--KAP subject ELA grade 10 school 2019 description 2
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT Reading score' and descriptionorder = 2),     
         	(select levelid from actlevel where levelid=1), 17, 1, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT Reading score' and descriptionorder = 2),
          	(select levelid from actlevel where levelid=2), 23, 17, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT Reading score' and descriptionorder = 2),
          	(select levelid from actlevel where levelid=3), 30, 23, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT Reading score' and descriptionorder = 2),
            (select levelid from actlevel where levelid=4), 36, 30, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));

--KAP subject ELA grade 10 school 2019 description 3
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT English score' and descriptionorder = 3),     
         	(select levelid from actlevel where levelid=1), 15, 1, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT English score' and descriptionorder = 3),
          	(select levelid from actlevel where levelid=2), 22, 15, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT English score' and descriptionorder = 3),
          	(select levelid from actlevel where levelid=3), 30, 22, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT English score' and descriptionorder = 3),
           (select levelid from actlevel where levelid=4), 36, 30, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));


--ACT Description
--KAP subject M grade 10 school 2019
INSERT INTO public.actdescription(
	subjectid, gradeid, assessmentprogramid, schoolyear, description, descriptionorder, createduser, createddate, activeflag, modifieddate, modifieduser)
	VALUES ((select id from contentarea where abbreviatedname= 'M'), 
            (select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M')),
            (select id from assessmentprogram where abbreviatedname = 'KAP'), 2019, 'Student''s actual KAP grade 10 math score', 1, (select id from aartuser where username ='cetesysadmin'), current_timestamp, true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescription(
	subjectid, gradeid, assessmentprogramid, schoolyear, description, descriptionorder, createduser, createddate, activeflag, modifieddate, modifieduser)
	VALUES ((select id from contentarea where abbreviatedname= 'M'), 
            (select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M')),
            (select id from assessmentprogram where abbreviatedname = 'KAP'), 2019, 
            'Student''s projected ACT math score', 2, (select id from aartuser where username ='cetesysadmin'), current_timestamp, true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));

--ACT Decription level score
--KAP subject M grade 10 school 2019 description 1
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s actual KAP grade 10 math score' and descriptionorder = 1),     
         	(select levelid from actlevel where levelid=1), 274, 220, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s actual KAP grade 10 math score' and descriptionorder = 1),
          	(select levelid from actlevel where levelid=2), 299, 275, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s actual KAP grade 10 math score' and descriptionorder = 1),
          	(select levelid from actlevel where levelid=3), 332, 300, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s actual KAP grade 10 math score' and descriptionorder = 1),
            (select levelid from actlevel where levelid=4), 380, 333, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));

--KAP subject M grade 10 school 2019 description 2
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s projected ACT math score' and descriptionorder = 2),     
         	(select levelid from actlevel where levelid=1), 17, 1, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s projected ACT math score' and descriptionorder = 2),
          	(select levelid from actlevel where levelid=2), 22, 17, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s projected ACT math score' and descriptionorder = 2),
          	(select levelid from actlevel where levelid=3), 28, 22, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s projected ACT math score' and descriptionorder = 2),
            (select levelid from actlevel where levelid=4), 36, 28, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));

--ACT Description
--KAP subject ELA grade 8 school 2019
INSERT INTO public.actdescription(
	subjectid, gradeid, assessmentprogramid, schoolyear, description, descriptionorder, createduser, createddate, activeflag, modifieddate, modifieduser)
	VALUES ((select id from contentarea where abbreviatedname= 'ELA'), 
            (select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA')),
            (select id from assessmentprogram where abbreviatedname = 'KAP'), 2019, 'Student''s actual KAP grade 8 ELA score', 1, (select id from aartuser where username ='cetesysadmin'), current_timestamp, true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescription(
	subjectid, gradeid, assessmentprogramid, schoolyear, description, descriptionorder, createduser, createddate, activeflag, modifieddate, modifieduser)
	VALUES ((select id from contentarea where abbreviatedname= 'ELA'), 
            (select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA')),
            (select id from assessmentprogram where abbreviatedname = 'KAP'), 2019, 
            'Student''s projected ACT Reading score', 2, (select id from aartuser where username ='cetesysadmin'), current_timestamp, true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescription(
	subjectid, gradeid, assessmentprogramid, schoolyear, description, descriptionorder, createduser, createddate, activeflag, modifieddate, modifieduser)
	VALUES ((select id from contentarea where abbreviatedname= 'ELA'), 
            (select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA')),
            (select id from assessmentprogram where abbreviatedname = 'KAP'), 2019, 'Student''s projected ACT English score', 3, (select id from aartuser where username ='cetesysadmin'), current_timestamp, true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));

--ACT Decription level score
--KAP subject ELA grade 8 school 2019 description 1
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s actual KAP grade 8 ELA score' and descriptionorder = 1),     
         	(select levelid from actlevel where levelid=1), 264, 220, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s actual KAP grade 8 ELA score' and descriptionorder = 1),
          	(select levelid from actlevel where levelid=2), 299, 265, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s actual KAP grade 8 ELA score' and descriptionorder = 1),
          	(select levelid from actlevel where levelid=3), 333, 300, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s actual KAP grade 8 ELA score' and descriptionorder = 1),
            (select levelid from actlevel where levelid=4), 380, 334, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));

--KAP subject ELA grade 8 school 2019 description 2
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT Reading score' and descriptionorder = 2),     
         	(select levelid from actlevel where levelid=1), 16, 1, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT Reading score' and descriptionorder = 2),
          	(select levelid from actlevel where levelid=2), 23, 16, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT Reading score' and descriptionorder = 2),
          	(select levelid from actlevel where levelid=3), 30, 23, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT Reading score' and descriptionorder = 2),
            (select levelid from actlevel where levelid=4), 36, 30, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));

--KAP subject ELA grade 8 school 2019 description 3
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT English score' and descriptionorder = 3),     
         	(select levelid from actlevel where levelid=1), 14, 1, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT English score' and descriptionorder = 3),
          	(select levelid from actlevel where levelid=2), 21, 14, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT English score' and descriptionorder = 3),
          	(select levelid from actlevel where levelid=3), 29, 22, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT English score' and descriptionorder = 3),
            (select levelid from actlevel where levelid=4), 36, 29, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));


--ACT Description
--KAP subject M grade 8 school 2019
INSERT INTO public.actdescription(
	subjectid, gradeid, assessmentprogramid, schoolyear, description, descriptionorder, createduser, createddate, activeflag, modifieddate, modifieduser)
	VALUES ((select id from contentarea where abbreviatedname= 'M'), 
            (select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'M')),
            (select id from assessmentprogram where abbreviatedname = 'KAP'), 2019, 'Student''s actual KAP grade 8 math score', 1, (select id from aartuser where username ='cetesysadmin'), current_timestamp, true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescription(
	subjectid, gradeid, assessmentprogramid, schoolyear, description, descriptionorder, createduser, createddate, activeflag, modifieddate, modifieduser)
	VALUES ((select id from contentarea where abbreviatedname= 'M'), 
            (select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'M')),
            (select id from assessmentprogram where abbreviatedname = 'KAP'), 2019, 
            'Student''s projected ACT math score', 2, (select id from aartuser where username ='cetesysadmin'), current_timestamp, true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));

--ACT Decription level score
--KAP subject M grade 8 school 2019 description 1
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s actual KAP grade 8 math score' and descriptionorder = 1),     
         	(select levelid from actlevel where levelid=1), 273, 220, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s actual KAP grade 8 math score' and descriptionorder = 1),
          	(select levelid from actlevel where levelid=2), 299, 274, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s actual KAP grade 8 math score' and descriptionorder = 1),
          	(select levelid from actlevel where levelid=3), 335, 300, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s actual KAP grade 8 math score' and descriptionorder = 1),
            (select levelid from actlevel where levelid=4), 380, 336, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));

--KAP subject M grade 8 school 2019 description 2
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s projected ACT math score' and descriptionorder = 2),     
         	(select levelid from actlevel where levelid=1), 18, 1, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s projected ACT math score' and descriptionorder = 2),
          	(select levelid from actlevel where levelid=2), 22, 18, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s projected ACT math score' and descriptionorder = 2),
          	(select levelid from actlevel where levelid=3), 29, 23, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));
INSERT INTO public.actdescriptionlevelscore(
	 actdescriptionid, actlevelid, maxvalue, minvalue, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ( (select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='8' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s projected ACT math score' and descriptionorder = 2),
            (select levelid from actlevel where levelid=4), 36, 29, current_timestamp, (select id from aartuser where username ='cetesysadmin'), true, current_timestamp, (select id from aartuser where username ='cetesysadmin'));

----DE19211 update ACT chart values
update actdescriptionlevelscore set minvalue=220 , maxvalue=268, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
             and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
             and description = 'Student''s actual KAP grade 10 ELA score' and descriptionorder = 1) and actlevelid = (select levelid from actlevel where levelid=1);

update actdescriptionlevelscore set minvalue=269 , maxvalue=299, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
             and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
             and description = 'Student''s actual KAP grade 10 ELA score' and descriptionorder = 1) and actlevelid = (select levelid from actlevel where levelid=2); 

update actdescriptionlevelscore set minvalue= 1, maxvalue=17, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT Reading score' and descriptionorder = 2) and actlevelid = (select levelid from actlevel where levelid=1); 

update actdescriptionlevelscore set minvalue=18, maxvalue=23, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT Reading score' and descriptionorder = 2) and actlevelid = (select levelid from actlevel where levelid=2);

update actdescriptionlevelscore set minvalue=23, maxvalue=29, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT Reading score' and descriptionorder = 2) and actlevelid = (select levelid from actlevel where levelid=3);
              
update actdescriptionlevelscore set minvalue=29, maxvalue=36, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT Reading score' and descriptionorder = 2) and actlevelid = (select levelid from actlevel where levelid=4);

update actdescriptionlevelscore set minvalue=1, maxvalue=16, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT English score' and descriptionorder = 3) and actlevelid = (select levelid from actlevel where levelid=1);
 
update actdescriptionlevelscore set minvalue=16, maxvalue=22, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT English score' and descriptionorder = 3) and actlevelid = (select levelid from actlevel where levelid=2);
 
update actdescriptionlevelscore set minvalue=22, maxvalue=28, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT English score' and descriptionorder = 3) and actlevelid = (select levelid from actlevel where levelid=3); 

update actdescriptionlevelscore set minvalue=28, maxvalue=36, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'ELA')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'ELA'))
              and description = 'Student''s projected ACT English score' and descriptionorder = 3) and actlevelid = (select levelid from actlevel where levelid=4); 

update actdescriptionlevelscore set minvalue=220, maxvalue=274, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s actual KAP grade 10 math score' and descriptionorder = 1) and actlevelid = (select levelid from actlevel where levelid=1); 

update actdescriptionlevelscore set minvalue=275, maxvalue=299, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s actual KAP grade 10 math score' and descriptionorder = 1) and actlevelid = (select levelid from actlevel where levelid=2); 

update actdescriptionlevelscore set minvalue=300, maxvalue=332, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s actual KAP grade 10 math score' and descriptionorder = 1) and actlevelid = (select levelid from actlevel where levelid=3); 

update actdescriptionlevelscore set minvalue=333, maxvalue=380, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s actual KAP grade 10 math score' and descriptionorder = 1) and actlevelid = (select levelid from actlevel where levelid=4); 

update actdescriptionlevelscore set minvalue=1, maxvalue=19, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s projected ACT math score' and descriptionorder = 2) and actlevelid = (select levelid from actlevel where levelid=1); 

update actdescriptionlevelscore set minvalue=19, maxvalue=22, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s projected ACT math score' and descriptionorder = 2) and actlevelid = (select levelid from actlevel where levelid=2); 

update actdescriptionlevelscore set minvalue=23, maxvalue=27, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s projected ACT math score' and descriptionorder = 2) and actlevelid = (select levelid from actlevel where levelid=3);

update actdescriptionlevelscore set minvalue=28, maxvalue=35, modifieddate=now() where actdescriptionid=(select id from actdescription where schoolyear=2019 
              and subjectid=(select id from contentarea where abbreviatedname= 'M')
              and gradeid=(select id from gradecourse where abbreviatedname='10' and contentareaid =(select id from contentarea where abbreviatedname= 'M'))
              and description = 'Student''s projected ACT math score' and descriptionorder = 2) and actlevelid = (select levelid from actlevel where levelid=4);
-----
            
END IF;
END
$do$;

---------GRF Error Message ---------
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_UNIQUEROWIDENTIFIER_NULL','grf_upload','uniquerowidentifier','Unique_Row_Identifier ~ The Unique_Row_Identifier field is blank.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_UNIQUEROWIDENTIFIER_INVALID','grf_upload','uniquerowidentifier','Unique_Row_Identifier ~ The entry in the Unique_Row_Identifier field is not valid.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_UNIQUEROWIDENTIFIER_LENGTH','grf_upload','uniquerowidentifier','Unique_Row_Identifier ~ The entry in the Unique_Row_Identifier field must be less than or equal to 8 characters.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_KITE_STUDENT_IDENTIFIER_INVALID','grf_upload','kiteStudentIdentifier','Kite_Student_Identifier ~ The entry in the Kite_Student_Identifier field is not valid.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_KITE_STUDENT_IDENTIFIER_NULL','grf_upload','kiteStudentIdentifier','Kite_Student_Identifier ~ The Kite_Student_Identifier field is blank.##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_KITE_STUDENT_IDENTIFIER_LENGTH','grf_upload','kiteStudentIdentifier','Kite_Student_Identifier ~ The entry in the Kite_Student_Identifier field must be less than or equal to 10 characters.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_STATE_STUDENT_IDENTIFIER_NULL','grf_upload','stateStudentIdentifier','State_Student_Identifier ~ The State_Student_Identifier field is blank.##',12,now(),12,now());	
	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_STATE_STUDENT_IDENTIFIER_LENGTH','grf_upload','stateStudentIdentifier','State_Student_Identifier  ~ The entry in the State_Student_Identifier field must be less than or equal to 10 characters.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_ACCOUNTABILITY_SCHOOL_IDENTIFIER_LENGTH','grf_upload','accountabilitySchoolIdentifier','Accountabilty_School_Identifier ~ The entry in the Accountability_School_Identifier field must be less than or equal to 100 characters.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_ACCOUNTABILITY_DISTRICT_IDENTIFIER_LENGTH','grf_upload','accountabilitydistrictidentifier','Accountabilty_District_Identifier ~ The entry in the Accountability_District_Identifier field must be less than or equal to 100 characters.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_CURRENT_GRADE_LEVEL_NULL','grf_upload','currentGradelevel','Current_Grade_Level ~ The Current_Grade_Level field is blank.##',12,now(),12,now());
	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_CURRENT_GRADE_LEVEL_INVALID','grf_upload','currentGradelevel','Current_Grade_Level ~ The entry in the Current_Grade_Level field is not valid.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_STUDENT_FIRST_NAME_LENGTH','grf_upload','studentlegalFirstName','Student_Legal_First_Name ~ The entry in the Student_Legal_First_Name field must be less than or equal to 60 characters.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_STUDENT_FIRST_NAME_NULL','grf_upload','studentlegalFirstName','Student_Legal_First_Name ~ The Student_Legal_First_Name field is blank.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_STUDENT_MIDDLE_NAME_LENGTH','grf_upload','studentlegalMiddleName','Student_Legal_Middle_Name ~ The entry in the Student_Legal_Middle_Name field must be less than or equal to 80 characters.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_STUDENT_LAST_NAME_LENGTH','grf_upload','studentlegalLastName','Student_Legal_Last_Name ~ The entry in the Student_Legal_Last_Name field must be equal to or less than 60 characters.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_STUDENT_LAST_NAME_NULL','grf_upload','studentlegalLastName','Student_Legal_Last_Name ~ The Student_Legal_Last_Name field is blank.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_GENERATIONCODE_LENGTH','grf_upload','generationCode','Generation_Code ~ The entry in the Generation_Code field must be less than or equal to 10 characters.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_USERNAME_LENGTH','grf_upload','userName','Username ~ The entry in the Username field must be less than or equal to 100 characters.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_FIRSTLANGUAGE_INVALID','grf_upload','firstLanguage','First_Language ~ The entry in the First_Language field is not valid.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_DATE_OF_BIRTH_NULL','grf_upload','dateOfBirth','Date_of_Birth ~ The Date_of_Birth field is blank.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_DATE_OF_BIRTH_INVALID','grf_upload','dateOfBirth','Date_of_Birth ~ The year entered in the Date_of_Birth field is not valid. The date format should be MM/DD/YYYY.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_DATE_OF_BIRTH_FORMAT','grf_upload','dateOfBirth','Date_of_Birth ~ The format entered in the Date_of_Birth field is not valid. The date format should be MM/DD/YYYY.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_GENDER_INVALID','grf_upload','gender','Gender ~ The entry in the Gender field is not valid.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_GENDER_NULL','grf_upload','gender','Gender ~ The Gender field is blank.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_COMPREHENSIVERACE_INVALID','grf_upload','comprehensiveRace','Comprehensive_Race ~ The entry in the Comprehensive_Race field is not valid.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_COMPREHENSIVERACE_NULL','grf_upload','comprehensiveRace','Comprehensive_Race ~ The Comprehensive_Race field is blank.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_HISPANIC_ETHNICITY_INVALID','grf_upload','hispanicEthnicity','Hispanic_Ethnicity ~ The entry in the Hispanic_Ethnicity field is not valid.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_HISPANIC_ETHNICITY_NULL','grf_upload','hispanicEthnicity','Hispanic_Ethnicity ~ The Hispanic_Ethnicity field is blank.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_PRIMARY_DISABILITY_CODE_INVALID','grf_upload','primaryDisabilityCode','Primary_Disability_Code ~ The entry in the Primary_Disability field is not valid.##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_PRIMARY_DISABILITY_CODE_NULL','grf_upload','primaryDisabilityCode','Primary_Disability_Code ~ The Primary_Disability field is blank.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_ESOLPARTICIPATIONCODE_INVALID','grf_upload','esolParticipationCode','ESOL_Participation_Code ~ The entry in the ESOL_Participation_Code field is not valid.## ',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_ESOLPARTICIPATIONCODE_NULL','grf_upload','esolParticipationCode','ESOL_Participation_Code ~ The ESOL_Participation_Code field is blank.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_SCHOOL_ENTRY_DATE_NULL','grf_upload','schoolEntryDate','School_Entry_Date ~ The School_Entry_Date field is blank.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_SCHOOL_ENTRY_DATE_FORMAT','grf_upload','schoolEntryDate','School_Entry_Date ~ The format for the School_Entry_Date field is not valid. The date format should be MM/DD/YYYY.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_SCHOOL_ENTRY_DATE_INVALID','grf_upload','schoolEntryDate','School_Entry_Date ~ The year entered in the School_Entry_Date field is not valid. The date format should be MM/DD/YYYY.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_DISTRICT_ENTRY_DATE_FORMAT','grf_upload','districtEntryDate','District_Entry_Date ~ The format for the District_Entry_Date field is not valid. The date format should be MM/DD/YYYY.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_DISTRICT_ENTRY_DATE_INVALID','grf_upload','districtEntryDate','District_Entry_Date ~ The year entered in the District_Entry_Date field is not valid. The date format should be MM/DD/YYYY.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_STATE_ENTRY_DATE_FORMAT','grf_upload','stateEntryDate','State_Entry_Date ~ The entry in State_Entry_Date field is not valid. The date format should be MM/DD/YYYY.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_STATE_ENTRY_DATE_INVALID','grf_upload','stateEntryDate','State_Entry_Date ~ The year entered in the State_Entry_Date field is not valid. The date format should be MM/DD/YYYY.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_ATTENDANCESCHOOLPROGRAMIDENTIFIER_NULL','grf_upload','attendanceSchoolProgramIdentifier','Attendance_School_Program_Identifier ~ The Attendance_School_Identifier field is blank.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_ATTENDANCESCHOOLPROGRAMIDENTIFIER_LENGTH','grf_upload','attendanceSchoolProgramIdentifier','Attendance_School_Program_Identifier ~ The entry in the Attendance_School_Identifier field must be less than or equal to 100 characters.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_STATE_NULL','grf_upload','state','State ~ The State field is blank.##',12,now(),12,now());	
	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_STATE_LENGTH','grf_upload','state','State ~ The entry in the State field must be less than or equal to 100 characters.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_DISTRICT_CODE_NULL','grf_upload','districtcode','District_Code ~ The District_Code field is blank.##',12,now(),12,now());	
	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_DISTRICT_CODE_INVALID','grf_upload','districtcode','District_Code ~ The entry in the District_Code field is not valid.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_SCHOOL_CODE_NULL','grf_upload','schoolcode','School_Code ~ The School_Code field is blank.##',12,now(),12,now());	
	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_SCHOOL_CODE_INVALID','grf_upload','schoolcode','School_Code ~ The entry in the School_Code field is not valid.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EDUCATOR_FIRST_NAME_LENGTH','grf_upload','educatorFirstName','Educator_First_Name ~ The entry in the Educator_First_Name field must be less than or equal to 80 characters.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EDUCATOR_FIRST_NAME_NULL','grf_upload','educatorFirstName','Educator_First_Name ~ The Educator_First_Name field is blank. ##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EDUCATOR_LAST_NAME_LENGTH','grf_upload','educatorLastName','Educator_Last_Name ~ The entry in the Educator_Last_Name field must be less than or equal to 80 characters.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EDUCATOR_LAST_NAME_NULL','grf_upload','educatorLastName','Educator_Last_Name ~ The Educator_Last_Name field is blank. ##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EDUCATOR_USER_NAME_LENGTH','grf_upload','educatorUserName','Educator_Username ~ The entry in the Educator_Username field must be less than or equal to 254 characters.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EDUCATOR_USER_NAME_NULL','grf_upload','educatorUserName','Educator_Username ~ The Educator_Username field is blank. ##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_STATE_EDUCATOR_IDENTIFIER_LENGTH','grf_upload','educatorIdentifier','State_Educator_Identifier ~ The entry in State_Educator_Identifier field must be less than or equal to 254 characters.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_STATE_EDUCATOR_IDENTIFIER_NULL','grf_upload','educatorIdentifier','State_Educator_Identifier ~ The State_Educator_Identifier field is blank. ##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_SUBJECT_NULL','grf_upload','subject','Subject ~ The Subject field is blank.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_PERFORMANCE_LEVEL_INVALID','grf_upload','performanceLevel','Performance_Level ~ The entry in the Performance_Level field is not valid.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_INVALIDATION_CODE_INVALID','grf_upload','invalidationCode','Invalidation_Code ~The entry in the Invalidation_Code field is not valid.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_FORMAT','grf_upload','exitwithdrawaldate','Exit_Withdrawal_Date ~The format for the Exit_Withdrawal_Date field is not valid. The date format should be MM/DD/YYYY.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_INVALID','grf_upload','exitwithdrawaldate','Exit_Withdrawal_Date ~ The year entered in the Exit_Withdrawal_Date field is not valid. The date format should be MM/DD/YYYY.## ',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_CURRENTDATE','grf_upload','exitwithdrawaldate','Exit_Withdrawal_Date~The date entered in the Exit_Withdrawal_Date field should be less than or equal to todays date. ##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_SCHOOLDATE','grf_upload','exitwithdrawaldate','Exit_Withdrawal_Date ~ The date entered in the Exit_Withdrawal_Date field should be greater or equal to the School Entry Date. ##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_NULL','grf_upload','exitwithdrawaldate','Exit_Withdrawal_Date ~ The Exit_Withdrawal_Date field cannot be blank if an  Exit_Withdrawal_Code is entered. ##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EXIT_WITHDRAWAL_CODE_NULL','grf_upload','exitwithdrawalcode','Exit_Withdrawl_Code ~ The Exit_Withdrawal_Code field cannot be blank if an Exit_Withdrawal_Date is entered. ##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EXIT_WITHDRAWAL_CODE_INVALID','grf_upload','exitwithdrawalcode','Exit_Withdrawl_Code ~The entry in the Exit_Withdrawal_Code field is not valid.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_NY_PERFORMANCE_LEVEL_INVALID','grf_upload','nyPerformanceLevel','NY_Performance_Level ~ The entry in the NY_Performance_Level field is not valid.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_LOCAL_STUDENT_IDENTIFIER_LENGTH','grf_upload','localstudentidentifier','Local_Student_Identifier ~ The entry in the Local_Student_Identifier field must be less than or equal to 100 Characters.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_FINAL_BAND_LENGTH','grf_upload','finalBand','Final_Band ~ The entry in Final_Band field must be less than or equal to 150 characters.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_TDB_GROWTH_LENGTH','grf_upload','TDB_Growth','TDB_Growth ~ The entry in TDB_Growth field must be less than or equal to 3 characters.##',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE1_INVALID','grf_upload','EE1','EE_1 ~ The entry in the EE_1 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE2_INVALID','grf_upload','EE2','EE_2 ~ The entry in the EE_2 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE3_INVALID','grf_upload','EE3','EE_3 ~ The entry in the EE_3 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE4_INVALID','grf_upload','EE4','EE_4 ~ The entry in the EE_4 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE5_INVALID','grf_upload','EE5','EE_5 ~ The entry in the EE_5 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE6_INVALID','grf_upload','EE6','EE_6 ~ The entry in the EE_6 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE7_INVALID','grf_upload','EE7','EE_7 ~ The entry in the EE_7 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE8_INVALID','grf_upload','EE8','EE_8 ~ The entry in the EE_8 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE9_INVALID','grf_upload','EE9','EE_9 ~ The entry in the EE_9 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE10_INVALID','grf_upload','EE10','EE_10 ~ The entry in the EE_10 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE11_INVALID','grf_upload','EE11','EE_11 ~ The entry in the EE_11 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE12_INVALID','grf_upload','EE12','EE_12 ~ The entry in the EE_12 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE13_INVALID','grf_upload','EE13','EE_13 ~ The entry in the EE_13 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE14_INVALID','grf_upload','EE14','EE_14 ~ The entry in the EE_14 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE15_INVALID','grf_upload','EE15','EE_15 ~ The entry in the EE_15 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE16_INVALID','grf_upload','EE16','EE_16 ~ The entry in the EE_16 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE17_INVALID','grf_upload','EE17','EE_17 ~ The entry in the EE_17 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE18_INVALID','grf_upload','EE18','EE_18 ~ The entry in the EE_18 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE19_INVALID','grf_upload','EE19','EE_19 ~ The entry in the EE_19 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE20_INVALID','grf_upload','EE20','EE_20 ~ The entry in the EE_20 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE21_INVALID','grf_upload','EE21','EE_21 ~ The entry in the EE_21 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE22_INVALID','grf_upload','EE22','EE_22 ~ The entry in the EE_22 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE23_INVALID','grf_upload','EE23','EE_23 ~ The entry in the EE_23 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE24_INVALID','grf_upload','EE24','EE_24 ~ The entry in the EE_24 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE25_INVALID','grf_upload','EE25','EE_25 ~ The entry in the EE_25 field is not valid##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EE26_INVALID','grf_upload','EE26','EE_26 ~ The entry in the EE_26 field is not valid##',12,now(),12,now());

INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CUST_VAL_STATE_MISMATCH','grf_upload','state','State ~ The entry in the State field does not match the signed-in user''s state. ##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CUST_VAL_SCHOOL_CODE_NULL','grf_upload','schoolcode','School_Code ~ The entry in the School_Code field does not exist.##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CUST_VAL_SCHOOL_CODE_MISMATCH_PARNDIST','grf_upload','schoolcode','School_Code ~ The entry in the School_Code field does not belong to the district code.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CUST_VAL_DISTRICT_CODE_NULL','grf_upload','districtcode','District_Code ~ The entry in the District_Code field does not exist.##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CUST_VAL_ATTENDANCESCHOOLPROGRAMIDENTIFIER_NULL','grf_upload','attendanceSchoolProgramIdentifier','Attendance_School_Program_Identifier ~ The entry in the Attendance_School_Program_Identifier field does not exist.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CUST_VAL_STATE_EDUCATOR_IDENTIFIER_NULL','grf_upload','educatorIdentifier','State_Educator_Identifier ~ The entry in the State_Educator_Identifier field is not valid.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CUST_VAL_ACCOUNTABILITY_DISTRICT_IDENTIFIER_NULL','grf_upload','accountabilitydistrictidentifier','Accountability_District_Identifier ~ The entry in the Accountability_District_Identifier field does not exist.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CUST_VAL_ACCOUNTABILITY_SCHOOL_IDENTIFIER_NULL','grf_upload','accountabilitySchoolIdentifier','Accountability_School_Identifier ~ The entry in the Accountability_School_Identifier field does not exist.##',12,now(),12,now());		
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CUST_VAL_ACCOUNTABILITY_SCHOOL_IDENTIFIER_MISMATCH_PARNDIST','grf_upload','accountabilitySchoolIdentifier','Accountability_School_Identifier ~ The entry in the Accountability_School_Identifier field does not belong to the Accountability_District_Identifier.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CUST_VAL_KITE_STUDENT_IDENTIFIER_NULL','grf_upload','kiteStudentIdentifier','Kite_Student_Identifier ~ The entry in the Kite_Student_Identifier field does not exist.##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CUST_VAL_SUBJECT_INVALID','grf_upload','subject','Subject ~ The entry in the Subject field does not exist. ##',12,now(),12,now());	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CUST_VAL_CURRENT_GRADE_LEVEL_NULL','grf_upload','currentGradelevel','Current_Grade_Level ~ The entry in the Current_Grade_Level field does not exist.##',12,now(),12,now());
    
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_MAIN_VAL_UNIQUEROWIDENTIFIER_UNIQUENESS','grf_upload','uniquerowidentifier','Multiple rows have the same Unique_Row_Identifier.',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_MAIN_VAL_STUDENT_SUBJECT_DUPLICATE','grf_upload','student_subject','Multiple students have the same Kite_Student_Identifier and Subject.',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_MAIN_VAL_STATE_KITE_STUDENT_IDENTIFIER_MULT_MAP','grf_upload','stateStudentIdentifier_kiteStudentIdentifier','Multiple Kite_Student_Identifiers are associated with the same State_Student_Identifier.',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_MAIN_VAL_KITE_STATE_STUDENT_IDENTIFIER_MULT_MAP','grf_upload','kiteStudentIdentifier_stateStudentIdentifier','Multiple State_Student_Identifiers are associated with the same Kite_Student_Identifier.',12,now(),12,now());
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_UPD_VAL_KITE_STD_ID_SUB_UNIQ_MISMATCH_ORG_UPLOAD','grf_upload','kiteStudentIdentifier_subject_uniquerowidentifier','The combination of Kite_Student_Identifier, Subject, and Unique_Row_Identifier is not available in the original GRF record.',12,now(),12,now());
---------
    
--DE19187 F787 GRF:  Additional changes to messages (grammar and spelling)
 DO $body$
begin
update appconfiguration set attrvalue = 'Student_Legal_Last_Name ~ The entry in the Student_Legal_Last_Name field must be less than or equal to 60 characters.##' where attrcode = 'GRF_CMN_VAL_STUDENT_LAST_NAME_LENGTH';
update appconfiguration set attrvalue = 'Exit_Withdrawal_Date~The date entered in the Exit_Withdrawal_Date field should be less than or equal to today''s date. ##' where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_CURRENTDATE';
update appconfiguration set attrvalue = 'Exit_Withdrawal_Date ~ The date entered in the Exit_Withdrawal_Date field should be greater than or equal to the School Entry Date. ##' where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_SCHOOLDATE';
update appconfiguration set attrvalue = 'Exit_Withdrawal_Date ~ The Exit_Withdrawal_Date field cannot be blank if an Exit_Withdrawal_Code is entered. ##' where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_NULL';
update appconfiguration set attrvalue = 'Exit_Withdrawal_Code ~ The Exit_Withdrawal_Code field cannot be blank if an Exit_Withdrawal_Date is entered. ##' where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_CODE_NULL';
update appconfiguration set attrvalue = 'Exit_Withdrawal_Code ~The entry in the Exit_Withdrawal_Code field is not valid.##' where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_CODE_INVALID';
update appconfiguration set attrvalue = 'EE_1 ~ The entry in the EE_1 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE1_INVALID';
update appconfiguration set attrvalue = 'EE_2 ~ The entry in the EE_2 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE2_INVALID';
update appconfiguration set attrvalue = 'EE_3 ~ The entry in the EE_3 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE3_INVALID';
update appconfiguration set attrvalue = 'EE_4 ~ The entry in the EE_4 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE4_INVALID';
update appconfiguration set attrvalue = 'EE_5 ~ The entry in the EE_5 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE5_INVALID';
update appconfiguration set attrvalue = 'EE_6 ~ The entry in the EE_6 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE6_INVALID';
update appconfiguration set attrvalue = 'EE_7 ~ The entry in the EE_7 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE7_INVALID';
update appconfiguration set attrvalue = 'EE_8 ~ The entry in the EE_8 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE8_INVALID';
update appconfiguration set attrvalue = 'EE_9 ~ The entry in the EE_9 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE9_INVALID';
update appconfiguration set attrvalue = 'EE_10 ~ The entry in the EE_10 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE10_INVALID';
update appconfiguration set attrvalue = 'EE_11 ~ The entry in the EE_11 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE11_INVALID';
update appconfiguration set attrvalue = 'EE_12 ~ The entry in the EE_12 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE12_INVALID';
update appconfiguration set attrvalue = 'EE_13 ~ The entry in the EE_13 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE13_INVALID';
update appconfiguration set attrvalue = 'EE_14 ~ The entry in the EE_14 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE14_INVALID';
update appconfiguration set attrvalue = 'EE_15 ~ The entry in the EE_15 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE15_INVALID';
update appconfiguration set attrvalue = 'EE_16 ~ The entry in the EE_16 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE16_INVALID';
update appconfiguration set attrvalue = 'EE_17 ~ The entry in the EE_17 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE17_INVALID';
update appconfiguration set attrvalue = 'EE_18 ~ The entry in the EE_18 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE18_INVALID';
update appconfiguration set attrvalue = 'EE_19 ~ The entry in the EE_19 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE19_INVALID';
update appconfiguration set attrvalue = 'EE_20 ~ The entry in the EE_20 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE20_INVALID';
update appconfiguration set attrvalue = 'EE_21 ~ The entry in the EE_21 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE21_INVALID';
update appconfiguration set attrvalue = 'EE_22 ~ The entry in the EE_22 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE22_INVALID';
update appconfiguration set attrvalue = 'EE_23 ~ The entry in the EE_23 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE23_INVALID';
update appconfiguration set attrvalue = 'EE_24 ~ The entry in the EE_24 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE24_INVALID';
update appconfiguration set attrvalue = 'EE_25 ~ The entry in the EE_25 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE25_INVALID';
update appconfiguration set attrvalue = 'EE_26 ~ The entry in the EE_26 field is not valid.##' where attrcode = 'GRF_CMN_VAL_EE26_INVALID';
end; $body$;
-------
--DE19190 F787 GRF: Added validation for exitwithdrawaldate should be greater than or equal to 1989. 
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser,
            createddate, modifieduser, modifieddate)
    VALUES ('GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_1989','grf_upload','exitwithdrawaldate','Exit_Withdrawal_Date ~ The date entered in the Exit_Withdrawal_Date field should be greater than or equal to 1989. ##',12,now(),12,now());
