
-- 243.sql

create table rawtoscalescore (
  id bigserial NOT NULL,
  testid bigint NOT NULL,
  level character varying(100),
  raw bigint NOT NULL,
  scalescore bigint NOT NULL,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  modifieddate timestamp with time zone NOT NULL DEFAULT now(),
  createduser bigint,
  modifieduser bigint,
  activeflag boolean DEFAULT true,
userreportuploadid bigint NOT NULL,
  CONSTRAINT rawtoscalescore_id_fk PRIMARY KEY (id),
  CONSTRAINT rawtoscalescore_testid_fk FOREIGN KEY (testid)
      REFERENCES test (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT rawtoscalescore_userreportuploadid_fk FOREIGN KEY (userreportuploadid)
      REFERENCES userreportupload (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
