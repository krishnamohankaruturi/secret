-- InActivate existing assigned tests(Math, ELA) for student : 9429917951 

update studentsresponses  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestsid in (11933396,12322477,11933395, 11933261, 11933262, 12235692,12095137);

update studentstestsections  set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid in (11933396,12322477,11933395, 11933261, 11933262, 12235692,12095137);

update studentstests set activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id in (11933396,12322477,11933395, 11933261, 11933262, 12235692,12095137);
	
--Stage 2 Math
update studentstests set activeflag = false, enrollmentid = 2386426, testsessionid = 2504393, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id =	12095137;
	
update studentstests set enrollmentid = 2065293 , testsessionid = 2504382, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id =	12102788;
	
	

