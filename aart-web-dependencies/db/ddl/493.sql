--ddl/493.sql 

CREATE TABLE studenttrackeroperationalteststatus
(
  studenttrackerid bigint NOT NULL,
  operationalwindowid bigint NOT NULL,
  statuscode character varying(30) NOT NULL,
  createddate timestamp with time zone,
  createduser bigint,
  CONSTRAINT studenttrackeroperationalteststatus_pkey PRIMARY KEY (studenttrackerid, operationalwindowid),
  CONSTRAINT studenttrackeroperationalteststatus_fk1 FOREIGN KEY (studenttrackerid)
      REFERENCES studenttracker (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT studenttrackeroperationalteststatus_fk2 FOREIGN KEY (operationalwindowid)
      REFERENCES operationaltestwindow (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);