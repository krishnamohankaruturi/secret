--math_enrollments_per_rawscoretestids.sql

select count(studentid) as math_count from 
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


--Math G3: 
AND ts.gradecourseid = 170 AND tc.contentareaid = 440 AND t.externalid = ANY(ARRAY [14688,14683,14685,12690,14687,14691,14680,14689,14682,14690]) 
--Math G4:
--AND ts.gradecourseid = 169 AND tc.contentareaid = 440 AND t.externalid = ANY(ARRAY [14698,15047,14696,15052,14692,12691,15048,15051,15050,14694,14700,14697,14699,14695])
--Math G5: 
--AND ts.gradecourseid = 152 AND tc.contentareaid = 440 AND t.externalid = ANY(ARRAY [12686,14708,14704,14705,14707,14710,14702,14706,14709,14703])
--Math G6 : 
--AND ts.gradecourseid = 151 AND tc.contentareaid = 440 AND t.externalid = ANY(ARRAY [14716,14711,14717,14712,14718,14719,14714,14713,14715,12688])
--Math G7: 
--AND ts.gradecourseid = 150 AND tc.contentareaid = 440 AND t.externalid = ANY(ARRAY [14617,14614,14609,14602,14613,14612,12687,14607,14636,14616])
--Math G8: 
--AND ts.gradecourseid = 149 AND tc.contentareaid = 440 AND t.externalid = ANY(ARRAY [14725,14724,12689,14727,14721,14722,14720,14723,14728,14726)
--Math G10: 
--AND ts.gradecourseid = 147 AND tc.contentareaid = 440 --AND t.externalid = ANY(ARRAY [14736,14733,14734,14730,14732,14738,14737,14731])

) studentList 
