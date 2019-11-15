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
where st.activeflag=true and tv.externalid=8280
and st.testcollectionid in (1365,1366,1367,1370,1371,1377,1378,1380,1420,1421,1422,1428,1451,1513,2301,2308,18,22,23,24,25,39,54,56,57,126,143,153,154,176,177,186,188,189,227,303,305,306,307,1343,1348,1350,1352,1353,1354,
1355,1358,1360,1361,1363,1364,1368,1369,1372,1373,1374,1375,1376,1379,1413,1414,1415,1416,1417,1418,1419,1423,
1424,1450,1498,1566,1665,1707,2493,2843,3107,3418,3435,3438,3439,3440,3441,3451,3452,3453,3873,3422,3423,3433)
and std.stateid in (35999,51)

LOOP

update studentsresponses set originalscore=0.835,score=0.833 where studentstestsectionsid=STUDENTTEST_ROW.studentstestsectionsid and score=0.835;

END LOOP;

END$$;