--DE18510 I-SMART research survey changes

DO
$do$
BEGIN
IF EXISTS (SELECT 1 from assessmentprogram where abbreviatedname='I-SMART') THEN
	insert into testenrollmentmethod (assessmentprogramid, methodcode, methodname, methodtype) 
	values ((select id from assessmentprogram where abbreviatedname='I-SMART'), 'RESEARCHSURVEY','Research Survey','TEM')
	ON CONFLICT DO NOTHING;
END IF;
END
$do$;
