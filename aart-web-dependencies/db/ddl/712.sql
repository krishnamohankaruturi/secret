--ddl/712.sql

--F671

-- Table: uploadgrffile

-- DROP TABLE uploadgrffile;

CREATE TABLE uploadgrffile
(
  id bigserial NOT NULL,
  assessmentprogramid bigint,
  batchuploadprocessid bigint NOT NULL,
  studentid bigint,
  statestudentidentifier character varying(50),
  aypschoolidentifier character varying(100),
  localstudentidentifier character varying(50),
  currentgradelevel character varying(150),
  gradeid bigint,
  studentlegalfirstname character varying(80),
  studentlegalmiddlename character varying(80),
  studentlegallastname character varying(80),
  generationcode character varying(10),
  username character varying(100),
  firstlanguage character varying(2),
  dateofbirth date,
  gender character varying(6),
  comprehensiverace character varying(5),
  hispanicethnicity character varying(1),
  primarydisabilitycode character varying(60),
  esolparticipationcode character varying(1),
  schoolentrydate date,
  districtentrydate date,
  stateentrydate date,
  attendanceschoolprogramidentifier character varying(100),
  state character varying(100),
  statecode character varying(100),
  stateid bigint,
  districtcode character varying(100),
  district character varying(100),
  districtid bigint,
  schoolcode character varying(100),
  school character varying(100),
  schoolid bigint,
  educatorfirstname character varying(80),
  educatorlastname character varying(80),
  educatorusername character varying(254),
  educatoridentifier character varying,
  kiteeducatoridentifier character varying,
  exitwithdrawaldate date,
  exitwithdrawalcode character varying,
  subject character varying(4),
  subjectid bigint,
  finalband character varying(150),
  sgp character varying(3),
  performancelevel bigint,
  nyperformancelevel bigint,
  invalidationcode bigint,
  totallinkagelevelsmastered character varying,
  iowalinkagelevelsmastered character varying,
  ee1 bigint,
  ee2 bigint,
  ee3 bigint,
  ee4 bigint,
  ee5 bigint,
  ee6 bigint,
  ee7 bigint,
  ee8 bigint,
  ee9 bigint,
  ee10 bigint,
  ee11 bigint,
  ee12 bigint,
  ee13 bigint,
  ee14 bigint,
  ee15 bigint,
  ee16 bigint,
  ee17 bigint,
  ee18 bigint,
  ee19 bigint,
  ee20 bigint,
  ee21 bigint,
  ee22 bigint,
  ee23 bigint,
  ee24 bigint,
  ee25 bigint,
  ee26 bigint,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser bigint NOT NULL,
  activeflag boolean DEFAULT true,
  reportyear bigint,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieduser bigint NOT NULL,
  externaluniquerowidentifier bigint,
  versionid bigint NOT NULL,
  CONSTRAINT pk_upload_grf_file PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);


-- Index: idx10_uploadgrffile

-- DROP INDEX idx10_uploadgrffile;

CREATE INDEX idx10_uploadgrffile
  ON uploadgrffile
  USING btree
  (educatoridentifier COLLATE pg_catalog."default");

-- Index: idx11_uploadgrffile

-- DROP INDEX idx11_uploadgrffile;

CREATE INDEX idx11_uploadgrffile
  ON uploadgrffile
  USING btree
  (state COLLATE pg_catalog."default");

-- Index: idx12_uploadgrffile

-- DROP INDEX idx12_uploadgrffile;

CREATE INDEX idx12_uploadgrffile
  ON uploadgrffile
  USING btree
  (currentgradelevel COLLATE pg_catalog."default");

-- Index: idx13_uploadgrffile

-- DROP INDEX idx13_uploadgrffile;

CREATE INDEX idx13_uploadgrffile
  ON uploadgrffile
  USING btree
  (districtcode COLLATE pg_catalog."default");

-- Index: idx14_uploadgrffile

-- DROP INDEX idx14_uploadgrffile;

CREATE INDEX idx14_uploadgrffile
  ON uploadgrffile
  USING btree
  (district COLLATE pg_catalog."default");

-- Index: idx15_uploadgrffile

-- DROP INDEX idx15_uploadgrffile;

CREATE INDEX idx15_uploadgrffile
  ON uploadgrffile
  USING btree
  (schoolcode COLLATE pg_catalog."default");

-- Index: idx16_uploadgrffile

-- DROP INDEX idx16_uploadgrffile;

CREATE INDEX idx16_uploadgrffile
  ON uploadgrffile
  USING btree
  (school COLLATE pg_catalog."default");

-- Index: idx17_uploadgrffile

-- DROP INDEX idx17_uploadgrffile;

CREATE INDEX idx17_uploadgrffile
  ON uploadgrffile
  USING btree
  (subject COLLATE pg_catalog."default");

-- Index: idx18_uploadgrffile

-- DROP INDEX idx18_uploadgrffile;

CREATE INDEX idx18_uploadgrffile
  ON uploadgrffile
  USING btree
  (reportyear);

-- Index: idx19_uploadgrffile

-- DROP INDEX idx19_uploadgrffile;

CREATE INDEX idx19_uploadgrffile
  ON uploadgrffile
  USING btree
  (assessmentprogramid);

-- Index: idx1_uploadgrffile

-- DROP INDEX idx1_uploadgrffile;

CREATE INDEX idx1_uploadgrffile
  ON uploadgrffile
  USING btree
  (batchuploadprocessid);

-- Index: idx2_uploadgrffile

-- DROP INDEX idx2_uploadgrffile;

CREATE INDEX idx2_uploadgrffile
  ON uploadgrffile
  USING btree
  (statestudentidentifier COLLATE pg_catalog."default");

-- Index: idx3_uploadgrffile

-- DROP INDEX idx3_uploadgrffile;

CREATE INDEX idx3_uploadgrffile
  ON uploadgrffile
  USING btree
  (id);

-- Index: idx4_uploadgrffile

-- DROP INDEX idx4_uploadgrffile;

CREATE INDEX idx4_uploadgrffile
  ON uploadgrffile
  USING btree
  (aypschoolidentifier COLLATE pg_catalog."default");

-- Index: idx5_uploadgrffile

-- DROP INDEX idx5_uploadgrffile;

CREATE INDEX idx5_uploadgrffile
  ON uploadgrffile
  USING btree
  (attendanceschoolprogramidentifier COLLATE pg_catalog."default");

-- Index: idx6_uploadgrffile

-- DROP INDEX idx6_uploadgrffile;

CREATE INDEX idx6_uploadgrffile
  ON uploadgrffile
  USING btree
  (performancelevel);

-- Index: idx7_uploadgrffile

-- DROP INDEX idx7_uploadgrffile;

CREATE INDEX idx7_uploadgrffile
  ON uploadgrffile
  USING btree
  (studentid);

-- Index: idx8_uploadgrffile

-- DROP INDEX idx8_uploadgrffile;

CREATE INDEX idx8_uploadgrffile
  ON uploadgrffile
  USING btree
  (nyperformancelevel);

-- Index: idx9_uploadgrffile

-- DROP INDEX idx9_uploadgrffile;

CREATE INDEX idx9_uploadgrffile
  ON uploadgrffile
  USING btree
  (invalidationcode);

  CREATE INDEX idx20_versionid_uploadgrffile
  ON uploadgrffile
  USING btree
  (versionid);

ALTER TABLE uploadgrffile
   ADD COLUMN gradechange boolean NOT NULL default false;


-- Table: grfstateapprove

-- DROP TABLE grfstateapprove;

CREATE TABLE grfstateapprove
(
  id bigserial NOT NULL,
  stateid bigint NOT NULL,
  activeflag boolean,
  updatedsuserid bigint,
  updateddate timestamp with time zone,
  source character varying(10),
  operation character varying(20),
  createddate timestamp with time zone,
  createduser bigint,
  modifieddate timestamp with time zone,
  modifieduser bigint,
  schoolyear bigint,
  CONSTRAINT pk_grf_state_approve PRIMARY KEY (id),
  CONSTRAINT fk_createduser_grfstateapprove FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_modifiedusr_grfstateapprove FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_staeid_grfstateapprove FOREIGN KEY (stateid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_updateduserid_grfstateapprove FOREIGN KEY (updatedsuserid)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);


-- Index: idx_grfstateapprove_id

-- DROP INDEX idx_grfstateapprove_id;

CREATE INDEX idx_grfstateapprove_id
  ON grfstateapprove
  USING btree
  (id);

-- Index: idx_grfstateapprove_schoolyear

-- DROP INDEX idx_grfstateapprove_schoolyear;

CREATE INDEX idx_grfstateapprove_schoolyear
  ON grfstateapprove
  USING btree
  (schoolyear);

-- Index: idx_grfstateapprove_stateid

-- DROP INDEX idx_grfstateapprove_stateid;

CREATE INDEX idx_grfstateapprove_stateid
  ON grfstateapprove
  USING btree
  (stateid);

-- Index: idx_grfstateapprove_updateddate

-- DROP INDEX idx_grfstateapprove_updateddate;

CREATE INDEX idx_grfstateapprove_updateddate
  ON grfstateapprove
  USING btree
  (updateddate);

-- Index: idx_grfstateapprove_updatedsuserid

-- DROP INDEX idx_grfstateapprove_updatedsuserid;

CREATE INDEX idx_grfstateapprove_updatedsuserid
  ON grfstateapprove
  USING btree
  (updatedsuserid);


--F688

ALTER TABLE assessmenttopic ADD COLUMN batchuploadid BIGINT;

ALTER TABLE studentpctbyassessmenttopic DROP COLUMN testingcycleid;

ALTER TABLE studentpctbyassessmenttopic ADD COLUMN reportcycle VARCHAR(20);


ALTER TABLE studentpctbyassessmenttopic ADD COLUMN batchuploadid BIGINT;

ALTER TABLE studentpctbyassessmenttopic ADD COLUMN testingprogramid BIGINT;


ALTER TABLE organizationpctbyassessmenttopic DROP COLUMN testingcycleid;

ALTER TABLE organizationpctbyassessmenttopic ADD COLUMN reportcycle VARCHAR(20);


ALTER TABLE organizationpctbyassessmenttopic ADD COLUMN batchuploadid BIGINT;

ALTER TABLE organizationpctbyassessmenttopic ADD COLUMN testingprogramid BIGINT;

ALTER TABLE organizationreportdetails  ADD COLUMN assessmentname VARCHAR;

--F681
CREATE TABLE uploadsccodefile
(
  id bigserial NOT NULL,
  batchuploadprocessid bigint NOT NULL,
  studentid bigint,
  state character varying,
  stateid  bigint,
  statestudentidentifier character varying(50),
  studentlegalfirstname character varying(80),
  studentlegalmiddlename character varying(80),
  studentlegallastname character varying(80),
  generationcode character varying(10),
  dateofbirth date,
  specialcircumstancecode bigint,
  ksdesccode character varying(20),
  specialcircumstancelabel character varying(1000),
  essentialelement character varying,
  assessment character varying(100),
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser bigint NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieduser bigint NOT NULL,
  reportyear bigint,
  sccodecreateddate timestamp with time zone,
  CONSTRAINT pk_upload_sc_code_file PRIMARY KEY (id)
);

CREATE INDEX idx1_uploadsccodefile ON uploadsccodefile USING btree (batchuploadprocessid);
CREATE INDEX idx2_uploadsccodefile ON uploadsccodefile USING btree (statestudentidentifier);
CREATE INDEX idx3_uploadsccodefile ON uploadsccodefile USING btree (specialcircumstancecode);
CREATE INDEX idx4_uploadsccodefile ON uploadsccodefile USING btree (essentialelement);
CREATE INDEX idx5_uploadsccodefile ON uploadsccodefile USING btree (ksdesccode);
CREATE INDEX idx6_uploadsccodefile ON uploadsccodefile USING btree (assessment);
CREATE INDEX idx7_uploadsccodefile ON uploadsccodefile USING btree (studentid);
CREATE INDEX idx8_uploadsccodefile ON uploadsccodefile USING btree (stateid);
