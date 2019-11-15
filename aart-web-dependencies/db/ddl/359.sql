-- US15795 - Technical: Fix Operation Test Window screen
alter table operationaltestwindow drop CONSTRAINT  operationaltestwindow_testcollection_fkey;

alter table operationaltestwindow drop column testcollectionid;

ALTER TABLE assessmentprogram ALTER COLUMN abbreviatedname SET NOT NULL;

DROP INDEX IF EXISTS idx_stage_predecessor;
CREATE INDEX idx_stage_predecessor ON stage USING btree (predecessorid);
