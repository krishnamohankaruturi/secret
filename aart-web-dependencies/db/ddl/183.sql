
--CB changes for metadata sync
ALTER TABLE contentframework ADD COLUMN assessmentprogramid bigint;
ALTER TABLE contentframework
  ADD CONSTRAINT contentframework_assessmentprogram_fkey FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION;
