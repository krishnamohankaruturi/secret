DO
$BODY$
DECLARE
	MARY_MATTHEW_USER_ID BIGINT;
	NOW_DATE TIMESTAMP WITHOUT TIME ZONE;
	current_studentstestsid BIGINT;
	current_studentstestsectionsid BIGINT;
	current_taskvariantid BIGINT;
	number_records_updated INTEGER;
	valid_score BOOLEAN;
	score_to_insert INTEGER;
	scorable_to_insert BOOLEAN;
	nonscorablecodeid_to_insert BIGINT;
	
	-- [state student identifier, score or non-scorable code]
	data TEXT[][] := ARRAY[
		['1446991946', '3'],
		['1939324327', '3'],
		['8434066823', '2'],
		['8589688615', 'IN'],
		['2280633868', '1'],
		['7912560173', '2'],
		['4514192821', '2'],
		['5299868367', '2'],
		['9879430697', '2'],
		['6685982233', 'IN'],
		['2527641601', '3'],
		['5762154254', '2'],
		['7073366214', '2'],
		['8176648116', '2'],
		['2195492171', '1'],
		['5590441013', 'IN'],
		['9225607652', '1'],
		['4487670632', '4'],
		['1878306545', '1'],
		['8754752604', '1'],
		['3529620688', '2'],
		['2025698909', '1'],
		['1134674481', '3'],
		['7953252155', '2'],
		['1042309248', '1'],
		['1834232155', '1'],
		['1488066337', '4'],
		['7289359542', '2'],
		['9361972936', 'IN'],
		['2609521936', '2'],
		['7082708548', '3'],
		['5836404836', '3'],
		['5922464647', '2'],
		['7296929294', '1'],
		['6042162829', '1'],
		['9422171342', 'IN'],
		['7507191036', '2'],
		['3513903162', '1'],
		['7658762334', '2'],
		['5401765509', '1'],
		['9647237049', '1'],
		['9721713929', '1'],
		['9476176997', '1'],
		['5319516436', '1'],
		['1060796503', '1'],
		['1790372976', '1'],
		['3440047997', '4'],
		['6133847131', '3'],
		['2271605407', 'IN'],
		['3143776676', 'IN'],
		['1286883172', '2'],
		['7200815292', 'BL'],
		['6865440439', '1'],
		['5543190514', 'OT'],
		['5628763314', '1'],
		['5374247751', 'IN'],
		['8932858187', '4'],
		['9789748698', '4'],
		['8779079121', '4'],
		['9980540249', '2'],
		['3437267973', '3'],
		['4551336548', '3'],
		['6306437215', '4'],
		['6478128423', '3'],
		['5591820239', '4'],
		['7611519719', '4'],
		['7700858543', '3'],
		['5190882276', '3'],
		['3040125435', '1'],
		['1806975823', 'IN'],
		['4832176951', '4'],
		['4558091966', '2'],
		['2636372539', '4'],
		['9033064898', '3'],
		['4990834194', '4'],
		['7823619962', '3'],
		['9949517176', 'IN'],
		['6008614704', '2'],
		['2587174163', '2'],
		['9983095823', 'BL'],
		['9277632526', '1'],
		['7725517154', '4'],
		['2715562179', '2'],
		['9535817965', '2'],
		['1724388754', '1'],
		['5064075561', '2'],
		['1258043386', '1'],
		['6612060875', '1'],
		['7806067604', '1'],
		['9648426813', '1'],
		['7508353951', '1'],
		['5002020976', '3'],
		['6933091805', '1'],
		['1832830144', '3'],
		['1265890528', '4'],
		['9726337569', '1'],
		['9461320051', '1'],
		['2275116346', '1'],
		['9133600546', '1'],
		['9865954877', '4'],
		['4206908788', '2'],
		['3055875834', '3'],
		['8009385034', '2'],
		['8870335593', '2'],
		['7922894171', 'IN'],
		['7693831843', '2'],
		['2917844744', 'IN'],
		['7633559853', '2'],----- this is the first grade 11 record
		['1484323556', '2'],
		['8141522981', '1'],
		['8561804866', '1'],
		['9151143755', '1'],
		['8151672706', '1'],
		['3063856916', '1'],
		['5203447993', '2'],
		['7520164896', '1'],
		['9474751684', '3'],
		['3395459926', '2'],
		['4217784902', '1'],
		['6533720465', '1'],
		['5356249152', '2'],
		['9764815863', '3'],
		['9189689356', 'IN'],
		['4156370526', '1'],
		['5190991202', '3'],
		['1122755864', '3'],
		['5125152234', '3'],
		['2074050232', '4'],
		['3922761836', '1'],
		['6138831241', '3'],
		['1679108441', '2'],
		['9562014061', '1'],
		['2634208731', '2'],
		['1433983273', '2'],
		['8896936284', '2'],
		['2940163693', '1']
	];
BEGIN
	
	SELECT now() INTO NOW_DATE;
	SELECT id FROM aartuser WHERE email = 'mmatthew@ku.edu' INTO MARY_MATTHEW_USER_ID;
	
	FOR i IN array_lower(data, 1) .. array_upper(data, 1) LOOP
		RAISE NOTICE '--------------------------------------------------------------------------------------------------';
		RAISE NOTICE 'Processing student %', data[i][1];
		
		SELECT st.id
		FROM studentstests st
		JOIN testsession ts on st.testsessionid = ts.id
		JOIN student s ON st.studentid = s.id
		JOIN testcollection tc ON st.testcollectionid = tc.id
		JOIN stage stg ON tc.stageid = stg.id
		JOIN contentarea ca ON tc.contentareaid = ca.id
		WHERE st.activeflag = TRUE
		AND tc.activeflag = TRUE
		AND ts.activeflag = TRUE
		AND ts.schoolyear = 2016
		AND s.statestudentidentifier = data[i][1]
		AND ca.abbreviatedname = 'ELA'
		AND stg.code = 'Prfrm'
		INTO current_studentstestsid;
		
		IF current_studentstestsid IS NULL THEN
			RAISE NOTICE 'No MDPT test found for student';
			CONTINUE;
		END IF;
		
		SELECT studentstestsectionsid, taskvariantid
		FROM studentsresponses
		WHERE activeflag = TRUE
		AND questarrequestid IS NOT NULL
		AND taskvariantid > 0
		AND studentstestsid = current_studentstestsid
		INTO current_studentstestsectionsid, current_taskvariantid;
		
		IF EXISTS (
			SELECT 1
			FROM studentresponsescore
			WHERE studentstestsectionsid = current_studentstestsectionsid
			AND taskvariantexternalid = (SELECT externalid FROM taskvariant WHERE id = current_taskvariantid)
			AND raterid = MARY_MATTHEW_USER_ID
			AND activeflag = TRUE
		) THEN
			RAISE NOTICE 'Scoring record by Mary Matthew (or her team) is already present for this student, skipping insert';
			CONTINUE;
		END IF;
			
		WITH updated_rows AS (
			UPDATE studentresponsescore
			SET raterorder = raterorder + 1
			WHERE studentstestsectionsid = current_studentstestsectionsid
			AND taskvariantexternalid = (SELECT externalid FROM taskvariant WHERE id = current_taskvariantid)
			RETURNING 1
		) SELECT count(*) from updated_rows INTO number_records_updated;

		RAISE NOTICE 'Updated % records', number_records_updated;
		
		SELECT id
		FROM category
		WHERE categorycode = data[i][2]
		AND categorytypeid = (SELECT id FROM categorytype where typecode = 'NON_SCORABLE_CODE')
		INTO nonscorablecodeid_to_insert;
		
		IF nonscorablecodeid_to_insert IS NULL THEN
			SELECT CASE WHEN trim(both ' ' from data[i][2]) ~ '^[1-4]$' THEN TRUE ELSE FALSE END INTO valid_score;
			
			IF valid_score THEN
				SELECT to_number(data[i][2], '9') INTO score_to_insert;
				SELECT TRUE INTO scorable_to_insert;
			ELSE
				RAISE NOTICE 'Could not parse % into a valid score', data[i][2];
				CONTINUE;
			END IF;
		ELSE
			SELECT NULL INTO score_to_insert;
			SELECT FALSE INTO scorable_to_insert;
		END IF;
		
		RAISE NOTICE '(scorable_to_insert, score_to_insert, nonscorablecodeid_to_insert) = (%, %, %)', scorable_to_insert, score_to_insert, nonscorablecodeid_to_insert;
		
		INSERT INTO studentresponsescore (
			studentstestsectionsid, taskvariantexternalid, score, dimension, diagnosticstatement,
			raterid, ratername, raterorder, raterexposure, createdate, modifieddate, activeflag,
			scorable, nonscorablecodeid
		)
		VALUES (
			current_studentstestsectionsid,
			(SELECT externalid FROM taskvariant WHERE id = current_taskvariantid),
			score_to_insert,
			'Holistic Score',
			NULL,
			MARY_MATTHEW_USER_ID,
			'Matthew, Mary',
			1,
			NULL,
			NOW_DATE,
			NOW_DATE,
			TRUE,
			scorable_to_insert,
			nonscorablecodeid_to_insert
		);
	END LOOP;
	
END;
$BODY$;