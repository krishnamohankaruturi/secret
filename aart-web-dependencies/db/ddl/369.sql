
-- 369.sql
-- 368.sql moved to reconsile with branch patches
-- US15813: Test Coordination - require confirm for some Special Circumstance codes

ALTER TABLE specialcircumstance ADD COLUMN requireConfirmation boolean;
ALTER TABLE specialcircumstance ALTER COLUMN requireConfirmation SET DEFAULT false;

