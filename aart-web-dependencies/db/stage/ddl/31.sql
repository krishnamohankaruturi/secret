--ddl/31.sql
 CREATE TABLE batchregisteredscoringassignments
 (
  batchregistrationid bigint NOT NULL,
  scoringassignmentid bigint,
  CONSTRAINT batchregisteredscoringassignments_batchregistrationid_fkey FOREIGN KEY (batchregistrationid)
      REFERENCES batchregistration (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
  );