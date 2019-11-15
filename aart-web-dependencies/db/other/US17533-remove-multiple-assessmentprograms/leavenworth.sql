DO
$BODY$
DECLARE
	currentstudentid BIGINT;
	
	data TEXT[][] := ARRAY[
		['3488110044', 'KAP'],
		['6238415169', 'KAP'],
		['9547917781', 'KAP'],
		['3477868757', 'KAP'],
		['7662239551', 'KAP'],
		['2349768953', 'KAP'],
		['3965300792', 'KAP'],
		['6129261284', 'KAP'],
		['1954781857', 'KAP'],
		['2596872471', 'KAP'],
		['5971975935', 'KAP'],
		['5003454961', 'KAP'],
		['2715275579', 'KAP'],
		['5543096879', 'KAP'],
		['5129713737', 'KAP'],
		['3346298191', 'KAP']
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