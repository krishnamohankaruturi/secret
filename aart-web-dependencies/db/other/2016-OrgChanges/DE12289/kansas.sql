-- all schools in district D0442 need to be removed
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id in (
  select id from organization_children((select id from organization where displayidentifier = 'D0442'))
);