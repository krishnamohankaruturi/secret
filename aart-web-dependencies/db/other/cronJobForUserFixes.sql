--this part of the query needs to be in a report.

Select now() as runtime;

select distinct au.id,au.username,au.firstname,au.surname,au.email,e.attendanceschoolid,o.*
 from aartuser au,roster r,enrollmentsrosters er, enrollment e,organization o
   where e.id = er.enrollmentid and r.id = er.rosterid and r.teacherid=au.id and o.id = e.attendanceschoolid
    and au.id not in (select aartuserid from usergroups);

--to be run as one transaction.

insert into groups(organizationid,groupname,defaultrole)
(select distinct e.attendanceschoolid,'DEFAULT' as groupname,true as defaultrole
 from aartuser au,roster r,enrollmentsrosters er, enrollment e
   where e.id = er.enrollmentid and r.id = er.rosterid and r.teacherid=au.id
    and au.id not in (select aartuserid from usergroups)    
    and e.attendanceschoolid not in (select organizationid from groups where defaultrole='t'));


insert into usergroups (aartuserid,groupid,status)
(
select distinct au.id,(select g.id from groups g where defaultrole = 't' and g.organizationid=e.attendanceschoolid) as groupid,0 as status
 from aartuser au,roster r,enrollmentsrosters er, enrollment e,organization o
   where e.id = er.enrollmentid and r.id = er.rosterid and r.teacherid=au.id and o.id = e.attendanceschoolid
    and au.id not in (select aartuserid from usergroups)
);

    --end of transaction.