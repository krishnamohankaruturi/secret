DO $$DECLARE

STUDENTTRACKERBAND_ROW RECORD;
STUDENTTRACKER_ROW RECORD;
STUDGRADE VARCHAR;
ENROLLMENT_ROW RECORD;
ROSTER_ROW RECORD;
ORGPOOLTYPE VARCHAR;
COUNT BIGINT;
STBAND_COUNT BIGINT;
TESTCOLLECTION_ROW RECORD;
TESTGRADE VARCHAR;
GRADEFLAG VARCHAR;
GRADEMISSPOSITION BIGINT;
STUDINFO_ROW RECORD;
TESTSESSIONIDDATA BIGINT;
STDATA BIGINT[][];
STBANDDATA BIGINT[];
STUDENTTRACKERBAND_ROW1 RECORD;

BEGIN

COUNT:=0;
STBAND_COUNT:=0;

STDATA:=ARRAY[[1073956,3],
[1073956,440],
[1083594,3],
[1118568,3],
[866888,3],
[952935,440],
[1076191,440],
[854632,3],
[483107,440],
[1073224,440],
[1074363,440],
[1074363,3],
[865790,440],
[572732,440],
[1122547,3],
[1122547,440],
[1074772,440],
[1134292,440],
[1131165,440],
[1131174,3],
[1131165,3],
[1131174,440],
[965245,3],
[1075383,3],
[1131186,440],
[1131186,3],
[1131231,3],
[1075383,440],
[908983,440],
[1134292,3],
[861536,440],
[776326,440],
[957765,3],
[957765,440],
[483107,3],
[1134712,440],
[1131246,3],
[776326,3],
[1131246,440],
[858919,440],
[1134712,3],
[858919,3],
[1076072,3],
[1076072,440],
[1088536,440],
[1113439,440],
[1088536,3],
[1113439,3],
[858646,440],
[858666,3],
[858666,440],
[931262,3],
[858646,3],
[882488,3],
[882488,440],
[1131231,440],
[952935,3],
[861536,3],
[880204,3],
[880204,440],
[801344,440],
[1002478,3],
[1002478,440],
[880627,3],
[880627,440],
[756404,3],
[756404,440],
[931262,440],
[1076191,3],
[857198,3],
[1073562,3],
[1073562,440],
[1083594,440],
[955319,440],
[955319,3],
[865790,3],
[885025,440],
[1075543,440],
[885025,3],
[1075543,3],
[965245,440],
[1140709,3],
[1140709,440],
[1145897,3],
[1145898,3],
[1145897,440],
[1145898,440],
[1126602,3],
[1081908,3],
[1081908,440],
[972058,440],
[972058,3],
[1126602,440],
[917749,3],
[917746,3],
[857198,440],
[908983,3],
[855514,3],
[654310,440],
[855514,440],
[854632,440],
[1088319,3],
[1088319,440],
[1100696,3],
[1089558,3],
[1100696,440],
[1089558,440],
[1128595,440],
[1128595,3],
[866888,440],
[1074326,440],
[1074326,3],
[1133225,440],
[1133225,3],
[1039272,440],
[1039272,3],
[1075208,440],
[1075208,3],
[1118568,440]];

FOREACH STBANDDATA slice 1 IN ARRAY STDATA

LOOP

FOR STUDENTTRACKER_ROW IN 
Select * from studenttracker where activeflag=true
and studentid=STBANDDATA[1]
and contentareaid=STBANDDATA[2]

LOOP

select distinct e.currentgradelevel as gradecourseid, e.id as enrollmentid, e.currentschoolyear, e.studentid, 
e.attendanceschoolid, o.organizationname as attendanceschoolname, o.displayIdentifier as attendanceschoolidentifier,
gc.abbreviatedname as gradeCourseName, e.modifieddate, stu.legalfirstname, stu.legallastname
INTO ENROLLMENT_ROW from enrollment e 
inner join organization o on o.id=e.attendanceschoolid
inner join student stu on stu.id=e.studentid 
inner join gradecourse gc on gc.id=e.currentgradelevel
inner join enrollmentsrosters er on er.enrollmentid = e.id and er.activeflag is true
inner join roster r on r.id=er.rosterid and r.activeflag=true
where e.studentid=STUDENTTRACKER_ROW.studentid and e.activeflag is true
and e.currentschoolyear=2015
and (e.exitwithdrawaldate is null or (e.exitwithdrawaldate < e.schoolentrydate))
and e.attendanceschoolid in (select id from organization_children_oftype((select stateid from student where id=STUDENTTRACKER_ROW.studentid), 'SCH'))
and r.currentschoolyear = 2015
and r.statesubjectareaid=STUDENTTRACKER_ROW.contentareaid
order by e.modifieddate desc limit 1;

ORGPOOLTYPE:=(select pooltype from organization where id=(select stateid from student where id=STUDENTTRACKER_ROW.studentid));

IF ORGPOOLTYPE='MULTIEEOFC' AND ENROLLMENT_ROW.gradeCourseName IN ('3','4','5','6','7','8') THEN

ORGPOOLTYPE:='MULTIEEOFG';

END IF;

IF ORGPOOLTYPE='MULTIEEOFC' THEN

STUDGRADE:=(select abbreviatedname from gradecourse where id=(select r.statecourseid 
from roster r join enrollmentsrosters er on r.id = er.rosterid
left join gradecourse course on r.statecoursesid = course.id
where r.statesubjectareaid=STUDENTTRACKER_ROW.contentareaid
and r.currentschoolyear = 2015
and er.enrollmentid = ENROLLMENT_ROW.enrollmentid
and er.activeflag is true
and r.activeflag is true
and r.statecourseid is not null
and r.sourcetype='TEST'
order by er.modifieddate desc limit 1));

IF STUDGRADE IS NULL THEN

STUDGRADE:=(select abbreviatedname from gradecourse where id=(select r.statecourseid 
from roster r join enrollmentsrosters er on r.id = er.rosterid
left join gradecourse course on r.statecoursesid = course.id
where r.statesubjectareaid=STUDENTTRACKER_ROW.contentareaid
and r.currentschoolyear = 2015
and er.enrollmentid = ENROLLMENT_ROW.enrollmentid
and er.activeflag is true
and r.activeflag is true
and r.statecourseid is not null
order by er.modifieddate desc limit 1));

END IF;

ELSE

STUDGRADE:=(select abbreviatedname from gradecourse where id=ENROLLMENT_ROW.gradecourseid);

END IF;

GRADEFLAG:='';
GRADEMISSPOSITION:=0;

FOR STUDENTTRACKERBAND_ROW IN
Select * from studenttrackerband where studenttrackerid=STUDENTTRACKER_ROW.id and activeflag=true and modifieddate>='2015-03-13' order by id

LOOP 

IF GRADEFLAG<>'MISMATCH' THEN

GRADEMISSPOSITION:=GRADEMISSPOSITION+1;

END IF;

IF STUDENTTRACKERBAND_ROW.testsessionid is NOT NULL AND GRADEFLAG<>'MISMATCH' THEN

TESTSESSIONIDDATA:=STUDENTTRACKERBAND_ROW.testsessionid;

select * INTO TESTCOLLECTION_ROW from testcollection where id=(select testcollectionid from studentstests where testsessionid=STUDENTTRACKERBAND_ROW.testsessionid);

IF TESTCOLLECTION_ROW.gradebandid is NOT NULL THEN

TESTGRADE:=(select distinct abbreviatedname from gradecourse where 
id in (select gradecourseid from gradebandgradecourse where gradebandid=TESTCOLLECTION_ROW.gradebandid)
and abbreviatedname=STUDGRADE);

ELSE

IF ORGPOOLTYPE='MULTIEEOFC' THEN 

TESTGRADE:=(select abbreviatedname from gradecourse where id=TESTCOLLECTION_ROW.courseid);

ELSE 

TESTGRADE:=(select abbreviatedname from gradecourse where id=TESTCOLLECTION_ROW.gradecourseid);

END IF;

END IF;

IF STUDGRADE<>TESTGRADE THEN

GRADEFLAG:='MISMATCH';

END IF;

END IF;

END LOOP;

IF GRADEFLAG='MISMATCH' THEN

COUNT:=COUNT+1;

select std.id,std.statestudentidentifier,
(select organizationname from organization where id=e.attendanceschoolid) as school,
(select organizationname from organization where id=(select parentorganizationid from organizationrelation
where organizationid=e.attendanceschoolid)) as district,
(select organizationname from organization where id=std.stateid) as state,
au.id as teacherid, au.email INTO STUDINFO_ROW
from student std, enrollment e, testsession ts, roster r, aartuser au
where std.id=e.studentid and e.currentschoolyear=2015 and e.activeflag=true
and ts.rosterid=r.id and r.teacherid=au.id and ts.id=TESTSESSIONIDDATA
and r.activeflag=true
and std.id = STUDENTTRACKER_ROW.studentid limit 1;

RAISE NOTICE '%,%,%,%,%,%,%,%,%,%,%,%',STUDENTTRACKER_ROW.studentid,STUDENTTRACKER_ROW.id,STUDENTTRACKER_ROW.contentareaid,STUDGRADE,TESTGRADE,GRADEMISSPOSITION,STUDINFO_ROW.statestudentidentifier,STUDINFO_ROW.school,STUDINFO_ROW.district,STUDINFO_ROW.state,STUDINFO_ROW.teacherid,STUDINFO_ROW.email;

FOR STUDENTTRACKERBAND_ROW1 IN
select stb.* from studenttrackerband stb
inner join studenttracker st on st.id=stb.studenttrackerid
where stb.activeflag=true and st.activeflag=true
and st.studentid=STUDENTTRACKER_ROW.studentid
and st.contentareaid=STUDENTTRACKER_ROW.contentareaid
and stb.modifieddate>='2015-03-13'

LOOP

STBAND_COUNT:=STBAND_COUNT+1;
RAISE NOTICE 'Student Tracker Band Id, %',STUDENTTRACKERBAND_ROW1.id;

IF STUDENTTRACKERBAND_ROW1.testsessionid is not null THEN

update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid=STUDENTTRACKERBAND_ROW1.testsessionid);
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid=STUDENTTRACKERBAND_ROW1.testsessionid;
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id=STUDENTTRACKERBAND_ROW1.testsessionid;

END IF;

update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id=STUDENTTRACKERBAND_ROW1.id;
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where id=STUDENTTRACKERBAND_ROW1.studenttrackerid;

END LOOP;

END IF;

END LOOP;

END LOOP;

RAISE NOTICE 'Total Student Tracker Count, %',COUNT;
RAISE NOTICE 'Total Student Tracker Band Count, %',STBAND_COUNT;

END$$;