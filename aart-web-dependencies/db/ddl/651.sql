--F457
ALTER TABLE externalstudentreports  ADD reporttype VARCHAR(25);
ALTER TABLE externalstudentreports  ADD reportprocessid bigint;
ALTER TABLE organizationreportdetails  ADD teacherid bigint;
ALTER TABLE organizationbundledreportsprocess ADD reporttype VARCHAR(25);
ALTER TABLE externalstudentreports  ADD schoolname VARCHAR(100);
ALTER TABLE organizationreportdetails ADD schoolname VARCHAR(100);
ALTER TABLE externalstudentreports ALTER COLUMN subjectid DROP NOT NULL;
ALTER TABLE organizationreportdetails ADD detailedreportpath_csv text;
CREATE INDEX idx_organizationreportdetails_detailedreportpath_csv
  ON organizationreportdetails
  USING btree
  (detailedreportpath_csv COLLATE pg_catalog."default");
ALTER TABLE organizationreportdetails ADD modifieddate timestamp with time zone NOT NULL DEFAULT now();
update organizationreportdetails set modifieddate=createddate;
ALTER TABLE organizationreportdetails ADD createduser bigint;
update organizationreportdetails set createduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin');
ALTER TABLE organizationreportdetails ADD modifieduser bigint;
update organizationreportdetails set modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin');

