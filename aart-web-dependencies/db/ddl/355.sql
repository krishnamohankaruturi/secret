--kids record staging changes
ALTER TABLE kids_record_staging ALTER COLUMN user_field_1 TYPE character varying(500);
ALTER TABLE kids_record_staging ALTER COLUMN user_field_2 TYPE character varying(500);
ALTER TABLE kids_record_staging ALTER COLUMN user_field_3 TYPE character varying(500);

ALTER TABLE kids_record_staging ALTER COLUMN grouping_math_1 TYPE character varying(50);
ALTER TABLE kids_record_staging ALTER COLUMN grouping_math_2 TYPE character varying(50);
ALTER TABLE kids_record_staging ALTER COLUMN grouping_reading_1 TYPE character varying(50);
ALTER TABLE kids_record_staging ALTER COLUMN grouping_reading_2 TYPE character varying(50);
ALTER TABLE kids_record_staging ALTER COLUMN grouping_science_1 TYPE character varying(50);
ALTER TABLE kids_record_staging ALTER COLUMN grouping_science_2 TYPE character varying(50);
ALTER TABLE kids_record_staging ALTER COLUMN grouping_history_1 TYPE character varying(50);
ALTER TABLE kids_record_staging ALTER COLUMN grouping_history_2 TYPE character varying(50);
ALTER TABLE kids_record_staging ALTER COLUMN grouping_writing_1 TYPE character varying(50);
ALTER TABLE kids_record_staging ALTER COLUMN grouping_writing_2 TYPE character varying(50);
ALTER TABLE kids_record_staging ALTER COLUMN grouping_kelpa_1 TYPE character varying(50);
ALTER TABLE kids_record_staging ALTER COLUMN grouping_kelpa_2 TYPE character varying(50);

ALTER TABLE kids_record_staging ALTER COLUMN state_math_assess TYPE character varying(10);
ALTER TABLE kids_record_staging ALTER COLUMN state_reading_assess TYPE character varying(10);
ALTER TABLE kids_record_staging ALTER COLUMN k8_state_sci_assess TYPE character varying(10);
ALTER TABLE kids_record_staging ALTER COLUMN hs_state_life_sci_assess TYPE character varying(10);
ALTER TABLE kids_record_staging ALTER COLUMN hs_state_phys_sci_assess TYPE character varying(10);
ALTER TABLE kids_record_staging ALTER COLUMN k8_state_hist_gov_assess TYPE character varying(10);
ALTER TABLE kids_record_staging ALTER COLUMN hs_state_hist_gov_world TYPE character varying(10);
ALTER TABLE kids_record_staging ALTER COLUMN hs_state_hist_gov_state TYPE character varying(10);
ALTER TABLE kids_record_staging ALTER COLUMN state_writing_assess TYPE character varying(10);
ALTER TABLE kids_record_staging ALTER COLUMN funding_bldg_no TYPE character varying(30);

ALTER TABLE kids_record_staging ADD COLUMN state_science_assess character varying(10);
ALTER TABLE kids_record_staging ADD COLUMN state_history_assess character varying(10);
ALTER TABLE kids_record_staging ADD COLUMN cte_assess character varying(10);
ALTER TABLE kids_record_staging ADD COLUMN pathways_assess character varying(10);
ALTER TABLE kids_record_staging ADD COLUMN math_proctor_id character varying(30);
ALTER TABLE kids_record_staging ADD COLUMN math_proctor_name character varying(100);
ALTER TABLE kids_record_staging ADD COLUMN reading_proctor_id character varying(30);
ALTER TABLE kids_record_staging ADD COLUMN reading_proctor_name character varying(100);
ALTER TABLE kids_record_staging ADD COLUMN science_proctor_id character varying(30);
ALTER TABLE kids_record_staging ADD COLUMN science_proctor_name character varying(100);
ALTER TABLE kids_record_staging ADD COLUMN cte_proctor_id character varying(30);
ALTER TABLE kids_record_staging ADD COLUMN cte_proctor_name character varying(100);
