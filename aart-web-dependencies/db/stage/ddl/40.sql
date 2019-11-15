--ddl/40.sql
--F570 Interim predictive
--Batch Report file upload info : this goes into audit database
ALTER TABLE batchupload ADD COLUMN testingprogramid bigint,
						ADD COLUMN testingprogramname text,
						ADD COLUMN reportcycle text;

DROP INDEX IF EXISTS idx_batchupload_testingprogramid;
CREATE INDEX idx_batchupload_testingprogramid
  ON batchupload
  USING btree
  (testingprogramid);
  
DROP INDEX IF EXISTS idx_batchupload_testingprogramname;
CREATE INDEX idx_batchupload_testingprogramname
  ON batchupload
  USING btree
  (testingprogramname COLLATE pg_catalog."default");
  
DROP INDEX IF EXISTS idx_batchupload_reportcycle;
CREATE INDEX idx_batchupload_reportcycle
  ON batchupload
  USING btree
  (reportcycle COLLATE pg_catalog."default");
