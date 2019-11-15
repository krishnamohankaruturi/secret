--ddl/559.sql US18351 - flags for handling SC codes and incompletes

ALTER TABLE studentreport ADD COLUMN suppressmdptScore boolean,
			ADD COLUMN suppressmainscalescoreprfrmlevel boolean,
			ADD COLUMN suppresscombinedscore boolean,
			ADD COLUMN aggregatemdptscore boolean,
			ADD COLUMN aggregatemainscalescorePrfrmLevel boolean,
			ADD COLUMN aggregatecombinedlevel boolean,
			ADD COLUMN aggregatesubscore boolean;
			
DROP INDEX IF EXISTS idx_studentreport_suppressmdptScore; 			  
CREATE INDEX idx_studentreport_suppressmdptScore
  ON studentreport
  USING btree
  (suppressmdptScore);

DROP INDEX IF EXISTS idx_studentreport_suppressmsscoreprfrmlevel; 			  
CREATE INDEX idx_studentreport_suppressmsscoreprfrmlevel
  ON studentreport
  USING btree
  (suppressmainscalescoreprfrmlevel);

DROP INDEX IF EXISTS idx_studentreport_suppresscombinedscore; 			  
CREATE INDEX idx_studentreport_suppresscombinedscore
  ON studentreport
  USING btree
  (suppresscombinedscore);

DROP INDEX IF EXISTS idx_studentreport_aggregatemdptscore; 			  
CREATE INDEX idx_studentreport_aggregatemdptscore
  ON studentreport
  USING btree
  (aggregatemdptscore);

DROP INDEX IF EXISTS idx_studentreport_aggrmainssscorePrfrmLevel; 			  
CREATE INDEX idx_studentreport_aggrmainssscorePrfrmLevel
  ON studentreport
  USING btree
  (aggregatemainscalescorePrfrmLevel);

DROP INDEX IF EXISTS idx_studentreport_aggregatecombinedlevel; 			  
CREATE INDEX idx_studentreport_aggregatecombinedlevel
  ON studentreport
  USING btree
  (aggregatecombinedlevel);

DROP INDEX IF EXISTS idx_studentreport_aggregatesubscore; 			  
CREATE INDEX idx_studentreport_aggregatesubscore
  ON studentreport
  USING btree
  (aggregatesubscore);


-- US18331, student report transfer and aggregation columns
DO
$BODY$
BEGIN
	IF EXISTS (
		SELECT column_name
		FROM information_schema.columns
		WHERE table_name = 'studentreport' AND column_name = 'transferred'
	) THEN
		RAISE NOTICE 'studentreport.transferred already found, skipping add';
	ELSE
		ALTER TABLE IF EXISTS studentreport ADD COLUMN transferred BOOLEAN;
	END IF;
	
	IF EXISTS (
		SELECT column_name
		FROM information_schema.columns
		WHERE table_name = 'studentreport' AND column_name = 'aggregatetodistrict'
	) THEN
		RAISE NOTICE 'studentreport.aggregatetodistrict already found, skipping add';
	ELSE
		ALTER TABLE IF EXISTS studentreport ADD COLUMN aggregatetodistrict BOOLEAN;
	END IF;
	
	IF EXISTS (
		SELECT column_name
		FROM information_schema.columns
		WHERE table_name = 'studentreport' AND column_name = 'aggregatetoschool'
	) THEN
		RAISE NOTICE 'studentreport.aggregatetoschool already found, skipping add';
	ELSE
		ALTER TABLE IF EXISTS studentreport ADD COLUMN aggregatetoschool BOOLEAN;
	END IF;
	
	DROP INDEX IF EXISTS idx_studentreport_transferred;
	CREATE INDEX idx_studentreport_transferred ON studentreport
		USING btree	(transferred);
	
	DROP INDEX IF EXISTS idx_studentreport_aggregatetodistrict;
	CREATE INDEX idx_studentreport_aggregatetodistrict ON studentreport
		USING btree	(aggregatetodistrict);
	
	DROP INDEX IF EXISTS idx_studentreport_aggregatetoschool;
	CREATE INDEX idx_studentreport_aggregatetoschool ON studentreport
		USING btree	(aggregatetoschool);
END
$BODY$;

--US18774: Reports: Upload subscore descriptions - add indentation option
alter table subscoresdescription add column indentneededflag boolean;
