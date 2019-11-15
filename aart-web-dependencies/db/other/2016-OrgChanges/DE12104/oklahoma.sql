update organization
set displayidentifier = '11I035212',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '11I035112'
and id in (select id from organization_children_oftype((select id from organization where displayidentifier = 'OK'), 'SCH'));

update organization
set displayidentifier = '11I035520',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '11I035515'
and id in (select id from organization_children_oftype((select id from organization where displayidentifier = 'OK'), 'SCH'));

update organization
set displayidentifier = '11I035225',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '11I035125'
and id in (select id from organization_children_oftype((select id from organization where displayidentifier = 'OK'), 'SCH'));

update organization
set displayidentifier = '11I035210',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '11I035110'
and id in (select id from organization_children_oftype((select id from organization where displayidentifier = 'OK'), 'SCH'));