-- 370.sql

DROP INDEX IF EXISTS idx_enrollment_restrictionid;
CREATE INDEX idx_enrollment_restrictionid
  ON enrollment USING btree (restrictionid);
  
DROP INDEX IF EXISTS idx_enrollment_source;
CREATE INDEX idx_enrollment_source
  ON enrollment USING btree (source);
  
DROP INDEX IF EXISTS idx_enrollmentsrosters_courseenrollmentstatusid;
CREATE INDEX idx_enrollmentsrosters_courseenrollmentstatusid
  ON enrollmentsrosters USING btree (courseenrollmentstatusid); 
  
DROP INDEX IF EXISTS idx_enrollmentsrosters_source;
CREATE INDEX idx_enrollmentsrosters_source
  ON enrollmentsrosters USING btree (source);
  
DROP INDEX IF EXISTS idx_gradeband_contentareaid;
CREATE INDEX idx_gradeband_contentareaid
  ON gradeband USING btree (contentareaid); 
  
DROP INDEX IF EXISTS idx_gradecontentareatesttypesubjectarea_gradecourseid;
CREATE INDEX idx_gradecontentareatesttypesubjectarea_gradecourseid
  ON gradecontentareatesttypesubjectarea USING btree (gradecourseid);
  


