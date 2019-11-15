-- ddl/737.sql

CREATE SEQUENCE public.appconfiguration_id_seq
  INCREMENT 1
  MINVALUE 100
  MAXVALUE 9223372036854775807
  START 100
  CACHE 1;
  
-- Table: public.appconfiguration

-- DROP TABLE public.appconfiguration;

CREATE TABLE public.appconfiguration
(
  id bigint NOT NULL DEFAULT nextval('appconfiguration_id_seq'::regclass),
  attrcode text,
  attrtype text,
  attrname text,
  attrvalue text,
  activeflag boolean DEFAULT true,
  createduser bigint,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieduser bigint,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  CONSTRAINT appconfiguration_pkey PRIMARY KEY (id)
);

-- Function: public.appconfiguration_jsondata(text)

-- DROP FUNCTION public.appconfiguration_jsondata(text);

CREATE OR REPLACE FUNCTION public.appconfiguration_jsondata(var_attrtype text)
  RETURNS json AS
$BODY$
SELECT  (('{"'::text ||attrtype ||'":{'::text) || array_to_string(array_agg(RTRIM(LTRIM(json_build_object(attrname, attrvalue)::text,'{'),'}')), ',') || '}}')::json AS json_data 
FROM appconfiguration
where attrtype =var_attrtype
group by attrtype;
$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
  