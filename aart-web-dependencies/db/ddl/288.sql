
-- 287.sql


CREATE TABLE itimclog
(
	id bigserial NOT NULL,
	requestid bigint not null,
	fromdate timestamp with time zone,
	todate timestamp with time zone,
	response text,
	errors TEXT,
	actiontype character varying(50) NOT NULL,
	createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
	createduser bigint NOT NULL,
	CONSTRAINT itimclog_pkey PRIMARY KEY (id)
);



 CREATE TABLE studentnodeprobability
(
	id bigserial NOT NULL,
	requestid bigint not null,
	studentid bigint not null,
	nodeid bigint not null,
	probability numeric(6,3) not null,
	createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
	createduser bigint NOT NULL,
	CONSTRAINT studentnodeprobability_pkey PRIMARY KEY (id),
	CONSTRAINT uk_studentnodeprobability UNIQUE (studentid, nodeid),
	CONSTRAINT studentnodeprobability_studentid_fkey FOREIGN KEY (studentid)
	REFERENCES student (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);
