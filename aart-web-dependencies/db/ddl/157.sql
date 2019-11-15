

--US14176 Name:  Summative Reports - cPass Report Upload CSV and Report Upload UI Changes 

CREATE TABLE entityscalescores
(
  id bigserial NOT NULL,
  testid bigint NOT NULL,
  entitytype character varying(100),
  studentid bigint NOT NULL,
  organizationid bigint NOT NULL,
  scalescore character varying(100),
  achievementlevellabel character varying(100),
  sem character varying(100),
  scaleandsem character varying(100),    
  userreportuploadid bigint NOT NULL,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  modifieddate timestamp with time zone NOT NULL DEFAULT now(),
  createduser bigint,
  modifieduser bigint,
  activeflag boolean DEFAULT true,  
  CONSTRAINT entityscalescores_id_fk PRIMARY KEY (id),
  CONSTRAINT entityscalescores_testid_fk FOREIGN KEY (testid)
      REFERENCES test (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT entityscalescores_studentid_fk FOREIGN KEY (studentid)
      REFERENCES student (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT entityscalescores_organizationid_fk FOREIGN KEY (organizationid)
      REFERENCES organization (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT entityscalescores_userreportuploadid_fk FOREIGN KEY (userreportuploadid)
      REFERENCES userreportupload (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

