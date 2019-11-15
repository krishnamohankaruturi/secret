
-- 311.sql

CREATE INDEX idx_organizationrelation_parentid
  ON organizationrelation USING btree (parentorganizationid);
  
--pd response save
CREATE OR REPLACE FUNCTION pdaddorupdateresponse(in_userid bigint, in_testid bigint, in_testsectionid bigint, in_userTestSectionId bigint, in_taskid bigint, in_foilid bigint, in_response text, in_score numeric)
  RETURNS integer AS
$BODY$
  BEGIN
	update usertestresponse set userid = in_userId,testid = in_testid, testsectionid = in_testSectionId,foilid = in_foilid,
	      response = in_response, score = in_score, modifieddate = now(), activeflag = true
	    where usertestsectionid =in_userTestSectionId and taskvariantid = in_taskId;
      
	IF NOT FOUND THEN
		insert into usertestresponse (usertestsectionid, taskvariantid, userid,  testid, testsectionid, foilid, 
			response, score, createddate, modifieddate, activeflag)
			values (in_userTestSectionId, in_taskId, in_userId,in_testId, in_testSectionId, in_foilId, in_response,in_score,now(), now(),true);
	END IF;	
	RETURN 1;
  EXCEPTION WHEN OTHERS THEN
    RETURN 0;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;