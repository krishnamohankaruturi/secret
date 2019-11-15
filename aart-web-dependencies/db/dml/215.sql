--dml/215.sql

DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities where authority='HIGH_STAKES_TESTSESSION_VIEW' and activeflag=true) = 0) THEN
		RAISE NOTICE  '%', 'Permission with HIGH_STAKES_TESTSESSION_VIEW not exists. Inserting';
		INSERT INTO authorities(
			    id, authority, displayname, objecttype, createddate, createduser, 
			    activeflag, modifieddate, modifieduser)
		    VALUES (nextval('authorities_id_seq'),'HIGH_STAKES_TESTSESSION_VIEW','View High Stakes Test Sessions',
				'Test Management-High Stakes', now(),(Select id from aartuser where username='cetesysadmin'), 
				TRUE,now(),(Select id from aartuser where username='cetesysadmin'));
	ELSE 
		RAISE NOTICE  '%', 'Permission with HIGH_STAKES_TESTSESSION_VIEW exists. Skipping';
	END IF;
END;
$BODY$;
	