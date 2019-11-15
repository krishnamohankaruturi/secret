--ddl/579.sql--/ddl/579.sql
CREATE TABLE testspecstatementofpurpose
(
  testspecificationid bigint NOT NULL,
  statementofpurposeid bigint NOT NULL,
  activeflag boolean DEFAULT true,
  CONSTRAINT testspecxstatementofpurpose_testspecificationid_fk1 FOREIGN KEY (testspecificationid)
      REFERENCES testspecification (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testspecxtypeofreporting_statementofpurposeid_fk2 FOREIGN KEY (statementofpurposeid)
      REFERENCES category (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
