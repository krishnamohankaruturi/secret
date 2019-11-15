-- F458
-- Table: uploadgrffile

CREATE TABLE uploadgrffile
(
  id bigint NOT NULL,
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
  externaluniquerowidentifier bigint,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser bigint NOT NULL,
  activeflag boolean DEFAULT true,
  reportyear bigint,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieduser bigint NOT NULL,
  CONSTRAINT pk_upload_grf_file PRIMARY KEY (id)
);

CREATE INDEX idx1_uploadgrffile   ON uploadgrffile  USING btree  (batchuploadprocessid);
CREATE INDEX idx2_uploadgrffile   ON uploadgrffile  USING btree  (statestudentidentifier);
CREATE INDEX idx3_uploadgrffile   ON uploadgrffile  USING btree  (id);
CREATE INDEX idx4_uploadgrffile   ON uploadgrffile  USING btree  (aypschoolidentifier);
CREATE INDEX idx5_uploadgrffile   ON uploadgrffile  USING btree  (attendanceschoolprogramidentifier);
CREATE INDEX idx6_uploadgrffile   ON uploadgrffile  USING btree  (performancelevel);
CREATE INDEX idx7_uploadgrffile   ON uploadgrffile  USING btree  (studentid);
CREATE INDEX idx8_uploadgrffile   ON uploadgrffile  USING btree  (nyperformancelevel);
CREATE INDEX idx9_uploadgrffile   ON uploadgrffile  USING btree  (invalidationcode);
CREATE INDEX idx10_uploadgrffile  ON uploadgrffile  USING btree  (educatoridentifier);
CREATE INDEX idx11_uploadgrffile  ON uploadgrffile  USING btree  (state);
CREATE INDEX idx12_uploadgrffile  ON uploadgrffile  USING btree  (currentgradelevel);
CREATE INDEX idx13_uploadgrffile  ON uploadgrffile  USING btree  (districtcode);
CREATE INDEX idx14_uploadgrffile  ON uploadgrffile  USING btree  (district);
CREATE INDEX idx15_uploadgrffile  ON uploadgrffile  USING btree  (schoolcode );
CREATE INDEX idx16_uploadgrffile  ON uploadgrffile  USING btree  (school);
CREATE INDEX idx17_uploadgrffile  ON uploadgrffile  USING btree  (subject);
CREATE INDEX idx18_uploadgrffile  ON uploadgrffile  USING btree  (reportyear);
CREATE INDEX idx19_uploadgrffile  ON uploadgrffile  USING btree  (assessmentprogramid);

ALTER TABLE uploadincidentfile ALTER COLUMN essentialelement TYPE character varying;
ALTER TABLE uploadsccodefile ALTER COLUMN essentialelement TYPE character varying;