-- Test Assignment Errors
DO
$BODY$
declare var_id bigint;
BEGIN
 select max(sortorder)+250 from  authorities where objecttype ='Administrative-Dash Board' into  var_id;

	IF ((SELECT count(id) FROM authorities where authority='VIEW_TEST_ASSIGNMENT_ERRORS' and activeflag=true) = 0) THEN
		RAISE NOTICE  '%', 'authorities with VIEW_TEST_ASSIGNMENT_ERRORS not exists. Inserting';
		INSERT INTO authorities(
			    id, authority, displayname, objecttype, createddate, createduser, 
			    activeflag, modifieddate, modifieduser,tabname,groupingname,level,sortorder)
		    VALUES (nextval('authorities_id_seq'),'VIEW_TEST_ASSIGNMENT_ERRORS','View Test Assignment Errors ',
				'Administrative-Dash Board', now(),(Select id from aartuser where username='cetesysadmin'), 
				TRUE,now(),(Select id from aartuser where username='cetesysadmin'),'Other','Dashboard',2,var_id);
	ELSE 
		RAISE NOTICE  '%', 'authorities with VIEW_TEST_ASSIGNMENT_ERRORS exists. Skipping';
	END IF;
END;
$BODY$;


