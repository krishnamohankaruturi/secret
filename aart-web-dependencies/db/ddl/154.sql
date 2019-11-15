
--US14176 Name:  Summative Reports - cPass Report Upload CSV and Report Upload UI Changes 


CREATE TABLE testcutscores
(
  id bigserial NOT NULL,
  testid bigint NOT NULL,
  achievementlevellabel character varying(100),
  achievementleveldescription character varying(100),
  lowerlevelcutscore character varying(100),
  upperlevelcutscore character varying(100),
  userreportuploadid bigint NOT NULL,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  modifieddate timestamp with time zone NOT NULL DEFAULT now(),
  createduser bigint,
  modifieduser bigint,
  activeflag boolean DEFAULT true,  
  CONSTRAINT testcutscores_id_fk PRIMARY KEY (id),
  CONSTRAINT testcutscores_testid_fk FOREIGN KEY (testid)
      REFERENCES test (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testcutscores_userreportuploadid_fk FOREIGN KEY (userreportuploadid)
      REFERENCES userreportupload (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

