
-- 310.sql

update groups set organizationtypeid = (select id from organizationtype where typecode='SCH') where organizationtypeid = (select id from organizationtype where typecode='BLDG');

CREATE INDEX idx_readaloudaccommodation_contentgroupid
  ON readaloudaccommodation USING btree (contentgroupid);

CREATE INDEX idx_readaloudaccommodation_accessibilityfileid
  ON readaloudaccommodation USING btree (accessibilityfileid);

CREATE INDEX idx_contentgroup_stimulusvariantid ON contentgroup
  USING btree (stimulusvariantid);

CREATE INDEX idx_contentgroup_taskvariantid ON contentgroup
  USING btree (taskvariantid);

CREATE INDEX idx_contentgroup_testid ON contentgroup
  USING btree (testid);

CREATE INDEX idx_contentgroup_testsectionid ON contentgroup
  USING btree (testsectionid);

CREATE INDEX idx_contentgroup_foilid ON contentgroup
  USING btree (foilid);

CREATE INDEX idx_testsectionresource_stimulusvariantid ON testsectionresource
  USING btree (stimulusvariantid);

CREATE INDEX idx_testsectionresource_testsectionid ON testsectionresource
  USING btree (testsectionid);

CREATE INDEX idx_aartuser_username ON aartuser (lower(username));