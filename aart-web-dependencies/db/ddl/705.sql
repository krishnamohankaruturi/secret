--ddl/705.sql

-- remove old functions
DROP FUNCTION IF EXISTS generate_student_password();
DROP FUNCTION IF EXISTS generate_student_password(BIGINT);
DROP FUNCTION IF EXISTS get_random_number(INTEGER, INTEGER);

CREATE OR REPLACE FUNCTION get_random_number_between(start_int BIGINT, end_int BIGINT) RETURNS BIGINT AS
$BODY$
BEGIN
    -- start_int <= x < end_int, where x is the returned number
    RETURN TRUNC(RANDOM() * (end_int - start_int) + start_int);
END;
$BODY$
LANGUAGE plpgsql VOLATILE STRICT
COST 100;


CREATE OR REPLACE FUNCTION generate_student_password(password_length INTEGER) RETURNS TEXT AS
$BODY$
DECLARE
    password TEXT;
    chars_needed INTEGER;
    random_number BIGINT;
BEGIN
    SELECT word
    FROM studentpassword
    WHERE CHAR_LENGTH(word) <= password_length
    AND word !~* 'i|l|o|jan|feb|mar|apr|may|jun|june|jul|july|aug|sep|sept|oct|nov|dec'
    ORDER BY RANDOM()
    LIMIT 1
    INTO password;
    
    SELECT COALESCE(password, '') INTO password;
    SELECT password_length - CHAR_LENGTH(password) INTO chars_needed;
    
    IF chars_needed > 0 THEN
        FOR i IN 1..chars_needed LOOP
            SELECT get_random_number_between(2, 10) INTO random_number; -- 1 is not a valid value, so 2-9
            SELECT CONCAT(password, random_number) INTO password;
        END LOOP;
    END IF;
    
    RETURN UPPER(password);
END;
$BODY$
LANGUAGE plpgsql VOLATILE STRICT
COST 100;
