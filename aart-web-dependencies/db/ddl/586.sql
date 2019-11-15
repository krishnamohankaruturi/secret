--ddl/*.sql ==> For dml/586.sql

ALTER TABLE enrollmenttesttypesubjectarea ADD COLUMN exited boolean;
ALTER TABLE enrollment DROP CONSTRAINT enrollment_uk;
