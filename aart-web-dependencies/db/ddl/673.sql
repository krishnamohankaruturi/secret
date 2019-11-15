--dml/673.sql
alter table modulereport add column completedtime timestamp with time zone;

DROP INDEX IF EXISTS idx_studentsurveyresponse_surveyid;

CREATE INDEX idx_studentsurveyresponse_surveyid
  ON studentsurveyresponse
  USING btree
  (surveyid);
