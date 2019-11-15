--ddl/744.sql F725 ISMART

ALTER TABLE complexityband ALTER COLUMN assessmentprogramid SET NOT NULL;
ALTER TABLE complexityband DROP CONSTRAINT IF EXISTS complexityband_pkey;
ALTER TABLE complexityband ADD CONSTRAINT complexityband_pkey PRIMARY KEY (id, bandcode, assessmentprogramid);
