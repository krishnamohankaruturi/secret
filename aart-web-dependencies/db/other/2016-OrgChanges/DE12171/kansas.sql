-- This script will update two school names in kansas as requested

update organization 
 set organizationname='Council Grove Junior-Senior High',
 modifieddate = now(),
 modifieduser = (select id from aartuser where username = 'cetesysadmin') 
where id=6169;

update organization 
 set organizationname='Council Grove Elementary',
 modifieddate = now(),
 modifieduser = (select id from aartuser where username = 'cetesysadmin') 
where id=6170;