-- ddl/765.sql
--Requested by CB team for F833
ALTER TABLE itemstatistic ADD COLUMN IF NOT EXISTS year integer;