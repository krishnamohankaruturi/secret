--ddl/724.sql

DROP FUNCTION IF EXISTS get_column_references(TEXT, TEXT, ANYELEMENT);

CREATE OR REPLACE FUNCTION get_column_references(
    referenced_table_name TEXT DEFAULT NULL,
    referenced_column_name TEXT DEFAULT NULL,
    referenced_value ANYELEMENT DEFAULT NULL::TEXT)
RETURNS TABLE ("table" TEXT, "column" TEXT, "row_count_of_value" BIGINT)
AS $BODY$
DECLARE
    ref RECORD;
BEGIN
    IF referenced_table_name IS NULL OR referenced_column_name IS NULL THEN
        RAISE NOTICE 'Usage: get_column_references(table, column[, value])';
        RAISE NOTICE 'Note: this function will return all references to the given table and column. If the value is specified, it will also return the number of rows that have that value referenced.';
        RETURN QUERY EXECUTE 'SELECT NULL::TEXT, NULL::TEXT, NULL::BIGINT;';
    END IF;
    
	FOR ref IN (
        WITH refs AS (
            SELECT
                conrelid::regclass::TEXT AS table,
                substring(pg_get_constraintdef(c.oid), 'FOREIGN KEY \(([^\)]*)\)') AS column,
                substring(pg_get_constraintdef(c.oid), 'REFERENCES ([^\(]*)') AS referenced_table,
                substring(pg_get_constraintdef(c.oid), 'REFERENCES .*\(([^)]*)\)') AS referenced_column
            FROM pg_constraint c
            JOIN pg_namespace n ON n.oid = c.connamespace
            WHERE contype = 'f' -- foreign
        )
        SELECT r.*
        FROM refs r
        WHERE r.referenced_table = referenced_table_name
        AND r.referenced_column = referenced_column_name
        ORDER BY r.table, r.column, r.referenced_table, r.referenced_column
    ) LOOP
        IF referenced_value IS DISTINCT FROM NULL THEN
            RAISE NOTICE 'attempting to find references in table: %, column: %...', ref.table, ref.column;
            RETURN QUERY EXECUTE
                FORMAT(
                    'SELECT %1$L::TEXT AS table, %2$L::TEXT AS column, (SELECT COUNT(*) FROM %1$I WHERE %2$I IS NOT DISTINCT FROM %3$L) AS row_count_of_value;',
                    ref.table, ref.column, referenced_value
                );
        ELSE
            RETURN QUERY EXECUTE FORMAT('SELECT %L::TEXT AS table, %L::TEXT AS column, NULL::BIGINT AS row_count_of_value;', ref.table, ref.column);
        END IF;
    END LOOP;
END;
$BODY$
LANGUAGE PLPGSQL VOLATILE
COST 100;

--COMMENT ON FUNCTION get_column_references(TEXT, TEXT, ANYELEMENT) IS 'Utility function to find references to a specific column (or optionally, a specific column''s value). The third argument is optional and will query other tables for that value. Performance may vary.';
