--MATH
with gradeids as (select id from gradecourse where abbreviatedname = (select abbreviatedname from gradecourse where id = 147))
select DISTINCT TESTID1 from rawtoscalescores 
where TESTID1 is not null 
and assessmentprogramid = 12
and subjectid = 440
and gradeid = any(array(select id from gradeids))
and schoolyear = 2016
UNION
select DISTINCT TESTID2 from rawtoscalescores 
where TESTID2 is not null
and assessmentprogramid = 12
and subjectid = 440
and gradeid = any(array(select id from gradeids))
and schoolyear = 2016
UNION
select DISTINCT TESTID3 from rawtoscalescores 
where TESTID3 is not null
and assessmentprogramid = 12
and subjectid = 440
and gradeid = any(array(select id from gradeids))
and schoolyear = 2016
UNION
select DISTINCT TESTID4 from rawtoscalescores 
where TESTID4 is not null
and assessmentprogramid = 12
and subjectid = 440
and gradeid = any(array(select id from gradeids))
and schoolyear = 2016
UNION
select DISTINCT performance_testid from rawtoscalescores 
where performance_testid is not null
and assessmentprogramid = 12
and subjectid = 440
and gradeid = any(array(select id from gradeids))
and schoolyear = 2016



--Gradeid Grade
--170  3
--169  4
--152  5
--151  6
--150  7
--149  8
--137  10