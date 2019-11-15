--ddl/480.sql

CREATE TABLE batchjobschedule
(
  id bigserial NOT NULL,
  jobname character varying(100) NOT NULL,
  jobrefname character varying(100) NOT NULL,
  initmethod character varying(100) NOT NULL,
  cronexpression character varying(60) NOT NULL,
  scheduled boolean NOT NULL DEFAULT false,
  allowedserver character varying(100) NOT NULL,
  CONSTRAINT batchjobschedule_pkey PRIMARY KEY (id)
);

CREATE INDEX idx_batchjobschedule_allowedserver
  ON batchjobschedule USING btree (allowedserver);
  
CREATE TABLE testenrollmentmethod
(
  id bigserial NOT NULL,
  assessmentprogramid bigint NOT NULL,
  methodcode character varying(50) NOT NULL,
  methodname character varying(100) NOT NULL,
  CONSTRAINT testenrollmentmethod_pkey PRIMARY KEY (id),
  CONSTRAINT fk1_testenrollmentmethod FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uc1_testenrollmentmethod UNIQUE (assessmentprogramid, methodcode)
);

CREATE INDEX idx1_testenrollmentmethod_apid
  ON testenrollmentmethod
  USING btree (assessmentprogramid);
