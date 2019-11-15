--418.sql
--US16232 

CREATE TABLE userpdtrainingdetail
(
  id bigserial NOT NULL,
  userid bigint NOT NULL,
  trainingcompleted boolean,
  trainingcompletiondate timestamp with time zone,
  currentschoolyear integer,
  createduser bigint,
  createddate timestamp with time zone NOT NULL DEFAULT now(),  
  modifieduser bigint,
  modifieddate timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT pk_userpdtrainingdetails PRIMARY KEY (id),
  CONSTRAINT userpdtrainingdetails_userid_fk FOREIGN KEY (userid)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,  
  CONSTRAINT userpdtrainingdetails_createduser_fk FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT userpdtrainingdetails_modifieduser_fk FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE userpasswordreset ADD COLUMN password character varying(128),
ADD COLUMN createddate TIMESTAMP WITH time ZONE NOT NULL DEFAULT now(),
ADD COLUMN requesttype character varying(15);
ALTER TABLE userpasswordreset ALTER COLUMN auth_token drop not null;