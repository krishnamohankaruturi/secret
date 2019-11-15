
-- dml 316.sql

DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities WHERE authority = 'DATA_EXTRACTS_TEC_RECORDS' AND activeflag = TRUE) = 0) THEN
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_TEC_RECORDS does not exist. Inserting...';
		INSERT INTO authorities(
				id, authority, displayname, objecttype, createddate, createduser,
				activeflag, modifieddate, modifieduser)
			VALUES (nextval('authorities_id_seq'), 'DATA_EXTRACTS_TEC_RECORDS', 'Create Test Records Data Extract',
				'Reports-Data Extracts', now(), (select id from aartuser where username='cetesysadmin'),
				TRUE, now(), (Select id from aartuser where username='cetesysadmin'));
	ELSE
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_TEC_RECORDS exists. Skipping...';
	END IF;
END;
$BODY$;


-- 315.sql
update surveylabel set optional = false where globalpagenum = 1;


--Fix Existing Data : Script to update profilestatus column of student table with apppropriate value based on existing data
update student set profilestatus='CUSTOM' where id in (select studentid from studentprofileitemattributevalue spiav 
where (spiav.selectedvalue like '%,%' or lower(spiav.selectedvalue) 
in ('true', 'setting', 'dictated', 'communication', 'responses', 'assessment', 'translations', 'accommodation')));

