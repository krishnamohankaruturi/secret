-- Adding audit columns on studentassessmentprogram
DO
$BODY$
BEGIN
IF not EXISTS (SELECT column_name 
               FROM information_schema.columns 
               WHERE table_name='studentassessmentprogram' and column_name='createddate') THEN
alter table studentassessmentprogram ADD COLUMN createddate timestamp without time zone;
else
raise NOTICE 'Already exists column createddate on table studentassessmentprogram';
END IF;
IF not EXISTS (SELECT column_name 
               FROM information_schema.columns 
               WHERE table_name='studentassessmentprogram' and column_name='createduser') THEN
alter table studentassessmentprogram ADD COLUMN createduser integer;
else
raise NOTICE 'Already exists column createduser on table studentassessmentprogram';
END IF;
IF not EXISTS (SELECT column_name 
               FROM information_schema.columns 
               WHERE table_name='studentassessmentprogram' and column_name='modifieddate') THEN
alter table studentassessmentprogram ADD COLUMN modifieddate timestamp without time zone;
else
raise NOTICE 'Already exists column modifieddate on table studentassessmentprogram';
END IF;
IF not EXISTS (SELECT column_name 
               FROM information_schema.columns 
               WHERE table_name='studentassessmentprogram' and column_name='modifieduser') THEN
alter table studentassessmentprogram ADD COLUMN modifieduser integer;
else
raise NOTICE 'Already exists column modifieduser on table studentassessmentprogram';
END IF;
END;
$BODY$;
-- Adding audit columns on testsectionstaskvariants
DO
$BODY$
BEGIN
IF not EXISTS (SELECT column_name 
               FROM information_schema.columns 
               WHERE table_name='testsectionstaskvariants' and column_name='createddate') THEN
alter table testsectionstaskvariants ADD COLUMN createddate timestamp without time zone;
else
raise NOTICE 'Already exists column createddate on table testsectionstaskvariants';
END IF;
IF not EXISTS (SELECT column_name 
               FROM information_schema.columns 
               WHERE table_name='testsectionstaskvariants' and column_name='createduser') THEN
alter table testsectionstaskvariants ADD COLUMN createduser integer;
else
raise NOTICE 'Already exists column createduser on table testsectionstaskvariants';
END IF;
IF not EXISTS (SELECT column_name 
               FROM information_schema.columns 
               WHERE table_name='testsectionstaskvariants' and column_name='modifieddate') THEN
alter table testsectionstaskvariants ADD COLUMN modifieddate timestamp without time zone;
else
raise NOTICE 'Already exists column modifieddate on table testsectionstaskvariants';
END IF;
IF not EXISTS (SELECT column_name 
               FROM information_schema.columns 
               WHERE table_name='testsectionstaskvariants' and column_name='modifieduser') THEN
alter table testsectionstaskvariants ADD COLUMN modifieduser integer;
else
raise NOTICE 'Already exists column modifieduser on table testsectionstaskvariants';
END IF;
END;
$BODY$;
-- Creating trigger for modifieddate set to now
CREATE OR REPLACE FUNCTION modifieddate()
 RETURNS trigger AS
 $BODY$
BEGIN
  NEW.modifieddate := NOW();
  RETURN NEW;
END;
 $BODY$
 LANGUAGE plpgsql VOLATILE
COST 100;
ALTER FUNCTION modifieddate() OWNER TO aart;
GRANT EXECUTE ON FUNCTION modifieddate() TO aart;
GRANT EXECUTE ON FUNCTION modifieddate() TO public;
--Set trigger on studentassessmentprogram
DROP TRIGGER if exists modifieddate ON studentassessmentprogram;
CREATE TRIGGER modifieddate
  BEFORE UPDATE
  ON studentassessmentprogram 
  FOR EACH ROW
  EXECUTE PROCEDURE modifieddate();
--Set trigger on testsectionstaskvariants
DROP TRIGGER if exists modifieddate ON testsectionstaskvariants;
CREATE TRIGGER modifieddate
  BEFORE UPDATE
  ON testsectionstaskvariants
  FOR EACH ROW
  EXECUTE PROCEDURE modifieddate();