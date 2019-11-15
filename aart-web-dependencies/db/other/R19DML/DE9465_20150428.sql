-- Student ID - 5685063571 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1078040,1085227);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1078040,1085227));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1078040,1085227);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1078040,1085227);
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where id in (67388,40193,48074);
