-- US17613: EP: High - Prod - Reactivate Missing Instructional Plans for Utah Student Part 2
-- New request came in the user story.
-- Both Nicholas Kim (2159677) and David Dillon (1670414) were exited from the school and added back to the school. At the time of exiting school students didn't finished any of their ITI plans, 
-- so all the plans and students tests were inactivated. When they added back to the school system won't show plans which were inactivated. Script will activate the studentstests, studenttestsections, testsessions and ITI plans.
-- For kid  Nicholas Kim (2159677)

update studentstestsections set activeflag = true, statusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'unused'),
       modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now(), startdatetime = null
       where studentstestid in (select id from studentstests  where testsessionid in (select ts.id from testsession ts join ititestsessionhistory itsh on itsh.testsessionid = ts.id join student stu on stu.id = itsh.studentid 
            where stu.statestudentidentifier = '2159677' and stu.stateid = (select id from organization where displayidentifier = 'UT')
            and itsh.activeflag is false));
-- Records going to update 10. 


update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused'),
       modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now(), startdatetime = null
       where testsessionid in (select ts.id from testsession ts join ititestsessionhistory itsh on itsh.testsessionid = ts.id join student stu on stu.id = itsh.studentid 
            where stu.statestudentidentifier = '2159677' and stu.stateid = (select id from organization where displayidentifier = 'UT')
            and itsh.activeflag is false);
-- Records going to update 10.


update testsession set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused'),
       modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now() 
       where id in (select ts.id from testsession ts join ititestsessionhistory itsh on itsh.testsessionid = ts.id join student stu on stu.id = itsh.studentid 
            where stu.statestudentidentifier = '2159677' and stu.stateid = (select id from organization where displayidentifier = 'UT')
            and itsh.activeflag is false);
 -- Records going to update 10.


 update ititestsessionhistory set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused'),
       modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now() 
       where id in (select itsh.id from testsession ts join ititestsessionhistory itsh on itsh.testsessionid = ts.id join student stu on stu.id = itsh.studentid 
            where stu.statestudentidentifier = '2159677' and stu.stateid = (select id from organization where displayidentifier = 'UT')
            and itsh.activeflag is false);
 -- Records going to update 10.

            

 -- For the kid David Dillon (1670414)    

 update studentstestsections set activeflag = true, statusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'unused'),
       modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now(), startdatetime = null
       where studentstestid in (select id from studentstests  where testsessionid in (select ts.id from testsession ts join ititestsessionhistory itsh on itsh.testsessionid = ts.id join student stu on stu.id = itsh.studentid 
            where stu.statestudentidentifier = '1670414' and stu.stateid = (select id from organization where displayidentifier = 'UT')
            and itsh.activeflag is false));       
-- Records going to update 31.


update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused'),
       modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now(), startdatetime = null
       where testsessionid in (select ts.id from testsession ts join ititestsessionhistory itsh on itsh.testsessionid = ts.id join student stu on stu.id = itsh.studentid 
            where stu.statestudentidentifier = '1670414' and stu.stateid = (select id from organization where displayidentifier = 'UT')
            and itsh.activeflag is false);
-- Records going to update 31.


update testsession set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused'),
       modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now() 
       where id in (select ts.id from testsession ts join ititestsessionhistory itsh on itsh.testsessionid = ts.id join student stu on stu.id = itsh.studentid 
            where stu.statestudentidentifier = '1670414' and stu.stateid = (select id from organization where displayidentifier = 'UT')
            and itsh.activeflag is false);
-- Records going to update 31.


 update ititestsessionhistory set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused'),
       modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now() 
       where id in (select itsh.id from testsession ts join ititestsessionhistory itsh on itsh.testsessionid = ts.id join student stu on stu.id = itsh.studentid 
            where stu.statestudentidentifier = '1670414' and stu.stateid = (select id from organization where displayidentifier = 'UT')
            and itsh.activeflag is false);
-- Records going to update 31.