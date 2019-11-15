--Changing the schoolyear datatype in reportsmedianscore table
alter table reportsmedianscore alter column schoolyear type bigint;

CREATE SEQUENCE message_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 27
  CACHE 1;

CREATE TABLE communcationmessages
(
  id bigint NOT NULL DEFAULT nextval('message_id_seq'::regclass),
  messagetitle character varying(500),
  messagecontent character varying(500),
  expiredate timestamp with time zone,
  displaydate timestamp with time zone,
  assessmentprogramid bigint,
  stateprogramid bigint,
  createddate timestamp with time zone,
  modifieddate timestamp with time zone,
  status character varying(250),
  displayuserdate character varying(250),
  expireuserdate character varying(250),
  displaytime character varying(250),
  expiretime character varying(250),
  createduser bigint,
  modifieduser bigint,
  organizationid bigint,
  activeflag boolean DEFAULT true,
  CONSTRAINT messagepk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);