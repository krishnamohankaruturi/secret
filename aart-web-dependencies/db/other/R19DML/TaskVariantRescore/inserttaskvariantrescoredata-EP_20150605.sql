DO $$DECLARE

ITEMDATA text[][];
TASKVARIANTDATA_ROW RECORD;
NEWITEMDATA text[];
NEWMAXSCORE BIGINT;
NEWSCORINGMETHOD character varying(75);
NEWSCORINGDATA text;
UPDATEREASON character varying(250);

BEGIN

UPDATEREASON:='Changed scoring method from Correct-Only to Partical-Credit';

ITEMDATA:=ARRAY[['8438','partialcredit','',''],
['13372','partialcredit','',''],
['20948','partialcredit','',''],
['21902','partialcredit','',''],
['21973','partialcredit','',''],
['22686','partialcredit','',''],
['23064','partialcredit','',''],
['23557','partialcredit','',''],
['23599','partialcredit','',''],
['23605','partialcredit','',''],
['23634','partialcredit','',''],
['25403','partialcredit','',''],
['26866','partialcredit','',''],
['26932','partialcredit','',''],
['26941','partialcredit','',''],
['26982','partialcredit','',''],
['28431','partialcredit','',''],
['28763','partialcredit','',''],
['30029','partialcredit','',''],
['30790','partialcredit','',''],
['35017','partialcredit','',''],
['8440','partialcredit','{"correctResponses":[{"score":1,"responses":[{"index":0,"value":"6 × 4","foilid":"menu1"},{"index":1,"value":"6 × 70","foilid":"menu2"},{"index":2,"value":"20 × 4","foilid":"menu3"},{"index":3,"value":"20 × 70","foilid":"menu4"}]}],"partialResponses":[{"index":0,"score":"0.25","value":"6 × 4","foilid":"menu1"},{"index":1,"score":"0.25","value":"6 × 70","foilid":"menu2"},{"index":2,"score":"0.25","value":"20 × 4","foilid":"menu3"},{"index":3,"score":"0.25","value":"20 × 70","foilid":"menu4"}]}',''],
['6092','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response 2","responseid":"25602","index":"0"},{"responseoption":"Response 3","responseid":"25605","index":"1"},{"responseoption":"Response 6","responseid":"25604","index":"2"}],"score":1},"partialResponses":[{"responseoption":"Response 2","responseid":"25602","index":"0","score":"0.333","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response 3","responseid":"25605","index":"1","score":"0.333","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response 6","responseid":"25604","index":"2","score":"0.333","correctResponseFlag":"true","quota":"-1"}]}',''],
['6235','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response 2","responseid":"26162","index":"0"},{"responseoption":"Response 4","responseid":"26161","index":"1"}],"score":1},"partialResponses":[ {"responseoption":"Response 2","responseid":"26162","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response 4","responseid":"26161","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['6373','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response 2","responseid":"26657","index":"0"},{"responseoption":"Response 4","responseid":"26656","index":"1"}],"score":1},"partialResponses":[{"responseoption":"Response 2","responseid":"26657","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response 4","responseid":"26656","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['6402','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response 1","responseid":"26765","index":"0"},{"responseoption":"Response 5","responseid":"26762","index":"1"}],"score":1},"partialResponses":[{"responseoption":"Response 1","responseid":"26765","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response 5","responseid":"26762","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['6518','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response 4","responseid":"27171","index":"0"},{"responseoption":"Response 6","responseid":"27172","index":"1"}],"score":1},"partialResponses":[{"responseoption":"Response 4","responseid":"27171","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response 6","responseid":"27172","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['6720','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response 2","responseid":"27904","index":"0"},{"responseoption":"Response 3","responseid":"27901","index":"1"},{"responseoption":"Response 5","responseid":"27902","index":"2"}],"score":1},"partialResponses":[{"responseoption":"Response 2","responseid":"27904","index":"0","score":"0.333","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response 3","responseid":"27901","index":"1","score":"0.333","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response 5","responseid":"27902","index":"2","score":"0.333","correctResponseFlag":"true","quota":"-1"}]}',''],
['20432','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 3","responseid":"75342","index":"2"},{"responseoption":"Response Option 5","responseid":"75343","index":"4"}],"score":1},"partialResponses":[{"responseoption":"Response Option 3","responseid":"75342","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 5","responseid":"75343","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['20623','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 2","responseid":"76061","index":"1"},{"responseoption":"Response Option 3","responseid":"76059","index":"2"}],"score":1},"partialResponses":[{"responseoption":"Response Option 2","responseid":"76061","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 3","responseid":"76059","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['22384','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 4","responseid":83024,"index":0},{"responseoption":"Response Option 6","responseid":83043,"index":1}],"score":1},"partialResponses":[{"responseoption":"Response Option 4","responseid":83024,"index":0,"score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 6","responseid":83043,"index":1,"score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['23113','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 1","responseid":"85831","index":"0"},{"responseoption":"Response Option 4","responseid":"85832","index":"1"}],"score":1},"partialResponses":[{"responseoption":"Response Option 1","responseid":"85831","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 4","responseid":"85832","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['23617','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 2","responseid":"87851","index":"1"},{"responseoption":"Response Option 5","responseid":"87853","index":"4"}],"score":1},"partialResponses":[{"responseoption":"Response Option 2","responseid":"87851","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 5","responseid":"87853","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['26908','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 2","responseid":"99572","index":"1"},{"responseoption":"Response Option 5","responseid":"99569","index":"4"}],"score":1},"partialResponses":[{"responseoption":"Response Option 2","responseid":"99572","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 5","responseid":"99569","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['28328','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 1","responseid":"104690","index":"0"},{"responseoption":"Response Option 2","responseid":"104687","index":"1"}],"score":1},"partialResponses":[{"responseoption":"Response Option 1","responseid":"104690","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 2","responseid":"104687","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['29325','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 1","responseid":"108415","index":"0"},{"responseoption":"Response Option 3","responseid":"108416","index":"2"}],"score":1},"partialResponses":[{"responseoption":"Response Option 1","responseid":"108415","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 3","responseid":"108416","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['32685','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 2","responseid":"120864","index":"1"},{"responseoption":"Response Option 4","responseid":"120868","index":"3"}],"score":1},"partialResponses":[{"responseoption":"Response Option 2","responseid":"120864","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 4","responseid":"120868","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['32717','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 4","responseid":"120987","index":"3"},{"responseoption":"Response Option 5","responseid":"120983","index":"4"}],"score":1},"partialResponses":[{"responseoption":"Response Option 4","responseid":"120987","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 5","responseid":"120983","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['33254','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 3","responseid":"122763","index":"2"},{"responseoption":"Response Option 5","responseid":"122764","index":"4"}],"score":1},"partialResponses":[{"responseoption":"Response Option 3","responseid":"122763","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 5","responseid":"122764","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['33969','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 1","responseid":"125169","index":"0"},{"responseoption":"Response Option 4","responseid":"125168","index":"3"}],"score":1},"partialResponses":[{"responseoption":"Response Option 1","responseid":"125169","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 4","responseid":"125168","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['34016','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 2","responseid":"125349","index":"1"},{"responseoption":"Response Option 5","responseid":"125350","index":"4"}],"score":1},"partialResponses":[{"responseoption":"Response Option 2","responseid":"125349","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 5","responseid":"125350","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['34930','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 3","responseid":"128684","index":"0"},{"responseoption":"Response Option 5","responseid":"128696","index":"1"}],"score":1},"partialResponses":[{"responseoption":"Response Option 3","responseid":"128684","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 5","responseid":"128696","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['34965','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 3","responseid":"128803","index":"2"},{"responseoption":"Response Option 4","responseid":"128801","index":"3"}],"score":1},"partialResponses":[{"responseoption":"Response Option 3","responseid":"128803","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 4","responseid":"128801","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}',''],
['36059','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 3","responseid":"132920","index":"2"},{"responseoption":"Response Option 5","responseid":"132922","index":"4"},{"responseoption":"Response Option 6","responseid":"132918","index":"5"}],"score":1},"partialResponses":[{"responseoption":"Response Option 3","responseid":"132920","index":"0","score":"0.333","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 5","responseid":"132922","index":"1","score":"0.333","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 6","responseid":"132918","index":"2","score":"0.333","correctResponseFlag":"true","quota":"-1"}]}',''],
['36281','partialcredit','{"correctResponse":{"responses":[{"responseoption":"Response Option 2","responseid":"133721","index":"1"},{"responseoption":"Response Option 4","responseid":"133719","index":"3"}],"score":1},"partialResponses":[{"responseoption":"Response Option 2","responseid":"133721","index":"0","score":"0.500","correctResponseFlag":"true","quota":"-1"},{"responseoption":"Response Option 4","responseid":"133719","index":"1","score":"0.500","correctResponseFlag":"true","quota":"-1"}]}','']];

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

