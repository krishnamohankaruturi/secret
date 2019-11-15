--DE18510 fix ISMART Auto Enrollment - add complexity bands by gradeband

DO
$BODY$
DECLARE
    iSmartContentAreaId BIGINT := (SELECT id FROM contentarea WHERE abbreviatedname = 'IS-Sci');
   	iSmartAssessmentProgramId BIGINT := (select id from assessmentprogram where activeflag is true and abbreviatedname ='I-SMART');
	iSmartGradeBand3Thru5 BIGINT := (select id from gradeband where abbreviatedname = 'IS.3-5' and contentareaid = iSmartContentAreaId);
	iSmartGradeBand6Thru8 BIGINT := (select id from gradeband where abbreviatedname = 'IS.6-8' and contentareaid = iSmartContentAreaId);
	iSmartGradeBand9Thru12 BIGINT := (select id from gradeband where abbreviatedname = 'IS.9-12' and contentareaid = iSmartContentAreaId);

BEGIN
    IF EXISTS (
        SELECT 1
        FROM complexityband
        WHERE contentareaid = iSmartContentAreaId
			AND assessmentprogramid = iSmartAssessmentProgramId
    ) THEN
        RAISE NOTICE 'Found complexity bands for ISMART program with id:% content areas.', iSmartContentAreaId;
		--update existing ISMART records to be the elementary gradeband records
        UPDATE complexityband
		SET
			minrange = 0.0,
			maxrange = 2.4193,
			gradebandid = iSmartGradeBand3Thru5
		WHERE
			id in (332, 333)
			AND contentareaid = iSmartContentAreaId
			AND assessmentprogramid = iSmartAssessmentProgramId
			AND bandcode in ('0','1');
			
        UPDATE complexityband
		SET
			minrange = 2.4194,
			maxrange = 3.0,
			gradebandid = iSmartGradeBand3Thru5
		WHERE
			id in (334, 335)
			AND contentareaid = iSmartContentAreaId
			AND assessmentprogramid = iSmartAssessmentProgramId
			AND bandcode in ('2','3');			
		
		RAISE NOTICE 'Updated existing complexity bands to be elementary school bands.';
		
		--insert middle school complexityband records
		
		IF NOT EXISTS(
			select 1 from complexityband where id=336
		) THEN
			Raise notice 'Inserting Band 0 for middle school.';
			INSERT INTO complexityband(id, bandname, bandcode, minrange, maxrange, contentareaid, assessmentprogramid, gradebandid)
    		VALUES (336,'FOUNDATIONAL', '0', 0.0, 2.4193, iSmartContentAreaId, iSmartAssessmentProgramId, iSmartGradeBand6Thru8);
    	ELSE
    		Raise notice 'Band 0 from middle school was already inserted.';
		END IF;
    	IF NOT EXISTS(
			select 1 from complexityband where id=337
		) THEN
			Raise notice 'Inserting Band 1 for middle school.';
			INSERT INTO complexityband(id, bandname, bandcode, minrange, maxrange, contentareaid, assessmentprogramid, gradebandid)
    		VALUES (337,'BAND_1', '1', 0.0, 2.4193, iSmartContentAreaId, iSmartAssessmentProgramId, iSmartGradeBand6Thru8);
    	ELSE
    		Raise notice 'Band 1 from middle school was already inserted.';
		END IF;
    	IF NOT EXISTS(
			select 1 from complexityband where id=338
		) THEN		
			Raise notice 'Inserting Band 2 for middle school.';
			INSERT INTO complexityband(id, bandname, bandcode, minrange, maxrange, contentareaid, assessmentprogramid, gradebandid)
    		VALUES (338,'BAND_2', '2', 2.4194, 3.0, iSmartContentAreaId, iSmartAssessmentProgramId, iSmartGradeBand6Thru8);
    	ELSE
    		Raise notice 'Band 2 from middle school was already inserted.';
		END IF;
    	IF NOT EXISTS(
			select 1 from complexityband where id=339
		) THEN		
			Raise notice 'Inserting Band 3 for middle school.';    		
			INSERT INTO complexityband(id, bandname, bandcode, minrange, maxrange, contentareaid, assessmentprogramid, gradebandid)
    		VALUES (339,'BAND_3', '3', 2.4194, 3.0, iSmartContentAreaId, iSmartAssessmentProgramId, iSmartGradeBand6Thru8);
    	ELSE
    		Raise notice 'Band 3 from middle school was already inserted.';
		END IF;		

		--insert high school complexityband records
    	IF NOT EXISTS(
			select 1 from complexityband where id=340
		) THEN		
			Raise notice 'Inserting Band 0 for high school.';    		
			INSERT INTO complexityband(id, bandname, bandcode, minrange, maxrange, contentareaid, assessmentprogramid, gradebandid)
    		VALUES (340,'FOUNDATIONAL', '0', 0.0, 1.4374, iSmartContentAreaId, iSmartAssessmentProgramId, iSmartGradeBand9Thru12);
    	ELSE
    		Raise notice 'Band 0 from high school was already inserted.';
		END IF;		 
    	IF NOT EXISTS(
			select 1 from complexityband where id=341
		) THEN		
			Raise notice 'Inserting Band 1 for high school.';   		
			INSERT INTO complexityband(id, bandname, bandcode, minrange, maxrange, contentareaid, assessmentprogramid, gradebandid)
    		VALUES (341,'BAND_1', '1', 1.4375, 2.4193, iSmartContentAreaId, iSmartAssessmentProgramId, iSmartGradeBand9Thru12);
    	ELSE
    		Raise notice 'Band 1 from high school was already inserted.';
		END IF;		 
    	IF NOT EXISTS(
			select 1 from complexityband where id=342
		) THEN		
			Raise notice 'Inserting Band 2 for high school.';     		
			INSERT INTO complexityband(id, bandname, bandcode, minrange, maxrange, contentareaid, assessmentprogramid, gradebandid)
    		VALUES (342,'BAND_2', '2', 2.4194, 3.0, iSmartContentAreaId, iSmartAssessmentProgramId, iSmartGradeBand9Thru12);
    	ELSE
    		Raise notice 'Band 2 from high school was already inserted.';
		END IF;		 
    	IF NOT EXISTS(
			select 1 from complexityband where id=343
		) THEN		
			Raise notice 'Inserting Band 3 for high school.';     		
			INSERT INTO complexityband(id, bandname, bandcode, minrange, maxrange, contentareaid, assessmentprogramid, gradebandid)
    		VALUES (343,'BAND_3', '3', 2.4194, 3.0, iSmartContentAreaId, iSmartAssessmentProgramId, iSmartGradeBand9Thru12);
    	ELSE
    		Raise notice 'Band 3 from high school was already inserted.';
		END IF;			
    ELSE
        RAISE NOTICE 'Did not find ismart complexity bands with id:% content area. skipping', iSmartContentAreaId;
    END IF;
    RAISE NOTICE 'Complete.';
END;
$BODY$;



--originally planned in dml/781.sql, but necessity of patch made it go faster.

DO $BODY$
DECLARE
	cetesysadmin BIGINT := (SELECT id FROM aartuser WHERE username = 'cetesysadmin' LIMIT 1);
BEGIN
	IF EXISTS (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'PLTW')
	AND NOT EXISTS (
		SELECT conf.id
		FROM appconfiguration conf
		JOIN assessmentprogram ap ON conf.assessmentprogramid = ap.id
		WHERE ap.abbreviatedname = 'PLTW'
		AND conf.attrcode = 'scoring-api-delay'
	) THEN
		RAISE NOTICE 'Did not find PLTW scoring API delay, inserting...';
		INSERT INTO appconfiguration (attrcode, attrtype, attrname, attrvalue, createduser, modifieduser, assessmentprogramid)
		VALUES (
			'scoring-api-delay',
			'PSQL interval string',
			'Scoring API delay interval',
			'1 day',
			cetesysadmin,
			cetesysadmin,
			(SELECT id FROM assessmentprogram WHERE abbreviatedname = 'PLTW')
		);
	ELSE
		RAISE NOTICE 'Found PLTW scoring API delay, skipping insert...';
	END IF;
END;
$BODY$;

IF NOT EXISTS(
		select * from category where categorycode like '11111' and categorytypeid =69
	) THEN
		INSERT INTO category
		(categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
		VALUES('7', '11111', 'Two or More Races', 69, NULL , 'AART_ORIG_CODE', 'now'::text::timestamp with time zone, 12, true, 'now'::text::timestamp with time zone, 12);
END IF;


DO $BODY$
DECLARE
    cetesysadmin BIGINT := (SELECT id FROM aartuser WHERE username = 'cetesysadmin' LIMIT 1);
BEGIN
    IF EXISTS (
        SELECT id
        FROM assessmentprogram
        WHERE activeflag IS TRUE
        AND abbreviatedname = 'PLTW'
    )
    AND EXISTS (
        SELECT fs.id
        FROM fieldspecification fs
        JOIN fieldspecificationsrecordtypes fsrt ON fs.id = fsrt.fieldspecificationid AND fsrt.activeflag IS TRUE
        JOIN category c ON fsrt.recordtypeid = c.id AND c.activeflag IS TRUE
        JOIN categorytype ct ON c.categorytypeid = ct.id AND ct.activeflag IS TRUE
        WHERE fs.activeflag IS TRUE
        AND ct.typecode = 'CSV_RECORD_TYPE'
        AND c.categorycode = 'PERSONAL_NEEDS_PROFILE_RECORD_TYPE'
        AND fs.fieldname = 'comprehensiveRace'
        AND fs.allowablevalues = '{'''',1,2,4,5,6,7,8}'
    )
    AND NOT EXISTS (
        SELECT c.id
        FROM category c
        JOIN categorytype ct ON c.categorytypeid = ct.id AND ct.activeflag IS TRUE
        WHERE c.activeflag IS TRUE
        AND ct.typecode = 'COMPREHENSIVE_RACE'
        AND c.categorycode = '0'
    ) THEN
        RAISE NOTICE 'Found PLTW and race field that needs updating';
        
        RAISE NOTICE 'Updating allowable values for race in PNP upload';
        
        UPDATE fieldspecification
        SET allowablevalues = '{'''',0,1,2,4,5,6,7,8}',
        modifieddate = now(),
        modifieduser = cetesysadmin
        WHERE id IN (
            SELECT fs.id
            FROM fieldspecification fs
            JOIN fieldspecificationsrecordtypes fsrt ON fs.id = fsrt.fieldspecificationid AND fsrt.activeflag IS TRUE
            JOIN category c ON fsrt.recordtypeid = c.id AND c.activeflag IS TRUE
            JOIN categorytype ct ON c.categorytypeid = ct.id AND ct.activeflag IS TRUE
            WHERE fs.activeflag IS TRUE
            AND ct.typecode = 'CSV_RECORD_TYPE'
            AND c.categorycode = 'PERSONAL_NEEDS_PROFILE_RECORD_TYPE'
            AND fs.fieldname = 'comprehensiveRace'
        );
        
        RAISE NOTICE 'Inserting % as race', 0;
        
        INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createduser, modifieduser)
        VALUES (
            'Unspecified', -- categoryname
            '0', -- categorycode
            'Race not specified', -- categorydescription
            (SELECT id FROM categorytype WHERE typecode = 'COMPREHENSIVE_RACE'), -- categorytypeid
            'AART_ORIG_CODE', -- originationcode
            cetesysadmin, -- createduser
            cetesysadmin -- modifieduser
        );
    ELSE
        RAISE NOTICE 'Did not find PLTW or race data that needs updating, skipping updates...';
    END IF;
END;
$BODY$;
