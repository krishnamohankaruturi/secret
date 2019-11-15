-- change name of PAIDEIA Charter School
update organization
set organizationname = 'PAIDEIA Cooperative School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 69200;

-- change name of Sitka Correspondance
update organization
set organizationname = 'Sitka REACH',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 36390;
