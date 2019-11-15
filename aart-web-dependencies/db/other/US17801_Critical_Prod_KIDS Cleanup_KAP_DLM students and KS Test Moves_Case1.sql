-- KAP AND DLM Case-1 completed:
DO
$BODY$
DECLARE
	currentstudentid BIGINT;
	
	data TEXT[][] := ARRAY[
		['2144960739', 'KAP'],
		['1672663725', 'DLM']		
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















	