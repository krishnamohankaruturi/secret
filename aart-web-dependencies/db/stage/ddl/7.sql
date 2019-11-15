
--TABLE: batchregistration
CREATE TABLE IF NOT EXISTS batchregistration
(
  id bigserial NOT NULL,
  submissiondate timestamp with time zone NOT NULL DEFAULT now(),
  status character varying(200),
  assessmentprogram bigint,
  testingprogram bigint,
  assessment bigint,
  testtype bigint,
  subject bigint,
  grade bigint,
  successcount integer,
  failedcount integer,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  modifieddate timestamp with time zone NOT NULL DEFAULT now(),
  createduser bigint,
  contentareaid bigint,
  batchtypecode character varying(20) NOT NULL DEFAULT 'BATCHAUTO'::character varying,
  CONSTRAINT batchregistration_pk PRIMARY KEY (id) 
);


-- Index: idx_batchregistration_assessment

DROP INDEX IF EXISTS idx_batchregistration_assessment;
CREATE INDEX idx_batchregistration_assessment
  ON batchregistration
  USING btree
  (assessment);

-- Index: idx_batchregistration_assessmentprogram

DROP INDEX IF EXISTS idx_batchregistration_assessmentprogram;
CREATE INDEX idx_batchregistration_assessmentprogram
  ON batchregistration
  USING btree
  (assessmentprogram);

-- Index: idx_batchregistration_contentareaid

DROP INDEX IF EXISTS idx_batchregistration_contentareaid;
CREATE INDEX idx_batchregistration_contentareaid
  ON batchregistration
  USING btree
  (contentareaid);

-- Index: idx_batchregistration_grade

DROP INDEX IF EXISTS idx_batchregistration_grade;
CREATE INDEX idx_batchregistration_grade
  ON batchregistration
  USING btree
  (grade);

-- Index: idx_batchregistration_subject

DROP INDEX IF EXISTS idx_batchregistration_subject;
CREATE INDEX idx_batchregistration_subject
  ON batchregistration
  USING btree
  (subject);

-- Index: idx_batchregistration_submissiondate

DROP INDEX IF EXISTS idx_batchregistration_submissiondate;
CREATE INDEX idx_batchregistration_submissiondate
  ON batchregistration
  USING btree
  (submissiondate);

-- Index: idx_batchregistration_testingprogram

DROP INDEX IF EXISTS idx_batchregistration_testingprogram;
CREATE INDEX idx_batchregistration_testingprogram
  ON batchregistration
  USING btree
  (testingprogram);

-- Index: idx_batchregistration_testtype

DROP INDEX IF EXISTS idx_batchregistration_testtype;
CREATE INDEX idx_batchregistration_testtype
  ON batchregistration
  USING btree
  (testtype);
  
  
  
--TABLE: batchregistrationreason
CREATE TABLE IF NOT EXISTS batchregistrationreason
(
  batchregistrationid bigint NOT NULL,
  studentid bigint,
  reason text,
  testsessionid bigint,
  CONSTRAINT batchregistrationreason_batchregistrationid_fkey FOREIGN KEY (batchregistrationid)
      REFERENCES batchregistration (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);



-- Index: idx_batchregistrationreason_batchregistrationid

DROP INDEX IF EXISTS idx_batchregistrationreason_batchregistrationid;
CREATE INDEX idx_batchregistrationreason_batchregistrationid
  ON batchregistrationreason
  USING btree
  (batchregistrationid);

-- Index: idx_batchregistrationreason_studentid

DROP INDEX IF EXISTS idx_batchregistrationreason_studentid;
CREATE INDEX idx_batchregistrationreason_studentid
  ON batchregistrationreason
  USING btree
  (studentid);
  
--TABLE: batchregisteredtestsessions  
CREATE TABLE IF NOT EXISTS batchregisteredtestsessions
(
  batchregistrationid bigint NOT NULL,
  testsessionid bigint,
  CONSTRAINT batchregisteredtestsessions_batchregistrationid_fkey FOREIGN KEY (batchregistrationid)
      REFERENCES batchregistration (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- Index: idx_batchregisteredtestsessions_batchregistrationid

DROP INDEX IF EXISTS idx_batchregisteredtestsessions_batchregistrationid;
CREATE INDEX idx_batchregisteredtestsessions_batchregistrationid
  ON batchregisteredtestsessions
  USING btree
  (batchregistrationid);
