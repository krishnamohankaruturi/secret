ALTER TABLE contentareatesttypesubjectarea ADD COLUMN stageid BIGINT;
ALTER TABLE contentareatesttypesubjectarea ADD CONSTRAINT fk_contentareatesttypesubjectarea_stageid FOREIGN KEY (stageid)
	REFERENCES stage (id) MATCH FULL
	ON UPDATE NO ACTION ON DELETE NO ACTION;
	
DROP TABLE batchregisteredtestsessions;
DROP TABLE batchregistrationreason;
DROP TABLE batchregistration;
