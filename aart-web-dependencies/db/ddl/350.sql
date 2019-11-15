
-- 350.sql

alter table ititestsessionhistory drop column statestudentidentifier;

DROP INDEX idx_testcollection_contentareaid;

CREATE INDEX idx_testingprogram_programabbr
  ON testingprogram USING btree (programabbr);

CREATE INDEX idx_testcollection_phasetype
  ON testcollection USING btree (phasetype);

CREATE INDEX idx_testcollection_pooltype
  ON testcollection USING btree (pooltype);

CREATE INDEX idx_testcollection_course
  ON testcollection USING btree (courseid);

CREATE INDEX idx_testcollection_stage
  ON testcollection USING btree (stageid);

CREATE INDEX idx_gradebandgradecourse_gradecourse 
	ON gradebandgradecourse USING btree (gradecourseid);
CREATE INDEX idx_gradebandgradecourse_gradeband 
	ON gradebandgradecourse USING btree (gradebandid);