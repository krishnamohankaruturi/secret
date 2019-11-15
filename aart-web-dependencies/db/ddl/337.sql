-- 337.sql

ALTER TABLE testsession DROP CONSTRAINT source_testsession_fk;

alter table testsession alter column source type character varying(10);

DROP INDEX IF EXISTS idx_testsession_source;
CREATE INDEX idx_testsession_source ON testsession USING btree (source);
