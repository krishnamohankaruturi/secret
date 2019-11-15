-- 347.sql
-- US15696 : Data extract - KSDE Test records for System Operations
-- US15698 : Data extract - KSDE STCO records for System Operations
-- permissions for the data extracts

DO
$BODY$
BEGIN
	
	IF ((SELECT count(id) FROM authorities WHERE authority = 'DATA_EXTRACTS_KSDE_TEST' AND activeflag = TRUE) = 0) THEN
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_KSDE_TEST does not exist. Inserting...';
		INSERT INTO authorities(
				id, authority, displayname, objecttype, createddate, createduser,
				activeflag, modifieddate, modifieduser)
			VALUES (nextval('authorities_id_seq'), 'DATA_EXTRACTS_KSDE_TEST', 'Create KSDE Test Records Data Extract',
				'Reports-Data Extracts', now(), (select id from aartuser where username='cetesysadmin'),
				TRUE, now(), (Select id from aartuser where username='cetesysadmin'));
	ELSE
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_KSDE_TEST exists. Skipping...';
	END IF;
	
	IF ((SELECT count(id) FROM authorities WHERE authority = 'DATA_EXTRACTS_KSDE_STCO' AND activeflag = TRUE) = 0) THEN
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_KSDE_STCO does not exist. Inserting...';
		INSERT INTO authorities(
				id, authority, displayname, objecttype, createddate, createduser,
				activeflag, modifieddate, modifieduser)
			VALUES (nextval('authorities_id_seq'), 'DATA_EXTRACTS_KSDE_STCO', 'Create KSDE STCO Records Data Extract',
				'Reports-Data Extracts', now(), (select id from aartuser where username='cetesysadmin'),
				TRUE, now(), (Select id from aartuser where username='cetesysadmin'));
	ELSE
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_KSDE_STCO exists. Skipping...';
	END IF;
END;
$BODY$;