--Clear the existing data
truncate table pnpstatesettings;

--KAPDLM 
---Display Enhancements KAP/KELPA/CPASS -- Masking is not showing for kelpa2 by default 
insert into pnpstatesettings
select distinct --piac.attributecontainer,pia.attributename,
sub.organizationid stateid, pianc.id pinacid,'eanble' viewoption,sub.assessmentprogramid assessmentprogramid
,now() createddate,(select id from aartuser where email='ats_dba_team@ku.edu') createduser, true activeflag,now() modifieddate,(select id from aartuser where email='ats_dba_team@ku.edu') modifieduser
FROM profileitemattributenameattributecontainer pianc 
LEFT JOIN profileitemattribute pia ON pianc.attributenameid = pia.id
LEFT JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
cross join ( select o.id organizationid,o.organizationname,a.id assessmentprogramid from orgassessmentprogram orga 
	 inner join  organization o on o.id=orga.organizationid
	 inner join assessmentprogram a on a.id=orga.assessmentprogramid 
	 where a.abbreviatedname  in ('KELPA2','DLM','I-SMART') and orga.activeflag is true and o.activeflag is true   and organizationtypeid=2 ) sub
where  attributecontainer='Masking'
and not exists (select 1 from pnpstatesettings tgt where tgt.assessmentprogramid=sub.assessmentprogramid and pianc.id=tgt.pinacid and sub.organizationid=tgt.stateid);

---Display Enhancements DLM and I-SMART
-- insert into pnpstatesettings
-- select distinct --piac.attributecontainer,pia.attributename,
-- sub.organizationid stateid, pianc.id pinacid,'hide' viewoption,sub.assessmentprogramid assessmentprogramid
-- ,now() createddate,(select id from aartuser where email='ats_dba_team@ku.edu') createduser, true activeflag,now() modifieddate,(select id from aartuser where email='ats_dba_team@ku.edu') modifieduser
-- FROM profileitemattributenameattributecontainer pianc 
-- LEFT JOIN profileitemattribute pia ON pianc.attributenameid = pia.id
-- LEFT JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
-- cross join ( select o.id organizationid,o.organizationname,a.id assessmentprogramid from orgassessmentprogram orga 
-- 	 inner join  organization o on o.id=orga.organizationid
-- 	 inner join assessmentprogram a on a.id=orga.assessmentprogramid 
-- 	 where a.abbreviatedname  in ('DLM','I-SMART') and orga.activeflag is true and o.activeflag is true   and organizationtypeid=2 ) sub
-- where  attributecontainer='Masking'
-- and not exists (select 1 from pnpstatesettings tgt where tgt.assessmentprogramid=sub.assessmentprogramid and pianc.id=tgt.pinacid and sub.organizationid=tgt.stateid);


---Language & Braille 
insert into pnpstatesettings
select distinct-- piac.attributecontainer,pia.attributename,
sub.organizationid stateid, pianc.id pinacid,'eanble' viewoption,sub.assessmentprogramid assessmentprogramid
,now() createddate,(select id from aartuser where email='ats_dba_team@ku.edu') createduser, true activeflag,now() modifieddate,(select id from aartuser where email='ats_dba_team@ku.edu') modifieduser
FROM profileitemattributenameattributecontainer pianc 
LEFT JOIN profileitemattribute pia ON pianc.attributenameid = pia.id
LEFT JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
cross join ( select o.id organizationid,o.organizationname,a.id assessmentprogramid from orgassessmentprogram orga 
	 inner join  organization o on o.id=orga.organizationid
	 inner join assessmentprogram a on a.id=orga.assessmentprogramid 
	 where a.abbreviatedname  in ('KAP','KELPA2','CPASS','I-SMART','DLM') and orga.activeflag is true and o.activeflag is true   and organizationtypeid=2 ) sub
where  attributecontainer='Braille' and pia.attributename in ('ebaeFileType','uebFileType')
and not exists (select 1 from pnpstatesettings tgt where tgt.assessmentprogramid=sub.assessmentprogramid and pianc.id=tgt.pinacid and sub.organizationid=tgt.stateid);


insert into pnpstatesettings
select distinct --piac.attributecontainer,pia.attributename,
sub.organizationid stateid, pianc.id pinacid,'hide' viewoption,sub.assessmentprogramid assessmentprogramid
,now() createddate,(select id from aartuser where email='ats_dba_team@ku.edu') createduser, true activeflag,now() modifieddate,(select id from aartuser where email='ats_dba_team@ku.edu') modifieduser
FROM profileitemattributenameattributecontainer pianc 
LEFT JOIN profileitemattribute pia ON pianc.attributenameid = pia.id
LEFT JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
cross join ( select o.id organizationid,o.organizationname,a.id assessmentprogramid from orgassessmentprogram orga 
	 inner join  organization o on o.id=orga.organizationid
	 inner join assessmentprogram a on a.id=orga.assessmentprogramid 
	 where a.abbreviatedname  in ('CPASS','KELPA2') and orga.activeflag is true and o.activeflag is true   and organizationtypeid=2 ) sub
where  attributecontainer='Braille' and pia.attributename in ('brailleMark','brailleGrade','brailleStatusCell','numberOfBrailleCells','numberOfBrailleDots','usage','brailleDotPressure','activateByDefault')
and not exists (select 1 from pnpstatesettings tgt where tgt.assessmentprogramid=sub.assessmentprogramid and pianc.id=tgt.pinacid and sub.organizationid=tgt.stateid);


insert into pnpstatesettings
select distinct --piac.attributecontainer,pia.attributename,
sub.organizationid stateid, pianc.id pinacid,'eanble' viewoption,sub.assessmentprogramid assessmentprogramid
,now() createddate,(select id from aartuser where email='ats_dba_team@ku.edu') createduser, true activeflag,now() modifieddate,(select id from aartuser where email='ats_dba_team@ku.edu') modifieduser
FROM profileitemattributenameattributecontainer pianc 
LEFT JOIN profileitemattribute pia ON pianc.attributenameid = pia.id
LEFT JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
cross join ( select o.id organizationid,o.organizationname,a.id assessmentprogramid from orgassessmentprogram orga 
	 inner join  organization o on o.id=orga.organizationid
	 inner join assessmentprogram a on a.id=orga.assessmentprogramid 
	 where a.abbreviatedname  in ('KELPA2') and orga.activeflag is true and o.activeflag is true   and organizationtypeid=2 ) sub
where  attributecontainer in ('keywordTranslationDisplay','Signing') --and pia.attributename in ('ebaeFileType','uebFileType')
and not exists (select 1 from pnpstatesettings tgt where tgt.assessmentprogramid=sub.assessmentprogramid and pianc.id=tgt.pinacid and sub.organizationid=tgt.stateid);

insert into pnpstatesettings
select distinct --piac.attributecontainer,pia.attributename,
sub.organizationid stateid, pianc.id pinacid,'hide' viewoption,sub.assessmentprogramid assessmentprogramid
,now() createddate,(select id from aartuser where email='ats_dba_team@ku.edu') createduser, true activeflag,now() modifieddate,(select id from aartuser where email='ats_dba_team@ku.edu') modifieduser
FROM profileitemattributenameattributecontainer pianc 
LEFT JOIN profileitemattribute pia ON pianc.attributenameid = pia.id
LEFT JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
cross join ( select o.id organizationid,o.organizationname,a.id assessmentprogramid from orgassessmentprogram orga 
	 inner join  organization o on o.id=orga.organizationid
	 inner join assessmentprogram a on a.id=orga.assessmentprogramid 
	 where a.abbreviatedname  in ('I-SMART') and orga.activeflag is true and o.activeflag is true   and organizationtypeid=2 ) sub
where  attributecontainer='Braille' and pia.attributename in ('brailleMark','brailleGrade','brailleStatusCell','numberOfBrailleCells','numberOfBrailleDots','usage','brailleDotPressure','activateByDefault')
and not exists (select 1 from pnpstatesettings tgt where tgt.assessmentprogramid=sub.assessmentprogramid and pianc.id=tgt.pinacid and sub.organizationid=tgt.stateid);


--Audio and Environment Support
insert into pnpstatesettings
select distinct --piac.attributecontainer,pia.attributename,nonselectedvalue,
sub.organizationid stateid, pianc.id pinacid,'hide' viewoption,sub.assessmentprogramid assessmentprogramid
,now() createddate,(select id from aartuser where email='ats_dba_team@ku.edu') createduser, true activeflag,now() modifieddate,(select id from aartuser where email='ats_dba_team@ku.edu') modifieduser
FROM profileitemattributenameattributecontainer pianc 
LEFT JOIN profileitemattribute pia ON pianc.attributenameid = pia.id
LEFT JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
cross join ( select o.id organizationid,o.organizationname,a.id assessmentprogramid from orgassessmentprogram orga 
	 inner join  organization o on o.id=orga.organizationid
	 inner join assessmentprogram a on a.id=orga.assessmentprogramid 
	 where a.abbreviatedname  in ('I-SMART','DLM') and orga.activeflag is true and o.activeflag is true   and organizationtypeid=2 ) sub
where  attributecontainer='Spoken' and pia.attributename in ('activateByDefault','ReadAtStartPreference','directionsOnly')
and not exists (select 1 from pnpstatesettings tgt where tgt.assessmentprogramid=sub.assessmentprogramid and pianc.id=tgt.pinacid and sub.organizationid=tgt.stateid);


insert into pnpstatesettings
select distinct --piac.attributecontainer,pia.attributename,nonselectedvalue,
sub.organizationid stateid, pianc.id pinacid,'hide' viewoption,sub.assessmentprogramid assessmentprogramid
,now() createddate,(select id from aartuser where email='ats_dba_team@ku.edu') createduser, true activeflag,now() modifieddate,(select id from aartuser where email='ats_dba_team@ku.edu') modifieduser
FROM profileitemattributenameattributecontainer pianc 
LEFT JOIN profileitemattribute pia ON pianc.attributenameid = pia.id
LEFT JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
cross join ( select o.id organizationid,o.organizationname,a.id assessmentprogramid from orgassessmentprogram orga 
	 inner join  organization o on o.id=orga.organizationid
	 inner join assessmentprogram a on a.id=orga.assessmentprogramid 
	 where a.abbreviatedname  in ('CPASS','KELPA2') and orga.activeflag is true and o.activeflag is true   and organizationtypeid=2 ) sub
where  attributecontainer='Spoken' and pia.attributename in ('directionsOnly','preferenceSubject')
and not exists (select 1 from pnpstatesettings tgt where tgt.assessmentprogramid=sub.assessmentprogramid and pianc.id=tgt.pinacid and sub.organizationid=tgt.stateid);

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--PLTW 


---Language & Braille 
insert into pnpstatesettings
select distinct-- piac.attributecontainer,pia.attributename,
sub.organizationid stateid, pianc.id pinacid,'eanble' viewoption,sub.assessmentprogramid assessmentprogramid
,now() createddate,(select id from aartuser where email='ats_dba_team@ku.edu') createduser, true activeflag,now() modifieddate,(select id from aartuser where email='ats_dba_team@ku.edu') modifieduser
FROM profileitemattributenameattributecontainer pianc 
LEFT JOIN profileitemattribute pia ON pianc.attributenameid = pia.id
LEFT JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
cross join ( select o.id organizationid,o.organizationname,a.id assessmentprogramid from orgassessmentprogram orga 
	 inner join  organization o on o.id=orga.organizationid
	 inner join assessmentprogram a on a.id=orga.assessmentprogramid 
	 where a.abbreviatedname  in ('PLTW') and orga.activeflag is true and o.activeflag is true   and organizationtypeid=2 ) sub
where  attributecontainer='Braille' and pia.attributename in ('ebaeFileType','uebFileType')
and not exists (select 1 from pnpstatesettings tgt where tgt.assessmentprogramid=sub.assessmentprogramid and pianc.id=tgt.pinacid and sub.organizationid=tgt.stateid);

-- select * from profileitemattribute where attributename = 'SpokenSourcePreference' or attributename = 'UserSpokenPreference';			
-- select * from profileItemAttributenameAttributeContainer where attributenameid in (59,61);
-- select * from profileitemattrnameattrcontainerviewoptions WHERE pianacid in (4,6) order by pianacid;


insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption , createddate, createduser, activeflag, modifieddate ,modifieduser)
select distinct pia.id pianacid, sub.assessmentprogramid  assessmentprogramid,'disable_human' viewoption ,now() createddate,(select id from aartuser where email='ats_dba_team@ku.edu') createduser
,true activeflag,now() modifieddate ,(select id from aartuser where email='ats_dba_team@ku.edu') modifieduser from profileitemattribute pi 
inner join profileItemAttributenameAttributeContainer pia on pia.attributenameid=pi.id
cross join ( select a.id assessmentprogramid from orgassessmentprogram orga 
	 inner join  organization o on o.id=orga.organizationid
	 inner join assessmentprogram a on a.id=orga.assessmentprogramid 
	 where a.abbreviatedname  in ('CPASS','KELPA2','DLM','I-SMART','PLTW') and orga.activeflag is true and o.activeflag is true   and organizationtypeid=2 ) sub
where (pi.attributename = 'SpokenSourcePreference' )
and not exists (select 1 from profileitemattrnameattrcontainerviewoptions tgt where tgt.assessmentprogramid =sub.assessmentprogramid and tgt.viewoption ='disable_human' and tgt.pianacid =pia.id );


delete from profileitemattrnameattrcontainerviewoptions where id in (
select distinct piav.id 
from profileitemattribute pi 
inner join profileItemAttributenameAttributeContainer pia on pia.attributenameid=pi.id
inner join profileitemattrnameattrcontainerviewoptions piav on piav.pianacid=pia.id
join ( select a.id assessmentprogramid from orgassessmentprogram orga 
	 inner join  organization o on o.id=orga.organizationid
	 inner join assessmentprogram a on a.id=orga.assessmentprogramid 
	 where a.abbreviatedname  in ('CPASS','KELPA2','I-SMART2','PLTW') and orga.activeflag is true and o.activeflag is true   and organizationtypeid=2 ) sub on sub.assessmentprogramid=piav.assessmentprogramid
where (pi.attributename = 'UserSpokenPreference'));

insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption , createddate, createduser, activeflag, modifieddate ,modifieduser)
select distinct pia.id pianacid, sub.assessmentprogramid  assessmentprogramid,'disable_graphicsonly' viewoption ,now() createddate,(select id from aartuser where email='ats_dba_team@ku.edu') createduser
,true activeflag,now() modifieddate ,(select id from aartuser where email='ats_dba_team@ku.edu') modifieduser from profileitemattribute pi 
inner join profileItemAttributenameAttributeContainer pia on pia.attributenameid=pi.id
cross join ( select a.id assessmentprogramid from orgassessmentprogram orga 
	 inner join  organization o on o.id=orga.organizationid
	 inner join assessmentprogram a on a.id=orga.assessmentprogramid 
	 where a.abbreviatedname  in ('CPASS','KELPA2','DLM','I-SMART','I-SMART2','PLTW') and orga.activeflag is true and o.activeflag is true   and organizationtypeid=2 ) sub
where (pi.attributename = 'UserSpokenPreference' )
and not exists (select 1 from profileitemattrnameattrcontainerviewoptions tgt where tgt.assessmentprogramid =sub.assessmentprogramid and tgt.viewoption ='disable_graphicsonly' and tgt.pianacid =pia.id );


--  select  pia.id pianacid, assessmentprogramid  assessmentprogramid,viewoption from profileitemattribute pi
--  inner join profileItemAttributenameAttributeContainer pia on pia.attributenameid=pi.id
--  inner join profileitemattrnameattrcontainerviewoptions piav on piav.pianacid=pia.id
--  where (pi.attributename = 'SpokenSourcePreference' or pi.attributename = 'UserSpokenPreference') and assessmentprogramid=2;


