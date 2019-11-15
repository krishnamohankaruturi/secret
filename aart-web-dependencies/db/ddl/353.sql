CREATE SEQUENCE blueprint_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 15
  CACHE 1;

CREATE TABLE blueprint
(
  id bigint NOT NULL DEFAULT nextval('blueprint_id_seq'::regclass),
  contentareaid bigint,
  gradecourseid bigint,
  gradebandid bigint,
  criteria bigint,
  groupnumber bigint,
  numberrequired bigint,
  createduser integer,
  createddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser integer,
  modifieddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp without time zone,
  activeflag boolean DEFAULT true);
  
ALTER TABLE blueprint ADD CONSTRAINT blueprint_pkey PRIMARY KEY(id);
ALTER TABLE blueprint ADD CONSTRAINT blueprint_contentareaid_fkey FOREIGN KEY (contentareaid) REFERENCES contentarea (id);
ALTER TABLE blueprint ADD CONSTRAINT blueprint_gradecourseid_fkey FOREIGN KEY (gradecourseid) REFERENCES gradecourse (id);
ALTER TABLE blueprint ADD CONSTRAINT blueprint_gradebandid_fkey FOREIGN KEY (gradebandid) REFERENCES gradeband (id);

CREATE INDEX idx_blueprint_contentareaid ON blueprint USING btree (contentareaid);
CREATE INDEX idx_blueprint_gradecourseid ON blueprint USING btree (gradecourseid);
CREATE INDEX idx_blueprint_gradebandid ON blueprint USING btree (gradebandid);

CREATE TABLE blueprintessentialelements
(
  blueprintid bigint NOT NULL,
  essentialelementid bigint,
  essentialelement character varying(40),
  ordernumber bigint);

ALTER TABLE blueprintessentialelements ADD CONSTRAINT blueprintessentialelements_pkey PRIMARY KEY(blueprintid,essentialelementid);
ALTER TABLE blueprintessentialelements ADD CONSTRAINT blueprintessentialelements_essentialelementid_fkey FOREIGN KEY (essentialelementid) REFERENCES contentframeworkdetail (id);

CREATE INDEX idx_blueprintessentialelements_essentialelementid ON blueprintessentialelements USING btree (essentialelementid);

Alter table studenttrackerband Add Column essentialelementid bigint;

ALTER TABLE studenttrackerband ADD CONSTRAINT studenttrackerband_essentialelementid_fkey FOREIGN KEY (essentialelementid) REFERENCES contentframeworkdetail (id);

CREATE INDEX idx_studenttrackerband_essentialelementid ON studenttrackerband USING btree (essentialelementid);

ALTER TABLE specialcircumstance ADD COLUMN assessmentprogramid bigint;
ALTER TABLE specialcircumstance ADD COLUMN activeflag boolean;
ALTER TABLE specialcircumstance ADD COLUMN createdate timestamp with time zone;
ALTER TABLE specialcircumstance ADD COLUMN createduser integer;
ALTER TABLE specialcircumstance ADD COLUMN modifieddate timestamp with time zone;
ALTER TABLE specialcircumstance ADD COLUMN modifieduser integer;
ALTER TABLE specialcircumstance ALTER COLUMN createdate SET DEFAULT ('now'::text)::timestamp without time zone;
ALTER TABLE specialcircumstance ALTER COLUMN modifieddate SET DEFAULT ('now'::text)::timestamp without time zone;
ALTER TABLE specialcircumstance ALTER COLUMN activeflag SET DEFAULT true;

ALTER TABLE specialcircumstance
  ADD CONSTRAINT specialcircumstance_assessmentprogramid_fkey FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
CREATE INDEX idx_specialcircumstance_assessmentprogram
  ON specialcircumstance USING btree (assessmentprogramid);
 