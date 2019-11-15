-- US17722: EP Prod - Remove duplicate rosters from NY district - Medium

-- Only rosters were active, all the students in these rosters were already removed, all studentstests and testsessions were marked to rosterunerolled status as students didn't completed the tests. 
-- No ITI plans were found on these rosters. So only deactivating the rosters.


update roster set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Inactivating rosters as per US17722'    
      where attendanceschoolid = (select schoolid from organizationtreedetail where lower(schoolname) = lower('Longwood High School')  and lower(districtname) = lower('Longwood CSD') and statedisplayidentifier = 'NY')
      and coursesectionname ilike 'ZZ%' and activeflag is true;

-- Number of records going to update : 2 