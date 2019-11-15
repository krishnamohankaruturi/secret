--INFO CB related tables. original 28.sql
CREATE TABLE stimulusvariantcontentarea
(
  stimulusvariantid bigint NOT NULL,
  contentareaid bigint NOT NULL,
  CONSTRAINT stimulusvariantcontentarea_pkey PRIMARY KEY (stimulusvariantid , contentareaid ),
  CONSTRAINT stimulusvariantcontentarea_fk1 FOREIGN KEY (stimulusvariantid)
      REFERENCES stimulusvariant (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT stimulusvariantcontentarea_fk2 FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE stimulusvariantgradecourse
(
  stimulusvariantid bigint NOT NULL,
  gradecourseid bigint NOT NULL,
  CONSTRAINT stimulusvariantgradecourse_pkey PRIMARY KEY (stimulusvariantid , gradecourseid ),
  CONSTRAINT stimulusvariantgradecourse_fk1 FOREIGN KEY (stimulusvariantid)
      REFERENCES stimulusvariant (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT stimulusvariantgradecourse_fk2 FOREIGN KEY (gradecourseid)
      REFERENCES gradecourse (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE stimulusvarianttestingprogram
(
  stimulusvariantid bigint NOT NULL,
  testingprogramid bigint NOT NULL,
  CONSTRAINT stimulusvarianttestingprogram_pkey PRIMARY KEY (stimulusvariantid , testingprogramid ),
  CONSTRAINT stimulusvarianttestingprogram_fk1 FOREIGN KEY (stimulusvariantid)
      REFERENCES stimulusvariant (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT stimulusvarianttestingprogram_fk2 FOREIGN KEY (testingprogramid)
      REFERENCES testingprogram (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

--adding default for already existing records.
ALTER TABLE testsection ADD COLUMN hardbreak boolean not null default false;

--dropping defaults once the column is added.
ALTER TABLE testsection ALTER COLUMN hardbreak drop default;

ALTER TABLE testsection ADD COLUMN sectionorder integer not null default -1;

ALTER TABLE testsection ALTER COLUMN sectionorder drop default;