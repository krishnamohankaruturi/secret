-- BEFORE AFTER Count for all DELETE tables
-- If delete table has pk by id then count(distinct id) or count(*)
-- Run time in prod 5 min in 2017
create temp table tmp_countbyyear(
table_name character varying,schoolyear integer,count integer);

insert into tmp_countbyyear
select 'ccqscoreitem'::character varying table_name,ts.schoolyear,count(distinct ccqi.id)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join scoringassignmentstudent sas on sas.studentstestsid=st.id
inner join ccqscore ccq on ccq.scoringassignmentstudentid=sas.id
inner join ccqscoreitem ccqi on ccqi.ccqscoreid=ccq.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'ccqscore'::character varying table_name,ts.schoolyear,count(distinct ccq.id)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join scoringassignmentstudent sas on sas.studentstestsid=st.id
inner join ccqscore ccq on ccq.scoringassignmentstudentid=sas.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'scoringassignmentstudent'::character varying table_name,ts.schoolyear,count(distinct sas.id)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join scoringassignmentstudent sas on sas.studentstestsid=st.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'scoringassignmentscorer'::character varying table_name,ts.schoolyear,count(distinct sas.id)
from testsession ts 
inner join scoringassignment sa on sa.testsessionid=ts.id
inner join scoringassignmentscorer sas on sas.scoringassignmentid=sa.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'scoringassignment'::character varying table_name,ts.schoolyear,count(distinct sa.id)
from testsession ts 
inner join scoringassignment sa on sa.testsessionid=ts.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'studentsresponses'::character varying table_name,ts.schoolyear,count(*)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join studentstestsections sts on sts.studentstestid=st.id
inner join studentsresponses sr on sr.studentstestsectionsid=sts.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'studentsresponsescopy'::character varying table_name,ts.schoolyear,count(*)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join studentstestsections sts on sts.studentstestid=st.id
inner join studentsresponsescopy sr on sr.studentstestsectionsid=sts.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'studentstestsectionstasksfoils'::character varying table_name,ts.schoolyear,count(*)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join studentstestsections sts on sts.studentstestid=st.id
inner join studentstestsectionstasksfoils stsf on stsf.studentstestsectionsid=sts.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'studentstests'::character varying table_name,ts.schoolyear,count(distinct sts.id)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join studentstestsections sts on sts.studentstestid=st.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'studentsresponseparameters'::character varying table_name,ts.schoolyear,count(distinct stsp.id)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join studentstestsections sts on sts.studentstestid=st.id
inner join studentsresponseparameters stsp on stsp.studentstestsectionsid=sts.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'exitwithoutsavetest'::character varying table_name,ts.schoolyear,count(*)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join studentstestsections sts on sts.studentstestid=st.id
inner join exitwithoutsavetest ews on ews.studenttestsectionid=sts.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'studentstestsections'::character varying table_name,ts.schoolyear,count(distinct sts.id)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join studentstestsections sts on sts.studentstestid=st.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'studentadaptivetestthetastatus'::character varying table_name,ts.schoolyear,count(*)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join studentadaptivetestthetastatus sts on sts.studentstestid=st.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'studentadaptivetestfinaltheta'::character varying table_name,ts.schoolyear,count(*)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join studentadaptivetestfinaltheta sts on sts.studentstestid=st.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'studentsadaptivetestsections'::character varying table_name,ts.schoolyear,count(*)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join studentsadaptivetestsections sts on sts.studentstestid=st.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'studentspecialcircumstance'::character varying table_name,ts.schoolyear,count(*)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join studentspecialcircumstance sts on sts.studenttestid=st.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'studentstestshighlighterindex'::character varying table_name,ts.schoolyear,count(*)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join studentstestshighlighterindex sts on sts.studenttestid=st.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'studentstestshistory'::character varying table_name,ts.schoolyear,count(distinct sts.id)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join studentstestshistory sts on sts.studentstestsid=st.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'studentsteststags'::character varying table_name,ts.schoolyear,count(*)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join studentsteststags sts on sts.studenttestid=st.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'studentstests'::character varying table_name,ts.schoolyear,count(st.id)
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'studenttrackerband'::character varying table_name,ts.schoolyear,count(distinct st.id)
from testsession ts 
inner join studenttrackerband st on st.testsessionid=ts.id
group by ts.schoolyear;
insert into tmp_countbyyear
select 'testsession'::character varying table_name,ts.schoolyear,count(ts.id)
from testsession ts 
group by ts.schoolyear;
insert into tmp_countbyyear
select 'enrollmenttesttypesubjectarea'::character varying table_name,currentschoolyear schoolyear, count(distinct ets.id) from enrollment er
inner join enrollmenttesttypesubjectarea ets on ets.enrollmentid=er.id 
inner join subjectarea sj on sj.id =ets.subjectareaid
inner join testtype tt on tt.id =ets.testtypeid
group by currentschoolyear;
insert into tmp_countbyyear
select 'enrollmentsrosters'::character varying table_name,currentschoolyear schoolyear,count(distinct er.id) count from enrollment e
inner join enrollmentsrosters er on er.enrollmentid=e.id
group by currentschoolyear;
insert into tmp_countbyyear
select 'enrollment'::character varying table_name,currentschoolyear schoolyear,count(*) count from enrollment
group by currentschoolyear;
insert into tmp_countbyyear
select 'ititestsessionhistory'::character varying table_name,currentschoolyear schoolyear,count(distinct iti.id) count from roster r
inner join ititestsessionhistory iti on iti.rosterid=r.id
group by currentschoolyear;
insert into tmp_countbyyear
select 'roster'::character varying table_name,currentschoolyear schoolyear,count(*) count from roster
group by currentschoolyear;

select * from tmp_countbyyear;