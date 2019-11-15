--ddl/25.sql

ALTER TABLE kids_record_staging DROP COLUMN IF EXISTS notes;
ALTER TABLE tasc_record_staging DROP COLUMN IF EXISTS notes;

ALTER TABLE kids_record_staging ADD COLUMN notes text;
ALTER TABLE tasc_record_staging ADD COLUMN notes text;

