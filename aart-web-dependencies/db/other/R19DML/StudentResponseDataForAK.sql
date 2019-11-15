DO $$DECLARE

STUDENTRESPONSE_ROW RECORD;
STUDENTTEST_ROW RECORD;

BEGIN

Raise Notice 'Student Test Id,Student Test Section Id,Task Variant Id,Student Response,Student Score,Scoring Data,Scoring Method,Max Score';

FOR STUDENTTEST_ROW IN 
select distinct sts.id as studentstestsectionsid,tv.id as taskvariantid
from studentstests st
join testcollection tc on tc.id=st.testcollectionid
join testcollectionstests tct on tct.testcollectionid=tc.id
join testsection ts on ts.testid = tct.testid
join testsectionstaskvariants tstv on tstv.testsectionid = ts.id
join taskvariant tv on tv.id = tstv.taskvariantid
join student std on std.id = st.studentid
join studentstestsections sts on sts.studentstestid=st.id and sts.testsectionid=ts.id
where tv.id in (215833,163792,219350,218499,146209,214510)
and st.testcollectionid in (1365,1366,1367,1370,1371,1377,1378,1380,1420,1421,1422,1428,1451,1513,2301,2308)
and std.stateid = 35999

LOOP

FOR STUDENTRESPONSE_ROW IN
select sr.studentstestsectionsid,sr.taskvariantid,sr.response,sr.score,tv.scoringdata,tv.scoringmethod,tv.maxscore,sr.studentstestsid 
from studentsresponses sr,taskvariant tv where sr.taskvariantid=tv.id and sr.studentstestsectionsid=STUDENTTEST_ROW.studentstestsectionsid
and sr.taskvariantid=STUDENTTEST_ROW.taskvariantid

LOOP

Raise Notice '%,%,%,%,%,%,%,%',STUDENTRESPONSE_ROW.studentstestsid,STUDENTRESPONSE_ROW.studentstestsectionsid,STUDENTRESPONSE_ROW.taskvariantid,STUDENTRESPONSE_ROW.response,STUDENTRESPONSE_ROW.score,STUDENTRESPONSE_ROW.scoringdata,STUDENTRESPONSE_ROW.scoringmethod,STUDENTRESPONSE_ROW.maxscore;

END LOOP;

END LOOP;

END$$;