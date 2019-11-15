-- Student ID-953143
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1478416,1085315,1491030,1074633);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1478416,1085315,1491030,1074633));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1478416,1085315,1491030,1074633);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1478416,1085315,1491030,1074633);
