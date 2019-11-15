--DE9465 Student ID - 1001780122
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1246183,1246196);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1246183,1246196));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1246183,1246196);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1246183,1246196);
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where studentid=1149453;

