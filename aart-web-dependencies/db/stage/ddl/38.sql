--ddl/38.sql

--Drop columns related to Financial Literacy Assessment
ALTER TABLE kids_record_staging drop column if exists financial_literacy_assess;
ALTER TABLE kids_record_staging drop column if exists grouping_financial_literacy_1;
ALTER TABLE kids_record_staging drop column if exists grouping_financial_literacy_2;

--Add new columns for HistoryGovernance proctor info to stagin tables
ALTER TABLE kids_record_staging ADD COLUMN state_history_proctor_id character varying(20),
				ADD COLUMN state_history_proctor_first_name character varying(120),
				ADD COLUMN state_history_proctor_last_name character varying(120);
	
