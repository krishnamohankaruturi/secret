-- ddl/531.sql

-- US17717, adding column for paper/pencil SC code mapping
DO
$BODY$
BEGIN
	IF EXISTS (
		SELECT column_name 
		FROM information_schema.columns 
		WHERE table_name='statespecialcircumstance' AND column_name='paperpencilcode'
	) THEN
		RAISE NOTICE 'paperpencilcode found, skipping add';
	ELSE
		ALTER TABLE statespecialcircumstance ADD COLUMN paperpencilcode CHARACTER VARYING (10);
	END IF;
END
$BODY$;