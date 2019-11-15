--dml/507.sql

--DE12361 Script from scriptbees

--Queries for Kansas 

--Truancy - No Paperwork Filed
update specialcircumstance set description='Truancy - No Paperwork Filed' , activeflag=true  where cedscode = 13811 ;

update specialcircumstance set assessmentprogramid = (select id from assessmentprogram where programname='KAP') where cedscode =13811;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13811' and specialcircumstancetype='Truancy - no paperwork filed' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

--Foreign exchange student

update specialcircumstance set description='Foreign exchange student' , activeflag=true  where cedscode = 13825 ;

update specialcircumstance set assessmentprogramid = (select id from assessmentprogram where programname='KAP') where cedscode =13825;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13825' and specialcircumstancetype='Foreign exchange student' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

---Cheating
update specialcircumstance set description='Cheating' ,activeflag=true where cedscode = 13821 ;

update specialcircumstance set assessmentprogramid = (select id from assessmentprogram where programname='KAP') where cedscode =13821;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13821' and specialcircumstancetype='Cheating' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

---Special treatment center

update specialcircumstance set description='Special treatment center' ,activeflag=true where cedscode = 13818 ;

update specialcircumstance set assessmentprogramid = (select id from assessmentprogram where programname='KAP') where cedscode =13818;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13818' and specialcircumstancetype='Special treatment center' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

update specialcircumstance set activeflag=true where cedscode=13816 and id=27 and specialcircumstancetype='Student took this grade level assessment last year';

----Queries for missouri

with org as (select org.id from organization org where org.displayidentifier ='MO'),
sp as (select id from specialcircumstance where cedscode='13821' and specialcircumstancetype='Cheating' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

update specialcircumstance set description='Administration or system failure',activeflag=true where cedscode = 13835 ;

with org as (select org.id from organization org where org.displayidentifier ='MO'),
sp as (select id from specialcircumstance where cedscode='13835' and specialcircumstancetype='Administration or system failure' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;


--discription 

update specialcircumstance set description='Short-term suspension - non-special education' where cedscode=13808;
update specialcircumstance set description='Suspension - special education' where cedscode=13809;
update specialcircumstance set description='Earlier truancy' where cedscode=13812;
update specialcircumstance set description='Psychological factors of emotional trauma' where cedscode=13822;
update specialcircumstance set description='Cross-enrolled' where cedscode=13833;
update specialcircumstance set description='Only for writing' where cedscode=13834;
--sc codes missing
update specialcircumstance set ksdecode=98 where cedscode=13836;
