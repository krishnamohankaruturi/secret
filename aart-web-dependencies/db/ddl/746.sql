--ddl/746.sql

DO
$BODY$
DECLARE
    tablenames TEXT[] := ARRAY[
        'fieldspecification', 'fieldspecificationsrecordtypes', 'fieldspecificationsrecordtypes', 'fieldspecificationsrecordtypes' 
    ]::TEXT[];
    columnnames TEXT[] := ARRAY[
        'regexmodeflags', 'required', 'jsondata', 'sortorder'
    ]::TEXT[];
    columntypes TEXT[] := ARRAY[
        'TEXT', 'BOOLEAN', 'JSONB', 'INTEGER'
    ]::TEXT[];
    columnadditionals TEXT[] := ARRAY[
        '', 'NOT NULL DEFAULT TRUE', '', ''
    ]::TEXT[];
    
    stmt TEXT := '';
BEGIN
    IF NOT (
        array_length(tablenames, 1) = array_length(columnnames, 1) AND
        array_length(tablenames, 1) = array_length(columntypes, 1) AND
        array_length(tablenames, 1) = array_length(columnadditionals, 1)
    ) THEN
        RAISE EXCEPTION 'Array lengths do not all match.' USING HINT = 'Make sure all of your data is accounted for.';
    END IF;
    
    FOR i IN 1 .. array_length(tablenames, 1) LOOP
        IF NOT EXISTS (
            SELECT column_name
            FROM information_schema.columns
            WHERE table_name = tablenames[i]
            AND column_name = columnnames[i]
        ) THEN
        	SELECT 'ALTER TABLE ' || tablenames[i] || ' ADD COLUMN ' || columnnames[i] || ' ' || columntypes[i] || ' ' || columnadditionals[i] INTO stmt;
            RAISE NOTICE 'Did not find column "%.%", executing %', tablenames[i], columnnames[i], stmt;
            EXECUTE stmt;
        ELSE
            RAISE NOTICE 'Found column "%.%", skipping ALTER TABLE...', tablenames[i], columnnames[i];
        END IF;
    END LOOP;
END;
$BODY$;


DROP FUNCTION IF EXISTS upsertstudentpnpoption(
    _studentid BIGINT,
    _containername TEXT,
    _attributename TEXT,
    _value TEXT,
    _userid BIGINT
);
CREATE OR REPLACE FUNCTION upsertstudentpnpoption(
    _studentid BIGINT,
    _containername TEXT,
    _attributename TEXT,
    _value TEXT,
    _userid BIGINT
)
RETURNS BIGINT AS $BODY$
BEGIN
    IF EXISTS (
        SELECT spiav.id
        FROM profileitemattributecontainer piac
        JOIN profileitemattributenameattributecontainer pianac ON piac.id = pianac.attributecontainerid AND pianac.activeflag IS TRUE
        JOIN profileitemattribute pia ON pianac.attributenameid = pia.id AND pia.activeflag IS TRUE
        JOIN studentprofileitemattributevalue spiav ON pianac.id = spiav.profileitemattributenameattributecontainerid
        WHERE piac.activeflag IS TRUE
        AND piac.attributecontainer = _containername
        AND pia.attributename = _attributename
        AND spiav.studentid = _studentid
    ) THEN
        UPDATE studentprofileitemattributevalue
        SET activeflag = FALSE,
        modifieddate = now(),
        modifieduser = _userid,
        selectedvalue = _value
        WHERE id IN (
            SELECT spiav.id
            FROM profileitemattributecontainer piac
            JOIN profileitemattributenameattributecontainer pianac ON piac.id = pianac.attributecontainerid AND pianac.activeflag IS TRUE
            JOIN profileitemattribute pia ON pianac.attributenameid = pia.id AND pia.activeflag IS TRUE
            JOIN studentprofileitemattributevalue spiav ON pianac.id = spiav.profileitemattributenameattributecontainerid
            WHERE piac.activeflag IS TRUE
            AND piac.attributecontainer = _containername
            AND pia.attributename = _attributename
            AND spiav.studentid = _studentid
        );
        
        UPDATE studentprofileitemattributevalue
        SET activeflag = TRUE,
        modifieddate = now(),
        modifieduser = _userid,
        selectedvalue = _value
        WHERE id IN (
            SELECT spiav.id
            FROM profileitemattributecontainer piac
            JOIN profileitemattributenameattributecontainer pianac ON piac.id = pianac.attributecontainerid AND pianac.activeflag IS TRUE
            JOIN profileitemattribute pia ON pianac.attributenameid = pia.id AND pia.activeflag IS TRUE
            JOIN studentprofileitemattributevalue spiav ON pianac.id = spiav.profileitemattributenameattributecontainerid
            WHERE piac.activeflag IS TRUE
            AND piac.attributecontainer = _containername
            AND pia.attributename = _attributename
            AND spiav.studentid = _studentid
            ORDER BY spiav.modifieddate DESC
            LIMIT 1
        );
    ELSE
        INSERT INTO studentprofileitemattributevalue (profileitemattributenameattributecontainerid, studentid, selectedvalue, createduser, modifieduser)
        VALUES (
            (
                SELECT pianac.id
                FROM profileitemattributecontainer piac
                JOIN profileitemattributenameattributecontainer pianac ON piac.id = pianac.attributecontainerid AND pianac.activeflag IS TRUE
                JOIN profileitemattribute pia ON pianac.attributenameid = pia.id AND pia.activeflag IS TRUE
                WHERE piac.activeflag IS TRUE
                AND piac.attributecontainer = _containername
                AND pia.attributename = _attributename
            ),
            _studentid,
            _value,
            _userid,
            _userid
        );
    END IF;
    
    RETURN 1;
END;
$BODY$
LANGUAGE PLPGSQL VOLATILE
COST 10;
