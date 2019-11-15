-- DML 650.sql
--F417
delete from datadictionaryfilemapping;

select * from DFMInsert(3,'Accessibility_Profile_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(10,'Accessibility_Profile_Summary_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(2,'Current_Enrollment_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(8,'DLM_Test_Administration_Monitoring_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(6,'Questar_Pre-ID_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(26,'Restricted_Special_Circumstance_Code_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(4,'Roster_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(25,'Student_Login_Usernames_Passwords_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(13,'Test_Administration_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(19,'Test_Form_Assignment_to_Test_Collection_QC_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(20,'Test_Form_Media_Resource_File_QC_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(7,'Test_Records_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(14,'Test_Ticket_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(5,'Users_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(39,'DLM_Blueprint_Coverage_Summary_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(38,'KAP_Student_Scores_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(36,'K-ELPA_Test_Administration_Monitoring_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(11,'KSDE_Test_And_TASC_Records_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(37,'Monitor_Scoring_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(35,'Security_Agreement_Completion_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(18,'Training_Management_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

select * from DFMInsert(34,'Training_Status_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);

--F458
update category set categorycode ='UPLOAD_GRF_NY_FILE_TYPE' where categorycode ='UPLOAD_NY_GRF_FILE_TYPE';
update category set categorycode ='UPLOAD_GRF_IA_FILE_TYPE' where categorycode ='UPLOAD_IA_GRF_FILE_TYPE';
update category set categorycode ='UPLOAD_SC_CODE_KS_FILE_TYPE' where categorycode ='UPLOAD_KS_SC_CODE_FILE_TYPE';

---updating as even if we have reject if empty is true not allowing to enter empty value 
INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'hispanicEthnicity', '{0,1,''''}', null, null, 1, 
            false, true, null, 'Hispanic_Ethnicity_upload_results', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);


UPDATE fieldspecification set allowablevalues ='{'''',Male,Female,male,female}',fieldlength=6, 
modifieddate =now() where fieldname='gender' and mappedname='Gender_With_Blank';

UPDATE fieldspecification set allowablevalues ='{'''',0,1,2,3,4,5,9}' , modifieddate =now()where fieldname like 'ee%' and mappedname like 'EE_%';

UPDATE fieldspecification set   rejectifinvalid =true,fieldlength=null, modifieddate =now() where  fieldname='externalUniqueRowIdentifier';

update fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where 
fieldname ='hispanicEthnicity' and mappedname='Hispanic_Ethnicity_upload_results' ), modifieddate =now() 
where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE')) 
and mappedname='Hispanic_Ethnicity';

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'stateStudentIdentifier', null, null, null, 50, 
            false, true, null, 'State_Student_Identifier_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);
        
INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'legalFirstName', null, null, null, 80, 
            false, true, null, 'Student_Legal_First_Name_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);


INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'legalMiddleName', null, null, null, 80, 
            false, true, null, 'Student_Legal_Middle_Name_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);
            
            
INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'legalLastName', null, null, null, 80, 
            false, true, null, 'Student_Legal_Last_Name_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);            


INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'generationCode', null, null, null, 10, 
            false, true, null, 'Generation_Code_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

           
            
INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'dateOfBirth', null, null, null, 10, 
            false, true, '^$|(0?[1-9]|1[012])(/)(0?[1-9]|[12][0-9]|3[01])(/)(19|20)?[0-9][0-9]', 'Date_of_Birth_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, 'Date', null, null);

           
 INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'schoolEntryDate', null, null, null, 10, 
            false, true, '^$|(0?[1-9]|1[012])(/)(0?[1-9]|[12][0-9]|3[01])(/)(19|20)?[0-9][0-9]', 'School_Entry_Date_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, 'Date', null, null);


INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'educatorFirstName', null, null, null, 80, 
            false, true, null, 'Educator_First_Name_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

 INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'educatorLastName', null, null, null, 80, 
            false, true, null, 'Educator_Last_Name_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

  INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'educatorIdentifier', null, null, null, null, 
            false, true, null, 'Educator_Identifier_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

   INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'currentGradelevel', null, null, null, 150, 
            true, true, null, 'Current_Grade_Level_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false,'number', null, null);


  INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'firstLanguage', null, null, null, 2, 
            false, true, null, 'First_Language_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, 'number', null, null);

   INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'hispanicEthnicity', null, null, null, 1, 
            false, true, null, 'Hispanic_Ethnicity_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, 'number', null, null);

     INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'exitWithdrawalType', null, null, null, null, 
            false, true, null, 'Exit_Withdrawal_Code_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

     INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'primaryDisabilityCode', null, null, null, 60, 
            false, true, null, 'Primary_Disability_Code_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

     INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'esolParticipationCode', null, null, null, 1, 
            false, true, null, 'ESOL_Participation_Code_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, 'number', null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'studentId', null, null, null, 10, 
            false, true, null, 'Student_Id_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, 'number', null, null);

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'schoolIdentifier', null, null, null, 100, 
            true, true, null, 'School_Code_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

 INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'residenceDistrictIdentifier', null, null, null, 100, 
            true, true, null, 'District_Code_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

  INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'attendanceSchoolProgramIdentifier', null, null, null, 100, 
            false, true, null, 'Attendance_School_Program_Identifier_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

  INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'aypSchoolIdentifier', null, null, null, 100, 
            false, true, null, 'Ayp_School_Identifier_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);
 
  INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'comprehensiveRace', null, null, null, 5, 
            false, true, null, 'Comprehensive_Race_RESULTS_UPLOAD', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, 'number', null, null);

     
         
update fieldspecification set fieldtype='number'  where fieldname='studentId' and rejectifempty is true and mappedname='Student_Id' and activeflag is true;                  
 
update fieldspecification set rejectifinvalid =true ,rejectifempty=false,fieldtype='number'  where fieldname='specialCircumstanceCode' and rejectifempty is true and mappedname='Special_Circumstance_Code' and activeflag is true;

update fieldspecification set rejectifinvalid =true ,rejectifempty=false  where fieldname='issueCode' and rejectifempty is true and mappedname='Issue_Code' and activeflag is true;

update fieldspecification set rejectifinvalid =true,allowablevalues=null,rejectifempty=false,fieldlength=1,fieldtype='number' where fieldname like 'ee%' and rejectifempty is false and mappedname like 'EE_%' and activeflag is true;

update fieldspecification set rejectifinvalid =true ,allowablevalues=null,rejectifempty=true,fieldtype='number'  where fieldname='kiteEducatorIdentifier' and rejectifempty is false and mappedname='Kite_Educator_Identifier' and activeflag is true;

update fieldspecification set rejectifinvalid =true ,allowablevalues=null,rejectifempty=false,fieldtype='number' where fieldname='invalidationCode' and rejectifempty is true and mappedname='Invalidation_Code' and activeflag is true;

update fieldspecification set rejectifinvalid =true ,allowablevalues=null,rejectifempty=false where fieldname='finalBand' and rejectifempty is true and mappedname='Final_Band' and activeflag is true;

update fieldspecification set rejectifinvalid =true ,allowablevalues=null,rejectifempty=false,fieldlength=6  where fieldname='gender' and rejectifempty is false and mappedname='Gender_With_Blank' and activeflag is true;

update fieldspecification set rejectifinvalid =true ,rejectifempty=false,fieldlength=8,fieldtype='number'  where fieldname='externalUniqueRowIdentifier' and rejectifempty is true and mappedname='Unique_Row_Identifier' and activeflag is true;

update fieldspecification set rejectifinvalid =true ,rejectifempty=false  where fieldname='educatorUserName' and rejectifempty is true and mappedname='Educator_User_Name' and activeflag is true;

update fieldspecification set rejectifinvalid =true ,rejectifempty=false where fieldname='userName' and rejectifempty is true and mappedname='User_Name' and activeflag is true;

update fieldspecification set rejectifinvalid =true ,rejectifempty=false where fieldname='sgp' and rejectifempty is true and mappedname='SGP' and activeflag is true;

update fieldspecification set rejectifinvalid =true ,rejectifempty=false where fieldname='specialCircumstanceLabel' and rejectifempty is true and mappedname='Special_Circumstance_Label' and activeflag is true;

update fieldspecification set fieldlength=null,rejectifinvalid =true ,rejectifempty=false where fieldname='essentialElement' and rejectifempty is true and mappedname='Essential_Element' and activeflag is true;

update fieldspecification set fieldlength=null,rejectifinvalid =true ,rejectifempty=false  where fieldname='assessment' and rejectifempty is true and mappedname='Assessment' and activeflag is true;

update fieldspecification set fieldlength=null,rejectifinvalid =true ,rejectifempty=false where fieldname='ksdeScCode' and rejectifempty is true and mappedname='Ksde_Sc_Code' and activeflag is true;

update fieldspecification set fieldlength=null,rejectifinvalid =true ,rejectifempty=false where fieldname='ksdeScCode' and rejectifempty is true and mappedname='Ksde_Sc_Code' and activeflag is true;

---field specification record type 
UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='stateStudentIdentifier' and rejectifempty is false and mappedname='State_Student_Identifier_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE','UPLOAD_SC_CODE_FILE_TYPE','UPLOAD_SC_CODE_KS_FILE_TYPE','UPLOAD_INCIDENT_FILE_TYPE'))and mappedname='State_Student_Identifier';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='legalFirstName' and rejectifempty is false and mappedname='Student_Legal_First_Name_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE','UPLOAD_SC_CODE_FILE_TYPE','UPLOAD_SC_CODE_KS_FILE_TYPE','UPLOAD_INCIDENT_FILE_TYPE'))and mappedname='Student_Legal_First_Name';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='legalMiddleName' and rejectifempty is false and mappedname='Student_Legal_Middle_Name_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE','UPLOAD_SC_CODE_FILE_TYPE','UPLOAD_SC_CODE_KS_FILE_TYPE','UPLOAD_INCIDENT_FILE_TYPE'))and mappedname='Student_Legal_Middle_Name';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='legalLastName' and rejectifempty is false and mappedname='Student_Legal_Last_Name_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE','UPLOAD_SC_CODE_FILE_TYPE','UPLOAD_SC_CODE_KS_FILE_TYPE','UPLOAD_INCIDENT_FILE_TYPE'))and mappedname='Student_Legal_Last_Name';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='generationCode' and rejectifempty is false and mappedname='Generation_Code_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE','UPLOAD_SC_CODE_FILE_TYPE','UPLOAD_SC_CODE_KS_FILE_TYPE','UPLOAD_INCIDENT_FILE_TYPE'))and mappedname='Generation_Code';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='dateOfBirth' and rejectifempty is false and mappedname='Date_of_Birth_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE','UPLOAD_SC_CODE_FILE_TYPE','UPLOAD_SC_CODE_KS_FILE_TYPE','UPLOAD_INCIDENT_FILE_TYPE'))and mappedname='Date_of_Birth';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='schoolEntryDate' and rejectifempty is false and mappedname ='School_Entry_Date_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE'))and mappedname='School_Entry_Date';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='schoolEntryDate' and rejectifempty is false and mappedname ='School_Entry_Date_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE'))and mappedname='School_Entry_Date';
-----need to change 
UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='educatorFirstName' and rejectifempty is false and mappedname ='Educator_First_Name_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE'))and mappedname='Educator_First_Name';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='educatorLastName' and rejectifempty is false and mappedname ='Educator_Last_Name_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE'))and mappedname='Educator_Last_Name';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='educatorIdentifier' and rejectifempty is false and mappedname ='Educator_Identifier_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE'))and mappedname='Educator_Identifier';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='currentGradelevel' and rejectifempty is true and mappedname ='Current_Grade_Level_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE'))and mappedname='Current_Grade_Level';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='firstLanguage' and rejectifempty is false and mappedname ='First_Language_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE'))and mappedname='First_Language';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='hispanicEthnicity' and rejectifempty is false and mappedname ='Hispanic_Ethnicity_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE'))and mappedname='Hispanic_Ethnicity';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='exitWithdrawalType' and rejectifempty is false and mappedname ='Exit_Withdrawal_Code_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE')) and mappedname='Exit_Withdrawal_Code';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='primaryDisabilityCode' and rejectifempty is false and mappedname ='Primary_Disability_Code_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE'))and mappedname='Primary_Disability_Code';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='esolParticipationCode' and rejectifempty is false and mappedname ='ESOL_Participation_Code_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE')) and mappedname='ESOL_Participation_Code';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='schoolIdentifier' and rejectifempty is true and mappedname ='School_Code_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE')) and mappedname='School_Code';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='residenceDistrictIdentifier' and rejectifempty is true and mappedname ='District_Code_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE')) and mappedname='District_Code';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='attendanceSchoolProgramIdentifier' and rejectifempty is false and mappedname ='Attendance_School_Program_Identifier_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE')) and mappedname='Attendance_School_Program_Identifier';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='aypSchoolIdentifier' and rejectifempty is false and mappedname ='Ayp_School_Identifier_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE')) and mappedname='Ayp_School_Identifier';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='comprehensiveRace' and rejectifempty is false and mappedname ='Comprehensive_Race_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_GRF_IA_FILE_TYPE','UPLOAD_GRF_NY_FILE_TYPE')) and mappedname='Comprehensive_Race';

UPDATE fieldspecificationsrecordtypes set fieldspecificationid =(select id from fieldspecification where fieldname='studentId' and rejectifempty is false and mappedname ='Student_Id_RESULTS_UPLOAD' and activeflag is true ) where recordtypeid in (select id from category where categorycode in ('UPLOAD_SC_CODE_FILE_TYPE','UPLOAD_SC_CODE_KS_FILE_TYPE','UPLOAD_INCIDENT_FILE_TYPE')) and mappedname='Studentid';