ALTER TABLE groups ADD COLUMN organizationtypeid bigint;

ALTER TABLE groups ADD CONSTRAINT fk_groups_organizationtype FOREIGN KEY (organizationtypeid)
      REFERENCES organizationtype (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
 