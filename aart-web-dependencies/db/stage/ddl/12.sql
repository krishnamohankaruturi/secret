--ddl/12.sql	
DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='ksdexmlaudit' and column_name='successcount') THEN
		raise NOTICE 'successcount found in ksdexmlaudit';
	else
		ALTER TABLE ksdexmlaudit ADD COLUMN successcount integer;
END IF;
END
$BODY$;

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='ksdexmlaudit' and column_name='failedcount') THEN
		raise NOTICE 'failedcount found in ksdexmlaudit';
	else
		ALTER TABLE ksdexmlaudit ADD COLUMN failedcount integer;
END IF;
END
$BODY$;