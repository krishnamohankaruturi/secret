--ddl/544.sql

ALTER TABLE studentresponsescore DROP CONSTRAINT fk_studentresponsescore_nsc;
DROP TABLE IF EXISTS nonscorablecode;
DROP SEQUENCE IF EXISTS nonscorablecode_id_seq;

ALTER TABLE studentresponsescore ADD CONSTRAINT fk_studentresponsescore_nsc FOREIGN KEY (nonscorablecodeid)
		REFERENCES category (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION;

