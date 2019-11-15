--453.sql
ALTER TABLE operationaltestwindow DROP COLUMN createddate;
ALTER TABLE operationaltestwindow ADD COLUMN createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone;

ALTER TABLE studentsurveyresponse DROP CONSTRAINT uk_student_survey_response;

ALTER TABLE studentsurveyresponse
  ADD CONSTRAINT uk_student_survey_response UNIQUE(surveyid, surveyresponseid, activeflag);
  
CREATE INDEX idx_studentreport_studentid ON studentreport USING btree (studentid);
CREATE INDEX idx_studentreport_enrollmentid ON studentreport USING btree (enrollmentid);
CREATE INDEX idx_studentreport_gradeid ON studentreport USING btree (gradeid);
CREATE INDEX idx_studentreport_contentareaid ON studentreport USING btree (contentareaid);
CREATE INDEX idx_studentreport_studenttest1id ON studentreport USING btree (studenttest1id);
CREATE INDEX idx_studentreport_studenttest2id ON studentreport USING btree (studenttest2id);

