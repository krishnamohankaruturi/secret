--Student ID to remove - 5917991758, Student ID to keep - 1691570737 -- merge tests

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (10079117,10442244, 10079110, 12536162, 8773024, 8741166);
	
update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id in (10079117,10442244, 10079110, 12536162, 8773024, 8741166);

UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'inactivated according to US17815'
  where id = 2288855;
	
UPDATE student SET activeflag = false, modifieddate= now(), modifieduser = 12 WHERE id = 1277045;

--Move completed performance tests to new studentid 

UPDATE studentstests SET enrollmentid = 2198045, studentid = 890088, testsessionid = 2179699, modifieddate= now(), modifieduser = 12 WHERE id = 8869939;
UPDATE studentstests SET enrollmentid = 2198045, studentid = 890088, testsessionid = 2179445, modifieddate= now(), modifieduser = 12 WHERE id = 8828774;
