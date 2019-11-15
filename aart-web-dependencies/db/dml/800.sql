--dml/800.sql

-- make a couple updates here because grade changes and various other un-enrollment processes use a hyphen meaningfully
update category
set categorycode = 'COMPLETED_WITH_TESTLET',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where categorycode = 'COMPLETED-WITH-TESTLET';

update category
set categorycode = 'COMPLETED_WITH_NO_TESTLET',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where categorycode = 'COMPLETED-WITH-NO-TESTLET';

DO
$BODY$
DECLARE
    sysadmin BIGINT := (SELECT id FROM aartuser WHERE username = 'cetesysadmin' LIMIT 1);
    
    cat RECORD;
    new_prefix_names TEXT[] := ARRAY[
        'Exit/Transfer Previously', 'Grade Change Inactivated', 'Unrostered Previously'
    ];
    new_prefixes TEXT[] := ARRAY[
        'exitclearunenrolled', 'gradechangeinactivated', 'rosterunenrolled'
    ];
BEGIN
    
    IF (array_upper(new_prefix_names, 1) != array_upper(new_prefixes, 1)) THEN
        RAISE NOTICE 'Array lengths don''t match. Double check your data.';
    ELSE
        FOR cat IN (
            SELECT c.*
            FROM category c
            JOIN categorytype ct ON c.categorytypeid = ct.id
            WHERE ct.typecode = 'IAP_STATUS'
            AND c.categorycode IN ('STARTED', 'COMPLETED_WITH_NO_TESTLET', 'COMPLETED_WITH_TESTLET', 'CANCELED')
        ) LOOP
            FOR i IN array_lower(new_prefixes, 1) .. array_upper(new_prefixes, 1) LOOP
                IF NOT EXISTS (
                    SELECT id
                    FROM category
                    WHERE categorycode = new_prefixes[i] || '-' || cat.categorycode
                    AND categorytypeid = cat.categorytypeid
                ) THEN
                    RAISE NOTICE 'Inserting record for % (%)', (new_prefix_names[i] || ' ' || cat.categoryname), (new_prefixes[i] || '-' || cat.categorycode);
                    
                    INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createduser, modifieduser)
                    VALUES (
                        new_prefix_names[i] || ' ' || cat.categoryname,
                        new_prefixes[i] || '-' || cat.categorycode,
                        cat.categorydescription,
                        cat.categorytypeid,
                        'EP',
                        sysadmin,
                        sysadmin
                    );
                ELSE
                    RAISE NOTICE 'Found existing record for %, skipping...', (new_prefixes[i] || '-' || cat.categorycode);
                END IF;
            END LOOP;
        END LOOP;
    END IF;
    
END;
$BODY$;
