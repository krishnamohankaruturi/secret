-- US18918 : EP: Prod - Request to correct move tests and correct dual enrollment for KAP students
-- Inactivating one kid 

UPDATE studentsresponses SET activeflag= false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      WHERE studentstestsid in (SELECT id from studentstests where enrollmentid in (2310817, 2354802) and status in (84, 465) and activeflag is true);


UPDATE studentstestsections SET activeflag= false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      where studentstestid in (SELECT id from studentstests where enrollmentid in (2310817, 2354802) and status in (84, 465) and activeflag is true);


UPDATE studentstests SET activeflag= false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
    where enrollmentid in (2310817, 2354802) and status in (84, 465) and activeflag is true;


UPDATE studentsresponses SET activeflag= false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      WHERE studentstestsid IN (9191503,9191834);


 UPDATE studentstestsections SET activeflag= false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      where studentstestid in (9191503,9191834);
     

UPDATE studentstests SET  activeflag= false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      where id in (9191503,9191834);     	

UPDATE studentstests SET enrollmentid=2368947, testsessionid = 2181896,modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')        
     where id =9041066 and testsessionid = 2182948 and enrollmentid = 2310817 and activeflag is true;
     

UPDATE studentstests SET enrollmentid=2368947, testsessionid = 2176866,modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')        
     where id =8877878 and testsessionid = 2178577 and enrollmentid = 2310817 and activeflag is true;
     
     

UPDATE enrollment SET activeflag = false,modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), exitwithdrawaltype = -55, exitwithdrawaldate = now(),
        notes = 'Inactivating enrollments as per US18198'
     WHERE id IN (2310817, 2354802);