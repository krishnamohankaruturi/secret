CREATE OR REPLACE FUNCTION remove_student_from_assessment_program(
	_studentid BIGINT,
	_assessmentprogramabbr TEXT,
	_schoolyear INTEGER
)
RETURNS VOID AS $func$
DECLARE
	enrollment_record RECORD;
	assessmentprogram_record RECORD;
	number_updated INTEGER;
BEGIN
	SELECT *
	FROM assessmentprogram
	WHERE abbreviatedname = _assessmentprogramabbr AND activeflag = TRUE
	LIMIT 1
	INTO assessmentprogram_record;
	
	IF assessmentprogram_record IS NULL THEN
		RAISE NOTICE 'No assessment program ''%'' found', _assessmentprogramabbr;
		RETURN;
	END IF;
	
	FOR enrollment_record IN (
		SELECT *
		FROM enrollment enrl
		WHERE enrl.studentid = _studentid AND enrl.currentschoolyear = _schoolyear AND enrl.activeflag = TRUE
	)
	LOOP
		RAISE NOTICE 'Processing enrollmentid %', enrollment_record.id;
		
		WITH updated_ettsa_rows AS (
			UPDATE enrollmenttesttypesubjectarea
			SET activeflag = FALSE,
			modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
			modifieddate = NOW()
			WHERE activeflag = TRUE
			AND enrollmentid = enrollment_record.id
			AND testtypeid IN (
				SELECT tt.id
				FROM testtype tt
				INNER JOIN assessment a ON tt.assessmentid = a.id AND tt.activeflag = TRUE AND a.activeflag = TRUE
				INNER JOIN testingprogram tp ON a.testingprogramid = tp.id AND tp.activeflag = TRUE
				WHERE tp.assessmentprogramid = assessmentprogram_record.id
			)
			RETURNING 1
		)
		SELECT COUNT(*) FROM updated_ettsa_rows INTO number_updated;
		RAISE NOTICE 'Deactivated % rows in enrollmenttesttypesubjectarea', number_updated;
	END LOOP;
	
	IF _assessmentprogramabbr = 'DLM' THEN
		RAISE NOTICE 'DLM detected. Deactivating First Contact Survey data...';

		WITH updated_ssr_rows AS (
			UPDATE studentsurveyresponse
			SET activeflag = FALSE,
			modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
			modifieddate = NOW()
			WHERE activeflag = TRUE
			AND surveyid IN (
				SELECT id FROM survey WHERE studentid = _studentid
			)
			RETURNING 1
		)
		SELECT COUNT(*) FROM updated_ssr_rows INTO number_updated;
		RAISE NOTICE 'Deactivated % rows in studentsurveyresponse', number_updated;

		WITH updated_survey_rows AS (
			UPDATE survey
			SET activeflag = FALSE,
			modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
			modifieddate = NOW()
			WHERE activeflag = TRUE
			AND studentid = _studentid
			RETURNING 1
		)
		SELECT COUNT(*) FROM updated_survey_rows INTO number_updated;
		RAISE NOTICE 'Deactivated % rows in survey', number_updated;
	END IF;
	
	WITH updated_sap_rows AS (
		UPDATE studentassessmentprogram
		SET activeflag = FALSE
		WHERE activeflag = TRUE
		AND studentid = _studentid
		AND assessmentprogramid = assessmentprogram_record.id
		RETURNING 1
	)
	SELECT COUNT(*) FROM updated_sap_rows INTO number_updated;
	RAISE NOTICE 'Deactivated % rows in studentassessmentprogram', number_updated;
END;
$func$ LANGUAGE 'plpgsql' STRICT;