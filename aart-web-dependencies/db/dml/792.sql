--dml/792.sql

DO
$BODY$
DECLARE
    sysadmin BIGINT := (SELECT id FROM aartuser WHERE username = 'cetesysadmin' LIMIT 1);
    assessmentProg BIGINT := (SELECT id FROM assessmentprogram WHERE programname = 'PLTW');
BEGIN
    IF NOT EXISTS (SELECT id FROM appconfiguration WHERE attrcode = 'current_school_year') THEN
        INSERT INTO appconfiguration (attrcode, attrtype, attrname, attrvalue, activeflag,createduser,modifieduser,assessmentprogramid) 
        VALUES ('current_school_year', 'current_school_year', 'current_school_year', '2019', true, sysadmin, sysadmin, assessmentProg);  
    END IF;
END;
$BODY$;

---end 792.sql
 
