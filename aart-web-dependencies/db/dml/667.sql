--F563

DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities where authority='END_STUDENT_TESTSESSION' and activeflag=true) = 0) THEN
		RAISE NOTICE  '%', 'Permission with END_STUDENT_TESTSESSION does not exist. Inserting';
INSERT INTO authorities(
	    id, authority, displayname, objecttype, createddate, createduser, 
	    activeflag, modifieddate, modifieduser)
    VALUES (nextval('authorities_id_seq'),'END_STUDENT_TESTSESSION','End Student Test Session',
		'Test Management-Test Session', now(),(Select id from aartuser where username='cetesysadmin'), 
		TRUE,now(),(Select id from aartuser where username='cetesysadmin'));
	ELSE 
		RAISE NOTICE  '%', 'Permission with END_STUDENT_TESTSESSION exists. Skipping';
	END IF;
END;
$BODY$;

DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities where authority='END_HS_STDNT_TESTSESSION' and activeflag=true) = 0) THEN
		RAISE NOTICE  '%', 'Permission with END_HS_STDNT_TESTSESSION does not exist. Inserting';
		INSERT INTO authorities(
			    id, authority, displayname, objecttype, createddate, createduser, 
			    activeflag, modifieddate, modifieduser)
		    VALUES (nextval('authorities_id_seq'),'END_HS_STDNT_TESTSESSION','End High Stakes Student Test Sessions',
				'Test Management-Test Session', now(),(Select id from aartuser where username='cetesysadmin'), 
				TRUE,now(),(Select id from aartuser where username='cetesysadmin'));
	ELSE 
		RAISE NOTICE  '%', 'Permission with END_HS_STDNT_TESTSESSION exists. Skipping';
	END IF;
END;
$BODY$;