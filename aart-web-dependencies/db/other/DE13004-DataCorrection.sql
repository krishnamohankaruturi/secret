--- DE13004: EP: Production – Student was exited, but test still showing up under login. Took incorrect test.

-- Switching the enrollmentid on the completed studentstests from enrollment from school 1558 to school 4639 with testid 34899. 
-- School 4639 had testsession id 2175134 (2016_4639_Grade 4_Mathematics_Performance) and school 1558 had testsessionid 2178593(2016_1558_Grade 4_Mathematics_Performance)
-- Studentstests id 8690982 need to move from 1558 to 4639 school.
update studentstests set enrollmentid = (select en.id from enrollment en join student stu on stu.id = en.studentid 
        join organization org on org.id = en.attendanceschoolid where stu.statestudentidentifier ='6435682356' and org.displayidentifier='4639' and en.currentschoolyear = 2016 and en.activeflag is true),
        modifieduser = (select id from aartuser where username = 'cetesysadmin'), testsessionid = 2175134   
        where enrollmentid =  (select en.id from enrollment en join student stu on stu.id = en.studentid 
        join organization org on org.id = en.attendanceschoolid where stu.statestudentidentifier ='6435682356' and org.displayidentifier='1558' and en.currentschoolyear = 2016 and en.activeflag is false and en.exitwithdrawaldate is not null)
        and status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete')
        and testid = 34899;
        
-- Records going to update : 1


-- Switching the enrollmentid on the unused studentstests from enrollment from school 4639 to school 1558 with testid 34899 and making the all studentstestsections and studentstests to inactive.
-- School 4639 had testsession id 2175134 (2016_4639_Grade 4_Mathematics_Performance) and school 1558 had testsessionid 2178593(2016_1558_Grade 4_Mathematics_Performance)
-- Studentstests id 9290837 need to move from 4639 to 1558 school.

update studentstestsections set activeflag = false, statusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'exitclearunenrolled'),
       modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now()
       where studentstestid = 9290837;

-- Records going to update : 1		

update studentstests set enrollmentid = (select en.id from enrollment en join student stu on stu.id = en.studentid 
        join organization org on org.id = en.attendanceschoolid where stu.statestudentidentifier ='6435682356' and org.displayidentifier='1558' and en.currentschoolyear = 2016 and en.activeflag is false and en.exitwithdrawaldate is not null),
        modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now(),  testsessionid = 2178593,     
        status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'exitclearunenrolled'), activeflag = false
        where id = 9290837;

-- Records going to update : 1



-- Inactivating the studentstests and studenttestsections for enrollment in school 1558 and testid 34737

update studentstestsections set activeflag = false, statusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'exitclearunenrolled'),
       modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now()    
       where studentstestid = (select id from studentstests where enrollmentid = (select en.id from enrollment en join student stu on stu.id = en.studentid 
        join organization org on org.id = en.attendanceschoolid where stu.statestudentidentifier ='6435682356' and org.displayidentifier='1558' and en.currentschoolyear = 2016 and en.activeflag is false and en.exitwithdrawaldate is not null)
		and testid = 34737);
	
-- Records going to update : 2

update studentstests set activeflag = false, modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now(),        
        status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'exitclearunenrolled')        
        where enrollmentid = (select en.id from enrollment en join student stu on stu.id = en.studentid join organization org on org.id = en.attendanceschoolid 
                  where stu.statestudentidentifier ='6435682356' and org.displayidentifier='1558' and en.currentschoolyear = 2016 and en.activeflag is false and en.exitwithdrawaldate is not null)
        and status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused')
        and testid =34737;
        
-- Records going to update : 1
        
