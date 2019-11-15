-- DE9078 - US15874_Counts returned are not based on date selected from picker.
-- Creating index for modified date on studentstests table

DROP INDEX IF EXISTS idx_studentstests_modifieddate;
CREATE INDEX idx_studentstests_modifieddate ON studentstests USING btree (modifieddate);

DROP INDEX IF EXISTS idx_aartuser_password;
CREATE INDEX idx_aartuser_password ON aartuser USING btree (password);  

DROP INDEX IF EXISTS idx_student_password;
CREATE INDEX idx_student_password ON student USING btree (password); 

DROP INDEX IF EXISTS idx_studentstestsections_studentstestid_testpartid;
DROP INDEX IF EXISTS idx_studentstestsections_studentstestid_testsectionid;

DROP INDEX IF EXISTS idx_studentstestsections_studentstestid;
CREATE INDEX idx_studentstestsections_studentstestid ON studentstestsections USING btree (studentstestid);

DROP INDEX IF EXISTS idx_studentstestsections_testpartid;
CREATE INDEX idx_studentstestsections_testpartid ON studentstestsections USING btree (testpartid);

DROP INDEX IF EXISTS idx_studentstestsections_testsectionid;
CREATE INDEX idx_studentstestsections_testsectionid ON studentstestsections USING btree (testsectionid);