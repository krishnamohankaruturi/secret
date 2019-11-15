--ddl/543.sql

ALTER TABLE studentresponsescore DROP COLUMN IF EXISTS scorable;
ALTER TABLE studentresponsescore DROP COLUMN IF EXISTS nonscorablecodeid;
DROP TABLE IF EXISTS nonscorablecode;
DROP SEQUENCE IF EXISTS nonscorablecode_id_seq;

CREATE SEQUENCE nonscorablecode_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE nonscorablecode (
	id BIGINT DEFAULT nextval('nonscorablecode_id_seq'::regclass),
	code CHARACTER VARYING (5),
	name CHARACTER VARYING (25),
	description CHARACTER VARYING (100),
	activeflag BOOLEAN,
	createddate TIMESTAMP WITH TIME ZONE,
	createduser BIGINT,
	modifieddate TIMESTAMP WITH TIME ZONE,
	modifieduser BIGINT,
	
	CONSTRAINT pk_nonscorablecode PRIMARY KEY (id),
	CONSTRAINT fk_nonscorablecode_cu FOREIGN KEY (createduser)
		REFERENCES aartuser (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT fk_nonscorablecode_mu FOREIGN KEY (modifieduser)
		REFERENCES aartuser (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

DO
$BODY$
BEGIN
	IF EXISTS (
		SELECT column_name 
		FROM information_schema.columns 
		WHERE table_name='studentresponsescore' AND column_name='scorable'
	) THEN
		RAISE NOTICE 'scorable found, skipping add';
	ELSE
		ALTER TABLE studentresponsescore ADD COLUMN scorable BOOLEAN DEFAULT TRUE;
	END IF;
	
	IF EXISTS (
		SELECT column_name 
		FROM information_schema.columns 
		WHERE table_name='studentresponsescore' AND column_name='nonscorablecodeid'
	) THEN
		RAISE NOTICE 'noscorablecodeid found, skipping add';
	ELSE
		ALTER TABLE studentresponsescore ADD COLUMN nonscorablecodeid BIGINT;
	END IF;
	
	IF EXISTS (
		SELECT column_name 
		FROM information_schema.columns 
		WHERE table_name='studentresponsescore' AND column_name='taskvariantexternalid'
	) THEN
		RAISE NOTICE 'taskvariantexternalid found, skipping add';
	ELSE
		ALTER TABLE studentresponsescore RENAME COLUMN taskvariantid TO taskvariantexternalid;
	END IF;
END
$BODY$;

ALTER TABLE studentresponsescore DROP CONSTRAINT IF EXISTS fk_studentresponsescore_nsc;
ALTER TABLE studentresponsescore ADD CONSTRAINT fk_studentresponsescore_nsc FOREIGN KEY (nonscorablecodeid)
		REFERENCES nonscorablecode (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

DROP INDEX IF EXISTS idx_studentresponsescore_raterorder;
CREATE INDEX idx_studentresponsescore_raterorder
	ON studentresponsescore USING btree (raterorder);

