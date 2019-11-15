--ddl/28.sql
CREATE TABLE activationtemplateaudittrailhistory
(
  id bigserial NOT NULL,
  eventname text,
  modifieduser bigint,
  affectedemailtemplateid bigint,
  beforevalue json,
  currentvalue json,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  domainaudithistoryid integer,
   CONSTRAINT activationtemplate_audittrailhistory_pk PRIMARY KEY (id),
  CONSTRAINT fk_activationtemplate_domainaudithistoryid FOREIGN KEY (domainaudithistoryid)
      REFERENCES domainaudithistory (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
CREATE INDEX activationtemplate_id_index ON activationtemplateaudittrailhistory (id);
CREATE INDEX activationtemplate_affectedemailtemplateid_index ON activationtemplateaudittrailhistory (affectedemailtemplateid);
CREATE INDEX activationtemplate_domainaudithistoryid_index ON activationtemplateaudittrailhistory (domainaudithistoryid);

  