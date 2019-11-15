--Updating existing record with correct identifier name. All Tests, FCS and PNP is set on this id.

UPDATE student SET statestudentidentifier = '9683090790', modifieddate = now(), 
modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 1315027;

-- Inactivating unused studentid
UPDATE student SET activeflag = false, modifieddate = now(), 
modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 971320;