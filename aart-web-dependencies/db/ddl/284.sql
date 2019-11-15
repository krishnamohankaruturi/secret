ALTER TABLE subjectarea ADD COLUMN activeflag boolean;
ALTER TABLE subjectarea ALTER COLUMN activeflag SET DEFAULT true;

DO
$BODY$
BEGIN
	IF ((SELECT count(*) FROM information_schema.constraint_table_usage WHERE table_name = 'subjectarea' and constraint_name = 'uk_subjectarea_subjectareacode'
) = 0) THEN
		RAISE NOTICE  '%', 'Constraint uk_subjectarea_subjectareacode not found.';
	ELSE 
		ALTER TABLE subjectarea DROP CONSTRAINT uk_subjectarea_subjectareacode;
		RAISE NOTICE  '%', 'Constraint uk_subjectarea_subjectareacode found. Droping the constraint.';
		ALTER TABLE subjectarea
			ADD CONSTRAINT uk_subjectarea_subjectareacode UNIQUE(subjectareacode, activeflag);
	END IF;
END;
$BODY$;