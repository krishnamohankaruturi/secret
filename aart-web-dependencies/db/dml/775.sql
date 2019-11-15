--dml/775.sql

insert into batchjobschedule (jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
values (
    'PLTW Scoring Nightly Job',
    'pltwScoringNightlyScheduler',
    'run',
    '0 0 21 * * ?',
    false,
    (
        select coalesce((
            select allowedserver
            from batchjobschedule
            where allowedserver != (select allowedserver from batchjobschedule where jobname ~* 'pltw batch auto job')
            order by random()
            limit 1
        ), '')
    )
);

insert into batchjobschedule (jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
values (
    'PLTW Scoring Daytime Job',
    'pltwScoringDaytimeScheduler',
    'run',
    '0 0 8-20 * * ?',
    false,
    (
        select case when allowedserver is not null then allowedserver else '' end
        from batchjobschedule
        where jobname ~* 'pltw scoring nightly job' limit 1
    )
);

DO
$BODY$
BEGIN
	IF EXISTS (SELECT 1 FROM assessmentprogram WHERE abbreviatedname = 'PLTW') THEN
        IF NOT EXISTS (
            SELECT 1
            FROM appconfiguration conf
            JOIN assessmentprogram ap ON conf.assessmentprogramid = ap.id AND ap.activeflag IS TRUE
            WHERE conf.activeflag IS TRUE
            AND conf.attrtype = 'scoring-api-url'
            AND ap.abbreviatedname = 'PLTW'
        ) THEN
        	RAISE NOTICE 'Did not find scoring-api-url for PLTW, inserting...';
            INSERT INTO appconfiguration (assessmentprogramid, attrcode, attrtype, attrname, attrvalue)
            VALUES (
                (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'PLTW'),
                'scoring-api-url',
                'scoring-api-url',
                'Scoring API URL',
                ''
            );
        ELSE
            RAISE NOTICE 'Found scoring-api-url for PLTW, skipping insert';
        END IF;
        
        IF NOT EXISTS (
            SELECT 1
            FROM appconfiguration conf
            JOIN assessmentprogram ap ON conf.assessmentprogramid = ap.id AND ap.activeflag IS TRUE
            WHERE conf.activeflag IS TRUE
            AND conf.attrtype = 'scoring-api-key'
            AND ap.abbreviatedname = 'PLTW'
        ) THEN
        	RAISE NOTICE 'Did not find scoring-api-key for PLTW, inserting...';
            INSERT INTO appconfiguration (assessmentprogramid, attrcode, attrtype, attrname, attrvalue)
            VALUES (
                (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'PLTW'),
                'scoring-api-key',
                'scoring-api-key',
                'Scoring API Key',
                ''
            );
        ELSE
            RAISE NOTICE 'Found scoring-api-key for PLTW, skipping insert';
        END IF;
        
        IF NOT EXISTS (
            SELECT 1
            FROM appconfiguration conf
            JOIN assessmentprogram ap ON conf.assessmentprogramid = ap.id AND ap.activeflag IS TRUE
            WHERE conf.activeflag IS TRUE
            AND conf.attrtype = 'scoring-api-page-size'
            AND ap.abbreviatedname = 'PLTW'
        ) THEN
        	RAISE NOTICE 'Did not find scoring-api-page-size for PLTW, inserting...';
            INSERT INTO appconfiguration (assessmentprogramid, attrcode, attrtype, attrname, attrvalue)
            VALUES (
                (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'PLTW'),
                'scoring-api-page-size',
                'scoring-api-page-size',
                'Scoring API Page Size (students per request sent, 200 is default)',
                '200'
            );
        ELSE
            RAISE NOTICE 'Found scoring-api-key for PLTW, skipping insert';
        END IF;
        
        IF NOT EXISTS (
            SELECT 1
            FROM appconfiguration conf
            JOIN assessmentprogram ap ON conf.assessmentprogramid = ap.id AND ap.activeflag IS TRUE
            WHERE conf.activeflag IS TRUE
            AND conf.attrtype = 'scoring-api-validate-enrollments'
            AND ap.abbreviatedname = 'PLTW'
        ) THEN
        	RAISE NOTICE 'Did not find scoring-api-validate-enrollments for PLTW, inserting...';
            INSERT INTO appconfiguration (assessmentprogramid, attrcode, attrtype, attrname, attrvalue)
            VALUES (
                (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'PLTW'),
                'scoring-api-validate-enrollments',
                'scoring-api-validate-enrollments',
                'Scoring API Validate Enrollments (true/false, only included in request if false)',
                'true'
            );
        ELSE
            RAISE NOTICE 'Found scoring-api-validate-enrollments for PLTW, skipping insert';
        END IF;
    END IF;
END;
$BODY$;
