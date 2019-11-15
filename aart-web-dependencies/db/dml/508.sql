--dml/508.sql
--kansas,WT duplicate value
update specialcircumstance set activeflag=false where cedscode=13816 and id=27 and specialcircumstancetype='Student took this grade level assessment last year';

--description missing
update specialcircumstance set description='Student not showing adequate effort' where cedscode=13823;
update specialcircumstance set description='Reading passage read to student (IEP)' where cedscode=13827 and activeflag=true;    

--coloradomissing dropdown
with org as (select org.id from organization org where org.displayidentifier = 'CO'),
sp as (select id from specialcircumstance where cedscode='13818' and specialcircumstancetype='Special treatment center' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

--new jersey missing dropdown

with org as (select org.id from organization org where org.displayidentifier = 'NJ'),
sp as (select id from specialcircumstance where cedscode='13816' and specialcircumstancetype='Student took this grade level assessment last year' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;