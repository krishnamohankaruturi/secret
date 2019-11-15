--ddl/538.sql US17795 Calculate Rawscore

ALTER TABLE studentreport ADD COLUMN studenttest3id bigint,
			  ADD COLUMN studenttest4id bigint,
			  ADD COLUMN studentperformancetestid bigint,
			  ADD COLUMN externaltest3id bigint,
			  ADD COLUMN externaltest4id bigint,
			  ADD COLUMN performancetestexternalid bigint,
			  ADD COLUMN mdptscore numeric(6,3),
			  ADD COLUMN mdptscorableflag boolean;

DROP INDEX IF EXISTS idx_studentreport_studenttest3id; 			  
CREATE INDEX idx_studentreport_studenttest3id
  ON studentreport
  USING btree
  (studenttest3id);

DROP INDEX IF EXISTS idx_studentreport_studenttest4id;  
CREATE INDEX idx_studentreport_studenttest4id
  ON studentreport
  USING btree
  (studenttest4id);

DROP INDEX IF EXISTS idx_studentreport_studentperformancetestid;  
CREATE INDEX idx_studentreport_studentperformancetestid
  ON studentreport
  USING btree
  (studentperformancetestid);
  
  
ALTER TABLE reportprocessreason ADD COLUMN testid3 bigint,
			ADD COLUMN testid4 bigint,
			ADD COLUMN performancetestid bigint;
			
CREATE SEQUENCE studentreporttestscores_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
CREATE TABLE studentreporttestscores(id bigint,
			  studentreportid bigint,
			  test1idrawscore numeric(6,3),
			  test2idrawscore numeric(6,3),
			  test3idrawscore numeric(6,3),
			  test4idrawscore numeric(6,3),
			  prfrmtestrawscore numeric(6,3),
			  test1idtaskvariantcount integer,
			  test2idtaskvariantcount integer,
			  test3idtaskvariantcount integer,
			  test4idtaskvariantcount integer,
			  prfrmtesttaskvariantcount integer,
			  test1idresponsecount integer,
			  test2idresponsecount integer,
			  test3idresponsecount integer,
			  test4idresponsecount integer,
			  prfrmtestresponsecount integer,
			  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
			  CONSTRAINT studentreporttestscores_pkey PRIMARY KEY (id),
			  CONSTRAINT studentreporttestscores_studentreportid_fk FOREIGN KEY (studentreportid)
      REFERENCES studentreport (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
    );

 DROP INDEX IF EXISTS idx_studentreporttestscores_studentreportid;  
 CREATE INDEX idx_studentreporttestscores_studentreportid
  ON studentreporttestscores
  USING btree
  (studentreportid);   
    