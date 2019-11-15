 -- Student ID-152885406
 
 update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1295495,1088320,1295433,1093471);
 update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1295495,1088320,1295433,1093471));
 update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1295495,1088320,1295433,1093471);
 update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1295495,1088320,1295433,1093471);
 
