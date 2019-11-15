--US17597 : PROD - Request to have student enrollments corrected that cannot be corrected with KSDE uploads

update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17597'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '7033'
  and s.statestudentidentifier = '9749959299'
);

update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17597'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '7033'
  and s.statestudentidentifier = '8019650075'
);

update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17597'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '7033'
  and s.statestudentidentifier = '3599728615'
);

update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17597'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '7033'
  and s.statestudentidentifier = '7293245327'
);

update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17597'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '7020'
  and s.statestudentidentifier = '1827820837'
);

update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17597'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '7020'
  and s.statestudentidentifier = '5936915638'
);

update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17597'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '7725'
  and s.statestudentidentifier = '6502584004'
);

update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17597'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '4318'
  and s.statestudentidentifier = '6166475608'
);

update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17597'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '1422'
  and s.statestudentidentifier = '3765512664'
);


