-- DE13049: EP Prod - No KAP tests created with valid TEST record after valid Clear record.
-- Activating the enrollmenttesttypesubjectarea KAP tests for student 8561068507

update enrollmenttesttypesubjectarea set activeflag = true, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
    where enrollmentid = (select en.id from enrollment en join student st on st.id = en.studentid where st.statestudentidentifier = '8561068507' and st.stateid = 51 and en.currentschoolyear =2016 and en.activeflag is true)
    and testtypeid = (select id from testtype where testtypecode = '2' and assessmentid = 26);

-- Records going to update : 3
    