update organization
set organizationname = 'Comanche Middle School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 3155;

update organization
set organizationname = 'Soule Elementary School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 3159;
