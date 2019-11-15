----dml/562.sql ==> For ddl/*.sql

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'SAAD') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'SSAD') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;


with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'TAQCP') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'CAPAD') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'SCOSL') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'PDAD') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'SCODL') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'SUP') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'DTC')
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'DUS') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'SCOBL') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'PRO') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'BTC') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'BUS') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'SCO') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'TD') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'SSCO') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'TEA') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'PRN') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

with csap as(select ga.groupid as gid, ga.authorityid as aid, o.id as oid, oa.assessmentprogramid as apid 
from groupauthorities ga, organization o
JOIN orgassessmentprogram oa on oa.organizationid = o.id
where ga.groupid = (select id from groups where groupcode = 'TEAR') and ga.organizationid is null and ga.assessmentprogramid is null
and oa.organizationid in  (select id from organization where organizationtypeid= 2) and o.activeflag = true)
insert into groupauthorities(organizationid,groupid,authorityid,assessmentprogramid,createduser,createddate,modifieduser,modifieddate)
select csap.oid,csap.gid,csap.aid,csap.apid,(select id from aartuser where username='cetesysadmin'),now(),(select id from aartuser where username='cetesysadmin'),now() from csap;

