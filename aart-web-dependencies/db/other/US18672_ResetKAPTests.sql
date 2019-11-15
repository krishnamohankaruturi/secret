--8950087685	ELA 	Stage 3
--9230301205	Math	Stage 3
--1431159603	ELA 	Stage 3
--7486916371	ELA 	Stage 3
--1845509374	ELA 	Stage 3
--7802731488	ELA		stage 3
--6083150272	ELA	stage 3
--8583945756	ELA	stage 3
--8006980659	ELA	Stage 3
--3693705178	Math	stage 1
--9589299733	Math	Stage 1
--8619372807	Math	Stage 1
--Reset stages
update studentstests set activeflag = true, status = 85,
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), enddatetime = null        
        where id in (12303569,13685524, 13511529, 13492697, 13569789, 12099288, 12188694, 12193692, 12099283, 10281836,9901210,9791410);

update studentstestsections set statusid =  129,       
          previousstatusid = 127,
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          where studentstestid in (12303569,13685524, 13511529, 13492697, 13569789, 12099288, 12188694, 12193692, 12099283, 10281836,9901210,9791410);

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12303569, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13685524, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13511529, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13492697, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13569789, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12099288, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12188694, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12193692, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12099283, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (10281836, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9901210, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9791410, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());

--Stages inactivate
update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in (12569019, 9413543,13815915, 12525335, 12548742, 10176261, 9917743, 10190516, 10176120,  12975815, 13560982, 13564402, 9901204, 13683337, 13828071);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (12569019, 9413543, 13815915, 12525335, 12548742, 10176261, 9917743, 10190516, 10176120,  12975815, 13560982, 13564402, 9901204, 13683337, 13828071);
          
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id in (12569019,9413543, 13815915, 12525335, 12548742, 10176261, 9917743, 10190516, 10176120,  12975815, 13560982, 13564402, 9901204, 13683337, 13828071);
        
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12569019, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9413543, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13815915, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12525335, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12548742, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (10176261, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9917743, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (10190516, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (10176120, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (12975815, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13560982, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13564402, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (9901204, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13683337, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
      VALUES (13828071, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
      
