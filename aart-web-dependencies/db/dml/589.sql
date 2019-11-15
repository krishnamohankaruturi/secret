--dml/589.sql

--Fix for DE14058, pnp options appear different than normal when being deactivated
DO
$BODY$
DECLARE
	data TEXT[][] := ARRAY[
		['activateByDefault', 'false'],
		['assignedSupport', 'false'],
		['directionsOnly', 'false'],
		['largePrintBooklet', 'false'],
		['paperAndPencil', 'false'],
		['ReadAtStartPreference', 'false'],
		['supportsAdaptiveEquip', 'false'],
		['supportsAdminIpad', 'false'],
		['supportsCalculator', 'false'],
		['supportsHumanReadAloud', 'false'],
		['supportsIndividualizedManipulatives', 'false'],
		['supportsLanguageTranslation', 'false'],
		['supportsPartnerAssistedScanning', 'false'],
		['supportsSignInterpretation', 'false'],
		['supportsStudentProvidedAccommodations', 'false'],
		['supportsTestAdminEnteredResponses', 'false'],
		['supportsTwoSwitch', 'false'],
		['visualImpairment', 'false'],
		['magnification', '2x'],
		['TimeMultiplier', 'unlimited'],
		['brailleDotPressure', '0.5'],
		['brailleGrade', 'uncontracted'],
		['brailleStatusCell', 'off'],
		['numberOfBrailleCells', '80'],
		['numberOfBrailleDots', '6'],
		['usage', 'preferred'],
		['Language', 'spa'],
		['automaticScanInitialDelay', '5'],
		['automaticScanRepeat', 'infinity'],
		['scanSpeed', '2'],
		['SigningType', 'asl'],
		['ReadAtStartPreference', 'false'],
		['SpokenSourcePreference', 'synthetic'],
		['UserSpokenPreference', 'textandgraphics'],
		['tactileFile', 'audioFile']
	];
BEGIN
	UPDATE profileitemattribute
	SET nonselectedvalue = '';
	
	FOR i IN array_lower(data, 1) .. array_upper(data, 1) LOOP
		UPDATE profileitemattribute
		SET nonselectedvalue = data[i][2]
		WHERE attributename = data[i][1];
	END LOOP;
	
END;
$BODY$;