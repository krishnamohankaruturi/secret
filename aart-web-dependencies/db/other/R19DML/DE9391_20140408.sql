--DE9391 Student ID - 141314001
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1062944,1059657);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1062944,1059657));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1062944,1059657);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1062944,1059657);
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where studentid=957769;