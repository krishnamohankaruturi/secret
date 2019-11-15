--ddl/695.sql

DO
 $BODY$
BEGIN
IF not EXISTS (SELECT column_name 
               FROM information_schema.columns 
               WHERE table_name='studentspecialcircumstance' and column_name='id') THEN
ALTER TABLE studentspecialcircumstance ADD COLUMN id bigint;
else
raise NOTICE 'Already exists column id on table studentspecialcircumstance';
END IF;
END;
 $BODY$;
DROP SEQUENCE if exists studentspecialcircumstance_id_seq;
CREATE SEQUENCE studentspecialcircumstance_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 50001
  CACHE 1;

update studentspecialcircumstance
set id=nextval('studentspecialcircumstance_id_seq'::regclass);
alter table studentspecialcircumstance alter column id SET NOT NULL;
ALTER TABLE studentspecialcircumstance ALTER COLUMN id SET DEFAULT nextval('studentspecialcircumstance_id_seq');
ALTER TABLE studentspecialcircumstance ADD CONSTRAINT studentspecialcircumstance_pkey PRIMARY KEY (id);