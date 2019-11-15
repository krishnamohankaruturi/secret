-- US12173: Organization Management - Create Organization

CREATE TABLE organizationhierarchy (
    organizationid bigint NOT NULL,
    organizationtypeid bigint NOT NULL
);

ALTER TABLE ONLY organizationhierarchy
    ADD CONSTRAINT fk_organizationhierarchy_organization FOREIGN KEY (organizationid) REFERENCES organization(id);

ALTER TABLE ONLY organizationhierarchy
    ADD CONSTRAINT fk_organizationhierarchy_organizationtype FOREIGN KEY (organizationtypeid) REFERENCES organizationtype(id);

ALTER table organization ADD COLUMN buildinguniqueness BIGINT;

ALTER table organization ADD COLUMN schoolstartdate timestamp with time zone;
ALTER table organization ADD COLUMN schooendtdate timestamp with time zone;