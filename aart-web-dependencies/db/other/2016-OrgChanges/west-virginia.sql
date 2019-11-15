-- move users from Geneva Kent Elementary School and Peyton Elementary School to Explorer Academy
update usersorganizations
set organizationid = 79262
where organizationid in (15935, 16195);

-- delete Geneva Kent Elementary School and Peyton Elementary School
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id in (15935, 16195);
