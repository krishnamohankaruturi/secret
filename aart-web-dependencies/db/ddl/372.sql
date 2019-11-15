--US15831 : Manage Accountability(AYP) school correctly on KSDE Test records
ALTER TABLE enrollment ADD COLUMN aypSchoolId bigint, ADD CONSTRAINT aypSchoolId_fk FOREIGN KEY(aypSchoolId) 
	REFERENCES organization (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION;
        
DROP INDEX IF EXISTS idx_enrollment_aypschoolid;
CREATE INDEX idx_enrollment_aypschoolid ON enrollment USING btree (aypschoolid);        
