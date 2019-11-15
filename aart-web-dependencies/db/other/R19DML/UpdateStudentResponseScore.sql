DO $$DECLARE

SCOREMAP_ROW RECORD;
STUDENTTEST_ROW RECORD;

BEGIN

FOR STUDENTTEST_ROW IN 
select distinct sts.id as studentstestsectionsid
from studentstests st
join testcollection tc on tc.id=st.testcollectionid
join testcollectionstests tct on tct.testcollectionid=tc.id
join testsection ts on ts.testid = tct.testid
join testsectionstaskvariants tstv on tstv.testsectionid = ts.id
join taskvariant tv on tv.id = tstv.taskvariantid
join student std on std.id = st.studentid
join studentstestsections sts on sts.studentstestid=st.id and sts.testsectionid=ts.id
where st.activeflag=true and tv.scoringmethod in ('partialquota','partialcredit')
and st.testcollectionid in (1365,1366,1367,1370,1371,1377,1378,1380,1420,1421,1422,1428,1451,1513,2301,2308)
and std.stateid = 35999

LOOP

FOR SCOREMAP_ROW IN
Select * from scoremapping

LOOP

update studentsresponses set originalscore=SCOREMAP_ROW.originalscore,score=SCOREMAP_ROW.convertedscore where studentstestsectionsid=STUDENTTEST_ROW.studentstestsectionsid and score=SCOREMAP_ROW.originalscore;

END LOOP;

END LOOP;

END$$;