--ddl/5.sql
ALTER TABLE questarregistrationreason ADD COLUMN questar_staging_id bigint;
ALTER TABLE questarregistrationreason ADD CONSTRAINT questarregistrationreason_stageid_fkey
	FOREIGN KEY (questar_staging_id) REFERENCES questar_staging (id) MATCH FULL
	ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE questarregistrationreason ADD COLUMN questar_staging_file_id BIGINT;
ALTER TABLE questarregistrationreason ADD CONSTRAINT questarregistrationreason_fileid_fkey
	FOREIGN KEY (questar_staging_file_id) REFERENCES questar_staging_file (id) MATCH FULL
	ON UPDATE NO ACTION ON DELETE NO ACTION;
	
ALTER TABLE questar_staging ADD COLUMN batchstatus CHARACTER VARYING (20);

ALTER TABLE questar_staging_response ADD COLUMN tasktypecode CHARACTER VARYING (75);

