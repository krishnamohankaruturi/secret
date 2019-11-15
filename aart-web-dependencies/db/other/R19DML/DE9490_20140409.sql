--DE9490 Student ID - 6183953544
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1058731,1059683);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1058731,1059683));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1058731,1059683);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1058731,1059683);
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where studentid=865666;

--DE9490 Student ID - 8776561969
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1059645,1058704);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1059645,1058704));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1059645,1058704);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1059645,1058704);
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where studentid=865660;