--DE15798
insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
	('studentId', null, null, null, null, true,
	 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'Number'); 



update fieldspecificationsrecordtypes 
set fieldspecificationid = (select id from fieldspecification where fieldname='studentId' and mappedname is null), modifieddate=now() 
where mappedname='EP_Student_ID';

--DE15799
update authorities set displayname='View School Aggregate (Bundled)' where displayname = 'View Alt Assess YearEnd School Summary Bundled Report';
update authorities set displayname='View Class Aggregate' where displayname = 'View Alternate Assessment Classroom Report';
update authorities set displayname='View School Aggregate' where displayname = 'View Alternate Assessment School Report';

-- changepond changes
INSERT INTO datadictionaryfilemapping(
            extracttypeid, assessmentprogramid, stateid, 
             specialdatadetailfilelocation, specialdatadetailfilename, 
            createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='KS'),
            'datadictionaries\kansas', 
            'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx', now(), (select id from aartuser where username = 'cetesysadmin'), 
            now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO datadictionaryfilemapping(
            extracttypeid, assessmentprogramid, stateid, 
             specialdatadetailfilelocation, specialdatadetailfilename, 
            createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='WI'),
            'datadictionaries\wisconsin', 
            'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx', now(), (select id from aartuser where username = 'cetesysadmin'), 
            now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO datadictionaryfilemapping(
            extracttypeid, assessmentprogramid, stateid, 
             specialdatadetailfilelocation, specialdatadetailfilename, 
            createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='NY'),
            'datadictionaries\ny', 
            'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx', now(), (select id from aartuser where username = 'cetesysadmin'), 
            now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO datadictionaryfilemapping(
            extracttypeid, assessmentprogramid, stateid, 
             specialdatadetailfilelocation, specialdatadetailfilename, 
            createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='IA'),
            'datadictionaries\iowa', 
            'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx', now(), (select id from aartuser where username = 'cetesysadmin'), 
            now(), (select id from aartuser where username = 'cetesysadmin'));

--Update Script to set other datadetail false
UPDATE datadictionaryfilemapping SET activeflag = false WHERE extracttypeid != 41;

--Iowa(update)
UPDATE datadictionaryfilemapping
   SET  filelocation='datadictionaries/Iowa',filename='DLM_General_Research_File_Extract_Data_Dictionary.pdf',
	specialdatadetailfilelocation = 'datadictionaries/Iowa',specialdatadetailfilename='DLM_General_Research_File_Extract_EE_Crosswalk.xlsx'
 WHERE extracttypeid= 41 and assessmentprogramid= (select id from assessmentprogram where abbreviatedname ='DLM') and stateid= (select id from organization where  displayidentifier  ='IA' and organizationtypeid = (select id from organizationtype where typecode  = 'ST'));
--Kansas(update)
UPDATE datadictionaryfilemapping
   SET  filelocation='datadictionaries/Kansas',filename='DLM_General_Research_File_Extract_Data_Dictionary.pdf',
	specialdatadetailfilelocation = 'datadictionaries/Kansas',specialdatadetailfilename='DLM_General_Research_File_Extract_EE_Crosswalk.xlsx'
 WHERE extracttypeid= 41 and assessmentprogramid= (select id from assessmentprogram where abbreviatedname ='DLM') and stateid= (select id from organization where  displayidentifier  ='KS' and organizationtypeid = (select id from organizationtype where typecode  = 'ST'));
--New York(update)
UPDATE datadictionaryfilemapping
   SET  filelocation='datadictionaries/New York',filename='DLM_General_Research_File_Extract_Data_Dictionary.pdf',
	specialdatadetailfilelocation = 'datadictionaries/New York',specialdatadetailfilename='DLM_General_Research_File_Extract_EE_Crosswalk.xlsx'
 WHERE extracttypeid= 41 and assessmentprogramid= (select id from assessmentprogram where abbreviatedname ='DLM') and stateid= (select id from organization where  displayidentifier  ='NY' and organizationtypeid = (select id from organizationtype where typecode  = 'ST'));
--Wisconsin(update)
UPDATE datadictionaryfilemapping
   SET  filelocation='datadictionaries/Wisconsin',filename='DLM_General_Research_File_Extract_Data_Dictionary.pdf',
	specialdatadetailfilelocation = 'datadictionaries/Wisconsin',specialdatadetailfilename='DLM_General_Research_File_Extract_EE_Crosswalk.xlsx'
 WHERE extracttypeid= 41 and assessmentprogramid= (select id from assessmentprogram where abbreviatedname ='DLM') and stateid= (select id from organization where  displayidentifier  ='WI' and organizationtypeid = (select id from organizationtype where typecode  = 'ST'));

--Alaska
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'),(select id from organization where  displayidentifier  ='AK' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/Alaska','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/Alaska', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));

--Colorado
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='CO' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/Colorado','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/Colorado', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));
--Illinois
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='IL' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/Illinois','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/Illinois', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));
--Maryland
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='MD' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/Maryland','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/Maryland', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));
--Miccosukee Indian School
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='BIE-Miccosukee' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/Miccosukee Indian School','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/Miccosukee Indian School', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));
--Missouri
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='MO' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/Missouri','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/Missouri', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));
--New Hampshire
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='NH' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/New Hampshire','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/New Hampshire', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));
--New Jersey
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='NJ' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/New Jersey','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/New Jersey', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));
--North Dakota
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='ND' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/North Dakota','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/North Dakota', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));
--Oklahoma
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='OK' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/Oklahoma','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/Oklahoma', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));
--Utah
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='UT' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/Utah','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/Utah', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));
--Vermont
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='VT' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/Vermont','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/Vermont', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));
--West Virginia
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='WV' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/West Virginia','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/West Virginia', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));

--DLM QC EOY State
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='DLMQCEOYST' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/DLM QC EOY State','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/DLM QC EOY State', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));

--DLM QC IM State "
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='DLMQCIMST' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/DLM QC IM State','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/DLM QC IM State', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));

--DLM QC State
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='DLMQCST' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/DLM QC State','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/DLM QC State', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));

--DLM QC YE State
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='DLMQCYEST' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/DLM QC YE State','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/DLM QC YE State', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));

--NY Training State
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='NYTRAIN' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/NY Training State','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/NY Training State', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));

--Pennsylvania
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='PA' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/Pennsylvania','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/Pennsylvania', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));

--Service Desk QC State
INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filelocation, filename, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
    VALUES ( 41, (select id from assessmentprogram where abbreviatedname ='DLM'), (select id from organization where  displayidentifier  ='SDQA' and organizationtypeid = (select id from organizationtype where typecode  = 'ST')),
            'datadictionaries/Service Desk QC State','DLM_General_Research_File_Extract_Data_Dictionary.pdf', 
            'datadictionaries/Service Desk QC State', 'DLM_General_Research_File_Extract_EE_Crosswalk.xlsx',
            now(), (select id from aartuser where username = 'cetesysadmin'), now(), (select id from aartuser where username = 'cetesysadmin'));
