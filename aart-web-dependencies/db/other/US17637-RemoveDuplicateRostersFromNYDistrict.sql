-- US17637: EP Prod - Remove duplicate rosters from NY district
-- No ITI plans were existed on these rosters.

-- For District: Greece CSD(260501060000) and  school: Olympia High School(260501060008)
update enrollmentsrosters set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin') 
where rosterid in 
  (select id from roster where activeflag = true and attendanceschoolid in (select schoolid from organizationtreedetail where statedisplayidentifier = 'NY' 
  and districtdisplayidentifier ='260501060000' and schooldisplayidentifier = '260501060008' and  coursesectionname ilike 'ZZ%'))
   and activeflag is true;
-- Records going to update: 0
   
update roster set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
    where activeflag = true and attendanceschoolid in (select schoolid from organizationtreedetail where statedisplayidentifier = 'NY' 
    and districtdisplayidentifier ='260501060000' and schooldisplayidentifier = '260501060008') and coursesectionname ilike 'ZZ%';
-- Records going to update: 1.    



-- For District: Greece CSD(260501060000) and  school:Athena Middle School(260501060020)
update enrollmentsrosters set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin') 
where rosterid in 
  (select id from roster where activeflag = true and attendanceschoolid in (select schoolid from organizationtreedetail where statedisplayidentifier = 'NY' 
  and districtdisplayidentifier ='260501060000' and schooldisplayidentifier = '260501060020' and  coursesectionname ilike 'ZZ%'))
   and activeflag is true;
-- Records going to update: 0

update roster set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
    where activeflag = true and attendanceschoolid in (select schoolid from organizationtreedetail where statedisplayidentifier = 'NY' 
    and districtdisplayidentifier ='260501060000' and schooldisplayidentifier = '260501060020') and coursesectionname ilike 'ZZ%';
-- Records going to update: 3.