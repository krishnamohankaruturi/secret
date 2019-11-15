--ddl/502.sql

DROP TABLE IF EXISTS dailyaccesscode;

CREATE TABLE dailyaccesscode (
  id bigserial NOT NULL,
  operationaltestwindowid bigint NOT NULL,
  contentareaid bigint NOT NULL,
  gradecourseid bigint NOT NULL,
  stageid bigint NOT NULL,
  partnumber integer NOT NULL,
  effectivedate date NOT NULL,
  accesscode character varying(30) NOT NULL,
  createduser bigint NOT NULL,
  createddate timestamp with time zone NOT NULL,
  modifieduser bigint NOT NULL,
  modifieddate timestamp with time zone NOT NULL,
  CONSTRAINT dailyaccesscode_pkey PRIMARY KEY (id),
  CONSTRAINT dailyaccesscode_fk1 FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT dailyaccesscode_fk2 FOREIGN KEY (gradecourseid)
      REFERENCES gradecourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT dailyaccesscode_fk3 FOREIGN KEY (stageid)
      REFERENCES stage (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT dailyaccesscode_fk4 FOREIGN KEY (operationaltestwindowid)
      REFERENCES operationaltestwindow (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Index: idx1_dailyaccesscode
CREATE INDEX idx1_dailyaccesscode ON dailyaccesscode USING btree (contentareaid);

-- Index: idx2_dailyaccesscode
CREATE INDEX idx2_dailyaccesscode ON dailyaccesscode USING btree (gradecourseid);

-- Index: idx3_dailyaccesscode
CREATE INDEX idx3_dailyaccesscode ON dailyaccesscode USING btree (stageid);

-- Index: idx4_dailyaccesscode
CREATE INDEX idx4_dailyaccesscode ON dailyaccesscode USING btree (effectivedate);

-- Index: idx5_dailyaccesscode
CREATE INDEX idx5_dailyaccesscode ON dailyaccesscode USING btree (operationaltestwindowid);


