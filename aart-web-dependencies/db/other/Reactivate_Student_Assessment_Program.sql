-- Function: reactivate_student_assessment_program(bigint, text, integer)

-- DROP FUNCTION reactivate_student_assessment_program(bigint, text, integer);

CREATE OR REPLACE FUNCTION reactivate_student_assessment_program(
    _studentid bigint,
    _assessmentprogramabbr text,
    _schoolyear integer)
  RETURNS void AS
$BODY$
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
			SET activeflag = TRUE,
			modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
			modifieddate = NOW()
			WHERE activeflag = FALSE
			AND enrollmentid = enrollment_record.id
			AND to_char(modifieddate, 'yyyy-mm-dd') = '2016-03-03'
			AND modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
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
		RAISE NOTICE 'Reactivated % rows in enrollmenttesttypesubjectarea', number_updated;
	END LOOP;
	
	IF _assessmentprogramabbr = 'DLM' THEN
		RAISE NOTICE 'DLM detected. Reactivating First Contact Survey data...';

		WITH updated_ssr_rows AS (
			UPDATE studentsurveyresponse
			SET activeflag = TRUE,
			modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
			modifieddate = NOW()
			WHERE activeflag = FALSE
			AND to_char(modifieddate, 'yyyy-mm-dd') = '2016-03-03'
			AND modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
			AND surveyid IN (
				SELECT id FROM survey WHERE studentid = _studentid
			)
			RETURNING 1
		)
		SELECT COUNT(*) FROM updated_ssr_rows INTO number_updated;
		RAISE NOTICE 'Reactivated % rows in studentsurveyresponse', number_updated;

		WITH updated_survey_rows AS (
			UPDATE survey
			SET activeflag = TRUE,
			modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
			modifieddate = NOW()
			WHERE activeflag = FALSE
			AND studentid = _studentid
			AND to_char(modifieddate, 'yyyy-mm-dd') = '2016-03-03'
			AND modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
			RETURNING 1
		)
		SELECT COUNT(*) FROM updated_survey_rows INTO number_updated;
		RAISE NOTICE 'Reactivated % rows in survey', number_updated;
	END IF;
	
	WITH updated_sap_rows AS (
		UPDATE studentassessmentprogram
		SET activeflag = TRUE
		WHERE activeflag = FALSE
		AND studentid = _studentid
		AND assessmentprogramid = assessmentprogram_record.id
		RETURNING 1
	)
	SELECT COUNT(*) FROM updated_sap_rows INTO number_updated;
	RAISE NOTICE 'Reactivated % rows in studentassessmentprogram', number_updated;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
