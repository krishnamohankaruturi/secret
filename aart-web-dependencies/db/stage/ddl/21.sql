--ddl/20.sql
-- Roll out ksde data to staging tables
CREATE TABLE ksdexmlaudit_2016 AS TABLE ksdexmlaudit;
CREATE TABLE kids_record_staging_2016 AS TABLE kids_record_staging;
CREATE TABLE tasc_record_staging_2016 AS TABLE tasc_record_staging;

-- Truncate the data of KSDE data of 2016
TRUNCATE ksdexmlaudit, kids_record_staging, tasc_record_staging;

CREATE TABLE batchregistrationreason_2016 AS TABLE batchregistrationreason;
CREATE TABLE batchregisteredtestsessions_2016 AS TABLE batchregisteredtestsessions;
CREATE TABLE batchregistration_2016 AS TABLE batchregistration;

-- Truncate the data of batch registration of 2016
TRUNCATE batchregistrationreason, batchregisteredtestsessions, batchregistration;

--kids_record_staging changes
ALTER TABLE kids_record_staging ADD COLUMN sequence_order bigint;
ALTER TABLE kids_record_staging ADD COLUMN notes character varying(1000);
ALTER TABLE kids_record_staging ADD COLUMN av_communications_assess character varying(10);
ALTER TABLE kids_record_staging ADD COLUMN grouping_av_communications character varying(50);
ALTER TABLE kids_record_staging ADD COLUMN financial_literacy_assess character varying(10);
ALTER TABLE kids_record_staging ADD COLUMN grouping_financial_literacy_1 character varying(50);
ALTER TABLE kids_record_staging ADD COLUMN grouping_financial_literacy_2 character varying(50);
ALTER TABLE kids_record_staging ADD COLUMN elpa_proctor_id character varying(20); 
ALTER TABLE kids_record_staging ADD COLUMN elpa_proctor_first_name character varying(120);
ALTER TABLE kids_record_staging ADD COLUMN elpa_proctor_last_name character varying(120); 
ALTER TABLE kids_record_staging ADD COLUMN emailSent boolean;
ALTER TABLE kids_record_staging ADD COLUMN emailSentTo character varying(400);
ALTER TABLE kids_record_staging ADD COLUMN exitWithdrawalType character varying(10);
ALTER TABLE kids_record_staging ADD COLUMN exitWithdrawalDate character varying(20);
ALTER TABLE kids_record_staging ADD COLUMN status character varying(10);

DROP INDEX IF EXISTS idx_kidsrecordstaging_sequenceorder;
CREATE INDEX idx_kidsrecordstaging_sequenceorder ON kids_record_staging (sequence_order);

DROP INDEX IF EXISTS idx_kidsrecordstaging_emailsent;
CREATE INDEX idx_kidsrecordstaging_emailsent ON kids_record_staging (emailSent);

DROP INDEX IF EXISTS idx_kidsrecordstaging_emailsentto;
CREATE INDEX idx_kidsrecordstaging_emailsentto ON kids_record_staging (emailSentTo);

DROP INDEX IF EXISTS idx_kidsrecordstaging_status;
CREATE INDEX idx_kidsrecordstaging_status ON kids_record_staging (status);

--tasc_record_staging changes
ALTER TABLE tasc_record_staging ADD COLUMN sequence_order bigint;
ALTER TABLE tasc_record_staging ADD COLUMN notes character varying(400);
ALTER TABLE tasc_record_staging ADD COLUMN birth_date character varying(50);
ALTER TABLE tasc_record_staging ADD COLUMN district_entry_date character varying(50);
ALTER TABLE tasc_record_staging ADD COLUMN school_entry_date character varying(50);
ALTER TABLE tasc_record_staging ADD COLUMN state_entry_date character varying(50);
ALTER TABLE tasc_record_staging ADD COLUMN hispanic_ethnicity character varying(10);
ALTER TABLE tasc_record_staging ADD COLUMN emailSent boolean;
ALTER TABLE tasc_record_staging ADD COLUMN emailSentTo character varying(120);
ALTER TABLE tasc_record_staging ADD COLUMN status character varying(20);

DROP INDEX IF EXISTS idx_tascrecordstaging_sequenceorder;
CREATE INDEX idx_tascrecordstaging_sequenceorder ON tasc_record_staging (sequence_order);

DROP INDEX IF EXISTS idx_tascrecordstaging_emailsent; 
CREATE INDEX idx_tascrecordstaging_emailsent ON tasc_record_staging (emailSent);

DROP INDEX IF EXISTS idx_tascrecordstaging_emailsentto;
CREATE INDEX idx_tascrecordstaging_emailsentto ON tasc_record_staging (emailSentTo);

DROP INDEX IF EXISTS idx_tascrecordstaging_status;
CREATE INDEX idx_tascrecordstaging_status ON tasc_record_staging (status);
