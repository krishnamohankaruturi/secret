--334.sql
ALTER TABLE testsession ADD COLUMN attendanceschoolid bigint;

ALTER TABLE testsession
 ADD CONSTRAINT testsession_attendanceschoolid_fk 
FOREIGN KEY (attendanceschoolid) REFERENCES organization (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

DROP INDEX IF EXISTS idx_testsession_attendanceschoolid;
CREATE INDEX idx_testsession_attendanceschoolid ON testsession USING btree (attendanceschoolid);

ALTER TABLE testsession ADD COLUMN operationaltestwindowid bigint;

ALTER TABLE testsession
 ADD CONSTRAINT testsession_operationaltestwindowid_fk 
FOREIGN KEY (operationaltestwindowid) REFERENCES operationaltestwindow (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

DROP INDEX IF EXISTS idx_testsession_operationaltestwindowid;
CREATE INDEX idx_testsession_operationaltestwindowid ON testsession USING btree (operationaltestwindowid);

ALTER TABLE student ADD COLUMN stateid bigint;

ALTER TABLE student
 ADD CONSTRAINT student_stateid_fk 
FOREIGN KEY (stateid) REFERENCES organization (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;