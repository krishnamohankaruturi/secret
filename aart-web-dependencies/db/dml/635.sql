--DE15220

--with the change to the exit process to only inactivate the enrollment, we must change existing enrollmentsrosters 
--related to exited enrollments to be active again in case the enrollment exit is reversed later this year.  
--This will allow the enrollment rosters to be available as soon as a reversal occurs.
update enrollmentsrosters set activeflag=true, modifieddate=now(), modifieduser=12 where id in  (
 select er.id
 from enrollment e 
 join enrollmentsrosters er on er.enrollmentid=e.id 
 join roster r on r.id=er.rosterid
 join contentarea ca on r.statesubjectareaid=ca.id and ca.abbreviatedname='ELP'
 where e.currentschoolyear=2017 
 	and e.exitwithdrawaldate is not null 
 	and e.exitwithdrawaltype<>0 
 	and er.activeflag is false 
 	and e.activeflag is false);


--fix data where the exitwithdrawaldate was not set to null when the exit reversal was processed
update enrollment set exitwithdrawaldate=null, modifieddate=now(), modifieduser=12 where id in (
 select e.id 
 from enrollment e 
 where e.currentschoolyear=2017 
 	and e.exitWithdrawalType=0 
 	and e.exitWithdrawalDate is not null 
 	and e.activeflag is true);
 	
-- Defines the hierarchy which enables who can update users below their level
UPDATE groups  SET hierarchy =10 where groupname='HS';
UPDATE groups  SET hierarchy =10 where groupname='Global System Administrator';
UPDATE groups  SET hierarchy =20 where groupname='PD Admin';
UPDATE groups  SET hierarchy =20 where groupname='Scoring State Lead';
UPDATE groups  SET hierarchy =20 where groupname='State System Administrator';
UPDATE groups  SET hierarchy =20 where groupname='Consortium Assessment Program Administrator';
UPDATE groups  SET hierarchy =20 where groupname='State Assessment Administrator';
UPDATE groups  SET hierarchy =20 where groupname='Test Administrator (QC Person)';
UPDATE groups  SET hierarchy =20 where groupname='PD State Admin';
UPDATE groups  SET hierarchy =30 where groupname='PD District Admin';
UPDATE groups  SET hierarchy =30 where groupname='Scoring District Lead';
UPDATE groups  SET hierarchy =30 where groupname='District Test Coordinator';
UPDATE groups  SET hierarchy =31 where groupname='District User';
UPDATE groups  SET hierarchy =30 where groupname='District Superintendent';
UPDATE groups  SET hierarchy =40 where groupname='Building Test Coordinator';
UPDATE groups  SET hierarchy =40 where groupname='Technology Director';
UPDATE groups  SET hierarchy =40 where groupname='Building User';
UPDATE groups  SET hierarchy =40 where groupname='Scoring Building Lead';
UPDATE groups  SET hierarchy =40 where groupname='Building Principal';
UPDATE groups  SET hierarchy =40 where groupname='State Scorer';
UPDATE groups  SET hierarchy =40 where groupname='Test Proctor';
UPDATE groups  SET hierarchy =40 where groupname='Scorer';
UPDATE groups  SET hierarchy =40 where groupname='PD User';
UPDATE groups  SET hierarchy =40 where groupname='Teacher';
UPDATE groups  SET hierarchy =40 where groupname='Teacher: PNP Read Only';

-- FCS changes
-- inactivate all the questions which are not prerequisites for Q210,Q211_* and Q300_* 
update surveylabelprerequisite set activeflag=false, 
modifieduser=(select id from aartuser where username  = 'cetesysadmin'),
modifieddate=now()
where 
surveyresponseid in (select id from surveyresponse  where labelid in (select id from surveylabel where  labelnumber in ('Q36','Q39')) and responsevalue='Yes')
and surveylabelid  in (
select id from surveylabel  where labelnumber  in ('Q210','Q211_1','Q211_2','Q211_3','Q211_4','Q211_5','Q300_1','Q300_2','Q300_3','Q300_4'));