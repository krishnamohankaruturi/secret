-- 382.sql
-- US15915 : Test coordination extend window for test session
-- insert permission for test session window extension
DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities WHERE authority = 'EXTEND_TEST_SESSION_WINDOW' AND activeflag = TRUE) = 0) THEN
		RAISE NOTICE '%', 'Permission EXTEND_TEST_SESSION_WINDOW does not exist. Inserting...';
		INSERT INTO authorities(
				id, authority, displayname, objecttype, createddate, createduser,
				activeflag, modifieddate, modifieduser)
			VALUES (nextval('authorities_id_seq'), 'EXTEND_TEST_SESSION_WINDOW', 'Extend Test Session Window',
				'Test Management-Test Session', now(), (select id from aartuser where username='cetesysadmin'),
				TRUE, now(), (Select id from aartuser where username='cetesysadmin'));
	ELSE
		RAISE NOTICE '%', 'Permission EXTEND_TEST_SESSION_WINDOW exists. Skipping...';
	END IF;
END;
$BODY$;