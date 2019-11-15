-- Student ID-1001013498
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1377536,1103642);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1377536,1103642));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1377536,1103642);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1377536,1103642);
 
