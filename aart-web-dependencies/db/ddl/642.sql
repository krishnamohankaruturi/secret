-- F396 KAP Reports 2017
-- Upload subscore default stage ids

ALTER TABLE subscoresmissingstages ADD COLUMN batchuploadid BIGINT;

DROP INDEX IF EXISTS idx_subscoresmissingstages_assessmentprogramid;
CREATE INDEX idx_subscoresmissingstages_assessmentprogramid ON subscoresmissingstages USING btree(assessmentprogramid);

DROP INDEX IF EXISTS idx_subscoresmissingstages_subjectid;
CREATE INDEX idx_subscoresmissingstages_subjectid ON subscoresmissingstages USING btree(subjectid);

DROP INDEX IF EXISTS idx_subscoresmissingstages_gradeid;
CREATE INDEX idx_subscoresmissingstages_gradeid ON subscoresmissingstages USING btree(gradeid);

DROP INDEX IF EXISTS idx_subscoresmissingstages_schoolyear;
CREATE INDEX idx_subscoresmissingstages_schoolyear ON subscoresmissingstages USING btree(schoolyear);

DROP INDEX IF EXISTS idx_subscoresmissingstages_batchuploadid;
CREATE INDEX idx_subscoresmissingstages_batchuploadid ON subscoresmissingstages USING btree(batchuploadid);
