--DDL for: F517 Implement ITI for Science 
DROP TABLE IF EXISTS itilinkagelevelmapping;

CREATE TABLE itilinkagelevelmapping(id BIGINT, actuallinkagelevel VARCHAR(40), mappinglinkagelevelname VARCHAR(40), contentareaabbreviatedname VARCHAR(10));


CREATE SEQUENCE itilinkagelevelmapping_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

ALTER TABLE itilinkagelevelmapping ALTER COLUMN id SET DEFAULT nextval('itilinkagelevelmapping_id_seq'::regclass);