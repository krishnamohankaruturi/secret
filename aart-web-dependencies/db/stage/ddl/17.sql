-- stage/ddl/17.sql

--For schema changes in domainaudithistory and useraudittrailhistory table
ALTER TABLE domainaudithistory ADD COLUMN status character varying(20) NOT NULL DEFAULT 'PENDING';
ALTER TABLE useraudittrailhistory DROP COLUMN source, DROP COLUMN eventtype;
ALTER TABLE useraudittrailhistory RENAME historicalvalue TO beforevalue;
ALTER TABLE useraudittrailhistory RENAME loggedinuser TO modifieduser;
ALTER TABLE useraudittrailhistory DROP COLUMN lastbatchexecutiontime;
ALTER TABLE useraudittrailhistory ADD COLUMN domainaudithistoryid integer;
ALTER TABLE domainaudithistory ADD PRIMARY KEY (id);
ALTER TABLE useraudittrailhistory ADD CONSTRAINT fk_domainaudithistoryid FOREIGN KEY (domainaudithistoryid) REFERENCES domainaudithistory;

--US18004
-- Table: organizationaudittrailhistory
-- DROP TABLE organizationaudittrailhistory;
CREATE TABLE organizationaudittrailhistory
(
  id bigint NOT NULL,
  eventname text,
  modifieduser bigint,
  affectedorganization bigint,
  beforevalue text,
  currentvalue text,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  domainaudithistoryid integer,
  CONSTRAINT fk_domainaudithistoryid FOREIGN KEY (domainaudithistoryid)
      REFERENCES domainaudithistory (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- Sequence: organizationaudittrailhistory_id_seq
-- DROP SEQUENCE organizationaudittrailhistory_id_seq;
CREATE SEQUENCE organizationaudittrailhistory_id_seq;


--US18005  
-- Table: roleaudittrailhistory
-- DROP TABLE roleaudittrailhistory
CREATE TABLE roleaudittrailhistory
(
  id bigint NOT NULL,
  eventname text,
  modifieduser bigint,
  affectedrole bigint,
  beforevalue text,
  currentvalue text,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  domainaudithistoryid integer,
  CONSTRAINT fk_domainaudithistoryid FOREIGN KEY (domainaudithistoryid)
      REFERENCES domainaudithistory (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Sequence: roleaudittrailhistory_id_seq
-- DROP SEQUENCE roleaudittrailhistory_id_seq;
CREATE SEQUENCE roleaudittrailhistory_id_seq;
