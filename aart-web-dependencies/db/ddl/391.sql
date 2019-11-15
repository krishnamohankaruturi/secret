-- 391.sql
-- Level Description Upload

CREATE TABLE leveldescription
(
  id bigserial NOT NULL,
  schoolyear bigint NOT NULL,
  assessmentprogram bigint NOT NULL,
  subject bigint NOT NULL,
  grade bigint NOT NULL,
  testid1 bigint,
  testid2 bigint,
  level bigint not null,
  levelname character varying(300),
  batchuploadid bigint NOT NULL,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  createduser bigint,
  activeflag boolean DEFAULT true,
  CONSTRAINT leveldescription_id_fk PRIMARY KEY (id),
  CONSTRAINT leveldescription_batchuploadid_fk FOREIGN KEY (batchuploadid)
      REFERENCES batchupload (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

alter table fieldspecification alter column minimum type numeric(10,6);

