--ddl/603.sql (For dml/603.sql)
ALTER TABLE testenrollmentmethod  ADD COLUMN methodtype VARCHAR;
ALTER TABLE operationaltestwindow ADD COLUMN scoringwindowflag BOOLEAN;
ALTER TABLE operationaltestwindow ADD COLUMN scoringwindowid BIGINT;
ALTER TABLE operationaltestwindow ADD COLUMN scoringwindowstartdate TIMESTAMP WITH TIME ZONE;
ALTER TABLE operationaltestwindow ADD COLUMN scoringwindowenddate TIMESTAMP WITH TIME ZONE;

