----dml/*.sql ==> For ddl/*.sql
--US19228: Ability to exclude permission from roles

INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 VALUES(
	(select id from groups where groupname ilike 'Teacher'), 
	(select id from authorities where authority ilike 'VIEW_GENERAL_STUDENT_REPORT'), 
	(select id from assessmentprogram where abbreviatedname ilike 'KAP'), 
	(select id from organization where displayidentifier = 'KS')
 );

INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 VALUES(
	(select id from groups where groupname ilike 'Teacher'), 
	(select id from authorities where authority ilike 'VIEW_ALT_YEAREND_STD_IND_REP'), 
	(select id from assessmentprogram where abbreviatedname ilike 'DLM'), 
	(select id from organization where displayidentifier = 'KS')
 );

INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 VALUES(
	(select id from groups where groupname ilike 'Teacher'), 
	(select id from authorities where authority ilike 'VIEW_ALT_YEAREND_STD_IND_REP'), 
	(select id from assessmentprogram where abbreviatedname ilike 'DLM'), 
	(select id from organization where displayidentifier = 'MO')
 );

INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 VALUES(
	(select id from groups where groupname ilike 'Teacher'), 
	(select id from authorities where authority ilike 'VIEW_ALT_YEAREND_STD_IND_REP'), 
	(select id from assessmentprogram where abbreviatedname ilike 'DLM'), 
	(select id from organization where displayidentifier = 'NY')
 );

INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 VALUES(
	(select id from groups where groupname ilike 'Teacher'), 
	(select id from authorities where authority ilike 'VIEW_ALT_YEAREND_STD_IND_REP'), 
	(select id from assessmentprogram where abbreviatedname ilike 'DLM'), 
	(select id from organization where displayidentifier = 'VT')
 );

INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 VALUES(
	(select id from groups where groupname ilike 'Teacher: PNP Read Only'), 
	(select id from authorities where authority ilike 'VIEW_GENERAL_STUDENT_REPORT'), 
	(select id from assessmentprogram where abbreviatedname ilike 'KAP'), 
	(select id from organization where displayidentifier = 'KS')
 );

INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 VALUES(
	(select id from groups where groupname ilike 'Teacher: PNP Read Only'), 
	(select id from authorities where authority ilike 'VIEW_GNRL_STUDENT_RPT_BUNDLED'), 
	(select id from assessmentprogram where abbreviatedname ilike 'KAP'), 
	(select id from organization where displayidentifier = 'KS')
 );

INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 VALUES(
	(select id from groups where groupname ilike 'Teacher: PNP Read Only'), 
	(select id from authorities where authority ilike 'VIEW_ALT_YEAREND_STD_IND_REP'), 
	(select id from assessmentprogram where abbreviatedname ilike 'DLM'), 
	(select id from organization where displayidentifier = 'KS')
 );

INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 VALUES(
	(select id from groups where groupname ilike 'Teacher: PNP Read Only'), 
	(select id from authorities where authority ilike 'VIEW_ALT_YEAREND_STD_IND_REP'), 
	(select id from assessmentprogram where abbreviatedname ilike 'DLM'), 
	(select id from organization where displayidentifier = 'MO')
 );

INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 VALUES(
	(select id from groups where groupname ilike 'Teacher: PNP Read Only'), 
	(select id from authorities where authority ilike 'VIEW_ALT_YEAREND_STD_IND_REP'), 
	(select id from assessmentprogram where abbreviatedname ilike 'DLM'), 
	(select id from organization where displayidentifier = 'NY')
 );

INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 VALUES(
	(select id from groups where groupname ilike 'Teacher: PNP Read Only'), 
	(select id from authorities where authority ilike 'VIEW_ALT_YEAREND_STD_IND_REP'), 
	(select id from assessmentprogram where abbreviatedname ilike 'DLM'), 
	(select id from organization where displayidentifier = 'VT')
 );

 INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 VALUES(
	(select id from groups where groupname ilike 'Teacher'), 
	(select id from authorities where authority ilike 'DATA_EXTRACTS_KAP_STUDENT_SCORES'), 
	(select id from assessmentprogram where abbreviatedname ilike 'KAP'), 
	(select id from organization where displayidentifier = 'KS')
 );

INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
 VALUES(
	(select id from groups where groupname ilike 'Teacher: PNP Read Only'), 
	(select id from authorities where authority ilike 'DATA_EXTRACTS_KAP_STUDENT_SCORES'), 
	(select id from assessmentprogram where abbreviatedname ilike 'KAP'), 
	(select id from organization where displayidentifier = 'KS')
 );
 