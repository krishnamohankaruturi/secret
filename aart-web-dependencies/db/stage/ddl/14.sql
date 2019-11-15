--ddl/14.sql

--SQL for US17559  -- NOTE: THIS BELOW SQL NEED TO BE EXECUTED IN AARTAUDIT DATABASE.
CREATE TABLE domainaudithistory
(
  id bigserial NOT NULL,
  source character varying(25) NOT NULL,
  objecttype character varying(50) NOT NULL,
  objectid bigint NOT NULL,
  createduserid integer NOT NULL,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  action character varying(25) NOT NULL,
  objectbeforevalues json,
  objectaftervalues json
)
WITH (
  OIDS=FALSE
);
 
CREATE INDEX idx_domainaudithistory_createduserid
  ON domainaudithistory
  USING btree
  (createduserid);
 
CREATE INDEX idx_domainaudithistory_objectid
  ON domainaudithistory
  USING btree
  (objectid);
 
CREATE INDEX idx_domainaudithistory_objecttype
  ON domainaudithistory
  USING btree
  (objecttype COLLATE pg_catalog."default");
 
CREATE INDEX idx_domainaudithistory_source
  ON domainaudithistory
  USING btree
  (source COLLATE pg_catalog."default");
  
  -- creating indexes on staging tables
  CREATE INDEX idx_kids_record_staging_ksdexmlaudit_id
  ON kids_record_staging USING btree (ksdexmlaudit_id);

  CREATE INDEX idx_tasc_record_staging_ksdexmlaudit_id
  ON tasc_record_staging USING btree (ksdexmlaudit_id); 