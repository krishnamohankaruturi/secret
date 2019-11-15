CREATE SEQUENCE uploadfile_id_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;

ALTER SEQUENCE uploadfile_id_seq OWNER TO aart;
 
CREATE TABLE uploadfile
(
	id BIGINT NOT NULL DEFAULT nextval('uploadfile_id_seq'::regclass),
	filename TEXT NOT NULL,
	statusid BIGINT NOT NULL,
	jsondata TEXT,
	createdate TIMESTAMP WITH TIME ZONE DEFAULT ('now'::text)::TIMESTAMP WITH TIME ZONE,
	createduser BIGINT,
	modifieddate TIMESTAMP WITH TIME ZONE DEFAULT ('now'::text)::TIMESTAMP WITH TIME ZONE,
	modifieduser BIGINT,
	CONSTRAINT uploadfile_pkey PRIMARY KEY (id),
	CONSTRAINT uploadfile_fkey1 FOREIGN KEY (statusid) REFERENCES category (id)
);