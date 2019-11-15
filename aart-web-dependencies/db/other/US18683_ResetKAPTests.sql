--1259613119	Math	Stage 1	909991
--1054392161	Math	Stage 3	458271
--2706259191	Math 	Stage 3	550756

--Reset stages
update studentstests set activeflag = true, status = 85,
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), enddatetime = null        
        where id in (13831110,13268945,9569309);

update studentstestsections set statusid =  129,       
          previousstatusid = 127,
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          where studentstestid in (13831110,13268945,9569309);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13831110, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13268945, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9569309, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());


--Stages inactivate
update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (9587522, 9764974,13888559, 13894084);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (9587522, 9764974, 13888559, 13894084);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (9587522,9764974, 13888559, 13894084);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9587522, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9764974, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13888559, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13894084, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      