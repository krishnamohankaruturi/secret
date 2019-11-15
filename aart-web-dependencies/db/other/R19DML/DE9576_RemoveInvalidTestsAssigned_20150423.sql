DO $$DECLARE

STUDENTTRACKERBAND_ROW RECORD;
STUDENTTEST_ROW RECORD;
STUDENTTRACKER_ROW RECORD;
COUNT BIGINT;
STDATA BIGINT[][];
STBANDDATA BIGINT[];

BEGIN

COUNT:=0;

STDATA:=ARRAY[[1038515,440],
[1038515,3]];

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

update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid=STUDENTTRACKERBAND_ROW.testsessionid);
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid=STUDENTTRACKERBAND_ROW.testsessionid;
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id=STUDENTTRACKERBAND_ROW.testsessionid;

END IF;

update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id=STUDENTTRACKERBAND_ROW.id;
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where id=STUDENTTRACKERBAND_ROW.studenttrackerid;

END LOOP;

END LOOP;

RAISE NOTICE 'Total Count, %',COUNT;

END$$;