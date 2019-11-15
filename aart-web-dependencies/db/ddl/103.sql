
--US13286 Name: Auto Registration Register students to Read Aloud and Braille test types.

CREATE TABLE enrollmenttesttypesubjectarea
(
	id bigserial NOT NULL,
	enrollmentid  bigint NOT NULL,
	testtypeid bigint NOT NULL,
	subjectareaid  bigint NOT NULL,
	createddate timestamp with time zone NOT NULL DEFAULT now(),
	modifieddate timestamp with time zone NOT NULL DEFAULT now(),
	createduser bigint,
	modifieduser bigint,
	activeflag boolean DEFAULT true,
	CONSTRAINT pk_enrollmenttesttypesubjectarea_id PRIMARY KEY (id),
	CONSTRAINT uk_enrollmenttesttypesubjectarea_ids UNIQUE (enrollmentid,testtypeid,subjectareaid),
	CONSTRAINT fk_enrollmenttesttypesubjectarea_enrollmentid_fk FOREIGN KEY (enrollmentid)
		REFERENCES enrollment (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT fk_enrollmenttesttypesubjectarea_testtypeid_fk FOREIGN KEY (testtypeid)
		REFERENCES testtype (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT fk_enrollmenttesttypesubjectarea_subjectareaid_fk FOREIGN KEY (subjectareaid)
		REFERENCES subjectarea (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION
);