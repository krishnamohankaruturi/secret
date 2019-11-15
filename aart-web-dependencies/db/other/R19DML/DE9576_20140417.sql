 -- Student ID-1002297138
 
 update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1229796,1191982,1038646,1229522,1214040,1192236,1048708);
 update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1229796,1191982,1038646,1229522,1214040,1192236,1048708));
 update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1229796,1191982,1038646,1229522,1214040,1192236,1048708);
 update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1229796,1191982,1038646,1229522,1214040,1192236,1048708);
 
