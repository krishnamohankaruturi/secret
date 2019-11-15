
--US13091 and US13092
create table complexitybandrules(
	id bigserial NOT NULL,
	complexitybandid bigint NOT NULL,
	rule text NOT NULL,
	complexitybandtypeid bigint NOT NULL,
	CONSTRAINT pk_surveyresponsescomplexitybands PRIMARY KEY (id),
	CONSTRAINT srcb_complexityband__fk FOREIGN KEY (complexitybandid)
	      REFERENCES category (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT srcb_complexitybandtype__fk FOREIGN KEY (complexitybandtypeid)
	      REFERENCES category (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE complexitybandrules
  OWNER TO aart;
GRANT ALL ON TABLE complexitybandrules TO aart;
GRANT SELECT ON TABLE complexitybandrules TO aart_reader;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE complexitybandrules TO aart_user;