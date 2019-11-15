--ddl/*.sql

--US17808: Reports: Upload subscore descriptions, usage enhancements for 2016
--Delete existing rows because the new column is going to be Non-Null. Causes conflict. Confirm with Gowtham/Arun
delete from subscoresdescription;

--Add the new column for subscoresdescription table
alter table subscoresdescription add column sectionlinebelowFlag boolean NOT NULL;

--Create index for the new column
DROP INDEX IF EXISTS idx_subscoresdescription_sectionlinebelowFlag;
CREATE INDEX idx_subscoresdescription_sectionlinebelowFlag
  ON subscoresdescription
  USING btree
  (sectionlinebelowFlag);
