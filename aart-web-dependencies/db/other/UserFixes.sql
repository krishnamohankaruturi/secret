--run this sql first, it has to return  21 rows.

select distinct au.id,(select g.id from groups g where defaultrole = 't' and g.organizationid=e.attendanceschoolid) as groupid,0 as status
 from aartuser au,roster r,enrollmentsrosters er, enrollment e,organization o
   where e.id = er.enrollmentid and r.id = er.rosterid and r.teacherid=au.id and o.id = e.attendanceschoolid
    and au.id not in (select aartuserid from usergroups);

    begin;
    
insert into usergroups (aartuserid,groupid,status)
(
select distinct au.id,(select g.id from groups g where defaultrole = 't' and g.organizationid=e.attendanceschoolid) as groupid,0 as status
 from aartuser au,roster r,enrollmentsrosters er, enrollment e,organization o
   where e.id = er.enrollmentid and r.id = er.rosterid and r.teacherid=au.id and o.id = e.attendanceschoolid
    and au.id not in (select aartuserid from usergroups)
);

--the number of inserts must be 21. rollback otherwise.

    