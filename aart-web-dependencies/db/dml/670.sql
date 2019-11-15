-- dml/670.sql

-- F561 - KAP predictive job
INSERT INTO batchjobschedule (jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
VALUES ('KAP Predictive Scheduler', 'kapPredictiveRunScheduler', 'run', '0 5/10 * * * ?', TRUE, 'epbp2.prodku.cete.us');

INSERT INTO testenrollmentmethod (assessmentprogramid, methodcode, methodname, methodtype)
VALUES ((SELECT id FROM assessmentprogram WHERE abbreviatedname = 'KAP'), 'PREDICTIVE', 'Predictive', 'TEM');

DO
$BODY$
DECLARE
    now_gmt TIMESTAMP WITH TIME ZONE;
    cetesysadminid BIGINT;
    
    data TEXT[][] := ARRAY[
        ['F', '2', 'M', '3'],
        ['F', '2', 'M', '4'],
        ['F', '2', 'M', '5'],
        ['F', '2', 'M', '6'],
        ['F', '2', 'M', '7'],
        ['F', '2', 'M', '8'],
        ['F', '2', 'M', '10'],
        ['F', '2', 'ELA', '3'],
        ['F', '2', 'ELA', '4'],
        ['F', '2', 'ELA', '5'],
        ['F', '2', 'ELA', '6'],
        ['F', '2', 'ELA', '7'],
        ['F', '2', 'ELA', '8'],
        ['F', '2', 'ELA', '10']
    ];
BEGIN
    SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
    SELECT NOW() AT TIME ZONE 'GMT' INTO now_gmt;
    
    IF array_length(data, 1) > 0 THEN
        FOR i IN 1 .. array_length(data, 1) LOOP
            IF NOT EXISTS (
                SELECT 1
                FROM gradecontentareatesttypesubjectarea gcattsa
                JOIN contentareatesttypesubjectarea cattsa ON gcattsa.contentareatesttypesubjectareaid = cattsa.id
                JOIN testtypesubjectarea ttsa on cattsa.testtypesubjectareaid = ttsa.id
                JOIN testtype tt on tt.id = ttsa.testtypeid
                JOIN assessment a on ttsa.assessmentid = a.id
                JOIN testingprogram tp on a.testingprogramid = tp.id
                JOIN contentarea ca on ca.id = cattsa.contentareaid
                JOIN gradecourse gc on ca.id = gc.contentareaid AND gcattsa.gradecourseid = gc.id
                WHERE tp.programabbr = data[i][1]
                AND tt.testtypecode = data[i][2]
                AND ca.abbreviatedname = data[i][3]
                AND gc.abbreviatedname = data[i][4]
            ) THEN
                INSERT INTO gradecontentareatesttypesubjectarea (contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
                    SELECT DISTINCT cattsa.id, gc.id, cetesysadminid, now_gmt, now_gmt, cetesysadminid
                    FROM contentareatesttypesubjectarea cattsa
                    JOIN testtypesubjectarea ttsa on cattsa.testtypesubjectareaid = ttsa.id
                    JOIN testtype tt on tt.id = ttsa.testtypeid
                    JOIN assessment a on ttsa.assessmentid = a.id
                    JOIN testingprogram tp on a.testingprogramid = tp.id
                    JOIN contentarea ca on ca.id = cattsa.contentareaid
                    JOIN gradecourse gc on ca.id = gc.contentareaid
                    WHERE tp.programabbr = data[i][1]
                    AND tt.testtypecode = data[i][2]
                    AND ca.abbreviatedname = data[i][3]
                    AND gc.abbreviatedname = data[i][4];
            ELSE
                RAISE NOTICE 'Found data corresponding to {testingprogram=%, testtypecode=%, contentarea=%, grade=%}', data[i][1], data[i][2], data[i][3], data[i][4];
            END IF;
        END LOOP;
    END IF;
END;
$BODY$;