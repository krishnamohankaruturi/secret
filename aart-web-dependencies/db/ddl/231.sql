--231.sql;

CREATE OR REPLACE FUNCTION addorupdateresponse(in_studentid bigint, in_testid bigint, in_testsectionid bigint, in_studenttestid bigint, in_studenttestsectionid bigint, in_taskid bigint, in_foilid bigint, in_response text, in_score numeric)
  RETURNS integer AS
$BODY$
  BEGIN
	UPDATE studentsresponses SET studentid = in_studentid, testid = in_testid, testsectionid = in_testSectionId,
			 studentstestsid = in_studentTestId, foilid = in_foilid, response = in_response, score = in_score, modifieddate=now()
			 WHERE studentstestsectionsid = in_studentTestSectionId and taskvariantid = in_taskId;
	IF NOT FOUND THEN
		--RAISE NOTICE 'inserting';
		INSERT INTO studentsresponses(studentid, testid, testsectionid, studentstestsid, 
			studentstestsectionsid, taskvariantid, foilid, response, score) 
			VALUES (in_studentid, in_testid, in_testSectionId, in_studentTestId, in_studentTestSectionId,
				in_taskId, in_foilid, in_response, in_score);
	END IF;	
	RETURN 1;
  EXCEPTION WHEN OTHERS THEN
    RETURN 0;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  