-- permissions updates dml

  --Removing the two permissions as part of DE16301
update authorities set tabName='--NA--',groupingName='',
	level=-99,sortorder= -99,activeflag=false,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_KELPA2_ELP_STUDENT_SCORE';
	
update authorities set tabName='--NA--',groupingName='',
	labelName='',level=-99,sortorder= -99,activeflag=false,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DATA_EXTRACTS_DLM_DS_SUMMARY';
	
	--updating displayname as part of DE16282
update authorities set displayname='Create KAP Students (Bundled)',
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority ='DYNA_BUNDLE_GENERAL_ASSESSMENT';
	
--changing the order of permissions 
	--Alternate Assessments
update authorities set sortorder= 98*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_BLUEPRINT_COVERAGE';
	
update authorities set sortorder= 99*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_CLASSROOM_REPORT';
	
update authorities set sortorder= 101*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin')
	where authority	= 'VIEW_ALTERNATE_ROSTER_REPORT';

update authorities set sortorder= 102*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin')
	where authority	= 'VIEW_ALTERNATE_STUDENT_REPORT';
	
update authorities set sortorder= 103*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_MONITORING_SUMMARY';

update authorities set sortorder= 104*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_SCHOOL_REPORT';
		
update authorities set sortorder= 105*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_SCH_SUMMARY_BUNDLED_REP';

update authorities set sortorder= 106*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin')
	where authority	= 'VIEW_ALT_YEAREND_STATE_REPORT';	
		
update authorities set sortorder= 107*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_YEAREND_STD_IND_REP';
	
update authorities set sortorder= 108*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_YEAREND_STD_BUNDLED_REP';
	
update authorities set sortorder= 109*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_STUDENT_SUMMARY_REP';
	
update authorities set sortorder= 110*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_STD_SUMMARY_BUNDLED_REP';

--Career Pathways Assessments

update authorities set sortorder= 113*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_CPASS_ASMNT_STUDENT_IND_REP';

update authorities set sortorder= 114*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_CPASS_ASMNT_STUDENT_BUN_REP';
		
--Data Extracts,General

update authorities set sortorder= 115*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_CURRENT_ENROLLMENT';
		
update authorities set sortorder= 116*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_MONITOR_SCORING';	
	
update authorities set sortorder= 117*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_ORGANIZATIONS';

update authorities set sortorder= 118*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_ROSTER';	
	
--Adding SC Code Data Extract as part of DE16300
update authorities set tabName='Reports',groupingName='Data Extracts',
    labelName='General',displayname ='Create SC Code Data Extract',
    level=1,sortorder= 119*100,
    modifieddate=now(), 
    modifieduser=(select id from aartuser where username='cetesysadmin') 
    where authority = 'DATA_EXTRACTS_SPL_CIRCM_CODE_REP';
    
update authorities set sortorder= 120*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_SECURITY_AGREEMENT';

update authorities set sortorder= 121*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_STU_UNAME_PASSWORD';
	
update authorities set sortorder= 122*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_PNP';

update authorities set sortorder= 123*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_PNP_SUMMARY';

update authorities set sortorder= 124*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_TEC_RECORDS';
		
update authorities set sortorder= 125*100,
	modifieddate=now(),
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_TEST_TICKETS';
		
update authorities set sortorder= 126*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_USERS';
	
--Data Extracts,DLM-Specific Extracts

update authorities set sortorder= 127*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DATA_EXTRACTS_DLM_BP_COVERAGE';
	
update authorities set sortorder= 128*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DATA_EXTRACTS_FCS_REPORT';

update authorities set sortorder= 129*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DATA_EXTRACTS_DLM_Gen_Research';

update authorities set sortorder= 130*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DATA_EXTRACTS_DLM_INCIDENT';	

update authorities set sortorder= 131*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PD_TRAINING_EXPORT_FILE_CREATOR';

update authorities set sortorder= 132*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DATA_EXTRACTS_DLM_SPEC_CIRCUM';	
	
update authorities set sortorder= 133*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DATA_EXTRACTS_DLM_TEST_ADMIN';
		
update authorities set sortorder= 134*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_DLM_TRAINING_STATUS_EXTRACT';
	
--Tools tab
--Organization Data

update authorities set sortorder= 156*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'TOOLS_DEACTIVATE_ORGANIZATION';
		
update authorities set sortorder= 157*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'TOOLS_MERGE_SCHOOLS';
		
update authorities set sortorder= 158*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'TOOLS_MOVE_A_SCHOOL';

--Test Reset

update authorities set sortorder= 164*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'MANAGE_LCS';
		
update authorities set sortorder= 165*100,
	modifieddate=now(), 
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'RESET_DLM_TESTLETS';

update helptopic set name='KELPA2 Assessments',
    description='Help topics regarding the administration of KELPA2 assessments.',
    modifieduser=(select id from aartuser where username = 'cetesysadmin'),
    modifieddate=now() 
    where name='K-ELPA Assessments' and activeflag is true;

update helptopic set name='KELPA2 Scoring',
    description='For help regarding KELPA2 Scoring.',
    modifieduser=(select id from aartuser where username = 'cetesysadmin'),
    modifieddate=now() 
    where name='K-ELPA Scoring' and activeflag is true;

update helptopic set name='KELPA2 Speaking',
    description='KELPA2 Speaking supplemental stimulus material',
    modifieduser=(select id from aartuser where username = 'cetesysadmin'),
    modifieddate=now() 
    where name='K-ELPA Speaking ' and activeflag is true;

update helptopic set name='KELPA2 Writing',
    description='KELPA2 Writing supplemental stimulus material',
    modifieduser=(select id from aartuser where username = 'cetesysadmin'),
    modifieddate=now() 
    where name='K-ELPA Writing ' and activeflag is true;
    
    
--Renaming the permission "Create DLM District Summary Data Extract"
update authorities set tabName='Reports',groupingName='Alternate Assessments',labelName=null,
	displayname='View DLM District Aggregate CSV',activeflag=true,
	level=1,sortorder= ((select sortorder from authorities where authority='VIEW_ALT_YEAREND_DISTRICT_REPORT')
	+(select sortorder from authorities where authority='VIEW_ALTERNATE_ROSTER_REPORT'))/2,
	modifieddate=now(),
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DATA_EXTRACTS_DLM_DS_SUMMARY';
	
-- updating the level for the edit pnp permission
update authorities set level = 1,
	modifieddate=now(),
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_PNP_OPTIONS';


--DE16338
--updating group authorities as false for whose authrities are not in use
update groupauthorities set activeflag = false,
modifieddate=now(), 
modifieduser=(select id from aartuser where username='cetesysadmin')
where id in
(select gau.id
from authorities au
inner join groupauthorities gau on au.id = gau.authorityid and au. activeflag = false);

update category set activeflag = false,
modifieddate=now(), 
modifieduser=(select id from aartuser where username='cetesysadmin')
where categorycode = 'KELPA_ELP_STUDENT_SCORE_FILE';


update reportassessmentprogram set activeflag = false
where authorityid =
(select id from authorities where authority ='VIEW_KELPA2_ELP_STUDENT_SCORE');

-- Scheduler rename
update batchjobschedule  set jobrefname='projectedTestingDistrictSummaryReportScheduler',
                 jobname='Projected Testing District Summary Report'
                 where jobrefname='districtSummaryReportScheduler';
