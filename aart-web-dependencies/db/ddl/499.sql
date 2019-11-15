ALTER TABLE studentstestsections ADD COLUMN previousstatusId bigint;

ALTER TABLE studentstestsections ADD CONSTRAINT
fk_previousstatus FOREIGN KEY (previousstatusId)
REFERENCES category (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE TABLE dailyaccesscode
(
  id bigserial NOT NULL,
  contenareaid bigint NOT NULL,
  gradecourseid bigint NOT NULL,
  stageid bigint NOT NULL,
  partnumber integer NOT NULL,
  displaypartnumber integer NOT NULL,
  createduser bigint NOT NULL,
  createddate timestamp with time zone NOT NULL,
  modifieduser bigint NOT NULL,
  modifieddate timestamp with time zone NOT NULL,
  effectivedate timestamp with time zone NOT NULL,
  expirydate timestamp with time zone NOT NULL,
  CONSTRAINT dailyaccesscode_pkey PRIMARY KEY (id),
  CONSTRAINT dailyaccesscode_fk1 FOREIGN KEY (contenareaid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT dailyaccesscode_fk2 FOREIGN KEY (gradecourseid)
      REFERENCES gradecourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT dailyaccesscode_fk3 FOREIGN KEY (stageid)
      REFERENCES stage (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

-- Index: idx1_dailyaccesscode

-- DROP INDEX idx1_dailyaccesscode;

CREATE INDEX idx1_dailyaccesscode
  ON dailyaccesscode
  USING btree
  (contenareaid);

-- Index: idx2_dailyaccesscode

-- DROP INDEX idx2_dailyaccesscode;

CREATE INDEX idx2_dailyaccesscode
  ON dailyaccesscode
  USING btree
  (gradecourseid);

-- Index: idx3_dailyaccesscode

-- DROP INDEX idx3_dailyaccesscode;

CREATE INDEX idx3_dailyaccesscode
  ON dailyaccesscode
  USING btree
  (stageid);

-- Index: idx4_dailyaccesscode

-- DROP INDEX idx4_dailyaccesscode;

CREATE INDEX idx4_dailyaccesscode
  ON dailyaccesscode
  USING btree
  (effectivedate);

-- Index: idx5_dailyaccesscode

-- DROP INDEX idx5_dailyaccesscode;

CREATE INDEX idx5_dailyaccesscode
  ON dailyaccesscode
  USING btree
  (expirydate);
  
-- US17066: DLM - Ability to download of Braille Refreshable File
CREATE INDEX idx_stimulusvariantattachment_filetype ON stimulusvariantattachment USING btree (filetype);  

