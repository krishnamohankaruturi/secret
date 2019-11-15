
--empty for dml/208.sql
--for removeal of duplicate stdents
ALTER TABLE studentsresponses_aart DROP CONSTRAINT studentsresponses_studentid_fkey;
ALTER TABLE studentsresponses_aart DROP CONSTRAINT studentsresponses_studentstestsid_fkey;
ALTER TABLE studentstestsections_aart DROP CONSTRAINT fk_studentstestsections_studentstestid;