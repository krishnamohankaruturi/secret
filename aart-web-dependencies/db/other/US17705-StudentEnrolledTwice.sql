
-- Inactivate wrong student enrollment
update studentassessmentprogram set activeflag = false where studentid in (1216686);

update enrollmenttesttypesubjectarea set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where enrollmentid in (1884967);	

update enrollment set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentid in (1216686);

-- Inactivate profile attibutes for incorrect student 
update studentprofileitemattributevalue set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentid in (1216686);

--Move students responses from wrong studentid to correct one
update studentsresponses set studentid = 851366, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestsid in (8583155, 9304225,8572140);

--Inactivate tests for wrong student and update with correct id
update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in(9304542,9304859);

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id in(9304542,9304859);
	
update studentstests set studentid = 851366, enrollmentid = 2377565, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id in(8583155,9304225,8572140);	
	
-- Inactivate wrong students
update student set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id in (1216686);
