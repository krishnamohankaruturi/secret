
--CB changes
ALTER TABLE frameworktype ADD COLUMN assessmentprogramid bigint;
ALTER TABLE frameworktype
  ADD CONSTRAINT frameworktype_assessmentprogram_fkey FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION;