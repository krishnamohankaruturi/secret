--ddl/29.sql
CREATE TABLE organizationmanagementaudit
(
  id bigserial NOT NULL,
  sourceorgid bigint,
  destorgid bigint,
  studentid bigint,
  aartuserid bigint,
  rosterid bigint,
  enrollmentid bigint,
  currentschoolyear bigint,
  operationtype character varying(10),
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  activeflag boolean DEFAULT true,
  createduser integer,
  modifieduser integer,
  CONSTRAINT orgmgmtaudit_pkey PRIMARY KEY (id)
);
