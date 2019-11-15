DO $$DECLARE

STUDENTTRACKERBAND_ROW RECORD;
STUDENTTEST_ROW RECORD;
STUDENTTRACKER_ROW RECORD;
COUNT BIGINT;
STDATA BIGINT[][];
STBANDDATA BIGINT[];

BEGIN

COUNT:=0;

STDATA:=ARRAY[[853469,440,1185393],
[861370,440,1108982],
[863283,440,1171904],
[864985,440,1164141],
[865167,440,1215602],
[912964,3,1176778],
[1033855,440,1171555],
[1038762,3,1180095],
[1088641,3,1017720],
[1088643,3,1017728],
[1088643,440,1009278],
[1093150,440,1187405],
[1093185,440,1009375],
[1093909,440,1181871],
[1093915,440,1200141],
[1093916,440,1194427]];

FOREACH STBANDDATA slice 1 IN ARRAY STDATA

LOOP

FOR STUDENTTRACKERBAND_ROW IN
select stb.* from studenttrackerband stb
inner join studenttracker st on st.id=stb.studenttrackerid
where stb.activeflag=true and st.activeflag=true
and st.studentid=STBANDDATA[1]
and st.contentareaid=STBANDDATA[2] 
and stb.id>=(select id from studenttrackerband where testsessionid=STBANDDATA[3])

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