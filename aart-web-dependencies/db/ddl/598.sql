-- ddl/598.sql (empty)
--Relaxing the validation on TestID1 (not required for social studies) for Raw to scale score upload

ALTER TABLE rawtoscalescores ALTER COLUMN testid1 DROP NOT NULL;