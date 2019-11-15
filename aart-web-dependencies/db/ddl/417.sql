--417.sql
--US15912: Reports - Subscore calculate raw score 

drop table if exists reportsubscores;
drop sequence if exists reportsubscores_id_seq;

CREATE TABLE reportsubscores
(
  id bigint NOT NULL,
  studentid bigint NOT NULL,
  testid bigint NOT NULL,
  subscoredefinitionname character varying(100) not null, 
  subscorerawscore numeric(6,3) not null,
  subscorescalescore bigint,
  subscorestandarderror numeric,
  studentreportid bigint not null,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT reportsubscores_id_pk PRIMARY KEY (id),
  CONSTRAINT reportsubscores_studentid_fk FOREIGN KEY (studentid)
      REFERENCES student (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT reportsubscores_studentreportid_fk FOREIGN KEY (studentreportid)
      REFERENCES studentreport (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);		

create sequence reportsubscores_id_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;