-- ddl script for ddl/772.sql

CREATE OR REPLACE FUNCTION public.getstudentassessmentprogram(
    sid bigint,
    schoolyear bigint)
  RETURNS bigint AS
$BODY$

	DECLARE
		finalid BIGINT;
		apnames text[];
	BEGIN
		SELECT array_agg(ap.abbreviatedname) into apnames from assessmentprogram ap
			inner join studentassessmentprogram sap on ap.id = sap.assessmentprogramid
			where sap.studentid = sid
			and ap.activeflag = true
			and sap.activeflag = true;
		
			IF(select 'DLM' = ANY(apnames)) THEN
				finalid = (select id from assessmentprogram where abbreviatedname = 'DLM' and activeflag is true);	
			
			ELSIF(select 'KAP' = ANY(apnames)) THEN
				finalid = (select id from assessmentprogram where abbreviatedname = 'KAP' and activeflag is true);
			
			ELSIF(select 'AMP' = ANY(apnames)) THEN
				finalid = (select id from assessmentprogram where abbreviatedname = 'AMP' and activeflag is true);
				
			ELSIF(select 'CPASS' = ANY(apnames)) THEN
				finalid = (select id from assessmentprogram where abbreviatedname = 'CPASS' and activeflag is true);
				
			ELSIF(select 'I-SMART' = ANY(apnames)) THEN
				finalid = (select id from assessmentprogram where abbreviatedname = 'I-SMART' and activeflag is true);
				
			ELSIF(select 'I-SMART2' = ANY(apnames)) THEN
				finalid = (select id from assessmentprogram where abbreviatedname = 'I-SMART2' and activeflag is true);
			ELSIF(select 'PLTW' = ANY(apnames)) THEN
				finalid = (select id from assessmentprogram where abbreviatedname = 'PLTW' and activeflag is true);	

			END IF;
			
		-- if we didn't find one, then we can infer one from the student's enrollment
		IF (finalid IS NULL) THEN
			finalid = (select assessmentprogramid from orgassessmentprogram oap join assessmentprogram ap on ap.id = oap.assessmentprogramid 
			where oap.organizationid =(select id from organization_parent((select attendanceschoolid from enrollment e where e.currentschoolyear=schoolyear and e.studentid=sid limit 1)) op where op.organizationtypeid=2)
			and ap.abbreviatedname in ('KAP', 'AMP' , 'CPASS' , 'ARMM', 'DLM','I-SMART','I-SMART2','PLTW') limit 1);
		END IF;
		
		RETURN finalid;
		END;

	$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  