-- US17633:EP - SR: High- Remove Duplicate Rosters - ZZDoNotUse

-- Inactivating the rosters.
update enrollmentsrosters set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin') 
where rosterid in 
  (select id from roster where activeflag = true and attendanceschoolid in (select schoolid from organizationtreedetail where statedisplayidentifier = 'NY' 
  and lower(districtname) = lower('Clarence CSD')) and coursesectionname ilike 'ZZ%')
   and activeflag is true;
-- Records going to update: 20
   
update roster set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
    where activeflag = true and attendanceschoolid in (select schoolid from organizationtreedetail where statedisplayidentifier = 'NY' 
    and lower(districtname) = lower('Clarence CSD')) and coursesectionname ilike 'ZZ%';
-- Records going to update: 15.    