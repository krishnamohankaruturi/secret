--ddl/437.sql

DROP INDEX IF EXISTS idx_userassessmentprogram_aartuser;
CREATE INDEX idx_userassessmentprogram_aartuser ON userassessmentprogram USING btree(aartuserid);

DROP INDEX IF EXISTS idx_userassessmentprogram_assessmentprogram;
CREATE INDEX idx_userassessmentprogram_assessmentprogram ON userassessmentprogram USING btree(assessmentprogramid);

DROP INDEX IF EXISTS idx_studentassessmentprogram_student;
CREATE INDEX idx_studentassessmentprogram_student ON studentassessmentprogram USING btree (studentid);

DROP INDEX IF EXISTS idx_studentassessmentprogram_assessmentprogram;
CREATE INDEX idx_studentassessmentprogram_assessmentprogram ON studentassessmentprogram USING btree(assessmentprogramid);

DROP INDEX IF EXISTS idx_cmstate_comminicationmessage;
CREATE INDEX idx_cmstate_comminicationmessage ON communicationmessagestate USING btree(comminicationmessageid);

DROP INDEX IF EXISTS idx_cmstate_state;
CREATE INDEX idx_cmstate_state ON communicationmessagestate USING btree(stateid);

DROP INDEX IF EXISTS idx_communicationmessage_assessmentprogram;
CREATE INDEX idx_communicationmessage_assessmentprogram ON communicationmessage USING btree(assessmentprogramid);

DROP INDEX IF EXISTS idx_userpdtrainingdetail_user;
CREATE INDEX idx_userpdtrainingdetail_user ON userpdtrainingdetail USING btree(userid);

DROP INDEX IF EXISTS idx_userpdtrainingdetail_currentschoolyear;
CREATE INDEX idx_userpdtrainingdetail_currentschoolyear ON userpdtrainingdetail USING btree(currentschoolyear);

