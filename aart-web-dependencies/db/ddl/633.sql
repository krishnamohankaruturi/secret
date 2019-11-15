-- 633 ddl
-- F453: US19455: DLM Visibility of Testlets Remaining
ALTER TABLE studentstests  DROP COLUMN IF EXISTS currenttestnumber;
ALTER TABLE studentstests ADD COLUMN currenttestnumber INTEGER;
ALTER TABLE studentstests  DROP COLUMN IF EXISTS numberoftestsrequired;
ALTER TABLE studentstests  ADD COLUMN numberoftestsrequired INTEGER;
