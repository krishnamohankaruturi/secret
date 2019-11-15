
-- 341.sql
DROP INDEX IF EXISTS idx_student_stateid;
CREATE INDEX idx_student_stateid ON student USING btree (stateid);

DROP INDEX IF EXISTS idx_usersecurityagreement_aartuserid;
CREATE INDEX idx_usersecurityagreement_aartuserid ON usersecurityagreement USING btree (aartuserid);

DROP INDEX IF EXISTS idx_usersecurityagreement_assessmentprogramid;
CREATE INDEX idx_usersecurityagreement_assessmentprogramid ON usersecurityagreement USING btree (assessmentprogramid);

DROP INDEX IF EXISTS idx_roster_currentschoolyear;
CREATE INDEX idx_roster_currentschoolyear ON roster USING btree (currentschoolyear);

DROP INDEX IF EXISTS idx_testsession_rosterid;
CREATE INDEX idx_testsession_rosterid ON testsession USING btree (rosterid);
