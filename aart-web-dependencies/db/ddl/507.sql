--ddl/507.sql

CREATE TABLE groupauthoritylockdownperiod (
  id bigserial NOT NULL,
  organizationid bigint NOT NULL,
  groupauthorityid bigint NOT NULL,
  fromdate timestamp with time zone NOT NULL,
  todate timestamp with time zone NOT NULL,
  createduser bigint NOT NULL,
  createddate timestamp with time zone NOT NULL,
  modifieduser bigint NOT NULL,
  modifieddate timestamp with time zone NOT NULL,
  activeflag boolean NOT NULL DEFAULT true,
  CONSTRAINT groupauthoritylockdownperiod_pkey PRIMARY KEY (id),
  CONSTRAINT groupauthoritylockdownperiod_fk1 FOREIGN KEY (organizationid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT groupauthoritylockdownperiod_fk2 FOREIGN KEY (groupauthorityid)
      REFERENCES groupauthorities (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX idx1_groupauthoritylockdownperiod ON groupauthoritylockdownperiod USING btree (organizationid);

CREATE INDEX idx2_groupauthoritylockdownperiod ON groupauthoritylockdownperiod USING btree (groupauthorityid);
