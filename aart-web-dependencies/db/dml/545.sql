--dlm/544.sql

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
	
	IF NOT EXISTS (
		SELECT 1
		FROM categorytype
		WHERE activeflag = TRUE
		AND typecode = 'NON_SCORABLE_CODE'
	) THEN
		INSERT INTO categorytype (typename, typecode, typedescription, originationcode, activeflag, createddate, createduser, modifieddate, modifieduser)
			VALUES (
				'Non-Scorable Code',
				'NON_SCORABLE_CODE',
				'Non-Scorable Code',
				'AART_ORIG_CODE',
				TRUE,
				now_gmt,
				cetesysadminid,
				now_gmt,
				cetesysadminid
			);
	ELSE
		RAISE NOTICE 'Found NON_SCORABLE_CODE in categorytype, skipping...';
	END IF;
	
	FOR i IN array_lower(data, 1) .. array_upper(data, 1) LOOP
		IF NOT EXISTS (
			SELECT 1
			FROM category
			WHERE activeflag = TRUE
			AND categorycode = data[i][1]
			AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'NON_SCORABLE_CODE')
		) THEN
			INSERT INTO category
				(categoryname, categorycode, categorydescription, categorytypeid, activeflag, createddate, createduser, modifieddate, modifieduser)
				VALUES (
					data[i][2],
					data[i][1],
					data[i][3],
					(SELECT id FROM categorytype WHERE activeflag = TRUE AND typecode = 'NON_SCORABLE_CODE'),
					TRUE,
					now_gmt,
					cetesysadminid,
					now_gmt,
					cetesysadminid
				);
		ELSE
			RAISE NOTICE 'Found % in category, skipping...', data[i][1];
		END IF;
	END LOOP;
END;
$BODY$;