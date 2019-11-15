--dml/712.sql

--F671
--subject
update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'subject' and rejectifempty is true) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE') and mappedname = 'Subject' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'subject' and rejectifempty is true) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE') and mappedname = 'Subject' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'subject' and rejectifempty is true) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE') and mappedname = 'Subject' and activeflag is true;


--state student identifier
update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'stateStudentIdentifier' and rejectifempty is true and activeflag is true and formatregex is null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE') and mappedname = 'State_Student_Identifier' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'stateStudentIdentifier' and rejectifempty is true and activeflag is true and formatregex is null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE') and mappedname = 'State_Student_Identifier' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'stateStudentIdentifier' and rejectifempty is true and activeflag is true and formatregex is null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE') and mappedname = 'State_Student_Identifier' and activeflag is true;


--school entry date
update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'schoolEntryDate' and rejectifempty is true and activeflag is true and formatregex is not null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE') and mappedname = 'School_Entry_Date' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'schoolEntryDate' and rejectifempty is true and activeflag is true and formatregex is not null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE') and mappedname = 'School_Entry_Date' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'schoolEntryDate' and rejectifempty is true and activeflag is true and formatregex is not null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE') and mappedname = 'School_Entry_Date' and activeflag is true;


--primaryDisabilityCode
update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'primaryDisabilityCode' and rejectifempty is true and activeflag is true) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE') and mappedname = 'Primary_Disability_Code' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'primaryDisabilityCode' and rejectifempty is true and activeflag is true) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE') and mappedname = 'Primary_Disability_Code' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'primaryDisabilityCode' and rejectifempty is true and activeflag is true) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE') and mappedname = 'Primary_Disability_Code' and activeflag is true;

--legalFirstName
update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'legalFirstName' and rejectifempty is true and activeflag is true and mappedname is null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE') and mappedname = 'Student_Legal_First_Name' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'legalFirstName' and rejectifempty is true and activeflag is true and mappedname is null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE') and mappedname = 'Student_Legal_First_Name' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'legalFirstName' and rejectifempty is true and activeflag is true and mappedname is null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE') and mappedname = 'Student_Legal_First_Name' and activeflag is true;


--Student_Legal_Last_Name
update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'legalLastName' and rejectifempty is true and activeflag is true and mappedname is null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE') and mappedname = 'Student_Legal_Last_Name' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'legalLastName' and rejectifempty is true and activeflag is true and mappedname is null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE') and mappedname = 'Student_Legal_Last_Name' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'legalLastName' and rejectifempty is true and activeflag is true and mappedname is null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE') and mappedname = 'Student_Legal_Last_Name' and activeflag is true;


--invalidationCode
update fieldspecification set modifieddate = now(), rejectifempty = true, allowablevalues = '{0,1}' where fieldname = 'invalidationCode';

--hispanicEthnicity
update fieldspecification set rejectifempty = true, allowablevalues = '{0,1}' where fieldname = 'hispanicEthnicity' and rejectifempty is false and activeflag is true and allowablevalues = '{0,1,''''}';

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'hispanicEthnicity' and rejectifempty is true and activeflag is true and allowablevalues = '{0,1}') where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE') and mappedname = 'Hispanic_Ethnicity' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'hispanicEthnicity' and rejectifempty is true and activeflag is true and allowablevalues = '{0,1}') where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE') and mappedname = 'Hispanic_Ethnicity' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'hispanicEthnicity' and rejectifempty is true and activeflag is true and allowablevalues = '{0,1}') where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE') and mappedname = 'Hispanic_Ethnicity' and activeflag is true;


--gender
update fieldspecification set modifieddate = now(), rejectifempty = true where fieldname = 'gender' and rejectifempty is false and activeflag is true;


--First_Language
update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'firstLanguage' and rejectifempty is false and activeflag is true and mappedname is null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE') and mappedname = 'First_Language' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'firstLanguage' and rejectifempty is false and activeflag is true and mappedname is null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE') and mappedname = 'First_Language' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'firstLanguage' and rejectifempty is false and activeflag is true and mappedname is null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE') and mappedname = 'First_Language' and activeflag is true;

--Unique_Row_Identifier
update fieldspecification set modifieddate = now(), rejectifempty = true where fieldname = 'externalUniqueRowIdentifier' and rejectifempty is false and activeflag is true;

--Date_of_Birth
update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'dateOfBirth' and rejectifempty is true and activeflag is true and formatregex is not null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE') and mappedname = 'Date_of_Birth' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'dateOfBirth' and rejectifempty is true and activeflag is true and formatregex is not null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE') and mappedname = 'Date_of_Birth' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'dateOfBirth' and rejectifempty is true and activeflag is true and formatregex is not null) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE') and mappedname = 'Date_of_Birth' and activeflag is true;

--Comprehensive_Race
update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'comprehensiveRace' and rejectifempty is true) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE') and mappedname = 'Comprehensive_Race' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'comprehensiveRace' and rejectifempty is true) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE') and mappedname = 'Comprehensive_Race' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'comprehensiveRace' and rejectifempty is true) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE') and mappedname = 'Comprehensive_Race' and activeflag is true;

--attendanceSchoolProgramIdentifier
update fieldspecification set modifieddate = now(), rejectifempty = true where fieldname = 'attendanceSchoolProgramIdentifier' and rejectifempty is false and activeflag is true and fieldlength = 100;

--ESOL_Participation_Code
update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'esolParticipationCode' and rejectifempty is true) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_FILE_TYPE') and mappedname = 'ESOL_Participation_Code' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'esolParticipationCode' and rejectifempty is true) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_IA_FILE_TYPE') and mappedname = 'ESOL_Participation_Code' and activeflag is true;

update fieldspecificationsrecordtypes set modifieddate = now(), fieldspecificationid = (select id from fieldspecification where fieldname = 'esolParticipationCode' and rejectifempty is true) where recordtypeid = (select id from category where categorycode = 'UPLOAD_GRF_NY_FILE_TYPE') and mappedname = 'ESOL_Participation_Code' and activeflag is true;

--"Current_Grade_Level"
update fieldspecification set modifieddate = now(), rejectifempty = true, allowablevalues='{K,1,2,3,4,5,6,7,8,9,10,11,12}', fieldtype = null where fieldname = 'currentGradelevel';

--permission
insert into authorities (authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,labelname,level,sortorder)
select 'CREATE_GRF_INFO','Create GRF Info' ,'Setting-Report Setup',now(),(select id from aartuser where email='cete@ku.edu'),
true,now(),(select id from aartuser where email='cete@ku.edu'),'Settings','Reports Setup','Manage GRF',1,1950 
WHERE NOT EXISTS (select 1 from authorities where authority='CREATE_GRF_INFO' 
and displayname='Create GRF Info' and activeflag is true);


insert into authorities (authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,labelname,level,sortorder)
select 'EDIT_GRF_INFO','Edit GRF Info' ,'Setting-Report Setup',now(),(select id from aartuser where email='cete@ku.edu'),
true,now(),(select id from aartuser where email='cete@ku.edu'),'Settings','Reports Setup','Manage GRF',1,1975 
WHERE NOT EXISTS (select 1 from authorities where authority='EDIT_GRF_INFO' 
and displayname='Edit GRF Info' and activeflag is true);

--F688

update organizationreportdetails set reporttype = 'CPASS_ORG_SCORE', modifieddate = now() where assessmentprogramid = 11 and reporttype = 'CPASS_GEN_SD' and reportcycle = 'Dec' and schoolyear = 2018 and scalescore is not null;

update fieldspecification set minimum =0,maximum=1000 where fieldname ='totalNoOfItems' and rejectifempty is true and activeflag is true;

update fieldspecification set fieldtype ='number',minimum =0,maximum=100 where fieldname ='percentCorrect' and rejectifempty is true and activeflag is true;


---F679 DML---


update authorities set authority ='CREATE_AGGREGATE_DISTRICT_CSV',displayname = 'Create DLM District Aggregate CSV',sortorder = 10075
where authority = 'DATA_EXTRACTS_DLM_DS_SUMMARY';

insert into authorities (authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,labelname,level,sortorder)
select 'CREATE_AGGREGATE_SCHOOL_CSV','Create DLM School Aggregate CSV' ,'Reports-Data Extracts',now(),(select id from aartuser where email='cete@ku.edu'),
true,now(),(select id from aartuser where email='cete@ku.edu'),'Reports','Alternate Assessments','',1,10025 
WHERE NOT EXISTS (select 1 from authorities where authority='CREATE_AGGREGATE_SCHOOL_CSV' 
and displayname='Create DLM School Aggregate CSV' and activeflag is true);

insert into authorities (authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,labelname,level,sortorder)
select 'CREATE_AGGREGATE_CLASSROOM_CSV','Create DLM Classroom Aggregate CSV' ,'Reports-Data Extracts',now(),(select id from aartuser where email='cete@ku.edu'),
true,now(),(select id from aartuser where email='cete@ku.edu'),'Reports','Alternate Assessments','',1,10050 
WHERE NOT EXISTS (select 1 from authorities where authority='CREATE_AGGREGATE_CLASSROOM_CSV' 
and displayname='Create DLM Classroom Aggregate CSV' and activeflag is true);

