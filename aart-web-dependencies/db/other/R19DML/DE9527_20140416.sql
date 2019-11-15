 -- Student ID-1089850 (Student ID 1124115)
 
 update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1376232,1374384);
 update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1376232,1374384));
 update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1376232,1374384);
 update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1376232,1374384);
 
  -- Student ID-1201006 (Student ID 1124113)
  
  update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1362422,1370915);
  update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1362422,1370915));
  update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1362422,1370915);
  update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1362422,1370915);
 