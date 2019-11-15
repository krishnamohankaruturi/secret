--adaptive test changes
ALTER TABLE studentstestsections DROP CONSTRAINT ukey_studentstestsections;

ALTER TABLE studentstestsections ADD COLUMN testpartid bigint;
ALTER TABLE studentstestsections
  ADD CONSTRAINT fk_studentstestsections_testpartid FOREIGN KEY (testpartid) REFERENCES testpart (id)
   ON UPDATE NO ACTION ON DELETE NO ACTION;
   
CREATE INDEX idx_studentstestsections_studentstestid_testpartid
  ON studentstestsections(studentstestid,testpartid);
  
CREATE INDEX idx_studentstestsections_studentstestid_testsectionid
  ON studentstestsections(studentstestid,testsectionid);
  
CREATE OR REPLACE FUNCTION insert_adaptive_testsection(_testpartid bigint, _studentstestid bigint)
  RETURNS integer AS
$BODY$
BEGIN
	IF (SELECT id from studentstestsections WHERE testpartid=_testpartid 
		AND studentstestid=_studentstestid AND activeflag=true) IS NULL THEN
		INSERT INTO studentstestsections(studentstestid, testpartid, statusid) 
			VALUES (_studentstestid, _testpartid, (SELECT category.id FROM public.category 
				JOIN public.categorytype ON category.categorytypeid = categorytype.id
				WHERE categorytype.typecode = 'STUDENT_TESTSECTION_STATUS' 
				AND category.categorycode = 'unused'));
	END IF; 
	RETURN 1;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  