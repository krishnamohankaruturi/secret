DO
$BODY$
DECLARE
	currentstudentid BIGINT;
	
	data TEXT[][] := ARRAY[
		['6641642186', 'DLM'],
		['7573260318', 'KAP'],
		['1931340773', 'KAP'],
		['9326161463', 'DLM'],
		['6323703084', 'DLM'],
		['2144960739', 'KAP'],
		['3991619512', 'DLM'],
		['4205344872', 'DLM'],
		['3363673167', 'KAP'],
		['5194220812', 'KAP'],
		['1061901343', 'DLM'],
		['8184502877', 'KAP'],
		['7236039306', 'DLM'],
		['9322544201', 'KAP'],
		['9255456474', 'KAP'],
		['9681167791', 'KAP'],
		['1944907394', 'DLM']
	];
BEGIN
	FOR i IN array_lower(data, 1) .. array_upper(data, 1) LOOP
		RAISE NOTICE '---- Attempting to remove ''%'' from ''%'' ----', data[i][1], data[i][2]; 
	
		SELECT id
		FROM student
		WHERE stateid = (SELECT id FROM organization WHERE displayidentifier = 'KS') AND statestudentidentifier = data[i][1]
		LIMIT 1
		INTO currentstudentid;
		
		IF currentstudentid IS NOT NULL THEN
			RAISE NOTICE 'Found student ''%'' as studentid %', data[i][1], currentstudentid;
			PERFORM remove_student_from_assessment_program(currentstudentid, data[i][2], 2016);
		ELSE
			RAISE NOTICE 'State student identifier ''%'' not found in KS, skipping...', data[i][1];
		END IF;
	END LOOP;
END;
$BODY$;