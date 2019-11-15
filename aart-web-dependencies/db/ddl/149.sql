
--changes for CB
ALTER TABLE taskvariantlearningmapnode ADD COLUMN nodedescription text;
ALTER TABLE stimulusvariant ADD COLUMN resourcename text;

CREATE SEQUENCE testsectionresource_id_seq
                INCREMENT 1
                MINVALUE 1
                MAXVALUE 9223372036854775807
                START 1;

CREATE TABLE testsectionresource
(
  id bigint NOT NULL DEFAULT nextval('testsectionresource_id_seq'::regclass),
  externalid bigint,
  testsectionid bigint NOT NULL,
  stimulusvariantid bigint NOT NULL,
  sortorder bigint,
  CONSTRAINT testsectionresource_pkey PRIMARY KEY (id),
  CONSTRAINT testsectionresource_testsection_fk FOREIGN KEY (testsectionid)
      REFERENCES testsection (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testsectionresource_stimulusvariant_fk FOREIGN KEY (stimulusvariantid)
      REFERENCES stimulusvariant (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION      
);              
