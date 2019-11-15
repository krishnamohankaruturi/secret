update organization
set organizationname = 'East Mills Jr/Sr High School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 21211;

update enrollment
set attendanceschoolid = 60046,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where attendanceschoolid = 69213;

update roster
set attendanceschoolid = 60046,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where attendanceschoolid = 69213;

update usersorganizations
set organizationid = 60046
where organizationid = 69213;

update enrollment
set attendanceschoolid = 60047,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where attendanceschoolid = 69212;

update roster
set attendanceschoolid = 60047,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where attendanceschoolid = 69212;

update usersorganizations
set organizationid = 60047
where organizationid = 69212;

update organization
set displayidentifier = '150914 436',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 60045;

update organization
set displayidentifier = '150914 236',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 60046;

update organization
set displayidentifier = '150914 136',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 60047;

update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id in (69211, 69212, 69213);
