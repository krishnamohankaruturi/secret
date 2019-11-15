-- Student : 6501775299

--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'inactivated according to US18856'
  where id = 2140035;

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18856'
  where id = 1930986;

--Math
--stg1
UPDATE studentstests set enrollmentid = 1930986,testsessionid = 2252642, modifieduser = 12, modifieddate = now()
      where id = 9849343;

--stg2
UPDATE studentstests set enrollmentid = 1930986,testsessionid = 3360099, modifieduser = 12, modifieddate = now()
      where id = 13948559;

--prfrm
UPDATE studentstests set enrollmentid = 1930986,testsessionid = 2181667 , modifieduser = 12, modifieddate = now()
      where id = 8908977;

--stg3
UPDATE studentstests set enrollmentid = 1930986,testsessionid = 3453527, modifieduser = 12, modifieddate = now()
      where id = 14100125;

--stg4
UPDATE studentstests set enrollmentid = 1930986,testsessionid = 2252641, modifieduser = 12, modifieddate = now()
      where id = 13230756;

--ELA
--stg1
UPDATE studentstests set enrollmentid = 1930986,testsessionid = 2252911, modifieduser = 12, modifieddate = now()
      where id = 9587498;

--stg2
UPDATE studentstests set enrollmentid = 1930986,testsessionid = 2482033, modifieduser = 12, modifieddate = now()
      where id = 11971248;
      
--prfrm
UPDATE studentstests set enrollmentid = 1930986,testsessionid = 2176995, modifieduser = 12, modifieddate = now()
      where id = 8603761;
      
--stg3
UPDATE studentstests set enrollmentid = 1930986,testsessionid = 2502723, modifieduser = 12, modifieddate = now()
      where id = 12067723;
--stg4
UPDATE studentstests set enrollmentid = 1930986,testsessionid = 2252910, modifieduser = 12, modifieddate = now()
      where id = 9587493;

