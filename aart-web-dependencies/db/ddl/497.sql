--ddl/497.sql

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='testsession' and column_name='subjectareaid') THEN
		raise NOTICE 'subjectareaid found in testsession';
	else
		ALTER TABLE testsession ADD COLUMN subjectareaid bigint;
END IF;
END
$BODY$;

--change from change pond 
ALTER TABLE IF EXISTS operationaltestwindowsstate RENAME TO operationaltestwindowstate;
ALTER TABLE operationaltestwindowstate
    RENAME CONSTRAINT ooperationaltestwindowsstate_pkey TO operationaltestwindowstate_pkey;
ALTER TABLE operationaltestwindowstate
    RENAME CONSTRAINT ooperationaltestwindowsstate_operationaltestwindowid_ TO operationaltestwindowstate_operationaltestwindowid;
ALTER TABLE operationaltestwindowstate ADD COLUMN activeflag boolean;
ALTER TABLE operationaltestwindowstate ALTER COLUMN activeflag SET DEFAULT true;