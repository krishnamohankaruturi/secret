--ddl/713.sql
--F671
-- Table: uploadincidentfile

-- DROP TABLE uploadincidentfile;

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
  essentialelement character varying,
  issuecode character varying(2),
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser bigint NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieduser integer NOT NULL,
  reportyear bigint
)
WITH (
  OIDS=FALSE
);
-- Index: idx1_uploadincidentfile

-- DROP INDEX idx1_uploadincidentfile;

CREATE INDEX idx1_uploadincidentfile
  ON uploadincidentfile
  USING btree
  (batchuploadprocessid);

-- Index: idx2_uploadincidentfile

-- DROP INDEX idx2_uploadincidentfile;

CREATE INDEX idx2_uploadincidentfile
  ON uploadincidentfile
  USING btree
  (studentid);

-- Index: idx3_uploadincidentfile

-- DROP INDEX idx3_uploadincidentfile;

CREATE INDEX idx3_uploadincidentfile
  ON uploadincidentfile
  USING btree
  (state COLLATE pg_catalog."default");

-- Index: idx4_uploadincidentfile

-- DROP INDEX idx4_uploadincidentfile;

CREATE INDEX idx4_uploadincidentfile
  ON uploadincidentfile
  USING btree
  (statestudentidentifier COLLATE pg_catalog."default");

-- Index: idx5_uploadincidentfile

-- DROP INDEX idx5_uploadincidentfile;

CREATE INDEX idx5_uploadincidentfile
  ON uploadincidentfile
  USING btree
  (id);

-- Index: idx6_uploadincidentfile

-- DROP INDEX idx6_uploadincidentfile;

CREATE INDEX idx6_uploadincidentfile
  ON uploadincidentfile
  USING btree
  (essentialelement COLLATE pg_catalog."default");

-- Index: idx7_uploadincidentfile

-- DROP INDEX idx7_uploadincidentfile;

CREATE INDEX idx7_uploadincidentfile
  ON uploadincidentfile
  USING btree
  (issuecode COLLATE pg_catalog."default");
