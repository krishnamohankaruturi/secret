DO
$BODY$
DECLARE
	currentstudentid BIGINT;
	
	data TEXT[][] := ARRAY[
		['1991227949', 'KAP'],
		['4515608932', 'KAP'],
		['7901605286', 'DLM'],
		['2257649753', 'KAP'],
		['7498504626', 'KAP'],
		['9756970243', 'KAP'],
		['2455034461', 'KAP'],
		['2121576231', 'KAP'],
		['3534016769', 'KAP'],
		['5599246604', 'KAP'],
		['5286656609', 'KAP'],
		['6965759488', 'DLM'],
		['5102598775', 'KAP'],
		['3470463271', 'DLM'],
		['9929640134', 'KAP'],
		['7469096965', 'KAP'],
		['1047959305', 'KAP'],
		['4385969752', 'KAP'],
		['6715568928', 'KAP'],
		['4521985858', 'KAP'],
		['6013398372', 'KAP'],
		['6813527487', 'KAP'],
		['8706854265', 'KAP']
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