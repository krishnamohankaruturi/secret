--US15900: Reports - Batch process submission

DROP SEQUENCE IF EXISTS reportprocess_id_seq;

CREATE SEQUENCE reportprocess_id_seq 
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;

DROP TABLE IF EXISTS reportprocess;

CREATE TABLE reportprocess
(
	id bigint NOT NULL,
	assessmentprogramid bigint NOT NULL,
	subjectid bigint NOT NULL,
	gradeid bigint NOT NULL,
	process character varying(500) NOT NULL,
	status character varying(200),
	successcount integer,
	failedcount integer,
	resultjson text,
	submissiondate timestamp with time zone NOT NULL DEFAULT now(),
	modifieddate timestamp with time zone DEFAULT now(),
	createduser bigint,
	modifieduser bigint,
	activeflag boolean NOT NULL DEFAULT true,
	CONSTRAINT reportprocess_pk PRIMARY KEY (id),
	CONSTRAINT reportprocess_createduser_fkey FOREIGN KEY (createduser) REFERENCES aartuser (id) 
	MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);


