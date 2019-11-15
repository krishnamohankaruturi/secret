--ddl/519.sql

ALTER TABLE survey DROP CONSTRAINT IF EXISTS uk_student;
ALTER TABLE aartuser ADD sourcetype character varying(20);

CREATE OR REPLACE FUNCTION updateandgetqueuedmodulereport(bigint, bigint)
  RETURNS SETOF modulereport AS
$BODY$ 
DECLARE 
	p_queuestatusid ALIAS FOR $1;
	p_inprogressstatusid ALIAS FOR $2;
	report_id bigint;
BEGIN
	select id into report_id from modulereport where statusid = p_queuestatusid and activeflag is true order by modifieddate asc limit 1;
	IF report_id IS NOT NULL THEN
		update modulereport set statusid = p_inprogressstatusid, modifieddate = now() where id = report_id;
	END IF;
	RETURN QUERY SELECT * FROM modulereport where id = report_id;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
-- Script from TDE team
CREATE OR REPLACE FUNCTION clearresponseparameters(in_testid bigint, in_studenttestid bigint, in_studenttestsectionid bigint, in_taskid bigint)
  RETURNS integer AS
$BODY$
  BEGIN
UPDATE studentsresponseparameters SET avalue = null, bvalue = null, b2value = null, formulacode = null, score = null, modifieddate=now() 
WHERE studentstestsectionsid = in_studentTestSectionId and taskvariantid = in_taskId;
RETURN 1;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
