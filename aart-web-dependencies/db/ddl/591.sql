--dml/591.sql - empty

CREATE SEQUENCE pnpaccomodations_id_seq;

-- to store pnpaccomodations instead of getting from messages.properties
CREATE TABLE pnpaccomodations
(
  id bigint NOT NULL DEFAULT nextval('pnpaccomodations_id_seq'::regclass),
  accomodation character varying(75) NOT NULL,
  categoryid bigint NOT NULL,
  pianacid bigint NOT NULL,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieduser integer NOT NULL,
  CONSTRAINT pnpaccomodations_id_pk PRIMARY KEY (id),
  CONSTRAINT pnpaccomodations_categoryid_fk FOREIGN KEY (categoryid)
REFERENCES public.category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
CONSTRAINT pnpaccomodations_pianacid_fk FOREIGN KEY (pianacid)
      REFERENCES public.profileitemattributenameattributecontainer (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION);
      
--to store state override pnp option settings

CREATE TABLE pnpstatesettings
(
  stateid bigint,
  pinacid bigint,
  viewoption character varying(20),
  assessmentprogramid bigint,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieduser integer NOT NULL,
  CONSTRAINT stateid_statepnpsettings_fk FOREIGN KEY (stateid)
      REFERENCES public.organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT piancid_statepnpsettings_fk FOREIGN KEY (pinacid)
      REFERENCES public.profileitemattributenameattributecontainer (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT assessmentprogramid_statepnpsettings_fk FOREIGN KEY (assessmentprogramid)
      REFERENCES public.assessmentprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION);


CREATE TABLE organizationmanagementaudit
(
  id bigserial NOT NULL,
  sourceorgid bigint,
  destorgid bigint,
  studentid bigint,
  aartuserid bigint,
  rosterid bigint,
  enrollmentid bigint,
  operationtype character varying(10),
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  activeflag boolean DEFAULT true,
  createduser integer,
  modifieduser integer,
  CONSTRAINT orgmgmtaudit_pkey PRIMARY KEY (id),
   CONSTRAINT orgmgmtaudit_sourceorgid_fk FOREIGN KEY (sourceorgid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
   CONSTRAINT orgmgmtaudit_destorgid_fk FOREIGN KEY (destorgid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
   CONSTRAINT orgmgmtaudit_studentid_fk FOREIGN KEY (studentid)
      REFERENCES student (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
   CONSTRAINT orgmgmtaudit_aartuser_fk FOREIGN KEY (aartuserid)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT orgmgmtaudit_rosterid_fk FOREIGN KEY (rosterid)
      REFERENCES roster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT orgmgmtaudit_enrollmentid_fk FOREIGN KEY (enrollmentid)
      REFERENCES enrollment (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_org_created_user FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_org_updated_user FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
