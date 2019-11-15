-- 36.sql

CREATE TABLE organizationtreedetail
(
  schoolid bigint NOT NULL,
  schoolname character varying(200) NOT NULL,
  schooldisplayidentifier character varying(100) NOT NULL,
  districtid bigint,
  districtname character varying(200),
  districtdisplayidentifier character varying(100),
  stateid bigint,
  statename character varying(200),
  statedisplayidentifier character varying(100),
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT organizationtreedetail_pkey PRIMARY KEY (schoolid)
)
WITH (
  OIDS=FALSE
);