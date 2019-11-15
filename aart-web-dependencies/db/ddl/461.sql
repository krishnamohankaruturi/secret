 -- 461.sql
ALTER TABLE student DROP CONSTRAINT IF EXISTS source_student_fk;
ALTER TABLE student ALTER COLUMN source TYPE character varying(20);

DROP FUNCTION IF EXISTS getstudentassessmentprogram(bigint, bigint);

CREATE OR REPLACE FUNCTION getstudentassessmentprogram(sid bigint, schoolyear bigint)
  RETURNS bigint AS
$BODY$
DECLARE
	finalid BIGINT;
	aprow RECORD;
BEGIN
	FOR aprow IN (
		select ap.id, ap.abbreviatedname
		from assessmentprogram ap
		inner join studentassessmentprogram sap on ap.id = sap.assessmentprogramid
		where sap.studentid = sid
		and ap.activeflag = true
		and sap.activeflag = true
	)
	LOOP
		IF (aprow.abbreviatedname = 'DLM') THEN -- look for DLM first
			finalid = (select aprow.id);
			EXIT;
		ELSE   
			finalid = (select aprow.id);
		END IF;
	END LOOP;
	
	-- if we didn't find one, then we can infer one from the student's enrollment
	IF (finalid IS NULL) THEN
		finalid = (select assessmentprogramid from orgassessmentprogram oap join assessmentprogram ap on ap.id = oap.assessmentprogramid 
		where oap.organizationid =(select id from organization_parent((select attendanceschoolid from enrollment e where e.currentschoolyear=schoolyear and e.studentid=sid limit 1)) op where op.organizationtypeid=2)
		and ap.abbreviatedname in ('KAP', 'AMP' , 'CPASS' , 'ARMM') limit 1);
	END IF;
	
	RETURN finalid;
	END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
