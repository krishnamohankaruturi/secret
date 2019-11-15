--295.sql

DROP INDEX IF EXISTS idx_studentstests_studentid;


CREATE INDEX idx_studentstests_testsession
  ON studentstests USING btree (testsessionid);

CREATE INDEX idx_studentstests_enrollment
  ON studentstests USING btree (enrollmentid);

CREATE INDEX idx_testsession_test
  ON testsession USING btree (testid);

CREATE INDEX idx_testsession_testcollection
  ON testsession USING btree (testcollectionid);

CREATE INDEX idx_studentsresponses_studentstests
  ON studentsresponses  USING btree (studentstestsid);

CREATE INDEX idx_category_categorytype
  ON category USING btree (categorytypeid);