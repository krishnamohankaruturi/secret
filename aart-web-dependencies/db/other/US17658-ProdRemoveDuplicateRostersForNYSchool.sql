-- US17658 : SR: EP Low - Prod - Remove Duplicate Rosters for NY School

-- No ITI testsessions existed for these rosters.
-- Inactivating the rosters.
update enrollmentsrosters set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin') 
where rosterid in 
  (select id from roster where activeflag = true and attendanceschoolid in (select schoolid from organizationtreedetail where statedisplayidentifier = 'NY' 
  and lower(schoolname) = lower('UCP of Ulster County')) and coursesectionname ilike 'ZZ%')
   and activeflag is true;
-- Records going to update: 0
   
update roster set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
    where activeflag = true and attendanceschoolid in (select schoolid from organizationtreedetail where statedisplayidentifier = 'NY' 
    and lower(schoolname) = lower('UCP of Ulster County')) and coursesectionname ilike 'ZZ%';
-- Records going to update: 3.   