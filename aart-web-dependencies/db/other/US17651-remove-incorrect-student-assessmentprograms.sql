DO
$BODY$
DECLARE
	currentstudentid BIGINT;
	
	data TEXT[][] := ARRAY[
		['3195230224', 'KAP'],
		['3036211055', 'KAP'],
		['7954790797', 'DLM'],
		['3119840327', 'DLM'],
		['9026390254', 'DLM'],
		['7573260318', 'KAP'],
		['4731413575', 'KAP'],
		['6185363593', 'DLM'],
		['1796209783', 'KAP'],
		['1539858626', 'DLM'],
		['7496774847', 'DLM'],
		['9482322878', 'DLM'],
		['9520487484', 'DLM'],
		['9026390254', 'DLM'],
		['1162807644', 'DLM'],
		['5685063571', 'DLM'],
		['1758990295', 'DLM'],
		['2636832149', 'DLM'],
		['1678225118', 'DLM'],
		['2585074579', 'DLM'],
		['1691004693', 'DLM'],
		['6769614928', 'DLM'],
		['8831675257', 'KAP'],
		['1160747148', 'DLM'],
		['8187792906', 'DLM'],
		['6678681223', 'DLM'],
		['8561068507', 'KAP'],
		['1569635439', 'DLM']
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