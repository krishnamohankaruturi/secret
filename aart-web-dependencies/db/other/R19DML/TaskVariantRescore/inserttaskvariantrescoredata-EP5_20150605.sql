DO $$DECLARE

ITEMDATA text[][];
TASKVARIANTDATA_ROW RECORD;
NEWITEMDATA text[];
NEWMAXSCORE BIGINT;
NEWSCORINGMETHOD character varying(75);
NEWSCORINGDATA text;
UPDATEREASON character varying(250);

BEGIN

UPDATEREASON:='Changed the scoring data';

ITEMDATA:=ARRAY[['28284','','{"correctResponse":{"responses":[{"responseoption":"Response Option 2","responseid":"104532","index":"0"},{"responseoption":"Response Option 5","responseid":"104540","index":"1"}],"score":1},"partialResponses":[]}',''],
['9080','','{"correctResponses":[{"score":1,"responses":[{"index":0,"answer":{"parallel":{"consider":false,"tolerance":"0"},"intersecting":{"consider":false,"tolerance":"0","y":"","x":""},"numberOfLines":"1","perpendicular":{"consider":false,"tolerance":"0","y":"","x":""},"lines":[{"xIntercept":{"min":"1.5","max":"1.5","consider":true,"tolerance":"0"},"yIntercept":{"min":"3","max":"3","consider":true,"tolerance":"0"},"start":{"consider":false,"tolerance":"0","y":"","x":""},"slope":{"min":"2","max":"2","consider":true,"tolerance":"0"},"end":{"consider":false,"tolerance":"0","y":"","x":""}}]}}]}]}',''],
['9218','','{"correctResponses":[{"score":1,"responses":[{"index":0,"answer":{"parallel":{"consider":false,"tolerance":"0"},"intersecting":{"consider":false,"tolerance":"0","y":"","x":""},"numberOfLines":"1","perpendicular":{"consider":false,"tolerance":"0","y":"","x":""},"lines":[{"xIntercept":{"min":"0","max":"0","consider":true,"tolerance":"0"},"yIntercept":{"min":"0","max":"0","consider":true,"tolerance":"0"},"start":{"consider":false,"tolerance":"0","y":"","x":""},"slope":{"min":"1.25","max":"1.25","consider":true,"tolerance":"0"},"end":{"consider":false,"tolerance":"0","y":"","x":""}}]}}]}]}',''],
['30462','','{"correctResponses":[{"score":1,"responses":[{"index":0,"answer":{"parallel":{"consider":false,"tolerance":"0"},"intersecting":{"consider":false,"tolerance":"0","y":"","x":""},"numberOfLines":"1","perpendicular":{"consider":false,"tolerance":"0","y":"","x":""},"lines":[{"xIntercept":{"min":"16","max":"16","consider":true,"tolerance":"0"},"yIntercept":{"min":"8","max":"8","consider":true,"tolerance":"0"},"start":{"consider":false,"tolerance":"0","y":"","x":""},"slope":{"min":"-.5","max":"-.5","consider":true,"tolerance":"0"},"end":{"consider":false,"tolerance":"0","y":"","x":""}}]}}]}]}',''],
['30716','','{"correctResponses":[{"score":1,"responses":[{"index":0,"answer":{"tolerance":"0.1","y":"-3","x":"5"}}]}],"partialResponses":[{"index":0,"answer":{"tolerance":"0.1","y":"-3","x":"5"},"score":"1.0"}]}',''],
['35354','','{"correctResponses":[{"score":1,"responses":[{"index":0,"answer":{"parallel":{"consider":false,"tolerance":"0"},"intersecting":{"consider":false,"tolerance":"0","y":"","x":""},"numberOfLines":"1","perpendicular":{"consider":false,"tolerance":"0","y":"","x":""},"lines":[{"xIntercept":{"min":"0","max":"0","consider":true,"tolerance":"0"},"yIntercept":{"min":"0","max":"0","consider":true,"tolerance":"0"},"start":{"consider":true,"tolerance":"0","y":"","x":""},"slope":{"min":"0.03","max":"0.03","consider":true,"tolerance":"0"},"end":{"consider":false,"tolerance":"0","y":"","x":""}}]}}]}]}',''],
['31126','','{"correctResponses":[{"score":1,"responses":[{"index":0,"foilid":"ikpYKJxgZEoVI2TO-foil0","dropid":"ikpYKJxgZEoVI2TO-drop5"},{"index":1,"foilid":"ikpYKJxgZEoVI2TO-foil0","dropid":"ikpYKJxgZEoVI2TO-drop10"},{"index":2,"foilid":"ikpYKJxgZEoVI2TO-foil0","dropid":"ikpYKJxgZEoVI2TO-drop11"},{"index":3,"foilid":"ikpYKJxgZEoVI2TO-foil0","dropid":"ikpYKJxgZEoVI2TO-drop12"},{"index":4,"foilid":"ikpYKJxgZEoVI2TO-foil0","dropid":"ikpYKJxgZEoVI2TO-drop17"},{"index":5,"foilid":"ikpYKJxgZEoVI2TO-foil0","dropid":"ikpYKJxgZEoVI2TO-drop18"}]}],"partialResponses":[{"index":0,"score":"0.04761905","foilid":"ikpYKJxgZEoVI2TO-foil0","dropid":"ikpYKJxgZEoVI2TO-drop5"},{"index":1,"score":"0.04761905","foilid":"ikpYKJxgZEoVI2TO-foil0","dropid":"ikpYKJxgZEoVI2TO-drop10"},{"index":2,"score":"0.04761905","foilid":"ikpYKJxgZEoVI2TO-foil0","dropid":"ikpYKJxgZEoVI2TO-drop11"},{"index":3,"score":"0.04761905","foilid":"ikpYKJxgZEoVI2TO-foil0","dropid":"ikpYKJxgZEoVI2TO-drop12"},{"index":4,"score":"0.04761905","foilid":"ikpYKJxgZEoVI2TO-foil0","dropid":"ikpYKJxgZEoVI2TO-drop17"},{"index":5,"score":"0.04761905","foilid":"ikpYKJxgZEoVI2TO-foil0","dropid":"ikpYKJxgZEoVI2TO-drop18"}]}',''],
['35554','','{"correctResponses":[{"score":1,"responses":[{"index":0,"answer":{"tolerance":"0.1","y":"3","x":"-5"}},{"index":1,"answer":{"tolerance":"0.1","y":"3","x":"-1"}},{"index":2,"answer":{"tolerance":"0.1","y":"-1","x":"-1"}},{"index":3,"answer":{"tolerance":"0.1","y":"-1","x":"-5"}}]}],"partialResponses":[{"index":0,"answer":{"tolerance":"0.1","y":"3","x":"-5"},"score":"0.25"},{"index":1,"answer":{"tolerance":"0.1","y":"3","x":"-1"},"score":"0.25"},{"index":2,"answer":{"tolerance":"0.1","y":"-1","x":"-1"},"score":"0.25"},{"index":3,"answer":{"tolerance":"0.1","y":"-1","x":"-5"},"score":"0.25"}]}','']];

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
and (otw.effectivedate <= '2015-05-15' and '2015-05-01' <= otw.expirydate and otw.expirydate<'2015-05-15') 
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

