update organization
set displayidentifier = '55Z003',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '55Z003000'
and id in (select id from organization_children((select id from organization where displayidentifier = 'OK')));

update organization
set organizationname = 'Oklahoma Connections Academy Elementary School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '55Z003970'
and id in (select id from organization_children((select id from organization where displayidentifier = 'OK')));