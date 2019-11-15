CREATE TABLE usersecurityagreement
(
  aartuserid bigint NOT NULL,
  assessmentprogramid bigint NOT NULL,
  schoolyear bigint,
  agreementelection boolean,
  agreementsigneddate timestamp,
  expiredate character varying(25),
  CONSTRAINT usersecurityagreement_aartuserid_fk FOREIGN KEY (aartuserid)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT usersecurityagreement_assessmentprogramid_fk FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION    
); 