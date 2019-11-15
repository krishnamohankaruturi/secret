DO
$BODY$
DECLARE
	currentstudentid BIGINT;

  data TEXT[][] := ARRAY[
  ['6907593491','KAP'],
  ['1150179457','KAP'],
  ['1058623176','KAP'],
  ['9848221077','KAP'],
  ['7337601212','KAP'],
  ['5071535343','KAP'],
  ['9433911903','KAP'],
  ['4471543326','KAP'],
  ['4471543326','KAP'],
  ['7406683545','KAP'],
  ['6374808975','KAP'],
  ['4899600224','KAP'],
  ['6323703084','DLM'],
  ['7894233706','KAP'],
  ['7894233706','KAP'],
  ['4791890299','KAP'],
  ['4791890299','KAP'],
  ['7680970173','KAP'],
  ['7970400817','KAP'],
  ['2610366191','KAP'],
  ['3178262733','KAP'],
  ['5654961381','KAP'],
  ['9750454685','KAP'],
  ['5637608547','KAP'],
  ['4466290881','KAP'],
  ['3209209251','KAP'],
  ['5420213303','KAP'],
  ['5304088638','KAP'],
  ['7582236513','KAP'],
  ['3577957727','KAP'],
  ['9701029194','KAP'],
  ['2610366191','KAP'],
  ['1793425469','KAP'],
  ['3504721847','KAP'],
  ['9256615266','KAP'],
  ['4011538736','KAP'],
  ['3800844567','KAP'],
  ['3539210296','KAP'],
  ['5972208814','KAP'],
  ['9855089618','KAP'],
  ['9405997521','KAP'],
  ['5115211858','KAP'],
  ['4217590016','KAP'],
  ['1885697899','KAP'],
  ['7757465214','KAP'],
  ['3875899377','KAP'],
  ['2751079172','KAP'],
  ['8651972369','KAP'],
  ['5330989965','KAP'],
  ['9549420604','KAP'],
  ['6737403476','KAP'],
  ['6273807531','KAP'],
  ['8977315824','KAP'],
  ['5005890092','KAP'],
  ['5651828664','KAP'],
  ['3840064171','KAP'],
  ['2363457005','KAP'],
  ['5517130062','KAP'],
  ['5517130062','KAP'],
  ['6274972293','KAP'],
  ['1128290316','KAP'],
  ['4071776579','KAP'],
  ['2374927121','KAP']
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
