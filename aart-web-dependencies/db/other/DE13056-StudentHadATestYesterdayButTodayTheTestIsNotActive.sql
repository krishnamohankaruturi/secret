-- DE13056: EP: Prod - Student had a test yesterday but today the test is not active.

update enrollmenttesttypesubjectarea set activeflag = true, testtypeid = (select id from testtype where  testtypecode = '2' and id = 2),
             modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')     
        where enrollmentid in (select en.id from student stu join enrollment en on en.studentid = stu.id where stu.statestudentidentifier = '9816387425' and stu.stateid = 51
                  and en.currentschoolyear = 2016 and en.activeflag is true)
         and subjectareaid = (select id from subjectarea where subjectareacode = 'SELAA') and activeflag is false
         and testtypeid = (select id from testtype where testtypecode = 'C');

-- Records going to update : 1

update studentstestsections set activeflag = true, statusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'unused'),
      modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')     
      where studentstestid in (select id from studentstests where enrollmentid in (select en.id from student stu join enrollment en on en.studentid = stu.id where stu.statestudentidentifier = '9816387425' and stu.stateid = 51
                  and en.currentschoolyear = 2016 and en.activeflag is true)
    and testsessionid in (select id from testsession where name = '2016_8552_Grade 11_English Language Arts_Performance' and testtypeid = 2 and subjectareaid = 17)
    and activeflag is false);

-- Records going to update : 2

    

update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused'),
    modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
    where enrollmentid in (select en.id from student stu join enrollment en on en.studentid = stu.id where stu.statestudentidentifier = '9816387425' and stu.stateid = 51
                  and en.currentschoolyear = 2016 and en.activeflag is true)
    and testsessionid in (select id from testsession where name = '2016_8552_Grade 11_English Language Arts_Performance' and testtypeid = 2 and subjectareaid = 17)
    and activeflag is false;

-- Records going to update : 1
    
 