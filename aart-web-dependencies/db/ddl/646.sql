--  646.sql DDL for DE15455 --

CREATE TABLE reportorganizationshortname
  ( id bigserial NOT NULL,
    organizationid bigint NOT NULL,
    originalorgname character varying(75),
    shortorgname character varying(45) NOT NULL,
    activeflag boolean DEFAULT TRUE,
    CONSTRAINT reportorganizationshortname_pkey PRIMARY KEY (id), 
    CONSTRAINT reportorganizationshortname_fk1 FOREIGN KEY (organizationid) 
    REFERENCES organization (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
   );


CREATE INDEX idx1_reportorganizationshortname ON reportorganizationshortname USING btree (organizationid);
CREATE INDEX idx2_reportorganizationshortname ON reportorganizationshortname USING btree (activeflag);