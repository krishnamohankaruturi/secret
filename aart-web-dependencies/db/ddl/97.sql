
--US13299
alter table student add column finalelabandid bigint;
alter table student add constraint finalelaband_student_fk FOREIGN KEY (finalelabandid)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;