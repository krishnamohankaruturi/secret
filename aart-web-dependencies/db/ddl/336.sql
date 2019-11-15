-- 335.sql

DO
$BODY$
BEGIN
	IF ((SELECT count(*) FROM information_schema.constraint_table_usage WHERE table_name = 'surveypagestatus' and constraint_name = 'uk_surveyidpagenum') = 0) THEN
		ALTER TABLE surveypagestatus ADD CONSTRAINT uk_surveyidpagenum UNIQUE (surveyid, globalpagenum);
	ELSE 
		RAISE NOTICE  '%', 'Constraint uk_surveyidpagenum on surveypagestatus already exists.';
	END IF;
END;
$BODY$;