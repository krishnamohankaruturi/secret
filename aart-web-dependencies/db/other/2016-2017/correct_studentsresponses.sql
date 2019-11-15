DO $$DECLARE

STUDENTTESTSECTION_ROW RECORD;

BEGIN

                                
FOR STUDENTTESTSECTION_ROW IN
(select studentstestsectionsid,taskvariantid,response from (select sr.studentstestsid,sr.studentstestsectionsid,sr.taskvariantid,tv.externalid as cbtaskvariantid,
tv.tasksubtypeid,sr.createddate,sr.modifieddate,sr.score,tv.maxscore,sr.response,
(select count(*) from (select unnest(string_to_array((substr(response,3,length(response)-4)),'},{'))
from studentsresponses where studentstestsectionsid=sr.studentstestsectionsid and taskvariantid=sr.taskvariantid and length(response)>4) as s1) as initial_length,
(select count(*) from (select distinct unnest(string_to_array((substr(response,3,length(response)-4)),'},{'))
from studentsresponses where studentstestsectionsid=sr.studentstestsectionsid and taskvariantid=sr.taskvariantid and length(response)>4) as s1) as unique_length
from studentstests st
INNER JOIN studentstestsections sts on sts.studentstestid = st.id
INNER JOIN testsectionstaskvariants tstv on tstv.testsectionid = sts.testsectionid
INNER JOIN taskvariant tv on tv.id = tstv.taskvariantid
INNER JOIN studentsresponses sr on sr.studentstestsectionsid = sts.id and sr.taskvariantid = tv.id
INNER JOIN tasktype tt on tt.id = tv.tasktypeid
INNER JOIN student s on (st.studentid = s.id)
INNER JOIN enrollment e on (s.id = e.studentid)
where tt.code='ITP' and e.currentschoolyear=2017 and e.activeflag is true and st.activeflag is true) as temp
where initial_length<>unique_length)

LOOP
RAISE NOTICE 'UPDATE STUDENT RESPONSE,%,%,%',STUDENTTESTSECTION_ROW.response,STUDENTTESTSECTION_ROW.studentstestsectionsid,STUDENTTESTSECTION_ROW.taskvariantid;
UPDATE studentsresponses set response=('[{'||array_to_string(ARRAY(select distinct unnest(string_to_array((substr(response,3,length(response)-4)),'},{')) 
from studentsresponses where studentstestsectionsid=STUDENTTESTSECTION_ROW.studentstestsectionsid and taskvariantid=STUDENTTESTSECTION_ROW.taskvariantid),'},{')||'}]'),modifieddate=now() 
where studentstestsectionsid=STUDENTTESTSECTION_ROW.studentstestsectionsid and taskvariantid=STUDENTTESTSECTION_ROW.taskvariantid;

END LOOP;

END$$;


-- issue correctd for long run 
/*
select  distinct sts.id studentstestsectionsid, tv.id taskvariantid,tv.externalid,maxscore,tasksubtypeid
into temp tmp_sr
from studentstests st
INNER JOIN studentstestsections sts on sts.studentstestid = st.id
INNER JOIN testsectionstaskvariants tstv on tstv.testsectionid = sts.testsectionid
INNER JOIN taskvariant tv on tv.id = tstv.taskvariantid
INNER JOIN tasktype tt on tt.id = tv.tasktypeid
INNER JOIN student s on (st.studentid = s.id)
INNER JOIN enrollment e on (s.id = e.studentid)
where tt.code='ITP' and e.currentschoolyear=2018 and e.activeflag is true and st.activeflag is true; 

select sr.studentstestsid,sr.studentstestsectionsid,sr.taskvariantid,tv.externalid as cbtaskvariantid,
tv.tasksubtypeid,sr.createddate,sr.modifieddate,sr.score,tv.maxscore,sr.response
into temp tmp_sr_res
from tmp_sr tv 
INNER JOIN studentsresponses sr on sr.studentstestsectionsid = tv.studentstestsectionsid and sr.taskvariantid = tv.taskvariantid;

select * into temp tmp_final_new  from (
select sr.studentstestsid,sr.studentstestsectionsid,sr.taskvariantid,sr.cbtaskvariantid as cbtaskvariantid,
sr.tasksubtypeid,sr.createddate,sr.modifieddate,sr.score,sr.maxscore,sr.response,
(select count(*) from (select unnest(string_to_array((substr(response,3,length(response)-4)),'},{'))
from studentsresponses where studentstestsectionsid=sr.studentstestsectionsid and taskvariantid=sr.taskvariantid and length(response)>4) as s1) as initial_length,
(select count(*) from (select distinct unnest(string_to_array((substr(response,3,length(response)-4)),'},{'))
from studentsresponses where studentstestsectionsid=sr.studentstestsectionsid and taskvariantid=sr.taskvariantid and length(response)>4) as s1) as unique_length
from tmp_sr_res sr
) as tem
where initial_length<>unique_length;



begin;
INSERT INTO taskvariantrescore (taskvariantid,cbtaskvariantid,reason,studenttestsectionids)
select taskvariantid,cbtaskvariantid,'Removed duplicate values in student response data',ARRAY_AGG(studentstestsectionsid) from tmp_final_new
group by taskvariantid,cbtaskvariantid;

UPDATE studentsresponses sr
set response=('[{'||array_to_string(ARRAY(select distinct unnest(string_to_array((substr(response,3,length(response)-4)),'},{'))  from studentsresponses where studentstestsectionsid=tmp.studentstestsectionsid and taskvariantid=tmp.taskvariantid),'},{')||'}]')
,modifieddate=now() 
from tmp_final_new tmp
where sr.studentstestsectionsid=tmp.studentstestsectionsid and sr.taskvariantid=tmp.taskvariantid;

commit;
*/

