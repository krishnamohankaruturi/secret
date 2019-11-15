-- dml/583.sql

-- Creating new permission for student user name and password data extract
DO
$BODY$
BEGIN
	
	IF ((SELECT count(id) FROM authorities WHERE authority = 'DATA_EXTRACTS_STU_UNAME_PASSWORD' AND activeflag = TRUE) = 0) THEN
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_STU_UNAME_PASSWORD does not exist. Inserting...';
		INSERT INTO authorities(
				authority, displayname, objecttype, createddate, createduser,
				activeflag, modifieddate, modifieduser)
			VALUES ('DATA_EXTRACTS_STU_UNAME_PASSWORD', 'Create Student Login details Data Extract',
				'Reports-Data Extracts', now(), (select id from aartuser where username='cetesysadmin'),
				TRUE, now(), (Select id from aartuser where username='cetesysadmin'));
	ELSE
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_STU_UNAME_PASSWORD exists. Skipping...';
	END IF;
	
END;
$BODY$;
