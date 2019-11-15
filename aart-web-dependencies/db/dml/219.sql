--dml/219.sql

DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities where authority='REACTIVATE_STUDENT_TESTSESSION' and activeflag=true) = 0) THEN
		RAISE NOTICE  '%', 'Permission with REACTIVATE_STUDENT_TESTSESSION not exists. Inserting';
		INSERT INTO authorities(
			    id, authority, displayname, objecttype, createddate, createduser, 
			    activeflag, modifieddate, modifieduser)
		    VALUES (nextval('authorities_id_seq'),'REACTIVATE_STUDENT_TESTSESSION','Reactivate Student Test Sessions',
				'Test Management-Test Session', now(),(Select id from aartuser where username='cetesysadmin'), 
				TRUE,now(),(Select id from aartuser where username='cetesysadmin'));
	ELSE 
		RAISE NOTICE  '%', 'Permission with REACTIVATE_STUDENT_TESTSESSION exists. Skipping';
	END IF;
END;
$BODY$;
	