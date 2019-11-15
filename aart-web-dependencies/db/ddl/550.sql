--ddl/550.sql

ALTER TABLE taskvariant ADD COLUMN previousid character varying(20);
ALTER TABLE taskvariant ADD COLUMN  previoussource character varying(75);
