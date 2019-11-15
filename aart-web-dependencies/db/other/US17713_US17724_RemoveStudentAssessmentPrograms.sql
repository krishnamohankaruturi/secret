DO
$BODY$
DECLARE
	currentstudentid BIGINT;
	
	data TEXT[][] := ARRAY[
		['5963130557', 'DLM'],
		['7427067231', 'DLM'],
		['9245424022', 'DLM'],
		['9245424022', 'DLM'],
		['7427067231', 'DLM'],
		['3468628331', 'DLM'],
		['5573086499', 'DLM'],
		['5253728597', 'DLM'],
		['9001690025', 'DLM'],
		['5755729468', 'DLM'],
		['4335425457', 'DLM'],
		['3958004741', 'DLM'],
		['8344278074', 'DLM'],
		['7159568583', 'DLM'],
		['4597948163', 'DLM'],
		['7799448873', 'KAP'],
		['3709551838', 'KAP'],
		['9423789412', 'KAP'],
		['8003379075', 'KAP'],
		['1282912844', 'KAP'],
		['9749953347', 'KAP'],
		['1766925324', 'KAP'],
		['3329516461', 'KAP'],
		['4902429306', 'KAP'],
		['4810771121', 'KAP'],
		['9179496156', 'KAP'],
		['5398765841', 'KAP'],
		['8713919512', 'KAP'],
		['2584786109', 'KAP'],
		['3384962516', 'KAP'],
		['2548654508', 'KAP'],
		['5581957568', 'KAP'],
		['5221038331', 'KAP'],
		['8365776928', 'KAP'],
		['2331414076', 'KAP'],
		['6972503701', 'KAP'],
		['4264677386', 'KAP'],
		['2382715022', 'KAP'],
		['1977213146', 'KAP'],
		['6027743867', 'KAP'],
		['5299572603', 'KAP'],
		['9379181671', 'KAP'],
		['8486375053', 'KAP'],
		['5705996578', 'KAP'],
		['7865819749', 'KAP'],
		['5021179586', 'KAP'],
		['2763882277', 'KAP'],
		['6280234711', 'KAP'],
		['5600255381', 'KAP'],
		['6833174369', 'KAP'],
		['9514349717', 'KAP'],
		['6402106682', 'KAP'],
		['3169577301', 'KAP'],
		['4529584704', 'KAP'],
		['4975038293', 'KAP'],
		['4240379458', 'KAP'],
		['9359032212', 'KAP'],
		['9447126499', 'KAP'],
		['3065224968', 'KAP'],
		['6266468271', 'KAP'],
		['2120491291', 'KAP'],
		['2798897297', 'KAP'],
		['1297985915', 'KAP'],
		['3395044246', 'DLM'],
		['5639192453', 'DLM'],
		['8561068507', 'DLM'],
		['4696263363', 'DLM'],
		['8711452617', 'DLM'],
		['8194758947', 'DLM']
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