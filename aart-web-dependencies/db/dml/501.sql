--dml/501.sql

-- Activating existing master special circustances codes
update specialcircumstance set activeflag = true where cedscode = '13826';
update specialcircumstance set activeflag = true where specialcircumstancetype ='Catastrophic illness or accident' and cedscode =13814;
update specialcircumstance set activeflag = true where specialcircumstancetype ='Other reason for nonparticipation' and cedscode =13831;
update specialcircumstance set activeflag = true where specialcircumstancetype ='Home schooled for assessed subjects' and cedscode =13815;
update specialcircumstance set activeflag = true where specialcircumstancetype ='Student took this grade level assessment last year' and cedscode =13816;
update specialcircumstance set activeflag = true where specialcircumstancetype ='Homebound' and cedscode =13824;
update specialcircumstance set activeflag = true where specialcircumstancetype ='Other reason for ineligibility' and cedscode =13830;
update specialcircumstance set activeflag = true where specialcircumstancetype ='Left testing' and cedscode =13832;
update specialcircumstance set activeflag = true where specialcircumstancetype ='Teacher cheating or mis-admin' and cedscode =13836;
update specialcircumstance set activeflag = true where specialcircumstancetype ='Fire alarm' and cedscode =13837;

-- Adding new special circumstances codes
insert into specialcircumstance (specialcircumstancetype, cedscode, activeflag, createduser, modifieduser)
select 'Absent', '09999', true, id, id from aartuser where username='cetesysadmin';

insert into specialcircumstance (specialcircumstancetype, cedscode, activeflag, createduser, modifieduser)
select 'Invalidation', '09999', true, id, id from aartuser where username='cetesysadmin';

-- ALASKA state special circustance codes -- 
with org as (select org.id from organization org where org.displayidentifier = 'AK'),
sp as (select id from specialcircumstance where cedscode='3454' and specialcircumstancetype='Medical Waiver' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'AK'),
sp as (select id from specialcircumstance where cedscode='13820' and specialcircumstancetype='Parent refusal' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'AK'),
sp as (select id from specialcircumstance where cedscode='13826' and specialcircumstancetype='Student refusal' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'AK'),
sp as (select id from specialcircumstance where cedscode='9999' and specialcircumstancetype='Absent' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'AK'),
sp as (select id from specialcircumstance where cedscode='9999' and specialcircumstancetype='Invalidation' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

-- KANSAS state special circustance codes -- 

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13807' and specialcircumstancetype='Long-term suspension - non-special education' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13810' and specialcircumstancetype='Truancy - paperwork filed' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13811' and specialcircumstancetype='Truancy - no paperwork filed' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13813' and specialcircumstancetype='Chronic absences' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13814' and specialcircumstancetype='Catastrophic illness or accident' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser,requireconfirmation)
select org.id, sp.id, usr.id, usr.id,true from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13815' and specialcircumstancetype='Home schooled for assessed subjects' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13816' and specialcircumstancetype='Student took this grade level assessment last year' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13817' and specialcircumstancetype='Incarcerated at adult facility' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13818' and specialcircumstancetype='Special treatment center' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13819' and specialcircumstancetype='Special detention center' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13820' and specialcircumstancetype='Parent refusal' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13821' and specialcircumstancetype='Cheating' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser,requireconfirmation)
select org.id, sp.id, usr.id, usr.id,true from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13824' and specialcircumstancetype='Homebound' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13825' and specialcircumstancetype='Foreign exchange student' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13827' and specialcircumstancetype='Reading passage read to student (IEP)' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser,requireconfirmation)
select org.id, sp.id, usr.id, usr.id,true from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13828' and specialcircumstancetype='Non-special education student used calculator on non-calculator items' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser,requireconfirmation)
select org.id, sp.id, usr.id, usr.id,true from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13829' and specialcircumstancetype='Student used math journal (non-IEP)' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser,requireconfirmation)
select org.id, sp.id, usr.id, usr.id,true from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13832' and specialcircumstancetype='Left testing' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='13836' and specialcircumstancetype='Teacher cheating or mis-admin' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'KS'),
sp as (select id from specialcircumstance where cedscode='09999' and specialcircumstancetype='Other' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser,requireconfirmation)
select org.id, sp.id, usr.id, usr.id,true from org, sp, usr;


-- MO state special circustance codes -- 
with org as (select org.id from organization org where org.displayidentifier = 'MO'),
sp as (select id from specialcircumstance where cedscode='13821' and specialcircumstancetype='Cheating' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'MO'),
sp as (select id from specialcircumstance where cedscode='13830' and specialcircumstancetype='Other reason for ineligibility' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'MO'),
sp as (select id from specialcircumstance where cedscode='13835' and specialcircumstancetype='Administration or system failure' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'MO'),
sp as (select id from specialcircumstance where cedscode='13836' and specialcircumstancetype='Teacher cheating or mis-admin' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'MO'),
sp as (select id from specialcircumstance where cedscode='9999' and specialcircumstancetype='Other' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

-- CO state special circustance codes -- 
with org as (select org.id from organization org where org.displayidentifier = 'CO'),
sp as (select id from specialcircumstance where cedscode='3454' and specialcircumstancetype='Medical Waiver' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'CO'),
sp as (select id from specialcircumstance where cedscode='13815' and specialcircumstancetype='Home schooled for assessed subjects' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'CO'),
sp as (select id from specialcircumstance where cedscode='13818' and specialcircumstancetype='Special treatment center' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'CO'),
sp as (select id from specialcircumstance where cedscode='13820' and specialcircumstancetype='Parent refusal' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'CO'),
sp as (select id from specialcircumstance where cedscode='13824' and specialcircumstancetype='Homebound' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'CO'),
sp as (select id from specialcircumstance where cedscode='13826' and specialcircumstancetype='Student refusal' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'CO'),
sp as (select id from specialcircumstance where cedscode='13831' and specialcircumstancetype='Other reason for nonparticipation' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'CO'),
sp as (select id from specialcircumstance where cedscode='13832' and specialcircumstancetype='Left testing' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'CO'),
sp as (select id from specialcircumstance where cedscode='13836' and specialcircumstancetype='Teacher cheating or mis-admin' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'CO'),
sp as (select id from specialcircumstance where cedscode='09999' and specialcircumstancetype='Other' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

-- Iowa State
with org as (select org.id from organization org where org.displayidentifier = 'IA'),
sp as (select id from specialcircumstance where cedscode='13824' and specialcircumstancetype='Homebound' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

-- Vermont state
with org as (select org.id from organization org where org.displayidentifier = 'VT'),
sp as (select id from specialcircumstance where cedscode='3454' and specialcircumstancetype='Medical Waiver' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

-- Wisconsin state
with org as (select org.id from organization org where org.displayidentifier = 'WI'),
sp as (select id from specialcircumstance where cedscode='3454' and specialcircumstancetype='Medical Waiver' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'WI'),
sp as (select id from specialcircumstance where cedscode='13831' and specialcircumstancetype='Other reason for nonparticipation' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;
with org as (select org.id from organization org where org.displayidentifier = 'WI'),
sp as (select id from specialcircumstance where cedscode='9999' and specialcircumstancetype='Other' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'WI'),
sp as (select id from specialcircumstance where cedscode='13820' and specialcircumstancetype='Parent refusal' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

-- North Dakota state
with org as (select org.id from organization org where org.displayidentifier = 'ND'),
sp as (select id from specialcircumstance where cedscode='13814' and specialcircumstancetype='Catastrophic illness or accident' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'ND'),
sp as (select id from specialcircumstance where cedscode='13820' and specialcircumstancetype='Parent refusal' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'ND'),
sp as (select id from specialcircumstance where cedscode='13826' and specialcircumstancetype='Student refusal' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'ND'),
sp as (select id from specialcircumstance where cedscode='9999' and specialcircumstancetype='Other' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

-- New Jersey state
with org as (select org.id from organization org where org.displayidentifier = 'NJ'),
sp as (select id from specialcircumstance where cedscode='3454' and specialcircumstancetype='Medical Waiver' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NJ'),
sp as (select id from specialcircumstance where cedscode='13813' and specialcircumstancetype='Chronic absences' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NJ'),
sp as (select id from specialcircumstance where cedscode='13814' and specialcircumstancetype='Catastrophic illness or accident' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NJ'),
sp as (select id from specialcircumstance where cedscode='13815' and specialcircumstancetype='Home schooled for assessed subjects' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NJ'),
sp as (select id from specialcircumstance where cedscode='13819' and specialcircumstancetype='Student took this grade level assessment last year' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NJ'),
sp as (select id from specialcircumstance where cedscode='13820' and specialcircumstancetype='Parent refusal' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NJ'),
sp as (select id from specialcircumstance where cedscode='13824' and specialcircumstancetype='Homebound' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NJ'),
sp as (select id from specialcircumstance where cedscode='13830' and specialcircumstancetype='Other reason for ineligibility' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NJ'),
sp as (select id from specialcircumstance where cedscode='13831' and specialcircumstancetype='Other reason for nonparticipation' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NJ'),
sp as (select id from specialcircumstance where cedscode='13832' and specialcircumstancetype='Left testing' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NJ'),
sp as (select id from specialcircumstance where cedscode='13836' and specialcircumstancetype='Teacher cheating or mis-admin' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NJ'),
sp as (select id from specialcircumstance where cedscode='13837' and specialcircumstancetype='Fire alarm' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NJ'),
sp as (select id from specialcircumstance where cedscode='9999' and specialcircumstancetype='Other' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;



-- West Virginia state
with org as (select org.id from organization org where org.displayidentifier = 'WV'),
sp as (select id from specialcircumstance where cedscode='13814' and specialcircumstancetype='Catastrophic illness or accident' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'WV'),
sp as (select id from specialcircumstance where cedscode='13820' and specialcircumstancetype='Parent refusal' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

-- Utah state

with org as (select org.id from organization org where org.displayidentifier = 'UT'),
sp as (select id from specialcircumstance where cedscode='3454' and specialcircumstancetype='Medical Waiver' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'UT'),
sp as (select id from specialcircumstance where cedscode='13813' and specialcircumstancetype='Chronic absences' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'UT'),
sp as (select id from specialcircumstance where cedscode='13816' and specialcircumstancetype='Student took this grade level assessment last year' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'UT'),
sp as (select id from specialcircumstance where cedscode='13820' and specialcircumstancetype='Parent refusal' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;


with org as (select org.id from organization org where org.displayidentifier = 'NY'),
sp as (select id from specialcircumstance where cedscode='13813' and specialcircumstancetype='Chronic absences' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NY'),
sp as (select id from specialcircumstance where cedscode='09999' and specialcircumstancetype='Other' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NY'),
sp as (select id from specialcircumstance where cedscode='13836' and specialcircumstancetype='Teacher cheating or mis-admin' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NY'),
sp as (select id from specialcircumstance where cedscode='13831' and specialcircumstancetype='Other reason for nonparticipation' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NY'),
sp as (select id from specialcircumstance where cedscode='03454' and specialcircumstancetype='Medical Waiver' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;

with org as (select org.id from organization org where org.displayidentifier = 'NY'),
sp as (select id from specialcircumstance where cedscode='13820' and specialcircumstancetype='Parent refusal' and activeflag is true),
usr as (select id from aartuser where username='cetesysadmin')
insert into statespecialcircumstance (stateid, specialcircumstanceid, createduser, modifieduser)
select org.id, sp.id, usr.id, usr.id from org, sp, usr;


--changes from changpond
-- User story US17187
INSERT INTO authorities(
             authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('HIGH_STAKES_SPL_CIRCUM_CODE', 'Apply Special Circumstance Code','Test Management-High Stakes', current_timestamp,(Select id from aartuser where username='cetesysadmin'), 
            true, current_timestamp,(Select id from aartuser where username='cetesysadmin'));

INSERT INTO authorities(
             authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('HIGH_STAKES_SPL_CIRCUM_CODE_SEL', 'Approve Special Circumstance Code Selection','Test Management-High Stakes', current_timestamp,(Select id from aartuser where username='cetesysadmin'), 
            true, current_timestamp,(Select id from aartuser where username='cetesysadmin'));
		
INSERT INTO authorities(
             authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('DATA_EXTRACT_SPL_CIRCUM_CODE_REP', 'Download Restricted SPL Circumstance Code Report','Reports-Data Extracts', current_timestamp,(Select id from aartuser where username='cetesysadmin'), 
            true, current_timestamp,(Select id from aartuser where username='cetesysadmin'));