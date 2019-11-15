-- 412. sql
DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='studentreport' and column_name='grade') THEN
		alter table studentreport rename  grade to gradeid;
	else
		raise NOTICE 'grade not exists';
	END IF;
END
$BODY$;

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='studentreport' and column_name='contentarea') THEN
		alter table studentreport rename contentarea to contentareaid;
	else
		raise NOTICE 'contentarea not exists';
	END IF;
END
$BODY$;



DO 
$BODY$
    BEGIN
        BEGIN
		alter table studentreport add column schoolyear bigint;

        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'schoolyear already exists in studentreport.';
        END;       
    END;
$BODY$;


DO 
$BODY$
    BEGIN
        BEGIN
			alter table studentreport add column filepath text;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'filepath already exists in studentreport.';
        END;       
    END;
$BODY$;

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='reportspercentbylevel' and column_name='level') THEN
		alter table reportspercentbylevel rename  level to levelid;
	else
		raise NOTICE 'level not exists';
	END IF;
END
$BODY$;

