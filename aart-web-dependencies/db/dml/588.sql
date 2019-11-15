-- dml/.sql

DO
$BODY$ 
DECLARE TTARECORD RECORD;
BEGIN

	IF NOT EXISTS (
		SELECT id
		FROM category
		WHERE categorycode = 'assessmentprogramunenrolled'
		AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'STUDENT_TEST_STATUS')
	) THEN
		INSERT INTO category (
			categoryname,
			categorycode,
			categorydescription,
			categorytypeid,
			externalid,
			originationcode,
			createddate,
			createduser,
			activeflag,
			modifieddate,
			modifieduser
		) VALUES (
			'Assessment Program Unenrolled',
			'assessmentprogramunenrolled',
			'Assessment Program Unenrolled',
			(SELECT id FROM categorytype where typecode = 'STUDENT_TEST_STATUS'),
			NULL,
			'AART_ORIG',
			NOW(),
			(SELECT id FROM aartuser where username='cetesysadmin'),
			TRUE,
			NOW(),
			(SELECT id FROM aartuser where username='cetesysadmin')
		);
	ELSE
		RAISE NOTICE 'Found assessmentprogramunenrolled test status, skipping INSERT';
	END IF;
	
	IF NOT EXISTS (
		SELECT id
		FROM category
		WHERE categorycode = 'assessmentprogramunenrolled'
		AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'STUDENT_TESTSECTION_STATUS')
	) THEN
		INSERT INTO category (
			categoryname,
			categorycode,
			categorydescription,
			categorytypeid,
			externalid,
			originationcode,
			createddate,
			createduser,
			activeflag,
			modifieddate,
			modifieduser
		) VALUES (
			'Assessment Program Unenrolled',
			'assessmentprogramunenrolled',
			'Assessment Program Unenrolled',
			(SELECT id FROM categorytype where typecode = 'STUDENT_TESTSECTION_STATUS'),
			NULL,
			'AART_ORIG',
			NOW(),
			(SELECT id FROM aartuser where username='cetesysadmin'),
			TRUE,
			NOW(),
			(SELECT id FROM aartuser where username='cetesysadmin')
		);
	ELSE
		RAISE NOTICE 'Found assessmentprogramunenrolled test section status, skipping INSERT';
	END IF;
	
END;
$BODY$;


update authorities set displayname='Manage Interim Assessments' where authority='PERM_INTERIM_ACCESS';

INSERT INTO testenrollmentmethod(
              assessmentprogramid, methodcode, methodname) VALUES( (select id from assessmentprogram where programname ilike 'KAP' 
limit 1),'INTERIM','INTERIM TEST WINDOW');

INSERT INTO operationaltestwindow(windowname, effectivedate, expirydate,assessmentprogramid,
             testenrollmentflag,
testenrollmentmethodid,createduser,modifieduser
              )
     VALUES ('INTERIM TEST WINDOW',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + '100 years'::interval,(select id from assessmentprogram where programname ilike 'KAP' limit 1),
             true, (select id from testenrollmentmethod where methodname ilike 'Interim Test Window')
             ,(select id from aartuser where username ilike 'cetesysadmin'),(select id from aartuser where username ilike 'cetesysadmin'));
