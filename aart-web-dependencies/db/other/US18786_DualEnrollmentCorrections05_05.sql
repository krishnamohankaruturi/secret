-- Student : 8960420077

--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'inactivated according to US18786'
  where id = 2287346;

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18786'
  where id = 2271095;

--Math
--stg1
UPDATE studentstests set enrollmentid = 2271095,testsessionid = 2249023, modifieduser = 12, modifieddate = now()
      where id = 10077272;

--stg2
UPDATE studentstests set enrollmentid = 2271095,testsessionid = 2511097, modifieduser = 12, modifieddate = now()
      where id = 13984721;

--prfrm
UPDATE studentstests set enrollmentid = 2271095,testsessionid = 2175339, modifieduser = 12, modifieddate = now()
      where id = 8813458;

--stg3
UPDATE studentstests set enrollmentid = 2271095,testsessionid = 2532618, modifieduser = 12, modifieddate = now()
      where id = 14032653;

--stg4
UPDATE studentstests set enrollmentid = 2271095,testsessionid = 2249021, modifieduser = 12, modifieddate = now()
      where id = 13229775;

--ELA
--stg1
UPDATE studentstests set enrollmentid = 2271095,testsessionid = 2261557, modifieduser = 12, modifieddate = now()
      where id = 10442010;

--prfrm
UPDATE studentstests set enrollmentid = 2271095,testsessionid = 2175341, modifieduser = 12, modifieddate = now()
      where id = 8869112;

--stg4
UPDATE studentstests set enrollmentid = 2271095,testsessionid = 2261556, modifieduser = 12, modifieddate = now()
      where id = 13230034;

