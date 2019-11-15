--dml/711.sql

--F683

-- Permission script
insert into authorities 
    (authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,labelname,level,sortorder)
select 'DATA_EXTRACTS_DLM_EXIT_STUDENT','Create DLM Exited Students Data Extract','Reports-Data Extracts',NOW(),
           (select id from aartuser where username='cetesysadmin'),TRUE, NOW(),
           (select id from aartuser where username='cetesysadmin'),'Reports','Data Extracts','DLM-Specific Extracts',1,
           ((select sortorder from authorities where authority='DATA_EXTRACTS_DLM_BP_COVERAGE')+
           (select sortorder from authorities where authority='DATA_EXTRACTS_FCS_REPORT'))/2
 where not exists(
 select 1 from authorities where authority='DATA_EXTRACTS_DLM_EXIT_STUDENT'
 );
 

--populating the data into studentExitCodes table

insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 1,'Transfer to public school, same district',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=1
 );
 
 insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 2,'Transfer to public school, different district, same state',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=2
 );
 
  insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 3,'Transfer to public school, different state',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=3
 );
 
   insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 4,'Transfer to an accredited private school',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=4
 );
 
   insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 5,'Transfer to a non-accredited private school',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=5
 );
 
   insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 6,'Transfer to home schooling',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=6
 );

  insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 7,'Matriculation to another school',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=7
 );
 
   insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 8,'Graduated with regular diploma',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=08
 );
 
   insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 9,'Completed school with other credentials (e.g., district-awarded GED)',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=9
 );
 
   insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 10,'Student death',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=10
 );
 
   insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 11,'Student illness',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=11
 );
 
  insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 12,'Student expulsion (or long-term suspension)',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=12
 );
 
  insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 13,'Reached maximum age for services',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=13
 );
 
  insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 14,'Discontinued schooling',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=14
 );
 
  insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 15,'Transfer to accredited or non-accredited juvenile correctional facility—educational services provided',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=15
 );
 
  insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 16,'Moved within the United States, not known to be enrolled in school',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=16
 );
 
  insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 17,'Unknown educational services provided',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=17
 );
 
  insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 18,'Student data claimed in error/never attended',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=18
 );
 
  insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 19,'Transfer to an adult education facility (i.e., for GED completion)',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=19
 );
 
   insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 20,'Transfer to a juvenile or adult correctional facility—no',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=20
 );
 
   insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 21,'Student moved to another country, may or may not be continuing enrollment',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=21
 );
 
   insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 30,'Student no longer meets eligibility criteria for alternate assessment',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=30
 );
 
   insert into studentexitcodes 
    (code,description,createddate,modifieddate,createduser,modifieduser,activeflag)
select 98,'Unresolved exit',NOW(), NOW(),
           (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
           TRUE
           where not exists(
 select 1 from studentexitcodes where code=98
 );



--F682

insert into authorities 
    (authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,labelname,level,sortorder)
select 'MANAGE_STATE_SPECIFIC_FILES','Manage state specific files','Reports-State specific files',NOW(),
           (select id from aartuser where username='cetesysadmin'),TRUE, NOW(),
           (select id from aartuser where username='cetesysadmin'),'Reports','State specific files',null,1,
           ((select sortorder from authorities where authority='VIEW_GENERAL_STUDENT_WRITING')+
           (select sortorder from authorities where authority='VIEW_TOOLS_MENU'))/2
 where not exists(
 select 1 from authorities where authority='MANAGE_STATE_SPECIFIC_FILES'
 );

insert into authorities 
    (authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,labelname,level,sortorder)
select 'VIEW_STATE_SPECIFIC_FILES','View state specific files','Reports-State specific files',NOW(),
           (select id from aartuser where username='cetesysadmin'),TRUE, NOW(),
           (select id from aartuser where username='cetesysadmin'),'Reports','State specific files',null,1,
           ((select sortorder from authorities where authority='MANAGE_STATE_SPECIFIC_FILES')+
           (select sortorder from authorities where authority='VIEW_TOOLS_MENU'))/2
 where not exists(
 select 1 from authorities where authority='VIEW_STATE_SPECIFIC_FILES'
 );
 
insert into categorytype(typename, typecode, typedescription, createduser, modifieduser,
createddate, modifieddate, activeflag) values('State Specific File Rules', 'STATE_SPECIFIC_FILE_RULES', 'State Specific File Rules', 
(select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'), now(), now(), true);

insert into category (categoryname, categorycode, categorydescription, categorytypeid,createduser, modifieduser, createddate, 
modifieddate, activeflag) values('20','STATE_SPECIFIC_FILE_MAX_SIZE', 'State specific file max size', 
(select id from categorytype where typecode='STATE_SPECIFIC_FILE_RULES'),(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'), now(), now(), true);

insert into category (categoryname, categorycode, categorydescription, categorytypeid,createduser, modifieduser, createddate, 
modifieddate, activeflag) values('.csv,.pdf,.xls,.xlsx,.doc,.docx,.txt','STATE_SPECIFIC_FILE_ALLOWED_TYPES', 'State specific file allowed types', 
(select id from categorytype where typecode='STATE_SPECIFIC_FILE_RULES'),(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'), now(), now(), true);
