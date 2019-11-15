--US18380,US18360

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (select id from studentstests st where studentid in(select id from student where statestudentidentifier in('9046980154',
        '8324090908') and stateid=51) 
        and testsessionid in(select id from testsession ts1 where st.testsessionid=ts1.id and subjectareaid = 1 and ts1.stageid in(1,2,4,5)) and activeflag is true);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (select id from studentstests st where studentid in(select id from student where statestudentidentifier in('9046980154',
        '8324090908') and stateid=51) 
        and testsessionid in(select id from testsession ts1 where st.testsessionid=ts1.id and subjectareaid = 1 and ts1.stageid in(1,2,4,5)) and activeflag is true);

update studentstests  set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (select id from studentstests st where studentid in(select id from student where statestudentidentifier in('9046980154',
        '8324090908') and stateid=51) 
        and testsessionid in(select id from testsession ts1 where st.testsessionid=ts1.id and subjectareaid = 1 and ts1.stageid in(1,2,4,5)) and activeflag is true);


update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (select id from studentstests st where studentid in(select id from student where statestudentidentifier in('2519557141'
        ,'3117512985', '6112996723','2978988649','5724262703','8324090908','1681527758') and stateid=51) and 
        testsessionid in(select id from testsession ts1 where st.testsessionid=ts1.id and subjectareaid = 17 and ts1.stageid in(1,2,4,5)) and activeflag is true);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (select id from studentstests st where studentid in(select id from student where statestudentidentifier in('2519557141'
        ,'3117512985', '6112996723','2978988649','5724262703','8324090908','1681527758') and stateid=51) and 
        testsessionid in(select id from testsession ts1 where st.testsessionid=ts1.id and subjectareaid = 17 and ts1.stageid in(1,2,4,5)) and activeflag is true);

update studentstests  set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (select id from studentstests st where studentid in(select id from student where statestudentidentifier in('2519557141'
        ,'3117512985', '6112996723','2978988649','5724262703','8324090908','1681527758') and stateid=51) and 
        testsessionid in(select id from testsession ts1 where st.testsessionid=ts1.id and subjectareaid = 17 and ts1.stageid in(1,2,4,5)) and activeflag is true);



INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13120712, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13120711, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9803773, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9803766, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'pending'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12916296, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12920909, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());





-- 1681527758 (MPT,MDPT)
update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (8764264,8967350);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (8764264,8967350);

update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (8764264,8967350);



-----Reactivation------
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (10330432);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (10330432);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (10330432, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());


update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (10330428,12794413,12853988);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (10330428,12794413,12853988);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (10330428,12794413,12853988);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (10330428, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12794413, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12853988, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());



--2266387383
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (9894127);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (9894127);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9894127, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12100277,12191788,9894123);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12100277,12191788,9894123);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12100277,12191788,9894123);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12100277, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12191788, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9894123, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());


--2711002403
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (9893438);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (9893438);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9893438, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12100254,12191753,9893429);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12100254,12191753,9893429);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12100254,12191753,9893429);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12100254, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12191753, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9893429, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--3793754871
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (9893933);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (9893933);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9893933, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12115807,12191770,9893925);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12115807,12191770,9893925);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12115807,12191770,9893925);
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12115807, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12191770, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9893925, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());


--4771134294
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (9929596);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (9929596);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9929596, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12105752,12210326,9929592);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12105752,12210326,9929592);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12105752,12210326,9929592);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12105752, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12210326, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9929592, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--6447305994
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (9822343);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (9822343);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9822343, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12266946,12368386,12493488);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12266946,12368386,12493488);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12266946,12368386,12493488);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12266946, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12368386, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12493488, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--7522088518
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (9893920);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (9893920);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9893920, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12099934,12164649,9893913);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12099934,12164649,9893913);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12099934,12164649,9893913);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12099934, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12164649, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9893913, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--8271971727
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (9893460);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (9893460);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9893460, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12115789,12191756,9893454);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12115789,12191756,9893454);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12115789,12191756,9893454);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12115789, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12191756, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9893454, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--8964319532
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (9822145);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (9822145);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9822145, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12266902,12368370);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12266902,12368370);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12266902,12368370);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12266902, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12368370, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--9290708778
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (9893413);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (9893413);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9893413, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12100253,12164828,9893406);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12100253,12164828,9893406);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12100253,12164828,9893406);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12100253, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12164828, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9893406, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--1358599068
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (11983492);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (11983492);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (11983492, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (11999328,10297134);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (11999328,10297134);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (11999328,10297134);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (11999328, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (10297134, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--1979077193
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (11857197);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (11857197);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (11857197, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (11880419,12483554);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (11880419,12483554);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (11880419,12483554);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (11880419, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12483554, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--2841107671
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (12102199);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (12102199);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12102199, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12936409,12541612);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12936409,12541612);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12936409,12541612);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12936409, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12541612, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--4225198499
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (12105755);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (12105755);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12105755, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12191332,9929670);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12191332,9929670);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12191332,9929670);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12191332, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9929670, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--4353363832
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (12374221);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (12374221);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12374221, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12612390,12466321);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12612390,12466321);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12612390,12466321);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12612390, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12466321, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--5953542151
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (12686811);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (12686811);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12686811, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12689683,12578771);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12689683,12578771);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12689683,12578771);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12689683, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12578771, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--6050142726
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (12050298);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (12050298);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12050298, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12106453,9982896);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12106453,9982896);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12106453,9982896);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12106453, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9982896, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--6241004662
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (12050288);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (12050288);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12050288, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12106451,9982853);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12106451,9982853);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12106451,9982853);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12106451, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9982853, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--6956552434
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (12105769);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (12105769);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12105769, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12191367,9930004);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12191367,9930004);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12191367,9930004);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12191367, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9930004, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--7519899632
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (12105747);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (12105747);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12105747, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12191317,9929505);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12191317,9929505);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12191317,9929505);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12191317, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9929505, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--8018844992
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (12025255);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (12025255);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12025255, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12210459,9718047);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12210459,9718047);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12210459,9718047);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12210459, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9718047, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--8221832812
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (11072269);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (11072269);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (11072269, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12678260,12541456);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12678260,12541456);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12678260,12541456);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12678260, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12541456, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--6050142726
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (12106453);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (12106453);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12106453, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (9982896);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (9982896);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (9982896);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9982896, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--7649782549
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (12668850);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (12668850);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12668850, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12451140);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12451140);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12451140);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12451140, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--5241360743
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (12106441);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (12106441);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12106441, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (9982671);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (9982671);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (9982671);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9982671, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--6241004662
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (12106451);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (12106451);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12106451, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (9982853);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (9982853);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (9982853);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9982853, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());


--9645500109
update studentstests set activeflag = true, status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'inprogress'),
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), startdatetime = null, enddatetime = null        
        where id in (10082476);

update studentstestsections set statusid =  (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'reactivated'),       
          previousstatusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'complete'),
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),  startdatetime = null, enddatetime = null          
          where studentstestid in (10082476);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (10082476, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete'),
                  'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12978000);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12978000);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12978000);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12978000, (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'unused'),
                  'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());






