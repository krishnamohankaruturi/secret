-- stage/ddl/16.sql

-- FOR US17687 - NOTE THIS SQL TO BE EXECUTED IN AARTAUDIT
CREATE TABLE useraudittrailhistory
(
  id bigint NOT NULL,
  source character varying(10),
  eventtype character varying(20),
  eventname text,
  loggedinuser bigint,
  affecteduser bigint,
  historicalvalue text,
  currentvalue text,
  lastbatchexecutiontime timestamp with time zone,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);

CREATE SEQUENCE useraudittrailhistory_id_seq;