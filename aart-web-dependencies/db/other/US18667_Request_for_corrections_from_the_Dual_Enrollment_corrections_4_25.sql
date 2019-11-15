--student: 6978486043
--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18667'
  where id = 2023078;

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18667'
  where id = 2126922;

--math
--prfrm
UPDATE studentstests set enrollmentid = 2126922,testsessionid = 2175359, modifieduser = 12, modifieddate = now()
      where id = 8711428;

--ELA
--stg1
UPDATE studentstests set enrollmentid = 2126922,testsessionid = 2249212, modifieduser = 12, modifieddate = now()
      where id = 9752124;

--stg2
UPDATE studentstests set enrollmentid = 2126922,testsessionid = 2762154, modifieduser = 12, modifieddate = now()
      where id = 12974190;
--prfrm
UPDATE studentstests set enrollmentid = 2126922,testsessionid = 2175401, modifieduser = 12, modifieddate = now()
      where id = 8681403;
      
--stg3
UPDATE studentstests set enrollmentid = 2126922,testsessionid = 2812154, modifieduser = 12, modifieddate = now()
      where id = 13203477;
--stg4
UPDATE studentstests set enrollmentid = 2126922,testsessionid = 2249211, modifieduser = 12, modifieddate = now()
      where id = 13230637;

--student: 1302712144
--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18667'
  where id = 2377832;

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18667'
  where id = 1839243;

--Math
--stg1
UPDATE studentstests set enrollmentid = 1839243,testsessionid = 2261767, modifieduser = 12, modifieddate = now()
      where id = 10155618;

--stg2
UPDATE studentstests set enrollmentid = 1839243,testsessionid = 2407607, modifieduser = 12, modifieddate = now()
      where id = 12789555;
--prfrm
UPDATE studentstests set enrollmentid = 1839243,testsessionid = 2203087, modifieduser = 12, modifieddate = now()
      where id = 9133640;
      
--stg3
UPDATE studentstests set enrollmentid = 1839243,testsessionid = 2408281, modifieduser = 12, modifieddate = now()
      where id = 12793761;
--stg4
UPDATE studentstests set enrollmentid = 1839243,testsessionid = 2261766, modifieduser = 12, modifieddate = now()
      where id = 10155616;


--ELA
--stg1
UPDATE studentstests set enrollmentid = 1839243,testsessionid = 2250102, modifieduser = 12, modifieddate = now()
      where id = 9436648;

--stg2
UPDATE studentstests set enrollmentid = 1839243,testsessionid = 2383109, modifieduser = 12, modifieddate = now()
      where id = 12110806;
--prfrm
UPDATE studentstests set enrollmentid = 1839243,testsessionid = 2202942, modifieduser = 12, modifieddate = now()
      where id = 9129155;
      
--stg3
UPDATE studentstests set enrollmentid = 1839243,testsessionid = 2383507, modifieduser = 12, modifieddate = now()
      where id = 13288038;
--stg4
UPDATE studentstests set enrollmentid = 1839243,testsessionid = 2250101, modifieduser = 12, modifieddate = now()
      where id = 13229872;  