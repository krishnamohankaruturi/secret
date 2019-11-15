-- Student ID - 980780240 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1067113);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1067113));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1067113);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1067113);
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where studentid=67388;