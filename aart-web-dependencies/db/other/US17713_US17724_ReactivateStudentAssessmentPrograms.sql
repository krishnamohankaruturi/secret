DO
$BODY$
DECLARE
	currentstudentid BIGINT;
	
	data TEXT[][] := ARRAY[
		['5963130557', 'KAP'],
		['7427067231', 'KAP'],
		['9245424022', 'KAP'],
		['9245424022', 'KAP'],
		['7427067231', 'KAP'],
		['3468628331', 'KAP'],
		['5573086499', 'KAP'],
		['5253728597', 'KAP'],
		['9001690025', 'KAP'],
		['5755729468', 'KAP'],
		['4335425457', 'KAP'],
		['3958004741', 'KAP'],
		['8344278074', 'KAP'],
		['7159568583', 'KAP'],
		['4597948163', 'KAP'],
		['7799448873', 'DLM'],
		['3709551838', 'DLM'],
		['9423789412', 'DLM'],
		['8003379075', 'DLM'],
		['1282912844', 'DLM'],
		['9749953347', 'DLM'],
		['1766925324', 'DLM'],
		['3329516461', 'DLM'],
		['4902429306', 'DLM'],
		['4810771121', 'DLM'],
		['9179496156', 'DLM'],
		['5398765841', 'DLM'],
		['8713919512', 'DLM'],
		['2584786109', 'DLM'],
		['3384962516', 'DLM'],
		['2548654508', 'DLM'],
		['5581957568', 'DLM'],
		['5221038331', 'DLM'],
		['8365776928', 'DLM'],
		['2331414076', 'DLM'],
		['6972503701', 'DLM'],
		['4264677386', 'DLM'],
		['2382715022', 'DLM'],
		['1977213146', 'DLM'],
		['6027743867', 'DLM'],
		['5299572603', 'DLM'],
		['9379181671', 'DLM'],
		['8486375053', 'DLM'],
		['5705996578', 'DLM'],
		['7865819749', 'DLM'],
		['5021179586', 'DLM'],
		['2763882277', 'DLM'],
		['6280234711', 'DLM'],
		['5600255381', 'DLM'],
		['6833174369', 'DLM'],
		['9514349717', 'DLM'],
		['6402106682', 'DLM'],
		['3169577301', 'DLM'],
		['4529584704', 'DLM'],
		['4975038293', 'DLM'],
		['4240379458', 'DLM'],
		['9359032212', 'DLM'],
		['9447126499', 'DLM'],
		['3065224968', 'DLM'],
		['6266468271', 'DLM'],
		['2120491291', 'DLM'],
		['2798897297', 'DLM'],
		['1297985915', 'DLM'],
		['3395044246', 'KAP'],
		['5639192453', 'KAP'],
		['8561068507', 'KAP'],
		['4696263363', 'KAP'],
		['8711452617', 'KAP'],
		['8194758947', 'KAP']
		];
BEGIN
	FOR i IN array_lower(data, 1) .. array_upper(data, 1) LOOP
		SELECT id
		FROM student
		WHERE stateid = (SELECT id FROM organization WHERE displayidentifier = 'KS') AND statestudentidentifier = data[i][1]
		LIMIT 1
		INTO currentstudentid;
		
		IF currentstudentid IS NOT NULL THEN
			PERFORM reactivate_student_assessment_program(currentstudentid, data[i][2], 2016);
		ELSE
			RAISE NOTICE 'State student identifier % not found in KS, skipping...', data[i][1];
		END IF;
	END LOOP;
END;
$BODY$;