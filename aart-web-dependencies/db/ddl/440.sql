--440.sql

--us16550
ALTER TABLE groups ADD groupcode character varying(50);

--US16235: Reports - Reports UI - enhance control over whether report is listed
drop table if exists reportassessmentprogram;
CREATE TABLE reportassessmentprogram 
(
  id bigserial NOT NULL,
  reporttypeid bigint NOT NULL,
  assessmentprogramid bigint NOT NULL,
  createdate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  readytoview boolean DEFAULT false,
  activeflag boolean DEFAULT true,
  CONSTRAINT pk_reportassessmentprogram PRIMARY KEY (id),
  CONSTRAINT reportassessmentprogram_assessmentprogramid_fk FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT reportassessmentprogram_reporttypeid_fk FOREIGN KEY (reporttypeid)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

--US16356
ALTER TABLE organizationreportdetails ALTER COLUMN contentareaid DROP NOT NULL;

ALTER TABLE organizationreportdetails ALTER COLUMN gradeid DROP NOT NULL;

--EP DB Schema For CB Test Panel US16486, US16474, US16487 & US16500
CREATE TABLE testpanel
(
  id bigint NOT NULL,
  externalid bigint,
  createdate timestamp with time zone,
 modifieddate timestamp with time zone,
  groupid bigint,
  companyid bigint,
  inuse boolean,
  status bigint,
  panelname character varying(100),
  organizationid bigint,
  contentareaid bigint,
  gradecourseid bigint,
  gradetype boolean,
  gradebandid bigint,
  testingprogramid bigint,
  testpopulationid bigint,
  modifiedusername character varying(75),
  createusername character varying(75),
  createuserid bigint,
  modifieduserid bigint,
  CONSTRAINT testpanel_pkey PRIMARY KEY (id),
  CONSTRAINT testpanel_fk1 FOREIGN KEY (testingprogramid)
      REFERENCES testingprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testpanel_fk2 FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testpanelfk3 FOREIGN KEY (gradecourseid)
      REFERENCES gradecourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testpanel_fk4 FOREIGN KEY (gradebandid)
      REFERENCES gradeband (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE testpanelstage(
                id bigint NOT NULL,
                externalid bigint,
                testpanelid bigint NOT NULL,
                stageid bigint NOT NULL,
                inuse boolean,
                CONSTRAINT testpanelstage_pkey PRIMARY KEY (id),
                CONSTRAINT testpanel_fk1 FOREIGN KEY (testpanelid)
                                REFERENCES testpanel (id) MATCH FULL
                                ON UPDATE NO ACTION ON DELETE NO ACTION,
                CONSTRAINT stage_fk1 FOREIGN KEY (stageid)
                                REFERENCES stage (id) MATCH SIMPLE
                                ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE testpanelstagetestcollection
(
  id bigint NOT NULL,
  externalid bigint,
  testpanelstageid bigint NOT NULL,
  testcollectionid bigint,
  inuse boolean,
  createuserid bigint,
  createusername character varying(256),
  createdate timestamp with time zone,
  modifieduserid bigint,
  modifiedusername character varying(256),
  modifieddate timestamp with time zone,
  CONSTRAINT testpanelstagetestcollection_pkey PRIMARY KEY (id),
  CONSTRAINT testpanelstagetestcollection_fk1 FOREIGN KEY (testcollectionid)
      REFERENCES testcollection (id) MATCH SIMPLE
     ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testpanelstagetestcollection_fk2 FOREIGN KEY (testpanelstageid)
      REFERENCES testpanelstage (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE testpanelstagemapping
(
  id bigint NOT NULL,
  externalid bigint,
  testpanelstageid bigint NOT NULL,
  interimtheta1 double precision,
  interimtheta2 double precision,
  testid1 bigint,
  testid2 bigint,
  testid3 bigint,
  createuserid bigint,
  createusername character varying(256),
  createdate timestamp with time zone,
  modifieduserid bigint,
  modifiedusername character varying(256),
  modifieddate timestamp with time zone,
  inuse boolean,
  CONSTRAINT testpanelstagemapping_pkey PRIMARY KEY (id),
  CONSTRAINT testpanelstagemapping_fk1 FOREIGN KEY (testpanelstageid)
      REFERENCES testpanelstage (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testpanelstagemapping_fk2 FOREIGN KEY (testid1)
      REFERENCES test (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testpanelstagemapping_fk3 FOREIGN KEY (testid2)
      REFERENCES test (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testpanelstagemapping_fk4 FOREIGN KEY (testid3)
      REFERENCES test(id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE testpanel RENAME testpopulationid to assessmentid;
ALTER TABLE testpanel ADD CONSTRAINT testpanel_fk5 FOREIGN KEY (assessmentid)
      REFERENCES assessment (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
