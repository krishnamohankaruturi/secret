--dml/726.sql

DO
$BODY$
DECLARE
    newContentArea TEXT := 'SS';
    newContentAreaId BIGINT := (SELECT id FROM contentarea WHERE abbreviatedname = newContentArea);
    
    oldContentArea TEXT := 'Sci';
    oldContentAreaId BIGINT := (SELECT id FROM contentarea WHERE abbreviatedname = oldContentArea);
BEGIN
    IF EXISTS (
        SELECT 1
        FROM complexityband
        WHERE contentareaid = newContentAreaId
    ) THEN
        RAISE NOTICE 'Found % complexity bands already. Skipping...', newContentArea;
    ELSE
        RAISE NOTICE 'Did not find % complexity bands. Copying from % to %...', newContentArea, oldContentArea, newContentArea;
        
        --id is not auto-generated for this table, so just adjust by 10
        INSERT INTO complexityband (id, bandname, bandcode, minrange, maxrange, contentareaid)
            SELECT id + 10 AS id, bandname, bandcode, minrange, maxrange, newContentAreaId
            FROM complexityband
            WHERE contentareaid = oldContentAreaId;
        
        RAISE NOTICE 'Complete.';
    END IF;
END;
$BODY$;