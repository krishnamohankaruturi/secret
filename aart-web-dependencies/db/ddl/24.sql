CREATE TABLE surveySection
(
  id bigserial NOT NULL,
  surveySectionName character varying(75) NOT NULL,
  surveySectionCode character varying(75) NOT NULL,
  surveySectionDescription character varying(150),
  parentSurveySectionId bigint,
  createdDate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  createdUser integer,
  activeFlag boolean DEFAULT true,
  modifiedDate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifiedUser integer,
  CONSTRAINT surveySection_pk PRIMARY KEY (id ),
  CONSTRAINT fk_surveySection_crdusr FOREIGN KEY (createdUser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_survey_section_updusr FOREIGN KEY (modifiedUser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT parent_survey_section_fk FOREIGN KEY (parentSurveySectionId)
      REFERENCES surveySection (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT survey_section_code_uk UNIQUE (surveySectionCode ),
  CONSTRAINT survey_section_name_uk UNIQUE (surveySectionName )
)
WITH (
  OIDS=FALSE
);
  
ALTER TABLE surveylabel ADD COLUMN optional boolean NOT NULL DEFAULT true;
ALTER TABLE surveylabel ADD COLUMN globalPageNum integer NOT NULL DEFAULT 0;

ALTER TABLE surveylabel ADD COLUMN surveysectionid bigint;


--
CREATE TABLE surveyPageStatus
(
  id bigserial NOT NULL,
  isCompleted boolean,
  surveyId bigint not null,
  globalPageNum integer not null,
  createdDate timestamp with time zone
  DEFAULT ('now'::text)::timestamp without time zone,
  createdUser integer,
  activeFlag boolean DEFAULT true,
  modifiedDate timestamp with time zone
  DEFAULT ('now'::text)::timestamp without time zone,
  modifiedUser integer,
  CONSTRAINT survey_page_status_pk PRIMARY KEY (id ),
  CONSTRAINT fk_survey_page_status_crdusr FOREIGN KEY (createdUser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_survey_page_status_updusr FOREIGN KEY (modifiedUser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT page_status_survey_fk FOREIGN KEY (surveyId)
      REFERENCES survey (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

-- CB publishing changes. R7 - I3

ALTER TABLE testletstimulusvariants ADD COLUMN sortorder integer;
ALTER TABLE testletstimulusvariants ADD COLUMN groupnumber integer NOT NULL DEFAULT 1;
ALTER TABLE testletstimulusvariants ADD COLUMN testletstimulusvariantid bigint;
CREATE SEQUENCE testletstimulusvariantid_seq;
UPDATE testletstimulusvariants SET testletstimulusvariantid = nextval('testletstimulusvariantid_seq');
 
ALTER TABLE testletstimulusvariants ADD CONSTRAINT testletstimulusvariant_pkey PRIMARY KEY(testletstimulusvariantid);
ALTER TABLE testletstimulusvariants ALTER COLUMN testletstimulusvariantid SET NOT NULL;
 
ALTER TABLE testsectionstaskvariants ADD COLUMN sortorder integer;
ALTER TABLE testsectionstaskvariants ADD COLUMN groupnumber integer NOT NULL DEFAULT 1;
 
--commented because of build failure.
--ALTER TABLE contentgroup ADD COLUMN stimulusvariantid bigint;
ALTER TABLE contentgroup ADD COLUMN testid bigint;
ALTER TABLE contentgroup ADD COLUMN highlighted boolean;
ALTER TABLE accessibilityfile ADD COLUMN stimulusvariantid bigint;

CREATE SEQUENCE textaccommodationid_seq;

CREATE TABLE textaccommodation
(
  id bigint NOT NULL DEFAULT nextval('textaccommodationid_seq'),
  externalid bigint NOT NULL,
  varianttypeid bigint,
  accommodationtext character varying(200),
  sortorder integer,
  inclusion boolean,
  contentgroupid bigint,
  accommodationtypeid bigint,
  createuserid bigint,
  createdate timestamp without time zone,
  modifieddate timestamp without time zone,
  modifieduserid bigint,
  originationcode character varying(20) NOT NULL,
  CONSTRAINT textaccommodation_pkey PRIMARY KEY (id),
  CONSTRAINT textaccommodation_fk1 FOREIGN KEY (contentgroupid)
      REFERENCES contentgroup (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT textaccommodation_fk2 FOREIGN KEY (varianttypeid)
      REFERENCES category (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT textaccommodation_fk3 FOREIGN KEY (accommodationtypeid)
      REFERENCES category (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
