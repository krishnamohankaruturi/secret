DO
$BODY$
DECLARE
	currentstudentid BIGINT;
	
	data TEXT[][] := ARRAY[
		['5994266205', 'DLM'],
		['8245215031', 'KAP'],
		['2443595153', 'KAP'],
		['4590101254', 'KAP'],
		['4044006563', 'KAP'],
		['4481917865', 'KAP'],
		['4020144761', 'KAP'],
		['2663121819', 'KAP'],
		['5764175216', 'KAP'],
		['7468278563', 'KAP'],
		['2978618477', 'KAP'],
		['8057925299', 'KAP'],
		['3406262465', 'KAP'],
		['1629702811', 'KAP'],
		['1878653652', 'KAP'],
		['5427290974', 'KAP'],
		['3793342778', 'KAP'],
		['5982267228', 'KAP'],
		['4988328392', 'KAP'],
		['4632479454', 'KAP'],
		['1609532473', 'KAP'],
		['4691365591', 'KAP'],
		['3801060462', 'KAP'],
		['9588912318', 'KAP'],
		['6002955461', 'KAP'],
		['9148867608', 'KAP'],
		['8091978367', 'KAP'],
		['7184185536', 'KAP'],
		['3893671617', 'KAP'],
		['5902345995', 'KAP'],
		['5116238156', 'KAP'],
		['5486968973', 'KAP'],
		['7787664886', 'KAP'],
		['1921270357', 'KAP'],
		['2279610078', 'KAP'],
		['1851625119', 'KAP'],
		['8431969857', 'KAP'],
		['5405048054', 'KAP'],
		['5658853621', 'KAP'],
		['4672548856', 'KAP'],
		['5683389046', 'KAP'],
		['6283752403', 'KAP'],
		['8639815488', 'KAP'],
		['4778054857', 'KAP'],
		['3530298301', 'KAP'],
		['6529653732', 'KAP'],
		['2489756125', 'KAP'],
		['5195169829', 'KAP'],
		['6496180911', 'KAP'],
		['4529127818', 'KAP'],
		['2246713315', 'KAP'],
		['9449786631', 'KAP'],
		['7242920256', 'KAP'],
		['4247353845', 'KAP'],
		['1814733116', 'KAP'],
		['7568914798', 'KAP'],
		['1204130515', 'KAP'],
		['8769600619', 'KAP'],
		['1007455969', 'KAP'],
		['3554410837', 'KAP'],
		['3536996418', 'KAP'],
		['4830164824', 'KAP'],
		['5600308604', 'KAP'],
		['1395763593', 'KAP'],
		['2849578975', 'KAP'],
		['3490602269', 'KAP'],
		['8675435878', 'KAP'],
		['3437765221', 'KAP'],
		['2829237927', 'KAP'],
		['2806246075', 'KAP'],
		['3614745856', 'KAP'],
		['5259239008', 'KAP'],
		['2317210671', 'KAP'],
		['3511508436', 'KAP'],
		['2136813633', 'KAP'],
		['6386161688', 'KAP'],
		['6732678256', 'KAP'],
		['7022198799', 'KAP'],
		['6581511005', 'KAP'],
		['4202359674', 'KAP'],
		['3361218039', 'KAP'],
		['6208742846', 'KAP'],
		['2572138069', 'KAP'],
		['9215019162', 'KAP'],
		['2874992534', 'KAP'],
		['5820484525', 'KAP'],
		['2252688335', 'KAP'],
		['3780745461', 'KAP'],
		['2331127123', 'KAP'],
		['4381465024', 'KAP'],
		['7288629104', 'KAP'],
		['3763294961', 'KAP'],
		['1005636192', 'KAP'],
		['3041516845', 'KAP'],
		['1133002145', 'KAP'],
		['7375599599', 'KAP'],
		['2128133491', 'KAP'],
		['4563531405', 'KAP'],
		['6353566026', 'KAP'],
		['7331497294', 'KAP'],
		['6427971026', 'KAP'],
		['4620128937', 'KAP'],
		['7763643293', 'KAP'],
		['5568914494', 'KAP'],
		['6542746021', 'KAP'],
		['6417507319', 'KAP'],
		['6325882911', 'KAP'],
		['8211238446', 'KAP'],
		['7021870825', 'KAP'],
		['3669894349', 'KAP'],
		['7269724075', 'KAP'],
		['4679188685', 'KAP'],
		['5350816152', 'KAP'],
		['2386682765', 'KAP'],
		['2083677668', 'KAP'],
		['4729980662', 'KAP'],
		['7486652692', 'KAP'],
		['8970131434', 'KAP'],
		['3242336534', 'KAP'],
		['6270289483', 'KAP'],
		['5183216274', 'KAP'],
		['1590236726', 'KAP'],
		['6903020969', 'KAP'],
		['4603307616', 'KAP'],
		['8890918845', 'KAP'],
		['9424499523', 'KAP'],
		['7159717848', 'KAP'],
		['3945955076', 'KAP'],
		['3175567533', 'KAP'],
		['8055869383', 'KAP'],
		['6717581719', 'KAP'],
		['4246263052', 'KAP'],
		['5608851811', 'KAP'],
		['1587052334', 'KAP'],
		['9367139985', 'KAP'],
		['9169071586', 'KAP'],
		['8174283439', 'KAP'],
		['8119961749', 'KAP'],
		['1811301223', 'KAP'],
		['5281157353', 'KAP'],
		['7727172642', 'KAP'],
		['5116343251', 'KAP'],
		['2618375874', 'KAP'],
		['7927087606', 'KAP'],
		['9477452173', 'KAP'],
		['1485596491', 'KAP'],
		['9161385514', 'KAP'],
		['8215852343', 'KAP'],
		['2593648244', 'KAP'],
		['1683734157', 'KAP'],
		['3986873015', 'KAP'],
		['7547323693', 'KAP'],
		['1478995998', 'KAP'],
		['4760824707', 'KAP'],
		['6705015381', 'KAP'],
		['4398372849', 'KAP'],
		['3278352505', 'KAP'],
		['5646587611', 'DLM'],
		['9246140893', 'DLM'],
		['5786452565', 'DLM'],
		['4414136644', 'DLM'],
		['1890078506', 'DLM'],
		['3365217177', 'DLM'],
		['6185363593', 'DLM'],
		['8831675257', 'DLM'],
		['2255859386', 'KAP'],
		['8009939382', 'DLM'],
		['5142630998', 'DLM']
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