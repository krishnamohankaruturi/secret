--dml/700.sql
--insert into lcs (lcsid) 
--select distinct lcsid from lcsstudentstests;

--Update script to remove ASSESSMENT TOPICS option from upload metadata �File Type.
update category set modifieduser =(select id  from aartuser where email ='cete@ku.edu' ), 
			modifieddate =now(), activeflag =false where categorycode='ASSESSMENT_TOPICS';
			
			
-- Not planned in this release & reverting.
update fieldspecificationsrecordtypes set activeflag = false ,
modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where mappedname = 'sectionRefId' ;

 -- Script changes for DE16542
update authorities set tabName='Other',groupingName='Interim',level=1,
sortorder= ((select sortorder from authorities where authority = 'PERM_INTERIM_ACCESS')+
(select sortorder from authorities where authority = 'VIEW_DASHBOARD_MENU'))/2,
modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin'),
objecttype = 'Other-Interim'
where authority = 'VIEW_INT_PRED_DISTRICT_REPORT';

update authorities set tabName='Other',groupingName='Interim',level=1,
sortorder= ((select sortorder from authorities where authority = 'VIEW_INT_PRED_DISTRICT_REPORT')+
(select sortorder from authorities where authority = 'VIEW_DASHBOARD_MENU'))/2,
modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin'),
objecttype = 'Other-Interim'
where authority = 'VIEW_INT_PRED_SCHOOL_REPORT';

update authorities set sortorder= 174*100,
modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin'),
objecttype = 'Other-Dashboard'
where authority = 'VIEW_REACTIVATIONS_DASHBOARD';

update authorities set sortorder= 175*100,
modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin'),
objecttype = 'Other-Dashboard'
where authority = 'VIEW_TESTING_SCORING_DASHBOARD';

update authorities set sortorder= 176*100,
modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin'),
objecttype = 'Other-Dashboard'
where authority = 'VIEW_TESTING_OUTHOURS_DASHBOARD';

update authorities set sortorder= 177*100,
modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin'),
objecttype = 'Other-Dashboard'
where authority = 'VIEW_TESTING_SUMMARY_DASHBOARD';
			


-- F510: Student Tracker enhancements (Upadting the YE model ELA testspecs to identify the wirting testlets)
DO
$BODY$
BEGIN

 DROP TABLE IF EXISTS temp_testSpec;
 DROP TABLE IF EXISTS lm_model;

 CREATE TEMP TABLE temp_testSpec AS (SELECT id FROM testspecification  where externalid in (815, 816, 817, 818, 819, 820, 767, 837, 841));

 CREATE TEMP TABLE lm_model AS (SELECT testspecificationid, max(ranking) as maxranking from lmassessmentmodelrule where testspecificationid in (select id from temp_testSpec) group by testspecificationid);

 UPDATE lmassessmentmodelrule lm set writingassessment = true FROM lm_model templm
 WHERE templm.testspecificationid = lm.testspecificationid and templm.maxranking = lm.ranking;

 DROP TABLE IF EXISTS temp_testSpec;
 DROP TABLE IF EXISTS lm_model;

END;
$BODY$;
