--423.sql



DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='studentreport' and column_name='exitstatus') THEN
		raise NOTICE 'exitstatus found';
	else
		alter table studentreport add column exitstatus boolean;
	END IF;
END
$BODY$;

 DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='studentreport' and column_name='incompletestatus') THEN
		raise NOTICE 'incompletestatus found';
	else
		alter table studentreport add column incompletestatus boolean;
	END IF;
END
$BODY$;

alter table studentreport drop column status;
alter table studentreport add column status boolean;