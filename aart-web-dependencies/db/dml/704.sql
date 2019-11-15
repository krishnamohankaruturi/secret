--dml/704.sql

DO
$BODY$
DECLARE
    -- states and their timezones
    states TEXT[][] := ARRAY[
        ['Alaska', 'US/Alaska'],
        ['Colorado', 'US/Mountain'],
        ['Delaware', 'US/Eastern'],
        ['Illinois', 'US/Central'],
        ['Iowa', 'US/Central'],
        ['Maryland', 'US/Eastern'],
        ['Michigan', 'US/Eastern'],
        ['Mississippi', 'US/Central'],
        ['Missouri', 'US/Central'],
        ['New Hampshire', 'US/Eastern'],
        ['New Jersey', 'US/Eastern'],
        ['New York', 'US/Eastern'],
        ['North Carolina', 'US/Eastern'],
        ['Oklahoma', 'US/Central'],
        ['Pennsylvania', 'US/Eastern'],
        ['Rhode Island', 'US/Eastern'],
        ['Utah', 'US/Mountain'],
        ['Vermont', 'US/Eastern'],
        ['Virginia', 'US/Eastern'],
        ['West Virginia', 'US/Eastern'],
        ['Wisconsin', 'US/Central']
    ];
    
    -- this is mostly just for reference, since individual schools and districts will need their own settings
    -- at the time of writing this script, Michigan had no child organizations
    special_states TEXT[][] := ARRAY[
        ['Michigan'], -- central and eastern
        ['North Dakota'] -- central and mountain
    ];
    
    cnt INTEGER := 0;
BEGIN
    IF array_length(states, 1) > 0 THEN
        FOR i IN 1 .. array_length(states, 1) LOOP
            WITH updated_rows AS (
                UPDATE organization
                SET timezoneid = (
                    SELECT c.id
                    FROM category c
                    JOIN categorytype ct ON c.categorytypeid = ct.id
                    WHERE ct.typecode = 'TIMEZONE'
                    AND c.categorycode = states[i][2]
                    LIMIT 1
                )
                WHERE id IN (
                    SELECT schoolid FROM organizationtreedetail WHERE statename = states[i][1]
                    UNION
                    SELECT districtid FROM organizationtreedetail WHERE statename = states[i][1]
                    UNION
                    SELECT id FROM organization WHERE organizationname = states[i][1]
                )
                RETURNING id
            )
            SELECT count(*) FROM updated_rows INTO cnt;
            
            RAISE NOTICE 'Updating % to % -- updated % rows.', states[i][1], states[i][2], cnt;
        END LOOP;
    END IF;
    
    --------------------- North Dakota
    -- default to Central
    UPDATE organization
    SET timezoneid = (
        SELECT c.id
        FROM category c
        JOIN categorytype ct ON c.categorytypeid = ct.id
        WHERE ct.typecode = 'TIMEZONE'
        AND c.categorycode = 'US/Central'
        LIMIT 1
    )
    WHERE id IN (
        SELECT schoolid FROM organizationtreedetail WHERE statename = 'North Dakota'
        UNION
        SELECT districtid FROM organizationtreedetail WHERE statename = 'North Dakota'
        UNION
        SELECT id FROM organization WHERE organizationname = 'North Dakota'
    );
    
    -- update a handful to Mountain
    UPDATE organization
    SET timezoneid = (
        SELECT c.id
        FROM category c
        JOIN categorytype ct ON c.categorytypeid = ct.id
        WHERE ct.typecode = 'TIMEZONE'
        AND c.categorycode = 'US/Mountain'
        LIMIT 1
    )
    WHERE id IN (
        SELECT schoolid
        FROM organizationtreedetail
        WHERE statename = 'North Dakota'
        AND districtname IN ('ADAMS 128', 'BILLINGS CO 1', 'BOWMAN CO 1', 'DICKINSON 1', 'DICKINSON SPECIAL ED UNIT', 'HETTINGER 13', 'MCKENZIE CO 1')
        UNION
        SELECT districtid
        FROM organizationtreedetail
        WHERE statename = 'North Dakota'
        AND districtname IN ('ADAMS 128', 'BILLINGS CO 1', 'BOWMAN CO 1', 'DICKINSON 1', 'DICKINSON SPECIAL ED UNIT', 'HETTINGER 13', 'MCKENZIE CO 1')
    );
    ------------------------------------------
END;
$BODY$;

