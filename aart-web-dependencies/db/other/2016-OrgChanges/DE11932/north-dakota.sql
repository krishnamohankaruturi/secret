update organizationrelation
set parentorganizationid = 79261,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where organizationid = 63829;