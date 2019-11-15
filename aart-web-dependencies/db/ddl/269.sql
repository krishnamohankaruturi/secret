
--PD changes
CREATE TABLE testletsensitivitytag
(
  testletid bigint NOT NULL,
  sensitivitytagid bigint NOT NULL,
  CONSTRAINT testletsensitivitytag_pkey PRIMARY KEY (testletid, sensitivitytagid),
  CONSTRAINT testletsensitivitytag_testletid_fk FOREIGN KEY (testletid)
      REFERENCES testlet (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testletsensitivitytag_sensitivitytagid_fk FOREIGN KEY (sensitivitytagid)
      REFERENCES category (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

ALTER TABLE modulestate ADD COLUMN ceu integer;
ALTER TABLE module ADD COLUMN passingscore integer;
ALTER TABLE test ADD COLUMN maxscore integer;
ALTER TABLE usermodule ADD COLUMN stateid bigint;

CREATE OR REPLACE FUNCTION calculate_max_score(publishedtestid bigint)
  RETURNS numeric AS
$BODY$ 

DECLARE 
        TVROW RECORD;
	max_score integer = 0;
	
	BEGIN
		RAISE INFO 'BEGIN CALCULATE MAX SCORE';

		FOR TVROW IN (select tv.id, tv.maxscore 
				from testsection ts
				inner join testsectionstaskvariants tstv on tstv.testsectionid = ts.id 
				inner join taskvariant tv on (tstv.taskvariantid = tv.id and tv.scoringneeded IS true)
				where ts.testid = publishedtestid ) LOOP
				
			--RAISE NOTICE  '%,%', TVROW.id, TVROW.maxscore;                                       
			max_score = max_score + TVROW.maxscore;
			--RAISE NOTICE  'Current MAX Score %', max_score;
		    END LOOP;

		RAISE NOTICE  'Total MAX Score %', max_score;
		RETURN cast(max_score as numeric);
 END;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;