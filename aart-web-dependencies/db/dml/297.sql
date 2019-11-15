
--empty
DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities where authority='UPLOAD_WEBSERVICE' and activeflag=true) = 0) THEN
		RAISE NOTICE  '%', 'Permission with UPLOAD_WEBSERVICE not exists. Inserting';
		INSERT INTO authorities(
			    id, authority, displayname, objecttype, createddate, createduser, 
			    activeflag, modifieddate, modifieduser)
		    VALUES (nextval('authorities_id_seq'),'UPLOAD_WEBSERVICE','Upload Web Service',
				'Administrative-Web Service', now(),(Select id from aartuser where username='cetesysadmin'), 
				TRUE,now(),(Select id from aartuser where username='cetesysadmin'));
	ELSE 
		RAISE NOTICE  '%', 'Permission with UPLOAD_WEBSERVICE exists. Skipping';
	END IF;
END;
$BODY$;