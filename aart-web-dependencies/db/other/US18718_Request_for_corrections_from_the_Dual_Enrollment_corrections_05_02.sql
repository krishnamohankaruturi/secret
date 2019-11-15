--student: 3152583931

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18718'
  where id = 2134123;

--Math
--stg1
UPDATE studentstests set enrollmentid = 2134123,testsessionid = 2257341, modifieduser = 12, modifieddate = now()
      where id = 10094363;

--prfrm
UPDATE studentstests set enrollmentid = 2134123,testsessionid = 2182936, modifieduser = 12, modifieddate = now()
      where id = 9035173;




--ELA
--stg1
UPDATE studentstests set enrollmentid = 2134123,testsessionid = 2265110, modifieduser = 12, modifieddate = now()
      where id = 10439004;

--stg2
UPDATE studentstests set enrollmentid = 2134123,testsessionid = 3007120, modifieduser = 12, modifieddate = now()
      where id = 13375018;

--prfrm
UPDATE studentstests set enrollmentid = 2134123,testsessionid = 2183111, modifieduser = 12, modifieddate = now()
      where id = 8968126;

--stg3
UPDATE studentstests set enrollmentid = 2134123,testsessionid = 3057889, modifieduser = 12, modifieddate = now()
      where id = 13467902;

--stg4
UPDATE studentstests set enrollmentid = 2134123,testsessionid = 2265109, modifieduser = 12, modifieddate = now()
      where id = 13230393;

--Sci
--stg4
UPDATE studentstests set enrollmentid = 2134123,testsessionid = 2271819, modifieduser = 12, modifieddate = now()
      where id = 10876188;


--student: 4162467609

--Inactivate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18718'
  where id = 2354780;

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18718'
  where id = 2239086;

--Math
--stg1
UPDATE studentstests set enrollmentid = 2239086,testsessionid = 2256021, modifieduser = 12, modifieddate = now()
      where id = 10078852;

--stg2
UPDATE studentstests set enrollmentid = 2239086,testsessionid = 2498847, modifieduser = 12, modifieddate = now()
      where id = 12045621;

--prfrm
UPDATE studentstests set enrollmentid = 2239086,testsessionid = 2182555, modifieduser = 12, modifieddate = now()
      where id = 9005291;

--stg3
UPDATE studentstests set enrollmentid = 2239086,testsessionid = 2740319, modifieduser = 12, modifieddate = now()
      where id = 12976197;

--stg4
UPDATE studentstests set enrollmentid = 2239086,testsessionid = 2256020, modifieduser = 12, modifieddate = now()
      where id = 9976443;

--ELA
--stg1
UPDATE studentstests set enrollmentid = 2239086,testsessionid = 2264548, modifieduser = 12, modifieddate = now()
      where id = 10434474;

--stg2
UPDATE studentstests set enrollmentid = 2239086,testsessionid = 2397368, modifieduser = 12, modifieddate = now()
      where id = 12322569;

--prfrm
UPDATE studentstests set enrollmentid = 2239086,testsessionid = 2183111, modifieduser = 12, modifieddate = now()
      where id = 9016547;

--stg3
UPDATE studentstests set enrollmentid = 2239086,testsessionid = 2182707, modifieduser = 12, modifieddate = now()
      where id = 13069520;

--stg4
UPDATE studentstests set enrollmentid = 2239086,testsessionid = 2264547, modifieduser = 12, modifieddate = now()
      where id = 13230384;

--Sci
--stg1
UPDATE studentstests set enrollmentid = 2239086,testsessionid = 2271294, modifieduser = 12, modifieddate = now()
      where id = 10875741;

--stg2
UPDATE studentstests set enrollmentid = 2239086,testsessionid = 2271295, modifieduser = 12, modifieddate = now()
      where id = 10875742;

--stg3
UPDATE studentstests set enrollmentid = 2239086,testsessionid = 2271293, modifieduser = 12, modifieddate = now()
      where id = 10875739;