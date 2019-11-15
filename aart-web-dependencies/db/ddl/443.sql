--443.sql

CREATE TABLE reportprocessrecordcounts
(
  id bigserial NOT NULL,
  process character varying(200),
  assessmentprogramid bigint NOT NULL,
  contentareaid bigint NOT NULL,
  gradeid bigint NOT NULL,
  count bigint,	
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  batchreportprocessid bigint NOT NULL,
  CONSTRAINT reportprocessrecordcounts_batchreportprocessid_fk FOREIGN KEY (batchreportprocessid)
      REFERENCES reportprocess (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

--US16330: Reports - Report process - KAP package student reports into school files
alter table organizationreportdetails add column gradecourseabbrname varchar(10);

--Changes from CB for testpanel
ALTER TABLE testpanel RENAME organizationid to assessmentprogramid;
ALTER TABLE testpanel ADD CONSTRAINT testpanel_assessmentprogramid_fkey FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE testpanel DROP COLUMN groupid;
ALTER TABLE testpanel DROP COLUMN companyid;
ALTER TABLE testpanel DROP COLUMN status;
ALTER TABLE testpanel DROP COLUMN modifiedusername;
ALTER TABLE testpanel DROP COLUMN createusername;
ALTER TABLE testpanel DROP COLUMN createuserid;
ALTER TABLE testpanel DROP COLUMN modifieduserid;

CREATE SEQUENCE testpanel_id_seq;

CREATE SEQUENCE testpanelstage_id_seq;

ALTER TABLE testpanelstagetestcollection DROP COLUMN modifiedusername;
ALTER TABLE testpanelstagetestcollection DROP COLUMN createusername;
ALTER TABLE testpanelstagetestcollection DROP COLUMN createuserid;
ALTER TABLE testpanelstagetestcollection DROP COLUMN modifieduserid;

CREATE SEQUENCE testpanelstagetestcollection_id_seq;

ALTER TABLE testpanelstagemapping DROP COLUMN modifiedusername;
ALTER TABLE testpanelstagemapping DROP COLUMN createusername;
ALTER TABLE testpanelstagemapping DROP COLUMN createuserid;
ALTER TABLE testpanelstagemapping DROP COLUMN modifieduserid;

CREATE SEQUENCE testpanelstagemapping_id_seq;

alter table reportsmedianscore add column batchreportprocessid bigint;	
 alter table reportsmedianscore drop column detailedreportpath;
  alter table reportsmedianscore drop column schoolreportpdfpath;
 alter table reportsmedianscore drop column schoolreportpdfsize;
 alter table reportsmedianscore drop column schoolreportzipsize;
