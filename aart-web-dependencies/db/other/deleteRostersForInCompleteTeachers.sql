delete from enrollmentsrosters
 where rosterid in (Select r.id from roster r,aartuser au
  where r.teacherid = au.id and au.username=au.email
   and au.username = au.uniquecommonidentifier
    and au.defaultusergroupsid = 0); 

delete from roster
 where teacherid in (Select id from aartuser
  where username=email and username = uniquecommonidentifier
   and defaultusergroupsid = 0); 

delete from usergroups
 where aartuserid in (Select id
  from aartuser where username=email and
   username = uniquecommonidentifier and defaultusergroupsid = 0);

delete from aartuser where username=email and
 username = uniquecommonidentifier and defaultusergroupsid = 0;