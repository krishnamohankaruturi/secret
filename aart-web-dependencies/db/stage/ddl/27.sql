--ddl/27.sql

ALTER TABLE kids_record_staging DROP COLUMN IF EXISTS status ;
ALTER TABLE kids_record_staging ADD COLUMN status character varying(20);
  