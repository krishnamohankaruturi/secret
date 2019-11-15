--648.sql DDL for F418,F458,F459
CREATE TABLE datadictionaryfilemapping
(
  id bigserial NOT NULL,
  extracttypeid bigint,
  assessmentprogramid bigint,
  stateid bigint,
  filename character varying(200),
  filelocation character varying(200),
  activeflag boolean DEFAULT true,
  specialdatadetailfilelocation character varying(200),
  specialdatadetailfilename character varying(200),
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser bigint NOT NULL,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieduser bigint NOT NULL,
  CONSTRAINT pk_datadictionaryfilemapping PRIMARY KEY (id),
  CONSTRAINT fk_datadictionaryfilemapping_assessmentprogramid FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX idx1_datadictionaryfilemapping   ON datadictionaryfilemapping  USING btree  (extracttypeid);
CREATE INDEX idx2_datadictionaryfilemapping   ON datadictionaryfilemapping  USING btree  (assessmentprogramid);
CREATE INDEX idx3_datadictionaryfilemapping   ON datadictionaryfilemapping  USING btree  (id);
CREATE INDEX idx4_datadictionaryfilemapping   ON datadictionaryfilemapping  USING btree  (stateid);


------   F459 DDL   ------

CREATE TABLE organizationgrfcalculation
(
  id bigint NOT NULL,
  assessmentprogramid bigint NOT NULL,
  batchuploadprocessid bigint NOT NULL,
  stateid bigint NOT NULL,
  statename character varying(100),
  districtid bigint,
  districtcode character varying(100),
  districtname character varying(100),
  reportyear bigint NOT NULL,  
  subjectid bigint NOT NULL,
  subjectname character varying(100),
  gradeid bigint NOT NULL,
  gradelevel character varying(100),
  noofstudentstested bigint NOT NULL,
  numberofemerging bigint NOT NULL,
  numberofapproachingtarget bigint NOT NULL,
  numberofattarget bigint NOT NULL,
  numberofadvanced bigint NOT NULL,
  percentageattargetadvanced integer NOT NULL,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieduser integer NOT NULL,
  CONSTRAINT pk_organization_summary_grf_calculation PRIMARY KEY (id),
  CONSTRAINT fk_organizationsummarygrfcalculation_crdusr FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_organizationsummarygrfcalculation_updusr FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE SEQUENCE organizationgrfcalculation_id_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE INDEX idx1_organizationgrfcalculation  ON organizationgrfcalculation USING btree(id);
CREATE INDEX idx2_organizationgrfcalculation  ON organizationgrfcalculation USING btree(assessmentprogramid);
CREATE INDEX idx3_organizationgrfcalculation  ON organizationgrfcalculation USING btree(batchuploadprocessid);
CREATE INDEX idx4_organizationgrfcalculation  ON organizationgrfcalculation USING btree(stateid);
CREATE INDEX idx5_organizationgrfcalculation  ON organizationgrfcalculation USING btree(districtid);
CREATE INDEX idx6_organizationgrfcalculation  ON organizationgrfcalculation USING btree(reportyear);
CREATE INDEX idx7_organizationgrfcalculation  ON organizationgrfcalculation USING btree(subjectid);
CREATE INDEX idx8_organizationgrfcalculation  ON organizationgrfcalculation USING btree(gradeid);

ALTER TABLE organizationreportdetails ADD COLUMN summaryreportcsvpath text;

