--229.sql;
	
CREATE OR REPLACE FUNCTION addOrUpdateResponse(in_studentid bigint, in_testid bigint, in_testSectionId bigint, in_studentTestId bigint, in_studentTestSectionId bigint, in_taskId bigint, in_foilid bigint, in_response text, in_score numeric(6,3))
 RETURNS integer AS
$BODY$
  DECLARE
	AFFECTEDROWS integer;
  BEGIN
	WITH a AS (UPDATE studentsresponses SET studentid = in_studentid, testid = in_testid, testsectionid = in_testSectionId,
			 studentstestsid = in_studentTestId, foilid = in_foilid, response = in_response, score = in_score, modifieddate=now()
			 WHERE studentstestsectionsid = in_studentTestSectionId and taskvariantid = in_taskId RETURNING 1)
			 SELECT count(*) INTO AFFECTEDROWS FROM a;
	IF AFFECTEDROWS = 0 THEN
		INSERT INTO studentsresponses(studentid, testid, testsectionid, studentstestsid, 
			studentstestsectionsid, taskvariantid, foilid, response, score) 
			VALUES (in_studentid, in_testid, in_testSectionId, in_studentTestId, in_studentTestSectionId,
				in_taskId, in_foilid, in_response, in_score);
	END IF;	
	RETURN 1;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
--CREATE INDEX idx_studentsresponses_taskvariantid ON studentsresponses USING btree (taskvariantid);

