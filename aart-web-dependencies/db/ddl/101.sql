
--US12929 : Professional Development: Define a module using a test imported from CB

ALTER TABLE module ADD COLUMN testid bigint;
ALTER TABLE module ADD CONSTRAINT uk_module_name_assessmentprogramid UNIQUE(assessmentprogramid,name);

