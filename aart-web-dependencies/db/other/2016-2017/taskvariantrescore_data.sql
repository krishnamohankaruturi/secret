INSERT INTO taskvariantrescore (taskvariantid,cbtaskvariantid,reason,studenttestsectionids) 
(select taskvariantid,cbtaskvariantid,'Removed duplicate values in student response data' as reason,ARRAY_AGG(studentstestsectionsid) as studenttestsectionids
from (select sr.studentstestsid,sr.studentstestsectionsid,sr.taskvariantid,tv.externalid as cbtaskvariantid,
tv.tasksubtypeid,sr.createddate,sr.modifieddate,sr.score,tv.maxscore,
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
where initial_length<>unique_length group by taskvariantid,cbtaskvariantid);