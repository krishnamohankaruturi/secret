--empty for dml/639.sql
DROP INDEX IF EXISTS idx_studentstests_transferedtestsessionid;

CREATE INDEX idx_studentstests_transferedtestsessionid
ON studentstests
USING btree (transferedtestsessionid);
