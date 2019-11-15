-- US17714: EP Prod - Remove duplicate rosters from NY district - Medium

-- Only rosters were active, all the students in these rosters were already removed, all studentstests and testsessions were marked to rosterunerolled status as students didn't completed the tests. 
-- No ITI plans were found on these rosters. So only deactivating the rosters.


update roster set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Inactivating rosters as per US17714'    
      where attendanceschoolid = (select schoolid from organizationtreedetail where schoolname ='WORLD JOURNALISM PREPARATORY'  and districtname = 'NYC DIST 25' and statedisplayidentifier = 'NY')
      and coursesectionname ilike 'ZZ%';

-- Number of records going to update : 2     