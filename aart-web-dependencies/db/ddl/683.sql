--679.sql

CREATE TABLE scoringtestmetadata
(
   id bigserial, 
   testid bigint, 
   taskvariantid bigint, 
   testletid bigint, 
   positionintest integer NOT NULL, 
   rubrictype text, 
   rubricminscore integer, 
   rubricmaxscore integer, 
   clusterscoring boolean,
   createddate timestamp with time zone ,
   CONSTRAINT scoringtestmetadata_pk_key PRIMARY KEY (id)
);

CREATE INDEX idx_scoringtestmetadata_testid
  ON scoringtestmetadata
  USING btree
  (testid);

CREATE INDEX idx_scoringtestmetadata_taskvariantid
  ON scoringtestmetadata
  USING btree
  (taskvariantid);

 CREATE INDEX idx_scoringtestmetadata_testletid
  ON scoringtestmetadata
  USING btree
  (testletid);


ALTER TABLE scoringassignmentstudent ADD column testid bigint;

ALTER TABLE scoringassignmentstudent ADD column enrollmentid bigint;

CREATE INDEX idx_scoringassignmentstudent_enrollmentid
  ON scoringassignmentstudent
  USING btree
  (enrollmentid);

CREATE INDEX idx_scoringassignmentstudent_testid
  ON scoringassignmentstudent
  USING btree
  (testid);

--DROP TABLE studentstestscore

CREATE TABLE studentstestscore
(
   id bigserial,
   studenttestid bigint,
   taskvariantid bigint,
   scorerid bigint,
   score integer,
   nonscorereason bigint,
   rubriccategoryid bigint,
   activeflag boolean,
   source character varying(20),
   createddate timestamp with time zone,
   createduser bigint,
   modifieddate timestamp with time zone,
   modifieduser bigint,
   CONSTRAINT studentstestscore_pk_key PRIMARY KEY (id)

);

CREATE INDEX idx_studentstestscore_studenttestid
  ON studentstestscore
  USING btree
  (studenttestid);


CREATE INDEX idx_studentstestscore_rubriccategoryid
  ON studentstestscore
  USING btree
  (rubriccategoryid);

  
CREATE INDEX idx_studentstestscore_taskvariantid
  ON studentstestscore
  USING btree
  (taskvariantid);

  
CREATE INDEX idx_studentstestscore_scorerid
  ON studentstestscore
  USING btree
  (scorerid); 

CREATE INDEX idx_studentstestscore_nonscorereason
  ON studentstestscore
  USING btree
  (nonscorereason);

CREATE INDEX idx_studentstestscore_activeflag
  ON studentstestscore
  USING btree
  (activeflag);  

ALTER TABLE studentstestscore
  ADD CONSTRAINT uk_studentstestscore UNIQUE (studenttestid, taskvariantid, rubriccategoryid, activeflag);


CREATE TABLE studentstestscorehistory
(
   id bigserial,
   studenttestid bigint,
   taskvariantid bigint,
   rubriccategoryid bigint,
   scorerid bigint,
   score integer,
   createddate timestamp with time zone,
   CONSTRAINT studentstestscorehistory_pk_key PRIMARY KEY (id)
);

CREATE INDEX idx_studentstestscorehistory_studenttestid
  ON studentstestscorehistory
  USING btree
  (studenttestid);

CREATE INDEX idx_studentstestscorehistory_taskvariantid
  ON studentstestscorehistory
  USING btree
  (taskvariantid);

CREATE INDEX idx_studentstestscorehistory_rubriccategoryid
  ON studentstestscorehistory
  USING btree
  (rubriccategoryid);

 ALTER TABLE studentstestscorehistory ADD Column nonscorereason bigint;
 
-- F579 DDL
--Adding coluns to the authorities
alter table authorities 
	add column tabName character varying(20) not null default '--NA--', 
	add column groupingName character varying(50), 
	add column labelName  character varying(50),
	add column level bigint not null default -99,
	add column sortorder bigint not null default -99 ;
	


-- F605 ddl
ALTER TABLE projectedtestingdate ADD COLUMN projectiontype varchar(1) default 'T' NOT NULL;

ALTER TABLE projectedtestingdate ADD COLUMN grade bigint;

-- Should be part of 684.sql?
--ALTER TABLE projectedtestingdate alter column grade set not null;

ALTER TABLE projectedtestingdate add CONSTRAINT grade_fk FOREIGN KEY (grade)
      REFERENCES public.gradecourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
