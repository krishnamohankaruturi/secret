CREATE TABLE studentsadaptivetestsections
( 
  studentstestid bigint,
  testpartid bigint,
  testsectioncontainerid bigint,
  testsectionid bigint, 
  activeflag boolean DEFAULT true, 
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  createusername character varying(256) DEFAULT 'TDE Student'::character varying,
  modifiedusername character varying(256) DEFAULT 'TDE Student'::character varying,  
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,  
  CONSTRAINT fk_studentadaptivetest_studentstestid FOREIGN KEY (studentstestid)
      REFERENCES studentstests (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentadaptivetest_partid FOREIGN KEY (testpartid)
      REFERENCES testpart (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentadaptivetest_testsectioncontainerid FOREIGN KEY (testsectioncontainerid)
            REFERENCES testsectioncontainer (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentadaptivetest_testsectionid FOREIGN KEY (testsectionid)
      REFERENCES testsection (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_studentadaptivetest PRIMARY KEY (studentstestid , testpartid, testsectionid)
);


CREATE TABLE studentadaptivetestthetastatus
(
  studentstestid bigint,
  testpartnumber integer,
  testsectioncontainernumber integer,
  initialtheta integer,
  interimtheta integer,
  testpartcomplete boolean DEFAULT false,
  activeflag boolean DEFAULT true,
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  createusername character varying(256) DEFAULT 'TDE Student'::character varying,
  modifiedusername character varying(256) DEFAULT 'TDE Student'::character varying,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone, 
  CONSTRAINT fk_studentadaptivetest_studentstestid FOREIGN KEY (studentstestid)
      REFERENCES studentstests (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION, 
  CONSTRAINT uk_studentadaptiveteststatus PRIMARY KEY (studentstestid , testpartnumber, testsectioncontainernumber)
);