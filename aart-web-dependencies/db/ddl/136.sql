
--table for CB
CREATE TABLE testaccessibilityflag
(
  testid bigint NOT NULL,
  accessibilityflagcode character varying(75),
  
  CONSTRAINT testaccessibilityflag_pkey PRIMARY KEY (testid, accessibilityflagcode),
  CONSTRAINT test_testidid_fkey FOREIGN KEY (testid)
      REFERENCES test (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
