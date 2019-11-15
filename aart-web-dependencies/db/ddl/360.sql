--US15752: Technical: Add indexes to all FK's

DROP INDEX IF EXISTS idx_accessibilityfile_taskvariantid;  
CREATE INDEX idx_accessibilityfile_taskvariantid
  ON accessibilityfile USING btree (taskvariantid); 
  
DROP INDEX IF EXISTS idx_accessibilityfile_assessmentprogramid;  
CREATE INDEX idx_accessibilityfile_assessmentprogramid
  ON accessibilityfile USING btree (assessmentprogramid);   
  
DROP INDEX IF EXISTS idx_activity_moduleid;  
CREATE INDEX idx_activity_moduleid
  ON activity USING btree (moduleid);  
  
DROP INDEX IF EXISTS idx_activity_userid;  
CREATE INDEX idx_activity_userid
  ON activity USING btree (userid);
  
DROP INDEX IF EXISTS idx_assessment_testingprogramid;  
CREATE INDEX idx_assessment_testingprogramid
  ON assessment USING btree (testingprogramid);
  
DROP INDEX IF EXISTS idx_assessment_assessmentname;  
CREATE INDEX idx_assessment_assessmentname
  ON assessment USING btree (assessmentname); 
  
DROP INDEX IF EXISTS idx_assessment_autoenrollmentflag;  
CREATE INDEX idx_assessment_autoenrollmentflag
  ON assessment USING btree (autoenrollmentflag); 
  
DROP INDEX IF EXISTS idx_assessmentprogramgrades_assessmentprogramid;  
CREATE INDEX idx_assessmentprogramgrades_assessmentprogramid
  ON assessmentprogramgrades USING btree (assessmentprogramid);    
    
DROP INDEX IF EXISTS idx_assessmentstestcollections_assessmentid;  
CREATE INDEX idx_assessmentstestcollections_assessmentid
  ON assessmentstestcollections USING btree (assessmentid);
  
DROP INDEX IF EXISTS idx_assessmentstestcollections_testcollectionid;  
CREATE INDEX idx_assessmentstestcollections_testcollectionid
  ON assessmentstestcollections USING btree (testcollectionid);
  
DROP INDEX IF EXISTS idx_autoregistrationcriteria_assessmentid;  
CREATE INDEX idx_autoregistrationcriteria_assessmentid
  ON autoregistrationcriteria USING btree (assessmentid);
  
DROP INDEX IF EXISTS idx_autoregistrationcriteria_assessmentprogramid;  
CREATE INDEX idx_autoregistrationcriteria_assessmentprogramid
  ON autoregistrationcriteria USING btree (assessmentprogramid);
  
DROP INDEX IF EXISTS idx_autoregistrationcriteria_contentareatesttypesubjectareaid;  
CREATE INDEX idx_autoregistrationcriteria_contentareatesttypesubjectareaid
  ON autoregistrationcriteria USING btree (contentareatesttypesubjectareaid);
  
DROP INDEX IF EXISTS idx_autoregistrationcriteria_gradecourseid;  
CREATE INDEX idx_autoregistrationcriteria_gradecourseid
  ON autoregistrationcriteria USING btree (gradecourseid);
  
DROP INDEX IF EXISTS idx_autoregistrationcriteria_testingprogramid;  
CREATE INDEX idx_autoregistrationcriteria_testingprogramid
  ON autoregistrationcriteria USING btree (testingprogramid);
  
DROP INDEX IF EXISTS idx_batchregistration_assessment;  
CREATE INDEX idx_batchregistration_assessment
  ON batchregistration USING btree (assessment); 
  
DROP INDEX IF EXISTS idx_batchregistration_assessmentprogram;  
CREATE INDEX idx_batchregistration_assessmentprogram
  ON batchregistration USING btree (assessmentprogram);
  
DROP INDEX IF EXISTS idx_batchregistration_contentareaid;  
CREATE INDEX idx_batchregistration_contentareaid
  ON batchregistration USING btree (contentareaid);
  
DROP INDEX IF EXISTS idx_batchregistration_grade;  
CREATE INDEX idx_batchregistration_grade
  ON batchregistration USING btree (grade); 
  
DROP INDEX IF EXISTS idx_batchregistration_subject;  
CREATE INDEX idx_batchregistration_subject
  ON batchregistration USING btree (subject);         
    
DROP INDEX IF EXISTS idx_batchregistration_testingprogram;  
CREATE INDEX idx_batchregistration_testingprogram
  ON batchregistration USING btree (testingprogram); 
  
DROP INDEX IF EXISTS idx_batchregistration_testtype;  
CREATE INDEX idx_batchregistration_testtype
  ON batchregistration USING btree (testtype); 
  
DROP INDEX IF EXISTS idx_batchregistration_submissiondate;  
CREATE INDEX idx_batchregistration_submissiondate
  ON batchregistration USING btree (submissiondate);
  
DROP INDEX IF EXISTS idx_batchregistrationreason_batchregistrationid;  
CREATE INDEX idx_batchregistrationreason_batchregistrationid
  ON batchregistrationreason USING btree (batchregistrationid);
  
DROP INDEX IF EXISTS idx_batchregistrationreason_batchregistrationid;  
CREATE INDEX idx_batchregistrationreason_batchregistrationid
  ON batchregistrationreason USING btree (batchregistrationid);
  
DROP INDEX IF EXISTS idx_batchstudenttracker_assessmentprogram;  
CREATE INDEX idx_batchstudenttracker_assessmentprogram
  ON batchstudenttracker USING btree (assessmentprogram);
  
DROP INDEX IF EXISTS idx_batchstudenttracker_contentareaid;  
CREATE INDEX idx_batchstudenttracker_contentareaid
  ON batchstudenttracker USING btree (contentareaid); 
  
DROP INDEX IF EXISTS idx_batchstudenttracker_orgid;  
CREATE INDEX idx_batchstudenttracker_orgid
  ON batchstudenttracker USING btree (orgid);
  
DROP INDEX IF EXISTS idx_batchstudenttrackerreason_batchstudenttrackerid;  
CREATE INDEX idx_batchstudenttrackerreason_batchstudenttrackerid
  ON batchstudenttrackerreason USING btree (batchstudenttrackerid);          
  
DROP INDEX IF EXISTS idx_batchstudenttrackerreason_gradecourseid;  
CREATE INDEX idx_batchstudenttrackerreason_gradecourseid
  ON batchstudenttrackerreason USING btree (gradecourseid);
          
DROP INDEX IF EXISTS idx_batchstudenttrackerreason_studentid;  
CREATE INDEX idx_batchstudenttrackerreason_studentid
  ON batchstudenttrackerreason USING btree (studentid);
  
DROP INDEX IF EXISTS idx_batchstudenttrackerreason_testid;  
CREATE INDEX idx_batchstudenttrackerreason_testid
  ON batchstudenttrackerreason USING btree (testid);  
      
DROP INDEX IF EXISTS idx_blueprint_contentareaid;  
CREATE INDEX idx_blueprint_contentareaid
  ON blueprint USING btree (contentareaid);
  
DROP INDEX IF EXISTS idx_blueprint_gradebandid;  
CREATE INDEX idx_blueprint_gradebandid
  ON blueprint USING btree (gradebandid);
  
DROP INDEX IF EXISTS idx_blueprint_gradecourseid;  
CREATE INDEX idx_blueprint_gradecourseid
  ON blueprint USING btree (gradecourseid);
  
DROP INDEX IF EXISTS idx_blueprintessentialelements_essentialelementid;  
CREATE INDEX idx_blueprintessentialelements_essentialelementid
  ON blueprintessentialelements USING btree (essentialelementid);    
  
DROP INDEX IF EXISTS idx_blueprintessentialelements_essentialelementid;  
CREATE INDEX idx_blueprintessentialelements_essentialelementid
  ON blueprintessentialelements USING btree (essentialelementid);           
  
DROP INDEX IF EXISTS idx_category_categorytypeid;  
CREATE INDEX idx_category_categorytypeid
  ON category USING btree (categorytypeid);     
        
DROP INDEX IF EXISTS idx_category_categorytypeid;  
CREATE INDEX idx_category_categorytypeid
  ON category USING btree (categorytypeid);    
    
DROP INDEX IF EXISTS idx_complexitybandrules_complexitybandid;  
CREATE INDEX idx_complexitybandrules_complexitybandid
  ON complexitybandrules USING btree (complexitybandid);    
    
DROP INDEX IF EXISTS idx_complexitybandrules_complexitybandtypeid;  
CREATE INDEX idx_complexitybandrules_complexitybandtypeid
  ON complexitybandrules USING btree (complexitybandtypeid);       
    
DROP INDEX IF EXISTS idx_compositestimulusvariant_compositestimulusvariantid;  
CREATE INDEX idx_compositestimulusvariant_compositestimulusvariantid
  ON compositestimulusvariant USING btree (compositestimulusvariantid);  
         
DROP INDEX IF EXISTS idx_compositestimulusvariant_stimulusvariantid;  
CREATE INDEX idx_compositestimulusvariant_stimulusvariantid
  ON compositestimulusvariant USING btree (stimulusvariantid);  
  
DROP INDEX IF EXISTS idx_contentareatesttypesubjectarea_contentareaid;  
CREATE INDEX idx_contentareatesttypesubjectarea_contentareaid
  ON contentareatesttypesubjectarea USING btree (contentareaid);
    
DROP INDEX IF EXISTS idx_contentareatesttypesubjectarea_testtypesubjectareaid;  
CREATE INDEX idx_contentareatesttypesubjectarea_testtypesubjectareaid
  ON contentareatesttypesubjectarea USING btree (testtypesubjectareaid);  
  
DROP INDEX IF EXISTS idx_contentframework_assessmentprogramid;  
CREATE INDEX idx_contentframework_assessmentprogramid
  ON contentframework USING btree (assessmentprogramid);    
  
DROP INDEX IF EXISTS idx_contentframework_contentareaid;  
CREATE INDEX idx_contentframework_contentareaid
  ON contentframework USING btree (contentareaid);     
  
DROP INDEX IF EXISTS idx_contentframework_frameworktypeid;  
CREATE INDEX idx_contentframework_frameworktypeid
  ON contentframework USING btree (frameworktypeid);  
  
DROP INDEX IF EXISTS idx_contentframework_gradebandid;  
CREATE INDEX idx_contentframework_gradebandid
  ON contentframework USING btree (gradebandid);  

DROP INDEX IF EXISTS idx_contentframework_gradecourseid;  
CREATE INDEX idx_contentframework_gradecourseid
  ON contentframework USING btree (gradecourseid);
  
DROP INDEX IF EXISTS idx_contentframeworkdetail_contentframeworkid;  
CREATE INDEX idx_contentframeworkdetail_contentframeworkid
  ON contentframeworkdetail USING btree (contentframeworkid);  

DROP INDEX IF EXISTS idx_contentgroup_taskvariantid;  
CREATE INDEX idx_contentgroup_taskvariantid
  ON contentgroup USING btree (taskvariantid); 

DROP INDEX IF EXISTS idx_contentgroup_foilid;  
CREATE INDEX idx_contentgroup_foilid
  ON contentgroup USING btree (foilid);
  
DROP INDEX IF EXISTS idx_contentgroup_stimulusvariantid;  
CREATE INDEX idx_contentgroup_stimulusvariantid
  ON contentgroup USING btree (stimulusvariantid);
  
DROP INDEX IF EXISTS idx_contentgroup_testid;  
CREATE INDEX idx_contentgroup_testid
  ON contentgroup USING btree (testid);
  
DROP INDEX IF EXISTS idx_contentgroup_testsectionid;  
CREATE INDEX idx_contentgroup_testsectionid
  ON contentgroup USING btree (testsectionid); 
  