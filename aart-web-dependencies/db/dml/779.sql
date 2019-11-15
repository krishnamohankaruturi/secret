--Update complexitybands for ISMART with missing content areas
DO
$BODY$
DECLARE
    ismartContentAreaId BIGINT := (SELECT id FROM contentarea WHERE abbreviatedname = 'IS-Sci');
   	iSmartAssessmentProgramId BIGINT := (select id from assessmentprogram where activeflag is true and abbreviatedname ='I-SMART');

BEGIN
    IF EXISTS (
        SELECT 1
        FROM complexityband
        WHERE contentareaid IS NULL
			AND assessmentprogramid = iSmartAssessmentProgramId
    ) THEN
        RAISE NOTICE 'Found complexity bands for ISMART program with null content areas.';
		
        UPDATE complexityband
		SET
			contentareaid = ismartContentAreaId
		WHERE
			contentareaid = NULL
			AND assessmentprogramid = iSmartAssessmentProgramId;

        RAISE NOTICE 'Complete.';
    ELSE
        RAISE NOTICE 'Did not find complexity bands with null content area. skipping';
    END IF;
END;
$BODY$;
