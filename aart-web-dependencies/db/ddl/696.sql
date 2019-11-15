--ddl/696.sql

--F97 DML
---leveldescription ALTER script
ALTER TABLE leveldescription ADD column assessmentcode character varying(100) DEFAULT null;

CREATE INDEX idx_leveldescription_assessment
  ON leveldescription
  USING btree
  (assessmentcode);
--- testcutscores ALTER script	
ALTER TABLE testcutscores  ADD column assessmentcode character varying(100) DEFAULT null;

CREATE INDEX idx_testcutscores_assessment
  ON testcutscores
  USING btree
  (assessmentcode);
--report process ALTER script
ALTER TABLE reportprocess ADD column assessmentcode character varying(100) DEFAULT null;

--- externalstudentreports ALTER script
ALTER  TABLE externalstudentreports ADD column assessmentcode character varying(100) DEFAULT null;

ALTER  TABLE externalstudentreports ADD column scalescore bigint;

ALTER  TABLE externalstudentreports ADD column standarderror decimal(6,3);

ALTER  TABLE externalstudentreports ADD column achievementlevel bigint;

ALTER  TABLE externalstudentreports ADD column completedflag boolean;

ALTER  TABLE externalstudentreports ADD column  reportcycle text;

Alter TABLE externalstudentreports alter column filepath drop not null;

ALTER  TABLE externalstudentreports ADD column testingprogramid bigint;

ALTER  TABLE externalstudentreports ADD column  generated boolean NOT NULL DEFAULT false;

CREATE INDEX idx_externalstudentreports_testingprogram
  ON externalstudentreports
  USING btree
  (testingprogramid);

CREATE INDEX idx_externalstudentreports_assessment
  ON externalstudentreports
  USING btree
  (assessmentcode);

  CREATE INDEX idx_externalstudentreports_reportcycle
  ON externalstudentreports
  USING btree
  (reportcycle);
  
  CREATE INDEX idx_externalstudentreports_testingprogramid
  ON externalstudentreports
  USING btree
  (testingprogramid);

--orgassessmentprogram ALTER script
ALTER TABLE orgassessmentprogram add column reportyear bigint default null;

UPDATE orgassessmentprogram set reportyear = 2018 where assessmentprogramid = 11;

CREATE INDEX idx_orgassessmentprogram_reportyear
  ON orgassessmentprogram
  USING btree
  (reportyear);

--- organizationreportdetails ALTER script
ALTER  TABLE organizationreportdetails ADD column testingprogramid bigint;

ALTER  TABLE organizationreportdetails ADD column assessmentcode character varying(100) DEFAULT null;

ALTER  TABLE organizationreportdetails ADD column scalescore bigint;

ALTER  TABLE organizationreportdetails ADD column standarderror decimal(6,3);

ALTER  TABLE organizationreportdetails ADD column  generated boolean NOT NULL DEFAULT false;

ALTER  TABLE organizationreportdetails ADD column reportcycle text;

CREATE INDEX idx_organizationreportdetails_testingprogram
  ON organizationreportdetails
  USING btree
  (testingprogramid);
  
CREATE INDEX idx_organizationreportdetails_assessment
  ON organizationreportdetails
  USING btree
  (assessmentcode);

CREATE INDEX idx_organizationreportdetails_reportcycle
 ON organizationreportdetails
  USING btree
  (reportcycle);

---assessmenttopic  CREATE script

CREATE TABLE assessmenttopic(
id bigserial NOT NULL,
topicname character varying(100),
schoolyear bigint NOT NULL,
assessmentcode character varying(100) DEFAULT null,
gradeid bigint,
contentareaid bigint,
topiccode character varying(75),
totalnoofitems numeric,
activeflag boolean NOT NULL DEFAULT true,
createduser integer NOT NULL DEFAULT 12,
createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
modifieduser integer NOT NULL DEFAULT 12,
modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,

CONSTRAINT assessmenttopic_pkey PRIMARY KEY (id),

CONSTRAINT fk_assessmenttopic_contentarea FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH SIMPLE,

CONSTRAINT fk_assessmenttopic_grade FOREIGN KEY (gradeid)
      REFERENCES gradecourse (id) MATCH SIMPLE,

CONSTRAINT fk_assessmenttopic_createduser FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE,
      
CONSTRAINT fk_assessmenttopic_modifieduser FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) 

);

CREATE INDEX idx_assessmenttopic_id
  ON assessmenttopic
  USING btree
  (id);

 CREATE INDEX idx_assessmenttopic_schoolyear
  ON assessmenttopic
  USING btree
  (schoolyear);
  
CREATE INDEX idx_assessmenttopic_assessment
  ON assessmenttopic
  USING btree
  (assessmentcode);
  
CREATE INDEX idx_assessmenttopic_grade
  ON assessmenttopic
  USING btree
  (gradeid);
  
CREATE INDEX idx_assessmenttopic_contentarea
  ON assessmenttopic
  USING btree
  (contentareaid);
  
CREATE INDEX idx_assessmenttopic_topic
  ON assessmenttopic
  USING btree
  (topiccode);

---studentpercentcorrectforassessmenttopic CREATE script
CREATE TABLE studentpctbyassessmenttopic(
id bigserial NOT NULL,
schoolyear bigint NOT NULL,
assessmentcode character varying(100) DEFAULT null,
studentid bigint NOT NULL,
gradeid bigint,
contentareaid bigint,
schoolid bigint,
assessmenttopicid bigint,
percentcorrect decimal(6,3),
testingcycleid bigint,
activeflag boolean NOT NULL DEFAULT true,
createduser integer NOT NULL DEFAULT 12,
createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
modifieduser integer NOT NULL DEFAULT 12,
modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,

CONSTRAINT studentpctbyassessmenttopic_pkey PRIMARY KEY (id),

CONSTRAINT fk_studentpctbyassessmenttopic_student FOREIGN KEY (studentid)
      REFERENCES student(id) MATCH SIMPLE,

CONSTRAINT fk_studentpctbyassessmenttopic_contentarea FOREIGN KEY (contentareaid)
      REFERENCES contentarea(id) MATCH SIMPLE,

CONSTRAINT fk_studentpctbyassessmenttopic_grade FOREIGN KEY (gradeid)
      REFERENCES gradecourse(id) MATCH SIMPLE,
      
CONSTRAINT fk_studentpctbyassessmenttopic_organization FOREIGN KEY (schoolid)
      REFERENCES organization(id) MATCH SIMPLE,

CONSTRAINT fk_studentpctbyassessmenttopic_assessmenttopic FOREIGN KEY (assessmenttopicid)
      REFERENCES assessmenttopic(id) MATCH SIMPLE,

CONSTRAINT fk_studentpctbyassessmenttopic_testingcycle FOREIGN KEY (testingcycleid)
      REFERENCES testingcycle(id) MATCH SIMPLE,

CONSTRAINT fk_studentpctbyassessmenttopic_createduser FOREIGN KEY (createduser)
      REFERENCES aartuser(id) MATCH SIMPLE,
      
CONSTRAINT fk_studentpctbyassessmenttopic_modifieduser FOREIGN KEY (modifieduser)
      REFERENCES aartuser(id) 

);
CREATE INDEX idx_studentpctbyassessmenttopic_id
  ON studentpctbyassessmenttopic
  USING btree
  (id);
  
CREATE INDEX idx_studentpctbyassessmenttopic_schoolyear
  ON studentpctbyassessmenttopic
  USING btree
  (schoolyear);
  
CREATE INDEX idx_studentpctbyassessmenttopic_assessment
  ON studentpctbyassessmenttopic
  USING btree
  (assessmentcode);
  
CREATE INDEX idx_studentpctbyassessmenttopic_grade
  ON studentpctbyassessmenttopic
  USING btree
  (gradeid);
  
CREATE INDEX idx_studentpctbyassessmenttopic_contentarea
  ON studentpctbyassessmenttopic
  USING btree
  (contentareaid);

CREATE INDEX idx_studentpctbyassessmenttopic_organization
  ON studentpctbyassessmenttopic
  USING btree
  (schoolid);
  
CREATE INDEX idx_studentpctbyassessmenttopic_assessmenttopic
  ON studentpctbyassessmenttopic
  USING btree
  (assessmenttopicid);

CREATE INDEX idx_studentpctbyassessmenttopic_testingcycle
  ON studentpctbyassessmenttopic
  USING btree
  (testingcycleid);


---organizationpercentcorrectforassessmenttopic CREATE script
CREATE TABLE organizationpctbyassessmenttopic(
id bigserial NOT NULL,
schoolyear bigint NOT NULL,
assessmentcode character varying(100) DEFAULT null,
gradeid bigint,
contentareaid bigint,
organizationid bigint,
assessmenttopicid bigint,
percentcorrect decimal(6,3),
testingcycleid bigint,
activeflag boolean NOT NULL DEFAULT true,
createduser integer NOT NULL DEFAULT 12,
createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
modifieduser integer NOT NULL DEFAULT 12,
modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,

CONSTRAINT organizationpctbyassessmenttopic_pkey PRIMARY KEY (id),

CONSTRAINT fk_organizationpctbyassessmenttopic_contentarea FOREIGN KEY (contentareaid)
      REFERENCES contentarea(id) MATCH SIMPLE,

CONSTRAINT fk_organizationpctbyassessmenttopic_grade FOREIGN KEY (gradeid)
      REFERENCES gradecourse(id) MATCH SIMPLE,
      
CONSTRAINT fk_organizationpctbyassessmenttopic_organization FOREIGN KEY (organizationid)
      REFERENCES organization(id) MATCH SIMPLE,

CONSTRAINT fk_organizationpctbyassessmenttopic_assessmenttopic FOREIGN KEY (assessmenttopicid)
      REFERENCES assessmenttopic(id) MATCH SIMPLE,

CONSTRAINT fk_organizationpctbyassessmenttopic_testingcycle FOREIGN KEY (testingcycleid)
      REFERENCES testingcycle(id) MATCH SIMPLE,

CONSTRAINT fk_organizationpctbyassessmenttopic_createduser FOREIGN KEY (createduser)
      REFERENCES aartuser(id) MATCH SIMPLE,
      
CONSTRAINT fk_organizationpctbyassessmenttopic_modifieduser FOREIGN KEY (modifieduser)
      REFERENCES aartuser(id) 
);

CREATE INDEX idx_organizationpctbyassessmenttopic_id
  ON organizationpctbyassessmenttopic
  USING btree
  (id);
  
CREATE INDEX idx_organizationpctbyassessmenttopic_schoolyear
  ON organizationpctbyassessmenttopic
  USING btree
  (schoolyear);
  
CREATE INDEX idx_organizationpctbyassessmenttopic_assessment
  ON organizationpctbyassessmenttopic
  USING btree
  (assessmentcode);
  
CREATE INDEX idx_organizationpctbyassessmenttopic_grade
  ON organizationpctbyassessmenttopic
  USING btree
  (gradeid);
  
CREATE INDEX idx_organizationpctbyassessmenttopic_contentarea
  ON organizationpctbyassessmenttopic
  USING btree
  (contentareaid);

CREATE INDEX idx_organizationpctbyassessmenttopic_organization
  ON organizationpctbyassessmenttopic
  USING btree
  (organizationid);
  
CREATE INDEX idx_organizationpctbyassessmenttopic_assessmenttopic
  ON organizationpctbyassessmenttopic
  USING btree
  (assessmenttopicid);

CREATE INDEX idx_organizationpctbyassessmenttopic_testingcycle
  ON organizationpctbyassessmenttopic
  USING btree
  (testingcycleid);

--  Colabarative Organization
INSERT INTO organization(
            organizationname, displayidentifier, organizationtypeid, 
            welcomemessage, createddate, activeflag, createduser, modifieduser, 
            modifieddate, buildinguniqueness, schoolstartdate, schoolenddate, 
            contractingorganization, expirepasswords, expirationdatetype, 
            pooltype, multitestassignment, reportprocess, reportyear, testingmodel, 
            ismerged)
    VALUES ('Collabarative', 'ALL', (select id from organizationtype where typecode = 'ST'), 
            null, now(), true, 13, 13, 
            now(), null, null, null, 
            false, null, null, 
            null, null, null, null, null, 
            false);

SELECT setval('testingcycle_id_seq', (SELECT MAX(id) FROM testingcycle), true);