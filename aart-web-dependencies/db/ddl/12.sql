--CB related changes for R5 I3
--testlet
CREATE TABLE testlet (
  id bigint NOT NULL,
  testletname character varying(100) NOT NULL,
  testingprogramid bigint,
  contentareaid bigint,
  gradecourseid bigint,
  externalid bigint,
  originationcode character varying(20) NOT NULL,
  createuserid bigint, 
  createdate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone NOT NULL,
  modifieduserid bigint,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone NOT NULL,
  CONSTRAINT testlet_pkey PRIMARY KEY (id ),
  CONSTRAINT testlet_fk1 FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testlet_fk2 FOREIGN KEY (testingprogramid)
     REFERENCES testingprogram (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testlet_fk3 FOREIGN KEY (gradecourseid)
      REFERENCES gradecourse (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE testletstimulusvariants (
  testletid bigint NOT NULL,
  stimulusvariantid bigint NOT NULL,
CONSTRAINT testletstimulusvariants_pkey PRIMARY KEY (testletid , stimulusvariantid ),
  CONSTRAINT testletstimulusvariants_fk1 FOREIGN KEY (testletid)
      REFERENCES testlet (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT testletstimulusvariants_fk2 FOREIGN KEY (stimulusvariantid)
      REFERENCES stimulusvariant (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE testlet_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

ALTER TABLE testsectionstaskvariants ADD COLUMN testletid bigint;

ALTER TABLE testsectionstaskvariants
  ADD CONSTRAINT testsectionstasks_testletid_fkey FOREIGN KEY (testletid)
      REFERENCES testlet (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION;

--read aloud/accessibility 
CREATE SEQUENCE contentgroup_id_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;

CREATE SEQUENCE readaloudaccomodation_id_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;

CREATE SEQUENCE accessibilityfile_id_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;


CREATE TABLE contentgroup
(  
	id bigint NOT NULL DEFAULT nextval('contentgroup_id_seq'::regclass), 
	externalid bigint NOT NULL,     
	accesselementid character varying(75),  
	spanstart integer,  
	spanend integer,  
	charindexstart integer,  
	charindexend integer,  
	taskvariantid bigint,  
	foilid bigint, 
	createuserid bigint,   
	createdate timestamp without time zone,  
	modifieddate timestamp without time zone, 
	modifieduserid bigint,
	originationcode character varying(20) NOT NULL,
	CONSTRAINT contentgroup_pkey PRIMARY KEY (id),
  	CONSTRAINT contentgroup_fk1 FOREIGN KEY (taskvariantid)
		REFERENCES taskvariant (id) MATCH FULL      
		ON UPDATE NO ACTION ON DELETE NO ACTION,  
	CONSTRAINT contentgroup_fk2 FOREIGN KEY (foilid)
		REFERENCES foil (id) MATCH FULL
		ON UPDATE NO ACTION ON DELETE NO ACTION

);


CREATE TABLE accessibilityfile
(
	id bigint NOT NULL DEFAULT nextval('accessibilityfile_id_seq'::regclass),
	externalid bigint NOT NULL,
	filename character varying(200),
	filelocation character varying(250),
	filesize double precision,
	filetypeid bigint,
	taskvariantid bigint NOT NULL,
	assessmentprogramid bigint NOT NULL,
	duration double precision,
	createuserid bigint,
	createdate timestamp without time zone,
	modifieddate timestamp without time zone,
	modifieduserid bigint,
	originationcode character varying(20) NOT NULL,
	CONSTRAINT accessibilityfile_pkey PRIMARY KEY (id),
  	CONSTRAINT accessibilityfile_fk1 FOREIGN KEY (taskvariantid)
		REFERENCES taskvariant (id) MATCH FULL
		ON UPDATE NO ACTION ON DELETE NO ACTION,  
  	CONSTRAINT accessibilityfile_fk2 FOREIGN KEY (assessmentprogramid)  
		REFERENCES assessmentprogram (id) MATCH FULL
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE readaloudaccommodation
(
	id bigint NOT NULL DEFAULT nextval('readaloudaccomodation_id_seq'::regclass),
	externalid bigint NOT NULL,
	contentgroupid bigint NOT NULL,
	readaloudtypeid bigint NOT NULL,
	synthetic boolean, 
	syntheticpronoun character varying(2000),
	human boolean,  
	defaultorder integer,
	alternateorder integer,   
	accessibilityfileid bigint NOT NULL,
	starttime integer,
	endtime integer,
	createuserid bigint,
	createdate timestamp without time zone,  
	modifieddate timestamp without time zone,
	modifieduserid bigint,
	originationcode character varying(20) NOT NULL,
	CONSTRAINT readaloudacc_pkey PRIMARY KEY (id),
	CONSTRAINT readaloudacc_fk1 FOREIGN KEY (contentgroupid)
		REFERENCES contentgroup (id) MATCH FULL
		ON UPDATE NO ACTION ON DELETE NO ACTION,
  	CONSTRAINT readaloudacc_fk2 FOREIGN KEY (accessibilityfileid)
		REFERENCES accessibilityfile (id) MATCH FULL
		ON UPDATE NO ACTION ON DELETE NO ACTION
);



--Test Collection
ALTER TABLE testcollection ADD COLUMN systemselectoptionid bigint;

ALTER TABLE testcollection ADD COLUMN externalid bigint;

--learning maps
CREATE SEQUENCE taskvariantlearningmapnode_id_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;

CREATE TABLE taskvariantlearningmapnode
(
  id bigint NOT NULL DEFAULT nextval('taskvariantlearningmapnode_id_seq'::regclass),
  externalid bigint NOT NULL,
  taskvariantid bigint,
  foilid bigint,
  nodecode character varying(20) not null,
  nodetypecodeid bigint,
  nodeweightid bigint,
  originationcode character varying(20) not null,
  createduserid bigint,
  createddate timestamp without time zone,  
  modifieddate timestamp without time zone,
  modifieduserid bigint,
  CONSTRAINT taskvariantlearningmapnode_pkey PRIMARY KEY (id)
);

ALTER TABLE taskvariantlearningmapnode ADD CONSTRAINT fk_taskvariant_id
 FOREIGN KEY (taskvariantid) REFERENCES taskvariant (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE taskvariantlearningmapnode ADD CONSTRAINT fk_foil_id
 FOREIGN KEY (foilid) REFERENCES foil (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE taskvariantlearningmapnode ADD CONSTRAINT fk_nodetypecode_id
 FOREIGN KEY (nodetypecodeid) REFERENCES category (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE taskvariantlearningmapnode ADD CONSTRAINT fk_nodeweight_id
 FOREIGN KEY (nodeweightid) REFERENCES category (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
 
--publishing changes on AART sided and removing columns added earlier.

ALTER TABLE stimulusvariant DROP COLUMN nodeid cascade;
 
ALTER TABLE taskvariant DROP COLUMN nodeid cascade;

CREATE OR REPLACE VIEW node_report AS 
 SELECT s1.id AS student_id, s1.statestudentidentifier AS state_student_identifier, 
 s1.legalfirstname AS legal_first_name, s1.legalmiddlename AS legal_middle_name,
  s1.legallastname AS legal_last_name, summary.node_key,
   st1.id AS students_tests_id, t1.id AS test_id, t1.testname AS test_name,
    t1.status AS test_status_id, tc1.id AS test_collection_id, tc1.name AS test_collection_name,
     summary.no_of_responses, summary.correct_response
   FROM student s1, studentstests st1, test t1, testcollectionstests tct1, testcollection tc1,
    ( SELECT sr.studentid AS student_id, tvln.nodecode AS node_key, count(1) AS no_of_responses, tvf.correctresponse AS correct_response
           FROM studentsresponses sr, student s, taskvariantsfoils tvf, taskvariant tv,taskvariantlearningmapnode tvln,
            studentstests st, test t, testcollectionstests tct, testcollection tc
          WHERE tvf.foilid = sr.foilid AND tv.id = tvf.taskvariantid and tv.id = tvln.taskvariantid 
           AND s.id = sr.studentid AND st.testid = t.id AND tct.testid = t.id AND tct.testcollectionid = tc.id
          GROUP BY sr.studentid, tvln.nodecode, tvf.correctresponse) summary
  WHERE st1.studentid = s1.id AND st1.testid = tct1.testid
   AND t1.id = st1.testid AND tct1.testid = t1.id AND tct1.testcollectionid = tc1.id AND summary.student_id = s1.id
  ORDER BY s1.id, summary.node_key, summary.correct_response;