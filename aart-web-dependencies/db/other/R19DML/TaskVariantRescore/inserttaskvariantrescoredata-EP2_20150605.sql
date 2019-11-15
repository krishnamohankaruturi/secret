DO $$DECLARE

ITEMDATA text[][];
TASKVARIANTDATA_ROW RECORD;
NEWITEMDATA text[];
NEWMAXSCORE BIGINT;
NEWSCORINGMETHOD character varying(75);
NEWSCORINGDATA text;
UPDATEREASON character varying(250);

BEGIN

ITEMDATA:=ARRAY[['16048','','',''],
['16753','','',''],
['16768','','',''],
['17047','','',''],
['17531','','',''],
['17801','','',''],
['17834','','',''],
['23010','','',''],
['22098','','',''],
['4837','','',''],
['22162','','',''],
['17439','','',''],
['17322','','',''],
['17320','','',''],
['17314','','',''],
['17896','','',''],
['17064','','',''],
['16034','','',''],
['15988','','',''],
['16031','','',''],
['29091','','',''],
['29223','','',''],
['29351','','',''],
['29341','','',''],
['29373','','',''],
['29407','','',''],
['29425','','',''],
['29455','','',''],
['29656','','',''],
['29568','','',''],
['29740','','',''],
['29771','','',''],
['29673','','',''],
['29688','','',''],
['29700','','',''],
['30240','','',''],
['17574','','',''],
['17323','','',''],
['16920','','',''],
['31632','','',''],
['30568','','',''],
['34200','','',''],
['35832','','',''],
['35834','','',''],
['35836','','',''],
['36501','','',''],
['36908','','',''],
['22384','','',''],
['32837','','',''],
['29612','','',''],
['40626','','',''],
['40984','','',''],
['41236','','',''],
['41594','','',''],
['41595','','',''],
['41596','','',''],
['41718','','',''],
['42158','','',''],
['42159','','',''],
['42343','','',''],
['42344','','',''],
['42670','','',''],
['42671','','',''],
['42672','','',''],
['42697','','',''],
['42698','','',''],
['42699','','',''],
['42726','','',''],
['42728','','',''],
['42730','','',''],
['42748','','',''],
['42749','','',''],
['42707','','',''],
['42708','','',''],
['42709','','',''],
['42759','','',''],
['42760','','',''],
['42783','','',''],
['42784','','',''],
['42805','','',''],
['42828','','',''],
['42829','','',''],
['42862','','',''],
['42863','','',''],
['42837','','',''],
['42838','','',''],
['42839','','',''],
['35437','','',''],
['23979','','',''],
['29827','','',''],
['40526','','',''],
['42804','','',''],
['44007','','',''],
['44063','','',''],
['44065','','',''],
['44159','','',''],
['44161','','',''],
['44163','','',''],
['44187','','',''],
['44189','','',''],
['44221','','',''],
['44223','','',''],
['44225','','',''],
['28946','','',''],
['23653','','',''],
['23680','','',''],
['23341','','',''],
['47118','','',''],
['47116','','',''],
['15189','','',''],
['15190','','',''],
['15192','','',''],
['15193','','',''],
['47719','','',''],
['48086','','',''],
['48087','','',''],
['48091','','',''],
['48092','','',''],
['48096','','',''],
['48097','','',''],
['48112','','',''],
['48119','','',''],
['48123','','',''],
['48141','','',''],
['48142','','',''],
['48143','','',''],
['48144','','',''],
['48148','','',''],
['48149','','',''],
['48150','','',''],
['48151','','',''],
['48157','','',''],
['48158','','',''],
['48159','','',''],
['48160','','',''],
['48174','','',''],
['48179','','',''],
['48184','','',''],
['48207','','',''],
['48208','','',''],
['48209','','',''],
['48215','','',''],
['48216','','',''],
['48217','','',''],
['48223','','',''],
['48224','','',''],
['48225','','',''],
['48244','','',''],
['48246','','',''],
['48252','','',''],
['48253','','',''],
['48259','','',''],
['48260','','',''],
['48278','','',''],
['48279','','',''],
['48285','','',''],
['48286','','',''],
['48292','','',''],
['48293','','',''],
['48324','','',''],
['48325','','',''],
['48326','','',''],
['48340','','',''],
['48341','','',''],
['48342','','',''],
['48350','','',''],
['48351','','',''],
['48352','','',''],
['48381','','',''],
['48382','','',''],
['48383','','',''],
['48396','','',''],
['48397','','',''],
['48398','','',''],
['48411','','',''],
['48413','','',''],
['48414','','',''],
['48520','','',''],
['48521','','',''],
['47065','','',''],
['50164','','',''],
['48444','','',''],
['50476','','',''],
['2140','','',''],
['50499','','',''],
['50504','','',''],
['50510','','',''],
['50512','','',''],
['50513','','',''],
['50514','','',''],
['50515','','',''],
['50516','','',''],
['50517','','',''],
['50518','','',''],
['50521','','',''],
['50522','','',''],
['50641','','',''],
['50721','','',''],
['50724','','',''],
['50725','','',''],
['50726','','',''],
['50729','','',''],
['51044','','',''],
['51045','','',''],
['34615','','',''],
['27709','','','']];

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

IF NEWITEMDATA[1]='27709' THEN

UPDATEREASON:='miskey - menu2 should be "accepted"';

ELSE

UPDATEREASON:='Responseids are numbers instead of string in scoring json';

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

