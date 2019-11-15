-- dml/581.sql

-- MDPT essay permissions
DO
$BODY$
DECLARE
	now_gmt TIMESTAMP WITH TIME ZONE;
	cetesysadminid BIGINT;
	/*groupcodes TEXT[] := ARRAY[
		'SSAD'
	];*/
BEGIN
	SELECT now() INTO now_gmt;
	SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
	
	IF NOT EXISTS (
		SELECT id FROM authorities WHERE authority = 'VIEW_GENERAL_STUDENT_MDPT_RESP'
	) THEN
		INSERT INTO authorities (authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser)
		VALUES (
			'VIEW_GENERAL_STUDENT_MDPT_RESP',
			'View General Student MDPT',
			'Reports-Performance Reports',
			now_gmt,
			cetesysadminid,
			TRUE,
			now_gmt,
			cetesysadminid
		);
	ELSE
		RAISE NOTICE 'Found permission ''VIEW_GENERAL_STUDENT_MDPT_RESP'', skipping insert';
	END IF;
	
	IF NOT EXISTS (
		SELECT id FROM category WHERE categorycode = 'GEN_ST_MDPT'
	) THEN
		INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
		VALUES (
			'Students (MDPTs)',
			'GEN_ST_MDPT',
			'General Student (MDPT)',
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
		RAISE NOTICE 'Found category ''GEN_ST_MDPT'', skipping insert';
	END IF;
	
	IF NOT EXISTS (
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
	) THEN
		INSERT INTO reportassessmentprogram (reporttypeid, assessmentprogramid, createdate, modifieddate, readytoview, activeflag, stateid, subjectid, authorityid)
		VALUES (
			(SELECT id FROM category WHERE categorycode = 'GEN_ST_MDPT'),
			(SELECT id FROM assessmentprogram WHERE abbreviatedname = 'KAP'),
			now_gmt,
			now_gmt,
			TRUE,
			TRUE,
			(SELECT id FROM organization WHERE displayidentifier = 'KS' AND organizationtypeid = (SELECT id FROM organizationtype WHERE typecode = 'ST')),
			(SELECT id FROM contentarea WHERE abbreviatedname = 'ELA'),
			(SELECT id FROM authorities WHERE authority = 'VIEW_GENERAL_STUDENT_MDPT_RESP')
		);
	ELSE
		RAISE NOTICE 'Found reportassessmentprogram entry for MDPT report in KS (KAP), skipping insert';
	END IF;
	
	/*FOR i IN array_lower(groupcodes, 1) .. array_upper(groupcodes, 1) LOOP
		-- add permissions to specific roles
		IF NOT EXISTS (
			SELECT ga.id
			FROM groupauthorities ga
			JOIN groups g ON ga.groupid = g.id
			JOIN authorities a ON ga.authorityid = a.id
			JOIN organization o ON ga.organizationid = o.id
			JOIN assessmentprogram ap ON ga.assessmentprogramid = ap.id
			WHERE g.groupcode = groupcodes[i]
			AND a.authority = 'VIEW_GENERAL_STUDENT_MDPT_RESP'
			AND o.displayidentifier = 'KS' AND o.organizationtypeid = (SELECT id FROM organizationtype WHERE typecode = 'ST')
			AND ap.abbreviatedname = 'KAP'
		) THEN
			INSERT INTO groupauthorities (groupid, authorityid, createddate, createduser, activeflag, modifieddate, modifieduser, organizationid, assessmentprogramid)
			VALUES (
				(SELECT id FROM groups WHERE groupcode = groupcodes[i]),
				(SELECT id FROM authorities WHERE authority = 'VIEW_GENERAL_STUDENT_MDPT_RESP'),
				now_gmt,
				cetesysadminid,
				TRUE,
				now_gmt,
				cetesysadminid,
				(SELECT id FROM organization WHERE displayidentifier = 'KS' AND organizationtypeid = (SELECT id FROM organizationtype WHERE typecode = 'ST')),
				(SELECT id FROM assessmentprogram WHERE abbreviatedname = 'KAP')
			);
		ELSE
			RAISE NOTICE 'Found permission ''VIEW_GENERAL_STUDENT_MDPT_RESP'' tied to % in KS (KAP), skipping insert', groupcodes[i];
		END IF;
		
		-- add entries for these reports to be visible on the UI
		IF NOT EXISTS (
			SELECT rapg.id
			FROM reportassessmentprogramgroup rapg
			JOIN reportassessmentprogram rap ON rapg.reportassessmentprogramid = rap.id
			JOIN groups g ON rapg.groupid = g.id
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
			AND g.groupcode = groupcodes[i]
		) THEN
			INSERT INTO reportassessmentprogramgroup (reportassessmentprogramid, groupid, activeflag)
			VALUES (
				(
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
				),
				(SELECT id FROM groups WHERE groupcode = groupcodes[i]),
				TRUE
			);
		ELSE
			RAISE NOTICE 'Found reportassessmentprogramgroup entry for KAP MDPT reports for %, skipping insert', groupcodes[i];
		END IF;
	END LOOP;*/
END;
$BODY$;

-- dml/581.sql

-- MDPT essay permissions
DO
$BODY$
DECLARE
	now_gmt TIMESTAMP WITH TIME ZONE;
	cetesysadminid BIGINT;
	/*groupcodes TEXT[] := ARRAY[
		'SSAD'
	];*/
BEGIN
	SELECT now() INTO now_gmt;
	SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
	
	IF NOT EXISTS (
		SELECT id FROM authorities WHERE authority = 'VIEW_GENERAL_STUDENT_MDPT_RESP'
	) THEN
		INSERT INTO authorities (authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser)
		VALUES (
			'VIEW_GENERAL_STUDENT_MDPT_RESP',
			'View General Student MDPT',
			'Reports-Performance Reports',
			now_gmt,
			cetesysadminid,
			TRUE,
			now_gmt,
			cetesysadminid
		);
	ELSE
		RAISE NOTICE 'Found permission ''VIEW_GENERAL_STUDENT_MDPT_RESP'', skipping insert';
	END IF;
	
	IF NOT EXISTS (
		SELECT id FROM category WHERE categorycode = 'GEN_ST_MDPT'
	) THEN
		INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
		VALUES (
			'Student (MDPT)',
			'GEN_ST_MDPT',
			'General Student (MDPT)',
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
		RAISE NOTICE 'Found category ''GEN_ST_MDPT'', skipping insert';
	END IF;
	
	IF NOT EXISTS (
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
	) THEN
		INSERT INTO reportassessmentprogram (reporttypeid, assessmentprogramid, createdate, modifieddate, readytoview, activeflag, stateid, subjectid, authorityid)
		VALUES (
			(SELECT id FROM category WHERE categorycode = 'GEN_ST_MDPT'),
			(SELECT id FROM assessmentprogram WHERE abbreviatedname = 'KAP'),
			now_gmt,
			now_gmt,
			TRUE,
			TRUE,
			(SELECT id FROM organization WHERE displayidentifier = 'KS' AND organizationtypeid = (SELECT id FROM organizationtype WHERE typecode = 'ST')),
			(SELECT id FROM contentarea WHERE abbreviatedname = 'ELA'),
			(SELECT id FROM authorities WHERE authority = 'VIEW_GENERAL_STUDENT_MDPT_RESP')
		);
	ELSE
		RAISE NOTICE 'Found reportassessmentprogram entry for MDPT report in KS (KAP), skipping insert';
	END IF;
	
	/*FOR i IN array_lower(groupcodes, 1) .. array_upper(groupcodes, 1) LOOP
		-- add permissions to specific roles
		IF NOT EXISTS (
			SELECT ga.id
			FROM groupauthorities ga
			JOIN groups g ON ga.groupid = g.id
			JOIN authorities a ON ga.authorityid = a.id
			JOIN organization o ON ga.organizationid = o.id
			JOIN assessmentprogram ap ON ga.assessmentprogramid = ap.id
			WHERE g.groupcode = groupcodes[i]
			AND a.authority = 'VIEW_GENERAL_STUDENT_MDPT_RESP'
			AND o.displayidentifier = 'KS' AND o.organizationtypeid = (SELECT id FROM organizationtype WHERE typecode = 'ST')
			AND ap.abbreviatedname = 'KAP'
		) THEN
			INSERT INTO groupauthorities (groupid, authorityid, createddate, createduser, activeflag, modifieddate, modifieduser, organizationid, assessmentprogramid)
			VALUES (
				(SELECT id FROM groups WHERE groupcode = groupcodes[i]),
				(SELECT id FROM authorities WHERE authority = 'VIEW_GENERAL_STUDENT_MDPT_RESP'),
				now_gmt,
				cetesysadminid,
				TRUE,
				now_gmt,
				cetesysadminid,
				(SELECT id FROM organization WHERE displayidentifier = 'KS' AND organizationtypeid = (SELECT id FROM organizationtype WHERE typecode = 'ST')),
				(SELECT id FROM assessmentprogram WHERE abbreviatedname = 'KAP')
			);
		ELSE
			RAISE NOTICE 'Found permission ''VIEW_GENERAL_STUDENT_MDPT_RESP'' tied to % in KS (KAP), skipping insert', groupcodes[i];
		END IF;
		
		-- add entries for these reports to be visible on the UI
		IF NOT EXISTS (
			SELECT rapg.id
			FROM reportassessmentprogramgroup rapg
			JOIN reportassessmentprogram rap ON rapg.reportassessmentprogramid = rap.id
			JOIN groups g ON rapg.groupid = g.id
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
			AND g.groupcode = groupcodes[i]
		) THEN
			INSERT INTO reportassessmentprogramgroup (reportassessmentprogramid, groupid, activeflag)
			VALUES (
				(
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
				),
				(SELECT id FROM groups WHERE groupcode = groupcodes[i]),
				TRUE
			);
		ELSE
			RAISE NOTICE 'Found reportassessmentprogramgroup entry for KAP MDPT reports for %, skipping insert', groupcodes[i];
		END IF;
	END LOOP;*/
END;
$BODY$;



create or replace function repopulateActiveNos()
returns void as $body$
declare
	missing_user_rec record;
	user_rec record;
	userorgs_rec record;
	activation_no varchar;
begin
	RAISE NOTICE 'Populating missing user activation numbers';

	for user_rec in select distinct id from aartuser where activeflag is true 
	and id in (select distinct uo.aartuserid from usersorganizations uo, userorganizationsgroups uog where uo.id = uog.userorganizationid 
		and uog.activationno is null)
	order by id asc loop 
		RAISE NOTICE 'processing user : % ', user_rec.id;
		select '' into activation_no;
		for userorgs_rec in select uog.id from usersorganizations uo, userorganizationsgroups uog where uo.id = uog.userorganizationid and
		uo.aartuserid = user_rec.id and uog.activationno is null loop
			--raise notice 'Missing activation no for % with uog : %', user_rec.id, userorgs_rec;
			select distinct activationno from usersorganizations uo, userorganizationsgroups uog where uo.id = uog.userorganizationid and
				uo.aartuserid = user_rec.id and uog.activationno is not null into activation_no;
			update userorganizationsgroups set activationno = case 
				when activation_no is null then (user_rec.id::text || userorgs_rec.id::text) 
				else activation_no end, 
			modifieddate=now(), 
			modifieduser= (select id from aartuser where username='cetesysadmin')
			where id = userorgs_rec.id;
		end loop;
	end loop;
end;
$body$language plpgsql;
select repopulateActiveNos();