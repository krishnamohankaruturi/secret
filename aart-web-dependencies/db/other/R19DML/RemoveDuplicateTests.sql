DO $$DECLARE

STUDENTTRACKERBAND_ROW RECORD;
STUDENTTEST_ROW RECORD;
STUDENTTRACKER_ROW RECORD;
COUNT BIGINT;

BEGIN

FOR STUDENTTRACKER_ROW IN 
select * from (
  SELECT studenttrackerid,
  ROW_NUMBER() OVER(PARTITION BY studenttrackerid ORDER BY studenttrackerid asc) AS Row
  FROM studenttrackerband stb, studentstests st where stb.testsessionid is not null 
  and stb.activeflag=true and stb.testsessionid=st.testsessionid 
  and st.activeflag=true and st.status in (84,85) and stb.modifieddate>'2015-03-14'
) dups
where 
dups.Row > 1

LOOP

COUNT:=0;

FOR STUDENTTRACKERBAND_ROW IN
select stb.id,stb.testsessionid,st.status from studenttrackerband stb,studentstests st where stb.testsessionid=st.testsessionid 
and stb.activeflag=true and st.activeflag=true and stb.studenttrackerid=STUDENTTRACKER_ROW.studenttrackerid order by stb.id

LOOP

IF STUDENTTRACKERBAND_ROW.status=84 AND COUNT=0 THEN

COUNT:=COUNT+1;

update studenttrackerband set activeflag=false where id=STUDENTTRACKERBAND_ROW.id;
update studentstestsections set activeflag=false where studentstestid in (select id from studentstests where testsessionid=STUDENTTRACKERBAND_ROW.testsessionid);
update studentstests set activeflag=false where testsessionid=STUDENTTRACKERBAND_ROW.testsessionid;
update testsession set activeflag=false where id=STUDENTTRACKERBAND_ROW.testsessionid;

END IF;

END LOOP;

END LOOP;

update studenttrackerband set activeflag=false where testsessionid is null and activeflag=true and createddate<'2015-03-14';

END$$;