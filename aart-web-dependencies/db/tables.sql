
CREATE TABLE organization (
	id bigserial NOT NULL PRIMARY KEY,
	parentorganizationid bigint REFERENCES organization(id),
	name varchar(75)
);

-- This is to hold name/value pairs that make up the policy (or properties) for a given organizaiton.
CREATE TABLE policy (
	id bigserial NOT NULL PRIMARY KEY,
	organizationid bigint REFERENCES organization(id),
	name varchar(75),
	data text
);


-- Table: aartuser
CREATE TABLE aartuser (
	id bigserial NOT NULL PRIMARY KEY,
	username character varying(32) NOT NULL,
	stateid character varying(32) NOT NULL,
	firstname character varying(32) NOT NULL,
	middlename character varying(32) NOT NULL,
	surname character varying(32) NOT NULL,	
	password character varying(16) NOT NULL,
	usertype character varying(8) NOT NULL,
    enabled smallint NOT NULL DEFAULT 1,
	CONSTRAINT username UNIQUE (username),
	CONSTRAINT login UNIQUE (firstname, surname, username, password)
);

-- Table: useraudit
CREATE TABLE useraudit (
  id bigserial NOT NULL,
  userid bigserial REFERENCES aartuser(id),
  useros character varying(256),
  logintime timestamp with time zone NOT NULL,
  logouttime timestamp with time zone,
  CONSTRAINT useraudit_pkey PRIMARY KEY (id)
);

-- These must go after user due to forgein keys
CREATE TABLE authorities
(
  id bigserial NOT NULL,
  username character varying(32) NOT NULL,
  authority character varying(32) NOT NULL,
  CONSTRAINT authorities_pkey PRIMARY KEY (id, authority),
  CONSTRAINT authorities_fkey FOREIGN KEY (id)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table: enrollment

-- DROP TABLE enrollment;

CREATE TABLE enrollment
(
  id bigserial NOT NULL,
  recordtype character varying(60) NOT NULL,
  aypschoolidentifier character varying(60) NOT NULL,
  residencedistrictidentifier character varying(60) NOT NULL,
  legallastname character varying(60) NOT NULL,
  legalmiddlename character varying(60) NOT NULL,
  generationcode character varying(60) NOT NULL,
  gender character varying(8) NOT NULL,
  dateofbirth date DEFAULT now(),
  currentgradelevel integer,
  localstudentidentifier character varying(60) NOT NULL,
  currentschoolyear integer,
  fundingschool character varying(60) NOT NULL,
  attendanceschoolprogramidentifier character varying(60) NOT NULL,
  schoolentrydate date DEFAULT now(),
  districtentrydate date DEFAULT now(),
  stateentrydate date DEFAULT now(),
  exitwithdrawaldate date DEFAULT now(),
  exitwithdrawaltype integer,
  specialcircumstancestransferchoice character varying(60) NOT NULL,
  comprehensiverace integer,
  primarydisabilitycode character varying(60) NOT NULL,
  giftedstudent character varying(60) NOT NULL,
  specialedprogramendingdate date DEFAULT now(),
  qualifiedfor504 character varying(60) NOT NULL,
  firstlanguage character varying(60) NOT NULL,
  testsubject character varying(60) NOT NULL,
  testtype character varying(60) NOT NULL,
  signingmode character varying(60) NOT NULL,
  encouragementmode character varying(60) NOT NULL,
  masking character varying(60),
  auditorybackground character varying(60) NOT NULL,
  additionaltestingtime boolean,
  breaks boolean,
  magnificationamount character varying(60) NOT NULL,
  invertcolorchoice boolean,
  foregroundcolorstring character varying(60) NOT NULL,
  backgroundcolorstring character varying(60) NOT NULL,
  colortintstring character varying(60),
  increasedwhitespacing character varying(60) NOT NULL,
  simplifiedlanguageenabled boolean,
  keywordhighlightenabled boolean,
  keywordtranslationenabled boolean,
  chunkingenabled boolean,
  itemtranslationdisplayenabled character varying(60) NOT NULL,
  linereaderenabled boolean,
  flaggingenabled boolean,
  scaffoldingenabled boolean,
  reducedanswerenabled boolean,
  negativesremovedenabled boolean,
  alternaterepresentationenabled boolean,
  readaloudenabled boolean,
  screenreader character varying(60) NOT NULL,
  content character varying(60) NOT NULL,
  braille character varying(60) NOT NULL,
  legalfirstname character varying(60) NOT NULL,
  CONSTRAINT enrollment_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE enrollment
  OWNER TO postgres;


CREATE OR REPLACE VIEW users AS 
 SELECT aartuser.username, aartuser.password, aartuser.enabled
   FROM aartuser;