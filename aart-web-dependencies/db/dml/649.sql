-- DML for F417
select * from reportassessmentprogram_fn('DLM','ALT_DS',null,'VIEW_ALT_YEAREND_DISTRICT_REPORT');
select * from reportassessmentprogram_fn('DLM','ALT_SS',null,'VIEW_ALT_YEAREND_STATE_REPORT');

update reportassessmentprogram set readytoview = true 
where reporttypeid = (select id from category where categorycode in ('ALT_DS') and activeflag is true limit 1)
and assessmentprogramid = (select id from assessmentprogram where abbreviatedname ilike ('DLM') and activeflag is true limit 1)
and authorityid = (select id from authorities where authority ilike ('VIEW_ALT_YEAREND_DISTRICT_REPORT') and activeflag is true limit 1);

update reportassessmentprogram set readytoview = true 
where reporttypeid = (select id from category where categorycode in ('ALT_SS') and activeflag is true limit 1)
and assessmentprogramid = (select id from assessmentprogram where abbreviatedname ilike ('DLM') and activeflag is true limit 1)
and authorityid = (select id from authorities where authority ilike ('VIEW_ALT_YEAREND_STATE_REPORT') and activeflag is true limit 1);

update authorities set modifieduser =(select id from aartuser where username = 'cetesysadmin'), modifieddate  =now() , displayname = 'Upload Reports results' , objecttype='Reports-Summative Reports' where authority='VIEW_UPLOAD_RESULTS';

update fieldspecification set modifieduser =(select id from aartuser where username = 'cetesysadmin'), modifieddate  =now() , allowablevalues ='{Male,Female,male,female}' where mappedname='Gender_With_Blank' and activeflag =true and fieldname='gender';

select * from DFMInsert(3,'Accessibility_Profile_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(10,'Accessibility_Profile_Summary_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(2,'Current_Enrollment_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(8,'DLM_Test_Administration_Monitoring_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(6,'Questar_Pre-ID_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(26,'Restricted_Special_Circumstance_Code_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(4,'Roster_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(25,'Student_Login_Usernames_Passwords_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(13,'Test_Administration_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(19,'Test_Form_Assignment_to_Test_Collection_QC_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(20,'Test_Form_Media_Resource_File_QC_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(7,'Test_Records_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(14,'Test_Ticket_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(5,'Users_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(39,'DLM_Blueprint_Coverage_Summary_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(38,'KAP_Student_Scores_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(36,'K-ELPA_Test_Administration_Monitoring_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(11,'KSDE_Test_And_TASC_Records_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(37,'Monitor_Scoring_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(35,'Security_Agreement_Completion_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(18,'Training_Management_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(34,'Training_Status_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(41,'DLM_General_Research_File_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(42,'DLM_Special_Circumstances_File_Extract_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');

select * from DFMInsert(43,'DLM_Incident_File_Extract_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');


update fieldspecificationsrecordtypes set mappedname ='Studentid' , modifieddate =now() where mappedname='Studentid'
and recordtypeid in (select id from category where categorycode in ('UPLOAD_INCIDENT_FILE_TYPE','UPLOAD_KS_SC_CODE_FILE_TYPE','UPLOAD_SC_CODE_FILE_TYPE','UPLOAD_GRF_FILE_TYPE','UPLOAD_IA_GRF_FILE_TYPE','UPLOAD_NY_GRF_FILE_TYPE'));

update fieldspecificationsrecordtypes set mappedname='KSDE_SC_Code' ,  modifieddate =now()where mappedname='Ksde_Sc_Code'
and recordtypeid in (select id from category where categorycode in ('UPLOAD_KS_SC_CODE_FILE_TYPE'));  

update fieldspecificationsrecordtypes set mappedname ='AYP_School_Identifier' , modifieddate =now() where mappedname='StudentId' 
and recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_IA_GRF_FILE_TYPE','UPLOAD_NY_GRF_FILE_TYPE'));

update fieldspecificationsrecordtypes set mappedname ='Username' , modifieddate =now() where mappedname='User_Name' 
and recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_IA_GRF_FILE_TYPE','UPLOAD_NY_GRF_FILE_TYPE'));

update fieldspecificationsrecordtypes set mappedname ='Date_of_Birth', modifieddate =now() where mappedname='Date_Of_Birth' 
and recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_IA_GRF_FILE_TYPE','UPLOAD_NY_GRF_FILE_TYPE'));

update fieldspecificationsrecordtypes set mappedname ='ESOL_Participation_Code' , modifieddate =now() where mappedname='Esol_Participation_Code' 
and recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_IA_GRF_FILE_TYPE','UPLOAD_NY_GRF_FILE_TYPE'));


update fieldspecificationsrecordtypes set mappedname ='KITE_Educator_Identifier' , modifieddate =now() where mappedname='Kite_Educator_Identifier' 
and recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_IA_GRF_FILE_TYPE','UPLOAD_NY_GRF_FILE_TYPE'));

update fieldspecificationsrecordtypes set mappedname ='NY_Performance_Level' , modifieddate =now() where mappedname='Ny_Performance_Level' 
and recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_IA_GRF_FILE_TYPE','UPLOAD_NY_GRF_FILE_TYPE'));

INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'externalUniqueRowIdentifier', null, null, null, 1, 
            true, false, null, 'Unique_Row_Identifier', true, 
            now(), (select id from aartuser where username = 'cetesysadmin'), true, now(), (select id from aartuser where username = 'cetesysadmin'), 
            false, null, null, null);

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='externalUniqueRowIdentifier' and mappedname='Unique_Row_Identifier' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadCommonGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Unique_Row_Identifier');
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='externalUniqueRowIdentifier' and mappedname='Unique_Row_Identifier' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadIowaGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Unique_Row_Identifier');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id  from fieldspecification where fieldname='externalUniqueRowIdentifier' and mappedname='Unique_Row_Identifier' and activeflag=true and rejectifempty=true), (select id from category where categoryname ='UploadNewYorkGrffile'), now(), (select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Unique_Row_Identifier');
            
update fieldspecification set rejectifempty=false where id in (select id  from fieldspecification where fieldname='kiteEducatorIdentifier' and mappedname='Kite_Educator_Identifier' and activeflag=true and rejectifempty=true);

update fieldspecificationsrecordtypes set mappedname ='Educator_Username' , modifieddate =now() where mappedname='Educator_User_Name' 
and recordtypeid in (select id from category where categorycode in ('UPLOAD_GRF_FILE_TYPE','UPLOAD_IA_GRF_FILE_TYPE','UPLOAD_NY_GRF_FILE_TYPE'));

UPDATE datadictionaryfilemapping SET filelocation = 'datadictionaries';
