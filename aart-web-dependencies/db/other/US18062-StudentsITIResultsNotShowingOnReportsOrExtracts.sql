-- US18062: EP Prod - Student's ITI results not showing on Reports or Extracts
-- Moving the ITI plans from student 1207436 to 1069571, as student is duplicated with two different statestudentidentifiers. 
-- Both student ids were enrolled in school "SNOW CANYON MIDDLE", with studentid 1207436 has inactive enrollment and stuentid 1069571 has active enrollment.
-- Both student id's were rostered in "DE OLIVEIRA ELA" and "DE OLIVEIRA Math". ITI plans were asssigned to the studentid 1207436. So moving them to studentid 1069571

update studentsresponses_aart set studentid = 1069571, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentid = 1207436;
-- Number of rows going to update : 0


update studentsresponses set studentid = 1069571, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestsid in (select id from studentstests where studentid = 1207436);
-- Number of rows going to update : 48


update studentstests set studentid = 1069571, enrollmentid = (select id from enrollment where studentid = 1069571 and activeflag is true and currentschoolyear = 2016), modifieddate = now(), 
      modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentid = 1207436;
-- Number of rows going to update : 13


update ititestsessionhistory set studentid = 1069571, studentenrlrosterid = 14430545, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentid = 1207436 and rosterid = 889868;
-- Number of rows going to update : 7


update ititestsessionhistory set studentid = 1069571, studentenrlrosterid = 14430544, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')  where studentid = 1207436 and rosterid = 889867;
-- Number of rows going to update : 6


-- Moving the inactive rosters from student id 1207436 to  studentid 1069571
update enrollmentsrosters set enrollmentid = (select id from enrollment where studentid = 1069571 and currentschoolyear = 2016 and activeflag is true),
     modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where enrollmentid = (select id from enrollment where studentid = 1207436 and currentschoolyear = 2016 and activeflag is false) and activeflag is false;
-- Number of rows going to update : 2


-- As student is already existed in the "DE OLIVEIRA ELA" and "DE OLIVEIRA Math" rosters removing the studentid 1207436 from these rosters. As student existed from the school studentid is still on these rosters.
-- Student id 1069571 already on "DE OLIVEIRA ELA" and "DE OLIVEIRA Math" rosters.
update enrollmentsrosters set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where enrollmentid = (select id from enrollment where studentid = 1207436 and currentschoolyear = 2016 and activeflag is false) and activeflag is true;

-- Number of rows going to update : 2       
       
-- Inactivating the studentid 1207436
update student set activeflag = false,  modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
	where id = 1207436;
-- Number of rows going to update : 1


