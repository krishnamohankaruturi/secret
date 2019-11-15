
-- 396.sql

CREATE TABLE subscoresdescription
(
  id bigserial NOT NULL,
  schoolyear bigint not null,
  assessmentprogram bigint not null,
  subject bigint not null,
  report character varying(200) not null, 
  subscoredefinitionname character varying(50) not null, 
  subscorereportdisplayname character varying(80) not null, 
  subscorereportdescription character varying(200) not null, 
  subscoredisplaysequence integer not null, 
  batchuploadid bigint NOT NULL,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT subscoresdescription_id_pk PRIMARY KEY (id),
  CONSTRAINT subscoresdescription_batchuploadid_fk FOREIGN KEY (batchuploadid)
      REFERENCES batchupload (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
