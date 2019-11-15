--ddl/548.sql
--changes needed for US17984, US18045

--To capture subscoredefinition level totalitems, student respondeditems and rawscore for each testId
CREATE TABLE reporttestlevelsubscores
(
  id bigint NOT NULL,
  studentid bigint NOT NULL,
  studentreportid bigint NOT NULL,
  testid bigint NOT NULL,  
  subscoredefinitionname character varying(100) NOT NULL,
  totalitemsincluded integer,
  itemsresponded integer,  
  subscorerawscore numeric(6,3),
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT reporttestlevelsubscores_id_pk PRIMARY KEY (id),
  CONSTRAINT reporttestlevelsubscores_studentid_fk FOREIGN KEY (studentid)
      REFERENCES student (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT reporttestlevelsubscores_studentreportid_fk FOREIGN KEY (studentreportid)
      REFERENCES studentreport (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
      CONSTRAINT reporttestlevelsubscores_testid_fk FOREIGN KEY (testid)
      REFERENCES test (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP SEQUENCE IF EXISTS reporttestlevelsubscores_id_seq;
CREATE SEQUENCE reporttestlevelsubscores_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

DROP INDEX IF EXISTS idx_reporttestlevelsubscores_studentid;  
CREATE INDEX idx_reporttestlevelsubscores_studentid
  ON reporttestlevelsubscores
  USING btree
  (studentid);

DROP INDEX IF EXISTS idx_reporttestlevelsubscores_studentreportid; 
CREATE INDEX idx_reporttestlevelsubscores_studentreportid
  ON reporttestlevelsubscores
  USING btree
  (studentreportid);

DROP INDEX IF EXISTS idx_reporttestlevelsubscores_testid; 
CREATE INDEX idx_reporttestlevelsubscores_testid
  ON reporttestlevelsubscores
  USING btree
  (testid);

DROP INDEX IF EXISTS idx_reporttestlevelsubscores_subscoredefinitionname;  
CREATE INDEX idx_reporttestlevelsubscores_subscoredefinitionname
  ON reporttestlevelsubscores
  USING btree
  (subscoredefinitionname COLLATE pg_catalog."default");

DROP INDEX IF EXISTS idx_reporttestlevelsubscores_subscorerawscore; 
CREATE INDEX idx_reporttestlevelsubscores_subscorerawscore
  ON reporttestlevelsubscores
  USING btree
  (subscorerawscore);

DROP INDEX IF EXISTS idx_reporttestlevelsubscores_createddate; 
CREATE INDEX idx_reporttestlevelsubscores_createddate
  ON reporttestlevelsubscores
  USING btree
  (createddate);

ALTER TABLE reportsubscores ALTER COLUMN rating SET NOT NULL;

DROP INDEX IF EXISTS idx_reportsubscores_rating;
CREATE INDEX idx_reportsubscores_rating
  ON reportsubscores
  USING btree
  (rating);