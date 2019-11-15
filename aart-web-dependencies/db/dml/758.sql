--dml/758.sql F842 PLTW Batch Auto Enrollment

DO $BODY$ 
begin
		IF EXISTS (SELECT 1 FROM assessmentprogram WHERE abbreviatedname ='PLTW' and activeflag is true) THEN
		
			INSERT INTO testenrollmentmethod(assessmentprogramid, methodcode,methodname,methodtype)
			values((select id from assessmentprogram where abbreviatedname='PLTW' and activeflag is true),'MLTSTG', 'Multi-stage','TEM');
	
			INSERT INTO batchjobschedule (jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
			SELECT 'PLTW Batch auto job', 'pltwAutoJobScheduler', 'run', '0 0 22 * * ?', false, 'localhost'
			WHERE NOT EXISTS (
			    SELECT 1 FROM batchjobschedule WHERE jobrefname = 'pltwAutoJobScheduler');
		
			IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='missing_comrehensiverace') THEN
				insert into appconfiguration(attrcode,attrtype,attrname,attrvalue,assessmentprogramid,createduser,modifieduser)
				values('missing_comrehensiverace', 'pltw_batch_auto_error_message', 'comprehensiverace', 'Missing comprehensive race', 
				   (select id from assessmentprogram where abbreviatedname ='PLTW' and activeflag is true), 12, 12);
			END IF;
			
			IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='missing_hispanic_ethnicity') THEN
				insert into appconfiguration(attrcode,attrtype,attrname,attrvalue,assessmentprogramid,createduser,modifieduser)
				values('missing_hispanic_ethnicity', 'pltw_batch_auto_error_message', 'ethnicity', 'Missing hispanic ethnicity', 
					   (select id from assessmentprogram where abbreviatedname ='PLTW' and activeflag is true), 12, 12);
			END IF;
		
			IF NOT EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='missing_race_ethnicity') THEN
				insert into appconfiguration(attrcode,attrtype,attrname,attrvalue,assessmentprogramid,createduser,modifieduser)
				values('missing_race_ethnicity', 'pltw_batch_auto_error_message', 'race_and_ethnicity', 'Missing comprehensive race and hispanic ethnicity', 
				   (select id from assessmentprogram where abbreviatedname ='PLTW' and activeflag is true), 12, 12);
			END IF;
		END IF;
end; $BODY$;


