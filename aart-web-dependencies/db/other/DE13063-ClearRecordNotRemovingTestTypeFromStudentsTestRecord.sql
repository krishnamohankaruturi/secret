-- DE13063: Clear record not removing test type from students' test record.
-- Changing testtype from 2 to GN for AMP students
update enrollmenttesttypesubjectarea set testtypeid = (select id from testtype where testtypecode = 'GN'), modifieduser = (select id from aartuser where uniquecommonidentifier = 'cetesysadmin'), modifieddate = now()
 where id in (select distinct ettsa.id from student stu
    join enrollment en on en.studentid = stu.id
    join enrollmenttesttypesubjectarea ettsa on ettsa.enrollmentid = en.id
    where stu.stateid = (select id from organization where displayidentifier = 'AK') and stu.activeflag is true
    and en.activeflag is true and en.currentschoolyear = 2016
    and stu.id not in (969558,969591)     
    and ettsa.testtypeid in (select id from testtype where testtypecode = '2') and ettsa.activeflag is true);

-- Records going to update 211.


-- Changing testtype 2 to Paper/Pencil testtype for DLM students.
update enrollmenttesttypesubjectarea set testtypeid = (select tt.id from testtype tt join assessment asmnt on asmnt.id = tt.assessmentid join testingprogram tp on tp.id = asmnt.testingprogramid 
                 join assessmentprogram asp on asp.id = tp.assessmentprogramid where tt.testtypecode = 'P' and asp.abbreviatedname = 'AMP'), 
        modifieduser = (select id from aartuser where uniquecommonidentifier = 'cetesysadmin'), modifieddate = now()
 where id in (select distinct ettsa.id from student stu
    join enrollment en on en.studentid = stu.id
    join enrollmenttesttypesubjectarea ettsa on ettsa.enrollmentid = en.id
    where stu.stateid = (select id from organization where displayidentifier = 'AK') and stu.activeflag is true
    and en.activeflag is true and en.currentschoolyear = 2016
    and stu.id in (969558,969591)        
    and ettsa.testtypeid in (select id from testtype where testtypecode = '2') and ettsa.activeflag is true);

-- Records going to update 4.