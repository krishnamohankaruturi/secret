-- US15915 : Test coordination extend window for test session
-- Creating new column windowexpirydate on Testsession table, and adding index to windowexpirydate

ALTER TABLE testsession ADD COLUMN windowexpirydate timestamp with time zone;

DROP INDEX IF EXISTS idx_testsession_windowexpirydate;
CREATE INDEX idx_testsession_windowexpirydate ON testsession USING btree (windowexpirydate);
