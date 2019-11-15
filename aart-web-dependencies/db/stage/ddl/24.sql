--ddl/24.sql

ALTER TABLE kids_record_staging DROP COLUMN IF EXISTS emailtemplateid;
ALTER TABLE tasc_record_staging DROP COLUMN IF EXISTS emailtemplateid;

ALTER TABLE kids_record_staging ADD COLUMN emailtemplateids character varying(30);
ALTER TABLE tasc_record_staging ADD COLUMN emailtemplateids character varying(30);

