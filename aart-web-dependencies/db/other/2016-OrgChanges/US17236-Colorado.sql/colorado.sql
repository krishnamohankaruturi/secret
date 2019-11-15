update organization
set organizationname = 'CEDAR SPRINGS HOSPITAL',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '99906031'
and id in (
  select id from organization_children((select id from organization where displayidentifier = 'CO'))
  union
  select id from organization_children((select id from organization where displayidentifier = 'CO-cPass'))
);

update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier in ('99906101', '99906109', '99908051', '99906129')
and id in (
  select id from organization_children((select id from organization where displayidentifier = 'CO'))
  union
  select id from organization_children((select id from organization where displayidentifier = 'CO-cPass'))
);