--prod
select * from organization_children(51) where displayidentifier like '0%';

--prod
Select au.* from aartuser au,usergroups ug,groups g
 where
  au.id = ug.aartuserid and
  g.id = ug.groupid and 
  g.organizationid in (select id from organization_children(51) where displayidentifier like '0%');