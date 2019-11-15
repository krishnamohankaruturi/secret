-- 416. sql


DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='studentreport' and column_name='assessmentprogram') THEN
		alter table studentreport rename assessmentprogram to assessmentprogramid;
	else
		raise NOTICE 'grade not exists';
	END IF;
END
$BODY$;