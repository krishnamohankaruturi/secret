update studentstestsections set activeflag=false where studentstestid in (select id from studentstests where studentid=864181);
update studentstests set activeflag=false where studentid=864181;
update testsession set activeflag=false where id in (select testsessionid from studentstests where studentid=864181);
update studenttrackerband set activeflag=false where studenttrackerid in (select id from studenttracker where studentid=864181);
update studenttracker set status='UNTRACKED' where studentid=864181;