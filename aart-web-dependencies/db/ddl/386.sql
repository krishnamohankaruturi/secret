-- US15863: Summative Report upload

CREATE SEQUENCE batchupload_id_seq 
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;

CREATE TABLE batchupload
(
	id bigint NOT NULL,
	filename text NOT NULL,
	filepath text NOT NULL,
	uploadtype character varying(100) NOT NULL,
	assessmentprogramid bigint NOT NULL,
	contentareaid bigint NOT NULL,
	status character varying(200),
	successcount integer,
	failedcount integer,
	resultjson text,
	submissiondate timestamp with time zone NOT NULL DEFAULT now(),
	createddate timestamp with time zone DEFAULT now(),
	modifieddate timestamp with time zone DEFAULT now(),
	createduser bigint,
	activeflag boolean NOT NULL DEFAULT true,
	schoolyear integer,
	CONSTRAINT batchupload_pk PRIMARY KEY (id),
		CONSTRAINT batchupload_createduser_fkey FOREIGN KEY (createduser)
		REFERENCES aartuser (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);
