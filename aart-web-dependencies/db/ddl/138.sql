
--changes for CB
ALTER table test ADD COLUMN unpublishreasonid bigint;

ALTER TABLE test
  ADD CONSTRAINT test_unpublishreasonid_fkey FOREIGN KEY (unpublishreasonid)
      REFERENCES category (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION;
