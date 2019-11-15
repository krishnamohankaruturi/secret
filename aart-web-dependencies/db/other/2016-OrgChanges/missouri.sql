-- delete Kirksville Area Tech Ctr
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 42598;

-- change name of 1060044040 to Cedar Ridge Elementary
update organization
set organizationname = 'Cedar Ridge Elementary',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 18084;

-- change name of 1060044070 to Buchanan Intermediate
update organization
set organizationname = 'Buchanan Intermediate',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 42554;

-- delete East Elementary
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 69343;

-- change other East Elementary
update organization
set displayidentifier = '0160904140',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 69344;
