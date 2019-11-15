--dml/617.sql
--F365/US19209

INSERT INTO authorities(
             authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('VIEW_ALL_STUDENT_REPORTS', 'View All Student Reports For Student','Reports-Performance Reports', current_timestamp,(Select id from aartuser where username='cetesysadmin'), 
            true, current_timestamp,(Select id from aartuser where username='cetesysadmin'));
            
DO
$BODY$
DECLARE
	now_gmt TIMESTAMP WITH TIME ZONE;
	cetesysadminid BIGINT;
	new_permission RECORD;
	org RECORD;
	
	assessmentprogram_abbrs TEXT[] := ARRAY['DLM', 'KAP'];
	contentarea_abbrs TEXT[] := ARRAY['M', 'ELA', 'SS', 'Sci'];
BEGIN
	SELECT now() INTO now_gmt;
	SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
	
	-- set up new permission data
	SELECT
		'All Student Reports for Student'::VARCHAR(100) AS "category.categoryname",
		'ALL_ST_RPT_FOR_ST'::VARCHAR(75) AS "category.categorycode",
		'Report type for all student reports for student.'::VARCHAR(200) AS "category.categorydescription",
		'VIEW_ALL_STUDENT_REPORTS'::VARCHAR(32) AS "authorities.authority",
		'View All Student Reports For Student'::VARCHAR(55) AS "authorities.displayname",
		'Reports-Performance Reports'::VARCHAR(100) AS "authorities.objecttype"
	INTO new_permission;
	
	IF NOT EXISTS (
		SELECT id FROM authorities WHERE authority = new_permission."authorities.authority"
	) THEN
		INSERT INTO authorities (authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser)
		VALUES (
			new_permission."authorities.authority",
			new_permission."authorities.displayname",
			new_permission."authorities.objecttype",
			now_gmt,
			cetesysadminid,
			TRUE,
			now_gmt,
			cetesysadminid
		);
	ELSE
		RAISE NOTICE 'Found permission %, skipping insert', new_permission."authorities.authority";
	END IF;
	
	IF NOT EXISTS (
		SELECT id FROM category WHERE categorycode = new_permission."category.categorycode"
	) THEN
		INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
		VALUES (
			new_permission."category.categoryname",
			new_permission."category.categorycode",
			new_permission."category.categorydescription",
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
		RAISE NOTICE 'Found category %, skipping insert', new_permission."category.categorycode";
	END IF;
	
	FOR a IN array_lower(assessmentprogram_abbrs, 1) .. array_upper(assessmentprogram_abbrs, 1) LOOP
		FOR org IN (
			SELECT o.id, o.displayidentifier
			FROM orgassessmentprogram oap
			JOIN organization o ON oap.organizationid = o.id
			JOIN assessmentprogram ap ON oap.assessmentprogramid = ap.id
			WHERE oap.activeflag = TRUE AND o.activeflag = TRUE
			AND ap.abbreviatedname = assessmentprogram_abbrs[a]
			AND o.organizationtypeid = (SELECT id FROM organizationtype WHERE typecode = 'ST')
			ORDER BY o.displayidentifier
		) LOOP
			FOR i IN array_lower(contentarea_abbrs, 1) .. array_upper(contentarea_abbrs, 1) LOOP
				IF NOT EXISTS (
					SELECT rap.id
					FROM reportassessmentprogram rap
					JOIN category c ON rap.reporttypeid = c.id
					JOIN assessmentprogram ap ON rap.assessmentprogramid = ap.id
					JOIN organization o ON rap.stateid = o.id
					JOIN contentarea ca ON rap.subjectid = ca.id
					JOIN authorities auth ON rap.authorityid = auth.id
					WHERE c.categorycode = new_permission."category.categorycode"
					AND ap.abbreviatedname = assessmentprogram_abbrs[a]
					AND o.id = org.id
					AND ca.abbreviatedname = contentarea_abbrs[i]
					AND auth.authority = new_permission."authorities.authority"
				) THEN
					INSERT INTO reportassessmentprogram (reporttypeid, assessmentprogramid, createdate, modifieddate, readytoview, activeflag, stateid, subjectid, authorityid)
					VALUES (
						(SELECT id FROM category WHERE categorycode = new_permission."category.categorycode"),
						(SELECT id FROM assessmentprogram WHERE abbreviatedname = assessmentprogram_abbrs[a]),
						now_gmt,
						now_gmt,
						TRUE,
						TRUE,
						org.id,
						(SELECT id FROM contentarea WHERE abbreviatedname = contentarea_abbrs[i]),
						(SELECT id FROM authorities WHERE authority = new_permission."authorities.authority")
					);
				ELSE
					RAISE NOTICE 'Found reportassessmentprogram entry for % (%) in % state (%), skipping insert',
						new_permission."category.categorydescription", contentarea_abbrs[i], assessmentprogram_abbrs[a], org.displayidentifier;
				END IF;
			END LOOP;
		END LOOP;
	END LOOP;
END;
$BODY$;
         

DO
$BODY$
DECLARE
	now_gmt TIMESTAMP WITH TIME ZONE;
	cetesysadminid BIGINT;
	new_permission RECORD;
	org RECORD;
	
	assessmentprogram_abbrs TEXT[] := ARRAY['CPASS'];
	contentarea_abbrs TEXT[] := ARRAY['GKS', 'AgF&NR', 'Mfg'];
BEGIN
	SELECT now() INTO now_gmt;
	SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
	
	-- set up new permission data
	SELECT
		'All Student Reports for Student'::VARCHAR(100) AS "category.categoryname",
		'ALL_ST_RPT_FOR_ST'::VARCHAR(75) AS "category.categorycode",
		'Report type for all student reports for student.'::VARCHAR(200) AS "category.categorydescription",
		'VIEW_ALL_STUDENT_REPORTS'::VARCHAR(32) AS "authorities.authority",
		'View All Student Reports For Student'::VARCHAR(55) AS "authorities.displayname",
		'Reports-Performance Reports'::VARCHAR(100) AS "authorities.objecttype"
	INTO new_permission;
	
	IF NOT EXISTS (
		SELECT id FROM authorities WHERE authority = new_permission."authorities.authority"
	) THEN
		INSERT INTO authorities (authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser)
		VALUES (
			new_permission."authorities.authority",
			new_permission."authorities.displayname",
			new_permission."authorities.objecttype",
			now_gmt,
			cetesysadminid,
			TRUE,
			now_gmt,
			cetesysadminid
		);
	ELSE
		RAISE NOTICE 'Found permission %, skipping insert', new_permission."authorities.authority";
	END IF;
	
	IF NOT EXISTS (
		SELECT id FROM category WHERE categorycode = new_permission."category.categorycode"
	) THEN
		INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
		VALUES (
			new_permission."category.categoryname",
			new_permission."category.categorycode",
			new_permission."category.categorydescription",
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
		RAISE NOTICE 'Found category %, skipping insert', new_permission."category.categorycode";
	END IF;
	
	FOR a IN array_lower(assessmentprogram_abbrs, 1) .. array_upper(assessmentprogram_abbrs, 1) LOOP
		FOR org IN (
			SELECT o.id, o.displayidentifier
			FROM orgassessmentprogram oap
			JOIN organization o ON oap.organizationid = o.id
			JOIN assessmentprogram ap ON oap.assessmentprogramid = ap.id
			WHERE oap.activeflag = TRUE AND o.activeflag = TRUE
			AND ap.abbreviatedname = assessmentprogram_abbrs[a]
			AND o.organizationtypeid = (SELECT id FROM organizationtype WHERE typecode = 'ST')
			ORDER BY o.displayidentifier
		) LOOP
			FOR i IN array_lower(contentarea_abbrs, 1) .. array_upper(contentarea_abbrs, 1) LOOP
				IF NOT EXISTS (
					SELECT rap.id
					FROM reportassessmentprogram rap
					JOIN category c ON rap.reporttypeid = c.id
					JOIN assessmentprogram ap ON rap.assessmentprogramid = ap.id
					JOIN organization o ON rap.stateid = o.id
					JOIN contentarea ca ON rap.subjectid = ca.id
					JOIN authorities auth ON rap.authorityid = auth.id
					WHERE c.categorycode = new_permission."category.categorycode"
					AND ap.abbreviatedname = assessmentprogram_abbrs[a]
					AND o.id = org.id
					AND ca.abbreviatedname = contentarea_abbrs[i]
					AND auth.authority = new_permission."authorities.authority"
				) THEN
					INSERT INTO reportassessmentprogram (reporttypeid, assessmentprogramid, createdate, modifieddate, readytoview, activeflag, stateid, subjectid, authorityid)
					VALUES (
						(SELECT id FROM category WHERE categorycode = new_permission."category.categorycode"),
						(SELECT id FROM assessmentprogram WHERE abbreviatedname = assessmentprogram_abbrs[a]),
						now_gmt,
						now_gmt,
						TRUE,
						TRUE,
						org.id,
						(SELECT id FROM contentarea WHERE abbreviatedname = contentarea_abbrs[i]),
						(SELECT id FROM authorities WHERE authority = new_permission."authorities.authority")
					);
				ELSE
					RAISE NOTICE 'Found reportassessmentprogram entry for % (%) in % state (%), skipping insert',
						new_permission."category.categorydescription", contentarea_abbrs[i], assessmentprogram_abbrs[a], org.displayidentifier;
				END IF;
			END LOOP;
		END LOOP;
	END LOOP;
END;
$BODY$;
         
                      