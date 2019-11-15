-- dml/601.sql

--K-ELPA Auto enrollment Batch Job scheduler
INSERT INTO  batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver) 
	VALUES ('KELPA Auto Registration Job Scheduler','kelpaJobScheduler','run','* * 22 * * ?', FALSE,'epbp1.prodku.cete.us');

-- DLM Monitoring Summary permission and reportassessmentprogram entries
DO
$BODY$
DECLARE
	now_gmt TIMESTAMP WITH TIME ZONE;
	cetesysadminid BIGINT;
	dlm_org RECORD;
BEGIN
	SELECT now() INTO now_gmt;
	SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
	
	IF NOT EXISTS (
		SELECT id FROM authorities WHERE authority = 'VIEW_ALT_MONITORING_SUMMARY'
	) THEN
		INSERT INTO authorities (authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser)
		VALUES (
			'VIEW_ALT_MONITORING_SUMMARY',
			'View AlternateAssessment Monitoring SummaryReport',
			'Reports-Performance Reports',
			now_gmt,
			cetesysadminid,
			TRUE,
			now_gmt,
			cetesysadminid
		);
	ELSE
		RAISE NOTICE 'Found permission ''VIEW_ALT_MONITORING_SUMMARY'', skipping insert';
	END IF;
	
	IF NOT EXISTS (
		SELECT id FROM category WHERE categorycode = 'ALT_MONITORING_SUMMARY'
	) THEN
		INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
		VALUES (
			'Monitoring Summary',
			'ALT_MONITORING_SUMMARY',
			'DLM Monitoring Summary',
			(SELECT id FROM categorytype WHERE typecode = 'REPORT_TYPES_UI'),
			NULL,
			'AART',
			now_gmt,
			cetesysadminid,
			TRUE,
			now_gmt,
			cetesysadminid
		);
	ELSE
		RAISE NOTICE 'Found category ''ALT_MONITORING_SUMMARY'', skipping insert';
	END IF;
	
	FOR dlm_org IN (
		SELECT o.id, o.displayidentifier
		FROM orgassessmentprogram oap
		JOIN organization o ON oap.organizationid = o.id
		JOIN assessmentprogram ap ON oap.assessmentprogramid = ap.id
		WHERE oap.activeflag = TRUE AND o.activeflag = TRUE
		AND ap.abbreviatedname = 'DLM'
		AND o.organizationtypeid = (SELECT id FROM organizationtype WHERE typecode = 'ST')
		ORDER BY o.displayidentifier
	) LOOP
		IF NOT EXISTS (
			SELECT rap.id
			FROM reportassessmentprogram rap
			JOIN category c ON rap.reporttypeid = c.id
			JOIN assessmentprogram ap ON rap.assessmentprogramid = ap.id
			JOIN organization o ON rap.stateid = o.id
			JOIN contentarea ca ON rap.subjectid = ca.id
			JOIN authorities auth ON rap.authorityid = auth.id
			WHERE c.categorycode = 'ALT_MONITORING_SUMMARY'
			AND ap.abbreviatedname = 'DLM'
			AND o.id = dlm_org.id
			AND ca.abbreviatedname = 'ELA'
			AND auth.authority = 'VIEW_ALT_MONITORING_SUMMARY'
		) THEN
			INSERT INTO reportassessmentprogram (reporttypeid, assessmentprogramid, createdate, modifieddate, readytoview, activeflag, stateid, subjectid, authorityid)
			VALUES (
				(SELECT id FROM category WHERE categorycode = 'ALT_MONITORING_SUMMARY'),
				(SELECT id FROM assessmentprogram WHERE abbreviatedname = 'DLM'),
				now_gmt,
				now_gmt,
				TRUE,
				TRUE,
				dlm_org.id,
				(SELECT id FROM contentarea WHERE abbreviatedname = 'ELA'),
				(SELECT id FROM authorities WHERE authority = 'VIEW_ALT_MONITORING_SUMMARY')
			);
		ELSE
			RAISE NOTICE 'Found reportassessmentprogram entry for Monitoring Summary (ELA) report in % (DLM), skipping insert', dlm_org.displayidentifier;
		END IF;
		
		IF NOT EXISTS (
			SELECT rap.id
			FROM reportassessmentprogram rap
			JOIN category c ON rap.reporttypeid = c.id
			JOIN assessmentprogram ap ON rap.assessmentprogramid = ap.id
			JOIN organization o ON rap.stateid = o.id
			JOIN contentarea ca ON rap.subjectid = ca.id
			JOIN authorities auth ON rap.authorityid = auth.id
			WHERE c.categorycode = 'ALT_MONITORING_SUMMARY'
			AND ap.abbreviatedname = 'DLM'
			AND o.id = dlm_org.id
			AND ca.abbreviatedname = 'M'
			AND auth.authority = 'VIEW_ALT_MONITORING_SUMMARY'
		) THEN
			INSERT INTO reportassessmentprogram (reporttypeid, assessmentprogramid, createdate, modifieddate, readytoview, activeflag, stateid, subjectid, authorityid)
			VALUES (
				(SELECT id FROM category WHERE categorycode = 'ALT_MONITORING_SUMMARY'),
				(SELECT id FROM assessmentprogram WHERE abbreviatedname = 'DLM'),
				now_gmt,
				now_gmt,
				TRUE,
				TRUE,
				dlm_org.id,
				(SELECT id FROM contentarea WHERE abbreviatedname = 'M'),
				(SELECT id FROM authorities WHERE authority = 'VIEW_ALT_MONITORING_SUMMARY')
			);
		ELSE
			RAISE NOTICE 'Found reportassessmentprogram entry for Monitoring Summary (Math) report in % (DLM), skipping insert', dlm_org.displayidentifier;
		END IF;
		
		IF NOT EXISTS (
			SELECT rap.id
			FROM reportassessmentprogram rap
			JOIN category c ON rap.reporttypeid = c.id
			JOIN assessmentprogram ap ON rap.assessmentprogramid = ap.id
			JOIN organization o ON rap.stateid = o.id
			JOIN contentarea ca ON rap.subjectid = ca.id
			JOIN authorities auth ON rap.authorityid = auth.id
			WHERE c.categorycode = 'ALT_MONITORING_SUMMARY'
			AND ap.abbreviatedname = 'DLM'
			AND o.id = dlm_org.id
			AND ca.abbreviatedname = 'Sci'
			AND auth.authority = 'VIEW_ALT_MONITORING_SUMMARY'
		) THEN
			INSERT INTO reportassessmentprogram (reporttypeid, assessmentprogramid, createdate, modifieddate, readytoview, activeflag, stateid, subjectid, authorityid)
			VALUES (
				(SELECT id FROM category WHERE categorycode = 'ALT_MONITORING_SUMMARY'),
				(SELECT id FROM assessmentprogram WHERE abbreviatedname = 'DLM'),
				now_gmt,
				now_gmt,
				TRUE,
				TRUE,
				dlm_org.id,
				(SELECT id FROM contentarea WHERE abbreviatedname = 'Sci'),
				(SELECT id FROM authorities WHERE authority = 'VIEW_ALT_MONITORING_SUMMARY')
			);
		ELSE
			RAISE NOTICE 'Found reportassessmentprogram entry for Monitoring Summary (Sci) report in % (DLM), skipping insert', dlm_org.displayidentifier;
		END IF;
		
		IF NOT EXISTS (
			SELECT rap.id
			FROM reportassessmentprogram rap
			JOIN category c ON rap.reporttypeid = c.id
			JOIN assessmentprogram ap ON rap.assessmentprogramid = ap.id
			JOIN organization o ON rap.stateid = o.id
			JOIN contentarea ca ON rap.subjectid = ca.id
			JOIN authorities auth ON rap.authorityid = auth.id
			WHERE c.categorycode = 'ALT_MONITORING_SUMMARY'
			AND ap.abbreviatedname = 'DLM'
			AND o.id = dlm_org.id
			AND ca.abbreviatedname = 'SS'
			AND auth.authority = 'VIEW_ALT_MONITORING_SUMMARY'
		) THEN
			INSERT INTO reportassessmentprogram (reporttypeid, assessmentprogramid, createdate, modifieddate, readytoview, activeflag, stateid, subjectid, authorityid)
			VALUES (
				(SELECT id FROM category WHERE categorycode = 'ALT_MONITORING_SUMMARY'),
				(SELECT id FROM assessmentprogram WHERE abbreviatedname = 'DLM'),
				now_gmt,
				now_gmt,
				TRUE,
				TRUE,
				dlm_org.id,
				(SELECT id FROM contentarea WHERE abbreviatedname = 'SS'),
				(SELECT id FROM authorities WHERE authority = 'VIEW_ALT_MONITORING_SUMMARY')
			);
		ELSE
			RAISE NOTICE 'Found reportassessmentprogram entry for Monitoring Summary (Social Studies) report in % (DLM), skipping insert', dlm_org.displayidentifier;
		END IF;
	END LOOP;
END;
$BODY$;