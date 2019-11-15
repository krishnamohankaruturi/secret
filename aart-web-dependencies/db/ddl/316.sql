
-- 316.sql

CREATE TABLE stage (
  id bigserial NOT NULL,
  externalid bigint,
  code character varying(75) NOT NULL,
  name character varying(75) NOT NULL,
  sortorder integer NOT NULL,
  predecessorid bigint,
  activeflag boolean DEFAULT true,  
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  modifieddate timestamp with time zone NOT NULL DEFAULT now(),
  originationcode character varying(20),
  CONSTRAINT stage_pkey PRIMARY KEY (id),
  CONSTRAINT fk_stage_predecessorid FOREIGN KEY (predecessorid)
      REFERENCES stage (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
 );

ALTER TABLE testcollection ADD stageid bigint;

ALTER TABLE testcollection
  ADD CONSTRAINT fk_testcollection_stageid FOREIGN KEY (stageid)
      REFERENCES stage (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

      
ALTER TABLE studentstests ADD COLUMN enhancednotes text;

CREATE TABLE complexityband
(
  id bigint NOT NULL,
  bandname character varying(60) NOT NULL,
  bandcode character varying(20),
  minrange double precision,
  maxrange double precision,
  CONSTRAINT complexityband_pkey PRIMARY KEY (id)
);

CREATE INDEX complexityband_fk1 ON complexityband
  USING btree (bandcode COLLATE pg_catalog."default");
  
ALTER TABLE student ADD COLUMN profilestatus varchar DEFAULT 'NO SETTINGS';