--ddl/600.sql
ALTER TABLE category ALTER COLUMN categorydescription TYPE character varying(200);

ALTER TABLE ccqscore ADD COLUMN status bigint;

-- US19247: ITI Blueprint Coverage - load criteria descriptions
-- As we are going to manually create table and loading the data manually to table using csv in all environments going to checkin ddl and dml into old versions. As of now 600.sql is already executed in prod. So checking into it.
DROP TABLE IF EXISTS blueprintcriteriadescription;
DROP SEQUENCE IF EXISTS blueprintcriteriadescription_id_seq;

CREATE TABLE blueprintcriteriadescription(id SERIAL, contentareaabbrname CHARACTER VARYING(10), contentareaid BIGINT, gradecourseabbrname CHARACTER VARYING(10), gradecourseid BIGINT, criteria BIGINT, criteriatext text);

