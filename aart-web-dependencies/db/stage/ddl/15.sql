-- stage/ddl/15.sql

-- US17717, adding questar columns
DO
$BODY$
BEGIN
	IF EXISTS (
		SELECT column_name 
		FROM information_schema.columns 
		WHERE table_name='questar_staging' AND column_name='sccode'
	) THEN
		RAISE NOTICE 'sccode found, skipping add';
	ELSE
		ALTER TABLE questar_staging ADD COLUMN sccode CHARACTER VARYING (10);
	END IF;
	
	IF EXISTS (
		SELECT column_name 
		FROM information_schema.columns 
		WHERE table_name='questar_staging' AND column_name='accommodation'
	) THEN
		RAISE NOTICE 'accommodation found, skipping add';
	ELSE
		ALTER TABLE questar_staging ADD COLUMN accommodation CHARACTER VARYING (10);
	END IF;
END
$BODY$;