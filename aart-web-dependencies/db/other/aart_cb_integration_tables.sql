


--aart_cb_integration_tables.sql
CREATE TABLE stimulusvariant
(
  id bigserial NOT NULL,
  externalid bigint,
  version int,
  stimulusformatid bigint,
  stimuluscontent text,
  stimulustitle character varying(75),
  gradecourseid bigint,
  testingprogramid bigint,
  contentareaid bigint,
  createdate timestamp without time zone default LOCALTIMESTAMP,
  modifieddate timestamp without time zone default LOCALTIMESTAMP,
  originationcode character varying(20),
  CONSTRAINT stimulusvariant_pkey PRIMARY KEY (id ),
  CONSTRAINT stimulusvariant_gradecourseid_fkey FOREIGN KEY (gradecourseid)
		REFERENCES gradecourse (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT stimulusvariant_testingprogramid_fkey FOREIGN KEY (testingprogramid)
		REFERENCES testingprogram (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT stimulusvariant_contentareaid_fkey FOREIGN KEY (contentareaid)
		REFERENCES contentarea (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT stimulusvariant_stimulusformatid_fkey FOREIGN KEY (stimulusformatid)
		REFERENCES category (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION	
);

CREATE TABLE stimulusvariantattachment
(
  id bigserial NOT NULL,
  externalid bigint,
  attachmentname character varying(75),
  filename character varying(75),
  filelocation character varying(75),
  filesize int,
  filetype character varying(75),
  stimulusvariantid bigint not null,
  createdate timestamp without time zone default LOCALTIMESTAMP,
  modifieddate timestamp without time zone default LOCALTIMESTAMP,
  originationcode character varying(20),
  CONSTRAINT stimulusvariantattachment_pkey PRIMARY KEY (id),
  CONSTRAINT stimulusvariantattachment_stimulusvariantid_fkey FOREIGN KEY (stimulusvariantid)
		REFERENCES stimulusvariant (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
  
);


CREATE TABLE foilsstimulusvariants
(
  foilid bigint NOT NULL,
  stimulusvariantid bigint not null,
  CONSTRAINT foilsstimulusvariants_pkey PRIMARY KEY (foilid, stimulusvariantid),
  CONSTRAINT foilsstimulusvariants_foilid_fkey FOREIGN KEY (foilid)
		REFERENCES foil (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT foilsstimulusvariants_stimulusvariantid_fkey FOREIGN KEY (stimulusvariantid)
		REFERENCES stimulusvariant (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);



CREATE TABLE taskvariantsstimulusvariants
(
  taskvariantid bigint NOT NULL,
  stimulusvariantid bigint NOT NULL,
  CONSTRAINT taskvariantsstimulusvariants_pkey PRIMARY KEY (taskvariantid, stimulusvariantid),
  CONSTRAINT taskvariantsstimulusvariants_taskvariantid_fkey FOREIGN KEY (taskvariantid)
		REFERENCES taskvariant (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT taskvariantsstimulusvariants_stimulusvariantid_fkey FOREIGN KEY (stimulusvariantid)
		REFERENCES stimulusvariant (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
 );

CREATE TABLE taskvariantstools
(
  taskvariantid bigint NOT NULL,
  toolid bigint NOT NULL,
  CONSTRAINT taskvariantstools_pkey PRIMARY KEY (taskvariantid, toolid),
  CONSTRAINT taskvariantstools_taskvariantid_fkey FOREIGN KEY (taskvariantid)
		REFERENCES taskvariant (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT taskvariantstools_toolid_fkey FOREIGN KEY (toolid)
		REFERENCES tool (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
 ); 
 

CREATE TABLE taskvariantitemusage
(
  taskvariantid bigint NOT NULL,
  itemusageid bigint NOT NULL,
  CONSTRAINT taskvariantitemusage_pkey PRIMARY KEY (taskvariantid, itemusageid),
  CONSTRAINT taskvariantitemusage_taskvariantid_fkey FOREIGN KEY (taskvariantid)
		REFERENCES taskvariant (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT taskvariantitemusage_itemusageid_fkey FOREIGN KEY (itemusageid)
		REFERENCES category (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE testsectionstools
(
  testsectionid bigint NOT NULL,
  toolid bigint NOT NULL,
  CONSTRAINT testsectionstools_pkey PRIMARY KEY (testsectionid, toolid),
  CONSTRAINT testsectionstools_testsectionid_fkey FOREIGN KEY (testsectionid)
		REFERENCES testsection (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testsectionstools_toolid_fkey FOREIGN KEY (toolid)
		REFERENCES tool (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
 ); 


CREATE TABLE testsectionsrules
(
  testsectionid bigint NOT NULL,
  ruleid bigint NOT NULL,
  navigation boolean,
  CONSTRAINT testsectionsrules_pkey PRIMARY KEY (testsectionid, ruleid),
  CONSTRAINT testsectionsrules_testsectionid_fkey FOREIGN KEY (testsectionid)
		REFERENCES testsection (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testsectionsrules_ruleid_fkey FOREIGN KEY (ruleid)
		REFERENCES category (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

