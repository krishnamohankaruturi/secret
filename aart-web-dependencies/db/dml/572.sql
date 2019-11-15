-- 572.sql dml
with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'GSAD') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'HS') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

-- User assessment program/permissions related data set up
select populateuserassessmentprograms();
select resetdefaultuserassessmentprogram();

-- deactivate old records.
update userassessmentprogram set activeflag = false where userorganizationsgroupsid is  null;

-- deactivate old records.
update groupauthorities set activeflag = false where groupid not in (select id from groups where groupcode='GSAD')
and organizationid is null;
