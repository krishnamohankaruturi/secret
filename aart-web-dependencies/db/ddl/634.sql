
--ddl/633.sql For Data team
ALTER TABLE studentassessmentprogram ALTER COLUMN createddate SET DEFAULT ('now'::text)::timestamp without time zone;
ALTER TABLE studentassessmentprogram ALTER COLUMN modifieddate SET DEFAULT ('now'::text)::timestamp without time zone;
ALTER TABLE testsectionstaskvariants ALTER COLUMN createddate SET DEFAULT ('now'::text)::timestamp without time zone;
ALTER TABLE testsectionstaskvariants ALTER COLUMN modifieddate SET DEFAULT ('now'::text)::timestamp without time zone;

--for DE15053
ALTER TABLE scoringassignment ADD COLUMN source varchar(20);
ALTER TABLE ccqscore ADD COLUMN source varchar(20);
ALTER TABLE ccqscoreitem ADD COLUMN source varchar(20);
ALTER TABLE scoringassignmentstudent ADD COLUMN kelpascoringstatus integer;
ALTER TABLE ccqscore ADD COLUMN modifieduser integer;
ALTER TABLE ccqscore ADD COLUMN modifieddate timestamp with time zone;
ALTER TABLE ccqscoreitem ADD  COLUMN modifieduser integer;
ALTER TABLE ccqscoreitem ADD COLUMN modifieddate timestamp with time zone;
ALTER TABLE ccqscoreitem ALTER COLUMN modifieddate SET DEFAULT ('now'::text)::timestamp without time zone;
ALTER TABLE ccqscore ALTER COLUMN modifieddate SET DEFAULT ('now'::text)::timestamp without time zone;
--Set trigger on ccqscore
DROP TRIGGER if exists modifieddate ON ccqscore;
CREATE TRIGGER modifieddate
  BEFORE UPDATE
  ON ccqscore 
  FOR EACH ROW
  EXECUTE PROCEDURE modifieddate();
--Set trigger on ccqscoreitem
DROP TRIGGER if exists modifieddate ON ccqscoreitem;
CREATE TRIGGER modifieddate
  BEFORE UPDATE
  ON ccqscoreitem
  FOR EACH ROW
  EXECUTE PROCEDURE modifieddate();

