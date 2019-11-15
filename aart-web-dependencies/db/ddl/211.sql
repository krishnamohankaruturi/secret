--ddl/211.sql
ALTER TABLE enrollmentsrosters ADD COLUMN courseenrollmentstatusid bigint;

ALTER TABLE enrollmentsrosters
  ADD CONSTRAINT enrollmentsrosters_course_status_fk FOREIGN KEY (courseenrollmentstatusid)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

      
