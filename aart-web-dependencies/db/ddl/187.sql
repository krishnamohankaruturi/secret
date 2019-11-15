CREATE TABLE organizationcontentarea (
  organizationid bigint NOT NULL,
  contentareaid bigint NOT NULL,
  organizationcontentareacode character varying(30) NOT NULL,
  CONSTRAINT organizationcontentarea_pkey PRIMARY KEY (organizationid, contentareaid, organizationcontentareacode),
  CONSTRAINT contentareaid FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT organizationid FOREIGN KEY (organizationid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE roster ADD COLUMN prevstatesubjectareaid bigint;