
--empty
DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities where authority='UNPUB_MODULE' and activeflag=true) = 0) THEN
		RAISE NOTICE  '%', 'Permission with UNPUB_MODULE not exists. Inserting';
		INSERT INTO authorities(
			    id, authority, displayname, objecttype, createddate, createduser, 
			    activeflag, modifieddate, modifieduser)
		    VALUES (nextval('authorities_id_seq'),'UNPUB_MODULE','Unpublish Module',
				'Professional Development-Professional Development', now(),(Select id from aartuser where username='cetesysadmin'), 
				TRUE,now(),(Select id from aartuser where username='cetesysadmin'));
	ELSE 
		RAISE NOTICE  '%', 'Permission with UNPUB_MODULE exists. Skipping';
	END IF;
END;
$BODY$;	

DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities where authority='UNREL_MODULE' and activeflag=true) = 0) THEN
		RAISE NOTICE  '%', 'Permission with UNREL_MODULE not exists. Inserting';
		INSERT INTO authorities(
			    id, authority, displayname, objecttype, createddate, createduser, 
			    activeflag, modifieddate, modifieduser)
		    VALUES (nextval('authorities_id_seq'),'UNREL_MODULE','Unrelease Module',
				'Professional Development-Professional Development', now(),(Select id from aartuser where username='cetesysadmin'), 
				TRUE,now(),(Select id from aartuser where username='cetesysadmin'));
	ELSE 
		RAISE NOTICE  '%', 'Permission with UNREL_MODULE exists. Skipping';
	END IF;
END;
$BODY$;	