--ddl/604.sql 

ALTER TABLE communicationmessagestate ADD COLUMN groupid bigint;
alter table communicationmessagestate alter stateid drop not null;

ALTER TABLE studentstests ADD COLUMN scoringassignmentid bigint DEFAULT NULL; 
ALTER TABLE scoringassignment ADD COLUMN rosterid bigint DEFAULT NULL;
ALTER TABLE ccqscoreitem ADD COLUMN taskvariantid bigint DEFAULT NULL;

ALTER TABLE lmassessmentmodelrule ADD COLUMN writingassessment BOOLEAN NOT NULL DEFAULT FALSE;
