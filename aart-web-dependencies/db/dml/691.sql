--dml/691.sql 
-- DML
insert into authorities 
    (authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,labelname,level,sortorder)
select 'DATA_EXTRACTS_TESTING_READINESS','Create Testing Readiness Data Extract','Reports-Data Extracts',NOW(),
           (select id from aartuser where username='cetesysadmin'),TRUE, NOW(),
           (select id from aartuser where username='cetesysadmin'),'Reports','Data Extracts','General',1,
           ((select sortorder from authorities where authority='DATA_EXTRACTS_PNP_SUMMARY')+
           (select sortorder from authorities where authority='DATA_EXTRACTS_TEC_RECORDS'))/2
 where not exists(
 select 1 from authorities where authority='DATA_EXTRACTS_TESTING_READINESS'
 );
 