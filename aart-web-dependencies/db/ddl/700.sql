-- script from TDE Team

CREATE TABLE lcs
(
  lcsid text NOT NULL,
  createddate timestamp with time zone DEFAULT now(),
  CONSTRAINT lcsid_pkey PRIMARY KEY (lcsid)
)
WITH (
  OIDS=FALSE
);

DROP TABLE IF EXISTS archivetablecount;
DROP TABLE IF EXISTS batchstudenttrackerreason;
DROP TABLE IF EXISTS communicationmessageorgassessmentprogram;
DROP TABLE IF EXISTS domainaudithistory;
DROP TABLE IF EXISTS gradetestspec_row;
DROP TABLE IF EXISTS row_count;
DROP TABLE IF EXISTS studentaudit;
--DROP TABLE IF EXISTS studentsresponses_aart;
DROP TABLE IF EXISTS temp_students_enrollments;
DROP TABLE IF EXISTS tmp_reactivated_ids;
DROP TABLE IF EXISTS tmp_tests_with_new_rosters;
DROP TABLE IF EXISTS tmp_tests_with_old_rosters;
ALTER TABLE blueprintcriteriadescription ADD CONSTRAINT blueprintcriteriadescription_pk PRIMARY KEY (id);
--ALTER TABLE communicationmessagestate ADD CONSTRAINT cmstate_pk PRIMARY KEY (stateid, comminicationmessageid,groupid);
ALTER TABLE externalstudentreports ADD CONSTRAINT externalstudentreports_pk PRIMARY KEY (id);
ALTER TABLE grouprestrictions ADD CONSTRAINT grouprestrictions_pk PRIMARY KEY (groupid,organizationid,assessmentprogramid);
ALTER TABLE ititestsessionsensitivitytags ADD CONSTRAINT ititestsessionsensitivitytags_pk PRIMARY KEY (ititestsessionhistoryid,sensitivitytag);
--ALTER TABLE operationaltestwindowstudent ADD CONSTRAINT operationaltestwindowstudent_pk 
			--PRIMARY KEY(studentid, contentareaid, courseid, operationaltestwindowid);
ALTER TABLE organizationannualresets ADD CONSTRAINT organizationannualresets_pk PRIMARY KEY(id);
ALTER TABLE organizationhierarchy ADD CONSTRAINT organizationhierarchy_pk PRIMARY KEY(organizationid, organizationtypeid);
ALTER TABLE organizationreportdetails ADD CONSTRAINT organizationreportdetails_pk PRIMARY KEY(id);
--ALTER TABLE reportprocessreason ADD CONSTRAINT reportprocessreason_pk PRIMARY KEY(reportprocessid);
ALTER TABLE scoringuploadfile ADD CONSTRAINT scoringuploadfile_pk PRIMARY KEY(id);

ALTER TABLE taskvariantrescore ADD COLUMN id integer;
CREATE SEQUENCE taskvariantrescore_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 297
  CACHE 1;
update taskvariantrescore set id= nextval('taskvariantrescore_id_seq');
ALTER TABLE taskvariantrescore ALTER COLUMN id SET NOT NULL;
ALTER TABLE taskvariantrescore ADD CONSTRAINT taskvariantrescore_id PRIMARY KEY(id);

ALTER TABLE studentsresponsescopy ADD COLUMN id bigint;
CREATE SEQUENCE studentsresponsescopy_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 297
  CACHE 1;
update studentsresponsescopy set id= nextval('studentsresponsescopy_id_seq');
ALTER TABLE studentsresponsescopy ALTER COLUMN id SET NOT NULL;
ALTER TABLE studentsresponsescopy ADD CONSTRAINT studentsresponsescopy_id PRIMARY KEY(id);

ALTER TABLE exitwithoutsavetest ADD COLUMN id bigint;
CREATE SEQUENCE exitwithoutsavetest_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 297
  CACHE 1;
update exitwithoutsavetest set id= nextval('exitwithoutsavetest_id_seq');
ALTER TABLE exitwithoutsavetest ALTER COLUMN id SET NOT NULL;
ALTER TABLE exitwithoutsavetest ADD CONSTRAINT exitwithoutsavetest_id PRIMARY KEY(id);

ALTER TABLE lcsstudentstests ADD COLUMN id bigint;
CREATE SEQUENCE lcsstudentstests_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 297
  CACHE 1;
update lcsstudentstests set id= nextval('lcsstudentstests_id_seq');
ALTER TABLE lcsstudentstests ALTER COLUMN id SET NOT NULL;
ALTER TABLE lcsstudentstests ADD CONSTRAINT lcsstudentstests_id PRIMARY KEY(id);

SELECT setval('taskvariantrescore_id_seq', (select max(id) from taskvariantrescore));
SELECT setval('studentsresponsescopy_id_seq', (select max(id) from studentsresponsescopy));
SELECT setval('exitwithoutsavetest_id_seq', (select max(id) from exitwithoutsavetest));
SELECT setval('lcsstudentstests_id_seq', (select max(id) from lcsstudentstests));

ALTER TABLE taskvariantrescore ALTER COLUMN id SET DEFAULT nextval('taskvariantrescore_id_seq'::regclass);
ALTER TABLE studentsresponsescopy ALTER COLUMN id SET DEFAULT nextval('studentsresponsescopy_id_seq'::regclass);
ALTER TABLE exitwithoutsavetest ALTER COLUMN id SET DEFAULT nextval('exitwithoutsavetest_id_seq'::regclass);
ALTER TABLE lcsstudentstests ALTER COLUMN id SET DEFAULT nextval('lcsstudentstests_id_seq'::regclass);