-- US18180 : SR - EP - High - Reactivate Stage 1 of KAP ELA Test - Stage 2 already listed as complete

-- Reactivating testsession 2016_1258_Grade 4_English Language Arts_Stage 1 for student EPID= 263497 
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where studentid =  263497
        and testsessionid = (select id from testsession where name = '2016_1258_Grade 4_English Language Arts_Stage 1');

-- Number of rows going to update: 1

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid = (select id from studentstests where studentid = 263497 
		and testsessionid = (select id from testsession where name = '2016_1258_Grade 4_English Language Arts_Stage 1'));

-- Number of rows going to update: 3

-- studentstestsid for student EPID= 263497 and testsession "2016_1258_Grade 4_English Language Arts_Stage 1" is 10292544
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (10292544, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

-- Insert count : 1      


-- Inactivating student responses and scores for student EPID=263497  in testsessions "2016_1258_Grade 4_English Language Arts_Stage 2" and "2016_1258_Grade 4_English Language Arts_Stage 3"
-- "2016_1258_Grade 4_English Language Arts_Stage 2" test is completed and "2016_1258_Grade 4_English Language Arts_Stage 3" is unused.

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (select id from studentstests where studentid = 263497 
		and testsessionid in (select id from testsession where name in ('2016_1258_Grade 4_English Language Arts_Stage 2', '2016_1258_Grade 4_English Language Arts_Stage 3')));

-- Number of rows going to update: 0

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (select id from studentstests where studentid =  263497
		and testsessionid in (select id from testsession where name in ('2016_1258_Grade 4_English Language Arts_Stage 2', '2016_1258_Grade 4_English Language Arts_Stage 3')));

-- Number of rows going to update: 6


update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where studentid =  263497
        and testsessionid in (select id from testsession where name in ('2016_1258_Grade 4_English Language Arts_Stage 2', '2016_1258_Grade 4_English Language Arts_Stage 3'));

-- Number of rows going to update: 2

-- studentstestsid for student EPID= 263497 and testsession "2016_1258_Grade 4_English Language Arts_Stage 2" is 11983487
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (11983487, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

-- Insert count : 1                  

-- studentstestsid for student EPID= 263497 and testsession "2016_1258_Grade 4_English Language Arts_Stage 3" is 11996135
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (11996135, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

-- Insert count : 1