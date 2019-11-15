DO
$BODY$
DECLARE
	currentstudentid BIGINT;
	
	data TEXT[][] := ARRAY[
		['3272960856','DLM'],
		['8348434712','DLM'],
		['9245424022','DLM'],
		['7427067231','DLM'],
		['5963130557','DLM'],
		['5573086499','DLM'],
		['2068102382','KAP'],
		['9632368975','KAP'],
		['2040483152','KAP'],
		['8523674764','DLM'],
		['1850819599','DLM']
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