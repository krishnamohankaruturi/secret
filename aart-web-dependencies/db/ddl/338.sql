-- 338.sql
ALTER TABLE testsession ADD COLUMN testtypeid bigint;

ALTER TABLE testsession
 ADD CONSTRAINT testsession_testtypeid_fk 
FOREIGN KEY (testtypeid) REFERENCES testtype (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

DROP INDEX IF EXISTS idx_testsession_testtypeid;
CREATE INDEX idx_testsession_testtypeid ON testsession USING btree (testtypeid);

ALTER TABLE testsession ADD COLUMN gradecourseid bigint;

ALTER TABLE testsession
 ADD CONSTRAINT testsession_gradecourseid_fk 
FOREIGN KEY (gradecourseid) REFERENCES gradecourse (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

DROP INDEX IF EXISTS idx_testsession_gradecourseid;
CREATE INDEX idx_testsession_gradecourseid ON testsession USING btree (gradecourseid);

ALTER TABLE testsession ADD COLUMN stageid bigint;

ALTER TABLE testsession
 ADD CONSTRAINT testsession_stageid_fk 
FOREIGN KEY (stageid) REFERENCES stage (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

DROP INDEX IF EXISTS idx_testsession_stageid;
CREATE INDEX idx_testsession_stageid ON testsession USING btree (stageid);

CREATE TABLE kids_record_staging
(
  id bigserial NOT NULL,
  ksdexmlaudit_id bigint NOT NULL,
  create_date character varying(30),
  record_common_id character varying(30),
  record_type character varying(30),
  state_student_identifier character varying(30),
  ayp_qpa_bldg_no character varying(30),
  residence_org_no character Varying(30),
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
  funding_bldg_no character varying(10),
  attendance_bldg_no character varying(30),
  school_entry_date character varying(30),
  District_Entry_Date character varying(30),
  State_Entry_Date character varying(30),
  User_Field_1 character varying(30),
  User_Field_2 character varying(30),
  User_Field_3 character varying(30),
  Exit_Withdrawal_Date character varying(30),
  Exit_Withdrawal_Type character varying(30),
  Special_Circumstances_Transfer character varying(30),
  Post_Graduation_Plans character varying(30),
  SID_Supplied_By_User character varying(10),
  comprehensive_race character varying(30),
  Primary_Exceptionality_Code character varying(30),
  Secondary_Exceptionality_Code character varying(30),
  Migratory_Status character varying(30),
  USA_Entry_Date character varying(30),
  First_Language character varying(30),
  ESOL_Participation_Code character varying(30),
  ESOL_Program_Ending_Date character varying(30),
  SPED_Program_End_Date character varying(30),
  ESOL_Program_Entry_Date character varying(30),
  Grouping_Math_1 character varying(30),
  Grouping_Math_2 character varying(30),
  Grouping_Reading_1 character varying(30),
  Grouping_Reading_2 character varying(30),
  Grouping_Science_1 character varying(30),
  Grouping_Science_2 character varying(30),
  Grouping_History_1 character varying(30),
  Grouping_History_2 character varying(30),
  Grouping_Writing_1 character varying(30),
  Grouping_Writing_2 character varying(30),
  State_Math_Assess character varying(30),
  State_Reading_Assess character varying(30),
  K8_State_Sci_Assess character varying(30),
  HS_State_Life_Sci_Assess character varying(30),
  HS_State_Phys_Sci_Assess character varying(30),
  K8_State_Hist_Gov_Assess character varying(30),
  HS_State_Hist_Gov_World character varying(30),
  HS_State_Hist_Gov_State character varying(30),
  State_Writing_Assess character varying(30),
  KELPA character varying(30),
  Grouping_KELPA_1 character varying(30),
  Grouping_KELPA_2 character varying(30)
)
WITH (
  OIDS=FALSE
);

-- Function: organization_children_oftype(bigint, IN typecode character varying)

-- DROP FUNCTION organization_children_oftype(bigint, IN typecode character varying);

CREATE OR REPLACE FUNCTION organization_children_oftype(IN parentid bigint, IN typecode character varying)
  RETURNS TABLE(id bigint, organizationname character varying, displayidentifier character varying, organizationtypeid bigint, welcomemessage character varying, contractingorganization boolean, schoolstartdate timestamp with time zone, schoolenddate timestamp with time zone, expirepasswords boolean, expirationdatetype bigint) AS
$BODY$
        WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
        SELECT organizationid, parentorganizationid FROM organizationrelation WHERE parentorganizationid = $1
          UNION
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation as parentorganization_relation
          WHERE organizationrelation.parentorganizationid = parentorganization_relation.organizationid)
          SELECT o.id, o.organizationname, o.displayidentifier, o.organizationtypeid, o.welcomemessage, o.contractingorganization, o.schoolstartdate, o.schoolenddate, o.expirepasswords,  o.expirationdatetype 
          from organization o JOIN organizationtype as orgtype ON o.organizationtypeid = orgtype.id
		WHERE orgtype.typecode = 'SCH' and o.id in (Select organizationid FROM organization_relation);
        $BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;

CREATE INDEX idx_contentareatesttypesubjectarea_contentarea
  ON contentareatesttypesubjectarea USING btree (contentareaid);

CREATE INDEX idx_contentareatesttypesubjectarea_testtypesubjectarea
  ON contentareatesttypesubjectarea USING btree (testtypesubjectareaid);

CREATE INDEX idx_testtypesubjectarea_testtype
	ON testtypesubjectarea USING btree (testtypeid);

CREATE INDEX idx_testtypesubjectarea_subjectarea
	ON testtypesubjectarea USING btree (subjectareaid);
