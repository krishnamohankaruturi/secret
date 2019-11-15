--dml/591.sql
-- script from changepond
--US19213
ALTER TABLE rubriccategory ADD COLUMN maximumscore bigint;

--- US19029 ---
CREATE TABLE activationemailtemplate
(
  id bigserial NOT NULL,
  templatename character varying(200),
  assessmentprogramid bigint,
  emailsubject text,
  emailbody text,
  includeeplogo boolean DEFAULT false,
  allstates boolean DEFAULT false,
  isdefault boolean DEFAULT false,
  createduser integer,
  modifieduser integer,
  activeflag boolean DEFAULT true,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  CONSTRAINT activation_email_template_pk PRIMARY KEY (id),
  CONSTRAINT fk_activationemailtemplate_crdusr FOREIGN KEY (createduser)
  REFERENCES aartuser (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_activationemailtemplate_updusr FOREIGN KEY (modifieduser)
  REFERENCES aartuser (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX activationemailtemplate_id_index ON activationemailtemplate (id);
CREATE INDEX activationemailtemplate_assessmentprogramid_index ON activationemailtemplate (assessmentprogramid);
CREATE INDEX activationemailtemplate_isdefault_index ON activationemailtemplate (isdefault);

CREATE TABLE activationemailtemplatestate
(
  id bigserial NOT NULL,
  templateid bigint,  
  stateid bigint,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieduser integer NOT NULL,  
  CONSTRAINT activation_email_template_state_pk PRIMARY KEY (id),  
  CONSTRAINT fk_activationemailtemplatestate_templateid FOREIGN KEY (templateid)
  REFERENCES activationemailtemplate (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,  
  CONSTRAINT fk_activationemailtemplatestate_stateid FOREIGN KEY (stateid)
  REFERENCES organization (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,  
  CONSTRAINT fk_activationemailtemplatestate_crdusr FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_activationemailtemplatestate_updusr FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX activationemailtemplatestate_id_index ON activationemailtemplatestate (id);
CREATE INDEX activationemailtemplatestate_templateid_index ON activationemailtemplatestate (templateid);
CREATE INDEX activationemailtemplatestate_stateid_index ON activationemailtemplatestate (stateid);
CREATE INDEX activationemailtemplatestate_activeflag_index ON activationemailtemplatestate (activeflag);

CREATE TABLE projectedtestingdate
(
  id bigserial NOT NULL,
  assessmentprogramid bigint NOT NULL,
  stateid bigint NOT NULL,
  districtid bigint NOT NULL,
  schoolid bigint NOT NULL,
  testdate date NOT NULL,
  monthName character varying(20),
  createduser bigint,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieduser bigint,  
  activeflag boolean NOT NULL,
  CONSTRAINT projectedtestingdate_pkey PRIMARY KEY (id)
);

CREATE INDEX idx_projectedtestingdate_activeflag ON projectedtestingdate (activeflag);
CREATE INDEX idx_projectedtestingdate_schoolid ON projectedtestingdate (schoolid);
CREATE INDEX idx_projectedtestingdate_id ON projectedtestingdate (id);

