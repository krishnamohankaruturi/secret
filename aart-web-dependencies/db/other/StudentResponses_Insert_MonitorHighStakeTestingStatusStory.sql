


-- Used for userstory - US10734 : Monitor High Stake Testing Status
INSERT INTO studentsresponses(
            studentid, testid, taskvariantid, foilid, response, studentstestsid, 
            createddate, createduser, activeflag, modifieddate, modifieduser) (select distinct st.studentid,st.testid,tstvs.taskvariantid,
(select foilid from taskvariantsfoils tvf where tvf.taskvariantid = tstvs.taskvariantid limit 1) as foilid, 'Mahesh ' || tstvs.taskvariantid as response, st.id as studentstestsid,
now() as createdDate,
123 as createdUser,
true as activeFlag,
now() as modifiedDate,
123 as modifiedUser
from
studentstests st, testsectionstaskvariants tstvs , testsection ts
where
st.testid=ts.testid and
tstvs.testsectionid = ts.id)

--node id update for dev.

update taskvariant set nodeid=17790+id;