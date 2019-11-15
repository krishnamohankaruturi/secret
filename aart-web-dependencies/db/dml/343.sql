--343.sql
--insert permission for summary PNP extract
DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities WHERE authority = 'DATA_EXTRACTS_PNP_SUMMARY' AND activeflag = TRUE) = 0) THEN
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_PNP_SUMMARY does not exist. Inserting...';
		INSERT INTO authorities(
				id, authority, displayname, objecttype, createddate, createduser,
				activeflag, modifieddate, modifieduser)
			VALUES (nextval('authorities_id_seq'), 'DATA_EXTRACTS_PNP_SUMMARY', 'Create Summary PNP Data Extract',
				'Reports-Data Extracts', now(), (select id from aartuser where username='cetesysadmin'),
				TRUE, now(), (Select id from aartuser where username='cetesysadmin'));
	ELSE
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_PNP_SUMMARY exists. Skipping...';
	END IF;
END;
$BODY$;