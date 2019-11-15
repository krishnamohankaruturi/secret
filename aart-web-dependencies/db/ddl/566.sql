--US18374, subscore ratings for school/district/state reports
DO
$BODY$
BEGIN
	IF EXISTS (
		SELECT column_name
		FROM information_schema.columns
		WHERE table_name = 'reportsmedianscore' AND column_name = 'rating'
	) THEN
		RAISE NOTICE 'reportsmedianscore.rating already found, skipping add';
	ELSE
		ALTER TABLE IF EXISTS reportsmedianscore ADD COLUMN rating INTEGER;
	END IF;
	
	DROP INDEX IF EXISTS idx_reportsmedianscore_rating;
	CREATE INDEX idx_reportsmedianscore_rating ON reportsmedianscore
		USING btree (rating);
END
$BODY$;


