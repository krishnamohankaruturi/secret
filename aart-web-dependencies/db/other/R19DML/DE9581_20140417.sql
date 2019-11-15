-- Student ID-9180418414
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1197988,1198099);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1197988,1198099));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1197988,1198099);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1197988,1198099);
update studenttracker set status='UNTRACKED' where studentid=1133265;

-- Student ID-6616191168
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1307273,1307084);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1307273,1307084));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1307273,1307084);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1307273,1307084);
update studenttracker set status='UNTRACKED' where studentid=857941;
 
