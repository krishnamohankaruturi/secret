-- this will update 2 schools, one in Colorado and one in Colorado-cPass
update organization
set organizationname = 'MOFFAT PREK-12 SCHOOL',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier = '28005958';