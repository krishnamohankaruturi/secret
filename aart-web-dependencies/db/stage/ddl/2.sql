-- add status for processing
ALTER TABLE questar_staging ADD COLUMN status CHARACTER VARYING (20);
ALTER TABLE questar_staging ADD COLUMN modifieddate TIMESTAMP WITH TIME ZONE;
