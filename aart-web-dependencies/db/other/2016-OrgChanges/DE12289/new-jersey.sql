update organization
set organizationname = 'PENNSYLVANIA AVENUE E.S.',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '010110100'
and id in (select id from organization_children((select id from organization where displayidentifier = 'NJ')));