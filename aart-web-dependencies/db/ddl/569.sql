-- Adding indexes
DROP INDEX IF EXISTS idx_studentspecialcircumstance_studenttestid;
CREATE INDEX idx_studentspecialcircumstance_studenttestid ON studentspecialcircumstance USING btree (studenttestid);
DROP INDEX IF EXISTS idx_studentspecialcircumstance_specialcircumstanceid;
CREATE INDEX idx_studentspecialcircumstance_specialcircumstanceid ON studentspecialcircumstance USING btree (specialcircumstanceid);
DROP INDEX IF EXISTS idx_specialcircumstance_id;
CREATE INDEX idx_specialcircumstance_id ON specialcircumstance USING btree(id);
DROP INDEX IF EXISTS idx_specialcircumstance_activeflag;
CREATE INDEX idx_specialcircumstance_activeflag ON specialcircumstance USING btree(activeflag);
DROP INDEX IF EXISTS idx_stage_code;
CREATE INDEX idx_stage_code ON stage USING btree(code);

-- script from scriptbees
-- To persist group restrictions by csap wise
CREATE TABLE grouprestrictions
(
  groupid bigint NOT NULL,
  organizationid bigint NOT NULL,
  assessmentprogramid bigint NOT NULL,
  singleuser boolean DEFAULT false,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser integer,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser integer,
  activeflag boolean DEFAULT true,
  CONSTRAINT grouprestrctions_assessmentprogramid_fk FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT grouprestrctions_groupid_fk FOREIGN KEY (groupid)
      REFERENCES groups (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT grouprestrctions_organizationid_fk FOREIGN KEY (organizationid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX grouprestrictions_csap ON grouprestrictions 
   (groupid ASC NULLS LAST, organizationid ASC NULLS LAST, assessmentprogramid ASC NULLS LAST, activeflag ASC NULLS LAST);