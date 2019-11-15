DO
$BODY$
DECLARE
	currentstudentid BIGINT;
	
	data TEXT[][] := ARRAY[
		['5985619265', 'KAP'],
		['3899396693', 'KAP'],
		['4455383875', 'DLM'],
		['9035757726', 'KAP'],
		['3170700316', 'KAP'],
		['7342908258', 'KAP'],
		['9954581928', 'KAP'],
		['7115135703', 'KAP'],
		['6267866084', 'KAP'],
		['2789587434', 'KAP'],
		['2097966896', 'KAP'],
		['3030607216', 'KAP'],
		['6948080553', 'KAP'],
		['3105747699', 'KAP'],
		['8764496023', 'KAP'],
		['1992954755', 'KAP'],
		['6509063883', 'KAP'],
		['5407951576', 'KAP'],
		['1554121337', 'KAP'],
		['3197675657', 'KAP'],
		['6735465792', 'KAP'],
		['7725718117', 'KAP'],
		['6472297525', 'KAP'],
		['8945311483', 'KAP'],
		['7829473749', 'KAP'],
		['2664923374', 'KAP'],
		['4954958849', 'KAP'],
		['1231247266', 'KAP'],
		['6232387686', 'KAP'],
		['2689692236', 'KAP'],
		['3181660051', 'KAP'],
		['1883543851', 'KAP'],
		['4181595994', 'KAP'],
		['6236538158', 'KAP'],
		['9070546213', 'KAP'],
		['8414182909', 'KAP'],
		['9137256858', 'KAP'],
		['7328766514', 'KAP'],
		['3996872992', 'KAP'],
		['7672341588', 'KAP'],
		['8721229813', 'KAP'],
		['6012427387', 'KAP'],
		['2062177542', 'KAP'],
		['1384731644', 'KAP'],
		['1848554885', 'KAP'],
		['2367957819', 'KAP'],
		['5629470051', 'KAP'],
		['4554137759', 'KAP'],
		['9362163047', 'KAP'],
		['5014572855', 'KAP'],
		['4349246398', 'KAP'],
		['1254428488', 'KAP'],
		['2210926882', 'KAP'],
		['1034489003', 'KAP'],
		['5769345519', 'KAP'],
		['6644095763', 'KAP'],
		['1755867131', 'KAP'],
		['1669772578', 'KAP'],
		['2600617892', 'KAP'],
		['3161816838', 'KAP'],
		['5180332591', 'KAP'],
		['1848652992', 'KAP'],
		['2617068102', 'KAP'],
		['8375136204', 'KAP'],
		['9037151396', 'KAP'],
		['1984633783', 'KAP'],
		['2899810286', 'KAP'],
		['3694469621', 'KAP'],
		['9385314238', 'KAP'],
		['7607630929', 'KAP'],
		['8364045253', 'KAP'],
		['1168502772', 'KAP'],
		['6732985779', 'KAP'],
		['8348036172', 'KAP'],
		['3638232522', 'KAP'],
		['2774810469', 'KAP'],
		['4793318835', 'KAP'],
		['4114823638', 'KAP'],
		['6418708466', 'KAP'],
		['4588769359', 'KAP'],
		['2794983253', 'KAP'],
		['1146849621', 'KAP'],
		['6606767121', 'KAP'],
		['9569302143', 'KAP'],
		['8616251332', 'KAP'],
		['1204216452', 'KAP'],
		['3140723776', 'KAP'],
		['5865669486', 'KAP'],
		['8459290689', 'KAP'],
		['7080358019', 'KAP'],
		['1478318856', 'KAP'],
		['4194774027', 'KAP'],
		['9882451098', 'KAP'],
		['4731419131', 'KAP'],
		['2525521137', 'KAP'],
		['1563669102', 'KAP'],
		['9493805778', 'KAP'],
		['7283692584', 'KAP'],
		['6213914641', 'KAP'],
		['1855228688', 'KAP'],
		['9756331348', 'KAP'],
		['4425455967', 'KAP'],
		['7034020523', 'KAP'],
		['9030251735', 'KAP'],
		['5351584778', 'KAP'],
		['8614279655', 'KAP'],
		['9791288933', 'KAP'],
		['6027998431', 'KAP'],
		['4995447644', 'KAP'],
		['3741129984', 'KAP'],
		['3401042793', 'KAP'],
		['7990337029', 'KAP'],
		['7804431996', 'KAP'],
		['3889025137', 'KAP'],
		['7541788767', 'KAP'],
		['6004495964', 'KAP'],
		['6376484506', 'KAP'],
		['6792634468', 'KAP'],
		['5692714388', 'KAP'],
		['6499870013', 'KAP'],
		['7206614582', 'KAP'],
		['5427607137', 'KAP'],
		['4145693833', 'KAP'],
		['5064883803', 'KAP'],
		['6526728286', 'KAP'],
		['3467354874', 'KAP'],
		['4348237905', 'KAP'],
		['4767029783', 'KAP'],
		['4561258205', 'KAP'],
		['1485518164', 'KAP'],
		['2624397736', 'KAP'],
		['6079610329', 'KAP'],
		['8714712695', 'KAP'],
		['9951004881', 'DLM'],
		['9400710062', 'KAP'],
		['4559900981', 'KAP'],
		['9662714553', 'KAP'],
		['2715083939', 'KAP'],
		['5080625929', 'KAP'],
		['5520255733', 'KAP'],
		['7951436852', 'KAP'],
		['9209668677', 'KAP'],
		['6793518046', 'KAP'],
		['1631911546', 'KAP'],
		['5387531429', 'KAP'],
		['1400853494', 'KAP'],
		['5137688037', 'KAP'],
		['1967803277', 'KAP'],
		['4456468114', 'KAP'],
		['5781806113', 'KAP'],
		['8176740217', 'KAP'],
		['8922881127', 'KAP'],
		['4929034779', 'KAP'],
		['1511060638', 'KAP'],
		['6349051599', 'KAP'],
		['8426776825', 'KAP'],
		['8910031565', 'KAP'],
		['3468628331', 'DLM'],
		['3395044246', 'DLM'],
		['6038192123', 'KAP'],
		['4810848256', 'DLM'],
		['7263562065', 'DLM'],
		['1835211887', 'KAP'],
		['6173020858', 'KAP'],
		['6535278123', 'KAP'],
		['4196442412', 'KAP'],
		['5995820982', 'KAP'],
		['2876519526', 'KAP'],
		['6996362938', 'KAP'],
		['1575833239', 'KAP'],
		['7695509116', 'KAP'],
		['1629325058', 'DLM'],
		['6938763569', 'KAP'],
		['4926871491', 'KAP'],
		['2589750889', 'KAP'],
		['6551047335', 'KAP'],
		['5345427682', 'KAP'],
		['7353485418', 'KAP'],
		['8207037741', 'KAP'],
		['2575548985', 'KAP'],
		['7145136767', 'DLM'],
		['3474391925', 'KAP'],
		['6043645793', 'DLM'],
		['1097694356', 'DLM'],
		['6364752616', 'DLM'],
		['3076028863', 'DLM'],
		['1908433469', 'DLM'],
		['4716581225', 'DLM'],
		['3765427357', 'DLM']
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

-- I split out the 2 enrollmenttesttypesubjectarea updates because for some reason, when including the enrollments together,
-- not all of the rows were getting selected properly for update
UPDATE enrollmenttesttypesubjectarea
SET activeflag = TRUE,
modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
modifieddate = NOW()
WHERE activeflag = FALSE
AND enrollmentid = (
	SELECT id
	FROM enrollment
	WHERE currentschoolyear = 2016
	AND studentid = (
		SELECT id
		FROM student
		WHERE stateid = (SELECT id FROM organization WHERE displayidentifier = 'KS')
		AND statestudentidentifier = '5524568159'
	)
)
AND testtypeid IN (
	SELECT tt.id
	FROM testtype tt
	INNER JOIN assessment a ON tt.assessmentid = a.id AND tt.activeflag = TRUE AND a.activeflag = TRUE
	INNER JOIN testingprogram tp ON a.testingprogramid = tp.id AND tp.activeflag = TRUE
	WHERE tp.assessmentprogramid = (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'KAP')
);

UPDATE enrollmenttesttypesubjectarea
SET activeflag = TRUE,
modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
modifieddate = NOW()
WHERE activeflag = FALSE
AND enrollmentid = (
	SELECT id
	FROM enrollment
	WHERE currentschoolyear = 2016
	AND studentid = (
		SELECT id
		FROM student
		WHERE stateid = (SELECT id FROM organization WHERE displayidentifier = 'KS')
		AND statestudentidentifier = '6900964575'
	)
)
AND testtypeid IN (
	SELECT tt.id
	FROM testtype tt
	INNER JOIN assessment a ON tt.assessmentid = a.id AND tt.activeflag = TRUE AND a.activeflag = TRUE
	INNER JOIN testingprogram tp ON a.testingprogramid = tp.id AND tp.activeflag = TRUE
	WHERE tp.assessmentprogramid = (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'KAP')
);

UPDATE studentassessmentprogram
SET activeflag = TRUE
WHERE activeflag = FALSE
AND studentid IN (
	SELECT id
	FROM student
	WHERE stateid = (SELECT id FROM organization WHERE displayidentifier = 'KS')
	AND statestudentidentifier IN ('5524568159', '6900964575')
)
AND assessmentprogramid = (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'KAP');