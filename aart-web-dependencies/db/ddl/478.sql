-- ddl/478.sql 
DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='studenttrackerband' and column_name='operationalwindowid') THEN
		raise NOTICE 'operationalwindowid found in studenttrackerband';
	else
		alter table studenttrackerband add column operationalwindowid BIGINT;
	END IF;
END
$BODY$;

 DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='studenttracker' and column_name='schoolyear') THEN
		raise NOTICE 'schoolyear found in studenttracker';
	else
		alter table studenttracker add column schoolyear bigint;
	END IF;
END
$BODY$;
 
 
 DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='studenttracker' and column_name='courseid') THEN
		raise NOTICE 'courseid found in studenttracker';
	else
		alter table studenttracker add column courseid bigint;
	END IF;
END
$BODY$;
