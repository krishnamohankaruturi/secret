--For dml/542.sql

--DE13220: Enrollment and Roster extracts' query optimizations
DROP INDEX IF EXISTS idx_enrollment_modifieduser;
CREATE INDEX idx_enrollment_modifieduser
  ON enrollment
USING btree(modifieduser);

DROP INDEX IF EXISTS idx_studentassessmentprogram_activeflag;
CREATE INDEX idx_studentassessmentprogram_activeflag
  ON studentassessmentprogram
  USING btree(activeflag)
  WHERE activeflag is true;
  
DROP INDEX IF EXISTS idx_enrollmentsrosters_modifieduser;
CREATE INDEX idx_enrollmentsrosters_modifieduser
  ON enrollmentsrosters
  USING btree(modifieduser);

