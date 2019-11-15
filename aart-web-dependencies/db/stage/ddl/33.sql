-- sif upload xml 33.sql

create sequence "sifxmlupload_id_seq";

CREATE TABLE sifxmlupload
(
   id bigint NOT NULL DEFAULT nextval('sifxmlupload_id_seq'::regclass),
   type character varying(50)  NOT NULL,
   xml text  NOT NULL,
   createdate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
   CONSTRAINT sifxmlupload_pkey PRIMARY KEY (id)
);

--Audit schema
ALTER TABLE domainaudithistory ADD modifieddate timestamp with time zone;

--These indexes already exist so commenting them
--CREATE INDEX idx1_batchupload_id   ON batchupload  USING btree  (id);
--CREATE INDEX idx4_batchupload_assessmentprogramid   ON batchupload  USING btree  (assessmentprogramid);
--CREATE INDEX idx6_batchupload_uploadtypeid   ON batchupload  USING btree  (uploadtypeid);

--CREATE INDEX idx1_batchuploadreason_batchuploadid   ON batchuploadreason  USING btree  (batchuploadid);
--CREATE INDEX idx2_batchuploadreason_line   ON batchuploadreason  USING btree  (line);
--CREATE INDEX idx3_batchuploadreason_fieldname   ON batchuploadreason  USING btree  (fieldname);
CREATE INDEX idx_batchupload_filename   ON batchupload  USING btree  (filename);
CREATE INDEX idx_batchupload_filepath   ON batchupload  USING btree  (filepath);
CREATE INDEX idx_batchupload_stateid   ON batchupload  USING btree  (stateid);

CREATE INDEX idx_batchuploadreason_errortype   ON batchuploadreason  USING btree  (errortype);