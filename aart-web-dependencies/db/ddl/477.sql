-- 477.sql
-- US16972 : Data extract - KSDE TASC records for System Operations
-- permissions for the data extracts(in dml)

--US16944: Test Management and Test Coordination - remove access to test sessions from prior school year

ALTER TABLE testsession DROP column IF EXISTS schoolyear;
ALTER TABLE testsession ADD column schoolyear bigint;

DROP INDEX IF EXISTS idx_testsession_schoolyear;
CREATE INDEX idx_testsession_schoolyear ON testsession USING btree (schoolyear);