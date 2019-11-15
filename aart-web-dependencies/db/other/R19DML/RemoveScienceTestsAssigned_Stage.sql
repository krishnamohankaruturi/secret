DO $$DECLARE

TESTSESSION_ROW RECORD;

BEGIN

FOR TESTSESSION_ROW IN
select testsessionid from batchregistrationreason where batchregistrationid in (7206,7207,7208) and testsessionid is not null

LOOP

update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid=TESTSESSION_ROW.testsessionid);
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid=TESTSESSION_ROW.testsessionid;
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id=TESTSESSION_ROW.testsessionid;

END LOOP;

END$$;