--student: 8392807413
--prfrm
UPDATE studentstests set enrollmentid = 1929237,testsessionid = 2191937, modifieduser = 12, modifieddate = now()
      where id = 9018347;
--prfrm
UPDATE studentstests set enrollmentid = 1929237,testsessionid = 2191942, modifieduser = 12, modifieddate = now()
      where id = 9030943;

--science
UPDATE studentstests set enrollmentid = 1929237,testsessionid = 2269450, modifieduser = 12, modifieddate = now()
      where id = 10599068;

UPDATE studentstests set enrollmentid = 1929237,testsessionid = 2269451, modifieduser = 12, modifieddate = now()
      where id = 10599073;

UPDATE studentstests set enrollmentid = 1929237,testsessionid = 2269449, modifieduser = 12, modifieddate = now()
      where id = 10599063;

--student: 1285300599
--Inactivate school 5814
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18548'
  where id = 1967971;

--activate school 1353
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18548'
  where id = 2276992;
--Math
--stg1
UPDATE studentstests set enrollmentid = 2276992,testsessionid = 2250062, modifieduser = 12, modifieddate = now()
      where id = 10041455;

--stg2
UPDATE studentstests set enrollmentid = 2276992,testsessionid = 2628391, modifieduser = 12, modifieddate = now()
      where id = 12811257;
--prfrm
UPDATE studentstests set enrollmentid = 2276992,testsessionid = 2180709, modifieduser = 12, modifieddate = now()
      where id = 8907806;
--stg4
UPDATE studentstests set enrollmentid = 2276992,testsessionid = 2250061, modifieduser = 12, modifieddate = now()
      where id = 10041447;

--ELA
--stg1
UPDATE studentstests set enrollmentid = 2276992,testsessionid = 2261645, modifieduser = 12, modifieddate = now()
      where id = 10419568;

--stg2
UPDATE studentstests set enrollmentid = 2276992,testsessionid = 2497063, modifieduser = 12, modifieddate = now()
      where id = 12629760;
--prfrm
UPDATE studentstests set enrollmentid = 2276992,testsessionid = 2180528, modifieduser = 12, modifieddate = now()
      where id = 8908734;

--stg3
UPDATE studentstests set enrollmentid = 2276992,testsessionid = 2527925, modifieduser = 12, modifieddate = now()
      where id = 12752496;
--stg4
UPDATE studentstests set enrollmentid = 2276992,testsessionid = 2261644, modifieduser = 12, modifieddate = now()
      where id = 12528894;

--Sci
--stg1
UPDATE studentstests set enrollmentid = 2276992,testsessionid = 2267564, modifieduser = 12, modifieddate = now()
      where id = 10615671;

--stg2
UPDATE studentstests set enrollmentid = 2276992,testsessionid = 2267565, modifieduser = 12, modifieddate = now()
      where id = 10615675;
--stg4
UPDATE studentstests set enrollmentid = 2276992,testsessionid = 2267563, modifieduser = 12, modifieddate = now()
      where id = 10615667;

--student: 7816586736
--Inactivate school 1611
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18548'
  where id = 2051483;

--activate school 1588
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18548'
  where id = 2387737;

--Math
--stg1
UPDATE studentstests set enrollmentid = 2387737,testsessionid = 2254605, modifieduser = 12, modifieddate = now()
      where id = 12201273;

--stg2
UPDATE studentstests set enrollmentid = 2387737,testsessionid = 2628440, modifieduser = 12, modifieddate = now()
      where id = 12756460;
--prfrm
UPDATE studentstests set enrollmentid = 2387737,testsessionid = 2182139, modifieduser = 12, modifieddate = now()
      where id = 8932211;
--stg3
UPDATE studentstests set enrollmentid = 2387737,testsessionid = 2677478, modifieduser = 12, modifieddate = now()
      where id = 12968789;

--ELA
--stg1
UPDATE studentstests set enrollmentid = 2387737,testsessionid = 2263952, modifieduser = 12, modifieddate = now()
      where id = 12235855;

--stg2
UPDATE studentstests set enrollmentid = 2387737,testsessionid = 2561130, modifieduser = 12, modifieddate = now()
      where id = 12398171;
--prfrm
UPDATE studentstests set enrollmentid = 2387737,testsessionid = 2182209, modifieduser = 12, modifieddate = now()
      where id = 8939885;
--stg3
UPDATE studentstests set enrollmentid = 2387737,testsessionid = 2662925, modifieduser = 12, modifieddate = now()
      where id = 12794860;