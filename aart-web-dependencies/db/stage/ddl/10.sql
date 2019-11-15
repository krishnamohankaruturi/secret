--
--this scripts creates the sequence tasc_record_staging_id_seq
--this serves as the primary key for the tasc_recrods staging table
--
--author: SEH
--date:  11/04/2015
--

CREATE SEQUENCE tasc_record_staging_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

 --Create new table with layout matching the tasc_data XML
 CREATE TABLE tasc_record_staging (
           id                                          bigint PRIMARY KEY DEFAULT nextval('tasc_record_staging_id_seq'),
           ksdexmlaudit_id                             bigint,  
           create_date                                 varchar(30),
           record_common_id                            varchar(30),           
           record_type		                           varchar(30),                              
           state_student_identifier                    varchar(30),
           ayp_qpa_bldg_no                             varchar(30), 
           student_legal_last_name                     varchar(70),
           student_legal_first_name                    varchar(70),
           student_legal_middle_name                   varchar(70),
           student_generation_code                     varchar(70),
           student_gender                              varchar(10),  
           current_grade_level                         varchar(10),
           current_school_year                         varchar(10),
           attendance_bldg_no                          varchar(30),   
           educator_bldg_no                            varchar(30), 
           state_subj_area_code                        varchar(30),
           local_course_id                             varchar(70),
           course_status                               varchar(10),
           teacher_identifier                          varchar(30),
           teacher_last_name                           varchar(70),
           teacher_first_name                          varchar(70),
           teacher_middle_name                         varchar(70),
           teacher_district_email                      varchar(70)
);
           
           
 --create indexes as on the other staging tables
 CREATE INDEX idx_tasc_record_staging_currschyear ON tasc_record_staging (current_school_year);
 CREATE INDEX idx_tasc_record_staging_statestudentid ON tasc_record_staging (state_student_identifier);