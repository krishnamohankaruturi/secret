

CREATE TABLE IF NOT EXISTS kids_record_staging
(
  id bigserial NOT NULL,
  ksdexmlaudit_id bigint NOT NULL,
  create_date character varying(30),
  record_common_id character varying(30),
  record_type character varying(30),
  state_student_identifier character varying(30),
  ayp_qpa_bldg_no character varying(30),
  residence_org_no character varying(30),
  legal_last_name character varying(70),
  legal_first_name character varying(70),
  legal_middle_name character varying(70),
  generation_code character varying(70),
  gender character varying(10),
  birth_date character varying(30),
  current_grade_level character varying(10),
  local_student_identifier character varying(30),
  hispanic_ethnicity character varying(10),
  current_school_year character varying(10),
  funding_bldg_no character varying(30),
  attendance_bldg_no character varying(30),
  school_entry_date character varying(30),
  district_entry_date character varying(30),
  state_entry_date character varying(30),
  user_field_1 character varying(500),
  user_field_2 character varying(500),
  user_field_3 character varying(500),
  exit_withdrawal_date character varying(30),
  exit_withdrawal_type character varying(30),
  special_circumstances_transfer character varying(30),
  post_graduation_plans character varying(30),
  sid_supplied_by_user character varying(10),
  comprehensive_race character varying(30),
  primary_exceptionality_code character varying(30),
  secondary_exceptionality_code character varying(30),
  migratory_status character varying(30),
  usa_entry_date character varying(30),
  first_language character varying(30),
  esol_participation_code character varying(30),
  esol_program_ending_date character varying(30),
  sped_program_end_date character varying(30),
  esol_program_entry_date character varying(30),
  grouping_math_1 character varying(50),
  grouping_math_2 character varying(50),
  grouping_reading_1 character varying(50),
  grouping_reading_2 character varying(50),
  grouping_science_1 character varying(50),
  grouping_science_2 character varying(50),
  grouping_history_1 character varying(50),
  grouping_history_2 character varying(50),
  grouping_writing_1 character varying(50),
  grouping_writing_2 character varying(50),
  state_math_assess character varying(10),
  state_reading_assess character varying(10),
  k8_state_sci_assess character varying(10),
  hs_state_life_sci_assess character varying(10),
  hs_state_phys_sci_assess character varying(10),
  k8_state_hist_gov_assess character varying(10),
  hs_state_hist_gov_world character varying(10),
  hs_state_hist_gov_state character varying(10),
  state_writing_assess character varying(10),
  kelpa character varying(30),
  grouping_kelpa_1 character varying(50),
  grouping_kelpa_2 character varying(50),
  state_science_assess character varying(10),
  state_history_assess character varying(10),
  cte_assess character varying(10),
  pathways_assess character varying(10),
  math_proctor_id character varying(30),
  math_proctor_name character varying(100),
  reading_proctor_id character varying(30),
  reading_proctor_name character varying(100),
  science_proctor_id character varying(30),
  science_proctor_name character varying(100),
  cte_proctor_id character varying(30),
  cte_proctor_name character varying(100)
);

CREATE TABLE IF NOT EXISTS stco_record_staging
(
  id bigserial NOT NULL,
  ksdexmlaudit_id bigint NOT NULL,
  create_date character varying(30),
  record_common_id character varying(30),
  record_type character varying(30),
  state_student_identifier character varying(30),
  ayp_qpa_bldg_no character varying(30),
  student_legal_last_name character varying(70),
  student_legal_first_name character varying(70),
  student_legal_middle_name character varying(70),
  student_generation_code character varying(70),
  student_gender character varying(10),
  student_birth_date character varying(30),
  current_grade_level character varying(10),
  local_student_identifier character varying(30),
  hispanic_ethnicity character varying(10),
  current_school_year character varying(10),
  attendance_bldg_no character varying(30),
  comprehensive_race character varying(30),
  educator_bldg_no character varying(30),
  kcc_id character varying(70),
  section character varying(30),
  local_course_id character varying(70),
  course_status character varying(10),
  teacher_identifier character varying(30),
  teacher_last_name character varying(70),
  teacher_first_name character varying(70),
  teacher_middle_name character varying(70)
);

CREATE SEQUENCE questar_staging_file_id_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;
	
CREATE SEQUENCE questar_staging_id_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;
	
CREATE SEQUENCE questar_staging_response_info_id_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;

CREATE TABLE questar_staging_file (
	id BIGINT NOT NULL,
	processeddate TIMESTAMP WITH TIME ZONE,
	filename TEXT,
	assessmentrefid TEXT,
	assessmentname TEXT,
	result CHARACTER VARYING (10),
	
	CONSTRAINT questar_staging_file_pkey PRIMARY KEY (id)
);
-- questar staging tables
CREATE TABLE questar_staging (
	id BIGINT NOT NULL,
	questar_staging_file_id BIGINT NOT NULL,
	createdate TIMESTAMP WITH TIME ZONE,
	refid TEXT,
	assessmentadministrationrefid TEXT,
	studentpersonalrefid TEXT,
	walkin BOOLEAN,
	formnumber BIGINT,
	districtcode TEXT,
	schoolcode TEXT,
	subject TEXT,
	studentfirstname TEXT,
	studentlastname TEXT,
	studentmiddlename TEXT,
	grade TEXT,
	studentkitenumber BIGINT,
	studentid TEXT,
	studentdateofbirth DATE,
	
	CONSTRAINT questar_staging_pkey PRIMARY KEY (id),
	
	CONSTRAINT questar_staging_fkey1 FOREIGN KEY (questar_staging_file_id)
		REFERENCES questar_staging_file (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP INDEX IF EXISTS idx_kids_record_staging_currschyr;
CREATE INDEX idx_kids_record_staging_currschyr  ON kids_record_staging USING btree (current_school_year COLLATE pg_catalog."default");

DROP INDEX IF EXISTS idx_kids_record_staging_ststudentid;
CREATE INDEX idx_kids_record_staging_ststudentid ON kids_record_staging USING btree (state_student_identifier COLLATE pg_catalog."default");

DROP INDEX IF EXISTS  idx_stco_record_staging_currschyr;
CREATE INDEX idx_stco_record_staging_currschyr ON stco_record_staging USING btree (current_school_year COLLATE pg_catalog."default");

DROP INDEX IF EXISTS idx_stco_record_staging_ststudentid;
CREATE INDEX idx_stco_record_staging_ststudentid ON stco_record_staging USING btree (state_student_identifier COLLATE pg_catalog."default");

CREATE TABLE questar_staging_response (
	id BIGINT NOT NULL,
	questar_staging_id BIGINT NOT NULL,
	response TEXT,
	itemnumber BIGINT,
	itemname TEXT,
	diagnosticstatement TEXT,
	numberofattempts INTEGER,
	intensityhex "char",
	
	CONSTRAINT questar_staging_response_info_pkey PRIMARY KEY (id),
	
	CONSTRAINT questar_staging_response_fkey1 FOREIGN KEY (questar_staging_id)
		REFERENCES questar_staging (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);