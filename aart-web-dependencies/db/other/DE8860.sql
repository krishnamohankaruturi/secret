update studenttrackerband set activeflag=false where studenttrackerid in (select id from studenttracker where studentid=926801);

update studenttracker set status='UNTRACKED' where studentid=926801;