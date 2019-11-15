--ddl/501.sql
-- Adding unique constraint on stateid, specialcircumstance
alter table statespecialcircumstance add constraint statespecialcircumstance_unique_key unique (stateid,specialcircumstanceid,activeflag);

-- User story US17195
ALTER TABLE aartuser
  ADD COLUMN "systemindicator" BOOLEAN DEFAULT FALSE;


ALTER TABLE groups
  ADD COLUMN "systemindicator" BOOLEAN DEFAULT FALSE;