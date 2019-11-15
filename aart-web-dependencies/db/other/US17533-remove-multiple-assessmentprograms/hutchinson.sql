DO
$BODY$
DECLARE
	currentstudentid BIGINT;
	
	data TEXT[][] := ARRAY[
		['8503440768', 'DLM'],
		['5837857192', 'KAP'],
		['7866035911', 'KAP'],
		['1865846945', 'KAP'],
		['6294839661', 'KAP'],
		['5439277412', 'KAP'],
		['2848205431', 'DLM'],
		['2123974366', 'KAP'],
		['2871736723', 'KAP'],
		['5243154369', 'KAP'],
		['4035715824', 'KAP'],
		['2331528756', 'DLM'],
		['2254372238', 'DLM'],
		['6967236474', 'KAP'],
		['2613838825', 'KAP'],
		['2081367483', 'KAP'],
		['2985403464', 'DLM'],
		['4618384573', 'KAP'],
		['2043892983', 'DLM'],
		['1267282088', 'KAP'],
		['2656249619', 'DLM'],
		['7110844854', 'DLM']
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