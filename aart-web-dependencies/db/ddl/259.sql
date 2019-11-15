
-- 259.sql

alter table testsession add column source bigint;

alter table testsession add CONSTRAINT source_testsession_fk FOREIGN KEY (source)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
      

