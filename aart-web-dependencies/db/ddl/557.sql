--ddl/557.sql

ALTER TABLE studentreport ADD CONSTRAINT studentreport_mdptlevelid_fk FOREIGN KEY (mdptlevelid)
      REFERENCES leveldescription (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
DROP INDEX IF EXISTS idx_studentreport_mdptlevelid;
CREATE INDEX idx_studentreport_mdptlevelid
  ON studentreport
  USING btree
  (mdptlevelid);
  
  
ALTER TABLE studentreport ADD CONSTRAINT studentreport_combinedlevelid_fk FOREIGN KEY (combinedlevelid)
      REFERENCES leveldescription (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
DROP INDEX IF EXISTS idx_studentreport_combinedlevelid;
CREATE INDEX idx_studentreport_combinedlevelid
  ON studentreport
  USING btree
  (combinedlevelid);