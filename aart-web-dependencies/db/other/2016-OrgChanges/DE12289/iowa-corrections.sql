update organization
set displayidentifier = '574777 427',
organizationname = 'North Linn Elementary',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 21610;

update organization
set displayidentifier = '311863 607',
organizationname = 'Hillcrest/Lawther Academy - Dubuque',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '311863 500'
and id in (select id from organization_children((select id from organization where displayidentifier = 'IA')));

update organization
set organizationname = 'Hillcrest/Lawther Academy - Maquoketa',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '494041 500'
and id in (select id from organization_children((select id from organization where displayidentifier = 'IA')));

-- the following are updates to the school for the deaf to correct their data
update usersorganizations
set organizationid = 58722,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where organizationid = 69223;

update enrollmentsrosters
set activeflag = true,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where enrollmentid in (
  select id from enrollment where attendanceschoolid = 58722 and currentschoolyear = 2016 and modifieduser = (select id from aartuser where username = 'cetesysadmin') 
)
and rosterid in (
  select id from roster where attendanceschoolid = 58722 and currentschoolyear = 2016 and modifieduser = (select id from aartuser where username = 'cetesysadmin')
)
and modifieduser = (select id from aartuser where username = 'cetesysadmin');

update enrollment
set activeflag = true,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where attendanceschoolid = 58722 and currentschoolyear = 2016 and modifieduser = (select id from aartuser where username = 'cetesysadmin');

update roster
set activeflag = true,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where attendanceschoolid = 58722 and currentschoolyear = 2016 and modifieduser = (select id from aartuser where username = 'cetesysadmin');

update organization
set activeflag = true,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 58722;

update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 69223;