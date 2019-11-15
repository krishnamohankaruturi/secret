
--US12808 Name:  Auto registration - Auto register General population 

CREATE TABLE subjectarea(
	id bigserial NOT NULL,
	subjectareacode character varying(50) NOT NULL,
	subjectareaname character varying(100) NOT NULL,
	createduser integer,
	createdate timestamp with time zone,
	modifieddate timestamp with time zone,
	modifieduser integer,
	CONSTRAINT pk_subjectarea_id PRIMARY KEY (id),
	CONSTRAINT uk_subjectarea_subjectareacode UNIQUE (subjectareacode)
)WITH (
  OIDS=FALSE
);

CREATE TABLE testtype(
	id bigserial NOT NULL,
	testtypecode character varying(50) NOT NULL,
	testtypename character varying(100) NOT NULL,
	assessmentid bigint NOT NULL,
	createduser integer,
	createdate timestamp with time zone,
	modifieddate timestamp with time zone,
	modifieduser integer,
	CONSTRAINT pk_testtype_id PRIMARY KEY (id),
	CONSTRAINT uk_testtype_testtypecode UNIQUE (testtypecode,assessmentid),
	CONSTRAINT fk_testtypesubjectarea_assessmentid FOREIGN KEY (assessmentid)
		REFERENCES assessment (id) MATCH FULL
			ON UPDATE NO ACTION ON DELETE NO ACTION
)WITH (
  OIDS=FALSE
);

CREATE TABLE testtypesubjectarea(
	id bigserial NOT NULL,
	testtypeid bigint NOT NULL,
	subjectareaid bigint NOT NULL,
	
	createduser integer,
	createdate timestamp with time zone,
	modifieddate timestamp with time zone,
	modifieduser integer,
	CONSTRAINT pk_testtypesubjectarea_id PRIMARY KEY (id),
	CONSTRAINT fk_testtypesubjectarea_testtypeid FOREIGN KEY (testtypeid)
		REFERENCES testtype (id) MATCH FULL
			ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT fk_testtypesubjectarea_subjectareaid FOREIGN KEY (subjectareaid)
		REFERENCES subjectarea (id) MATCH FULL
			ON UPDATE NO ACTION ON DELETE NO ACTION,	
	CONSTRAINT uk_testtypesubjectarea_testtypeid_subjectareaid UNIQUE (testtypeid,subjectareaid)
)WITH (
  OIDS=FALSE
);


CREATE TABLE contentareatesttypesubjectarea(
	id bigserial NOT NULL,
	contentareaid bigint,
	testtypesubjectareaid bigint NOT NULL,
	createduser integer,
	createdate timestamp with time zone,
	modifieddate timestamp with time zone,
	modifieduser integer,
	CONSTRAINT pk_contentareatesttypesubjectarea_id PRIMARY KEY (id),
	CONSTRAINT fk_contentareatesttypesubjectarea_contentareaid FOREIGN KEY (contentareaid)
		REFERENCES contentarea (id) MATCH FULL
			ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT fk_contentareatesttypesubjectarea_testtypesubjectareaid FOREIGN KEY (testtypesubjectareaid)
		REFERENCES testtypesubjectarea (id) MATCH FULL
			ON UPDATE NO ACTION ON DELETE NO ACTION,	
	CONSTRAINT uk_contentareatesttypesubjectarea_contentareaid_testtypesubjectareaid UNIQUE (contentareaid,testtypesubjectareaid)
)WITH (
  OIDS=FALSE
);