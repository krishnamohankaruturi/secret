 -- Student ID-1001465924
 
 update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1286206);
 update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1286206));
 update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1286206);
 update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1286206);
 
 -- Student ID-1001466242

 update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1292281);
 update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1292281));
 update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1292281);
 update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1292281);
 
 -- Student ID-1002752896

 update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1292278);
 update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1292278));
 update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1292278);
 update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1292278);
 