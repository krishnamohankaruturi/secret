
--empty
insert into category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, modifieddate, createduser, modifieduser)
values ('Unreleased', 'UNRELEASED', 'The module is not released for use.',(select id from categorytype where typecode='MODULE_STATUS'),'AART_ORIG_CODE', now(), now(), (select id from aartuser where username='cetesysadmin'), (select id from aartuser where username='cetesysadmin'));


DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities where authority='STATE_CEU' and activeflag=true) = 0) THEN
		RAISE NOTICE  '%', 'Permission with STATE_CEU not exists. Inserting';
		INSERT INTO authorities(
			    id, authority, displayname, objecttype, createddate, createduser, 
			    activeflag, modifieddate, modifieduser)
		    VALUES (nextval('authorities_id_seq'),'STATE_CEU','State CEU',
				'Professional Development-Professional Development', now(),(Select id from aartuser where username='cetesysadmin'), 
				TRUE,now(),(Select id from aartuser where username='cetesysadmin'));
	ELSE 
		RAISE NOTICE  '%', 'Permission with STATE_CEU exists. Skipping';
	END IF;
END;
$BODY$;	