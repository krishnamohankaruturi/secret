--US15857 : Technical: SQL Improvements
--Check EnrollmentDao.xml  : getTECExtractByOrg query perfomance after added index.
DROP INDEX IF EXISTS idx_testtype_testtypecode;
CREATE INDEX idx_testtype_testtypecode
  ON testtype
  USING btree (testtypecode);