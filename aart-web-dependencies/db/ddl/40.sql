--changes for CB related to adaptive testing and ETL
ALTER TABLE taskvariant ADD COLUMN scoringmethod character varying(75);
ALTER TABLE taskvariant ADD COLUMN scoringdata text;
ALTER TABLE taskvariant ADD COLUMN innovativeitemdata text;
ALTER TABLE taskvariant ADD COLUMN tasksubtypeid bigint;
ALTER TABLE taskvariant ADD COLUMN expectedlines character varying(20);
ALTER TABLE taskvariant ADD COLUMN rubricneeded boolean;
ALTER TABLE taskvariant ADD COLUMN rubricviewedby character varying(20);
ALTER TABLE taskvariant ADD COLUMN rubricdirections text;
ALTER TABLE taskvariant ADD COLUMN scoringneeded boolean;
ALTER TABLE taskvariant ADD COLUMN maxscore integer;

ALTER TABLE taskvariantsfoils ADD COLUMN feedback character varying(500);
ALTER TABLE taskvariantsfoils ADD COLUMN responsename CHARACTER VARYING(20);
ALTER TABLE taskvariantsfoils ADD COLUMN maxcharacters SMALLINT;
ALTER TABLE taskvariantsfoils ADD COLUMN responsetype SMALLINT;
ALTER TABLE taskvariantsfoils ADD COLUMN casesensitive BOOLEAN;

CREATE TABLE tasksubtype
(
  id bigint NOT NULL, 
  tasktypeid bigint NOT NULL,
  name character varying(100) NOT NULL,
  code character varying(75),
  inuse boolean,
  createduser integer,
  createdate timestamp with time zone,
  modifieddate timestamp with time zone,
  modifieduser integer,
  originationcode character varying(20),
  CONSTRAINT tasksubtype_pkey PRIMARY KEY (id ),
  CONSTRAINT tasksubtype_tasktypeid_fkey FOREIGN KEY (tasktypeid)
      REFERENCES tasktype (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE tasksubtypeid_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1;
  
ALTER TABLE taskvariant
  ADD CONSTRAINT taskvariant_tasksubtypeid_fkey FOREIGN KEY (tasksubtypeid)
      REFERENCES tasksubtype (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE TABLE testformat
(
	id bigint NOT NULL,
	code character varying(30),
	name character varying(256),
  	createdate timestamp with time zone,
  	modifieduserid bigint,
  	modifieddate timestamp with time zone,
	CONSTRAINT testformat_pkey PRIMARY KEY (id)
);


-- All Adaptive changes below
ALTER TABLE test ADD COLUMN testformatcode character  varying(30);
--ALTER TABLE test ADD COLUMN testformatname character varying(256);
ALTER TABLE test ADD COLUMN adaptivetypecode character varying(25);
ALTER TABLE test ADD COLUMN subsectionselectionmodeltypecode character varying(25);
ALTER TABLE test ADD COLUMN unidimentionalmodeltypecode character varying(25);
ALTER TABLE test ADD COLUMN testtimeformattypecode character varying(25);
ALTER TABLE test ADD COLUMN numberofparts integer;
ALTER TABLE test ADD COLUMN ndcst integer;
ALTER TABLE test ADD COLUMN ndit integer;
ALTER TABLE test ADD COLUMN interimthetaestmodeltypecode character varying(25);
ALTER TABLE test ADD COLUMN unidimnssbpsnasbpsmodeltypecode character varying(25);

CREATE SEQUENCE testsectioncontainerid_seq;

CREATE TABLE testsectioncontainer
(
	id bigint NOT NULL,
	testid bigint,
	sectionname character varying(25) NOT NULL,
	sectionnumber integer,
	numberofsubsections integer,
	CONSTRAINT testsectioncontainer_pkey PRIMARY KEY (id),
	CONSTRAINT testsectioncontainer_testid_fkey FOREIGN KEY (testid)
		REFERENCES test (id) MATCH FULL
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE testsection ADD COLUMN testsectioncontainerid bigint;
ALTER TABLE testsection ADD COLUMN subsectionnumber integer;

ALTER TABLE testsection
  ADD CONSTRAINT testsection_testsectioncontainerid_fkey FOREIGN KEY (testsectioncontainerid)
      REFERENCES testsectioncontainer (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE SEQUENCE testpartid_seq;

CREATE TABLE testpart
(
	id bigint NOT NULL,
	testid bigint,
	testsectioncontainerid bigint,
	partname character varying(25) NOT NULL,
	partnumber integer,
	selectednumberofsubsections integer,
	administratednumberofsubsections integer,
	CONSTRAINT testpart_pkey PRIMARY KEY (id),
	CONSTRAINT testpart_testid_fkey FOREIGN KEY (testid)
		REFERENCES test (id) MATCH FULL
		ON UPDATE NO ACTION ON DELETE NO ACTION,	
	CONSTRAINT testpart_testsectioncontainerid_fkey FOREIGN KEY (testsectioncontainerid)
		REFERENCES testsectioncontainer (id) MATCH FULL
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE testconstructid_seq;

CREATE TABLE testconstruct
(
	id bigint NOT NULL,
	testid bigint,
	constructnumber integer NOT NULL,
	thetanodevalue integer NOT NULL,
	CONSTRAINT testconstruct_pkey PRIMARY KEY (id),
	CONSTRAINT testconstruct_testid_fkey FOREIGN KEY (testid)
		REFERENCES test (id) MATCH FULL
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE testsectioncontainerthetanodeid_seq;

CREATE TABLE testsectioncontainerthetanode
(
	id bigint NOT NULL,
	testsectioncontainerid bigint NOT NULL,
	index integer NOT NULL,
	value1 integer NULL,
	value2 integer NULL,
	value3 integer NULL,
	value4 integer NULL,
	value5 integer NULL,
	value6 integer NULL,
	CONSTRAINT testsectioncontainerthetanode_pkey PRIMARY KEY (id),
	CONSTRAINT testsectioncontainerthetanode_testsectioncontainerid_fkey FOREIGN KEY (testsectioncontainerid)
		REFERENCES testsectioncontainer (id) MATCH FULL
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE testselectionstatisticid_seq;

CREATE TABLE testselectionstatistic
(
	id bigint NOT NULL,
	testsectionid bigint NOT NULL,
	testsectioncontainerthetanodeid bigint NOT NULL,
	selectionstatisticvalue double precision NOT NULL,
	CONSTRAINT testselectionstatistic_pkey PRIMARY KEY (id),
	CONSTRAINT testselectionstatistic_testsectionid_fkey FOREIGN KEY (testsectionid)
		REFERENCES testsection (id) MATCH FULL
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT testselectionstatistic_testsectioncontainerthetanodeid_fkey FOREIGN KEY (testsectioncontainerthetanodeid)
		REFERENCES testsectioncontainerthetanode (id) MATCH FULL
		ON UPDATE NO ACTION ON DELETE NO ACTION		
); 

CREATE TABLE testsectioncontainerconstruct
(
  	testsectioncontainerid bigint NOT NULL,
	testconstructid bigint NOT NULL,
  	itemdiscriminationparametername character varying(25) NOT NULL,
  	itemdiscriminationparameterindex integer,
  	CONSTRAINT testsectioncontainerconstruct_pkey PRIMARY KEY (testsectioncontainerid, testconstructid),
  	CONSTRAINT testsectioncontainerconstruct_testsectioncontainerid_fkey FOREIGN KEY (testsectioncontainerid)
      		REFERENCES testsectioncontainer (id) MATCH FULL
     		ON UPDATE NO ACTION ON DELETE NO ACTION,
  	CONSTRAINT testsectioncontainerconstruct_testconstructid_fkey FOREIGN KEY (testconstructid)
      		REFERENCES testconstruct (id) MATCH FULL
      		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE itemstatistic
(
  taskvariantid bigint NOT NULL,
  itemstatisticname character varying(25) NOT NULL,
  itemstatisticvalue numeric(10,9),
  CONSTRAINT itemstatistic_pkey PRIMARY KEY (taskvariantid, itemstatisticname),
  CONSTRAINT itemstatistic_taskvariantid_fkey FOREIGN KEY (taskvariantid)
      REFERENCES taskvariant (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);