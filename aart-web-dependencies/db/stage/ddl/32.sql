--ddl/32.sql
CREATE TABLE batchupload
(
  id bigint NOT NULL,
  filename text NOT NULL,
  filepath text NOT NULL,
  assessmentprogramid bigint NOT NULL,
  contentareaid bigint NOT NULL,
  status character varying(200),
  successcount integer,
  failedcount integer,
  resultjson text,
  submissiondate timestamp with time zone NOT NULL DEFAULT now(),
  createddate timestamp with time zone DEFAULT now(),
  modifieddate timestamp with time zone DEFAULT now(),
  createduser bigint,
  activeflag boolean NOT NULL DEFAULT true,
  schoolyear integer,
  uploadtypeid bigint,
  stateid bigint,
  districtid bigint,
  schoolid bigint,
  selectedorgid bigint,
  uploadeduserorgid bigint,
  uploadedusergroupid bigint,
  alertcount bigint,
  documentid bigint,
  CONSTRAINT batchupload_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

-- Index: idx_batchupload_activeflag
CREATE INDEX idx_batchupload_activeflag
  ON batchupload
  USING btree
  (activeflag);

-- Index: idx_batchupload_assessmentprogramid
CREATE INDEX idx_batchupload_assessmentprogramid
  ON batchupload
  USING btree
  (assessmentprogramid);

-- Index: idx_batchupload_contentareaid
CREATE INDEX idx_batchupload_contentareaid
  ON batchupload
  USING btree
  (contentareaid);

-- Index: idx_batchupload_createduser
CREATE INDEX idx_batchupload_createduser
  ON batchupload
  USING btree
  (createduser);

-- Index: idx_batchupload_status
CREATE INDEX idx_batchupload_status
  ON batchupload
  USING btree
  (status COLLATE pg_catalog."default");

-- Index: idx_batchupload_uploadedusergroupid
CREATE INDEX idx_batchupload_uploadedusergroupid
  ON batchupload
  USING btree
  (uploadedusergroupid);

-- Index: idx_batchupload_uploadeduserorgid
CREATE INDEX idx_batchupload_uploadeduserorgid
  ON batchupload
  USING btree
  (uploadeduserorgid);

-- Index: idx_batchupload_uploadtypeid
CREATE INDEX idx_batchupload_uploadtypeid
  ON batchupload
  USING btree
  (uploadtypeid);
  
-- Table: batchuploadreason
CREATE TABLE batchuploadreason
(
  batchuploadid bigint NOT NULL,
  line character varying(25),
  fieldname character varying(300),
  reason text,
  errortype character varying(10),
  CONSTRAINT batchuploadreason_batchuploadid_fkey FOREIGN KEY (batchuploadid)
      REFERENCES batchupload (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);


-- Index: idx_batchuploadreason_batchuploadid
CREATE INDEX idx_batchuploadreason_batchuploadid
  ON batchuploadreason
  USING btree
  (batchuploadid);

-- Index: idx_batchuploadreason_fieldname
CREATE INDEX idx_batchuploadreason_fieldname
  ON batchuploadreason
  USING btree
  (fieldname COLLATE pg_catalog."default");

-- Index: idx_batchuploadreason_line
CREATE INDEX idx_batchuploadreason_line
  ON batchuploadreason
  USING btree
  (line COLLATE pg_catalog."default");


alter table batchupload add column assessmentprogramname character varying(100);
alter table batchupload add column contentareaname character varying(100);
alter table batchupload add column uploadtype character varying(100);
alter table batchupload add column createduserdisplayname character varying(161);

CREATE INDEX idx_batchupload_apname
  ON batchupload
  USING btree
  (assessmentprogramname COLLATE pg_catalog."default");
  
CREATE INDEX idx_batchupload_contentareaname
  ON batchupload
  USING btree
  (contentareaname COLLATE pg_catalog."default");
  
CREATE INDEX idx_batchupload_uploadtype
  ON batchupload
  USING btree
  (uploadtype COLLATE pg_catalog."default");

-- batchupload_id_seq need to created with new id
-- data need to be copied to new tables from normal table
-- new columns need to be populated.
CREATE SEQUENCE batchupload_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 10396
  CACHE 1;
