UPDATE testsession SET rosterid = 891211 ,  modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE id in (2971524, 2922755,2810062,2716905,2668614,2490995,2282745,2015413) ;
