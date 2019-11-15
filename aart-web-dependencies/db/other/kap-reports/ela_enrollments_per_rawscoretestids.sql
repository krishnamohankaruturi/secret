--ela_enrollments_per_rawscoretestids.sql


select count(studentid) as ela_count from 
(SELECT distinct st.id AS studentid, st.legalfirstname as studentfirstname, st.legallastname as studentlastname,
ts.gradecourseid, tc.contentareaid,
st.stateid,
st.statestudentidentifier,
gc.name as gradename,
ca.name as contentareaname, ca.abbreviatedname as contentareacode,
2016 as currentschoolyear,
2 as assessmentprogramid
FROM student st
JOIN studentstests sts ON sts.studentid = st.id
JOIN enrollment en ON en.studentid = st.id and sts.enrollmentid=en.id
join enrollmenttesttypesubjectarea etsa on etsa.enrollmentid = en.id and etsa.activeflag is true
join testtype tt on etsa.testtypeid = tt.id and tt.activeflag is true
join organization org on org.id = en.aypschoolid
JOIN testsession ts ON ts.id = sts.testsessionid
JOIN testcollection tc ON tc.id = ts.testcollectionid
JOIN test t ON t.id = sts.testid
join gradecourse gc on gc.id = ts.gradecourseid
join contentarea ca on ca.id = tc.contentareaid
WHERE st.stateid = 51  and tt.assessmentid = 26
AND (en.activeflag OR ((NOT en.activeflag) AND (en.exitwithdrawaltype IS NOT NULL)))
AND en.currentschoolyear = 2016
AND (ts.source = ANY(ARRAY['BATCHAUTO','QUESTARPROCESS']) AND ts.activeflag = TRUE)
--Enrollments 
AND (sts.status = ANY(ARRAY [84,85,86,465]) AND sts.activeflag)
--Enrollments used for calculations (Tests with tatus 84, 465 
--AND (sts.status = ANY(ARRAY [85,86]) AND sts.activeflag)
AND org.reportprocess = true


--ELA G3
--AND ts.gradecourseid = 133 AND tc.contentareaid = 3 AND t.externalid = ANY(ARRAY [14774,14756,13400,14780,14763,14749,14765,14754,14775,14777,14778,14769,14781,14779,14751,14772,14776])  
--ELA G4
AND ts.gradecourseid = 132 AND tc.contentareaid = 3 AND t.externalid = ANY(ARRAY [14554,14759,14758,14764,14768,14770,14766,14761,14755,14767,14750,14752,14757,14753,14762])  
--ELA G5
--AND ts.gradecourseid = 131 AND tc.contentareaid = 3 AND t.externalid = ANY(ARRAY [14791,14794,14785,14790,14555,14789,14792,14795,14787,14783,14788,14786,14782,14793])  
--ELA G6
--AND ts.gradecourseid = 130 AND tc.contentareaid = 3 AND t.externalid = ANY(ARRAY [14807,14801,14556,14773,14797,14800,14803,14808,14802,14805,14806,14796,14799,14771,14798,14804])  
--ELA G7
--AND ts.gradecourseid = 129 AND tc.contentareaid = 3 AND t.externalid = ANY(ARRAY [14810,14815,14816,14814,14818,14813,14817,14812,14557,14811])  
--ELA G8
--AND ts.gradecourseid = 128 AND tc.contentareaid = 3 AND t.externalid = ANY(ARRAY [14823,14837,14558,14840,14841,14842,14828,14829,14822,14835,14843,14839,14836,14838])  
--ELA G10
--AND ts.gradecourseid = 126 AND tc.contentareaid = 3 AND t.externalid = ANY(ARRAY [14821,14832,14819,14830,14825,14833,14831,14827,14820,14824,14834,14826])  

) studentList 
