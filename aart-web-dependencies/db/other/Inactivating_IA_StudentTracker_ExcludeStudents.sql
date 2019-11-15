update studenttrackerband set activeflag = false, modifieddate = now(), modifieduser = 12
 where studenttrackerid in(select id from studenttracker where studentid in (1403997, 1406564)); 

update studenttracker set activeflag = false, modifieddate = now(), modifieduser = 12
 where studentid in (1403997, 1406564);

update studentstests set activeflag = false, modifieddate = now(), modifieduser = 12, manualupdatereason = 'Students are excluded from the student tracker'
 where studentid in (1403997, 1406564);

update studentstestsections set activeflag = false, modifieddate = now(), modifieduser = 12
    where studentstestid in (select id from studentstests where studentid in (1403997, 1406564));

update testsession set activeflag = false, modifieddate = now(), modifieduser = 12
  where id in (select testsessionid from studentstests where studentid in (1403997, 1406564));
