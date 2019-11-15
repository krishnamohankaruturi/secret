--Educator Email: michelle.harris@cusd4.xom is incorrect should be michelle.harris@cusd4.com  - 
--There are 2 user entries exists, associated with active and inactive orgs. Making one user active with right org.

UPDATE aartuser SET activeflag = false, modifieddate = now(),
	modifieduser = (SELECT id from aartuser where username = 'cetesysadmin') where id = 162225;

UPDATE usersorganizations SET organizationid=79432 WHERE ID in (183414) ;