
--US14309
alter table authorities alter column objecttype type character varying(100);


-- moved these from 172.sql as they already executed in prod
CREATE SEQUENCE ksdexmlaudit_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
ALTER TABLE ksdexmlaudit ADD COLUMN processedcode character varying(30);
ALTER TABLE ksdexmlaudit ADD COLUMN processeddate timestamp with time zone;
ALTER TABLE ksdexmlaudit ADD COLUMN fromdate timestamp with time zone;
ALTER TABLE ksdexmlaudit ADD COLUMN todate timestamp with time zone;
ALTER TABLE ksdexmlaudit ADD COLUMN id bigint;
ALTER TABLE ksdexmlaudit ALTER COLUMN id SET DEFAULT nextval('ksdexmlaudit_id_seq'::regclass);