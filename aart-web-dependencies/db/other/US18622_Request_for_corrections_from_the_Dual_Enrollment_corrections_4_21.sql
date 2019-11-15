--student: 3511585694
--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18622'
  where id = 2138335;

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18622'
  where id = 2091280;

--math
--stg1
UPDATE studentstests set enrollmentid = 2091280,testsessionid = 2255619, modifieduser = 12, modifieddate = now()
      where id = 9799669;

--prfrm
UPDATE studentstests set enrollmentid = 2091280,testsessionid = 2178049, modifieduser = 12, modifieddate = now()
      where id = 8662857;
--stg4
UPDATE studentstests set enrollmentid = 2091280,testsessionid = 2255618, modifieduser = 12, modifieddate = now()
      where id = 13229359;

--ELA
--stg1
UPDATE studentstests set enrollmentid = 2091280,testsessionid = 2264662, modifieduser = 12, modifieddate = now()
      where id = 10294180;

--stg2
UPDATE studentstests set enrollmentid = 2091280,testsessionid = 2479145, modifieduser = 12, modifieddate = now()
      where id = 12274208;
--prfrm
UPDATE studentstests set enrollmentid = 2091280,testsessionid = 2178533, modifieduser = 12, modifieddate = now()
      where id = 8687487;
      
--stg3
UPDATE studentstests set enrollmentid = 2091280,testsessionid = 2498645, modifieduser = 12, modifieddate = now()
      where id = 13122978;
--stg4
UPDATE studentstests set enrollmentid = 2091280,testsessionid = 2264661, modifieduser = 12, modifieddate = now()
      where id = 13229373;

--student: 5006646101
--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18622'
  where id = 2138117;

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18622'
  where id = 2091239;

--Math
--stg1
UPDATE studentstests set enrollmentid = 2091239,testsessionid = 2255929, modifieduser = 12, modifieddate = now()
      where id = 9813906;

--stg2
UPDATE studentstests set enrollmentid = 2091239,testsessionid = 3083546, modifieduser = 12, modifieddate = now()
      where id = 13505437;
--prfrm
UPDATE studentstests set enrollmentid = 2091239,testsessionid = 2182967, modifieduser = 12, modifieddate = now()
      where id = 8961547;
      
--stg3
UPDATE studentstests set enrollmentid = 2091239,testsessionid = 3118936, modifieduser = 12, modifieddate = now()
      where id = 13614403;
--stg4
UPDATE studentstests set enrollmentid = 2091239,testsessionid = 2255927, modifieduser = 12, modifieddate = now()
      where id = 13229370;

--ELA
--stg1
UPDATE studentstests set enrollmentid = 2091239,testsessionid = 2256709, modifieduser = 12, modifieddate = now()
      where id = 9764680;

--stg2
UPDATE studentstests set enrollmentid = 2091239,testsessionid = 2778790, modifieduser = 12, modifieddate = now()
      where id = 13122967;
--prfrm
UPDATE studentstests set enrollmentid = 2091239,testsessionid = 2178620, modifieduser = 12, modifieddate = now()
      where id = 8690590;
      
--stg3
UPDATE studentstests set enrollmentid = 2091239,testsessionid = 2833768, modifieduser = 12, modifieddate = now()
      where id = 13288655;
--stg4
UPDATE studentstests set enrollmentid = 2091239,testsessionid = 2256708, modifieduser = 12, modifieddate = now()
      where id = 13229388;

--student: 9769678368
--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18622'
  where id = 2037087;

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18622'
  where id = 2182576;

--Math
--stg1
UPDATE studentstests set enrollmentid = 2182576,testsessionid = 2249464, modifieduser = 12, modifieddate = now()
      where id = 9866461;

--stg2
UPDATE studentstests set enrollmentid = 2182576,testsessionid = 2576334, modifieduser = 12, modifieddate = now()
      where id = 12269852;
--prfrm
UPDATE studentstests set enrollmentid = 2182576,testsessionid = 2175603, modifieduser = 12, modifieddate = now()
      where id = 8625267;
      
--stg3
UPDATE studentstests set enrollmentid = 2182576,testsessionid = 2580804, modifieduser = 12, modifieddate = now()
      where id = 12355540;
--stg4
UPDATE studentstests set enrollmentid = 2182576,testsessionid = 2249462, modifieduser = 12, modifieddate = now()
      where id = 9866454;

--ELA
--stg1
UPDATE studentstests set enrollmentid = 2182576,testsessionid = 2261869, modifieduser = 12, modifieddate = now()
      where id = 10263053;

--stg2
UPDATE studentstests set enrollmentid = 2182576,testsessionid = 2579690, modifieduser = 12, modifieddate = now()
      where id = 12281389;
--prfrm
UPDATE studentstests set enrollmentid = 2182576,testsessionid = 2175663, modifieduser = 12, modifieddate = now()
      where id = 8646230;
      
--stg3
UPDATE studentstests set enrollmentid = 2182576,testsessionid = 2602535, modifieduser = 12, modifieddate = now()
      where id = 12356440;
--stg4
UPDATE studentstests set enrollmentid = 2182576,testsessionid = 2261868, modifieduser = 12, modifieddate = now()
      where id = 12503289;


--student: 6567522755
--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18622'
  where id = 2041182;

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18622'
  where id = 2182462;

--Math
--stg1
UPDATE studentstests set enrollmentid = 2182462,testsessionid = 2252204, modifieduser = 12, modifieddate = now()
      where id = 9663785;

--stg2
UPDATE studentstests set enrollmentid = 2182462,testsessionid = 2605516, modifieduser = 12, modifieddate = now()
      where id = 12367220;
--prfrm
UPDATE studentstests set enrollmentid = 2182462,testsessionid = 2181894, modifieduser = 12, modifieddate = now()
      where id = 8943403;
      
--stg3
UPDATE studentstests set enrollmentid = 2182462,testsessionid = 2653354, modifieduser = 12, modifieddate = now()
      where id = 12680263;
--stg4
UPDATE studentstests set enrollmentid = 2182462,testsessionid = 2252203, modifieduser = 12, modifieddate = now()
      where id = 9663780; 

--ELA
--stg1
UPDATE studentstests set enrollmentid = 2182462,testsessionid = 2252469, modifieduser = 12, modifieddate = now()
      where id = 9924085;

--stg2
UPDATE studentstests set enrollmentid = 2182462,testsessionid = 2607065, modifieduser = 12, modifieddate = now()
      where id = 13122926;
--prfrm
UPDATE studentstests set enrollmentid = 2182462,testsessionid = 2176861, modifieduser = 12, modifieddate = now()
      where id = 8653411;
      
--stg4
UPDATE studentstests set enrollmentid = 2182462,testsessionid = 2252467, modifieduser = 12, modifieddate = now()
      where id = 13228881;     



--student: 3205078845
--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18622'
  where id = 2032843;

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18622'
  where id = 1998992;

--Math
--stg1
UPDATE studentstests set enrollmentid = 1998992,testsessionid = 2252204, modifieduser = 12, modifieddate = now()
      where id = 9602386;

--stg2
UPDATE studentstests set enrollmentid = 1998992,testsessionid = 2605516, modifieduser = 12, modifieddate = now()
      where id = 13122482;
--prfrm
UPDATE studentstests set enrollmentid = 1998992,testsessionid = 2181894, modifieduser = 12, modifieddate = now()
      where id = 8929408;      

--stg4
UPDATE studentstests set enrollmentid = 1998992,testsessionid = 2252203, modifieduser = 12, modifieddate = now()
      where id = 13228841; 

--ELA
--stg1
UPDATE studentstests set enrollmentid = 1998992,testsessionid = 2252469, modifieduser = 12, modifieddate = now()
      where id = 9627678;

--stg2
UPDATE studentstests set enrollmentid = 1998992,testsessionid = 2607065, modifieduser = 12, modifieddate = now()
      where id = 12552808;
--prfrm
UPDATE studentstests set enrollmentid = 1998992,testsessionid = 2176861, modifieduser = 12, modifieddate = now()
      where id = 8639399;
      
--stg4
UPDATE studentstests set enrollmentid = 1998992,testsessionid = 2252467, modifieduser = 12, modifieddate = now()
      where id = 12550525;

--student: 4734829195
--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18622'
  where id = 2146836;

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18622'
  where id = 1969321;  

--Math
--stg1
UPDATE studentstests set enrollmentid = 1969321,testsessionid = 2252782, modifieduser = 12, modifieddate = now()
      where id = 9828650;
--prfrm
UPDATE studentstests set enrollmentid = 1969321,testsessionid = 2182064, modifieduser = 12, modifieddate = now()
      where id = 8924484;      

--stg4
UPDATE studentstests set enrollmentid = 1969321,testsessionid = 2252781, modifieduser = 12, modifieddate = now()
      where id = 13229380;

--ELA
--stg1
UPDATE studentstests set enrollmentid = 1969321,testsessionid = 2253300, modifieduser = 12, modifieddate = now()
      where id = 9868436;

--stg2
UPDATE studentstests set enrollmentid = 1969321,testsessionid = 2585152, modifieduser = 12, modifieddate = now()
      where id = 12340479;
--prfrm
UPDATE studentstests set enrollmentid = 1969321,testsessionid = 2177160, modifieduser = 12, modifieddate = now()
      where id = 8610818;

--student: 6174232042
--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18622'
  where id = 2362018;

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18622'
  where id = 2278980; 

--ELA
--prfrm
UPDATE studentstests set enrollmentid = 2278980,testsessionid = 2175165, modifieduser = 12, modifieddate = now()
      where id = 8813753;

--Sci
--stg1
UPDATE studentstests set enrollmentid = 2278980,testsessionid = 2266578, modifieduser = 12, modifieddate = now()
      where id = 10859569;

--stg2
UPDATE studentstests set enrollmentid = 2278980,testsessionid = 2266580, modifieduser = 12, modifieddate = now()
      where id = 13228446;
--stg3
UPDATE studentstests set enrollmentid = 2278980,testsessionid = 2266577, modifieduser = 12, modifieddate = now()
      where id = 13228447; 

--Social
--stg1
UPDATE studentstests set enrollmentid = 2278980,testsessionid = 2266533, modifieduser = 12, modifieddate = now()
      where id = 10666683; 