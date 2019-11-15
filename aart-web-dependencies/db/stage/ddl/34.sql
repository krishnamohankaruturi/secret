-- F458 and F459
CREATE SEQUENCE uploadincidentfile_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE uploadsccodefile_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

 CREATE SEQUENCE uploadgrfflie_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 12
  CACHE 1;

  ALTER TABLE batchupload ADD COLUMN reportyear bigint;
  
  
CREATE TABLE uploadincidentfile
(
  id bigint NOT NULL,
  studentid bigint,
  stateid bigint,
  batchuploadprocessid bigint NOT NULL,
  statestudentidentifier character varying(50),
  state character varying(50),
  studentlegalfirstname character varying(80),
  studentlegalmiddlename character varying(80),
  studentlegallastname character varying(80),
  generationcode character varying(10),
  dateofbirth date,
  essentialelement character varying(20),
  issuecode character varying(2),
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser bigint NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieduser integer NOT NULL,
  reportyear bigint
);

CREATE INDEX idx1_uploadincidentfile  ON uploadincidentfile  USING btree  (batchuploadprocessid);
CREATE INDEX idx2_uploadincidentfile  ON uploadincidentfile  USING btree  (studentid);
CREATE INDEX idx3_uploadincidentfile  ON uploadincidentfile  USING btree  (state);
CREATE INDEX idx4_uploadincidentfile  ON uploadincidentfile  USING btree  (statestudentidentifier );
CREATE INDEX idx5_uploadincidentfile  ON uploadincidentfile  USING btree  (id);
CREATE INDEX idx6_uploadincidentfile  ON uploadincidentfile  USING btree  (essentialelement );
CREATE INDEX idx7_uploadincidentfile  ON uploadincidentfile  USING btree  (issuecode );

CREATE TABLE uploadsccodefile
(
  id bigint NOT NULL,
  batchuploadprocessid bigint NOT NULL,
  studentid bigint,
  state character varying,
  stateid  bigint,
  statestudentidentifier character varying(50),
  studentlegalfirstname character varying(80),
  studentlegalmiddlename character varying(80),
  studentlegallastname character varying(80),
  generationcode character varying(10),
  dateofbirth date,
  specialcircumstancecode bigint,
  ksdesccode character varying(20),
  specialcircumstancelabel character varying(1000),
  essentialelement character varying(20),
  assessment character varying(5),
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser bigint NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieduser bigint NOT NULL,  
  reportyear bigint,
  CONSTRAINT pk_upload_sc_code_file PRIMARY KEY (id)
);

CREATE INDEX idx1_uploadsccodefile  ON uploadsccodefile  USING btree  (batchuploadprocessid);
CREATE INDEX idx2_uploadsccodefile  ON uploadsccodefile  USING btree  (statestudentidentifier);
CREATE INDEX idx3_uploadsccodefile  ON uploadsccodefile  USING btree  (specialcircumstancecode);
CREATE INDEX idx4_uploadsccodefile  ON uploadsccodefile  USING btree  (essentialelement );
CREATE INDEX idx5_uploadsccodefile  ON uploadsccodefile  USING btree  (ksdesccode);
CREATE INDEX idx6_uploadsccodefile  ON uploadsccodefile  USING btree  (assessment);
CREATE INDEX idx7_uploadsccodefile  ON uploadsccodefile  USING btree  (studentid);
CREATE INDEX idx8_uploadsccodefile  ON uploadsccodefile  USING btree  (stateid);
