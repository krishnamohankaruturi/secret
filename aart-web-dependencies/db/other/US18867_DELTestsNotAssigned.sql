-- Student ID: 2916798722 , MO Student not Receiving Math or ELA Tests
UPDATE studenttracker SET status = 'UNTRACKED', modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()  
 where id in (281053, 281033); 