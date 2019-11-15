-- First Contact tables.

-- Table: survey

-- DROP TABLE survey;

CREATE TABLE survey
(
  id bigserial NOT NULL,
  studentid bigint NOT NULL,
  surveyname character varying(100),
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser integer NOT NULL,
  CONSTRAINT pk_survey PRIMARY KEY (id ),
  CONSTRAINT fk_student FOREIGN KEY (studentid)
      REFERENCES student (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_student UNIQUE (studentid )
)
WITH (
  OIDS=FALSE
);

--changes to survey label.
--1. survey order.

ALTER TABLE surveylabels RENAME TO surveylabel;

ALTER TABLE surveylabel RENAME "position"  TO surveyorder;
ALTER TABLE surveylabel
   ALTER COLUMN surveyorder TYPE integer;
ALTER TABLE surveylabel
   ALTER COLUMN surveyorder SET NOT NULL;

--changes to surveyresponses.
-- 1. add response label.
-- 2. rename response sequence to response order.
   
ALTER TABLE surveyresponses ADD COLUMN responselabel character varying(10) NOT NULL DEFAULT 1;

update surveyresponses set responselabel = responsesequence||'';

ALTER TABLE surveyresponses
   ALTER COLUMN responselabel DROP DEFAULT;

ALTER TABLE surveyresponses RENAME responsesequence  TO responseorder;
ALTER TABLE surveyresponses
   ALTER COLUMN responseorder TYPE integer;
ALTER TABLE surveyresponses
   ALTER COLUMN responseorder SET NOT NULL;

ALTER TABLE surveyresponses RENAME TO surveyresponse;   

--adding the column to indicate whether a setter method is present.

ALTER TABLE fieldspecification ADD COLUMN iskeyvaluepairfield boolean NOT NULL DEFAULT false;

ALTER TABLE fieldspecification ALTER COLUMN iskeyvaluepairfield DROP DEFAULT;

-- Drop old table which we are not using.
drop table if exists studentssurveyresponses;

-- table for student survey response.   

CREATE TABLE studentsurveyresponse
(
  id bigserial NOT NULL,
  surveyid bigint NOT NULL,
  surveyresponseid bigint NOT NULL,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser integer NOT NULL,
  responsetext text,
  CONSTRAINT pk_student_survey_response PRIMARY KEY (id ),
  CONSTRAINT fk_student_survey_response_survey FOREIGN KEY (surveyid)
      REFERENCES survey (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_student_survey_response_survey_response FOREIGN KEY (surveyresponseid)
      REFERENCES surveyresponse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_student_survey_response UNIQUE (surveyid , surveyresponseid )
)
WITH (
  OIDS=FALSE
);


--CB Related tables.
ALTER TABLE taskvariant ADD COLUMN cognitivetaxonomyid2 bigint;
ALTER TABLE taskvariant ADD COLUMN cognitivetaxonomydimensionid2 bigint;
ALTER TABLE taskvariant ADD CONSTRAINT task_cognitivetaxonomydimensionid2_fkey FOREIGN KEY (cognitivetaxonomydimensionid2)
      REFERENCES cognitivetaxonomydimension (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE taskvariant ADD CONSTRAINT task_cognitivetaxonomyid2_fkey FOREIGN KEY (cognitivetaxonomyid2)
      REFERENCES cognitivetaxonomy (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
--media metadata
CREATE TABLE stimulusvariantattachmentmetadata
(
  attachmentid bigint NOT NULL,
  propertycode character varying(75) NOT NULL,
  propertyvalue character varying(75),
  createdate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  CONSTRAINT stimulusvariantattachmentmetadata_pkey PRIMARY KEY (attachmentid, propertycode),
  CONSTRAINT stimulusvariantattachmentmetadata_fk1 FOREIGN KEY (attachmentid)
      REFERENCES stimulusvariantattachment (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
 
--apip addition
ALTER TABLE contentgroup ADD COLUMN htmlelementid character varying(100);     

--new metadata associated to task
ALTER TABLE taskvariant ADD COLUMN essentialelementlinkageid bigint;
ALTER TABLE taskvariant ADD CONSTRAINT task_essentialelementlinkage_fkey FOREIGN KEY (essentialelementlinkageid)
  REFERENCES category (id) MATCH SIMPLE;
ALTER TABLE taskvariant ADD COLUMN testletaccessid bigint;
ALTER TABLE taskvariant ADD CONSTRAINT task_testletaccess_fkey FOREIGN KEY (testletaccessid)
  REFERENCES category (id) MATCH SIMPLE;
ALTER TABLE taskvariant ADD COLUMN alternatepathwayid bigint;
ALTER TABLE taskvariant ADD CONSTRAINT task_alternatepathway_fkey FOREIGN KEY (alternatepathwayid)
  REFERENCES category (id) MATCH SIMPLE;