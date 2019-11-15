--US17663 : PROD - Request to have student enrollments corrected that cannot be corrected with KSDE uploads

update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17663'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '4639'
  and s.statestudentidentifier = '6435682356'
);


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17663'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '7026'
  and s.statestudentidentifier = '3487798972'
);


update enrollment
set activeflag = true,
exitwithdrawaldate = null,
exitwithdrawaltype = null,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17663'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '4322'
  and s.statestudentidentifier = '9028854541'
);



update enrollment
set activeflag = false,
exitwithdrawaldate = now(),
exitwithdrawaltype = -55,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17663'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '8603'
  and s.statestudentidentifier = '5937284895'
);



