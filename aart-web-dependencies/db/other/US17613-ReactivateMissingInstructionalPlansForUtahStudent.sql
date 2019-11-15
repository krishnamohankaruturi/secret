-- US17613: EP: High - Prod - Reactivate Missing Instructional Plans for Utah Student
-- Activating the enrollmentsroster which have ITI plans for the kid  '2015927'.

update enrollmentsrosters set activeflag = true, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin') 
  where rosterid in (select id from roster where id in (887533,887530) and coursesectionname in ('ZZ Inactive Math','ZZ Inactive ELA') and activeflag is true)
  and enrollmentid in (select en.id from student stu join enrollment en on en.studentid = stu.id
          where stu.statestudentidentifier = '2015927' 
          and stu.stateid = (select id from organization where displayidentifier = 'UT')
          and en.activeflag is true and en.currentschoolyear = 2016);

-- Records going to update: 2.      