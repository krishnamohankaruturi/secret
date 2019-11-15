--dml/687.sql

DO
$BODY$
DECLARE
    now_gmt TIMESTAMP WITH TIME ZONE;
    cetesysadminid BIGINT;
BEGIN
    SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
    SELECT NOW() INTO now_gmt;
    
    RAISE NOTICE 'Updating category table to update MDPT link on left of reports page...';
    UPDATE category
    SET categoryname = 'Student Writing Responses',
    categorycode = 'GEN_ST_WRITING',
    modifieddate = now_gmt,
    modifieduser = cetesysadminid
    WHERE categorycode = 'GEN_ST_MDPT';
    
    RAISE NOTICE 'Updating authorities table to change permission code...';
    UPDATE authorities
    SET authority = 'VIEW_GENERAL_STUDENT_WRITING',
    modifieddate = now_gmt,
    modifieduser = cetesysadminid
    WHERE authority = 'VIEW_GENERAL_STUDENT_MDPT_RESP';
END;
$BODY$;