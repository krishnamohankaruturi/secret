-- remove Foundations schools
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id in (64322, 64328);
