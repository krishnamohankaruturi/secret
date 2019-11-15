-- Student ID: 1002339346 , Missing ELA tests for student
 UPDATE studenttracker SET status = 'UNTRACKED', modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()  where id in (321120);
