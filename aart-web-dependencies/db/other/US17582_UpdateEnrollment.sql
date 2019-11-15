--US17582 : PROD - Request to have student enrollments corrected that cannot be corrected with KSDE uploads

update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17582'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '7620'
  and s.statestudentidentifier = '1362321362'
);


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17582'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '7616'
  and s.statestudentidentifier = '6676515458'
);

update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17582'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '7616'
  and s.statestudentidentifier = '3598574673'
);


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17582'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '4952'
  and s.statestudentidentifier = '4809700607'
);

update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17582'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '6756'
  and s.statestudentidentifier = '3028281031'
);



update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17582'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '7620'
  and s.statestudentidentifier = '4696414817'
);
