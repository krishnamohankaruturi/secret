--444.sql

--US10444
alter table testcollectionssessionrules add column operationaltestwindowid bigint;

DO
$BODY$ 
DECLARE
	DISTINCTTCRECORD RECORD;
BEGIN           
	FOR DISTINCTTCRECORD IN                
		select distinct testcollectionid from testcollectionssessionrules
	LOOP 
		update testcollectionssessionrules set operationaltestwindowid = (
					select operationaltestwindowid from operationaltestwindowstestcollections 
						where testcollectionid = DISTINCTTCRECORD.testcollectionid order by modifieddate desc limit 1)
			where testcollectionid=DISTINCTTCRECORD.testcollectionid;			
	END LOOP;
END;
$BODY$;
ALTER TABLE testcollectionssessionrules DROP CONSTRAINT test_collections_session_rules_pkey;
delete from testcollectionssessionrules where operationaltestwindowid is null;
alter table testcollectionssessionrules rename to operationaltestwindowsessionrule;
