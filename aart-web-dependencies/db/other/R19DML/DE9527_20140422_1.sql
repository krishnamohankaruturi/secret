-- Student ID-1002639008
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1406024,1405583);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1406024,1405583));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1406024,1405583);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1406024,1405583);
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where studentid=1148718;
update enrollmentsrosters set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (9955032,9955030);

-- Student ID-1001080755

update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1406027,1405586);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1406027,1405586));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1406027,1405586);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1406027,1405586);
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where studentid=1148720;
update enrollmentsrosters set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (9955033,9955031);

