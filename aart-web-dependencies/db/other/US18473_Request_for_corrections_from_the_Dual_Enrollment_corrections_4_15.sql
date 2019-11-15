--student: 7512493568
--Inactivate school 1810
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18473'
  where id = 1823906;

--activate school 9898
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18473'
  where id = 2371720;

--Math
--stg1
UPDATE studentstests set enrollmentid = 2371720,testsessionid = 2258029, modifieduser = 12, modifieddate = now()
      where id = 10135092;

--stg2
UPDATE studentstests set enrollmentid = 2371720,testsessionid = 2772178, modifieduser = 12, modifieddate = now()
      where id = 12924501;
--prfrm
UPDATE studentstests set enrollmentid = 2371720,testsessionid = 2183112, modifieduser = 12, modifieddate = now()
      where id = 9235553;
--stg3
UPDATE studentstests set enrollmentid = 2371720,testsessionid = 2783415, modifieduser = 12, modifieddate = now()
      where id = 12981243;

--stg4
UPDATE studentstests set enrollmentid = 2371720,testsessionid = 2258028, modifieduser = 12, modifieddate = now()
      where id = 13230416;

--ELA
--stg1
UPDATE studentstests set enrollmentid = 2371720,testsessionid = 2258333, modifieduser = 12, modifieddate = now()
      where id = 10161623;

--stg2
UPDATE studentstests set enrollmentid = 2371720,testsessionid = 2618066, modifieduser = 12, modifieddate = now()
      where id = 12604057;
--prfrm
UPDATE studentstests set enrollmentid = 2371720,testsessionid = 2179308, modifieduser = 12, modifieddate = now()
      where id = 9235984;
--stg3
UPDATE studentstests set enrollmentid = 2371720,testsessionid = 2619095, modifieduser = 12, modifieddate = now()
      where id = 12715494;

--stg4
UPDATE studentstests set enrollmentid = 2371720,testsessionid = 2258332, modifieduser = 12, modifieddate = now()
      where id = 12540945;