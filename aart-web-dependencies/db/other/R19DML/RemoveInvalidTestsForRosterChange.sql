DO $$DECLARE

STUDENTTRACKERBAND_ROW RECORD;
STUDENTTRACKER_ROW RECORD;
COUNT BIGINT;
STBAND_COUNT BIGINT;

BEGIN

COUNT:=0;

FOR STUDENTTRACKER_ROW IN 
Select * from studenttracker where activeflag=true

LOOP

STBAND_COUNT:=(select count(*) from studenttrackerband where studenttrackerid=STUDENTTRACKER_ROW.id and activeflag=true and modifieddate>'2015-03-14');

IF STBAND_COUNT=1 THEN

select stb.testsessionid,ts.rosterid,st.status,er.activeflag INTO STUDENTTRACKERBAND_ROW 
from studenttrackerband stb, studentstests st, testsession ts, enrollmentsrosters er, 
enrollment e, studenttracker str 
where stb.testsessionid=st.testsessionid and stb.testsessionid=ts.id and ts.rosterid=er.rosterid
and stb.studenttrackerid=str.id and str.studentid=e.studentid and e.id=er.enrollmentid
and e.activeflag=true and e.currentschoolyear=2015
and stb.activeflag=true and stb.modifieddate>'2015-03-14'
and stb.studenttrackerid=STUDENTTRACKER_ROW.id;

IF STUDENTTRACKERBAND_ROW.activeflag=false and STUDENTTRACKERBAND_ROW.status=84 THEN

RAISE NOTICE 'Student Tracker Id,Test Session Id, %, %',STUDENTTRACKER_ROW.id,STUDENTTRACKERBAND_ROW.testsessionid;
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid=STUDENTTRACKERBAND_ROW.testsessionid;
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid=STUDENTTRACKERBAND_ROW.testsessionid);
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id=STUDENTTRACKERBAND_ROW.testsessionid;
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid=STUDENTTRACKERBAND_ROW.testsessionid;
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where id=STUDENTTRACKER_ROW.id;
COUNT:=COUNT+1;

END IF;

END IF;

END LOOP;

RAISE NOTICE 'Total Count, %',COUNT;

END$$;