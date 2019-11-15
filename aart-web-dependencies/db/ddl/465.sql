-- ddl/465.sql

--changes from change pond team 
CREATE TABLE operationaltestwindowsstate
(
operationaltestwindowid bigint NOT NULL,
stateid bigint NOT NULL,
createduser bigint NOT NULL,
createddate time with time zone NOT NULL DEFAULT now(),
modifieduser integer,
modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
CONSTRAINT ooperationaltestwindowsstate_pkey PRIMARY KEY (operationaltestwindowid, stateid),
CONSTRAINT ooperationaltestwindowsstate_operationaltestwindowid_ FOREIGN KEY (operationaltestwindowid)
      REFERENCES operationaltestwindow (id) 
);

