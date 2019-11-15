
----dml/*.sql ==> For ddl/*.sql
--US19228: Ability to exclude permission from roles
--DE14866: Should not be able to select bundled report permission for teacher

 INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 VALUES(
	(select id from groups where groupname ilike 'Teacher'), 
	(select id from authorities where authority ilike 'VIEW_GNRL_STUDENT_RPT_BUNDLED'), 
	(select id from assessmentprogram where abbreviatedname ilike 'KAP'), 
	(select id from organization where displayidentifier = 'KS')
 );