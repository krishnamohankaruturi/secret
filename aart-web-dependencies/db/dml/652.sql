--dml/652.sql --- F457 - DLM reports 

--StudentSummary(Bundled)
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('VIEW_ALT_STD_SUMMARY_BUNDLED_REP', 'View Alt Assess YearEnd Student Summary Bundled Report', 
    'Reports-Performance Reports',now(),(select id from aartuser where email='cete@ku.edu'), true, 
            now(),(select id from aartuser where email='cete@ku.edu'));


INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Student Summary &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(Bundled)','ALT_ST_SUM_ALL','DLM Student Summary(Bundled) Reports',(select id from categorytype where typecode = 'REPORT_TYPES_UI'),null,'AART',current_timestamp,(select id from aartuser where username like 'cetesysadmin'),true,current_timestamp,(select id from aartuser where username like 'cetesysadmin'));


update category set categoryname='Student Summary &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(Bundled)' where categorycode='ALT_ST_SUM_ALL';

--SchoolSummary(Bundled)
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('VIEW_ALT_SCH_SUMMARY_BUNDLED_REP', 'View Alt Assess YearEnd School Summary Bundled Report', 
    'Reports-Performance Reports',now(),(select id from aartuser where email='cete@ku.edu'), true, 
            now(),(select id from aartuser where email='cete@ku.edu'));


INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('School (Bundled)','ALT_SCH_SUM_ALL','DLM School Summary(Bundled) Reports',(select id from categorytype where typecode = 'REPORT_TYPES_UI'),null,'AART',current_timestamp,(select id from aartuser where username like 'cetesysadmin'),true,current_timestamp,(select id from aartuser where username like 'cetesysadmin'));

--Student Summary
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('VIEW_ALT_STUDENT_SUMMARY_REP', 'View Alternate Assessment Student Summary Report', 
    'Reports-Performance Reports',now(),(select id from aartuser where email='cete@ku.edu'), true, 
            now(),(select id from aartuser where email='cete@ku.edu'));


INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Student Summary','ALT_STD_SUMMARY','DLM Student Summary Reports',(select id from categorytype where typecode = 'REPORT_TYPES_UI'),null,'AART',current_timestamp,(select id from aartuser where username like 'cetesysadmin'),true,current_timestamp,(select id from aartuser where username like 'cetesysadmin'));

--Classroom
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('VIEW_ALT_CLASSROOM_REPORT', 'View Alternate Assessment Classroom Report', 
    'Reports-Performance Reports',now(),(select id from aartuser where email='cete@ku.edu'), true, 
            now(),(select id from aartuser where email='cete@ku.edu'));


INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Classroom','ALT_CLASS_ROOM','DLM Classroom Reports',(select id from categorytype where typecode = 'REPORT_TYPES_UI'),null,'AART',current_timestamp,(select id from aartuser where username like 'cetesysadmin'),true,current_timestamp,(select id from aartuser where username like 'cetesysadmin'));

--School
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('VIEW_ALT_SCHOOL_REPORT', 'View Alternate Assessment School Report', 
    'Reports-Performance Reports',now(),(select id from aartuser where email='cete@ku.edu'), true, 
            now(),(select id from aartuser where email='cete@ku.edu'));


INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('School','ALT_SCHOOL','DLM School Reports',(select id from categorytype where typecode = 'REPORT_TYPES_UI'),null,'AART',current_timestamp,(select id from aartuser where username like 'cetesysadmin'),true,current_timestamp,(select id from aartuser where username like 'cetesysadmin'));


--Dynamic StudentSummary(Bundled)
--Create permission
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('CREATE_ALT_STD_SUM_DYN_BNDL_REP', 'Create Student Summary (Dynamic Bundled) - Alt Assess', 
    'Reports-Performance Reports',now(),(select id from aartuser where email='cete@ku.edu'), true, 
            now(),(select id from aartuser where email='cete@ku.edu'));
--Scheduler            
INSERT INTO batchjobschedule(
            jobname, jobrefname, initmethod, cronexpression, scheduled, 
            allowedserver)
    VALUES ('Student Summary(Dynamic Bundled) Report scheduler', 'dynamicStudentSummaryBundledReportScheduler', 'startbatchStudentSummaryDynamicBundledReportProcess', '0 0/5 * * * ?', false, 'localhost');


--StudentSummary bundled permission
--VIEW_ALT_STD_SUMMARY_BUNDLED_REP                      
DO
$BODY$
DECLARE
	now_gmt TIMESTAMP WITH TIME ZONE;
	cetesysadminid BIGINT;
	new_permission RECORD;
	org RECORD;
	
	assessmentprogram_abbrs TEXT[] := ARRAY['DLM'];
BEGIN
	SELECT now() INTO now_gmt;
	SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
	
	-- set up new permission data
	SELECT
		'Student Summary &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(Bundled)'::VARCHAR(100) AS "category.categoryname",
		'ALT_ST_SUM_ALL'::VARCHAR(75) AS "category.categorycode",
		'DLM Student Summary(Bundled) Reports'::VARCHAR(200) AS "category.categorydescription",
		'VIEW_ALT_STD_SUMMARY_BUNDLED_REP'::VARCHAR(32) AS "authorities.authority",
		'View Alt Assess YearEnd Student Summary Bundled Report'::VARCHAR(55) AS "authorities.displayname",
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
			
				IF NOT EXISTS (
					SELECT rap.id
					FROM reportassessmentprogram rap
					JOIN category c ON rap.reporttypeid = c.id
					JOIN assessmentprogram ap ON rap.assessmentprogramid = ap.id
					JOIN organization o ON rap.stateid = o.id
					JOIN authorities auth ON rap.authorityid = auth.id
					WHERE c.categorycode = new_permission."category.categorycode"
					AND ap.abbreviatedname = assessmentprogram_abbrs[a]
					AND o.id = org.id					
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
						null,
						(SELECT id FROM authorities WHERE authority = new_permission."authorities.authority")
					);
				ELSE
					RAISE NOTICE 'Found reportassessmentprogram entry for (%) in % state (%), skipping insert',
						new_permission."category.categorydescription", assessmentprogram_abbrs[a], org.displayidentifier;
				END IF;
			
		END LOOP;
	END LOOP;
END;
$BODY$;
         

--SchoolSummary Bundled permission
DO
$BODY$
DECLARE
	now_gmt TIMESTAMP WITH TIME ZONE;
	cetesysadminid BIGINT;
	new_permission RECORD;
	org RECORD;
	
	assessmentprogram_abbrs TEXT[] := ARRAY['DLM'];
BEGIN
	SELECT now() INTO now_gmt;
	SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
	
	-- set up new permission data
	SELECT
		'School (Bundled)'::VARCHAR(100) AS "category.categoryname",
		'ALT_SCH_SUM_ALL'::VARCHAR(75) AS "category.categorycode",
		'DLM School Summary(Bundled) Reports'::VARCHAR(200) AS "category.categorydescription",
		'VIEW_ALT_SCH_SUMMARY_BUNDLED_REP'::VARCHAR(32) AS "authorities.authority",
		'View Alt Assess YearEnd School Summary Bundled Report'::VARCHAR(55) AS "authorities.displayname",
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
			
				IF NOT EXISTS (
					SELECT rap.id
					FROM reportassessmentprogram rap
					JOIN category c ON rap.reporttypeid = c.id
					JOIN assessmentprogram ap ON rap.assessmentprogramid = ap.id
					JOIN organization o ON rap.stateid = o.id
					JOIN authorities auth ON rap.authorityid = auth.id
					WHERE c.categorycode = new_permission."category.categorycode"
					AND ap.abbreviatedname = assessmentprogram_abbrs[a]
					AND o.id = org.id					
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
						null,
						(SELECT id FROM authorities WHERE authority = new_permission."authorities.authority")
					);
				ELSE
					RAISE NOTICE 'Found reportassessmentprogram entry for (%) in % state (%), skipping insert',
						new_permission."category.categorydescription", assessmentprogram_abbrs[a], org.displayidentifier;
				END IF;
			
		END LOOP;
	END LOOP;
END;
$BODY$;


--Student Summary
DO
$BODY$
DECLARE
	now_gmt TIMESTAMP WITH TIME ZONE;
	cetesysadminid BIGINT;
	new_permission RECORD;
	org RECORD;
	
	assessmentprogram_abbrs TEXT[] := ARRAY['DLM'];
BEGIN
	SELECT now() INTO now_gmt;
	SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
	
	-- set up new permission data
	SELECT
		'Student Summary'::VARCHAR(100) AS "category.categoryname",
		'ALT_STD_SUMMARY'::VARCHAR(75) AS "category.categorycode",
		'DLM Student Summary Reports'::VARCHAR(200) AS "category.categorydescription",
		'VIEW_ALT_STUDENT_SUMMARY_REP'::VARCHAR(32) AS "authorities.authority",
		'View Alternate Assessment Student Summary Report'::VARCHAR(55) AS "authorities.displayname",
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
			
				IF NOT EXISTS (
					SELECT rap.id
					FROM reportassessmentprogram rap
					JOIN category c ON rap.reporttypeid = c.id
					JOIN assessmentprogram ap ON rap.assessmentprogramid = ap.id
					JOIN organization o ON rap.stateid = o.id
					JOIN authorities auth ON rap.authorityid = auth.id
					WHERE c.categorycode = new_permission."category.categorycode"
					AND ap.abbreviatedname = assessmentprogram_abbrs[a]
					AND o.id = org.id					
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
						null,
						(SELECT id FROM authorities WHERE authority = new_permission."authorities.authority")
					);
				ELSE
					RAISE NOTICE 'Found reportassessmentprogram entry for (%) in % state (%), skipping insert',
						new_permission."category.categorydescription", assessmentprogram_abbrs[a], org.displayidentifier;
				END IF;
			
		END LOOP;
	END LOOP;
END;
$BODY$;

--School

DO
$BODY$
DECLARE
	now_gmt TIMESTAMP WITH TIME ZONE;
	cetesysadminid BIGINT;
	new_permission RECORD;
	org RECORD;
	
	assessmentprogram_abbrs TEXT[] := ARRAY['DLM'];
BEGIN
	SELECT now() INTO now_gmt;
	SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
	
	-- set up new permission data
	SELECT
		'School'::VARCHAR(100) AS "category.categoryname",
		'ALT_SCHOOL'::VARCHAR(75) AS "category.categorycode",
		'DLM School Reports'::VARCHAR(200) AS "category.categorydescription",
		'VIEW_ALT_SCHOOL_REPORT'::VARCHAR(32) AS "authorities.authority",
		'View Alternate Assessment School Report'::VARCHAR(55) AS "authorities.displayname",
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
			
				IF NOT EXISTS (
					SELECT rap.id
					FROM reportassessmentprogram rap
					JOIN category c ON rap.reporttypeid = c.id
					JOIN assessmentprogram ap ON rap.assessmentprogramid = ap.id
					JOIN organization o ON rap.stateid = o.id
					JOIN authorities auth ON rap.authorityid = auth.id
					WHERE c.categorycode = new_permission."category.categorycode"
					AND ap.abbreviatedname = assessmentprogram_abbrs[a]
					AND o.id = org.id					
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
						null,
						(SELECT id FROM authorities WHERE authority = new_permission."authorities.authority")
					);
				ELSE
					RAISE NOTICE 'Found reportassessmentprogram entry for (%) in % state (%), skipping insert',
						new_permission."category.categorydescription", assessmentprogram_abbrs[a], org.displayidentifier;
				END IF;
			
		END LOOP;
	END LOOP;
END;
$BODY$;

--Classroom
DO
$BODY$
DECLARE
	now_gmt TIMESTAMP WITH TIME ZONE;
	cetesysadminid BIGINT;
	new_permission RECORD;
	org RECORD;
	
	assessmentprogram_abbrs TEXT[] := ARRAY['DLM'];
BEGIN
	SELECT now() INTO now_gmt;
	SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
	
	-- set up new permission data
	SELECT
		'Classroom'::VARCHAR(100) AS "category.categoryname",
		'ALT_CLASS_ROOM'::VARCHAR(75) AS "category.categorycode",
		'DLM Classroom Reports'::VARCHAR(200) AS "category.categorydescription",
		'VIEW_ALT_CLASSROOM_REPORT'::VARCHAR(32) AS "authorities.authority",
		'View Alternate Assessment Classroom Report'::VARCHAR(55) AS "authorities.displayname",
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
			
				IF NOT EXISTS (
					SELECT rap.id
					FROM reportassessmentprogram rap
					JOIN category c ON rap.reporttypeid = c.id
					JOIN assessmentprogram ap ON rap.assessmentprogramid = ap.id
					JOIN organization o ON rap.stateid = o.id
					JOIN authorities auth ON rap.authorityid = auth.id
					WHERE c.categorycode = new_permission."category.categorycode"
					AND ap.abbreviatedname = assessmentprogram_abbrs[a]
					AND o.id = org.id					
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
						null,
						(SELECT id FROM authorities WHERE authority = new_permission."authorities.authority")
					);
				ELSE
					RAISE NOTICE 'Found reportassessmentprogram entry for (%) in % state (%), skipping insert',
						new_permission."category.categorydescription", assessmentprogram_abbrs[a], org.displayidentifier;
				END IF;
			
		END LOOP;
	END LOOP;
END;
$BODY$;
