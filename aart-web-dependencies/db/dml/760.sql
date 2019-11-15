-- dml/760.sql
--F816 sso login error messages
DO $BODY$ 
begin
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='sso_user_inactive') THEN
                 INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
    			 VALUES ('sso_user_inactive','sso_error_conditions','inactive_user','The user, %1$s, is inactive in Educator Portal and cannot login until activated.',12,now(),12,now());
		END IF;
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='sso_orgs_inactive') THEN
                 INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
    			 VALUES ('sso_orgs_inactive','sso_error_conditions','inactive_orgs','The user, %1$s, is associated with only inactive organizations and cannot login until an active organization is added to the user.',12,now(),12,now());
		END IF; 
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='sso_user_not_found') THEN
                 INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
    			 VALUES ('sso_user_not_found','sso_error_conditions','user_not_found','The user, %1$s, could not be found in Educator Portal.',12,now(),12,now());
		END IF; 
		IF not EXISTS (SELECT 1 FROM appconfiguration WHERE attrcode='sso_auth_error') THEN
                 INSERT INTO public.appconfiguration(attrcode, attrtype, attrname, attrvalue, createduser,createddate, modifieduser, modifieddate)
    			 VALUES ('sso_auth_error','sso_error_conditions','generic_auth_error','There was an issue authenticating your user.  Please log in again.',12,now(),12,now());
		END IF; 
end; $BODY$;

--F841 script to insert permission
INSERT INTO authorities(
authority, displayname, objecttype, createddate, createduser, 
activeflag, modifieddate, modifieduser,tabName,groupingName,labelName,level,sortorder)
VALUES ('DATA_EXTRACTS_TEST_ADMIN_PLTW', 'Create PLTW Test Administration Data Extract','Reports-Data Extracts', current_timestamp,(Select id from aartuser where username='cetesysadmin'), 
true, current_timestamp,(Select id from aartuser where username='cetesysadmin'),'Reports','Data Extracts','PLTW-Specific Extracts',1, ((select sortorder from authorities where authority='DATA_EXTRACTS_TEST_ADMIN')+
(select sortorder from authorities where authority='DATA_EXTRACTS_KELPA2_AGREEMENT'))/2);


update authorities set sortorder = 14270
where id=(select id from authorities where authority ='DATA_EXTRACTS_TEST_ADMIN_PLTW');


--F840 insert permission DATA_EXTRACTS_PLTW_TST_READINESS
INSERT INTO authorities(
authority, displayname, objecttype, createddate, createduser, 
activeflag, modifieddate, modifieduser,tabName,groupingName,labelName,level,sortorder)
VALUES ('DATA_EXTRACTS_PLTW_TESTING_READ', 'Create PLTW Testing Readiness','Reports-Data Extracts', current_timestamp,(Select id from aartuser where username='cetesysadmin'), 
true, current_timestamp,(Select id from aartuser where username='cetesysadmin'),'Reports','Data Extracts','PLTW-Specific Extracts',1, 14280);


select * from insert_groupauthoritiesexclusion();
