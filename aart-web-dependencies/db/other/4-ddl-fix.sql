--Integration related changes.
--this needs to be run before build button is selected.

ALTER TABLE assessment DROP COLUMN assessmentprogramid;
ALTER TABLE assessment DROP COLUMN testtypeid;
ALTER TABLE assessment DROP COLUMN testsubjectid; 

--delete the assessments so that it can be recreated with proper constraints.
--It is ok to do so because production at this time has no means of creating tests and assessments.
-- or add something with default, update it and drop the constraint..? This is better TODO.


ALTER TABLE assessment
	DROP CONSTRAINT assessment_testsubjectid_fkey;

ALTER TABLE assessment
	DROP CONSTRAINT test_type_fk;

ALTER TABLE assessment
	DROP COLUMN testtypeid,
	DROP COLUMN testsubjectid,
	ADD COLUMN assessmentcode character varying(75),
	ADD COLUMN assessmentdescription character varying(75);
	
ALTER TABLE restrictionsorganizations
	ALTER COLUMN restrictionid DROP DEFAULT;

ALTER TABLE assessment
	ADD CONSTRAINT uk_assessment_name_testing_program UNIQUE (assessmentname, testingprogramid);

ALTER TABLE assessmentprogram
	ADD CONSTRAINT uk_program_name UNIQUE (programname);

--This is needs to be executed after the data gets inserted or modified correctly.
	

update assessment set testingprogramid = (select id from testingprogram limit 1);

--if assessment name is incorrect, then the data needs to be deleted because it is junk data.
delete from studentsassessments where assessmentid in (select id from assessment where assessmentname is null);

delete from assessment where assessmentname is null;

--update the codes so that the not null constraints can be added.
update assessment set assessmentcode=assessmentname||'Code' where assessmentcode is null;

update assessment set assessmentdescription=' ' where assessmentdescription is null;

ALTER TABLE assessment
	ALTER COLUMN assessmentname SET NOT NULL,
	ALTER COLUMN testingprogramid SET NOT NULL,
	ALTER COLUMN assessmentcode SET NOT NULL,
	ALTER COLUMN assessmentdescription SET NOT NULL;

ALTER TABLE assessment
	ADD CONSTRAINT uk_assessment_code_testing_program UNIQUE
	(assessmentcode, testingprogramid);

ALTER TABLE testingprogram
	ADD CONSTRAINT uk_testingprogram_assessmentprogram
	UNIQUE (programname, assessmentprogramid);