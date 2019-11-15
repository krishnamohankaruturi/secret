-- 400.sql

CREATE TABLE studentreport
(
  id bigserial NOT NULL,
  studentid bigint NOT NULL,
  enrollmentid bigint NOT NULL,
  grade bigint,
  contentarea bigint,
  attendanceschoolid bigint NOT NULL,
  districtid bigint NOT NULL,
  stateid bigint NOT NULL,
  studenttest1id  bigint NOT NULL,
  studenttest2id  bigint NOT NULL,
  externaltest1id  bigint NOT NULL,
  externaltest2id  bigint NOT NULL,
  levelid bigint,
  rawscore numeric(6,3),
  subscore numeric(6,3),
  batchreportprocessid bigint NOT NULL, 	
  CONSTRAINT studentreport_pkey PRIMARY KEY (id),
  CONSTRAINT studentreport_attendanceschoolid_fk FOREIGN KEY (attendanceschoolid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT studentreport_studentid_fk FOREIGN KEY (studentid)
      REFERENCES student (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT studentreport_enrollmentid_fk FOREIGN KEY (enrollmentid)
      REFERENCES enrollment (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION, 
 CONSTRAINT studentreport_districtid_fk FOREIGN KEY (districtid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION, 
CONSTRAINT studentreport_stateid_fk FOREIGN KEY (stateid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
CONSTRAINT studentreport_batchreportprocessid_fk FOREIGN KEY (batchreportprocessid)
      REFERENCES reportprocess (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);


CREATE TABLE reportprocessreason
(
  reportprocessid bigint NOT NULL,
  studentid bigint,
  reason text,
  CONSTRAINT batchreportprocessreason_reportprocessid_fkey FOREIGN KEY (reportprocessid)
      REFERENCES reportprocess (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

alter table subscoreframework alter column frameworklevel1 type character varying(50);
alter table subscoreframework alter column frameworklevel2 type character varying(50);
alter table subscoreframework alter column frameworklevel3 type character varying(50);
alter table subscoreframework alter column framework type character varying(50);

alter table fieldspecification alter column minimum type bigint;
alter table fieldspecification add column minimumregex character varying(300);
alter table fieldspecification add column maximumregex character varying(300);


