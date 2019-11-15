update studentstests set activeflag = false, modifieddate = now(), modifieduser = 12, manualupdatereason = 'Students are not supposed to get the test cbid : 18082'
 where testid in (select id from test where externalid = 18082);

update studentstestsections set activeflag = false, modifieddate = now(), modifieduser = 12
    where studentstestid in (select id from studentstests where testid in (select id from test where externalid = 18082));

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = 12
     where studentstestsid in (select id from studentstests where testid in (select id from test where externalid = 18082));