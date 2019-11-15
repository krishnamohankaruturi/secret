-- US15536: Student Tracker - Simple Version 1 (preliminary)
-- Sequence: batchstudenttracker_id_seq
-- DROP SEQUENCE batchstudenttracker_id_seq;

CREATE SEQUENCE batchstudenttracker_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

-- Table: batchstudenttracker
-- DROP TABLE batchstudenttracker;
CREATE TABLE batchstudenttracker
(
  id bigint NOT NULL DEFAULT nextval('batchstudenttracker_id_seq'::regclass),
  submissiondate timestamp with time zone NOT NULL DEFAULT now(),
  status character varying(200),
  assessmentprogram bigint,
  orgid bigint,
  orgdisplayidentifier character varying(100),
  orgschoolyear integer,
  orgpooltype character varying(30),
  contentareaid bigint,
  contentareaname character varying(100),
  successcount integer,
  failedcount integer,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  modifieddate timestamp with time zone NOT NULL DEFAULT now(),
  createduser bigint,
  CONSTRAINT batchstudenttracker_pk PRIMARY KEY (id),
  CONSTRAINT batchstudenttracker_assessmentprogram_fkey FOREIGN KEY (assessmentprogram)
      REFERENCES assessmentprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT batchstudenttracker_createduser_fkey FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,

  CONSTRAINT batchstudenttracker_orgid_fkey FOREIGN KEY (orgid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,   

  CONSTRAINT batchstudenttracker_contentareaid_fkey FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION        	
);

-- Table: batchstudenttrackerreason
-- DROP TABLE batchstudenttrackerreason;
CREATE TABLE batchstudenttrackerreason
(
  batchstudenttrackerid bigint NOT NULL,
  studentid bigint,
  studentfirstname character varying(80),
  studentmiddlename character varying(80),
  studentlastname character varying(80),
  gradecourseid bigint,
  testid bigint,
  testcollectionid bigint,
  reason text,
  CONSTRAINT batchstudenttrackerreason_batchstudenttrackerid_fkey FOREIGN KEY (batchstudenttrackerid)
      REFERENCES batchstudenttracker (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT batchstudenttrackerreason_studentid_fkey FOREIGN KEY (studentid)
      REFERENCES student (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT batchstudenttrackerreason_gradecourseid_fkey FOREIGN KEY (gradecourseid)
      REFERENCES gradecourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT batchstudenttrackerreason_testid_fkey FOREIGN KEY (testid)
      REFERENCES studentstests (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION                                    	
);
