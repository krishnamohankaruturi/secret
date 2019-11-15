DO
$BODY$
DECLARE
	currentstudentid BIGINT;
	
	data TEXT[][] := ARRAY[
		['1855997584', 'KAP'],
		['6688530643', 'KAP'],
		['6688530643', 'KAP'],
		['1790860733', 'KAP'],
		['3100501373', 'KAP'],
		['3100501373', 'KAP'],
		['6758641422', 'KAP'],
		['5785148757', 'KAP'],
		['5785148757', 'KAP'],
		['8530422414', 'KAP'],
		['4030819788', 'KAP'],
		['6633229976', 'KAP'],
		['6633229976', 'KAP'],
		['1569635439', 'DLM'],
		['3878998813', 'KAP'],
		['3878998813', 'KAP'],
		['6024287879', 'KAP'],
		['6024287879', 'KAP'],
		['1796481408', 'KAP'],
		['9465081795', 'KAP'],
		['9465081795', 'KAP'],
		['6567591552', 'KAP']
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