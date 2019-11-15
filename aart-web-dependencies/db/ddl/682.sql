--ddl/682.sql F638 - Interim Predictive School District Summary Reports

DROP TABLE IF EXISTS predictivereportcreditpercent;
CREATE TABLE predictivereportcreditpercent
(
  id bigserial NOT NULL,
  schoolyear bigint,
  assessmentprogramid bigint,
  testingprogramid bigint,
  reportcycle text,
  organizationid bigint,
  organizationtypeid bigint,
  gradeid bigint,
  contentareaid bigint,
  testid bigint,
  externaltestid bigint,
  taskvariantid bigint,
  taskvariantexternalid bigint,
  taskvariantposition integer,
  questioninformationid bigint,
  credittypeid bigint,
  fullcreditpercent integer,
  fullcreditstudentcount integer,
  testattemptedstudentcount integer,
  unansweredstudentcount integer,  
  batchreportprocessid bigint NOT NULL,
  createduser bigint DEFAULT 12,
  modifieduser bigint DEFAULT 12,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  CONSTRAINT predictivereportcreditpercent_pkey PRIMARY KEY (id),
  CONSTRAINT predictivereportcreditpercent_batchreportprocessid_fk FOREIGN KEY (batchreportprocessid)
      REFERENCES reportprocess (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT predictivereportcreditpercent_organizationid_fk FOREIGN KEY (organizationid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT predictivereportcreditpercent_questioninformationid_fk FOREIGN KEY (questioninformationid)
      REFERENCES questioninformation (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT predictivereportcreditpercent_contentareaid_fk FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT predictivereportcreditpercent_gradeid_fk FOREIGN KEY (gradeid)
      REFERENCES gradecourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT predictivereportcreditpercent_credittypeid_fk FOREIGN KEY (credittypeid)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP INDEX IF EXISTS idx_predictivereportcreditpercent_assessmentprogramid;
CREATE INDEX idx_predictivereportcreditpercent_assessmentprogramid
  ON predictivereportcreditpercent
  USING btree
  (assessmentprogramid);
  
DROP INDEX IF EXISTS idx_predictivereportcreditpercent_testingprogramid;
CREATE INDEX idx_predictivereportcreditpercent_testingprogramid
  ON predictivereportcreditpercent
  USING btree
  (testingprogramid);

DROP INDEX IF EXISTS idx_predictivereportcreditpercent_schoolyear;
CREATE INDEX idx_predictivereportcreditpercent_schoolyear
  ON predictivereportcreditpercent
  USING btree
  (schoolyear);

DROP INDEX IF EXISTS idx_predictivereportcreditpercent_reportcycle;
CREATE INDEX idx_predictivereportcreditpercent_reportcycle
  ON predictivereportcreditpercent
  USING btree
  (reportcycle COLLATE pg_catalog."default");

DROP INDEX IF EXISTS idx_predictivereportcreditpercent_organizationid;
CREATE INDEX idx_predictivereportcreditpercent_organizationid
  ON predictivereportcreditpercent
  USING btree
  (organizationid);

DROP INDEX IF EXISTS idx_predictivereportcreditpercent_gradeid;
CREATE INDEX idx_predictivereportcreditpercent_gradeid
  ON predictivereportcreditpercent
  USING btree
  (gradeid);

DROP INDEX IF EXISTS idx_predictivereportcreditpercent_contentareaid;
CREATE INDEX idx_predictivereportcreditpercent_contentareaid
  ON predictivereportcreditpercent
  USING btree
  (contentareaid);

DROP INDEX IF EXISTS idx_interimstudentreport_gradeid;
CREATE INDEX idx_interimstudentreport_gradeid
  ON interimstudentreport
  USING btree
  (gradeid);

DROP INDEX IF EXISTS idx_interimstudentreport_contentareaid;
CREATE INDEX idx_interimstudentreport_contentareaid
  ON interimstudentreport
  USING btree
  (contentareaid);
  
 
