--sql
ALTER TABLE userresponse RENAME TO usertestresponse;

ALTER TABLE usertestresponse DROP COLUMN createduser;
ALTER TABLE usertestresponse DROP COLUMN modifieduser;

ALTER TABLE usertest DROP COLUMN createduser;
ALTER TABLE usertest DROP COLUMN modifieduser;

ALTER TABLE usertestsection DROP COLUMN createduser;
ALTER TABLE usertestsection DROP COLUMN modifieduser;
  