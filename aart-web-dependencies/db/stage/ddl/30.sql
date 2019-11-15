--ddl/30.sql
CREATE SEQUENCE studentpnpsaudithistory_id_seq;
CREATE TABLE studentpnpsaudithistory
(
  id bigint NOT NULL DEFAULT nextval('studentpnpsaudithistory_id_seq'::regclass),
  eventname character varying(50),
  studentid bigint,
  statestudentidentifier character varying(50),
  studentfirstname character varying(80),
  studentlastname character varying(80),
  beforevalue text,
  currentvalue text,
  activeflag boolean,
  loggedinuserfirstname character varying(80),
  loggedinuserlastname character varying(80),
  state character varying(80),
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
  );

