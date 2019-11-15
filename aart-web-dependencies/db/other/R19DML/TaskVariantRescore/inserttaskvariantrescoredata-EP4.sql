DO $$DECLARE

ITEMDATA text[][];
TASKVARIANTDATA_ROW RECORD;
NEWITEMDATA text[];
NEWMAXSCORE BIGINT;
NEWSCORINGMETHOD character varying(75);
NEWSCORINGDATA text;
UPDATEREASON character varying(250);

BEGIN

UPDATEREASON:='Changed scoring method from Null to Correct-Only';

ITEMDATA:=ARRAY[['6540','correctOnly','',''],
['6734','correctOnly','',''],
['6736','correctOnly','',''],
['7084','correctOnly','','']];

FOREACH NEWITEMDATA slice 1 IN ARRAY ITEMDATA

LOOP

FOR TASKVARIANTDATA_ROW IN
select distinct tv.id as taskvariantid,tv.externalid as cbtaskvariantid,
tv.maxscore,tv.scoringmethod,tv.scoringdata,tv.tasktypeid,tv.tasksubtypeid,tt.code as tasktypecode,tst.code as tasksubtypecode
from test t 
join testcollectionstests tct on t.id=tct.testid 
join testcollection tc on tc.id=tct.testcollectionid
join operationaltestwindowstestcollections otwtc on otwtc.testcollectionid = tc.id
join operationaltestwindow otw on otw.id = otwtc.operationaltestwindowid
join testsection ts on ts.testid = tct.testid
join testsectionstaskvariants tstv on tstv.testsectionid = ts.id 
join taskvariant tv on tv.id=tstv.taskvariantid
left join tasktype tt on tt.id=tv.tasktypeid
left join tasksubtype tst on tst.id=tv.tasksubtypeid
where tv.externalid=cast(NEWITEMDATA[1] as bigint)
and (otw.effectivedate <= '2015-05-15' and '2015-05-15' <= otw.expirydate) 
AND (otw.suspendwindow is null or otw.suspendwindow=false)

LOOP

IF NEWITEMDATA[2] IS NOT NULL AND NEWITEMDATA[2]<>'' THEN

NEWSCORINGMETHOD:=NEWITEMDATA[2];

END IF;

IF NEWITEMDATA[3] IS NOT NULL AND NEWITEMDATA[3]<>'' THEN

NEWSCORINGDATA:=NEWITEMDATA[3];

END IF;

IF NEWITEMDATA[4] IS NOT NULL AND NEWITEMDATA[4]<>'' THEN

NEWMAXSCORE:=cast(NEWITEMDATA[4] as BIGINT);

END IF;

insert into taskvariantrescore (taskvariantid,cbtaskvariantid,maxscore,scoringmethod,scoringdata,newmaxscore,newscoringmethod,newscoringdata,reason) values (TASKVARIANTDATA_ROW.taskvariantid,TASKVARIANTDATA_ROW.cbtaskvariantid,TASKVARIANTDATA_ROW.maxscore,TASKVARIANTDATA_ROW.scoringmethod,TASKVARIANTDATA_ROW.scoringdata,NEWMAXSCORE,NEWSCORINGMETHOD,NEWSCORINGDATA,UPDATEREASON);

IF NEWITEMDATA[2] IS NOT NULL AND NEWITEMDATA[2]<>'' THEN

update taskvariant set scoringmethod=NEWSCORINGMETHOD where id=TASKVARIANTDATA_ROW.taskvariantid;

END IF;

IF NEWITEMDATA[3] IS NOT NULL AND NEWITEMDATA[3]<>'' THEN

update taskvariant set scoringdata=NEWSCORINGDATA where id=TASKVARIANTDATA_ROW.taskvariantid;

END IF;

IF NEWITEMDATA[4] IS NOT NULL AND NEWITEMDATA[4]<>'' THEN

update taskvariant set maxscore=NEWMAXSCORE where id=TASKVARIANTDATA_ROW.taskvariantid;

END IF;

END LOOP;

END LOOP;

END$$;

