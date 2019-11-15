DROP INDEX IF EXISTS idx_roster_restrictionid;  
CREATE INDEX idx_roster_restrictionid 
  ON roster USING btree (restrictionid); 

DROP INDEX IF EXISTS idx_profileitemattribute_attributename;  
CREATE INDEX idx_profileitemattribute_attributename 
  ON profileitemattribute USING btree (attributename); 
  
delete from studentsteststags;
ALTER TABLE studentsteststags DROP CONSTRAINT studentsteststags_pkey;

ALTER TABLE studentsteststags
  ADD CONSTRAINT studentsteststags_pkey PRIMARY KEY(studenttestid);

ALTER TABLE studentsteststags DROP COLUMN testletid;

ALTER TABLE itemstatistic ADD COLUMN assessmentprogramid bigint;
ALTER TABLE itemstatistic ADD COLUMN testid bigint;
ALTER TABLE itemstatistic ADD CONSTRAINT itemstatistic_assessmentprogramid_fkey 
	FOREIGN KEY (assessmentprogramid) REFERENCES assessmentprogram (id);
	
ALTER TABLE itemstatistic ADD CONSTRAINT itemstatistic_testid_fkey 
	FOREIGN KEY (testid) REFERENCES test(id);
	