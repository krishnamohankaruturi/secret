-- US15536: Student Tracker - Simple Version 1 (preliminary)

ALTER TABLE enrollmentsrosters ADD COLUMN trackerstatus VARCHAR DEFAULT 'UNTRACKED';

-- Sequence: proportionmetrics_id_seq

-- DROP SEQUENCE proportionmetrics_id_seq;

CREATE SEQUENCE proportionmetrics_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

-- Table: proportionmetrics

-- DROP TABLE proportionmetrics;

CREATE TABLE proportionmetrics
(
  id bigint NOT NULL DEFAULT nextval('proportionmetrics_id_seq'::regclass),
  gradeid bigint,
  conceptualareaid bigint,
  essentialelementid bigint,
  conceptualarea character varying(100),
  essentialelement character varying(100),  
  eedescription text,
  linkagelevelabbr character varying(75),
  proportionlow numeric(3,2),
  proportionhigh numeric(3,2),
  createdate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp without time zone,
  modifieddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp without time zone,
  active boolean DEFAULT TRUE,
  
  CONSTRAINT proportionmetrics_pkey PRIMARY KEY (id),
  CONSTRAINT proportionmetrics_gradeid_fkey FOREIGN KEY (gradeid)
      REFERENCES gradecourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT proportionmetrics_conceptualareaid_fkey FOREIGN KEY (conceptualareaid)
      REFERENCES contentframeworkdetail (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT proportionmetrics_essentialelementid_fkey FOREIGN KEY (essentialelementid)
      REFERENCES contentframeworkdetail (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Index: idx_proportionmetrics_gradeid
-- DROP INDEX idx_proportionmetrics_gradeid;

CREATE INDEX idx_proportionmetrics_gradeid
  ON proportionmetrics USING btree (gradeid);
  
-- Index: idx_proportionmetrics_essentialelementid
-- DROP INDEX idx_proportionmetrics_essentialelementid;

CREATE INDEX idx_proportionmetrics_essentialelementid
  ON proportionmetrics USING btree (essentialelementid);  

-- Index: idx_proportionmetrics_essentialelementid
-- DROP INDEX idx_proportionmetrics_essentialelementid;

CREATE INDEX idx_proportionmetrics_essentialelement
  ON proportionmetrics USING btree (essentialelement);  


CREATE SEQUENCE studentbandrecommendation_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

-- Table: studentbandrecommendation

-- DROP TABLE studentbandrecommendation;

CREATE TABLE studentbandrecommendation
(
  id bigint NOT NULL DEFAULT nextval('studentbandrecommendation_id_seq'::regclass),
  studentid bigint NOT NULL,
  testid bigint,
  testcollectionid bigint,
  contentareaid bigint,
  usedbandid bigint,
  usedessentialelementid bigint,
  usedessentialelement character varying(100),
  recolinkagelevel character varying(30),
  recofinalband bigint,
  source character varying(100),
  createdate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp without time zone,
  modifieddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp without time zone,
  active boolean DEFAULT TRUE,
  
  CONSTRAINT studentbandrecommendation_pkey PRIMARY KEY (id),
  CONSTRAINT studentbandrecommendation_studentid_fkey FOREIGN KEY (studentid)
      REFERENCES student (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT studentbandrecommendation_testid_fkey FOREIGN KEY (testid)
      REFERENCES test (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,

  CONSTRAINT studentbandrecommendation_testcollectionid_fkey FOREIGN KEY (testcollectionid)
      REFERENCES testcollection (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,

  CONSTRAINT studentbandrecommendation_contentareaid_fkey FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,

  CONSTRAINT studentbandrecommendation_usedbandid_fkey FOREIGN KEY (usedbandid)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,    
              
  CONSTRAINT studentbandrecommendation_usedessentialelementid_fkey FOREIGN KEY (usedessentialelementid)
      REFERENCES contentframeworkdetail (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
      
  CONSTRAINT studentbandrecommendation_recofinalband_fkey FOREIGN KEY (recofinalband)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION     
);

CREATE TABLE testtypeassessment
(
  testtypeid bigint NOT NULL,
  assessmentid bigint NOT NULL,
  createduser integer,
  createdate timestamp with time zone,
  modifieddate timestamp with time zone,
  modifieduser integer,
  activeflag boolean DEFAULT true,
  CONSTRAINT pk_testtypeassessment_id PRIMARY KEY (testtypeid, assessmentid),
  CONSTRAINT fk_testtypeassessment_assessmentid FOREIGN KEY (assessmentid)
      REFERENCES assessment (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_testtypeassessment_testtypeid FOREIGN KEY (testtypeid)
      REFERENCES testtype (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE assessment ADD COLUMN autoenrollmentflag boolean;
ALTER TABLE assessment ALTER COLUMN autoenrollmentflag SET DEFAULT false;