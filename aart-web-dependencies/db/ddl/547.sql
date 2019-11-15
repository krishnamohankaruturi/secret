--For dml/*.sql
--US17654: Reports: Upload subscore raw score enhancements for 2016
--Removal of 2 columns: Scale Score, Standard Error
--Addition of 5 columns: TestID3, TestID4, PerformanceTestID, Rating, Minimum Percent Responses


alter table subscoresrawtoscale drop column scalescore;
alter table subscoresrawtoscale drop column standarderror;
DROP INDEX IF EXISTS idx_subscoresrawtoscale_scalescore;
DROP INDEX IF EXISTS idx_subscoresrawtoscale_standarderror;


--As we need to add columns which will be NOT NULL
delete from subscoresrawtoscale; 

alter table subscoresrawtoscale add column testid3 bigint;
alter table subscoresrawtoscale add column testid4 bigint;
alter table subscoresrawtoscale add column performancetestid bigint;
alter table subscoresrawtoscale add column rating int NOT NULL;
alter table subscoresrawtoscale add column minimumpercentresponses numeric(3,2) NOT NULL;

ALTER TABLE reportsubscores ADD COLUMN rating integer;

