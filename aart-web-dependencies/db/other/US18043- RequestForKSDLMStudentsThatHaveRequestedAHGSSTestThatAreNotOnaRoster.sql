-- US18403: EP: Prod - Request for KS DLM Students that have requested a HGSS test that are not on a roster
select count(students.*) from (select distinct stu.id from student stu
    join enrollment en on en.studentid = stu.id
    join enrollmenttesttypesubjectarea ettsa on ettsa.enrollmentid = en.id
    join studentassessmentprogram sap on sap.studentid = stu.id 
    where stu.stateid = 51 and en.currentschoolyear = 2016 and  ettsa.subjectareaid = 20 and ettsa.testtypeid in (select id from testtype where testtypecode = '3') 
    and sap.assessmentprogramid = 3 and sap.activeflag is true and en.activeflag is true and ettsa.activeflag is true and stu.activeflag is true
    EXCEPT
    SELECT distinct stu.id from student stu
    join enrollment en on en.studentid = stu.id
    join enrollmenttesttypesubjectarea ettsa on ettsa.enrollmentid = en.id
    join enrollmentsrosters er on er.enrollmentid = ettsa.enrollmentid
    join roster r  on r.id = er.rosterid    
    join studentassessmentprogram sap on sap.studentid = stu.id
    where stu.stateid = 51 and en.currentschoolyear = 2016 and ettsa.subjectareaid = 20 and ettsa.testtypeid in (select id from testtype where testtypecode = '3') 
    and r.statesubjectareaid = 443
    and sap.assessmentprogramid = 3 and sap.assessmentprogramid = 3 and en.activeflag is true and ettsa.activeflag is true
    and stu.activeflag is true and sap.activeflag is true and er.activeflag is true and r.activeflag is true
    ) as students;

