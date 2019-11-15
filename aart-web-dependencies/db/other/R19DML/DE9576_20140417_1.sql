-- Student ID-1002148159
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1252352,1089835,1255084,1087457);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1252352,1089835,1255084,1087457));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1252352,1089835,1255084,1087457);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1252352,1089835,1255084,1087457);

-- Student ID-1002494575
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1247277,1243478,1090161,1266068,1247306,1243344,1051448);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1247277,1243478,1090161,1266068,1247306,1243344,1051448));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1247277,1243478,1090161,1266068,1247306,1243344,1051448);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studenttrackerid in (24617,24618);
update studenttracker set status='UNTRACKED' where studentid=862290;
 
-- Student ID-1001475678
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1249306);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1249306));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1249306);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1249306);
