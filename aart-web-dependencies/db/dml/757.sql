-- dml/757.sql
-- adds api allowed roles to appconfiguration table

DO
$BODY$
BEGIN
     IF NOT EXISTS (
    	SELECT 1
    	FROM appconfiguration conf
    	JOIN assessmentprogram ap ON conf.assessmentprogramid = ap.id AND ap.activeflag IS TRUE
        WHERE conf.activeflag IS TRUE
        AND conf.attrtype = 'api-allowed-groupcodes'
        AND ap.abbreviatedname = 'PLTW'
    ) THEN
    	RAISE NOTICE 'Did not find api-check-hashes for PLTW, inserting...';
    	
	    INSERT INTO appconfiguration (assessmentprogramid, attrcode, attrtype, attrname, attrvalue)
        VALUES (
            (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'PLTW'),
            'api-allowed-groupcodes',
            'api-allowed-groupcodes',
            'Specifies which groupcodes an assessment program is allowed to assign to users through the api',
            'TEA,BTC,DTC,PGAD,SAAD'
        );
    ELSE
        RAISE NOTICE 'Found api-allowed-groupcodes for PLTW, skipping insert';
    END IF;
    
    END;
$BODY$;
