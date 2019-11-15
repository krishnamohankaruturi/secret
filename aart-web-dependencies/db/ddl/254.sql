
--254.sql

DO
$BODY$
BEGIN
	IF ((SELECT count(*) FROM information_schema.constraint_table_usage WHERE table_name = 'testsession' and constraint_name = 'uk_roster_name'
) = 0) THEN
		RAISE NOTICE  '%', 'Constraint uk_roster_name not found.';
	ELSE 
		ALTER TABLE testsession DROP constraint uk_roster_name;
		RAISE NOTICE  '%', 'Constraint uk_roster_name found. Droping the constraint.';
	END IF;
END;
$BODY$;