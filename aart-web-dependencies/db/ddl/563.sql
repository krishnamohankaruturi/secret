--ddl/563.sql - US18817 : adding leveltype column to accommodate Main, MDPT and Combinedlevel in same table

ALTER TABLE reportspercentbylevel ADD COLUMN leveltype character varying(20);

DROP INDEX IF EXISTS idx_reportspercentbylevel_leveltype;

CREATE INDEX idx_reportspercentbylevel_leveltype
  ON reportspercentbylevel
  USING btree
  (leveltype COLLATE pg_catalog."default");
  