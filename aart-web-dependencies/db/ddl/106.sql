-- Table: studentsadaptivetestsections

DROP TABLE studentsadaptivetestsections;

CREATE TABLE studentsadaptivetestsections
( 
  studentstestid bigint,
  testpartid bigint,
  testsectioncontainerid bigint,
  testsectioncontainerthetanodeid bigint,
  testsectionid bigint, 
  taskvariantid bigint,
  activeflag boolean DEFAULT true, 
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  createusername character varying(256) DEFAULT 'TDE Student'::character varying,
  modifiedusername character varying(256) DEFAULT 'TDE Student'::character varying,  
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,  
  CONSTRAINT fk_studentsadaptivetestsections_studentstestid FOREIGN KEY (studentstestid)
      REFERENCES studentstests (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentsadaptivetestsections_partid FOREIGN KEY (testpartid)
      REFERENCES testpart (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentsadaptivetestsections_testsectioncontainerid FOREIGN KEY (testsectioncontainerid)
            REFERENCES testsectioncontainer (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentsadaptivetestsections_testsectionid FOREIGN KEY (testsectionid)
      REFERENCES testsection (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentsadaptivetestsections_testsectioncontainerthetanodeid FOREIGN KEY (testsectioncontainerthetanodeid)
      REFERENCES testsectioncontainerthetanode (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentsadaptivetestsections_taskvariantid FOREIGN KEY (taskvariantid)
      REFERENCES taskvariant (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_studentsadaptivetestsections PRIMARY KEY (studentstestid , testpartid, testsectionid)
);

-- Table: studentadaptivetestthetastatus

DROP TABLE studentadaptivetestthetastatus;

CREATE TABLE studentadaptivetestthetastatus
(
  studentstestid bigint,
  testpartid bigint,
  testpartnumber integer,
  testsectioncontainerid bigint,
  testsectioncontainernumber integer,
  initialtheta integer,
  interimtheta integer,
  testpartcomplete boolean DEFAULT false,
  activeflag boolean DEFAULT true,
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  createusername character varying(256) DEFAULT 'TDE Student'::character varying,
  modifiedusername character varying(256) DEFAULT 'TDE Student'::character varying,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone, 
  CONSTRAINT fk_studentadaptivetestthetastatus_studentstestid FOREIGN KEY (studentstestid)
      REFERENCES studentstests (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION, 
  CONSTRAINT fk_studentadaptivetestthetastatus_testsectioncontainerid FOREIGN KEY (testsectioncontainerid)
      REFERENCES testsectioncontainer (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentadaptivetestthetastatus_testpartid FOREIGN KEY (testpartid)
      REFERENCES testpart (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_studentadaptivetestthetastatus PRIMARY KEY (studentstestid , testpartnumber, testsectioncontainernumber)
);
 