--ddl/711.sql

--F683

-- Adding new table foe dynamic exit codes
 CREATE TABLE studentexitcodes
(
  id bigserial NOT NULL,
  code integer NOT NULL,
  description text NOT NULL,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  modifieddate timestamp with time zone NOT NULL DEFAULT now(),
  createduser bigint,
  modifieduser bigint,
  activeflag boolean DEFAULT true,
  CONSTRAINT pk_studentexitcodes PRIMARY KEY (id),
  CONSTRAINT studentexitcodes_createduser_fk FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT studentexitcodes_modifieduser_fk FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_exitcode UNIQUE (code)
);


create table studentexitdetails (
	id bigserial, 
	studentid bigint not null,
	stateid bigint not null,
	assessmentprogramid bigint not null, 
	gradeid bigint not null, 
	subjectid bigint not null, 
	exitcode integer not null, 
	exitdate timestamp with time zone,
	reportyear bigint not null,
	createddate timestamp with time zone NOT NULL DEFAULT now(),
  	modifieddate timestamp with time zone NOT NULL DEFAULT now(),
  	createduser bigint,
  	modifieduser bigint,
  	activeflag boolean DEFAULT true,
	CONSTRAINT pk_studentexitdetails PRIMARY KEY (id),
  	CONSTRAINT studentexitdetails_createduser_fk FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  	CONSTRAINT studentexitdetails_modifieduser_fk FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT studentexitdetails_studentid_fk FOREIGN KEY (studentid)
      REFERENCES student (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  	CONSTRAINT studentexitdetails_assessmentprogramid_fk FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT studentexitdetails_gradeid_fk FOREIGN KEY (gradeid)
      REFERENCES gradecourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  	CONSTRAINT studentexitdetails_subjectid_fk FOREIGN KEY (subjectid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT studentexitdetails_stateid_fk FOREIGN KEY (stateid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


--F682

create table statespecificfile(
	id  SERIAL NOT NULL,
	filename character varying(255) NOT NULL,
	filedescription character varying(255) NOT NULL,
	filelocation character varying(1025) NOT NULL,
	stateid bigint NOT NULL,
	assessmentprogramid bigint NOT NULL,
	createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
	modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
	createduser bigint,
	activeflag boolean default true,
	modifieduser bigint,
	CONSTRAINT pk_statespecificfile PRIMARY KEY (id),
	CONSTRAINT statespecificfile_state_fk FOREIGN KEY (stateid)
	REFERENCES organization (id) MATCH SIMPLE
	ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT statespecificfile_ap_fk FOREIGN KEY (assessmentprogramid)
	REFERENCES assessmentprogram (id) MATCH SIMPLE
	ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT statespecificfile_createduser_fk FOREIGN KEY (createduser)
	REFERENCES aartuser (id) MATCH SIMPLE
	ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT statespecificfile_modifieduser_fk FOREIGN KEY (modifieduser)
	REFERENCES aartuser (id) MATCH SIMPLE
	ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX ssf_filename
    ON public.statespecificfile USING btree
    (filename ASC NULLS LAST);

CREATE INDEX ssf_filedescription
    ON public.statespecificfile USING btree
    (filedescription ASC NULLS LAST);

CREATE INDEX ssf_activeflag
    ON public.statespecificfile USING btree
    (activeflag ASC NULLS LAST);

CREATE INDEX ssf_stateid
    ON public.statespecificfile USING btree
    (stateid ASC NULLS LAST);

CREATE INDEX ssf_assessmentprogramid
    ON public.statespecificfile USING btree
    (assessmentprogramid ASC NULLS LAST);
