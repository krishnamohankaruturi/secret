--US17636 : PROD - Request to have student enrollments corrected that cannot be corrected with KSDE uploads

update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '1796'
  and s.statestudentidentifier = '8510186057'
)
and activeflag is false;


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '7616'
  and s.statestudentidentifier = '4941545281'
)
and activeflag is false;

update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '1588'
  and s.statestudentidentifier = '9103229254'
)
and activeflag is false;


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '1588'
  and s.statestudentidentifier = '6677052311'
)
and activeflag is false;

update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '1588'
  and s.statestudentidentifier = '3834050504'
)
and activeflag is false;


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '0523'
  and s.statestudentidentifier = '8737118005'
)
and activeflag is false;


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '1012'
  and s.statestudentidentifier = '5124919884'
)
and activeflag is false;


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '2780'
  and s.statestudentidentifier = '1495966585'
)
and activeflag is false;



update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '2780'
  and s.statestudentidentifier = '5978044643'
)
and activeflag is false;


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '3374'
  and s.statestudentidentifier = '4790288692'
)
and activeflag is false;


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '7616'
  and s.statestudentidentifier = '4941545281'
)
and activeflag is false;


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '7604'
  and s.statestudentidentifier = '8226486998'
)
and activeflag is false;


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '0885'
  and s.statestudentidentifier = '4605604847'
)
and activeflag is false;


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '0885'
  and s.statestudentidentifier = '5562779625'
)
and activeflag is false;


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '3166'
  and s.statestudentidentifier = '9579031533'
)
and activeflag is false;


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '5058'
  and s.statestudentidentifier = '5268694561'
)
and activeflag is false;


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '8710'
  and s.statestudentidentifier = '2530517541'
)
and activeflag is false;


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17636'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '8710'
  and s.statestudentidentifier = '6396704684'
)
and activeflag is false;















