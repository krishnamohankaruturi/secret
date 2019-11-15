
-- 388.sql

drop table testcutscores;

CREATE TABLE testcutscores
(
  id bigserial NOT NULL,
  schoolyear bigint NOT NULL,
  assessmentprogram bigint NOT NULL,
  subject bigint NOT NULL,
  grade bigint NOT NULL,
  testid1 bigint,
  testid2 bigint,
  level bigint not null,
  levelLowCutScore character varying(100),
  levelHighCutScore character varying(100),
  batchuploadid bigint NOT NULL,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  createduser bigint,
  activeflag boolean DEFAULT true,
  CONSTRAINT testcutscores_id_fk PRIMARY KEY (id),
  CONSTRAINT testcutscores_batchuploadid_fk FOREIGN KEY (batchuploadid)
      REFERENCES batchupload (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);