-- 407.sql

CREATE TABLE reportsmedianscore
(
  id bigserial NOT NULL,
  assessmentprogramid bigint NOT NULL,
  contentAreaId bigint NOT NULL,
  gradeid bigint NOT NULL,
  organizationid bigint,
  organizationtypeid bigint,
  score bigint,
  standarddeviation numeric(10,6),
  standarderror numeric(10,6),
  schoolyear integer,
  studentcount integer,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT reportsmedianscore_pk PRIMARY KEY (id),
  CONSTRAINT reportsmedianscore_organizationid_fkey FOREIGN KEY (organizationid)
      REFERENCES organization (id) MATCH SIMPLE,
  CONSTRAINT reportsmedianscore_organizationtypeid_fkey FOREIGN KEY (organizationtypeid)
      REFERENCES organizationtype (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);



CREATE TABLE reportspercentbylevel
(
  id bigserial NOT NULL,
  organizationid bigint,
  organizationtypeid bigint,
  assessmentprogram bigint,
  grade bigint,
  contentarea bigint,
  studenttest1id bigint,
  studenttest2id bigint,
  externaltest1id bigint,
  externaltest2id bigint,
  level bigint,
  percent integer,
  studentcount integer,
  batchreportprocessid bigint NOT NULL,
  schoolyear integer,
    createddate timestamp with time zone NOT NULL DEFAULT now(),

  CONSTRAINT reportspercentbylevel_pkey PRIMARY KEY (id),
  CONSTRAINT reportspercentbylevel_organizationid_fk FOREIGN KEY (organizationid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT studentreport_batchreportprocessid_fk FOREIGN KEY (batchreportprocessid)
      REFERENCES reportprocess (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
 )
WITH (
  OIDS=FALSE
);

alter table studentreport add column status boolean;