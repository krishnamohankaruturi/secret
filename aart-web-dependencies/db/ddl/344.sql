drop table studentstestsreactivationhistory;
drop sequence studentstestsreactivationhistory_id_seq;

CREATE SEQUENCE studentstestshistory_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
CREATE TABLE studentstestshistory
(
  id bigint NOT NULL DEFAULT nextval('studentstestshistory_id_seq'::regclass),	
  studentstestsid bigint NOT NULL,
  studentstestsstatusid bigint NOT NULL,
  action character varying(20),
  acteduser integer NOT NULL,
  acteddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp without time zone,
  	
  CONSTRAINT studentstestshistory_pkey PRIMARY KEY (id),
  CONSTRAINT fk_studentstestshistory_studentstestsid FOREIGN KEY (studentstestsid)
      REFERENCES studentstests (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentstestshistory_acteduser FOREIGN KEY (acteduser)
      REFERENCES aartuser (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION         
);

CREATE INDEX idx_studentstestshistory_studentstestsid ON studentstestshistory USING btree (studentstestsid);