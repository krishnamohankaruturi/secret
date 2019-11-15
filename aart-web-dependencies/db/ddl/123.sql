

--US13414 - create audit table for ksde xml files
CREATE TABLE ksdexmlaudit
(
  id bigserial NOT NULL,
  type character varying(10) NOT NULL,
  xml text NOT NULL,
  createdate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  processeddate timestamp with time zone,
  processedcode character varying(30),
  fromdate timestamp with time zone,
  todate timestamp with time zone,
  CONSTRAINT ksdexmlaudit_pk PRIMARY KEY (id)
);

ALTER TABLE ksdexmlaudit ALTER COLUMN id SET DEFAULT nextval('ksdexmlaudit_id_seq'::regclass);


