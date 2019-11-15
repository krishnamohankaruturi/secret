-- Student ID-1001968671
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1462313);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1462313));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1462313);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1462313);
update enrollmentsrosters set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id=9954252;

-- Student ID-1001164989
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1281586);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1281586));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1281586);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1281586);

-- Student ID-248120

update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1278902,1230880,1052539,1278899,1230889,1180768,1033092);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1278902,1230880,1052539,1278899,1230889,1180768,1033092));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1278902,1230880,1052539,1278899,1230889,1180768,1033092);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (289512,107807,287272,147933,289573,106563,287273,247287,164424);
 

 
