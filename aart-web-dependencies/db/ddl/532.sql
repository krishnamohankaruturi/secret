-- ddl/532.sql

-- US17558 change the logic to have soft delete instead of hard delete
ALTER TABLE usersorganizations ADD COLUMN activeflag boolean DEFAULT true;
ALTER TABLE userorganizationsgroups ADD COLUMN activeflag boolean DEFAULT true;

ALTER TABLE usersorganizations ADD CONSTRAINT aartuserid_organizationid UNIQUE (aartuserid,organizationid);

-- Table reportassessmentprogramgroups created for US17540
CREATE SEQUENCE reportassessmentprogramgroup_id_seq;

CREATE TABLE reportassessmentprogramgroup
(
  id bigint NOT NULL DEFAULT nextval('reportassessmentprogramgroup_id_seq'::regclass),
  reportassessmentprogramid bigint NOT NULL,
  groupid bigint NOT NULL,
  activeflag boolean DEFAULT true,
  CONSTRAINT pk_reportassessmentprogramgroup PRIMARY KEY (id),
  CONSTRAINT reportassessmentprogramgroup_groupsid_fk FOREIGN KEY (groupid)
      REFERENCES groups (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT reportassessmentprogramgroup_reportassessmentprogramid_fk FOREIGN KEY (reportassessmentprogramid)
      REFERENCES reportassessmentprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- script changes from change pond
-- SQL for US16360
-- column added for state id and subject id
alter table reportassessmentprogram add column stateid bigint;
alter table reportassessmentprogram add column subjectid bigint default null;
Alter table reportassessmentprogram add column authorityid bigint default null;
Alter table reportassessmentprogram add CONSTRAINT reportassessmentprogram_authorityid_fk FOREIGN KEY (authorityid)
      REFERENCES authorities (id);

-- foreign key to organization table
alter table reportassessmentprogram add CONSTRAINT reportassessmentprogram_stateid_fk FOREIGN KEY (stateid)
      REFERENCES organization(id);

-- foreign key to contentarea table for subject Id.
alter table reportassessmentprogram add CONSTRAINT reportassessmentprogram_subjectid_fk FOREIGN KEY (subjectid)
      REFERENCES contentarea(id);

-- composite unique key 
ALTER TABLE reportassessmentprogram ADD CONSTRAINT assessmentprogramid_reporttypeid_stateid_subjectid 
	UNIQUE (assessmentprogramid,reporttypeid,subjectid,stateid);

-- Function for fetching states and will make entry in reportassessmentprogram table 
-- for each and every state binded to that assessmentprogram

CREATE OR REPLACE FUNCTION reportassessmentprogram_fn(
    assessmentprogramcode text,
    reporttypecode text,
    subjectcode text,
    authoritycode text)
  RETURNS integer AS
$BODY$
declare 
 assessmentprogram_id bigint;
 reportypeid bigint;
 subjectid bigint;
 stateid bigint;
 authority_id bigint;
BEGIN

 Select into assessmentprogram_id (Select id from assessmentprogram where abbreviatedname ilike (assessmentprogramcode) and activeflag is true); 
 Select into reportypeid (Select id from category where categorycode in (reporttypecode) and activeflag is true);
 Select into subjectid (Select id from contentarea where abbreviatedname ilike (subjectcode) and activeflag is true);
 Select into authority_id (select id from authorities where authority ilike (authoritycode) and activeflag is true limit 1);

 FOR stateid IN(Select orgass.organizationid from orgassessmentprogram orgass 
  inner join organization org on org.id = orgass.organizationid 
  where assessmentprogramid in (Select id from assessmentprogram 
  where abbreviatedname ilike (assessmentprogramcode) and activeflag is true) and organizationtypeid = 2 and orgass.activeflag is true 
  and org.activeflag is true)
 LOOP
  INSERT INTO reportassessmentprogram(
  reporttypeid, assessmentprogramid, createdate, modifieddate, 
  readytoview, activeflag, subjectid, stateid,authorityid)
  VALUES ( reportypeid, assessmentprogram_id, current_timestamp, current_timestamp, 
  false,true, subjectid, stateid,authority_id);
 END LOOP;
RETURN 0;
 EXCEPTION 
  WHEN unique_violation THEN
 RAISE NOTICE 'unique key violation constraint';
RETURN 0;
    WHEN OTHERS THEN
RETURN 1;
 END;
 $BODY$
  LANGUAGE plpgsql VOLATILE;
 