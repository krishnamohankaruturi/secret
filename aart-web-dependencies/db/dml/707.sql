--dml/707.sql

DELETE FROM studentpassword WHERE word = 'shat';

DO
$BODY$
DECLARE
    _now TIMESTAMP WITH TIME ZONE;
    _ceteid BIGINT;
    
    updated_count BIGINT;
BEGIN
    SELECT now() INTO _now;
    SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO _ceteid;
    
    WITH updated_rows AS (
        UPDATE student
        SET password = generate_student_password((
            SELECT c.categoryname::INTEGER
            FROM category c
            JOIN categorytype ct ON c.categorytypeid = ct.id
            WHERE ct.typecode = 'STUDENT_PASSWORD'
            AND c.categorycode = 'STUDENT_PASSWORD_LENGTH'
        )),
        modifieddate = _now,
        modifieduser = _ceteid
        WHERE password ~* 'shat'
        RETURNING id
    )
    SELECT COUNT(DISTINCT id) FROM updated_rows INTO updated_count;
    
    RAISE NOTICE 'Updated % passwords', updated_count;
END;
$BODY$;

--from 36.prodfix 708.sql

--remove exclusions
delete from groupauthoritiesexclusion where assessmentprogramid in(Select id from assessmentprogram where abbreviatedname in('KAP', 'KELPA2') and activeflag is true) and 
authorityid in(select id from authorities where authority ilike ('VIEW_GENERAL_STUDENT_WRITING') and activeflag is true limit 1);

--Add student writing score responses access
select * from reportassessmentprogram_fn('KELPA2', 'GEN_ST_WRITING', '', 'VIEW_GENERAL_STUDENT_WRITING');
update  reportassessmentprogram set readytoview = true where reporttypeid=(Select id from category where categorycode in ('GEN_ST_WRITING') and activeflag is true)
and assessmentprogramid = (Select id from assessmentprogram where abbreviatedname ilike ('KELPA2') and activeflag is true)
and authorityid = (select id from authorities where authority ilike ('VIEW_GENERAL_STUDENT_WRITING') and activeflag is true limit 1);
