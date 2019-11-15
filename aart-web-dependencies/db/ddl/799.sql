--DE19496 adding id to the usersecurityagreement table.

ALTER TABLE usersecurityagreement DROP COLUMN IF EXISTS id;

DROP SEQUENCE IF EXISTS usersecurityagreement_id_seq;



--sequence
CREATE SEQUENCE usersecurityagreement_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 2147483647
  START 1
  CACHE 1;

--add id column
alter table usersecurityagreement add id bigint DEFAULT nextval('usersecurityagreement_id_seq');

--add primary key
ALTER TABLE public.usersecurityagreement ADD CONSTRAINT usersecurityagreement_pkey PRIMARY KEY (id);


