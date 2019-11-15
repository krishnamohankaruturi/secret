--AART related tables from 10.sql 

CREATE TABLE IF NOT EXISTS testsession (
    id bigserial PRIMARY KEY NOT NULL,
    rosterid bigint REFERENCES roster (id),
    name character varying (75) NOT NULL,
    status bigint REFERENCES category(id)
);

-- will need to add the not null constraint after the data migration script is run on the database.
ALTER TABLE studentstests ADD COLUMN testsessionid bigint REFERENCES testsession (id);
ALTER TABLE studentstests DROP CONSTRAINT IF EXISTS uk_student_test_teacher;
ALTER TABLE studentstests ADD CONSTRAINT uk_student_testsession UNIQUE (studentid, testsessionid);
ALTER TABLE studentstests DROP COLUMN IF EXISTS teacherid;
ALTER TABLE testsession ADD CONSTRAINT uk_roster_name UNIQUE (rosterid, name);
ALTER TABLE testsession DROP COLUMN IF EXISTS testcollectionid;
ALTER TABLE testsession DROP COLUMN IF EXISTS studentstestsid;

CREATE TABLE IF NOT EXISTS studentsresponses
(
  id bigserial not null,
  studentid bigint not null,
  testid bigint not null,
  taskvariantid bigint not null,
  foilid bigint,
  response text,
  CONSTRAINT studentsresponses_pkey PRIMARY KEY (id),
  CONSTRAINT studentsresponses_studentid_fkey FOREIGN KEY (studentid)
      REFERENCES student (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT studentsresponses_testid_fkey FOREIGN KEY (testid)
      REFERENCES test (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT studentsresponses_foilid_fkey FOREIGN KEY (foilid)
      REFERENCES foil (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT studentsresponses_taskvariantid_fkey FOREIGN KEY (taskvariantid)
      REFERENCES taskvariant (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);