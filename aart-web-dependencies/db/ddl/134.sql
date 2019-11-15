
--ALTER TABLE studentsresponses ADD COLUMN testsectionid bigint;
--ALTER TABLE studentsresponses ADD COLUMN studentstestsectionsid bigint;
--ALTER TABLE studentsresponses ADD COLUMN score numeric(5,2);
--ALTER TABLE studentsresponses ALTER COLUMN createduser DROP NOT NULL;
--ALTER TABLE studentsresponses ALTER COLUMN modifieduser DROP NOT NULL;

--ALTER TABLE studentsresponses DROP COLUMN id;


DO 
$BODY$ 
    BEGIN
        BEGIN
            ALTER TABLE studentsresponses ADD COLUMN testsectionid bigint;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column testsectionid already exists in studentsresponses.';
        END;
END;
$BODY$;

DO 
$BODY$ 
    BEGIN
        BEGIN
            ALTER TABLE studentsresponses ADD COLUMN studentstestsectionsid bigint;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column studentstestsectionsid already exists in studentsresponses.';
        END;
       END;
$BODY$;

DO 
$BODY$
BEGIN
    BEGIN
        ALTER TABLE studentsresponses ADD COLUMN score numeric(5,2);
    EXCEPTION
        WHEN duplicate_column THEN RAISE NOTICE 'column score already exists in studentsresponses.';
    END;
END;
$BODY$;
