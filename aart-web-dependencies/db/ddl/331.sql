-- Index on student
DROP INDEX IF EXISTS idx_student_profilestatus;
CREATE INDEX idx_student_profilestatus ON student USING btree (profilestatus);
 