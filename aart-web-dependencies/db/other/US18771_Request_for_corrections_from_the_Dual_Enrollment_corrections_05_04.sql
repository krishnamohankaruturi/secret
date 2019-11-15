--student: 4286807533

--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'inactivated according to US18771'
  where id = 2239028;

--student: 2116622166

--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'inactivated according to US18771'
  where id = 2362261;


--Math
--stg1
UPDATE studentstests set enrollmentid = 2027260,testsessionid = 2253130, modifieduser = 12, modifieddate = now()
      where id = 10139558;

--stg2
UPDATE studentstests set enrollmentid = 2027260,testsessionid = 2754819, modifieduser = 12, modifieddate = now()
      where id = 13826517;

--prfrm
UPDATE studentstests set enrollmentid = 2027260,testsessionid = 2182108, modifieduser = 12, modifieddate = now()
      where id = 9090762;

--stg3
UPDATE studentstests set enrollmentid = 2027260,testsessionid = 2755990, modifieduser = 12, modifieddate = now()
      where id = 13940951;

--stg4
UPDATE studentstests set enrollmentid = 2027260,testsessionid = 2253129, modifieduser = 12, modifieddate = now()
      where id = 10139555;

--ELA
--stg1
UPDATE studentstests set enrollmentid = 2027260,testsessionid = 2253836, modifieduser = 12, modifieddate = now()
      where id = 10180534;

--stg2
UPDATE studentstests set enrollmentid = 2027260,testsessionid = 2627811, modifieduser = 12, modifieddate = now()
      where id = 12267836;

--prfrm
UPDATE studentstests set enrollmentid = 2027260,testsessionid = 2177386, modifieduser = 12, modifieddate = now()
      where id = 9091185;

--stg3
UPDATE studentstests set enrollmentid = 2027260,testsessionid = 2628025, modifieduser = 12, modifieddate = now()
      where id = 12894209;

--stg4
UPDATE studentstests set enrollmentid = 2027260,testsessionid = 2253835, modifieduser = 12, modifieddate = now()
      where id = 12586004;

--student: 9103465454

--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'inactivated according to US18771'
  where id = 2234465;

--student: 2428233688

--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'inactivated according to US18771'
  where id = 2362219;

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18771'
  where id = 2027281;

--Math
--stg1
UPDATE studentstests set enrollmentid = 2027281,testsessionid = 2253190, modifieduser = 12, modifieddate = now()
      where id = 9627863;

--stg2
UPDATE studentstests set enrollmentid = 2027281,testsessionid = 2756285, modifieduser = 12, modifieddate = now()
      where id = 12878709;

--prfrm
UPDATE studentstests set enrollmentid = 2027281,testsessionid = 2176984, modifieduser = 12, modifieddate = now()
      where id = 8616182;

--stg3
UPDATE studentstests set enrollmentid = 2027281,testsessionid = 2762140, modifieduser = 12, modifieddate = now()
      where id = 12891048;

--stg4
UPDATE studentstests set enrollmentid = 2027281,testsessionid = 2253189, modifieduser = 12, modifieddate = now()
      where id = 9627852;
  
--ELA
--stg1
UPDATE studentstests set enrollmentid = 2027281,testsessionid = 2263531, modifieduser = 12, modifieddate = now()
      where id = 10254140;

--stg2
UPDATE studentstests set enrollmentid = 2027281,testsessionid = 2511026, modifieduser = 12, modifieddate = now()
      where id = 12102761;

--prfrm
UPDATE studentstests set enrollmentid = 2027281,testsessionid = 2177397, modifieduser = 12, modifieddate = now()
      where id = 8635014;

--stg3
UPDATE studentstests set enrollmentid = 2027281,testsessionid = 2513364, modifieduser = 12, modifieddate = now()
      where id = 12284821;

--stg4
UPDATE studentstests set enrollmentid = 2027281,testsessionid = 2263530, modifieduser = 12, modifieddate = now()
      where id = 10445347;
  
--student: 2841107671

--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'inactivated according to US18771'
  where id = 2362216;

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18771'
  where id = 2027385;

--Math
--stg1
UPDATE studentstests set enrollmentid = 2027385,testsessionid = 2253202, modifieduser = 12, modifieddate = now()
      where id = 10098753;

--stg2
UPDATE studentstests set enrollmentid = 2027385,testsessionid = 2772192, modifieduser = 12, modifieddate = now()
      where id = 13703356;

--prfrm
UPDATE studentstests set enrollmentid = 2027385,testsessionid = 2181747, modifieduser = 12, modifieddate = now()
      where id = 9084272;

--stg3
UPDATE studentstests set enrollmentid = 2027385,testsessionid = 2774602, modifieduser = 12, modifieddate = now()
      where id = 13742430;

--stg4
UPDATE studentstests set enrollmentid = 2027385,testsessionid = 2253201, modifieduser = 12, modifieddate = now()
      where id = 13231044;

--ELA
--stg1
UPDATE studentstests set enrollmentid = 2027385,testsessionid = 2263318, modifieduser = 12, modifieddate = now()
      where id = 10439937;

--stg2
UPDATE studentstests set enrollmentid = 2027385,testsessionid = 2511757, modifieduser = 12, modifieddate = now()
      where id = 12102199;

--prfrm
UPDATE studentstests set enrollmentid = 2027385,testsessionid = 2181779, modifieduser = 12, modifieddate = now()
      where id = 8921746;

--stg3
UPDATE studentstests set enrollmentid = 2027385,testsessionid = 2574327, modifieduser = 12, modifieddate = now()
      where id = 13501503;

--stg4
UPDATE studentstests set enrollmentid = 2027385,testsessionid = 2263317, modifieduser = 12, modifieddate = now()
      where id = 13230400;

--student: 1291467122

--Inactivate
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'inactivated according to US18771'
  where id = 2359748;

--student: 8967448937

--activate
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18771'
  where id = 2027422;

--Math
--stg1
UPDATE studentstests set enrollmentid = 2027422,testsessionid = 2253436, modifieduser = 12, modifieddate = now()
      where id = 10123570;

--stg2
UPDATE studentstests set enrollmentid = 2027422,testsessionid = 2976111, modifieduser = 12, modifieddate = now()
      where id = 13317127;

--prfrm
UPDATE studentstests set enrollmentid = 2027422,testsessionid = 2181883, modifieduser = 12, modifieddate = now()
      where id = 8937315;

--stg3
UPDATE studentstests set enrollmentid = 2027422,testsessionid = 3005543, modifieduser = 12, modifieddate = now()
      where id = 13355557;

--stg4
UPDATE studentstests set enrollmentid = 2027422,testsessionid = 2253435, modifieduser = 12, modifieddate = now()
      where id = 13231094;

--ELA
--stg1
UPDATE studentstests set enrollmentid = 2027422,testsessionid = 2253856, modifieduser = 12, modifieddate = now()
      where id = 10153392;

--stg2
UPDATE studentstests set enrollmentid = 2027422,testsessionid = 2573513, modifieduser = 12, modifieddate = now()
      where id = 12300879;

--prfrm
UPDATE studentstests set enrollmentid = 2027422,testsessionid = 2177404, modifieduser = 12, modifieddate = now()
      where id = 8666092;

--stg3
UPDATE studentstests set enrollmentid = 2027422,testsessionid = 2575310, modifieduser = 12, modifieddate = now()
      where id = 12306215;

--stg4
UPDATE studentstests set enrollmentid = 2027422,testsessionid = 2253855, modifieduser = 12, modifieddate = now()
      where id = 9721504;

--Social
 --stg1
UPDATE studentstests set enrollmentid = 2027422,testsessionid = 2268157, modifieduser = 12, modifieddate = now()
      where id = 10677155;


--student: 1917931093

--inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivated according to US18771'
  where id = 2362627;

--student: 8126518553
--Inactivate school
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'inactivated according to US18771'
  where id = 2239225;

--activate school
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Reactivated according to US18771'
  where id = 2182316;

--Math
--stg1
UPDATE studentstests set enrollmentid = 2182316,testsessionid = 2263415, modifieduser = 12, modifieddate = now()
      where id = 10379238;

--stg2
UPDATE studentstests set enrollmentid = 2182316,testsessionid = 2875001, modifieduser = 12, modifieddate = now()
      where id = 12958187;

--prfrm
UPDATE studentstests set enrollmentid = 2182316,testsessionid = 2177248, modifieduser = 12, modifieddate = now()
      where id = 8744311;

--stg3
UPDATE studentstests set enrollmentid = 2182316,testsessionid = 2805032, modifieduser = 12, modifieddate = now()
      where id = 12968641;

--stg4
UPDATE studentstests set enrollmentid = 2182316,testsessionid = 2263414, modifieduser = 12, modifieddate = now()
      where id = 13230814;

--ELA
--stg1
UPDATE studentstests set enrollmentid = 2182316,testsessionid = 2254238, modifieduser = 12, modifieddate = now()
      where id = 9940248;

--stg2
UPDATE studentstests set enrollmentid = 2182316,testsessionid = 2511657, modifieduser = 12, modifieddate = now()
      where id = 12118533;

--prfrm
UPDATE studentstests set enrollmentid = 2182316,testsessionid = 2177556, modifieduser = 12, modifieddate = now()
      where id = 8809875;

--stg3
UPDATE studentstests set enrollmentid = 2182316,testsessionid = 2589867, modifieduser = 12, modifieddate = now()
      where id = 12322400;

--stg4
UPDATE studentstests set enrollmentid = 2182316,testsessionid = 2254237, modifieduser = 12, modifieddate = now()
      where id = 12515161;
      