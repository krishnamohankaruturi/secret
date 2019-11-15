--22.sql

ALTER TABLE kids_record_staging DROP COLUMN IF EXISTS exitWithdrawalType;
ALTER TABLE kids_record_staging DROP COLUMN IF EXISTS exitWithdrawalDate;
ALTER TABLE kids_record_staging ADD COLUMN exit_withdrawal_type character varying(10);
ALTER TABLE kids_record_staging ADD COLUMN exit_withdrawal_date character varying(30);
ALTER TABLE kids_record_staging ADD COLUMN triggerEmail boolean;
ALTER TABLE kids_record_staging ADD COLUMN birth_date character varying(30);

DROP INDEX IF EXISTS idx_kidsrecordstaging_triggerEmail;
CREATE INDEX idx_kidsrecordstaging_triggerEmail ON kids_record_staging (triggerEmail);

ALTER TABLE tasc_record_staging ADD COLUMN triggerEmail boolean;
DROP INDEX IF EXISTS idx_tascrecordstaging_triggerEmail;
CREATE INDEX idx_tascrecordstaging_triggerEmail ON tasc_record_staging (triggerEmail);