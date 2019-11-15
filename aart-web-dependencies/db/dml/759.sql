--View Orphaned API Error Messages

DO
$BODY$
declare var_id bigint;
BEGIN

	select min(sortorder)+51 from  authorities where objecttype ='Administrative-Dash Board' into  var_id;
	
	IF ((SELECT count(id) FROM authorities where authority='VIEW_ORPHANED_API_ERRORS' and activeflag=true) = 0) THEN
		RAISE NOTICE  '%', 'Permission with VIEW_ORPHANED_API_ERRORS not exists. Inserting';
		INSERT INTO authorities(
			    id, authority, displayname, objecttype, createddate, createduser, 
			    activeflag, modifieddate, modifieduser,tabname,groupingname,level,sortorder)
		    VALUES (nextval('authorities_id_seq'),'VIEW_ORPHANED_API_ERRORS','View Orphaned API Errors ',
				'Administrative-Dash Board', now(),(Select id from aartuser where username='cetesysadmin'), 
				TRUE,now(),(Select id from aartuser where username='cetesysadmin'),'Other','Dashboard',3,var_id);
	ELSE 
		RAISE NOTICE  '%', 'Permission with VIEW_ORPHANED_API_ERRORS exists. Skipping';
	END IF;
END;
$BODY$;


-- API Errors
DO
$BODY$
declare var_id bigint;
BEGIN
 select min(sortorder)+50 from  authorities where objecttype ='Administrative-Dash Board' into  var_id;

	IF ((SELECT count(id) FROM authorities where authority='VIEW_API_ERRORS' and activeflag=true) = 0) THEN
		RAISE NOTICE  '%', 'authorities with VIEW_API_ERRORS not exists. Inserting';
		INSERT INTO authorities(
			    id, authority, displayname, objecttype, createddate, createduser, 
			    activeflag, modifieddate, modifieduser,tabname,groupingname,level,sortorder)
		    VALUES (nextval('authorities_id_seq'),'VIEW_API_ERRORS','View API Errors ',
				'Administrative-Dash Board', now(),(Select id from aartuser where username='cetesysadmin'), 
				TRUE,now(),(Select id from aartuser where username='cetesysadmin'),'Other','Dashboard',2,var_id);
	ELSE 
		RAISE NOTICE  '%', 'authorities with VIEW_API_ERRORS exists. Skipping';
	END IF;
END;
$BODY$;

