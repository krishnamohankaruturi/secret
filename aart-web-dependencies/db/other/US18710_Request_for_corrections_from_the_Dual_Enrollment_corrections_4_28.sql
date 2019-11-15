--student: 5630363174
--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18710'
  where id = 2280047;

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18710'
  where id = 2364307;

--ELA
--prfrm
UPDATE studentstests set enrollmentid = 2364307,testsessionid = 2177000, modifieduser = 12, modifieddate = now()
      where id = 9156862;