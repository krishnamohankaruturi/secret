-- 462.sql ddl

 
DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='organizationreportdetails' and column_name='summaryreportpath') THEN
		raise NOTICE 'summaryreportpath found';
	else
		alter table organizationreportdetails add column summaryreportpath text;
	END IF;
END
$BODY$;

ALTER TABLE enrollmenttesttypesubjectarea DROP CONSTRAINT IF EXISTS uk_enrollmenttesttypesubjectarea_ids;