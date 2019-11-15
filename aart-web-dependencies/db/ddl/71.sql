
--US12928 Define module structure
create table module
(
	id bigserial NOT NULL,
	name character varying(250) NOT NULL,
	description text NOT NULL,
	assessmentprogramid bigint NOT NULL,
	stateassignedid bigint,
	suggestedaudience character varying(2000),
	ceu integer,
	completiontime integer,
	statusid bigint NOT NULL,
	createddate timestamp with time zone NOT NULL DEFAULT now(),
	modifieddate timestamp with time zone NOT NULL DEFAULT now(),
	createduser bigint,
	modifieduser bigint,
	activeflag boolean DEFAULT true,
	CONSTRAINT pk_module PRIMARY KEY (id),
	CONSTRAINT module_assessmentprogram_fk FOREIGN KEY (assessmentprogramid)
		REFERENCES assessmentprogram (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT module_status_fk FOREIGN KEY (statusid)
		REFERENCES category (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT module_createduser_fk FOREIGN KEY (createduser)
		REFERENCES aartuser (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT module_modifieduser_fk FOREIGN KEY (modifieduser)
		REFERENCES aartuser (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT module_organization_fk FOREIGN KEY (stateassignedid)
		REFERENCES organization (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION	
);

ALTER TABLE module
  OWNER TO aart;
GRANT ALL ON TABLE module TO aart;
GRANT SELECT ON TABLE module TO aart_reader;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE module TO aart_user;