-- update Robinson Elementary School name and identifier
update organization
set organizationname = 'Robinson 14',
displayidentifier = '2201474874',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 39010;

-- delete Hazen Middle school
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 38807;

-- remove Powers Lake Elementary and High Schools
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id in (38992, 38993);

-- update Powers Lake Public School identifier
update organization
set displayidentifier = '0702771585',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 69350;

-- update users from Powers Lake schools that were merged to the new public school
update usersorganizations
set organizationid = 69350
where organizationid in (38992, 38993);

-- displayidentifier changes
update organization
set displayidentifier = '0900121041',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '21041';

update organization
set displayidentifier = '0900652011',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '52011';

update organization
set displayidentifier = '0940185591',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '85591';

update organization
set displayidentifier = '0900173621',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '73621';

update organization
set displayidentifier = '0950213058',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '13058';

update organization
set displayidentifier = '1800118561',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '18561';

update organization
set displayidentifier = '5100460065',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '60065';

-- change name of Sheyenne 9th Grade Center
update organization
set organizationname = 'SHEYENNE HIGH SCHOOL',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 39041;

-- move Tate Topa Tribal School to the newly created Tate Topa district
update organizationrelation
set parentorganizationid = 79261,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where organizationid = 63829;
