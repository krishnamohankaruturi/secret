-- Student ID-330033286
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1083443,1081469);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1083443,1081469));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1083443,1081469);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1083443,1081469);
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where studentid=866888;


