--312.sql --indexes for adaptive and testobject
DROP INDEX IF EXISTS idx_readaloudaccommodation_contentgroupid;
CREATE INDEX idx_readaloudaccommodation_contentgroupid
  ON readaloudaccommodation USING btree (contentgroupid);

DROP INDEX IF EXISTS idx_readaloudaccommodation_accessibilityfileid;
CREATE INDEX idx_readaloudaccommodation_accessibilityfileid
  ON readaloudaccommodation USING btree (accessibilityfileid);

DROP INDEX IF EXISTS idx_contentgroup_stimulusvariantid;
CREATE INDEX idx_contentgroup_stimulusvariantid ON contentgroup
  USING btree (stimulusvariantid);

DROP INDEX IF EXISTS idx_contentgroup_taskvariantid;
CREATE INDEX idx_contentgroup_taskvariantid ON contentgroup
  USING btree (taskvariantid);

DROP INDEX IF EXISTS idx_contentgroup_testid;
CREATE INDEX idx_contentgroup_testid ON contentgroup
  USING btree (testid);

DROP INDEX IF EXISTS idx_contentgroup_testsectionid;
CREATE INDEX idx_contentgroup_testsectionid ON contentgroup
  USING btree (testsectionid);

DROP INDEX IF EXISTS idx_contentgroup_foilid;
CREATE INDEX idx_contentgroup_foilid ON contentgroup
  USING btree (foilid);

DROP INDEX IF EXISTS idx_testsectionresource_stimulusvariantid;
CREATE INDEX idx_testsectionresource_stimulusvariantid ON testsectionresource
  USING btree (stimulusvariantid);

DROP INDEX IF EXISTS idx_testsectionresource_testsectionid;
CREATE INDEX idx_testsectionresource_testsectionid ON testsectionresource
  USING btree (testsectionid);

DROP INDEX IF EXISTS idx_aartuser_username;
CREATE INDEX idx_aartuser_username ON aartuser (lower(username));

CREATE INDEX idx_testcollection_contentarea ON testcollection USING btree (contentareaid);
CREATE INDEX idx_testcollection_gradecourse ON testcollection USING btree (gradecourseid);
CREATE INDEX idx_testcollection_gradeband ON testcollection USING btree (gradebandid);
  
CREATE INDEX idx_testpart_testid ON testpart USING btree (testid);
CREATE INDEX idx_testpart_testsectioncontainerid ON testpart USING btree (testsectioncontainerid);
CREATE INDEX idx_testpart_partnumber ON testpart USING btree (partnumber);

CREATE INDEX idx_testsectionstaskvariants_testsectionid ON testsectionstaskvariants USING btree (testsectionid);
CREATE INDEX idx_testsectionstaskvariants_taskvariantid ON testsectionstaskvariants USING btree (taskvariantid);
CREATE INDEX idx_testsectionstaskvariants_testletid ON testsectionstaskvariants USING btree (testletid);

CREATE INDEX idx_testsection_testid ON testsection USING btree (testid);
CREATE INDEX idx_testsection_testsectioncontainerid ON testsection USING btree (testsectioncontainerid);
CREATE INDEX idx_testsection_toolsusageid ON testsection USING btree (toolsusageid);
CREATE INDEX idx_testsection_taskdeliveryruleid ON testsection USING btree (taskdeliveryruleid);

CREATE INDEX idx_testcollectionstests_testid ON testcollectionstests USING btree (testid);
CREATE INDEX idx_testcollectionstests_testcollectionid ON testcollectionstests USING btree (testcollectionid);
CREATE INDEX idx_testcollection_contentareaid ON testcollection USING btree (contentareaid);
CREATE INDEX idx_testfeedbackrules_testid ON testfeedbackrules USING btree (testid);

CREATE INDEX idx_taskvariant_tasktypeid ON taskvariant USING btree (tasktypeid);
CREATE INDEX idx_taskvariant_tasksubtypeid ON taskvariant USING btree (tasksubtypeid);
CREATE INDEX idx_taskvariant_tasklayoutid ON taskvariant USING btree (tasklayoutid);
CREATE INDEX idx_taskvariant_tasklayoutformatid ON taskvariant USING btree (tasklayoutformatid);

CREATE INDEX idx_taskvariant_contextstimulusid ON taskvariant USING btree (contextstimulusid);
CREATE INDEX idx_stimulusvariantattachment_stimulusvariantid ON stimulusvariantattachment USING btree (stimulusvariantid);
CREATE INDEX idx_taskvariantsstimulusvariants_taskvariantid ON taskvariantsstimulusvariants USING btree (taskvariantid);
CREATE INDEX idx_taskvariantsstimulusvariants_stimulusvariantid ON taskvariantsstimulusvariants USING btree (stimulusvariantid);
CREATE INDEX idx_testletstimulusvariants_testletid ON testletstimulusvariants USING btree (testletid);
CREATE INDEX idx_testletstimulusvariants_stimulusvariantid ON testletstimulusvariants USING btree (stimulusvariantid);

CREATE INDEX idx_compositestimulusvariant_compositestimulusvariantid ON compositestimulusvariant USING btree (compositestimulusvariantid);
CREATE INDEX idx_compositestimulusvariant_stimulusvariantid ON compositestimulusvariant USING btree (stimulusvariantid);

CREATE INDEX idx_testlet_questionviewid ON testlet USING btree (questionviewid);
CREATE INDEX idx_testlet_testletlayoutid ON testlet USING btree (testletlayoutid);
CREATE INDEX idx_testlet_displayviewid ON testlet USING btree (displayviewid);

CREATE INDEX idx_taskvariantsfoils_taskvariantid ON taskvariantsfoils USING btree (taskvariantid);
CREATE INDEX idx_taskvariantsfoils_foilid ON taskvariantsfoils USING btree (foilid);
CREATE INDEX idx_foilsstimulusvariants_foilid ON foilsstimulusvariants USING btree (foilid);
CREATE INDEX idx_foilsstimulusvariants_stimulusvariantid ON foilsstimulusvariants USING btree (stimulusvariantid);

CREATE INDEX idx_studentsadaptivetestsections_testpartid ON studentsadaptivetestsections USING btree (testpartid);
CREATE INDEX idx_studentsadaptivetestsections_tscontainerid ON studentsadaptivetestsections USING btree (testsectioncontainerid);
CREATE INDEX idx_studentsadaptivetestsections_studentstestid ON studentsadaptivetestsections USING btree (studentstestid);
CREATE INDEX idx_itemstatistic_taskvariantid ON itemstatistic USING btree (taskvariantid);
CREATE INDEX idx_itemstatistic_itemstatisticname ON itemstatistic USING btree (itemstatisticname);
CREATE INDEX idx_testsectioncontainerconstruct_tscontainerid ON testsectioncontainerconstruct USING btree (testsectioncontainerid);
CREATE INDEX idx_testsectioncontainerconstruct_idparametername ON testsectioncontainerconstruct USING btree (itemdiscriminationparametername);
CREATE INDEX idx_testsectioncontainerconstruct_testconstructid ON testsectioncontainerconstruct USING btree (testconstructid);
CREATE INDEX idx_testconstruct_testid ON testconstruct USING btree (testid);
CREATE INDEX idx_studentadaptivetestthetastatus_studentstestid ON studentadaptivetestthetastatus USING btree (studentstestid);
CREATE INDEX idx_testsectioncontainer_testid ON testsectioncontainer USING btree (testid);
CREATE INDEX idx_testsectioncontainerthetanode ON testsectioncontainerthetanode USING btree (testsectioncontainerid);

--pd response save
CREATE OR REPLACE FUNCTION pdaddorupdateresponse(in_userid bigint, in_testid bigint, in_testsectionid bigint, in_usertestsectionid bigint, in_taskid bigint, in_foilid bigint, in_response text, in_score numeric)
  RETURNS integer AS
$BODY$
  BEGIN
	update usertestresponse set userid = in_userId,testid = in_testid, testsectionid = in_testSectionId,foilid = in_foilid,
	      response = in_response, score = in_score, modifieddate = now(), activeflag = true
	    where usertestsectionid =in_userTestSectionId and taskvariantid = in_taskId;
      
	IF NOT FOUND THEN
		insert into usertestresponse (usertestsectionid, taskvariantid, userid,  testid, testsectionid, foilid, 
			response, score, createddate, modifieddate, activeflag)
			values (in_userTestSectionId, in_taskid, in_userId,in_testId, in_testSectionId, in_foilId, in_response,in_score,now(), now(),true);
	END IF;	
	RETURN 1;
  EXCEPTION WHEN OTHERS THEN
    RETURN 0;    
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;