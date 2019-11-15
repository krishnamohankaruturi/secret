-- Remove redundant data for autoregistration

DROP TABLE autoregistrationcriteria;

CREATE TABLE autoregistrationcriteria(
	id bigserial NOT NULL,
	assessmentprogramid bigint NOT NULL,
	testingprogramid bigint NOT NULL,
	assessmentid bigint NOT NULL,
	contentareatesttypesubjectareaid bigint NOT NULL,
	createduser integer,
	createdate timestamp with time zone,
	modifieddate timestamp with time zone,
	modifieduser integer,
	CONSTRAINT pk_autoregistrationcriteria_id PRIMARY KEY (id),
	CONSTRAINT fk_autoregistrationcriteria_contentareatesttypesubjectareaid FOREIGN KEY (contentareatesttypesubjectareaid)
		REFERENCES contentareatesttypesubjectarea (id) MATCH FULL
			ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT fk_autoregistrationcriteria_assessmentprogramid FOREIGN KEY (assessmentprogramid)
		REFERENCES assessmentprogram (id) MATCH FULL
			ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT fk_autoregistrationcriteria_testingprogramid FOREIGN KEY (testingprogramid)
		REFERENCES testingprogram (id) MATCH FULL
			ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT fk_autoregistrationcriteria_assessmentid FOREIGN KEY (assessmentid)
		REFERENCES assessment (id) MATCH FULL
			ON UPDATE NO ACTION ON DELETE NO ACTION,	
	CONSTRAINT uk_autoregistrationcriteria_ap_tp_asst_cattsa UNIQUE (assessmentprogramid,testingprogramid,assessmentid,contentareatesttypesubjectareaid)
)WITH (
  OIDS=FALSE
);