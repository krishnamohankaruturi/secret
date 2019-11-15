--468.sql
alter table ccqscore drop column if exists scoringassignmentid;
alter table student drop column if exists assessmentprogramid;

DROP INDEX IF EXISTS idx_excludeditems_schoolyear;
CREATE INDEX idx_excludeditems_schoolyear ON excludeditems USING btree (schoolyear);

DROP INDEX IF EXISTS idx_excludeditems_gradeid;
CREATE INDEX idx_excludeditems_gradeid ON excludeditems USING btree (gradeid);

DROP INDEX IF EXISTS idx_excludeditems_assessmentprogramid;
CREATE INDEX idx_excludeditems_assessmentprogramid ON excludeditems USING btree (assessmentprogramid);

DROP INDEX IF EXISTS idx_taskvariant_externalid;
CREATE INDEX idx_taskvariant_externalid ON taskvariant USING btree (externalid);

DROP INDEX IF EXISTS idx_studentstests_activeflag;
CREATE INDEX idx_studentstests_activeflag ON studentstests USING btree (activeflag);
