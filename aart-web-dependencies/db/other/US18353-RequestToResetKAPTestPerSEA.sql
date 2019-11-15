-- US18353: High EP: Prod - Request to reset KAP test per SEA
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (9499573, 10211654, 10082476);

-- Number of rows going to update: 3

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (9499573, 10211654, 10082476);

-- Number of rows going to update: 6


INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9499573, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

-- Insert count: 

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (10211654, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
                  
-- Insert count: 1                  

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (10082476, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now()); 
-- Insert count: 1                  
                

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12699033, 12680397, 12623358, 12747339, 10211652, 10082473, 12743186, 12799026, 12458230);

-- Number of rows going to update: 16 

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12699033, 12680397, 12623358, 12747339, 10211652, 10082473, 12743186, 12799026, 12458230);

-- Number of rows going to update: 18


update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12699033, 12680397, 12623358, 12747339, 10211652, 10082473, 12743186, 12799026, 12458230);

-- Number of rows going to update: 9


INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12699033, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

-- Insert count: 1                  

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12680397, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
-- Insert count: 1                  

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12623358, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

-- Insert count: 1                  

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12747339, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
-- Insert count: 1
                  
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (10211652, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

-- Insert count: 1

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (10082473, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

-- Insert count: 1 

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12743186, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

-- Insert count: 1

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12799026, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

-- Insert count: 1 

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12458230, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());                  