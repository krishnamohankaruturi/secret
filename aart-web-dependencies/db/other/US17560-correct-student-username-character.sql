update student
set legalfirstname = 'Josa', username = 'josa.ayal',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where statestudentidentifier = '6527532369' and stateid = (select id from organization where displayidentifier = 'KS');