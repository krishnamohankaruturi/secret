--US15831 : Manage Accountability(AYP) school correctly on KSDE Test records
/*
ALTER TABLE enrollment ALTER COLUMN aypSchoolId SET NOT NULL;

ALTER TABLE enrollment DROP CONSTRAINT enrollment_uk;

ALTER TABLE enrollment ADD CONSTRAINT enrollment_uk UNIQUE (studentid, attendanceschoolid, currentschoolyear, aypschoolid); */
