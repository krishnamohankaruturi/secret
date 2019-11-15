
--dml/225.sql;
UPDATE groups SET organizationtypeid=(select id from organizationtype where typecode='BLDG') 
	WHERE groupname in ('Building Principal');

DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities where authority='REACTIVATE_HS_STDNT_TESTSESSION' and activeflag=true) = 0) THEN
		RAISE NOTICE  '%', 'Permission with REACTIVATE_HS_STDNT_TESTSESSION not exists. Inserting';
		INSERT INTO authorities(
			    id, authority, displayname, objecttype, createddate, createduser, 
			    activeflag, modifieddate, modifieduser)
		    VALUES (nextval('authorities_id_seq'),'REACTIVATE_HS_STDNT_TESTSESSION','Reactivate High Stakes Student Test Sessions',
				'Test Management-Test Session', now(),(Select id from aartuser where username='cetesysadmin'), 
				TRUE,now(),(Select id from aartuser where username='cetesysadmin'));
	ELSE 
		RAISE NOTICE  '%', 'Permission with REACTIVATE_HS_STDNT_TESTSESSION exists. Skipping';
	END IF;
END;
$BODY$;

DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities where authority='TEST_COORD_TESTSESSION_MONITOR' and activeflag=true) = 0) THEN
		RAISE NOTICE  '%', 'Permission with TEST_COORD_TESTSESSION_MONITOR not exists. Inserting';
		INSERT INTO authorities(
			    id, authority, displayname, objecttype, createddate, createduser, 
			    activeflag, modifieddate, modifieduser)
		    VALUES (nextval('authorities_id_seq'),'TEST_COORD_TESTSESSION_MONITOR','Monitor Testcoordination Test Session',
				'Test Management-Test Session', now(),(Select id from aartuser where username='cetesysadmin'), 
				TRUE,now(),(Select id from aartuser where username='cetesysadmin'));
	ELSE 
		RAISE NOTICE  '%', 'Permission with TEST_COORD_TESTSESSION_MONITOR exists. Skipping';
	END IF;
END;
$BODY$;
