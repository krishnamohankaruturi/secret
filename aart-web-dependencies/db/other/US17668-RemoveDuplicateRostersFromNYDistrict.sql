-- US17668: EP Prod - Remove duplicate rosters from NY district - Medium
-- NO ITI and testsessions are associated to these rosters.

-- Removing rosters for Longwood CSD district
update enrollmentsrosters set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin') 
where rosterid in 
  (select id from roster where activeflag = true and attendanceschoolid in (select schoolid from organizationtreedetail where statedisplayidentifier = 'NY' 
  and lower(districtname) = lower('Longwood CSD')) and coursesectionname ilike 'ZZ%')
   and activeflag is true;
-- Records going to update: 10
   
update roster set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
    where activeflag = true and attendanceschoolid in (select schoolid from organizationtreedetail where statedisplayidentifier = 'NY' 
    and lower(districtname) = lower('Longwood CSD')) and coursesectionname ilike 'ZZ%';
-- Records going to update: 15.  
    

-- -- Removing rosters for Clinton-Essex-Warren-Washing BOCES school in BOCES
update enrollmentsrosters set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin') 
where rosterid in 
  (select id from roster where activeflag = true and attendanceschoolid in (select schoolid from organizationtreedetail where statedisplayidentifier = 'NY' 
  and lower(districtname) = lower('BOCES') and lower(schoolname) = lower('Clinton-Essex-Warren-Washing BOCES')) and coursesectionname ilike 'ZZ%')
   and activeflag is true;
-- Records going to update: 2
   
update roster set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
    where activeflag = true and attendanceschoolid in (select schoolid from organizationtreedetail where statedisplayidentifier = 'NY' 
    and lower(districtname) = lower('BOCES') and lower(schoolname) = lower('Clinton-Essex-Warren-Washing BOCES')) and coursesectionname ilike 'ZZ%';
-- Records going to update: 6.      