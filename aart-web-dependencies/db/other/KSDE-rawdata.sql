copy (
  select distinct stu.statestudentidentifier,st.studentid,CASE WHEN ca.abbreviatedname='M' then '1' else '0' end as KSDEFileCode, 
    ca.abbreviatedname as subject, count(sr.studentstestsid) as responsecount,
    array_to_string(array_agg(distinct s.code),'~') as stages,
    array_to_string(array_agg(distinct st.id),'~') as studenttestid, array_to_string(array_agg(distinct st.testid),'~') as testid, 
    array_to_string(array_agg(distinct st.status),'~') as status, 
    t.attendanceschoolid as schoolid,org.organizationname as schoolname,
    (select distinct rawscore from studentreport srpt where srpt.studentid=st.studentid and srpt.contentareaid=ca.id and srpt.enrollmentid=st.enrollmentid) as rawscore,
    (select distinct scalescore from studentreport srpt where srpt.studentid=st.studentid and srpt.contentareaid=ca.id and srpt.enrollmentid=st.enrollmentid) as scalescore
  from studentstests st
    inner join student stu on st.studentid=stu.id
    inner join testsession t on t.id = st.testsessionid and t.createddate>='2015-03-01'
    inner join testcollection tc on t.testcollectionid=tc.id
    inner join stage s on tc.stageid=s.id
    inner join organization org on org.id = t.attendanceschoolid
    inner join contentarea ca on ca.id=tc.contentareaid
    left outer join studentsresponses sr on sr.studentstestsid = st.id
  where s.code = ANY(ARRAY['Stg1','Stg2']) and ca.abbreviatedname = ANY(ARRAY['M','ELA']) 
    and st.activeflag is true
    and st.studentid = ANY(select distinct sti.studentid from studentstests sti inner join lcstemp lt on lt.studentstestsid = sti.id) 
  group by stu.statestudentidentifier,st.studentid,t.attendanceschoolid,org.organizationname,ca.id,ca.abbreviatedname,st.enrollmentid
  order by stu.statestudentidentifier,t.attendanceschoolid,responsecount
) TO '/srv/pg_data/postgres/ksde-rawdata.csv' DELIMITER ',' CSV HEADER;
