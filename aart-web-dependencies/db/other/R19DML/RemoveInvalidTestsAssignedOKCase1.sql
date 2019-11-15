DO $$DECLARE

STUDENTTRACKERBAND_ROW RECORD;
STUDENTTEST_ROW RECORD;
STUDENTTRACKER_ROW RECORD;
COUNT BIGINT;
STDATA BIGINT[][];
STBANDDATA BIGINT[];

BEGIN

COUNT:=0;

STDATA:=ARRAY[[487676,440],
[855354,440],
[863311,440],
[863312,440],
[863318,440],
[864526,3],
[864526,440],
[864529,3],
[864529,440],
[864843,3],
[912975,3],
[912976,3],
[929344,3],
[1081925,440],
[1110370,440]];

FOREACH STBANDDATA slice 1 IN ARRAY STDATA

LOOP

FOR STUDENTTRACKERBAND_ROW IN
select stb.* from studenttrackerband stb
inner join studenttracker st on st.id=stb.studenttrackerid
where stb.activeflag=true and st.activeflag=true
and st.studentid=STBANDDATA[1]
and st.contentareaid=STBANDDATA[2] 
and stb.modifieddate>='2015-03-13'

LOOP

COUNT:=COUNT+1;
RAISE NOTICE 'Student Tracker Band Id, %',STUDENTTRACKERBAND_ROW.id;

IF STUDENTTRACKERBAND_ROW.testsessionid is not null THEN

update studentstestsections set activeflag=false where studentstestid in (select id from studentstests where testsessionid=STUDENTTRACKERBAND_ROW.testsessionid);
update studentstests set activeflag=false,status=494 where testsessionid=STUDENTTRACKERBAND_ROW.testsessionid;
update testsession set activeflag=false where id=STUDENTTRACKERBAND_ROW.testsessionid;

END IF;

update studenttrackerband set activeflag=false where id=STUDENTTRACKERBAND_ROW.id;
update studenttracker set status='UNTRACKED' where id=STUDENTTRACKERBAND_ROW.studenttrackerid;

END LOOP;

END LOOP;

RAISE NOTICE 'Total Count, %',COUNT;

END$$;