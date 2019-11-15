-- Student ID-1045825
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1317324,1079920,1315896,1090548);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1317324,1079920,1315896,1090548));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1317324,1079920,1315896,1090548);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1317324,1079920,1315896,1090548);

-- Student ID-1001417325

update enrollmentsrosters set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id=8471649;


-- Student ID-1002282251
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1227488,1195834);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1227488,1195834));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1227488,1195834);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1227488,1195834);

-- Student ID-1001454103

update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1238323,1195797);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1238323,1195797));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1238323,1195797);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1238323,1195797);
 

 
