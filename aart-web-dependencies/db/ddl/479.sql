--479.sql
 DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='operationaltestwindow' and column_name='autoenrollmentflag') THEN
		raise NOTICE 'autoenrollmentflag found in operationaltestwindow';
	else
		ALTER TABLE operationaltestwindow ADD COLUMN autoenrollmentflag boolean;
END IF;
END
$BODY$;

  
