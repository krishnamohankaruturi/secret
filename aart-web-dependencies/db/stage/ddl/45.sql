-- For feature F815

CREATE TABLE fcscompbandhistory
(
  id bigserial,
  studentid bigint,
  studentname character varying(160),
  compbandsubject character varying(75),
  previouscompband character varying(75),
  updatedcompband character varying(75),
  schoolyear bigint,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifiedby integer NOT NULL,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone
);