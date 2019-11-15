update organization
set displayidentifier = '0391413160',
organizationname = 'Westport MS',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '1060044050'
and id in (select id from organization_children((select id from organization where displayidentifier = 'MO')));