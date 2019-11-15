-- Sequence: ksdexmlaudit_id_seq

CREATE SEQUENCE ksdexmlaudit_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

--Table: ksdexmlaudit
CREATE TABLE ksdexmlaudit
(
  id bigint NOT NULL DEFAULT nextval('ksdexmlaudit_id_seq'::regclass),
  type character varying(10) NOT NULL,
  xml text NOT NULL,
  createdate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  processeddate timestamp with time zone,
  processedcode character varying(30),
  fromdate timestamp with time zone,
  todate timestamp with time zone,
  errors text,
  CONSTRAINT ksdexmlaudit_pkey PRIMARY KEY (id)
);

-- Index: idx_ksdexmlaudit_type

CREATE INDEX idx_ksdexmlaudit_type
  ON ksdexmlaudit
  USING btree
  (type COLLATE pg_catalog."default");