-- Rename existing table, sequence and indexes
ALTER TABLE kids_record_staging RENAME TO kids_record_staging_2015;

ALTER SEQUENCE kids_record_staging_id_seq RENAME TO kids_record_staging_2015_id_seq;

--ALTER TABLE  kids_record_staging_2015 ALTER COLUMN id nextval('kids_record_staging_2015_id_seq'::regclass);

-- Drop existing indexes

DROP INDEX idx_kids_record_staging_currschyr;
DROP INDEX idx_kids_record_staging_ststudentid;

-- Create new indexes
CREATE INDEX idx_kids_record_staging_2015_currschyr
  ON kids_record_staging_2015
  USING btree
  (current_school_year COLLATE pg_catalog."default");

CREATE INDEX idx_kids_record_staging_2015_ststudentid
  ON kids_record_staging_2015
  USING btree
  (state_student_identifier COLLATE pg_catalog."default");


-- Create new table, sequence and indexes

CREATE SEQUENCE kids_record_staging_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 2000
  CACHE 1;


CREATE TABLE kids_record_staging
(

  id bigint NOT NULL DEFAULT nextval('kids_record_staging_id_seq'::regclass),
  ksdexmlaudit_id bigint NOT NULL,
  create_date character varying(30),
  record_common_id character varying(30),
  record_type character varying(30),
  state_student_identifier character varying(30),
  ayp_qpa_bldg_no character varying(30),
  legal_last_name character varying(70),
  legal_first_name character varying(70),
  legal_middle_name character varying(70),
  generation_code character varying(70),
  gender character varying(10),
  current_grade_level character varying(10),
  hispanic_ethnicity character varying(10),
  current_school_year character varying(10),
  attendance_bldg_no character varying(30),
  school_entry_date character varying(30),
  district_entry_date character varying(30),
  state_entry_date character varying(30),
  comprehensive_race character varying(30),
  primary_exceptionality_code character varying(30),
  secondary_exceptionality_code character varying(30),
  grouping_math_1 character varying(50),
  grouping_math_2 character varying(50),
  grouping_reading_1 character varying(50),
  grouping_reading_2 character varying(50),
  grouping_science_1 character varying(50),
  grouping_science_2 character varying(50),
  grouping_history_1 character varying(50),
  grouping_history_2 character varying(50),
  state_math_assess character varying(10),
  state_science_assess character varying(10),
  animal_systems_assess character varying(10),
  comprehensive_ag_assess character varying(10),
  comprehensive_business_assess character varying(10),
  design_preconstruction_assess character varying(10),
  ela_proctor_id character varying(20),
  ela_proctor_name character varying(120),
  elpa21_assess character varying(10),
  esol_participation_code character varying(10),
  finance_assess character varying(10),
  general_cte_assess character varying(10),
  grouping_animal_systems character varying(50),
  grouping_comprehensive_ag character varying(50),
  grouping_comprehensive_business character varying(50),
  grouping_cte_1 character varying(50),
  grouping_cte_2 character varying(50),
  grouping_design_preconstruction character varying(50),
  grouping_elpa21_1 character varying(50),
  grouping_elpa21_2 character varying(50),
  grouping_finance character varying(50),
  grouping_manufacturing_prod character varying(50),
  grouping_plant_systems character varying(50),
  manufacturing_prod_assess character varying(10),
  math_dlm_proctor_id character varying(20),
  math_dlm_proctor_name character varying(120),
  plant_systems_assess character varying(10),
  science_dlm_proctor_id character varying(20),
  science_dlm_proctor_name character varying(120),
  state_ela_assess character varying(10),
  state_hist_gov_assess character varying(10)
)
WITH (
  OIDS=FALSE
);


CREATE INDEX idx_kids_record_staging_currschyr
  ON kids_record_staging
  USING btree
  (current_school_year COLLATE pg_catalog."default");

CREATE INDEX idx_kids_record_staging_ststudentid
  ON kids_record_staging
  USING btree
  (state_student_identifier COLLATE pg_catalog."default");



