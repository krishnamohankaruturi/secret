
--changes for CB
ALTER TABLE test DROP COLUMN IF EXISTS subsectionselectionmodeltypecode;
ALTER TABLE test DROP COLUMN IF EXISTS unidimentionalmodeltypecode;

ALTER TABLE test ADD COLUMN convergencecriterionvalue decimal;
ALTER TABLE test ADD COLUMN maxiterationsvalue smallint;
ALTER TABLE test ADD COLUMN maxthetavalue decimal;
ALTER TABLE test ADD COLUMN maxthetachangevalue decimal;
ALTER TABLE test ADD COLUMN minthetavalue decimal;
ALTER TABLE test ADD COLUMN minthetachangevalue decimal;

ALTER TABLE testconstruct ADD COLUMN meanvalue decimal;
ALTER TABLE testconstruct ADD COLUMN covariancevalue decimal;

CREATE SEQUENCE gradeband_id_seq
                INCREMENT 1
                MINVALUE 1
                MAXVALUE 9223372036854775807
                START 1;

CREATE TABLE gradeband
(
  id bigint NOT NULL DEFAULT nextval('gradeband_id_seq'::regclass),
  externalid bigint,
  sortorder bigint,
  name character varying(100) NOT NULL,
  abbreviatedname character varying(75),
  createdate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  originationcode character varying(20),
  createduser integer,
  activeflag boolean DEFAULT true,
  modifieduser integer,
  contentareaid bigint,
  CONSTRAINT gradeband_pkey PRIMARY KEY (id),
  CONSTRAINT gradeband_code_contentarea_uk UNIQUE (abbreviatedname, activeflag, contentareaid),
  CONSTRAINT gradeband_contentarea_fk FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


                
CREATE TABLE gradebandgradecourse
(
  gradebandid bigint NOT NULL,
  gradecourseid bigint,
  CONSTRAINT gradebandgradecourse_uk UNIQUE (gradebandid, gradecourseid),
  CONSTRAINT gradebandgradecourse_gradeband_fk FOREIGN KEY (gradebandid)
      REFERENCES gradeband (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT gradebandgradecourse_gradecourse_fk FOREIGN KEY (gradecourseid)
      REFERENCES gradecourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION        
);

CREATE TABLE testpriorparameter
(
  testpriorparameterid bigint NOT NULL,
  testid bigint,
  xtestconstructid bigint NOT NULL,
  ytestconstructid bigint NOT NULL,
  covariancematrixvalue decimal,
  CONSTRAINT testpriorparameter_pkey PRIMARY KEY (testpriorparameterid),
  CONSTRAINT testpriorparameter_fk1 FOREIGN KEY (testid)
      REFERENCES test (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,  
  CONSTRAINT testpriorparameter_fk2 FOREIGN KEY (xtestconstructid)
      REFERENCES testconstruct (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testpriorparameter_fk3 FOREIGN KEY (ytestconstructid)
      REFERENCES testconstruct (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE testpriorparameter_id_seq
                INCREMENT 1
                MINVALUE 1
                MAXVALUE 9223372036854775807
                START 1;

ALTER TABLE taskvariant ADD COLUMN deliveryformattype character varying(30);

ALTER table test ADD COLUMN gradetype boolean;
ALTER table test ADD COLUMN gradebandid bigint;

ALTER TABLE test
  ADD CONSTRAINT test_gradebandid_fkey FOREIGN KEY (gradebandid)
      REFERENCES gradeband (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION;