--student: 2088400397
--Inactivate school 0846
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18583'
  where id = 2357570;

--activate school 9025
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18583'
  where id = 2090761;


--Math
--stg1
UPDATE studentstests set enrollmentid = 2090761,testsessionid = 2255315, modifieduser = 12, modifieddate = now()
      where id = 10105575;

--stg2
UPDATE studentstests set enrollmentid = 2090761,testsessionid = 2911408, modifieduser = 12, modifieddate = now()
      where id = 13221016;
--prfrm
UPDATE studentstests set enrollmentid = 2090761,testsessionid = 2177873, modifieduser = 12, modifieddate = now()
      where id = 8662455;

--ELA
--stg1
UPDATE studentstests set enrollmentid = 2090761,testsessionid = 2264438, modifieduser = 12, modifieddate = now()
      where id = 10293784;

--stg2
UPDATE studentstests set enrollmentid = 2090761,testsessionid = 2628622, modifieduser = 12, modifieddate = now()
      where id = 12715846;
--prfrm
UPDATE studentstests set enrollmentid = 2090761,testsessionid = 2178333, modifieduser = 12, modifieddate = now()
      where id = 8687089;
