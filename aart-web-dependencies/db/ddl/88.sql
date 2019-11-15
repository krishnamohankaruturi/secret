

--US13094 and US13096
ALTER TABLE student ADD COLUMN commbandid bigint;
ALTER TABLE student ADD COLUMN elabandid bigint;
ALTER TABLE student ADD CONSTRAINT elaband_student_fk FOREIGN KEY (elabandid)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE student ADD CONSTRAINT commband_student_fk FOREIGN KEY (commbandid)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

