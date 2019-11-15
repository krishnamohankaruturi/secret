--dml/543.sql

DO
$BODY$
DECLARE
	now_gmt TIMESTAMP WITH TIME ZONE;
	cetesysadminid BIGINT;
	
	data TEXT[][] := ARRAY[
		['BL', 'Blank', 'The response is blank.'],
		['IN', 'Insufficient', 'The response does not include enough student writing to score.'],
		['OT', 'Off Task', 'The response is unrelated to the resources and/or prompt.'],
		['OL', 'Other Language', 'The response is in a language other than English.']
	];
BEGIN
	SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
	SELECT NOW() AT TIME ZONE 'GMT' INTO now_gmt;
	
	FOR i IN array_lower(data, 1) .. array_upper(data, 1) LOOP
		IF NOT EXISTS (
			SELECT 1
			FROM nonscorablecode
			WHERE activeflag = TRUE
			AND code = data[i][1]
		) THEN
			INSERT INTO nonscorablecode
				(code, name, description, activeflag, createddate, createduser, modifieddate, modifieduser)
				VALUES (
					data[i][1],
					data[i][2],
					data[i][3],
					TRUE,
					now_gmt,
					cetesysadminid,
					now_gmt,
					cetesysadminid
				);
		ELSE
			RAISE NOTICE 'Found % in nonscorablecode, skipping...', data[i][1];
		END IF;
	END LOOP;
END;
$BODY$;