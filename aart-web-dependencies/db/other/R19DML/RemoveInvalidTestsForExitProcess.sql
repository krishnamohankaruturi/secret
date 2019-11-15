DO $$DECLARE

STUDENTTRACKERBAND_ROW1 RECORD;
STUDENTTRACKERBAND_ROW2 RECORD;
STUDENTTRACKER_ROW RECORD;
STUDENTTEST_ROW1 RECORD;
STUDENTTEST_ROW2 RECORD;
COUNT BIGINT;

BEGIN

COUNT:=0;

FOR STUDENTTRACKER_ROW IN 
Select * from studenttracker where activeflag=true

LOOP

Select * into STUDENTTRACKERBAND_ROW1 from studenttrackerband where studenttrackerid=STUDENTTRACKER_ROW.id and activeflag=true and modifieddate>'2015-03-14' order by id desc limit 1;
Select * into STUDENTTRACKERBAND_ROW2 from studenttrackerband where studenttrackerid=STUDENTTRACKER_ROW.id and activeflag=true and modifieddate>'2015-03-14' order by modifieddate desc limit 1;

IF STUDENTTRACKERBAND_ROW1.testsessionid is not null 
and STUDENTTRACKERBAND_ROW2.testsessionid is not null THEN

IF STUDENTTRACKERBAND_ROW1.testsessionid=STUDENTTRACKERBAND_ROW2.testsessionid THEN

Select * into STUDENTTEST_ROW1 from studentstests where testsessionid=STUDENTTRACKERBAND_ROW1.testsessionid;

IF STUDENTTEST_ROW1.activeflag=false and STUDENTTRACKERBAND_ROW1.activeflag=true 
and STUDENTTEST_ROW1.status in (477,489,491) THEN

COUNT:=COUNT+1;
RAISE NOTICE 'Student Tracker Id,Student Tracker Band Id, %, %',STUDENTTRACKER_ROW.id,STUDENTTRACKERBAND_ROW1.id;
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id=STUDENTTRACKERBAND_ROW1.id;
update studenttracker set status='UNTRACKED' where id=STUDENTTRACKER_ROW.id;

END IF;

ELSE

Select * into STUDENTTEST_ROW1 from studentstests where testsessionid=STUDENTTRACKERBAND_ROW1.testsessionid;
Select * into STUDENTTEST_ROW2 from studentstests where testsessionid=STUDENTTRACKERBAND_ROW2.testsessionid;

IF STUDENTTEST_ROW1.activeflag=false and STUDENTTRACKERBAND_ROW1.activeflag=true 
and STUDENTTEST_ROW1.status in (477,489,491) AND STUDENTTEST_ROW2.activeflag=false 
and STUDENTTRACKERBAND_ROW2.activeflag=true 
and STUDENTTEST_ROW2.status in (477,489,491) THEN

COUNT:=COUNT+1;
RAISE NOTICE 'Student Tracker Id,Student Tracker Band Id1,Student Tracker Band Id2, %, %, %',STUDENTTRACKER_ROW.id,STUDENTTRACKERBAND_ROW1.id,STUDENTTRACKERBAND_ROW2.id;
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id=STUDENTTRACKERBAND_ROW1.id;
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id=STUDENTTRACKERBAND_ROW2.id;
update studenttracker set status='UNTRACKED' where id=STUDENTTRACKER_ROW.id;

END IF;

END IF;

END IF;

END LOOP;

RAISE NOTICE 'Total Count, %',COUNT;

END$$;