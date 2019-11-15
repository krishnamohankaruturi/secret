--ddl/752.sql

CREATE OR REPLACE FUNCTION public.getstudentassessmentprogram(
		sid bigint,
		schoolyear bigint)
	    RETURNS bigint
	    LANGUAGE 'plpgsql'

	    COST 100
	    VOLATILE 
	AS $BODY$

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

			END IF;
			
		-- if we didn't find one, then we can infer one from the student's enrollment
		IF (finalid IS NULL) THEN
			finalid = (select assessmentprogramid from orgassessmentprogram oap join assessmentprogram ap on ap.id = oap.assessmentprogramid 
			where oap.organizationid =(select id from organization_parent((select attendanceschoolid from enrollment e where e.currentschoolyear=schoolyear and e.studentid=sid limit 1)) op where op.organizationtypeid=2)
			and ap.abbreviatedname in ('KAP', 'AMP' , 'CPASS' , 'ARMM', 'DLM','I-SMART','I-SMART2') limit 1);
		END IF;
		
		RETURN finalid;
		END;

	$BODY$;

--for TDE F785
CREATE TABLE if not exists public.s3filenames (
	id bigserial NOT NULL,
	filename text UNIQUE,
	studentid bigint,
	studentstestsectionsid bigint,
	filetype text,
	createddate timestamp with time zone DEFAULT now(),
        modifieddate timestamp with time zone DEFAULT now(),
	CONSTRAINT s3filenames_pkey PRIMARY KEY (id),	
	CONSTRAINT fk_student_id FOREIGN KEY (studentid)
      REFERENCES public.student (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,      
      CONSTRAINT fk_students_test_sections_id FOREIGN KEY (studentstestsectionsid)
      REFERENCES public.studentstestsections (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
	 
) ;
CREATE INDEX if not exists idx_s3filenames_studentid ON s3filenames (studentid);
CREATE INDEX if not exists idx_s3filenames_studentstestsectionsid ON s3filenames (studentstestsectionsid);