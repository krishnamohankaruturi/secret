--Get Tests per studentid and enrollmentid 
SELECT DISTINCT sts.studentid AS studentid,sts.testid AS testid,t.externalid AS testexternalid,sts.id AS studentstestsid,tv.externalid AS taskvariantexternalid,tv.id AS taskvariantid,
str.score,spc.specialcircumstanceid,stg.code as stagecode, stg.sortorder,sts.status as studentstestsstatus, stsec.statusid as sectionstatus,tsec.sectionorder,str.studentstestsectionsid, tv.scoringneeded, sts.enrollmentid,
(case when sth.acteddate is null then sts.modifieddate else sth.acteddate end) as datestudentstestsmodified
FROM studentstests sts  
JOIN testsession ts ON ts.id = sts.testsessionid
join testcollection tc on tc.id = ts.testcollectionid
JOIN test t ON t.id = sts.testid
JOIN test t ON t.id = sts.testid
join studentstestsections stsec on stsec.studentstestid = sts.id
left JOIN studentsresponses str ON str.studentstestsid = sts.id and str.studentstestsectionsid = stsec.id
left JOIN taskvariant tv ON tv.id = str.taskvariantid
LEFT JOIN testsectionstaskvariants tstv on tstv.taskvariantid = tv.id
LEFT JOIN testsection tsec on tsec.id = tstv.testsectionid 
left join studentspecialcircumstance spc on spc.studenttestid = sts.id AND spc.activeflag is true AND spc.status = ANY(ARRAY [548, 549])
left join studentstestshistory sth on sts.id = sth.studentstestsid and sth.action = 'END TEST SESSION'
WHERE sts.activeflag = TRUE
AND sts.status = ANY(ARRAY [84,85,86, 465]) 
AND ts.activeflag = TRUE
AND ts.source = ANY(ARRAY['BATCHAUTO','QUESTARPROCESS'])
and t.externalid = ANY(Array [14688,14683,14685,12690,14687,14691,14680,14689,14682,14690])
AND sts.enrollmentid = ANY(ARRAY [2055492])
AND sts.studentid = 1159675 