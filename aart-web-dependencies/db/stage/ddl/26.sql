--ddl/*.sql ==> For dml/26.sql
--US19159: Emails Related to Processing KIDS for 2017

ALTER TABLE kids_record_staging ADD COLUMN emailsentdate timestamp with time zone;
ALTER TABLE tasc_record_staging ADD COLUMN emailsentdate timestamp with time zone;
DROP INDEX IF EXISTS idx_kidsrecordstaging_emailsentdate;
CREATE INDEX idx_kidsrecordstaging_emailSentDate ON kids_record_staging (emailsentdate); 
  