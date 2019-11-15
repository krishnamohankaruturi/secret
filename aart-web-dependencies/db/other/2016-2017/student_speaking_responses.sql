select * from (
select std.id as studentid,std.statestudentidentifier,sts.testsectionid,sts.id as studentstestsectionsid,tv.id as taskvariantid,tv.externalid as cbtaskvariantid,tstv.taskvariantposition,sr.response,st.status,
(array(select csi.score from scoringassignmentstudent sas inner join ccqscore cs on cs.scoringassignmentstudentid = sas.id inner join ccqscoreitem csi on csi.ccqscoreid = cs.id and csi.taskvariantid = tv.id)) as studentscore
from testsectionstaskvariants tstv
inner join studentstestsections sts on sts.testsectionid=tstv.testsectionid
inner join taskvariant tv on tv.id = tstv.taskvariantid
inner join studentstests st on st.id = sts.studentstestid
inner join student std on std.id = st.studentid
left join studentsresponses sr on sr.studentstestsectionsid = sts.id and sr.taskvariantid = tv.id
where tstv.testsectionid=87974 and tstv.taskvariantid in (439353,439352,439351,439339,439338,439337) and st.status in (85,86)
union
select std.id as studentid,std.statestudentidentifier,sts.testsectionid,sts.id as studentstestsectionsid,tv.id as taskvariantid,tv.externalid as cbtaskvariantid,tstv.taskvariantposition,sr.response,st.status,
(array(select csi.score from scoringassignmentstudent sas inner join ccqscore cs on cs.scoringassignmentstudentid = sas.id inner join ccqscoreitem csi on csi.ccqscoreid = cs.id and csi.taskvariantid = tv.id)) as studentscore
from testsectionstaskvariants tstv
inner join studentstestsections sts on sts.testsectionid=tstv.testsectionid
inner join taskvariant tv on tv.id = tstv.taskvariantid
inner join studentstests st on st.id = sts.studentstestid
inner join student std on std.id = st.studentid
left join studentsresponses sr on sr.studentstestsectionsid = sts.id and sr.taskvariantid = tv.id
where tstv.testsectionid=87975 and tstv.taskvariantid in (439367,439366,439359,439358,439357,439356) and st.status in (85,86)
union
select std.id as studentid,std.statestudentidentifier,sts.testsectionid,sts.id as studentstestsectionsid,tv.id as taskvariantid,tv.externalid as cbtaskvariantid,tstv.taskvariantposition,sr.response,st.status,
(array(select csi.score from scoringassignmentstudent sas inner join ccqscore cs on cs.scoringassignmentstudentid = sas.id inner join ccqscoreitem csi on csi.ccqscoreid = cs.id and csi.taskvariantid = tv.id)) as studentscore
from testsectionstaskvariants tstv
inner join studentstestsections sts on sts.testsectionid=tstv.testsectionid
inner join taskvariant tv on tv.id = tstv.taskvariantid
inner join studentstests st on st.id = sts.studentstestid
inner join student std on std.id = st.studentid
left join studentsresponses sr on sr.studentstestsectionsid = sts.id and sr.taskvariantid = tv.id
where tstv.testsectionid=87977 and tstv.taskvariantid in (439382,439381,439378,439377,439374,439373,439372,439371) and st.status in (85,86)
) as temp order by studentstestsectionsid,taskvariantposition,taskvariantid;
