--ddl/526.sql US17652 CombinedLevelMap

CREATE TABLE combinedlevelmap
(
  id bigserial NOT NULL,
  schoolyear bigint NOT NULL,
  assessmentprogramid bigint NOT NULL,
  subjectid bigint NOT NULL,
  gradeid bigint NOT NULL,
  stageslowscalescore bigint NOT NULL,
  stageshighscalescore bigint NOT NULL,
  performancescalescore numeric NOT NULL,
  combinedlevel numeric NOT NULL,
  batchuploadid bigint NOT NULL,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  createduser bigint,
  modifieddate timestamp with time zone NOT NULL DEFAULT now(),
  modifieduser bigint,
  activeflag boolean DEFAULT true,
  comment character varying(1000),
  CONSTRAINT combinedlevelmap_id_fk PRIMARY KEY (id),
  CONSTRAINT combinedlevelmap_batchuploadid_fk FOREIGN KEY (batchuploadid)
      REFERENCES batchupload (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

