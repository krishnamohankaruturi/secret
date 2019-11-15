
-- 397.sql

CREATE TABLE subscoreframework
(
  id bigserial NOT NULL,
  schoolyear bigint not null,
  assessmentprogram bigint not null,
  subject bigint not null,
  grade bigint not null,
  subscoredefinitionname character varying(50) not null, 
  framework bigint,
  frameworklevel1 bigint,
  frameworklevel2 bigint,
  frameworklevel3 bigint,
  batchuploadid bigint NOT NULL,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT subscoreframework_id_pk PRIMARY KEY (id),
  CONSTRAINT subscoreframework_batchuploadid_fk FOREIGN KEY (batchuploadid)
      REFERENCES batchupload (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);


-- change column type to avoid length restrictions
ALTER TABLE studentresponsescore ALTER COLUMN dimension TYPE TEXT;