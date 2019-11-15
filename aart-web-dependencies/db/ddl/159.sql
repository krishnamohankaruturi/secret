
--US14176 Name:  Summative Reports - cPass Report Upload CSV and Report Upload UI Changes

CREATE TABLE scaleandsem
(
  id bigserial NOT NULL,
  testid bigint NOT NULL,
  scalescore character varying(100),
  achievementlevellabel character varying(100),
  sem character varying(100),
  entitysubscores character varying(100),
  userreportuploadid bigint NOT NULL,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  modifieddate timestamp with time zone NOT NULL DEFAULT now(),
  createduser bigint,
  modifieduser bigint,
  activeflag boolean DEFAULT true,  
  CONSTRAINT scaleandsem_id_fk PRIMARY KEY (id),
  CONSTRAINT scaleandsem_testid_fk FOREIGN KEY (testid)
      REFERENCES test (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT scaleandsem_userreportuploadid_fk FOREIGN KEY (userreportuploadid)
      REFERENCES userreportupload (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);




CREATE TABLE entitysubscores
(
  id bigserial NOT NULL,
  testid bigint NOT NULL,
  claimorcategory character varying(100),
  entitytype character varying(100),
  subscore character varying(100),
  subscoresem character varying(100),
  entitytopicscores character varying(100),
  userreportuploadid bigint NOT NULL,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  modifieddate timestamp with time zone NOT NULL DEFAULT now(),
  createduser bigint,
  modifieduser bigint,
  activeflag boolean DEFAULT true,  
  CONSTRAINT entitysubscores_id_fk PRIMARY KEY (id),
  CONSTRAINT entitysubscores_testid_fk FOREIGN KEY (testid)
      REFERENCES test (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT entitysubscores_userreportuploadid_fk FOREIGN KEY (userreportuploadid)
      REFERENCES userreportupload (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);


