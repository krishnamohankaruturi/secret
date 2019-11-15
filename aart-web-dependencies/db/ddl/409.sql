-- Table to keep track of who invoked EP interfaces
CREATE TABLE interfacerequesthistory
(
  id bigserial NOT NULL,
  interfacename character varying(50),
  requesteduserid character varying(50),
  requestedipaddress character varying(50),
  requesteddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp without time zone,
  modulereportid bigint,
  message character varying(200),
  CONSTRAINT interfacerequesthistory_pkey PRIMARY KEY (id),
  CONSTRAINT fk_interfacerequesthistory_modulereportid FOREIGN KEY (modulereportid)
      REFERENCES modulereport (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);