-- ddl/.sql

-- US17715, adding student previous year level to reports
-- this table is for suppressing specific combinations of content areas and grades
CREATE SEQUENCE suppressedlevel_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE suppressedlevel (
	id BIGINT DEFAULT NEXTVAL('suppressedlevel_id_seq'::regclass),
	contentareaid BIGINT NOT NULL,
	gradecourseid BIGINT NOT NULL,
	activeflag BOOLEAN,
	createddate TIMESTAMP WITH TIME ZONE,
	createduser BIGINT,
	modifieddate TIMESTAMP WITH TIME ZONE,
	modifieduser BIGINT,
	
	CONSTRAINT pk_suppressedlevel PRIMARY KEY (id),
	CONSTRAINT fk_suppressedlevel_ca FOREIGN KEY (contentareaid)
		REFERENCES contentarea (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT fk_suppressedlevel_gc FOREIGN KEY (gradecourseid)
		REFERENCES gradecourse (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE studentreport ADD COLUMN previousyearlevelid BIGINT;


--US17838: Reports: Map student scale score and MDPT score to combined level
alter table studentreport add column combinedlevel decimal;
--Create index for the new column
DROP INDEX IF EXISTS idx_studentreport_combinedlevel;
CREATE INDEX idx_studentreport_combinedlevel
  ON studentreport
  USING btree
  (combinedlevel);
  
  
--Indexing BatchUpload and BatchUploadReason tables   
DROP INDEX IF EXISTS idx_batchuploadreason_batchuploadid;
CREATE INDEX idx_batchuploadreason_batchuploadid
  ON batchuploadreason
  USING btree
  (batchuploadid);

DROP INDEX IF EXISTS idx_batchuploadreason_fieldname;
CREATE INDEX idx_batchuploadreason_fieldname
  ON batchuploadreason
  USING btree
  (fieldname);

DROP INDEX IF EXISTS idx_batchuploadreason_line;
CREATE INDEX idx_batchuploadreason_line
  ON batchuploadreason
  USING btree
  (line);

DROP INDEX IF EXISTS idx_batchupload_uploadtypeid;
CREATE INDEX idx_batchupload_uploadtypeid
  ON batchupload
  USING btree
  (uploadtypeid);

DROP INDEX IF EXISTS idx_batchupload_assessmentprogramid;
CREATE INDEX idx_batchupload_assessmentprogramid
  ON batchupload
  USING btree
  (assessmentprogramid);

DROP INDEX IF EXISTS idx_batchupload_contentareaid;
CREATE INDEX idx_batchupload_contentareaid
  ON batchupload
  USING btree
  (contentareaid);

DROP INDEX IF EXISTS idx_batchupload_createduser;
CREATE INDEX idx_batchupload_createduser
  ON batchupload
  USING btree
  (createduser);

DROP INDEX IF EXISTS idx_batchupload_activeflag;
CREATE INDEX idx_batchupload_activeflag
  ON batchupload
  USING btree
  (activeflag);

DROP INDEX IF EXISTS idx_batchupload_status;
CREATE INDEX idx_batchupload_status
  ON batchupload
  USING btree
  (status);

DROP INDEX IF EXISTS idx_batchupload_uploadedusergroupid;
CREATE INDEX idx_batchupload_uploadedusergroupid
  ON batchupload
  USING btree
  (uploadedusergroupid);

DROP INDEX IF EXISTS idx_batchupload_uploadeduserorgid;
CREATE INDEX idx_batchupload_uploadeduserorgid
  ON batchupload
  USING btree
  (uploadeduserorgid);

-- Adding indexes on testsession subjectareaid field and activeflag field on enrollmenttesttypesubjectarea.
DROP INDEX IF EXISTS idx_testsession_subjectarea;
CREATE INDEX idx_testsession_subjectarea ON testsession USING btree (subjectareaid);

DROP INDEX IF EXISTS idx_enrollmenttesttypesubjectarea_active;
CREATE INDEX idx_enrollmenttesttypesubjectarea_active ON enrollmenttesttypesubjectarea USING btree(activeflag);




DROP INDEX IF EXISTS idx_modulereport_createduser;

CREATE INDEX idx_modulereport_createduser
  ON modulereport
  USING btree
  (createduser);
  
DROP INDEX IF EXISTS idx_modulereport_reporttypeid;

CREATE INDEX idx_modulereport_reporttypeid
  ON modulereport
  USING btree
  (reporttypeid);

  
--Adding indexes on CombinedLevelMap table  
DROP INDEX IF EXISTS idx_combinedlevelmap_assessmentprogramid; 

CREATE INDEX idx_combinedlevelmap_assessmentprogramid
  ON combinedlevelmap
  USING btree
  (assessmentprogramid);


DROP INDEX IF EXISTS idx_combinedlevelmap_gradeid; 

CREATE INDEX idx_combinedlevelmap_gradeid
  ON combinedlevelmap
  USING btree
  (gradeid);


DROP INDEX IF EXISTS idx_combinedlevelmap_subjectid; 

CREATE INDEX idx_combinedlevelmap_subjectid
  ON combinedlevelmap
  USING btree
  (subjectid);

DROP INDEX IF EXISTS idx_combinedlevelmap_batchuploadid; 

CREATE INDEX idx_combinedlevelmap_batchuploadid
  ON combinedlevelmap
  USING btree
  (batchuploadid);

DROP INDEX IF EXISTS idx_combinedlevelmap_stageslowscalescore; 

CREATE INDEX idx_combinedlevelmap_stageslowscalescore
  ON combinedlevelmap
  USING btree
  (stageslowscalescore);

DROP INDEX IF EXISTS idx_combinedlevelmap_stageshighscalescore; 

CREATE INDEX idx_combinedlevelmap_stageshighscalescore
  ON combinedlevelmap
  USING btree
  (stageshighscalescore);

DROP INDEX IF EXISTS idx_combinedlevelmap_performancescalescore; 

CREATE INDEX idx_combinedlevelmap_performancescalescore
  ON combinedlevelmap
  USING btree
  (performancescalescore);

DROP INDEX IF EXISTS idx_combinedlevelmap_combinedlevel; 
  
CREATE INDEX idx_combinedlevelmap_combinedlevel
  ON combinedlevelmap
  USING btree
  (combinedlevel);  

DROP INDEX IF EXISTS idx_combinedlevelmap_schoolyear; 
  
CREATE INDEX idx_combinedlevelmap_schoolyear
  ON combinedlevelmap
  USING btree
  (schoolyear);  

DROP INDEX IF EXISTS idx_testpanel_assessmentprogramid;
CREATE INDEX idx_testpanel_assessmentprogramid ON testpanel USING btree (assessmentprogramid);
DROP INDEX IF EXISTS idx_testpanel_contentareaid;
CREATE INDEX idx_testpanel_contentareaid ON testpanel USING btree (contentareaid);
DROP INDEX IF EXISTS idx_testpanel_gradecourseid;
CREATE INDEX idx_testpanel_gradecourseid ON testpanel USING btree (gradecourseid);
DROP INDEX IF EXISTS idx_testpanel_activeflag;
CREATE INDEX idx_testpanel_activeflag ON testpanel USING btree (activeflag);

DROP INDEX IF EXISTS idx_testpanelstage_testpanelid;
CREATE INDEX idx_testpanelstage_testpanelid ON testpanelstage USING btree (testpanelid);
DROP INDEX IF EXISTS idx_testpanelstage_stageid;
CREATE INDEX idx_testpanelstage_stageid ON testpanelstage USING btree (stageid);
DROP INDEX IF EXISTS idx_testpanelstage_activeflag;
CREATE INDEX idx_testpanelstage_activeflag ON testpanelstage USING btree (activeflag);

DROP INDEX IF EXISTS idx_testpanelstagemapping_testpanelstageid;
CREATE INDEX idx_testpanelstagemapping_testpanelstageid ON testpanelstagemapping USING btree (testpanelstageid);
DROP INDEX IF EXISTS idx_testpanelstagemapping_externaltestid1;
CREATE INDEX idx_testpanelstagemapping_externaltestid1 ON testpanelstagemapping USING btree (externaltestid1);
DROP INDEX IF EXISTS idx_testpanelstagemapping_externaltestid2;
CREATE INDEX idx_testpanelstagemapping_externaltestid2 ON testpanelstagemapping USING btree (externaltestid2);
DROP INDEX IF EXISTS idx_testpanelstagemapping_externaltestid3;
CREATE INDEX idx_testpanelstagemapping_externaltestid3 ON testpanelstagemapping USING btree (externaltestid3);
DROP INDEX IF EXISTS idx_testpanelstagemapping_interimtheta1;
CREATE INDEX idx_testpanelstagemapping_interimtheta1 ON testpanelstagemapping USING btree (interimtheta1);
DROP INDEX IF EXISTS idx_testpanelstagemapping_interimtheta2;
CREATE INDEX idx_testpanelstagemapping_interimtheta2 ON testpanelstagemapping USING btree (interimtheta2);
DROP INDEX IF EXISTS idx_testpanelstagemapping_activeflag;
CREATE INDEX idx_testpanelstagemapping_activeflag ON testpanelstagemapping USING btree (activeflag);

DROP INDEX IF EXISTS idx_testpanelstagetestcollection_activeflag;
CREATE INDEX idx_testpanelstagetestcollection_activeflag ON testpanelstagetestcollection USING btree (activeflag);
DROP INDEX IF EXISTS idx_testpanelstagetestcollection_testpanelstageid;
CREATE INDEX idx_testpanelstagetestcollection_testpanelstageid ON testpanelstagetestcollection USING btree (testpanelstageid);
DROP INDEX IF EXISTS idx_testpanelstagetestcollection_externaltestcollectionid;
CREATE INDEX idx_testpanelstagetestcollection_externaltestcollectionid ON testpanelstagetestcollection USING btree (externaltestcollectionid);

DROP INDEX IF EXISTS idx_studentstests_previousstudentstestid;
CREATE INDEX idx_studentstests_previousstudentstestid ON studentstests USING btree (previousstudentstestid);
DROP INDEX IF EXISTS idx_studentstests_interimtheta;
CREATE INDEX idx_studentstests_interimtheta ON studentstests USING btree (interimtheta);

DROP INDEX IF EXISTS idx_testcollection_externalid;
CREATE INDEX idx_testcollection_externalid ON testcollection USING btree (externalid);

DROP INDEX IF EXISTS idx_test_externalid;
CREATE INDEX idx_test_externalid ON test USING btree (externalid);

DROP INDEX IF EXISTS idx_testsession_testpanelid;
CREATE INDEX idx_testsession_testpanelid ON testsession USING btree (testpanelid);

DROP INDEX IF EXISTS idx_exitwithoutsavetest_studenttestsectionid;

CREATE INDEX idx_exitwithoutsavetest_studenttestsectionid
  ON exitwithoutsavetest USING btree (studenttestsectionid);  

DROP INDEX IF EXISTS idx_textaccommodation_contentgroupid;

CREATE INDEX idx_textaccommodation_contentgroupid
  ON textaccommodation USING btree (contentgroupid);  
  
  
DROP INDEX IF EXISTS idx_operationaltestwindow_effectivedate;

CREATE INDEX idx_operationaltestwindow_effectivedate
  ON operationaltestwindow USING btree (effectivedate); 


DROP INDEX IF EXISTS idx_operationaltestwindow_expirydate;

CREATE INDEX idx_operationaltestwindow_expirydate
  ON operationaltestwindow USING btree (expirydate); 
  
  
DROP INDEX IF EXISTS idx_operationaltestwindow_suspendwindow;

CREATE INDEX idx_operationaltestwindow_suspendwindow
  ON operationaltestwindow USING btree (suspendwindow);