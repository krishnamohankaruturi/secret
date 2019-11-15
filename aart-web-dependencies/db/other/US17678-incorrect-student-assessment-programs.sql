DO
$BODY$
DECLARE
	currentstudentid BIGINT;
	
	data TEXT[][] := ARRAY[
		['7496774847', 'DLM'],
		['3715185082', 'KAP'],
		['2144960739', 'DLM'],
		['5524568159', 'KAP'],
		['6900964575', 'KAP'],
		['1160747148', 'DLM'],
		['8187792906', 'DLM'],
		['6678681223', 'DLM'],
		['8561068507', 'DLM'],
		['8251391121', 'KAP'],
		['8251391121', 'KAP'],
		['3871135534', 'KAP'],
		['9237498357', 'KAP'],
		['4665150796', 'KAP']
	];
BEGIN
	FOR i IN array_lower(data, 1) .. array_upper(data, 1) LOOP
		SELECT id
		FROM student
		WHERE stateid = (SELECT id FROM organization WHERE displayidentifier = 'KS') AND statestudentidentifier = data[i][1]
		LIMIT 1
		INTO currentstudentid;
		
		IF currentstudentid IS NOT NULL THEN
			PERFORM remove_student_from_assessment_program(currentstudentid, data[i][2], 2016);
		ELSE
			RAISE NOTICE 'State student identifier % not found in KS, skipping...', data[i][1];
		END IF;
	END LOOP;
END;
$BODY$;