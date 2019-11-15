-- US18108: SR - Reactivate Section 3 of KAP ELA Test - Section 4 already listed as complete

-- Reactivating testsession 2016_7715_Grade 6_English Language Arts_Stage 3  for student EPID= 172164
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null
        where studentid = 172164 
        and testsessionid = (select id from testsession where name = '2016_7715_Grade 6_English Language Arts_Stage 3');

-- Number of rows going to update: 1

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null
          where studentstestid = (select id from studentstests where studentid = 172164 
		and testsessionid = (select id from testsession where name = '2016_7715_Grade 6_English Language Arts_Stage 3'));

-- Number of rows going to update: 3




-- Just clearing the student responses and scores for student EPID= 172164 in testsession 2016_7715_Grade 6_English Language Arts_Stage 4.
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null, scores = null
        where studentid = 172164 
        and testsessionid = (select id from testsession where name = '2016_7715_Grade 6_English Language Arts_Stage 4');

-- Number of rows going to update: 1


update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid = (select id from studentstests where studentid = 172164 
		and testsessionid = (select id from testsession where name = '2016_7715_Grade 6_English Language Arts_Stage 4'));

-- Number of rows going to update: 3


update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid = (select id from studentstests where studentid = 172164 
		and testsessionid = (select id from testsession where name = '2016_7715_Grade 6_English Language Arts_Stage 4'));

-- Number of rows going to update: 3		