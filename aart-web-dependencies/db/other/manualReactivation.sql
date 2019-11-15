--this script moves the users in active state to inactive state.

--insert the default group of the organization for this user.

insert into usergroups(aartuserid,groupid,status)
( 
	Select ug.aartuserid,g2.id as groupid,0 as status from usergroups ug,groups g,groups g2,aartuser au
	 where ug.groupid=g.id and au.id = ug.aartuserid and au.username='testlyles+idtest9@gmail.com'
	 and g2.organizationid = g.organizationid
	 and g.defaultrole <> 't'
	 and g2.defaultrole = 't'
	 and (ug.aartuserid,g2.id) not in (select aartuserid,groupid from usergroups)
);

--delete the non-default groups.

delete from usergroups where (aartuserid,groupid) in 
(Select au.id as aartuserid,g.id as groupid from usergroups ug,groups g,aartuser au
where ug.groupid=g.id and au.id = ug.aartuserid and au.username='testlyles+idtest9@gmail.com'
and g.defaultrole <> 't');