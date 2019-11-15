--Inactivate school 5761
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18521'
  where id = 2382237;

--activate school 5798
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18521'
  where id = 2188801;

--Math
--stage1
UPDATE studentstests set enrollmentid = 2188801,testsessionid = 2258611, modifieduser = 12, modifieddate = now()
      where id = 9860133;
      
--stage2
UPDATE studentstests set enrollmentid = 2188801,testsessionid = 2576346, modifieduser = 12, modifieddate = now()
      where id = 13123016;
      
--prfrm
UPDATE studentstests set enrollmentid = 2188801,testsessionid = 2179366, modifieduser = 12, modifieddate = now()
      where id = 8726050;
      
--stage4
UPDATE studentstests set enrollmentid = 2188801,testsessionid = 2258610, modifieduser = 12, modifieddate = now()
      where id = 9877683;

--ELA
--stage1
UPDATE studentstests set enrollmentid = 2188801,testsessionid = 2265567, modifieduser = 12, modifieddate = now()
      where id = 10361388;
      
--stage2
UPDATE studentstests set enrollmentid = 2188801,testsessionid = 2776856, modifieduser = 12, modifieddate = now()
      where id = 13123024;
      
--prfrm
UPDATE studentstests set enrollmentid = 2188801,testsessionid = 2179661, modifieduser = 12, modifieddate = now()
      where id = 8756871;
      
--stage3
UPDATE studentstests set enrollmentid = 2188801,testsessionid = 2833795, modifieduser = 12, modifieddate = now()
      where id = 13178626;

