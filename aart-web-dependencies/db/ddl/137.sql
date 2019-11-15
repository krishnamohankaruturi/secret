CREATE TABLE studentadaptivetestfinaltheta
(
  studentstestid bigint,
  testconstructid bigint,
  testconstructnumber integer,
  thetavalue bigint,
  iterationcount integer,
  activeflag boolean DEFAULT true,
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  createusername character varying(256) DEFAULT 'TDE Student'::character varying,
  modifiedusername character varying(256) DEFAULT 'TDE Student'::character varying,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone, 
  CONSTRAINT fk_studentadaptivetestfinaltheta_studentstestid FOREIGN KEY (studentstestid)
      REFERENCES studentstests (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION, 
  CONSTRAINT fk_studentadaptivetestfinaltheta_testsectioncontainerid FOREIGN KEY (testconstructid)
      REFERENCES testconstruct (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_studentadaptivetestfinaltheta PRIMARY KEY (studentstestid , testconstructid)
);
