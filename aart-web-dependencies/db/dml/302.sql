--empty
-- permissions for the data extracts

DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities WHERE authority = 'DATA_EXTRACTS_CURRENT_ENROLLMENT' AND activeflag = TRUE) = 0) THEN
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_CURRENT_ENROLLMENT does not exist. Inserting...';
		INSERT INTO authorities(
				id, authority, displayname, objecttype, createddate, createduser,
				activeflag, modifieddate, modifieduser)
			VALUES (nextval('authorities_id_seq'), 'DATA_EXTRACTS_CURRENT_ENROLLMENT', 'Create Enrollment Data Extract',
				'Reports-Data Extracts', now(), (select id from aartuser where username='cetesysadmin'),
				TRUE, now(), (Select id from aartuser where username='cetesysadmin'));
	ELSE
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_CURRENT_ENROLLMENT exists. Skipping...';
	END IF;

	IF ((SELECT count(id) FROM authorities WHERE authority = 'DATA_EXTRACTS_PNP' AND activeflag = TRUE) = 0) THEN
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_PNP does not exist. Inserting...';
		INSERT INTO authorities(
				id, authority, displayname, objecttype, createddate, createduser,
				activeflag, modifieddate, modifieduser)
			VALUES (nextval('authorities_id_seq'), 'DATA_EXTRACTS_PNP', 'Create Student PNP Data Extract',
				'Reports-Data Extracts', now(), (select id from aartuser where username='cetesysadmin'),
				TRUE, now(), (Select id from aartuser where username='cetesysadmin'));
	ELSE
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_PNP exists. Skipping...';
	END IF;

	IF ((SELECT count(id) FROM authorities WHERE authority = 'DATA_EXTRACTS_ROSTER' AND activeflag = TRUE) = 0) THEN
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_ROSTER does not exist. Inserting...';
		INSERT INTO authorities(
				id, authority, displayname, objecttype, createddate, createduser,
				activeflag, modifieddate, modifieduser)
			VALUES (nextval('authorities_id_seq'), 'DATA_EXTRACTS_ROSTER', 'Create Roster Data Extract',
				'Reports-Data Extracts', now(), (select id from aartuser where username='cetesysadmin'),
				TRUE, now(), (Select id from aartuser where username='cetesysadmin'));
	ELSE
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_ROSTER exists. Skipping...';
	END IF;

	IF ((SELECT count(id) FROM authorities WHERE authority = 'DATA_EXTRACTS_USERS' AND activeflag = TRUE) = 0) THEN
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_USERS does not exist. Inserting...';
		INSERT INTO authorities(
				id, authority, displayname, objecttype, createddate, createduser,
				activeflag, modifieddate, modifieduser)
			VALUES (nextval('authorities_id_seq'), 'DATA_EXTRACTS_USERS', 'Create User Data Extract',
				'Reports-Data Extracts', now(), (select id from aartuser where username='cetesysadmin'),
				TRUE, now(), (Select id from aartuser where username='cetesysadmin'));
	ELSE
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_USERS exists. Skipping...';
	END IF;
END;
$BODY$;