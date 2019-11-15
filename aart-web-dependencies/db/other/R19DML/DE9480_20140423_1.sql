-- Student ID-1001479468
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1282515,1195846);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1282515,1195846));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1282515,1195846);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1282515,1195846);


-- Student ID-1001471328
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1293729,1196645);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1293729,1196645));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1293729,1196645);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1293729,1196645);


-- Student ID-1001476568
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1288320,1195770);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1288320,1195770));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1288320,1195770);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1288320,1195770);
