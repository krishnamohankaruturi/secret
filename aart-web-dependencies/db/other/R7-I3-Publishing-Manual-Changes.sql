--these are publishing changes that got executed in QA1 manually.
--it failed in qa2. So this is being executed in qa2 also manually.

--For publishing executed in QA manually.

ALTER TABLE stimulusvariant ADD COLUMN srcack text;
ALTER TABLE testlet ALTER COLUMN questionviewid DROP NOT NULL;
ALTER TABLE testlet ALTER COLUMN questionlocked DROP NOT NULL;
ALTER TABLE testlet ALTER COLUMN displayviewid DROP NOT NULL;
 
ALTER TABLE testletstimulusvariants
  DROP CONSTRAINT testletstimulusvariants_pkey;
  
--CB publishing changes.R7-I2
--commented because of build failure.
-- looks like this was executed in QA manually. So checking in 23.sql so that it will not execute.
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription,
categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Paginated', 'paginated', 'Paginated',
(select id from categorytype where typecode='TESTLET_LAYOUT'), 'CB', now(),
(select id from aartuser where username='cetesysadmin'), now(),
(select id from aartuser where username='cetesysadmin'));
