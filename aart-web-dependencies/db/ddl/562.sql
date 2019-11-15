--For ddl/*.sql
--script bees US17776
ALTER TABLE groupauthorities ADD organizationid BIGINT, ADD assessmentprogramid BIGINT;

ALTER TABLE groupauthorities ADD CONSTRAINT fk_group_authorities_organization
 FOREIGN KEY (organizationid) REFERENCES organization (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE groupauthorities ADD CONSTRAINT fk_group_authorities_assessmentprogram
 FOREIGN KEY (assessmentprogramid) REFERENCES assessmentprogram (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE INDEX groupauthorities_csap_idx ON groupauthorities USING btree
  (groupid, organizationid, assessmentprogramid, activeflag);
  