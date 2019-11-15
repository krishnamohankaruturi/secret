-- 340.sql
ALTER TABLE batchregistrationreason ALTER COLUMN reason TYPE text;

DROP INDEX IF EXISTS idx_testsession_status;
CREATE INDEX idx_testsession_status ON testsession USING btree (status);

DROP INDEX IF EXISTS idx_testsession_roster;
CREATE INDEX idx_testsession_roster ON testsession USING btree (rosterid);

DROP INDEX IF EXISTS idx_ititestsessionhistory_testsession;
CREATE INDEX idx_ititestsessionhistory_testsession ON ititestsessionhistory USING btree (testsessionid);

DROP INDEX IF EXISTS idx_ititestsessionhistory_roster;
CREATE INDEX idx_ititestsessionhistory_roster ON ititestsessionhistory USING btree (rosterid);

DROP INDEX IF EXISTS idx_operationaltestwindow_suspendwindow;
CREATE INDEX idx_operationaltestwindow_suspendwindow ON operationaltestwindow USING btree (suspendwindow);

DROP INDEX IF EXISTS idx_operationaltestwindow_effectivedate;
CREATE INDEX idx_operationaltestwindow_effectivedate ON operationaltestwindow USING btree (effectivedate);

DROP INDEX IF EXISTS idx_operationaltestwindow_expirydate;
CREATE INDEX idx_operationaltestwindow_expirydate ON operationaltestwindow USING btree (expirydate);

DROP INDEX IF EXISTS idx_taskvariantlearningmapnode_tvid;
CREATE INDEX idx_taskvariantlearningmapnode_tvid ON taskvariantlearningmapnode USING btree (taskvariantid);

DROP INDEX IF EXISTS idx_enrollmentsrosters_trackerstatus;
CREATE INDEX idx_enrollmentsrosters_trackerstatus ON enrollmentsrosters USING btree (trackerstatus);