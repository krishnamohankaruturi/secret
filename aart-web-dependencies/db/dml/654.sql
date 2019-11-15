--dml/654.sql
update authorities set displayname = 'Create new students(bundled)- cPass assessment' where authority = 'DYN_BUND_CARPATH_ASSESSMENT';

update grfreportdistrictshortname set organizationid = 
(select id from organization where organizationname  ='EDUCATIONAL PARTNERSHIP FOR INSTRUCTING CHILDREN (048242)'),
districtcode ='048242' where originaldistrictname ='EDUCATIONAL PARTNERSHIP FOR INSTRUCTING CHILDREN (048242)';

update grfreportdistrictshortname set organizationid = 
(select id from organization where organizationname  ='YOUTH CONSULTATION SERVICES - HACKENSACK (GEORGE WASHINGTON) (048370)'), 
districtcode ='048370' where originaldistrictname ='YOUTH CONSULTATION SERVICES - HACKENSACK (GEORGE WASHINGTON) (048370)';

update grfreportdistrictshortname set organizationid = 
(select id from organization where organizationname  ='NEW BEGINNINGS AT EMERSON c/o VILLANO ELEMENTARY SCHOOL (048394)'),
 districtcode ='048394' where originaldistrictname ='NEW BEGINNINGS AT EMERSON c/o VILLANO ELEMENTARY SCHOOL (048394)';

update grfreportdistrictshortname set organizationid = 
(select id from organization where organizationname  ='Office Of Education Department Of Children And Families (218501)')
 where originaldistrictname ='Office Of Education Department Of Children And Families (218501)';

update grfreportdistrictshortname set organizationid = 
(select id from organization where organizationname  ='North Jersey Elks Developmental Disabilities Agency HS (328353)')
where originaldistrictname ='North Jersey Elks Developmental Disabilities Agency HS (328353)';

update grfreportdistrictshortname set organizationid = 
(select id from organization where organizationname  ='North Jersey Elks Developmental Disabilities Agency ES (328289)')
where originaldistrictname ='North Jersey Elks Developmental Disabilities Agency ES (328289)';

update grfreportdistrictshortname set organizationid = 
(select id from organization where organizationname  ='VISTA AT ENTRADA SCHOOL OF PERFORMING ARTS AND TECHNOLOGY')
where originaldistrictname ='VISTA AT ENTRADA SCHOOL OF PERFORMING ARTS AND TECHNOLOGY';

update grfreportdistrictshortname set organizationid = 
(select id from organization where organizationname  ='PAUL ROBESON CHARTER SCHOOL FOR THE HUMANITIES (806025)')
where originaldistrictname ='PAUL ROBESON CHARTER SCHOOL FOR THE HUMANITIES (806025)';


---F459 DML -----

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ('VIEW_ALT_YEAREND_STATE_REPORT', 'View Alt Assess Yearend State Summary', 'Reports-Performance Reports',CURRENT_TIMESTAMP,
(select id from aartuser where email='cete@ku.edu'), true,CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ('VIEW_ALT_YEAREND_DISTRICT_REPORT', 'View Alt Assess Yearend District Summary', 'Reports-Performance Reports',CURRENT_TIMESTAMP,
(select id from aartuser where email='cete@ku.edu'), true,CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ('DATA_EXTRACTS_DLM_DS_SUMMARY', 'Create DLM District Summary Data Extract', 'Reports-Data Extracts',CURRENT_TIMESTAMP,
(select id from aartuser where email='cete@ku.edu'), true,CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));

INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('District Summary','ALT_DS','DLM General District Summary',
    (select id from categorytype where typecode = 'REPORT_TYPES_UI'),null,'AART',
    current_timestamp,(select id from aartuser where username like 'cetesysadmin'),
    true,current_timestamp,(select id from aartuser where username like 'cetesysadmin'));

INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('State Summary','ALT_SS','DLM General State Summary',
    (select id from categorytype where typecode = 'REPORT_TYPES_UI'),null,'AART',
    current_timestamp,(select id from aartuser where username like 'cetesysadmin'),
    true,current_timestamp,(select id from aartuser where username like 'cetesysadmin'));
		

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


select * from DFMInsert(43,'DLM_Incident_File_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin','DLM');


UPDATE datadictionaryfilemapping
   SET  filelocation='datadictionaries/kansas', 
       filename='DLM_General_Research_File_Extract_Data_Dictionary.pdf',specialdatadetailfilelocation = 'datadictionaries/kansas'
 WHERE extracttypeid= 41 and assessmentprogramid= (select id from assessmentprogram where abbreviatedname ='DLM') and stateid= (select id from organization where  displayidentifier  ='KS');

UPDATE datadictionaryfilemapping
   SET  filelocation='datadictionaries/wisconsin', 
       filename='DLM_General_Research_File_Extract_Data_Dictionary.pdf',specialdatadetailfilelocation = 'datadictionaries/wisconsin'
 WHERE extracttypeid= 41 and assessmentprogramid= (select id from assessmentprogram where abbreviatedname ='DLM') and stateid= (select id from organization where  displayidentifier  ='WI');

UPDATE datadictionaryfilemapping
   SET  filelocation='datadictionaries/ny', 
       filename='DLM_General_Research_File_Extract_Data_Dictionary.pdf',specialdatadetailfilelocation = 'datadictionaries/ny'
 WHERE extracttypeid= 41 and assessmentprogramid= (select id from assessmentprogram where abbreviatedname ='DLM') and stateid= (select id from organization where  displayidentifier  ='NY');

UPDATE datadictionaryfilemapping
   SET  filelocation='datadictionaries/iowa', 
       filename='DLM_General_Research_File_Extract_Data_Dictionary.pdf', specialdatadetailfilelocation = 'datadictionaries/iowa'
 WHERE extracttypeid= 41 and assessmentprogramid= (select id from assessmentprogram where abbreviatedname ='DLM') and stateid= (select id from organization where  displayidentifier  ='IA');
 