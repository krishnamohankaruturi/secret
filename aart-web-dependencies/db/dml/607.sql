-- dml/607.sql

-- MDPT reportassessmentprogram entry correction
UPDATE reportassessmentprogram
SET subjectid = NULL, modifieddate = NOW()
WHERE id = (
	SELECT rap.id
	FROM reportassessmentprogram rap
	JOIN category c ON rap.reporttypeid = c.id
	JOIN assessmentprogram ap ON rap.assessmentprogramid = ap.id
	JOIN organization o ON rap.stateid = o.id
	JOIN contentarea ca ON rap.subjectid = ca.id
	JOIN authorities auth ON rap.authorityid = auth.id
	WHERE c.categorycode = 'GEN_ST_MDPT'
	AND ap.abbreviatedname = 'KAP'
	AND o.displayidentifier = 'KS' AND o.organizationtypeid = (SELECT id FROM organizationtype WHERE typecode = 'ST')
	AND ca.abbreviatedname = 'ELA'
	AND auth.authority = 'VIEW_GENERAL_STUDENT_MDPT_RESP'
);

-- DLM Monitoring Summary correction of reportassessmentprogram entries
DO
$BODY$
DECLARE
	now_gmt TIMESTAMP WITH TIME ZONE;
	cetesysadminid BIGINT;
	new_permission RECORD;
	dlm_org RECORD;
	groupids_to_add_again BIGINT[];
	new_reportassessmentprogramid BIGINT;
	
	assessmentprogram_abbr TEXT := 'DLM';
BEGIN
	SELECT now() INTO now_gmt;
	SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
	
	-- set up new permission data
	SELECT
		'Monitoring Summary'::VARCHAR(100) AS "category.categoryname",
		'ALT_MONITORING_SUMMARY'::VARCHAR(75) AS "category.categorycode",
		'DLM Monitoring Summary'::VARCHAR(200) AS "category.categorydescription",
		'VIEW_ALT_MONITORING_SUMMARY'::VARCHAR(32) AS "authorities.authority",
		'View AlternateAssessment Monitoring SummaryReport'::VARCHAR(55) AS "authorities.displayname",
		'Reports-Performance Reports'::VARCHAR(100) AS "authorities.objecttype"
	INTO new_permission;
	
	FOR dlm_org IN (
		SELECT o.id, o.displayidentifier
		FROM orgassessmentprogram oap
		JOIN organization o ON oap.organizationid = o.id
		JOIN assessmentprogram ap ON oap.assessmentprogramid = ap.id
		WHERE oap.activeflag = TRUE AND o.activeflag = TRUE
		AND ap.abbreviatedname = assessmentprogram_abbr
		AND o.organizationtypeid = (SELECT id FROM organizationtype WHERE typecode = 'ST')
		ORDER BY o.displayidentifier
	) LOOP
		
		-- Find groupids that can already see the reports in this state
		SELECT ARRAY(
			SELECT DISTINCT rapg.groupid::BIGINT
			FROM reportassessmentprogramgroup rapg
			JOIN reportassessmentprogram rap ON rapg.reportassessmentprogramid = rap.id
			JOIN category c ON rap.reporttypeid = c.id
			JOIN assessmentprogram ap ON rap.assessmentprogramid = ap.id
			JOIN organization o ON rap.stateid = o.id
			JOIN authorities auth ON rap.authorityid = auth.id
			WHERE c.categorycode = new_permission."category.categorycode"
			AND ap.abbreviatedname = assessmentprogram_abbr
			AND o.id = dlm_org.id
			AND auth.authority = new_permission."authorities.authority"
		)
		INTO groupids_to_add_again;
		
		-- Delete entries in reportassessmentprogramgroup that already exist
		DELETE FROM reportassessmentprogramgroup
		WHERE reportassessmentprogramid IN (
			SELECT rap.id
			FROM reportassessmentprogram rap
			JOIN category c ON rap.reporttypeid = c.id
			JOIN assessmentprogram ap ON rap.assessmentprogramid = ap.id
			JOIN authorities auth ON rap.authorityid = auth.id
			WHERE c.categorycode = new_permission."category.categorycode"
			AND ap.abbreviatedname = assessmentprogram_abbr
			AND rap.stateid = dlm_org.id
			AND auth.authority = new_permission."authorities.authority"
		);
		
		-- Delete entries in reportassessmentprogram that already exist
		DELETE FROM reportassessmentprogram
		WHERE id IN (
			SELECT rap.id
			FROM reportassessmentprogram rap
			JOIN category c ON rap.reporttypeid = c.id
			JOIN assessmentprogram ap ON rap.assessmentprogramid = ap.id
			JOIN authorities auth ON rap.authorityid = auth.id
			WHERE c.categorycode = new_permission."category.categorycode"
			AND ap.abbreviatedname = assessmentprogram_abbr
			AND rap.stateid = dlm_org.id
			AND auth.authority = new_permission."authorities.authority"
		);
		
		-- Add one entry to contain all content areas
		IF NOT EXISTS (
			SELECT rap.id
			FROM reportassessmentprogram rap
			JOIN category c ON rap.reporttypeid = c.id
			JOIN assessmentprogram ap ON rap.assessmentprogramid = ap.id
			JOIN authorities auth ON rap.authorityid = auth.id
			WHERE c.categorycode = new_permission."category.categorycode"
			AND ap.abbreviatedname = assessmentprogram_abbr
			AND rap.stateid = dlm_org.id
			AND auth.authority = new_permission."authorities.authority"
		) THEN
			INSERT INTO reportassessmentprogram (reporttypeid, assessmentprogramid, createdate, modifieddate, readytoview, activeflag, stateid, subjectid, authorityid)
			VALUES (
				(SELECT id FROM category WHERE categorycode = new_permission."category.categorycode"),
				(SELECT id FROM assessmentprogram WHERE abbreviatedname = assessmentprogram_abbr),
				now_gmt,
				now_gmt,
				TRUE,
				TRUE,
				dlm_org.id,
				NULL,
				(SELECT id FROM authorities WHERE authority = new_permission."authorities.authority")
			)
			RETURNING id INTO new_reportassessmentprogramid;
		ELSE
			RAISE NOTICE 'Found reportassessmentprogram entry for % in % state (%), skipping insert',
				new_permission."category.categorydescription", contentarea_abbrs[i], assessmentprogram_abbr, dlm_org.displayidentifier;
		END IF;
		
		IF array_length(groupids_to_add_again, 1) > 0 THEN
			RAISE NOTICE 'Found roles found in % with permission for %', dlm_org.displayidentifier, new_permission."authorities.displayname";
			
			-- Add entries to reportassessmentprogramgroup table for all roles that had it before
			FOR i IN 1 .. array_length(groupids_to_add_again, 1) LOOP
				RAISE NOTICE 'Re-adding role % to %', groupids_to_add_again[i], dlm_org.displayidentifier;
				
				INSERT INTO reportassessmentprogramgroup (reportassessmentprogramid, groupid, activeflag)
				VALUES (
					new_reportassessmentprogramid,
					groupids_to_add_again[i],
					TRUE
				);
			END LOOP;
		END IF;
	END LOOP;
END;
$BODY$;