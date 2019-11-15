
-- R9 -Iter 1
--US12922 Name: Professional Development: Landing Page for non-admin user

create table activity
(
	id bigserial NOT NULL,	
	description text NOT NULL,
	userid bigint NOT NULL,
	moduleid bigint,	
	createddate timestamp with time zone NOT NULL DEFAULT now(),
	modifieddate timestamp with time zone NOT NULL DEFAULT now(),
	createduser bigint,
	modifieduser bigint,
	activeflag boolean DEFAULT true,
	CONSTRAINT pk_activity PRIMARY KEY (id),
	CONSTRAINT activity_userid_fk FOREIGN KEY (userid)
		REFERENCES aartuser (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT activity_moduleid_fk FOREIGN KEY (moduleid)
		REFERENCES module (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT activity_createduser_fk FOREIGN KEY (createduser)
		REFERENCES aartuser (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT activity_modifieduser_fk FOREIGN KEY (modifieduser)
		REFERENCES aartuser (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION	
);

ALTER TABLE activity OWNER TO aart;
GRANT ALL ON TABLE activity TO aart;
GRANT SELECT ON TABLE activity TO aart_reader;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE activity TO aart_user;
