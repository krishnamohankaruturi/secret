-- US19025: FCS Audit Trails
DROP TABLE IF EXISTS firstcontactsurveyaudithistory;
CREATE TABLE firstcontactsurveyaudithistory(id BIGINT NOT NULL, eventname TEXT, modifieduser BIGINT NOT NULL, 
       statestudentidentifier CHARACTER VARYING(50), studentfirstname CHARACTER VARYING(80), studentlastname CHARACTER VARYING(80), surveyid BIGINT, studentid BIGINT,
       surveystatusbeforeedit CHARACTER VARYING(80), surveystatusafteredit CHARACTER VARYING(80),
       beforevalue JSON, currentvalue JSON, 
       createddate TIMESTAMP WITH TIME ZONE DEFAULT ('now'::TEXT)::TIMESTAMP WITHOUT TIME ZONE, domainaudithistoryid INTEGER);


ALTER TABLE firstcontactsurveyaudithistory ADD CONSTRAINT fk_domainaudithistoryid FOREIGN KEY (domainaudithistoryid)
      REFERENCES domainaudithistory (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

DROP INDEX IF EXISTS idx_firstcontactsurveyaudithistory_modifieduser;
CREATE INDEX idx_firstcontactsurveyaudithistory_modifieduser
  ON studentaudittrailhistory
  USING btree
  (modifieduser);

DROP INDEX IF EXISTS idx_firstcontactsurveyaudithistory_statestudentidentifier;
CREATE INDEX idx_firstcontactsurveyaudithistory_statestudentidentifier
  ON firstcontactsurveyaudithistory
  USING btree
  (statestudentidentifier COLLATE pg_catalog."default");

DROP INDEX IF EXISTS idx_firstcontactsurveyaudithistory_domainaudithistoryid;
CREATE INDEX idx_firstcontactsurveyaudithistory_domainaudithistoryid
  ON firstcontactsurveyaudithistory
  USING btree
  (domainaudithistoryid);

DROP INDEX IF EXISTS idx_firstcontactsurveyaudithistory_studentid;
CREATE INDEX idx_firstcontactsurveyaudithistory_studentid
  ON firstcontactsurveyaudithistory
  USING btree
  (studentid);

DROP INDEX IF EXISTS idx_firstcontactsurveyaudithistory_surveyid;
CREATE INDEX idx_firstcontactsurveyaudithistory_surveyid
  ON firstcontactsurveyaudithistory
  USING btree
  (surveyid);

DROP SEQUENCE IF EXISTS firstcontactsurveyaudithistory_id_seq;
CREATE SEQUENCE firstcontactsurveyaudithistory_id_seq;
