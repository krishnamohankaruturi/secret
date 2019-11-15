-- US15862: Reports - Upload conversion from raw to scale score by test - overall score - enhancements

--drop table rawtoscalescores;

CREATE TABLE rawtoscalescores
(
  id bigserial NOT NULL,
  schoolyear bigint not null,
  assessmentprogram bigint not null,
  subject bigint not null,
  grade bigint not null,
  testid1 bigint not null,
  testid2 bigint,
  rawscore numeric not null,
  scalescore bigint not null,
  standarderror numeric not null,
  batchuploadid bigint NOT NULL,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT rawtoscalescores_id_pk PRIMARY KEY (id),
  CONSTRAINT rawtoscalescores_batchuploadid_fk FOREIGN KEY (batchuploadid)
      REFERENCES batchupload (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
 
