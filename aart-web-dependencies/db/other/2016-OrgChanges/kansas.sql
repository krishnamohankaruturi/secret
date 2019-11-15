-- delete schools
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id in (5688, 5689, 5693, 9313, 6168);

update organization
set organizationname = 'Council Grove Junior-Senior High',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 6170;

update organization
set organizationname = 'Prairie Heights Elementary',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 6171;
