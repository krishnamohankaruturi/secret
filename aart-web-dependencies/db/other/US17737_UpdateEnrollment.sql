--US17737 : PROD - Request to have student enrollments corrected that cannot be corrected with KSDE uploads

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '7358' and s.statestudentidentifier = '3249772933') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '7358' and s.statestudentidentifier = '6118784302') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '7358' and s.statestudentidentifier = '1512579785') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '7358' and s.statestudentidentifier = '8855247638') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '8110' and s.statestudentidentifier = '7244259808') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '3794' and s.statestudentidentifier = '7337625146') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '0865' and s.statestudentidentifier = '1591983517') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '7143' and s.statestudentidentifier = '2729489231') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '1817' and s.statestudentidentifier = '5313114182') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '7730' and s.statestudentidentifier = '9454541986') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '0193' and s.statestudentidentifier = '2974918921') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '2584' and s.statestudentidentifier = '9544564829') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '0808' and s.statestudentidentifier = '6636478535') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '9097' and s.statestudentidentifier = '5541175631') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '8002' and s.statestudentidentifier = '7522759226') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '4322' and s.statestudentidentifier = '6218639541') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '4819' and s.statestudentidentifier = '4330369788') and activeflag is false; 


update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17737' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '5770' and s.statestudentidentifier = '5195181691') and activeflag is false; 


update enrollment
set activeflag = false,
exitwithdrawaldate = now(),
exitwithdrawaltype = -55,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17737'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '2034'
  and s.statestudentidentifier = '9655686124'
);



update enrollment
set activeflag = false,
exitwithdrawaldate = now(),
exitwithdrawaltype = -55,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17737'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '1986'
  and s.statestudentidentifier = '7472259691'
);



update enrollment
set activeflag = false,
exitwithdrawaldate = now(),
exitwithdrawaltype = -55,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
notes = 'Activated enrollment as per US17737'
where id = (
  select e.id
  from enrollment e
  inner join student s on e.studentid = s.id
  inner join organization o on e.attendanceschoolid = o.id
  where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
  and s.stateid = (select id from organization where displayidentifier = 'KS')
  and o.displayidentifier = '1977'
  and s.statestudentidentifier = '3138836917'
);




















