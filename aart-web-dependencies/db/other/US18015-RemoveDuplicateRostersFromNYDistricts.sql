-- US18015 : EP Prod - Remove duplicate rosters from NY districts - High

-- Removing rosters with name 'DO NOT USE INACTIVE' from State: NY and District: Commack UFSD

update enrollmentsrosters set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin') 
where rosterid in 
  (select id from roster where activeflag = true and attendanceschoolid in (select schoolid from organizationtreedetail where statedisplayidentifier = 'NY' 
  and lower(districtname) = lower('Commack UFSD')) and lower(coursesectionname) = lower('DO NOT USE INACTIVE'))
   and activeflag is true;
-- Records going to update: 0
   
update roster set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
    where activeflag = true and attendanceschoolid in (select schoolid from organizationtreedetail where statedisplayidentifier = 'NY' 
    and lower(districtname) = lower('Commack UFSD')) and lower(coursesectionname) = lower('DO NOT USE INACTIVE');
-- Records going to update: 26.  



--  Removing rosters with name 'zzdonotuse' from State: NY and District: Port Washington UFSD
update enrollmentsrosters set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
where rosterid in 
  (select id from roster where activeflag = true and attendanceschoolid in (select schoolid from organizationtreedetail where statedisplayidentifier = 'NY' 
  and lower(districtname) = lower('Port Washington UFSD')) and lower(coursesectionname) = lower('zzdonotuse'))
   and activeflag is true;
-- Records going to update: 1
   
update roster set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin') 
    where activeflag = true and attendanceschoolid in (select schoolid from organizationtreedetail where statedisplayidentifier = 'NY' 
    and lower(districtname) = lower('Port Washington UFSD')) and lower(coursesectionname) = lower('zzdonotuse');
-- Records going to update: 4.      