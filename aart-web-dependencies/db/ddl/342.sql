-- 342.sql
-- US15652 : Monitor test session on Test Coordination - capture reactivation history

CREATE SEQUENCE studentstestsreactivationhistory_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
CREATE TABLE studentstestsreactivationhistory
(
  id bigint NOT NULL DEFAULT nextval('studentstestsreactivationhistory_id_seq'::regclass),	
  testsessionid bigint,
  studentid bigint,
  reactivationdate timestamp with time zone NOT NULL,
  reactivatinguser integer NOT NULL,
  createduser integer NOT NULL,
  createdate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser integer NOT NULL,
  modifieddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp without time zone,
  	
  CONSTRAINT studentstestsreactivationhistory_pkey PRIMARY KEY (id),
  CONSTRAINT fk_studentstestsreactivationhistory_testsessionid FOREIGN KEY (testsessionid)
      REFERENCES testsession (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentstestsreactivationhistory_studentid FOREIGN KEY (studentid)
      REFERENCES student (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,    
  CONSTRAINT fk_studentstestsreactivationhistory_reactivatinguser FOREIGN KEY (reactivatinguser)
      REFERENCES aartuser (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentstestsreactivationhistory_createduser FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,     
  CONSTRAINT fk_studentstestsreactivationhistory_modifieduser FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION    
);

