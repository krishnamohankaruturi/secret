--POPULATE LAST YEAR SCORES IN NEW SCHEMA

INSERT INTO studentstestscore(
            studenttestid, taskvariantid, scorerid, score, nonscorereason, 
            rubriccategoryid, activeflag, source, createddate, createduser, 
            modifieddate, modifieduser)
   (select distinct on (sas.studentstestsid, ccqi.taskvariantid, ccqi.rubriccategoryid) 
sas.studentstestsid as studenttestid, 
ccqi.taskvariantid,
(CASE WHEN ccqi.externalscorer is null THEN ccqi.createduser ELSE ccqi.externalscorer END) as scorerid, 
ccqi.score, 
ccqi.nonscoringreason,
ccqi.rubriccategoryid,
true as activeflag,
ccqi.source,
now() as createddate,
(Select id from aartuser where username  = 'cetesysadmin') as createduser,
now() as modifieddate,
(Select id from aartuser where username  = 'cetesysadmin') as modifieduser
from scoringassignmentstudent sas
inner join ccqscore ccq on ccq.scoringassignmentstudentid = sas.id and ccq.activeflag is true
inner join ccqscoreitem ccqi on ccqi.ccqscoreid = ccq.id and ccqi.activeflag is true
where sas.activeflag is true 
order by sas.studentstestsid, ccqi.taskvariantid, ccqi.rubriccategoryid, ccqi.modifieddate desc);



update scoringassignmentstudent sas set enrollmentid  = (select enrollmentid from studentstests where id = sas.studentstestsid), testid = (select testid from studentstests where id = sas.studentstestsid);



DO 
$BODY$
DECLARE
    rec RECORD;
BEGIN
   FOR rec IN select distinct (testid) from scoringassignmentstudent order by testid LOOP
   
    INSERT INTO scoringtestmetadata(
            testid, taskvariantid, testletid, positionintest, rubrictype, 
            rubricminscore, rubricmaxscore, clusterscoring, createddate)            
            (WITH main as (
	    select row_number() over() as positionintest ,* from (select ts.testid, tv.id as taskvariantid,
	      tstv.testsectionid,
	      tstv.testletid,
	      tstv.taskvariantposition,
	      tv.rubricminscore,
	      tv.rubricmaxscore,
	      tv.rubrictype,
	      tv.clusterscoring,
	      tt.code,
	      ts.sectionorder
	  from testsection ts
	  inner join testsectionstaskvariants tstv on tstv.testsectionid = ts.id
	  inner join taskvariant tv on tv.id = tstv.taskvariantid
	  inner join tasktype tt on tt.id = tv.tasktypeid
	  where ts.testid =  rec.testid
	  order by sectionorder,taskvariantposition
	  ) testwithitem
	 )
	 select m.testid, m.taskvariantid, m.testletid, m.positionintest, m.rubrictype, 
	 m.rubricminscore, m.rubricmaxscore, m.clusterscoring, now() as createddate  
	 from main m where code in ('ER','UDI','UI'));
   END LOOP;
END; 
$BODY$;

----F477 DML --------
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ('PERM_QC_TESTSESSION_CREATE', 'Create QC Test Session', 'Test Management-Test Session',CURRENT_TIMESTAMP,
(select id from aartuser where email='cete@ku.edu'), true,CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));

--F632
delete from groupauthorities where authorityid = (select id from authorities where authority='REACTIVATE_HS_STDNT_TESTSESSION');
delete from authorities where authority='REACTIVATE_HS_STDNT_TESTSESSION';
delete from groupauthorities where authorityid = (select id from authorities where authority='END_HS_STDNT_TESTSESSION');
delete from authorities where authority='END_HS_STDNT_TESTSESSION';


--F581 DML
-- Update both abbreviated name and program name for K-ELPA
update assessmentprogram set abbreviatedname='KELPA2', 
				programname='KELPA2',
				modifieddate=now(),
				modifieduser=(select id from aartuser where username='cetesysadmin')
				where programname='K-ELPA' and activeflag is true;

update authorities set authority ='DATA_EXTRACTS_KELPA2_AGREEMENT',
                       displayname='Create KELPA2 Test Administration Monitoring Extract',
                       modifieddate=now(),
                       modifieduser=(select id from aartuser where username='cetesysadmin')
                       where  authority='DATA_EXTRACTS_K-ELPA_AGREEMENT' and activeflag is true; 

update authorities set authority ='VIEW_KELPA2_ELP_STUDENT_SCORE',
                       modifieddate=now(),
                       modifieduser=(select id from aartuser where username='cetesysadmin')
                       where  authority='VIEW_KELPA_ELP_STUDENT_SCORE' and activeflag is true;

-- F579 DML
--Settings Tab
	
--Batch Processes
		--95
update authorities set tabName='Settings',groupingName='Batch Processes',labelName='Registration',
	level=1, sortorder= 1*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_BATCH_REGISTER';
		--96
update authorities set tabName='Settings',groupingName='Batch Processes',labelName='Reporting',
	level=1, sortorder= 2*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_BATCH_REPORT';
		--97
update authorities set tabName='Settings',groupingName='Batch Processes',labelName='Web Services',
	level=1, sortorder= 3*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'UPLOAD_WEBSERVICE';	

--Create User Messages
		--98
update authorities set tabName='Settings',groupingName='Create User Messages',
	level=1, sortorder= 4*100,
    modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
    where authority = 'EP_MESSAGE_CREATOR';

--First Contact Survey
		--99
update authorities set tabName='Settings',groupingName='First Contact Survey',
	level=1, sortorder= 5*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'EDIT_FIRST_CONTACT_SETTINGS';
		
--General --> Annual Resets
		--100
update authorities set tabName='Settings',groupingName='General',labelName='Annual Resets',
	level=1, sortorder= 6*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ANNUAL_RESET';
--General --> PNP Options
		--101
update authorities set tabName='Settings',groupingName='General',labelName='PNP Options',
	level=2,  sortorder= 7*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_PNP_OPTIONS';
		
--Instructional Tools Support
		--103
update authorities set tabName='Settings',groupingName='Instructional Tools Support',
	level=1,  sortorder= 8*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'CHANGE_ITI_CONFIG';	
	
--Organization
		--104
update authorities set tabName='Settings',groupingName='Organization',level=1, sortorder= 9*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ORG_CREATE';
		--105
update authorities set tabName='Settings',groupingName='Organization',level=1, sortorder= 10*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ORG_MODIFY';
		--106
update authorities set tabName='Settings',groupingName='Organization',level=1, sortorder= 11*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ORG_MANAGE';
		--107
update authorities set tabName='Settings',groupingName='Organization',level=1, sortorder= 12*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ORG_UPLOAD';
		--108
update authorities set tabName='Settings',groupingName='Organization',level=1, sortorder= 13*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ORG_VIEW';
	
--Quality Control
		--109
update authorities set tabName='Settings',groupingName='Quality Control',level=1, sortorder= 14*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'QUALITY_CONTROL_COMPLETE';	

--Reports Setup --> Report Access
		--110
update authorities set tabName='Settings',groupingName='Reports Setup',labelName='Report Access',level=1, sortorder= 15*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority ='EDIT_REPORT_CONTROL_ACCESS';
		--111
update authorities set tabName='Settings',groupingName='Reports Setup',labelName='Report Access',level=1, sortorder= 16*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_REPORT_CONTROL_ACCESS';	
	
--Reports Setup -->Upload Metadata
	--112
update authorities set tabName='Settings',groupingName='Reports Setup',labelName='Upload Metadata',level=1, sortorder= 17*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority ='PERM_TEST_UPLOAD';	
	
--Reports Setup -->Upload Results
	--113
update authorities set tabName='Settings',groupingName='Reports Setup',labelName='Upload Results',level=1, sortorder= 18*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'SUMMATIVE_REPORTS_UPLOAD';	
	--114
update authorities set tabName='Settings',groupingName='Reports Setup',labelName='Upload Results',level=1, sortorder= 19*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_UPLOAD_RESULTS';		
		
--Roles
		--118
--"Limit number of users (1) per role" there is no query for this it is part of code

		--119
update authorities set tabName='Settings',groupingName='Roles',level=1, sortorder= 20*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ROLE_VIEW';
		--115
update authorities set tabName='Settings',groupingName='Roles',level=2, sortorder= 21*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ROLE_ASSIGN';
		--116
update authorities set tabName='Settings',groupingName='Roles',level=2, sortorder= 22*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ROLE_CREATE';
		--117
update authorities set tabName='Settings',groupingName='Roles',level=2, sortorder= 23*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ROLE_MODIFY';

--Rosters
		--124
update authorities set tabName='Settings',groupingName='Rosters',level=1, sortorder= 24*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ROSTERRECORD_VIEW';

		--(it is added from delete list{in use})(171)
update authorities set tabName='Settings',groupingName='Rosters', sortorder= 25*100,
	level=2,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ROSTERRECORD_CREATE';

		--(it is added from delete list{in use})(172)
update authorities set tabName='Settings',groupingName='Rosters', sortorder= 26*100,
	level=2,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ROSTERRECORD_MODIFY';

		--120
update authorities set tabName='Settings',groupingName='Rosters', sortorder= 27*100,
	level=2,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_REPORT_DOWNLOAD';
		--121
update authorities set tabName='Settings',groupingName='Rosters',  sortorder= 28*100,
	level=2,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ROSTERRECORD_SEARCH';
		--122
update authorities set tabName='Settings',groupingName='Rosters', sortorder= 29*100,
	level=2,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ROSTERRECORD_UPLOAD';
		--123
update authorities set tabName='Settings',groupingName='Rosters', sortorder= 30*100,
	level=2,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ROSTERRECORD_VIEWALL';

--Settings --> Students
		--130
update authorities set tabName='Settings',groupingName='Students',level=1,  sortorder= 31*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_STUDENTRECORD_CREATE';
		--131
update authorities set tabName='Settings',groupingName='Students',level=1,  sortorder= 32*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_STUDENTRECORD_MODIFY';

		--133
update authorities set tabName='Settings',groupingName='Students',level=1,  sortorder= 33*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_EXIT_STUDENT';
		--134
update authorities set tabName='Settings',groupingName='Students',level=1,  sortorder= 34*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_EXIT_ALT_STUDENT';

	--(it is added from delete list{in use})(173)
update authorities set tabName='Settings',groupingName='Students',displayname='Find student',level=1, sortorder= 35*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_STUDENTRECORD_SEARCH';
	
		--135
update authorities set tabName='Settings',groupingName='Students',level=1,  sortorder= 36*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_TRANSFER_STUDENT';
			
		--132
update authorities set tabName='Settings',groupingName='Students',level=1,  sortorder= 37*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_STUDENTRECORD_VIEW';
	
--Students --> Access Profile (PNP)
		--125	
update authorities set tabName='Settings',groupingName='Students',labelName='Access Profile (PNP)',level=1, sortorder= 38*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'CREATE_STUDENT_PNP';	
		--126
update authorities set tabName='Settings',groupingName='Students',labelName='Access Profile (PNP)',level=1,  sortorder= 39*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'EDIT_STUDENT_PNP';
		--127
update authorities set tabName='Settings',groupingName='Students',labelName='Access Profile (PNP)',level=1, sortorder= 40*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_STUDENT_PNP';

--Students --> First Contact Survey (FCS)															
		--128
update authorities set tabName='Settings',groupingName='Students',labelName='First Contact Survey (FCS)',level=1, sortorder= 41*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'EDIT_FIRST_CONTACT_SURVEY';
		--129
update authorities set tabName='Settings',groupingName='Students',labelName='First Contact Survey (FCS)',level=1, sortorder= 42*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_FIRST_CONTACT_SURVEY';

	
--Settings --> Test Records			
		--136
update authorities set tabName='Settings',groupingName='Test Records',level=1, sortorder= 43*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_TESTRECORD_CLEAR';
		--137
update authorities set tabName='Settings',groupingName='Test Records',level=1, sortorder= 44*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_TESTRECORD_CREATE';	
		--138
update authorities set tabName='Settings',groupingName='Test Records',level=1, sortorder= 45*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_ENRL_UPLOAD';
		--139
update authorities set tabName='Settings',groupingName='Test Records',level=1, sortorder= 46*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_TESTRECORD_VIEW';	

--Settings --> Users
		--150
update authorities set tabName='Settings',groupingName='Users',level=1, sortorder= 47*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_USER_VIEW';
		--141
update authorities set tabName='Settings',groupingName='Users',
	level=2, sortorder= 48*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_USER_ACTIVATE';
		--142
update authorities set tabName='Settings',groupingName='Users',
	level=2,  sortorder= 49*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_USER_CLAIM';
		--143
update authorities set tabName='Settings',groupingName='Users',
	level=2, sortorder= 50*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_USER_CREATE';
		--144
update authorities set tabName='Settings',groupingName='Users',displayname='Deactivate User',
	level=2, sortorder= 51*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_USER_INACTIVATE';
		--145
update authorities set tabName='Settings',groupingName='Users',
	level=2, sortorder= 52*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_USER_MODIFY';

		--146
update authorities set tabName='Settings',groupingName='Users',
	level=2, sortorder= 53*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_USER_MERGE';
		--147
update authorities set tabName='Settings',groupingName='Users',
	level=2, sortorder= 54*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_USER_MOVE';
		--148
update authorities set tabName='Settings',groupingName='Users',
	level=2, sortorder= 55*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_USER_SEARCH';

		--140	
update authorities set tabName='Settings',groupingName='Users',displayname='Upload PD Training Results',
	level=2, sortorder= 56*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PD_TRAINING_RESULTS_UPLOADER';

		--149
update authorities set tabName='Settings',groupingName='Users',
	level=2, sortorder= 57*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_USER_UPLOAD';

--MANAGE TESTS TAB

--Instructional Tools
	--2
update authorities set tabName='Manage Tests',groupingName='Instructional Tools',level=1,sortorder= 58*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='CANCEL_ITI_ASSIGNMENT';
	--3
update authorities set tabName='Manage Tests',groupingName='Instructional Tools',level=1,sortorder= 59*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='ITI_OVERRIDE_SYS_REC_LEVEL';
	
--Projected Testing Group
	--4
update authorities set tabName='Manage Tests',groupingName='Projected Testing',level=1,sortorder= 60*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='EDIT_DETAILED_PROJECTED_TESTING';
	--5
update authorities set tabName='Manage Tests',groupingName='Projected Testing',level=1,sortorder= 61*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='VIEW_DETAILED_PROJECTED_TESTING';
	--6
update authorities set tabName='Manage Tests',groupingName='Projected Testing',level=1,sortorder= 62*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='VIEW_SUMMARY_PROJECTED_TESTING';
	
--Test Coordination & Test Management --> Summative Testing
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Summative Testing',
	level=1,sortorder= 63*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='HIGH_STAKES';
	--7
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Summative Testing',
	level=2,sortorder= 64*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='HIGH_STAKES_SPL_CIRCUM_CODE';
	--8
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Summative Testing',
	level=2,sortorder= 65*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='HIGH_STAKES_SPL_CIRCUM_CODE_SEL';

	
--Test Coordination & Test Management -->Test Cordination menu
	--12
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Coordination Menu',
	level=1,sortorder= 66*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='HIGH_STAKES_TICKETING';
	--15
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Coordination Menu',
	level=2,sortorder= 67*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='TEST_COORD_TESTSESSION_SCORING';	
	--16
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Coordination Menu',
	level=2,sortorder= 68*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='VIEW_DAILY_ACCESS_CODES';

	--13
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Coordination Menu',
	level=2,sortorder= 69*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='TEST_COORD_TESTSESSION_MONITOR';
	--10
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Coordination Menu',
	level=3,sortorder= 70*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='END_HS_STDNT_TESTSESSION';
	--11
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Coordination Menu',
	level=3,sortorder= 71*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='EXTEND_TEST_SESSION_WINDOW';

	--14
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Coordination Menu',
	level=3,sortorder= 72*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='REACTIVATE_HS_STDNT_TESTSESSION';

	--17
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Coordination Menu',
	level=3,sortorder= 73*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='VIEW_INTERIM_THETA_VALUES';
	
--Test Coordination & Test Management -->Test Management menu
	--26
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Management Menu',
	level=1,sortorder= 74*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='PERM_TESTSESSION_VIEW';
	--25
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Management Menu',
	level=2,sortorder= 75*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='HIGH_STAKES_TESTSESSION_VIEW';
	--28
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Management Menu',
	level=2,sortorder= 76*100, 
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='PERM_TEST_VIEW';
-- New property added in X Release
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Management Menu',
	level=3,sortorder= 77*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='PERM_QC_TESTSESSION_CREATE';
	--19
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Management Menu',
	level=3,sortorder= 78*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='PERM_TESTSESSION_CREATE';
	--20
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Management Menu',
	level=3,sortorder= 79*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='PERM_TESTSESSION_DELETE';
	--21
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Management Menu',
	level=3,sortorder= 80*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='PERM_TESTSESSION_MODIFY';
	--22
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Management Menu',
	level=3,sortorder= 81*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='END_STUDENT_TESTSESSION';
	--23
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Management Menu',
	level=3,sortorder= 82*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='PERM_TESTSESSION_MONITOR';
	--24
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Management Menu',
	level=3,sortorder= 83*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='REACTIVATE_STUDENT_TESTSESSION';

	--27
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',labelName='Test Management Menu',
	level=3,sortorder= 84*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='PERM_TESTTICKET_VIEW';

	--18
update authorities set tabName='Manage Tests',groupingName='Test Coordination & Test Management',
	labelName='Test Management Menu',displayname='View Test Item Learning Map Nodes',level=1,sortorder= 85*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='PERM_VIEW_NODES';
	
--Test Window Group
	--29
update authorities set tabName='Manage Tests',groupingName='Test Windows',level=1,sortorder= 86*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='PERM_OTW_EDIT';
	--30
update authorities set tabName='Manage Tests',groupingName='Test Windows',level=1,sortorder= 87*100,
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='PERM_OTW_VIEW';	

-- SCORING Tab
		--94
update authorities set tabName='Scoring',level=0,sortorder= 88*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_SCORING_VIEW';	
	--Manage Scoring
--Grouping is not mentioned in sheet but mentioned in mock-up 	
		--89
update authorities set tabName='Scoring',groupingName='Manage Scoring',
	level=1,sortorder= 89*100, 
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_SCORING_ASSIGNSCORER';
		--90
update authorities set tabName='Scoring',groupingName='Manage Scoring',
	level=1,sortorder= 90*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_SCORING_MONITORSCORES';	
	
--My Scoring
		--92
update authorities set tabName='Scoring',groupingName='My Scoring',
	level=1,sortorder= 91*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_SCORE_ALL_TEST';
		
		--91
update authorities set tabName='Scoring',groupingName='My Scoring',
	level=1,sortorder= 92*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_SCORE_CCQ_TESTS';
	
		--93
update authorities set tabName='Scoring',groupingName='My Scoring',
	level=1,sortorder= 93*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_SCORE_UPLOADSCORER';

--REPORTS Tab
--Under Reports Main tab
		--88
update authorities set tabName='Reports',level=0,sortorder= 94*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_REPORT_VIEW';
	--87
update authorities set tabName='Reports',sortorder= 95*100,
	level=1,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_ALL_STUDENT_REPORTS';


	--Alternate Assessments
		--35
update authorities set tabName='Reports',groupingName='Alternate Assessments',displayname ='Create DLM Students (Bundled)',
	level=1,sortorder= 96*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DYNA_BUNDLE_ALTERNATE_ASSESSMENT';
		--36
update authorities set tabName='Reports',groupingName='Alternate Assessments',displayname ='Create DLM Student Summary (Bundled)',
	level=1,sortorder= 97*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'CREATE_ALT_STD_SUM_DYN_BNDL_REP';
		--37
update authorities set tabName='Reports',groupingName='Alternate Assessments',displayname ='View DLM ITI Class Roster',
	level=1,sortorder= 98*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin')
	where authority	= 'VIEW_ALTERNATE_ROSTER_REPORT';
		--38
update authorities set tabName='Reports',groupingName='Alternate Assessments',displayname ='View DLM ITI Student Progress',
	level=1,sortorder= 99*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin')
	where authority	= 'VIEW_ALTERNATE_STUDENT_REPORT';
		--39
update authorities set tabName='Reports',groupingName='Alternate Assessments',displayname ='View DLM District Aggregate',
	level=1,sortorder= 100*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_YEAREND_DISTRICT_REPORT';
		--41
update authorities set tabName='Reports',groupingName='Alternate Assessments', displayname ='View DLM Students (Bundled)',
	level=1,sortorder= 101*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_YEAREND_STD_BUNDLED_REP';
	--40
update authorities set tabName='Reports',groupingName='Alternate Assessments',displayname ='View DLM State Aggregate',
	level=1,sortorder= 102*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin')
	where authority	= 'VIEW_ALT_YEAREND_STATE_REPORT';
		--43
update authorities set tabName='Reports',groupingName='Alternate Assessments',displayname ='View DLM Student Summary (Bundled)',
	level=1,sortorder= 103*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_STD_SUMMARY_BUNDLED_REP';
	--42
update authorities set tabName='Reports',groupingName='Alternate Assessments',displayname ='View DLM Student Individual',
	level=1,sortorder= 104*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_YEAREND_STD_IND_REP';

		--44
update authorities set tabName='Reports',groupingName='Alternate Assessments',displayname ='View DLM Blueprint Coverage',
	level=1,sortorder= 105*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_BLUEPRINT_COVERAGE';
		--45
update authorities set tabName='Reports',groupingName='Alternate Assessments',displayname ='View DLM Student Summary',
	level=1,sortorder= 106*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_STUDENT_SUMMARY_REP';
		--46
update authorities set tabName='Reports',groupingName='Alternate Assessments',displayname ='View DLM Monitoring Summary',
	level=1,sortorder= 107*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_MONITORING_SUMMARY';
		--47
update authorities set tabName='Reports',groupingName='Alternate Assessments',displayname ='View DLM Class Aggregate',
	level=1,sortorder= 108*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_CLASSROOM_REPORT';
		--48
update authorities set tabName='Reports',groupingName='Alternate Assessments',displayname ='View DLM School Aggregate',
	level=1,sortorder= 109*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_SCHOOL_REPORT';
		--49
update authorities set tabName='Reports',groupingName='Alternate Assessments',displayname ='View DLM School Aggregate (Bundled)',
	level=1,sortorder= 110*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ALT_SCH_SUMMARY_BUNDLED_REP';

--Career Pathways Assessments														
		--50
update authorities set tabName='Reports',groupingName='Career Pathways Assessments',displayname ='Create cPass Students (Bundled)',
	level=1,sortorder= 111*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DYN_BUND_CARPATH_ASSESSMENT';
		--51
update authorities set tabName='Reports',groupingName='Career Pathways Assessments',displayname ='View cPass School Detail',
	level=1,sortorder= 112*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_CPASS_ASMNT_SCHOOL_DTL_REP';
		--52
update authorities set tabName='Reports',groupingName='Career Pathways Assessments',displayname ='View cPass Students (Bundled)',
	level=1,sortorder= 113*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_CPASS_ASMNT_STUDENT_BUN_REP';
		--53
update authorities set tabName='Reports',groupingName='Career Pathways Assessments',displayname ='View cPass Student Individual',
	level=1,sortorder= 114*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_CPASS_ASMNT_STUDENT_IND_REP';	

--Data Extracts->General
	--68
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='General',
	level=1,sortorder= 115*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_PNP';

	--69
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='General',
	level=1,sortorder= 116*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_PNP_SUMMARY';
--62
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='General',
	level=1,sortorder= 117*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_CURRENT_ENROLLMENT';
		--63
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='General',
	level=1,sortorder= 118*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_MONITOR_SCORING';
		--64
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='General',
	level=1,sortorder= 119*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_ORGANIZATIONS';
		--65
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='General',
	level=1,sortorder= 120*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_ROSTER';
		--66
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='General',
	level=1,sortorder= 121*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_SECURITY_AGREEMENT';
		--67
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='General',
	level=1,sortorder= 122*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_STU_UNAME_PASSWORD';

		--70
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='General',
	level=1,sortorder= 123*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_TEC_RECORDS';
		--71
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='General',
	level=1,sortorder= 124*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_TEST_TICKETS';
		--72
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='General',
	level=1,sortorder= 125*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_USERS';		
--Data Extracts->DLM-Specific Extracts													
	
		--54
update authorities set tabName='Reports',groupingName='Data Extracts',
	labelName='DLM-Specific Extracts',displayname ='Create DLM Blueprint Coverage Summary Data Extract',
	level=1,sortorder= 126*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DATA_EXTRACTS_DLM_BP_COVERAGE';
-- Marked for deletion but code exists and in use
update authorities set tabName='Reports',groupingName='Data Extracts',
	labelName='DLM-Specific Extracts',displayname ='Create DLM District Summary Data Extract',
	level=1,sortorder= 127*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DATA_EXTRACTS_DLM_DS_SUMMARY';
		--55
update authorities set tabName='Reports',groupingName='Data Extracts',
	labelName='DLM-Specific Extracts',displayname ='Create DLM General Research File Data Extract',
	level=1,sortorder= 128*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DATA_EXTRACTS_DLM_Gen_Research';		
		--56
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='DLM-Specific Extracts',
	level=1,sortorder= 129*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DATA_EXTRACTS_DLM_INCIDENT';		
		--57
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='DLM-Specific Extracts',
	level=1,sortorder= 130*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DATA_EXTRACTS_DLM_SPEC_CIRCUM';
		--58
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='DLM-Specific Extracts',
	level=1,sortorder= 131*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DATA_EXTRACTS_DLM_TEST_ADMIN';
		--59
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='DLM-Specific Extracts',
	level=1,sortorder= 132*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_DLM_TRAINING_STATUS_EXTRACT';
		--60
update authorities set tabName='Reports',groupingName='Data Extracts',
	labelName='DLM-Specific Extracts',displayname ='Create DLM First Contact Survey Extract',
	level=1,sortorder= 133*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DATA_EXTRACTS_FCS_REPORT';
		--61
update authorities set tabName='Reports',groupingName='Data Extracts',
	labelName='DLM-Specific Extracts',displayname ='Create DLM PD Training Export File',
	level=1,sortorder= 134*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PD_TRAINING_EXPORT_FILE_CREATOR';
		
--Data Extracts-->KAP-Specific Extracts										
		--77
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='KAP-Specific Extracts',
	level=1,sortorder= 135*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_KAP_STUDENT_SCORES';		
		--78
update authorities set tabName='Reports',groupingName='Data Extracts',
	labelName='KAP-Specific Extracts',displayname ='Create KAP Test Administration Data Extract',
	level=1,sortorder= 136*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_TEST_ADMIN';
		--79
update authorities set tabName='Reports',groupingName='Data Extracts',
	labelName='KAP-Specific Extracts',displayname ='Create SC Code Data Extract',
	level=1,sortorder= 137*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACT_SPL_CIRCUM_CODE_REP';
-- KELPA2 specific extracts
	--80
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='KELPA2-Specific Extracts',
	displayname='Create KELPA2 Test Administration Monitoring Extract',
	level=1,sortorder= 138*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_KELPA2_AGREEMENT';

--Data Extracts->Internal (KU) Extracts									
		--73
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='Internal (KU) Extracts',
	level=1,sortorder= 139*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_KSDE_TEST_TASC';
		--74
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='Internal (KU) Extracts',
	level=1,sortorder= 140*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_QUESTAR_PREID';
		--75
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='Internal (KU) Extracts',
	level=1,sortorder= 141*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_TESTFORM_ASIGNMENT';
		--76
update authorities set tabName='Reports',groupingName='Data Extracts',labelName='Internal (KU) Extracts',
	level=1,sortorder= 142*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DATA_EXTRACTS_TESTFORM_MEDIA';		

--General Assessments														
		--81
update authorities set tabName='Reports',groupingName='General Assessments',displayname ='Create KAP Students(Bundled)',
	level=1,sortorder= 143*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'DYNA_BUNDLE_GENERAL_ASSESSMENT';		
		--82
update authorities set tabName='Reports',groupingName='General Assessments',displayname ='View KAP District Summary',
	level=1,sortorder= 144*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_GENERAL_DISTRICT_REPORT';
		--83
update authorities set tabName='Reports',groupingName='General Assessments',displayname ='View KAP School Summary',
	level=1,sortorder= 145*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_GENERAL_SCHOOL_REPORT';
		--84
update authorities set tabName='Reports',groupingName='General Assessments',displayname ='View KAP Student Individual ',
	level=1,sortorder= 146*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_GENERAL_STUDENT_REPORT';
		--85
update authorities set tabName='Reports',groupingName='General Assessments',displayname ='View KAP Students (Bundled)',
	level=1,sortorder= 147*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_GNRL_STUDENT_RPT_BUNDLED';
-- Performance report exists and retaining 
update authorities set tabName='Reports',groupingName='General Assessments',
	level=1,sortorder= 148*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_KELPA2_ELP_STUDENT_SCORE';
		--86
update authorities set tabName='Reports',groupingName='General Assessments',displayname='View Student Writing Responses',
	level=1,sortorder= 149*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_GENERAL_STUDENT_MDPT_RESP';

--TOOLS Tab
--Under tools main tab
		--155	
update authorities set tabName='Tools',level=0,sortorder= 150*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_TOOLS_MENU';
	
--Help Management
		--151
update authorities set tabName='Tools',groupingName='Help Management',level=1,sortorder= 151*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'TOOLS_CREATE_HELP_CONTENT';
	
--Miscellaneous
		--154
update authorities set tabName='Tools',groupingName='Miscellaneous', level=1,sortorder= 152*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_MISCELLANEOUS';
		--152
update authorities set tabName='Tools',groupingName='Miscellaneous',level=2,sortorder= 153*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'AUDIT_HISTORY';
		--153
update authorities set tabName='Tools',groupingName='Miscellaneous',sortorder= 154*100,
	level=2,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'CUSTOM_REPORTS';	
	
--Organization Data
		--160
update authorities set tabName='Tools',groupingName='Organization Data',displayname='View Organization Data',level=1,sortorder= 155*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'TOOLS_VIEW_ORGANIZATION_DATA';
		--157
update authorities set tabName='Tools',groupingName='Organization Data',displayname='Merge Schools',
	level=2,sortorder= 156*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'TOOLS_MERGE_SCHOOLS';
		--158
update authorities set tabName='Tools',groupingName='Organization Data',displayname='Move a School',
	level=2,sortorder= 157*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'TOOLS_MOVE_A_SCHOOL';
	--156
update authorities set tabName='Tools',groupingName='Organization Data',displayname='Deactivate Organization',
	level=2,sortorder= 158*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'TOOLS_DEACTIVATE_ORGANIZATION';
		--159
update authorities set tabName='Tools',groupingName='Organization Data',displayname='Reactivate Organization',
	level=2,sortorder= 159*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'TOOLS_REACTIVATE_ORGANIZATION';
	
--Stundent Information
		--163
update authorities set tabName='Tools',groupingName='Student Information',level=1,sortorder= 160*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_STUDENT_INFORMATION';	
	--161
update authorities set tabName='Tools',groupingName='Student Information',
	level=2,sortorder= 161*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'EDIT_TDE_LOGIN';
		--162
update authorities set tabName='Tools',groupingName='Student Information',
	level=2,sortorder= 162*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'MERGE_STUDENT_RECORDS';
	
--Test Reset
	--166
update authorities set tabName='Tools',groupingName='Test Reset',level=1,sortorder= 163*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_TEST_RESET';
	--165
update authorities set tabName='Tools',groupingName='Test Reset',
	level=2,sortorder= 164*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'RESET_DLM_TESTLETS';
	--164
update authorities set tabName='Tools',groupingName='Test Reset',
	level=2,sortorder= 165*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'MANAGE_LCS';
	
--User maanagement
		--169
update authorities set tabName='Tools',groupingName='User Management',level=1,sortorder= 166*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_USER_MANAGEMENT';
	--167
update authorities set tabName='Tools',groupingName='User Management',
	level=2,sortorder= 167*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'EDIT_ACTIVATION_EMAIL';
		--168
update authorities set tabName='Tools',groupingName='User Management',
	level=2,sortorder= 168*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'EDIT_INACTIVE_ACCOUNTS';

		--170(it is added from delete list{in use})
update authorities set tabName='Tools',groupingName='User Management',
	level=2,sortorder= 169*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'EDIT_INTERNAL_USERS';
	
--Other Tab

--Home Page
	--33
update authorities set tabName='Other',groupingName='Home Page',level=1,sortorder= 170*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'EP_MESSAGE_VIEWER';
--Interim
	--34
update authorities set tabName='Other',groupingName='Interim',level=1,sortorder= 171*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'PERM_INTERIM_ACCESS';	
--Dashboard	
	--31																		
update authorities set tabName='Other',groupingName='Dashboard',level=1,sortorder= 172*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_DASHBOARD_MENU';
		--32
update authorities set tabName='Other',groupingName='Dashboard',
	level=2,sortorder= 173*100,
	modifieddate=now(), modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority = 'VIEW_KIDS_ERROR_MESSAGES';
	
	
	
--Permissions Delete Script	
		-- 1
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_ASSESSPROGPARTIC_VIEW';
		-- 2
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_MANAGEORGASSESSPROG_VIEW';
		-- 3
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_ORG_DELETE';
		-- 4
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_ORG_SEARCH';
		-- 5
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_ROLE_DELETE';
		-- 6
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_ROLE_SEARCH';
		-- 7
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_USER_DELETE';
		-- 8
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'DELETE_MODULES';
		-- 9
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'EDIT_MODULES';
		-- 10
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PUB_MODULE';
		-- 11
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'REL_MODULE';
		-- 12
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'STATE_ADMIN';
		-- 13
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'STATE_CEU';
		-- 14
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'UNPUB_MODULE';
		-- 15
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'UNREL_MODULE';
		-- 16
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_ADMIN';
		-- 17
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_REPORT_PERF_PARENT_VIEW';
		-- 18
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_REPORT_CREATE';
		-- 19
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_REPORT_DELETE';
		-- 20
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_REPORT_MODIFY';
		-- 21
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_REPORT_PRINT';
		-- 22
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_REPORT_PUBLISH';
		-- 23
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERSONAL_NEEDS_PROFILE_UPLOAD';
		-- 24
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_FIRST_CONTACT_UPLOAD';
		-- 25
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_ROSTERRECORD_ARCHIVE';
		-- 26
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_ROSTERRECORD_DELETE';
		-- 27
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_STUDENTRECORD_ARCHIVE';
		-- 28
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_STUDENTRECORD_DELETE';
		-- 29
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_BATCH_CREATE';
		-- 30
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_BATCH_MODIFY';
		-- 31
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_BATCH_EXPORT';
		-- 32
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_BATCH_SEARCH';
		-- 33
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_BATCH_VIEW';
		-- 34
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_FORM_CREATE';
		-- 35
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_FORM_DELETE';
		-- 36
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_FORM_MODIFY';
		-- 37
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_FORM_PUBLISH';
		-- 38
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_FORM_SEARCH';
		-- 39
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_FORM_VIEW';
		-- 40
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_STIMULUS_CREATE';
		-- 41
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_STIMULUS_DELETE';
		-- 42
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_STIMULUS_MODIFY';
		-- 43
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_STIMULUS_SEARCH';
		-- 44
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_STIMULUS_VIEW';
		-- 45
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TASK_CREATE';	
		-- 46
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TASK_DELETE';
		-- 47
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TASK_DOWNLOAD';
		-- 48
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TASK_MODIFY';
		-- 49
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TASK_PRINT';	
		-- 50
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TASK_PUBLISH';
		-- 51
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TASK_VIEW';
		-- 52
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TEST_CREATE';
		-- 53
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TEST_DELETE';	
		-- 54
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TEST_MODIFY';
		-- 55
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TEST_SEARCH';
		-- 56
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TESTSESSION_SCHEDULE';
		-- 57
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TESTTICKET_CREATE';	
		-- 58
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TESTTICKET_DELETE';
		-- 59
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TESTTICKET_DOWNLOAD';
		-- 60
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TESTTICKET_MODIFY';
		-- 61
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TESTTICKET_PRINT';	
		-- 62
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TESTTICKET_REISSUE';
		-- 63
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TESTTICKET_RESET';
		-- 64
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TESTTICKET_SEARCH';
		-- 65
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'PERM_TESTTICKET_VALIDATE';	

update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'ENROLL_MODULE';
	
update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_MODULES';

update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_PROFESSIONAL_DEVELOPMENT';

update authorities set activeflag = false ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority	= 'VIEW_GENERAL_ROSTER_REPORT';	

-- updating to camel casing.
update authorities set displayname= 'ITI - Cancel Assigned Test' where authority = 'CANCEL_ITI_ASSIGNMENT';

-- For SIF roster name changes.
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid, createduser, activeflag,modifieduser,mappedname)  
select id as fieldspecificationid,(Select id from category where categorycode='ROSTER_XML_RECORD_TYPE') recordtypeid, fs.createduser as createduser,
 fs.activeflag as activeflag, fs.modifieduser as modifieduser, 'sectionRefId' as mappedname
from fieldspecification fs, fieldspecificationsrecordtypes fst where fieldname='rosterName' and fs.id = fst.fieldspecificationid
and fst.recordtypeid = (Select id from category where categorycode='SCRS_RECORD_TYPE');



-- F605 SQL queries
update projectedtestingdate 
	set grade =(select id from gradecourse where abbreviatedname ='OTH' and contentareaid is null 
   	and activeflag is true),
   	modifieddate=now(),
	modifieduser=(select id from aartuser where username = 'cetesysadmin');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
    rejectifempty, rejectifinvalid, showerror, mappedname,createddate,createduser, activeflag, 
    modifieddate, modifieduser, iskeyvaluepairfield) 
    SELECT 'grade', '{K,1,2,3,4,5,6,7,8,9,10,11,12}', null, null, 2,
    true, true, true, 'projection_grade', now(), (select id from aartuser where username = 'cetesysadmin'), true, 
    now(), (select id from aartuser where username = 'cetesysadmin'), false
WHERE NOT EXISTS (
    SELECT 1 FROM fieldspecification WHERE mappedname = 'projection_grade'
);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, showerror, mappedname,createddate, createduser, activeflag, 
	modifieddate, modifieduser, iskeyvaluepairfield) 
	SELECT 'projectionType', '{T,S,Testing,Scoring}', null, null, 10, 
	true, true, true, 'projection_type', now(), (select id from aartuser where username = 'cetesysadmin'), true, 
	now(), (select id from aartuser where username = 'cetesysadmin'), false
	WHERE NOT EXISTS (
    SELECT 1 FROM fieldspecification WHERE mappedname = 'projection_type'); 

INSERT INTO fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname)
SELECT (select id  from fieldspecification where fieldname='grade' and mappedname='projection_grade' and activeflag=true), 
(select id from category where categoryname ='Projected Testing' and activeflag is true), now(), 
(select id from aartuser where username = 'cetesysadmin'), 
true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Grade' 
WHERE NOT EXISTS (
		SELECT 1 FROM fieldspecificationsrecordtypes WHERE fieldspecificationid = 
			(select id  from fieldspecification where fieldname='grade' and mappedname='projection_grade' and activeflag=true));


INSERT INTO fieldspecificationsrecordtypes(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser, mappedname)
SELECT (select id  from fieldspecification where fieldname='projectionType' and mappedname='projection_type' and activeflag=true), 
(select id from category where categoryname ='Projected Testing' and activeflag is true), now(), 
(select id from aartuser where username = 'cetesysadmin'), 
true, now(), (select id from aartuser where username = 'cetesysadmin'), 'Testing or Scoring'
WHERE NOT EXISTS (
    SELECT 1 FROM fieldspecificationsrecordtypes WHERE fieldspecificationid = 
    	(select id  from fieldspecification where fieldname='projectionType' and mappedname='projection_type' and activeflag=true)); 

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser,
tabName, groupingName, level, sortorder)
SELECT 'VIEW_PROJECTED_SCORING', 'View Detailed Projected Scoring', 'Test Management-Projected Testing',now(),
(select id from aartuser where email='cete@ku.edu'), true,now(),(select id from aartuser where email='cete@ku.edu'),
'Manage Tests', 'Projected Testing', 1, (60*100 +50)
WHERE NOT EXISTS (
    SELECT 1 FROM authorities WHERE authority = 'VIEW_PROJECTED_SCORING'); 

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser,
tabName, groupingName, level, sortorder) 
SELECT 'VIEW_DISTRICT_PROJECTION_SUMMARY', 'View District Projection Summary', 'Test Management-Projected Testing',now(),
(select id from aartuser where email='cete@ku.edu'), true,now(),(select id from aartuser where email='cete@ku.edu'),
'Manage Tests', 'Projected Testing', 1, (61*100 +50)
WHERE NOT EXISTS (
    SELECT 1 FROM authorities WHERE authority = 'VIEW_DISTRICT_PROJECTION_SUMMARY');  

INSERT INTO batchjobschedule (jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
SELECT 'District Summary Report', 'districtSummaryReportScheduler', 'run', '0 0 0 * * ?', TRUE, 'localhost'
WHERE NOT EXISTS (
    SELECT 1 FROM batchjobschedule WHERE jobname = 'District Summary Report');

    
-- F571 SQL queries
--Deactivating the all the districts and schools with in the Colorado-cPass State

--update organization 
--	set activeflag = false,modifieddate=now(),
--	modifieduser=(select id from aartuser where username='cetesysadmin')
--	where id in (
--		select id from organization_children
--		(
--		(select id from organization where organizationname ='Colorado-cPass' 
--		 and organizationtypeid=(select id from organizationtype where typecode ='ST'))
--		)
--	);
--
----Deactivating the Colorado-cPass State
--update organization 
--	set activeflag = false,modifieddate=now(),
--	modifieduser=(select id from aartuser where username='cetesysadmin') 
--	where organizationname='Colorado-cPass' and organizationtypeid=(select id from organizationtype where typecode ='ST');

--insering entry in orgassessmentprogram for adding CPASS to Colorado
INSERT INTO orgassessmentprogram
    (organizationid, assessmentprogramid, createddate, createduser, activeflag, modifieddate, modifieduser)
SELECT (SELECT id FROM organization WHERE organizationname ='Colorado' 
		and organizationtypeid=(select id from organizationtype where typecode ='ST')),
    	(SELECT id FROM assessmentprogram WHERE abbreviatedname ='CPASS'), now(),
    	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), true, now(), 
    	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
WHERE NOT EXISTS (
SELECT 1 FROM orgassessmentprogram WHERE organizationid=(SELECT id FROM organization WHERE organizationname ='Colorado' 
    and organizationtypeid=(SELECT id FROM organizationtype WHERE typecode ='ST'))
	and assessmentprogramid =(SELECT id FROM assessmentprogram WHERE abbreviatedname ='CPASS')
);

-- update the sort order for property.
update authorities set sortorder = (7300+7400)/2,
	modifieddate=now(),
	modifieduser=(select id from aartuser where username='cetesysadmin') 
	where authority='PERM_VIEW_NODES';

-- column name change for F605 projection type.
update fieldspecificationsrecordtypes 
set mappedname='Projection Type',
modifieduser=(select id from aartuser where username = 'cetesysadmin'),
modifieddate=now() 
where mappedname  ='Testing or Scoring'; 
