DO
$BODY$
DECLARE
	currentstudentid BIGINT;
	
	data TEXT[] := ARRAY[
		['3022003846'],
		['2094341569'],
		['4009903465'],
		['7554208772'],
		['2382674636'],
		['8837347073'],
		['2095741153'],
		['6515202415'],
		['8028615309'],
		['7661478811'],
		['3298845094'],
		['6260130317'],
		['7440135369'],
		['2945316285'],
		['7547587585'],
		['9492870614'],
		['1854578588'],
		['2439709956'],
		['9277315768'],
		['5318455611'],
		['7312431909'],
		['3610608668'],
		['4378696339'],
		['4945015406'],
		['4987896494'],
		['1698543557'],
		['5994947334'],
		['5947698323'],
		['4006419163'],
		['6885168548'],
		['7328179139']
		];
BEGIN
	FOR i IN array_lower(data, 1) .. array_upper(data, 1) LOOP
		RAISE NOTICE '---- Processing for student ''%'' '' ----', data[i][1];	
			
		PERFORM check_student_pnp_and_stage4_test_availability(data[i][1]);
		
	END LOOP;
END;
$BODY$;