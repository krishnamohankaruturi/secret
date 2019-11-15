--DE9391
update studenttrackerband set activeflag=false where studenttrackerid in (select id from studenttracker where studentid=881288);
update studenttracker set status='UNTRACKED' where id in (select id from studenttracker where studentid=881288);
--DE9371
update studenttrackerband set activeflag=false where studenttrackerid in (select id from studenttracker where studentid=964078);
update studenttracker set status='UNTRACKED' where id in (select id from studenttracker where studentid=964078);
