-- Changing the enrollmenttesttypesubjectarea duplicate records created by previous script(DE13063-ClearRecordNotRemovingTestTypeFromStudentsTestRecord.sql). 
update enrollmenttesttypesubjectarea set activeflag = false, testtypeid = (select id from testtype where testtypecode = 'C'), modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now()
where id in (select distinct ettsa.id from student stu
    join enrollment en on en.studentid = stu.id
    join enrollmenttesttypesubjectarea ettsa on ettsa.enrollmentid = en.id   
    where stu.stateid = (select id from organization where displayidentifier = 'AK') and stu.activeflag is true
    and en.activeflag is true and en.currentschoolyear = 2016
    and (select count(*) from enrollmenttesttypesubjectarea where enrollmentid = ettsa.enrollmentid and testtypeid = ettsa.testtypeid and subjectareaid = ettsa.subjectareaid) = 2          
    and ettsa.testtypeid in (select id from testtype where testtypecode = 'GN') and ettsa.activeflag is true)
    and modifieduser is null and modifieddate >= '2016-03-22';
    
-- Records going to update : 210

    
update enrollmenttesttypesubjectarea set modifieduser = (select id from aartuser where username = 'cetesysadmin')
        where modifieduser is null;

-- Records going to update: 5        
    