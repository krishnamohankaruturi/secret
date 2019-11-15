
--new source columns
alter table student add column source bigint;
alter table student add CONSTRAINT source_student_fk FOREIGN KEY (source)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

alter table enrollment add column source bigint;
alter table enrollment add CONSTRAINT source_student_fk FOREIGN KEY (source)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
alter table enrollmentsrosters add column source bigint;
alter table enrollmentsrosters add CONSTRAINT source_student_fk FOREIGN KEY (source)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
alter table roster add column source bigint;
alter table roster add CONSTRAINT source_student_fk FOREIGN KEY (source)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
--US14846 add esol fields to webservice consumer
alter table enrollment add column usaentrydate timestamp with time zone;
alter table enrollment add column firstlanguage character varying(2);
alter table enrollment add column esolparticipationcode character varying(1);
alter table enrollment add column esolprogramendingdate timestamp with time zone;
alter table enrollment add column esolprogramentrydate timestamp with time zone;      