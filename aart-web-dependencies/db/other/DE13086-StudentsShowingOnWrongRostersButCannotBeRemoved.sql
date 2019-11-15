-- DE13086: Students showing on wrong rosters but cannot be removed
update enrollmentsrosters set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username ='cetesysadmin')
  where enrollmentid in (select id from enrollment where studentid in (1139917, 1139918) and currentschoolyear = 2016) 
  and rosterid = (select id from roster where coursesectionname = 'Epperly 4th Math' and attendanceschoolid = 33232 and teacherid = 147664);

 -- Records going to update 2


-- For MO student. Student completed the tests in these rosters and itiplans also complted in these rosters. So not changing anything on iti or studentstests.
update enrollmentsrosters set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username ='cetesysadmin')
  where enrollmentid in (select id from enrollment where studentid = 833124 and currentschoolyear = 2016) 
  and rosterid in (select id from roster where coursesectionname in ('zzdonotuse', 'zzdonotuse', 'zzdonotuse') and attendanceschoolid =  18426);

 -- Records going to update 3