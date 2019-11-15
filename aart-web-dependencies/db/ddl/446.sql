--446.sql
--operationaltestwindowsessionrule clean up
ALTER TABLE operationaltestwindowsessionrule RENAME CONSTRAINT fk_test_collections_session_rules_updusr TO fk_otw_session_rule_updusr;
ALTER TABLE operationaltestwindowsessionrule RENAME CONSTRAINT test_collections_session_rules_session_rule_id_fkey TO fk_otw_session_rule_session_rule_id;
ALTER TABLE operationaltestwindowsessionrule RENAME CONSTRAINT fk_test_collections_session_rules_crdusr TO fk_otw_session_rule_crdusr;
ALTER TABLE operationaltestwindowsessionrule DROP CONSTRAINT IF EXISTS test_collections_session_rules_test_collection_id_fkey;
ALTER TABLE operationaltestwindowsessionrule DROP CONSTRAINT IF EXISTS test_collections_session_rules_pkey;
ALTER TABLE operationaltestwindowsessionrule ADD CONSTRAINT fk_otw_session_rule_otw FOREIGN KEY (operationaltestwindowid) 
      REFERENCES operationaltestwindow (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE operationaltestwindowsessionrule ADD COLUMN id BIGSERIAL PRIMARY KEY;      
ALTER TABLE operationaltestwindowsessionrule DROP CONSTRAINT IF EXISTS operationaltestwindowsessionrule_pkey;

DO
$BODY$ 
DECLARE
	DISTINCTOTWRECORD RECORD;
	DISTINCTOTWSRRECORD RECORD;
BEGIN           
	FOR DISTINCTOTWRECORD IN                
		select distinct operationaltestwindowid from operationaltestwindowsessionrule order by operationaltestwindowid
	LOOP 		
			FOR DISTINCTOTWSRRECORD IN                
				select distinct sessionruleid from operationaltestwindowsessionrule 
					where operationaltestwindowid = DISTINCTOTWRECORD.operationaltestwindowid
			LOOP 	
				delete from operationaltestwindowsessionrule 
				where operationaltestwindowid = DISTINCTOTWRECORD.operationaltestwindowid
				and sessionruleid = DISTINCTOTWSRRECORD.sessionruleid 
				and id not in(
					select id from operationaltestwindowsessionrule 
					where operationaltestwindowid = DISTINCTOTWRECORD.operationaltestwindowid
						and sessionruleid = DISTINCTOTWSRRECORD.sessionruleid and activeflag is true order by modifieddate desc limit 1);
			END LOOP;			
	END LOOP;
END;
$BODY$;

ALTER TABLE operationaltestwindowsessionrule ADD CONSTRAINT pk_otw_session_rule PRIMARY KEY(operationaltestwindowid, sessionruleid);
ALTER TABLE operationaltestwindowsessionrule DROP COLUMN IF EXISTS id;
ALTER TABLE operationaltestwindowsessionrule DROP COLUMN IF EXISTS testcollectionid;

--CB chnages from change pond
ALTER TABLE testpanelstage RENAME COLUMN inuse TO activeflag;
ALTER TABLE testpanelstagemapping RENAME COLUMN inuse TO activeflag;
ALTER TABLE testpanelstagetestcollection RENAME COLUMN inuse TO activeflag;

