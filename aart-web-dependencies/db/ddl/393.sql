-- Upload Reports
-- US15913: Reports - Upload subscore conversion from raw to scale score by test
drop table IF EXISTS subscoresrawtoscale;

CREATE TABLE subscoresrawtoscale
(
  id bigserial NOT NULL,
  schoolyear bigint not null,
  assessmentprogram bigint not null,
  subject bigint not null,
  grade bigint not null,
  testid1 bigint not null,
  testid2 bigint,
  subscoredefinitionname character varying(100) not null, 
  rawscore numeric not null,
  scalescore bigint not null,
  standarderror numeric not null,
  batchuploadid bigint NOT NULL,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT subscores_rawtoscale_id_pk PRIMARY KEY (id),
  CONSTRAINT subscores_rawtoscale_batchuploadid_fk FOREIGN KEY (batchuploadid)
      REFERENCES batchupload (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
