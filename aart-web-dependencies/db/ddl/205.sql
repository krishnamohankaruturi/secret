ALTER TABLE studentstests ADD COLUMN enrollmentid bigint;

ALTER TABLE studentstests
  ADD CONSTRAINT fk_enrollmentid FOREIGN KEY (enrollmentid)
      REFERENCES enrollment (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;