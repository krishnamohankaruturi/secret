--4559626677 - Math - Stage3 reset
update studentstests set activeflag = true, status = 85,
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), enddatetime = null        
        where id in (13107836);

update studentstestsections set statusid =  129,       
          previousstatusid = 127,
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          where studentstestid in (13107836);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13107836, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--Stage4 inactivate
update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (9949870);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (9949870);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (9949870);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9949870, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      

--1101580364 - Math - Stage3 reset
update studentstests set activeflag = true, status = 85,
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), enddatetime = null        
        where id in (13107842);

update studentstestsections set statusid =  129,       
          previousstatusid = 127,
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          where studentstestid in (13107842);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13107842, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--Stage4 inactivate
update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (9951234);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (9951234);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (9951234);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9951234, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
----6182017309 - Math - Stage3 reset
update studentstests set activeflag = true, status = 85,
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), enddatetime = null        
        where id in (12610298);

update studentstestsections set statusid =  129,       
          previousstatusid = 127,
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')        
          where studentstestid in (12610298);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12610298, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--Stage4 inactivate
update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (9704266);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (9704266);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (9704266);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9704266, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
-- 1805748955 - ELA - Stage1 reset

update studentstests set activeflag = true, status = 85,
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), enddatetime = null        
        where id in (10180236);

update studentstestsections set statusid =  129,       
          previousstatusid = 127,
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')        
          where studentstestid in (10180236);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (10180236, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--Stage2 inactivate
update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12280945);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12280945);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12280945);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12280945, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
 --Stage3 inactivate
update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12374840);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12374840);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12374840);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12374840, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

 --Stage4 inactivate
update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12448784);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12448784);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12448784);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12448784, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
 -- 2940025975 - ELA - Stage2 reset

update studentstests set activeflag = true, status = 85,
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), enddatetime = null        
        where id in (13267696);

update studentstestsections set statusid =  129,       
          previousstatusid = 127,
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          where studentstestid in (13267696);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13267696, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--Stage3 inactivate
update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (13284281);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (13284281);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (13284281);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13284281, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
--Stage4 inactivate
update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12564374);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12564374);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12564374);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12564374, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());