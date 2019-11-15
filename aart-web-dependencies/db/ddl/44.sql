
--additions for CB/TDE
ALTER TABLE accessibilityfile ALTER taskvariantid DROP NOT NULL;

ALTER TABLE test ADD COLUMN testinternalname character varying(100);

ALTER TABLE contentgroup
  ADD CONSTRAINT contentgroup_testid_fkey FOREIGN KEY (testid)
      REFERENCES test (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE contentgroup
  ADD CONSTRAINT contentgroup_stimulusvariantid_fkey FOREIGN KEY (stimulusvariantid)
      REFERENCES stimulusvariant (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE TABLE rubriccategory (
	id BIGINT NOT NULL,
	taskvariantid BIGINT NOT NULL,
	name TEXT,

	CONSTRAINT rubriccategory_pkey PRIMARY KEY (id),
	CONSTRAINT rubriccategory_taskvariantid_fkey FOREIGN KEY (taskvariantid)
		REFERENCES taskvariant (id) MATCH FULL
		ON UPDATE NO ACTION
		ON DELETE NO ACTION
);

CREATE SEQUENCE rubriccategoryid_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1;

CREATE TABLE rubricinfo (
	id BIGINT NOT NULL,
	rubriccategoryid BIGINT NOT NULL,
	score INTEGER,
	description TEXT,

	CONSTRAINT rubricinfo_pkey PRIMARY KEY (id),
	CONSTRAINT rubricinfo_rubriccategoryid_fkey FOREIGN KEY (rubriccategoryid)
		REFERENCES rubriccategory (id) MATCH FULL
		ON UPDATE NO ACTION
		ON DELETE  NO ACTION 
);


CREATE SEQUENCE rubricinfoid_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1;
