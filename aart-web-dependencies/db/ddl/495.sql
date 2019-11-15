-- 495.sql 

ALTER TABLE studenttrackeroperationalteststatus  RENAME TO studenttrackerblueprintstatus;

ALTER TABLE studenttrackerblueprintstatus DROP CONSTRAINT studenttrackeroperationalteststatus_fk1;

ALTER TABLE studenttrackerblueprintstatus DROP CONSTRAINT studenttrackeroperationalteststatus_fk2;

ALTER TABLE studenttrackerblueprintstatus ADD CONSTRAINT studenttrackeroperationalteststatus_fk1 FOREIGN KEY (studenttrackerid)
      REFERENCES studenttracker (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
ALTER TABLE studenttrackerblueprintstatus
  ADD CONSTRAINT studenttrackeroperationalteststatus_fk2 FOREIGN KEY (operationalwindowid)
      REFERENCES operationaltestwindow (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE INDEX idx1_studenttrackerblueprintstatus_statuscode
  ON studenttrackerblueprintstatus
  USING btree (statuscode);
  
-- script from change pond
--US17086-Enhance Operational Test Window Setup
ALTER TABLE operationaltestwindowsessionrule ADD COLUMN dacstarttime time with time zone;
ALTER TABLE operationaltestwindowsessionrule ADD COLUMN dacendtime time with time zone;
--ALTER TABLE operationaltestwindowsessionrule ADD COLUMN dacavailabilitycode character varying(30); 
-- possible values of dacavailabilitycode will be ON_TEST_DAY || A_DAY_BEFORE_TEST_DAY

--US17035-Operational Test Window - DLM Multi-assign ST method plus number of tests by subject
CREATE TABLE operationaltestwindowmultiassigndetail
(
  id bigserial NOT NULL,
  operationaltestwindowid bigint NOT NULL,
  contentareaid bigint NOT NULL,
  numberoftests integer,
  CONSTRAINT otwmultiassigndetail_pkey PRIMARY KEY (id),
  CONSTRAINT fk_operationaltestwindowid FOREIGN KEY (operationaltestwindowid)
      REFERENCES operationaltestwindow (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_contentareaid FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH SIMPLE
     ON UPDATE NO ACTION ON DELETE NO ACTION
);
