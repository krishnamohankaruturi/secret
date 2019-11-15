--INFO cb related tables.
CREATE TABLE contentarea (
    id bigserial NOT NULL,
    externalid bigint,
    sortorder bigint,
    name character varying(100),
    abbreviatedname character varying(10),
	createdate timestamp without time zone default LOCALTIMESTAMP,
    modifieddate timestamp without time zone default LOCALTIMESTAMP,
	originationCode character varying(20),
	CONSTRAINT contentarea_pkey PRIMARY KEY (id)
);

create table gradecourse
(
  id bigserial NOT NULL,
  externalid bigint,
  name character varying(150),
  abbreviatedname character varying(10),
  ordinality bigint,
  gradelevel integer,
  shortdescription character varying(160),
  longdescription text,
  createdate timestamp without time zone default LOCALTIMESTAMP,
  modifieddate timestamp without time zone default LOCALTIMESTAMP,
  originationCode character varying(20),
  CONSTRAINT gradecourse_pkey PRIMARY KEY (id )
);

CREATE TABLE frameworktype(
	id bigserial not null,
	externalid bigint,
	name character varying(100),
	createdate timestamp without time zone default LOCALTIMESTAMP,
	modifieddate timestamp without time zone default LOCALTIMESTAMP,
	originationcode character varying(20),
	CONSTRAINT frameworktype_pkey PRIMARY KEY (id)
);

CREATE TABLE frameworklevel(
	id bigserial not null,
	externalid bigint,
	title character varying(80),
	level bigint,
	frameworktypeid bigint not null,
	createdate timestamp without time zone default LOCALTIMESTAMP,
	modifieddate timestamp without time zone default LOCALTIMESTAMP,
	originationcode character varying(20),
	constraint frameworklevel_pkey PRIMARY KEY (id),
	CONSTRAINT frameworklevel_frameworktypeid_fkey FOREIGN KEY (frameworktypeid)
		REFERENCES frameworktype (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE cognitivetaxonomy
(
  id bigserial NOT NULL,
  externalid bigint,
  name character varying(200),
  dimensions bigint,
  yearadopted bigint,
  citation character varying(1000),
  abbreviatedname character varying(75),
  shortdescription character varying(160),
  longdescription text,
  createdate timestamp without time zone default LOCALTIMESTAMP,
  modifieddate timestamp without time zone default LOCALTIMESTAMP,
  originationcode character varying(20),
  CONSTRAINT cognitivetaxonomy_pkey PRIMARY KEY (id )
);

CREATE TABLE cognitivetaxonomydimension
(
  id bigserial NOT NULL,
  externalid bigint,
  name character varying(200),
  dimension bigint,
  num bigint,
  definition character varying(2000),
  cognitivetaxonomyid bigint,
  abbreviatedname character varying(75),
  level bigint,
  shortdescription character varying(160),
  longdescription text,
  createdate timestamp without time zone default LOCALTIMESTAMP,
  modifieddate timestamp without time zone default LOCALTIMESTAMP,
  originationcode character varying(20),
  CONSTRAINT cognitivetaxonomydimension_pkey PRIMARY KEY (id),
  CONSTRAINT cognitivetaxonomydimension_cognitivetaxonomyid_fkey FOREIGN KEY (cognitivetaxonomyid)
		REFERENCES cognitivetaxonomy (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
  
);

CREATE TABLE tasktype
(
  id bigserial NOT NULL,
  externalid bigint,
  code character varying(75),
  name character varying(75),
  standalone boolean,
  grouped boolean,
  createdate timestamp without time zone default LOCALTIMESTAMP,
  modifieddate timestamp without time zone default LOCALTIMESTAMP,
  originationCode character varying(20),
  CONSTRAINT tasktype_pkey PRIMARY KEY (id)
);

CREATE TABLE tasklayout
(
  id bigserial NOT NULL,
  externalid bigint,
  layoutname character varying(75),
  layoutaliasname character varying(75),
  layoutcode character varying(75),
  layouthtml character varying(75),
  defaultlayout boolean,
  tasktypeid bigint,
  createdate timestamp without time zone default LOCALTIMESTAMP,
  modifieddate timestamp without time zone default LOCALTIMESTAMP,
  originationCode character varying(20),
  CONSTRAINT tasklayout_pkey PRIMARY KEY (id),
  CONSTRAINT tasklayout_tasktypeid_fkey FOREIGN KEY (tasktypeid)
	REFERENCES tasktype (id) MATCH SIMPLE
	ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE tasklayoutformat(
	id bigserial not null,
	externalid bigint,
	formatname character varying(75),
	formatcode character varying(75),
	formatcharacters character varying(75),
	alternatenumbering boolean,
	createdate timestamp without time zone default LOCALTIMESTAMP,
	modifieddate timestamp without time zone default LOCALTIMESTAMP,
	originationcode character varying(20),
	CONSTRAINT tasklayoutformat_pkey PRIMARY KEY (id)
);


CREATE TABLE testingprogram
(
  id bigserial NOT NULL,
  externalid bigint,
  programname character varying(50),
  programdescription character varying(200),
  assessmentprogramid bigint,
  createdate timestamp without time zone default LOCALTIMESTAMP,
  modifieddate timestamp without time zone default LOCALTIMESTAMP,
  originationcode character varying(20),
  CONSTRAINT testingprogram_pkey PRIMARY KEY (id ),
  CONSTRAINT testingprogram_assessmentprogramid_fkey FOREIGN KEY (assessmentprogramid)
		REFERENCES assessmentprogram (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);




CREATE TABLE testcollection (
	id bigserial NOT NULL,
	name character varying(75),
	randomizationtype character varying(20),
	gradecourseid bigint,
	contentareaid bigint,
	createdate timestamp without time zone default LOCALTIMESTAMP,
	modifieddate timestamp without time zone default LOCALTIMESTAMP,
	originationcode character varying(20),
	CONSTRAINT testcollection_pkey PRIMARY KEY (id),
	CONSTRAINT testcollection_gradecourseid_fkey FOREIGN KEY (gradecourseid)
		REFERENCES gradecourse (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT testcollection_contentareaid_fkey FOREIGN KEY (contentareaid)
		REFERENCES contentarea (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE testcollectionstests (
	testcollectionid bigint NOT NULL,
	testid bigint NOT NULL,
	CONSTRAINT testcollectiontests_pkey PRIMARY KEY (testcollectionid, testid),
	CONSTRAINT testcollectionstests_testcollectionid_fkey FOREIGN KEY (testcollectionid)
		REFERENCES testcollection (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT testcollectionstests_testid_fkey FOREIGN KEY (testid)
		REFERENCES test (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);




create table contentframework (
    id bigserial not null,
    externalid bigint,
	frameworktypeid bigint not null,
    gradecourseid bigint not null,
    contentareaid bigint not null,
	title character varying(75),
	content character varying(75),
	description character varying(75),
    sortorder bigint,
	createdate timestamp without time zone default LOCALTIMESTAMP,
    modifieddate timestamp without time zone default LOCALTIMESTAMP,
	originationCode character varying(20),
    CONSTRAINT contentframework_pkey PRIMARY KEY (id),
	CONSTRAINT contentframework_frameworktypeid_fkey FOREIGN KEY (frameworktypeid)
		REFERENCES frameworktype (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT contentframework_gradecourseid_fkey FOREIGN KEY (gradecourseid)
		REFERENCES gradecourse (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT contentframework_contentareaid_fkey FOREIGN KEY (contentareaid)
		REFERENCES contentarea (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

create table contentframeworkdetail (
    id bigserial NOT NULL,
    externalid bigint,
	sortorder bigint,
    name character varying(10),
    contentcode character varying(20),
    description character varying(700),
    comments character varying(250),
	frameworklevelid bigint not null,
	contentframeworkid bigint not null,
    parentcontentframeworkdetailid bigint,
    createdate timestamp without time zone default LOCALTIMESTAMP,
    modifieddate timestamp without time zone default LOCALTIMESTAMP,
	originationCode character varying(20),
	CONSTRAINT contentframeworkdetail_pkey PRIMARY KEY (id),
	CONSTRAINT contentframeworkdetail_contentframeworkid_fkey FOREIGN KEY (contentframeworkid)
		REFERENCES contentframework (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);



CREATE TABLE tool
(
  id bigserial NOT NULL,
  externalid bigint,
  tooltypeid bigint,
  name character varying(200),
  codename character varying(30),
  description text,
  shortdescription character varying(160),
  longdescription text,
  createdate timestamp without time zone default LOCALTIMESTAMP,
  modifieddate timestamp without time zone default LOCALTIMESTAMP,
  originationCode character varying(20),
  CONSTRAINT tool_pkey PRIMARY KEY (id)
);

CREATE TABLE foil
(
  id bigserial NOT NULL,
  externalid bigint,
  foiltext character varying(600),
  parentfoilid bigint,
  createdate timestamp without time zone default LOCALTIMESTAMP,
  modifieddate timestamp without time zone default LOCALTIMESTAMP,
  originationCode character varying(20),
  CONSTRAINT foil_pkey PRIMARY KEY (id ),
  CONSTRAINT foil_parentfoilid_fkey FOREIGN KEY (parentfoilid)
		REFERENCES foil (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE task
(
  id bigserial NOT NULL,
  externalid bigint,
  externaltaskvariantrevisionid bigint,
  taskname character varying(75),
  variantname character varying(75),
  taskstem character varying(3000),
  itemname character varying(75),
  itemdescription character varying(75),
  contentframeworkdetailid bigint,
  tasktypeid bigint,
  tasklayoutid bigint,
  tasklayoutformatid bigint,
  contentareaid bigint,
  gradecourseid bigint,
  testingprogramid bigint,
  frameworktypeid bigint,
  taskdifficultyid bigint,
  cognitivetaxonomyid bigint,
  cognitivetaxonomydimensionid bigint,
  createdate timestamp without time zone default LOCALTIMESTAMP,
  modifieddate timestamp without time zone default LOCALTIMESTAMP,
  originationCode character varying(20),
  CONSTRAINT task_pkey PRIMARY KEY (id ),
  CONSTRAINT task_contentframeworkdetailid_fkey FOREIGN KEY (contentframeworkdetailid)
		REFERENCES contentframeworkdetail (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT task_tasktypeid_fkey FOREIGN KEY (tasktypeid)
		REFERENCES tasktype (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT task_tasklayoutid_fkey FOREIGN KEY (tasklayoutid)
		REFERENCES tasklayout (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT task_tasklayoutformatid_fkey FOREIGN KEY (tasklayoutformatid)
		REFERENCES tasklayoutformat (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT task_contentareaid_fkey FOREIGN KEY (contentareaid)
		REFERENCES contentarea (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT task_gradecourse_fkey FOREIGN KEY (gradecourseid)
		REFERENCES gradecourse (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT task_frameworktypeid_fkey FOREIGN KEY (frameworktypeid)
		REFERENCES frameworktype (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT task_cognitivetaxonomyid_fkey FOREIGN KEY (cognitivetaxonomyid)
		REFERENCES cognitivetaxonomy (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT task_cognitivetaxonomydimensionid_fkey FOREIGN KEY (cognitivetaxonomydimensionid)
		REFERENCES cognitivetaxonomydimension (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT task_testingprogramid_fkey FOREIGN KEY (testingprogramid)
		REFERENCES testingprogram (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
		
);

create table tasksfoils (
	taskid bigint not null,
	foilid bigint not null,
	externaltaskid bigint,
	externalfoilid bigint,
	originationcode character varying(20),
	CONSTRAINT tasksfoils_pk PRIMARY KEY (taskid, foilid),
	CONSTRAINT tasksfoils_taskid_fkey FOREIGN KEY (taskid)
		REFERENCES task (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT tasksfoils_foilid_fkey FOREIGN KEY (foilid)
		REFERENCES foil (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);







CREATE TABLE testsection
(
  id bigserial NOT NULL,
  externalid bigint,
  externaltestid bigint,
  testid bigint NOT NULL,
  testsectionname character varying(75),
  numberoftestitems integer,
  instructions text,
  helpnotes text,
  toolsusageid bigint,
  taskdeliveryruleid bigint,
  createdate timestamp without time zone default LOCALTIMESTAMP,
  modifieddate timestamp without time zone default LOCALTIMESTAMP,
  originationCode character varying(20),
  CONSTRAINT testsection_pkey PRIMARY KEY (id),
  CONSTRAINT testsection_testid_fkey FOREIGN KEY (testid)
  	REFERENCES test (id) MATCH SIMPLE
	ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE testsectionstasks
(
	testsectionid bigint NOT NULL,
	taskid bigint NOT NULL,
	externaltestsectionid bigint,
	externaltaskid bigint,
	originationcode character varying(20),
	CONSTRAINT testsectionstasks_pk PRIMARY KEY (testsectionid, taskid),
	CONSTRAINT testsectionstasks_testsectionid_fkey FOREIGN KEY (testsectionid)
		REFERENCES testsection (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT testsectionstasks_taskid_fkey FOREIGN KEY (taskid)
		REFERENCES task (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE assessmentstestcollections
(
	assessmentid bigint NOT NULL,
	testcollectionid bigint NOT NULL,
	CONSTRAINT assessmentstests_pk PRIMARY KEY (assessmentid, testcollectionid),
	CONSTRAINT assessmentstests_assessmentid_fkey FOREIGN KEY (assessmentid)
		REFERENCES assessment (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT assessmentstests_testcollectionid_fkey FOREIGN KEY (testcollectionid)
		REFERENCES testcollection (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- IMPACT High.No Code Breakage.Needs refactor.
--alter existing aart tables
-- 
alter table studentstests add column testcollectionid bigint;

-- All tests searched through organization...
--Search Tests Select an assesmment program<--
--Subject categories and grade...TODO filter by grade or assessment program ?
-- How does CB look for organizations inside of AART, when an organization in AART and CB mean somethign different.

--alter the test table .
-- it has grade and subject...that needs to go away later.
--find out if gradeCourseId and ContentArea needs to be added here and also on test collection.
--Is content area organization specific. In AART need not be organization specific.
--TODO are these 2 columns needed.
--09/07/2012 
alter table test add column externalid bigint,
				-- add column	gradecourseid bigint, 
				-- add column	contentareaid bigint,
				 add column	createdate timestamp without time zone default LOCALTIMESTAMP,
				 add column	modifieddate timestamp without time zone default LOCALTIMESTAMP,
				 add column	originationcode character varying(20);
		
--need to decide if all of the name value pairs will continue to live in the category table				 
--alter table test drop column if exists gradeid, 
	--			 drop column if exists subjectid;
	
--Can origination code be a foreign key in category.OK leave it considering the changes.
--High impact.
--Added 09/07/2012 test type and test subject needs to go away but not now.
-- remove assessmentProgram id but not now.
-- Add testing program id now nullable.
-- impact on upload Test.
-- add nullable contraints so that AART won't fail.
alter table assessment add column externalid bigint,
	                   add column testingprogramid bigint,
	                    add column	createdate timestamp without time zone default LOCALTIMESTAMP,
				 add column	modifieddate timestamp without time zone default LOCALTIMESTAMP,
				 add column	originationcode character varying(20),
				 add Constraint assessment_testingprogramid_fk FOREIGN KEY (testingprogramid)
				      REFERENCES testingprogram (id) MATCH SIMPLE
					  ON UPDATE NO ACTION ON DELETE NO ACTION;

--No impact.				 
alter table assessmentprogram add column externalid bigint,
	                    add column	createdate timestamp without time zone default LOCALTIMESTAMP,
				 add column	modifieddate timestamp without time zone default LOCALTIMESTAMP,
				 add column	originationcode character varying(20);

ALTER TABLE student ADD COLUMN synced BOOLEAN DEFAULT FALSE;

--for CB integration.

ALTER TABLE assessment ALTER COLUMN assessmentname  Type character varying(75);


--INFO: cb related tables.
--Original 4.sql

alter table test add column	gradecourseid bigint, 
				 add column	contentareaid bigint,
				 add column directions text,
				 add column uitypecode varchar(20),
				 add column reviewtext text,
				 add constraint test_gradecourseid_fkey FOREIGN KEY (gradecourseid)
				     REFERENCES gradecourse (id) MATCH SIMPLE
					 ON UPDATE NO ACTION ON DELETE NO ACTION,
				 add constraint test_contentareaid_fkey FOREIGN KEY (contentareaid)
				     REFERENCES contentarea (id) MATCH SIMPLE
					   ON UPDATE NO ACTION ON DELETE NO ACTION;

--Integration related changes.
--delete the assessments so that it can be recreated with proper constraints.
--It is ok to do so because production at this time has no means of creating tests and assessments.
-- or add something with default, update it and drop the constraint..? This is better TODO.

--constraints get dropped along with the column
--ALTER TABLE assessment
	--DROP CONSTRAINT if exists assessment_testsubjectid_fkey;

--ALTER TABLE assessment
	--DROP CONSTRAINT if exists test_type_fk;

ALTER TABLE assessment
	DROP COLUMN testtypeid,
	DROP COLUMN testsubjectid,
	DROP COLUMN assessmentprogramid,
	ADD COLUMN assessmentcode character varying(75),
	ADD COLUMN assessmentdescription character varying(75);
	
ALTER TABLE restrictionsorganizations
	ALTER COLUMN restrictionid DROP DEFAULT;

ALTER TABLE assessment
	ADD CONSTRAINT uk_assessment_name_testing_program UNIQUE (assessmentname, testingprogramid);

ALTER TABLE assessmentprogram
	ADD CONSTRAINT uk_program_name UNIQUE (programname);

--This is needs to be executed after the data gets inserted or modified correctly.
	

update assessment set testingprogramid = (select id from testingprogram limit 1);

--if assessment name is incorrect, then the data needs to be deleted because it is junk data.
delete from studentsassessments where assessmentid in (select id from assessment where assessmentname is null);

delete from assessment where assessmentname is null;

--update the codes so that the not null constraints can be added.
update assessment set assessmentcode=assessmentname||'Code' where assessmentcode is null;

update assessment set assessmentdescription=' ' where assessmentdescription is null;

ALTER TABLE assessment
	ALTER COLUMN assessmentname SET NOT NULL,
	--ALTER COLUMN testingprogramid SET NOT NULL,
	ALTER COLUMN assessmentcode SET NOT NULL,
	ALTER COLUMN assessmentdescription SET NOT NULL;

ALTER TABLE assessment
	ADD CONSTRAINT uk_assessment_code_testing_program UNIQUE
	(assessmentcode, testingprogramid);

ALTER TABLE testingprogram
	ADD CONSTRAINT uk_testingprogram_assessmentprogram
	UNIQUE (programname, assessmentprogramid);
	
	

--INFO CB related tables.
--Original 5.sql
--adding data here so that not null constraints can be added.

INSERT INTO contentarea (externalid, sortorder, name, abbreviatedname, createdate, modifieddate, originationcode)
VALUES (NULL, 1, 'Content Area 1', 'C.A.1', '2012-09-14 10:34:58.761', '2012-09-14 10:34:58.761', NULL);
INSERT INTO contentarea (externalid, sortorder, name, abbreviatedname, createdate, modifieddate, originationcode)
VALUES (NULL, 2, 'Content Area', 'C.A.2', '2012-09-14 10:50:10.996', '2012-09-14 10:50:10.996', NULL);

INSERT INTO gradecourse (externalid, name, abbreviatedname, ordinality, gradelevel, shortdescription, longdescription, createdate, modifieddate, originationcode)
VALUES (NULL, 'Grade 1', 'GR1', 1, 1, 'This is Grade 1.', 'This is Grade level 1', '2012-09-14 10:10:29.14', '2012-09-14 10:10:29.14', NULL);
INSERT INTO gradecourse (externalid, name, abbreviatedname, ordinality, gradelevel, shortdescription, longdescription, createdate, modifieddate, originationcode)
VALUES (NULL, 'Grade 2', 'GR2', 1, 1, 'This is Grade 2', 'This is grade level 2', '2012-09-14 10:11:32.317', '2012-09-14 10:11:32.317', NULL);


--aart_cb_integration_alter.sql
alter table tasksfoils add column responseorder int,
                       add column correctresponse boolean,
		       add column responsescore int,
		       drop constraint if exists tasksfoils_taskid_fkey;


alter table testsectionstasks drop constraint if exists testsectionstasks_taskid_fkey;

alter table task rename to taskvariant;					  

alter table tasksfoils rename to taskvariantsfoils; 

alter table taskvariantsfoils rename column taskid to taskvariantid;

alter table taskvariantsfoils add constraint taskvariantsfoils_taskvariantid_fkey FOREIGN KEY (taskvariantid)
						REFERENCES taskvariant (id) MATCH SIMPLE
						ON UPDATE NO ACTION ON DELETE NO ACTION;
						
alter table testsectionstasks rename to testsectionstaskvariants;

alter table testsectionstaskvariants rename column taskid to taskvariantid;

alter table testsectionstaskvariants add constraint taskfoils_taskvariantid_fkey FOREIGN KEY (taskvariantid)
				REFERENCES taskvariant (id) MATCH SIMPLE
				ON UPDATE NO ACTION ON DELETE NO ACTION;


alter table foil add column rationale text,
                  add column instructionalimplications text,
		  alter column foiltext type text;
				  


alter table taskvariant add column version int,
                 drop column externaltaskvariantrevisionid,
				 add column testlet boolean,
				 add column contextstimulusid bigint,
				 add column numberofresponses int,
				 add column shuffled boolean,
				 add column innovativeitempackagepath character varying(250);
				 

alter table testsection drop column instructions,
                        drop column externaltestid,
			add column begininstructions text,
			add column endinstructions text,
			add constraint testsection_toolsusageid_fkey FOREIGN KEY (toolsusageid)
			  REFERENCES category (id) MATCH SIMPLE
		                   ON UPDATE NO ACTION ON DELETE NO ACTION;
						   

						   
alter table test drop constraint test_subjectid_fkey,
				 drop constraint test_gradeid_fkey,
				 drop constraint test_gradecourseid_fkey,
				 drop constraint test_contentareaid_fkey,
				 drop constraint test_assessmentid_fkey,
				 drop column subjectid,
	             drop column gradeid,
				 drop column gradecourseid,
				 drop column contentareaid,
				 drop column assessmentid,
				 add column begininstructions text,
				 add column endinstructions text,
				 add column status character varying(75);
						
				
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

--aart_cb_integration_alter2.sql

alter table categorytype add column externalid bigint,
						 add column originationcode character varying(20),
						 --drop constraint uk_category_type_code,
						 add constraint
						 categorytype_typecode_originationcode_uk
						 UNIQUE (typecode, originationcode);
						 
						 
alter table category add column externalid bigint,
						 add column originationcode character varying(20),
						 --drop constraint category_code_uk,
						 --drop constraint category_name_uk,
						 add constraint category_categorycode_originationcode_uk
						 UNIQUE (categorycode, originationcode, categorytypeid),
						 add constraint category_categoryname_originationcode_uk
						 UNIQUE (categoryname, originationcode, categorytypeid);

--from arun for integration.

ALTER TABLE testsection ADD COLUMN contextstimulusid bigint;
ALTER TABLE testsectionstaskvariants ADD COLUMN taskvariantposition integer;

--point to grade instead of category.

ALTER TABLE ENROLLMENT DROP CONSTRAINT current_grade_level_fk;

--this is necessary so that the constraint can be added.
update enrollment set currentgradelevel
= (select id from gradecourse limit 1)
where currentgradelevel not in (select id from gradecourse);

ALTER TABLE ENROLLMENT ADD CONSTRAINT current_grade_level_fk FOREIGN KEY (currentgradelevel)
      REFERENCES gradecourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

--when ever the student is registered to an assessment, he needs to be
-- registered for a specific subject.

ALTER TABLE studentsassessments ADD COLUMN contentareaid bigint;

update studentsassessments set contentareaid = (select id from contentarea limit 1); 

ALTER TABLE studentsassessments ALTER COLUMN contentareaid SET NOT NULL;

ALTER TABLE studentsassessments ADD CONSTRAINT content_area_fk
 FOREIGN KEY (contentareaid) REFERENCES contentarea (id)
 ON UPDATE NO ACTION ON DELETE NO ACTION;
 
 --assessment table changes
--ALTER TABLE assessment DROP COLUMN testtypeid; 
--ALTER TABLE assessment ADD COLUMN assessmentcode character varying(75);
--ALTER TABLE assessment ADD COLUMN assessmentdescription character varying(75); 

--constraint changes

ALTER TABLE studentsassessments DROP CONSTRAINT pk_student_assessment;
ALTER TABLE studentsassessments ADD CONSTRAINT pk_students_assessments
PRIMARY KEY (studentid, assessmentid, contentareaid);
